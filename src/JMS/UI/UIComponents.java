package JMS.UI;

import javax.swing.*;

public class UIComponents {
    public JTextField yourPhoneNumberField = new JTextField();
    public JLabel yourPhoneNumberLabel = new JLabel("Put Phone number without area code:");
    public JTextField yourAreaCode = new JTextField();
    public JLabel yourAreaCodeLabel = new JLabel("Enter area code:");

    public JTextField otherPhoneNumberField = new JTextField();
    public JLabel otherPhoneNumberLabel = new JLabel("Put sender's Phone number:");
    public JTextField otherAreaCode = new JTextField();
    public JLabel otherAreaCodeLabel = new JLabel("Sender's area code:");

    public JButton next = new JButton("Next");
    public JButton back = new JButton("Back");
    public JButton loadConfig = new JButton("Load past configurations if any");

    public void addComponents(JFrame frame) {
        frame.add(yourPhoneNumberLabel);
        frame.add(yourPhoneNumberField);
        frame.add(yourAreaCodeLabel);
        frame.add(yourAreaCode);

        frame.add(otherPhoneNumberLabel);
        frame.add(otherPhoneNumberField);
        frame.add(otherAreaCodeLabel);
        frame.add(otherAreaCode);

        frame.add(next);
        frame.add(back);

        frame.add(loadConfig);

        // Set initial visibility
        otherPhoneNumberLabel.setVisible(false);
        otherPhoneNumberField.setVisible(false);
        otherAreaCodeLabel.setVisible(false);
        otherAreaCode.setVisible(false);
    }
}
