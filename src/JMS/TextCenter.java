package JMS;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class TextCenter extends JFrame
{

    private JTextField textField;
    public JTextArea areaToText;
    private JLabel textLabel;
    private UIMessaging messaging = new UIMessaging();

    public TextCenter(String PH, String OPH)
    {
        setTitle("Text Area");
        setSize(900,500);
        setLocation(960/2,540/2);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
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
                }
            }
        });
    }
    
}
