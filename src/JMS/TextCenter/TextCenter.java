package JMS.TextCenter;

import JMS.ServerClient.Client;

import JMS.ServerClient.Server;
import JMS.UI.UIMessaging;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.IOException;

public class TextCenter extends JFrame {
    private JTextField textField;
    private JTextArea areaToText;
    private JLabel textLabel;
    private Client client;
    private JMS.TextCenter.MessageHandler messageHandler;
    private String phoneNumber;
    private String otherPhoneNumber;

    public TextCenter(String PH, String OPH, String areaCode, String otherAreaCode, String serverAddress, int serverPort) throws IOException {
        this.phoneNumber = PH;
        this.otherPhoneNumber = OPH;
        Server server = new Server(PH,OPH,serverPort, serverAddress);
        server.trySomething(); //this is the reason for the exception
        this.client = new Client(serverAddress, serverPort);
        client.connectToServer(); // It has to be before to initalize dataoutput stream
        System.out.println("The port passed to the server is : "+ serverPort);
        setTitle("Text Area");
        setSize(900, 500);
        setLocation(960 / 2, 540 / 2);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());
        System.out.println("Text Center was called?");

        initUI();

        System.out.println("Client connected to server");

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosed(WindowEvent e) {
                FileOperations.saveToFile(phoneNumber, messageHandler.getTextHistory());
            }
        });
    }

    private void initUI() {

        areaToText = new JTextArea();
        areaToText.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(areaToText);
        add(scrollPane, BorderLayout.CENTER);

        this.messageHandler = new JMS.TextCenter.MessageHandler(client, areaToText);

        textField = new JTextField();
        textField.addActionListener(messageHandler::onTextFieldAction);
        add(textField, BorderLayout.NORTH);



        textLabel = new JLabel("You are texting " + otherPhoneNumber);
        add(textLabel, BorderLayout.SOUTH);
    }
}
