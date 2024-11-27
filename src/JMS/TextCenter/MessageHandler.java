package JMS.TextCenter;

import JMS.ServerClient.Client;
import JMS.ServerClient.MessageBuffer;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.util.ArrayList;

public class MessageHandler {
    private Client client;
    private JTextArea areaToText;
    private ArrayList<String> textList = new ArrayList<>();

    public MessageHandler(Client client, JTextArea areaToText) {
        this.client = client;
        this.areaToText = areaToText;
        System.out.println("Line 18 area to text is initalied message Center");
    }

    public void appendRecievedMessage(String message) {
        SwingUtilities.invokeLater(() -> areaToText.append("Other: " + message + "\n"));
    }

    public void onTextFieldAction(ActionEvent e) {
        String input = ((JTextField) e.getSource()).getText();
        if (!input.isEmpty()) {
            sendTextToLogic(input);
            areaToText.append("You: " + input + "\n");
            textList.add(input);
            ((JTextField) e.getSource()).setText(""); // clear text field
            if (!MessageBuffer.message.isEmpty()) {
                areaToText.append("Other: " + MessageBuffer.message.get(0) + "\n");
                MessageBuffer.message.remove(0);
            }
            client.sendMessage(input);
        }
    }

    private void sendTextToLogic(String input) {
        client.sendMessage(input);
    }

    public ArrayList<String> getTextList() {
        return textList;
    }
}
