package JMS;

import JMS.SendData.SendData;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;


public class TextCenter extends JFrame
{

    private JTextField textField;
    private ArrayList<String> textList = new ArrayList<String>();
    public JTextArea areaToText;
    private JLabel textLabel;
    private UIMessaging messaging = new UIMessaging();
    String phoneNumber;
    String otherPhoneNumber;
    String identification;


    public TextCenter(String PH, String OPH, String idNumber)
    {
        this.phoneNumber = PH;
        this.otherPhoneNumber = OPH;
        this.identification = idNumber;
        setTitle("Text Area");
        setSize(900,500);
        setLocation(960/2,540/2);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        textField = new JTextField();
        add(textField, BorderLayout.NORTH);

        //Text area
        areaToText = new JTextArea();
        areaToText.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(areaToText);
        add(scrollPane,BorderLayout.CENTER);
        //adding scroll pane to same area of text

        textLabel = new JLabel("You are texting ");
        add(textLabel,BorderLayout.SOUTH);

        textField.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                String input = textField.getText();

                if (!input.isEmpty())
                {
                    areaToText.append(input + "\n"); //put input to text area
                    textField.setText(""); //Make it nothing after entered
                    textList.add(input);
                    sendTextToLogic(input);
                }
            }

        });
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosed(WindowEvent e)
            {
                String directory = "C:\\Users\\Public\\";

                File file = null;

                try {

                    file = File.createTempFile(identification,"txt");


                    try (BufferedWriter writer = new BufferedWriter(new FileWriter(file)))
                    {
                        for (String item : textList)
                        {
                            writer.write(item);
                            writer.newLine();
                            System.out.println("written!");
                        }
                    }
                    SendData sendData = new SendData(identification,file);
                    boolean processed = sendData.processData();

                    if (processed)
                    {
                        if (file.delete())
                        {
                            System.out.println("File deleted");
                        }
                        else
                        {
                            System.out.println("Failed to delete");
                        }
                    }
                    else
                    {
                        System.out.println("File not processed succesfully");
                    }
                }
                catch (IOException ex)
                {
                    throw new RuntimeException(ex);
                }
            }

        });
    }

    public void sendTextToLogic(String input)
    {
        TextLogic logic = new TextLogic(input, phoneNumber, otherPhoneNumber);
    }
    }