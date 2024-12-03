package JMS.TextCenter;

import JMS.ServerClient.Client;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class MessageHandler {
    private final Client client;
    private final JTextArea areaToText;
    private final BlockingQueue<String> messageQueue = new LinkedBlockingQueue<>();

    public MessageHandler(Client client, JTextArea areaToText) {
        this.client = client;
        this.areaToText = areaToText;


        client.setMessageListener(message -> {
            try {
                messageQueue.put(message);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                System.err.println("Error adding message to queue: " + e.getMessage());
            }
        });

        new Thread(this::processMessages).start();

        System.out.println("MessageHandler initialized and listening for messages.");
    }

    public void processMessages() {
        while (true) {
            try {
                String message = messageQueue.take();
                SwingUtilities.invokeLater(() -> areaToText.append("Other: " + message + "\n"));

            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                System.err.println("Message processing interrupted.");
                break;
            }
        }
    }
    public String[] getTextHistory()
    {
        String allText = areaToText.getText();
        String[] strList = allText.split("\n");
        return strList;
    }

    public void onTextFieldAction(ActionEvent e) {
        String input = ((JTextField) e.getSource()).getText();
        if (!input.isEmpty()) {
            areaToText.append("You: " + input + "\n");
            ((JTextField) e.getSource()).setText("");
            client.sendMessage(input);
            System.out.println("Sent message: " + input);
        }
    }
}
