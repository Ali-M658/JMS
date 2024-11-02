package JMS;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.util.ArrayList;
import java.util.Timer;

public class UIMessaging extends JFrame {
    //Fix fitting later
    ArrayList<Point> sizePoints = new ArrayList<Point>();

    Timer timer;

    ImageIcon imageIcon;

    JTextField yourPhoneNumberField;
    JLabel yourPhoneNumberLabel;

    JTextField yourAreaCode;
    JLabel yourAreaCodeLabel;

    JButton setNumber;
    JButton setAreaCode;

    JTextField otherPhoneNumberField;
    JLabel otherPhoneNumberLabel;

    JTextField otherAreaCode;
    JLabel otherAreaCodeLabel;

    JButton otherSetNumber;
    JButton otherSetAreaCode;


    JButton next;
    JButton back;

    Rectangle codeLabelLoc;
    Rectangle areaCodeLoc;
    Rectangle phoneNumberLoc;
    Rectangle phoneFieldLoc;
    Rectangle setNumberLoc;
    Rectangle setAreaCodeLoc;
    Rectangle nextLoc;
    Rectangle backLoc;

    public UIMessaging() {

        codeLabelLoc = new Rectangle(0, 0, 100, 40);
        areaCodeLoc = new Rectangle((900 * 110) / 900, 0, 50, 40);
        phoneNumberLoc = new Rectangle((900 * 160) / 900, 0, 300, 40); /**Starting point */
        phoneFieldLoc = new Rectangle((900 * 300) / 900, 0, 200, 40);
        setNumberLoc = new Rectangle((900 * 590) / 900, 0, 100, 40);
        setAreaCodeLoc = new Rectangle((900 * 700) / 900, 0, 100, 40);
        nextLoc = new Rectangle(740, 500, 100, 50);
        backLoc = new Rectangle(740, 400, 100, 50);
        System.out.println("If selected");


        imageIcon = new ImageIcon("C:\\Users\\ali\\Downloads\\Message-icon.png");
        setSize(900, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(true);
        setLayout(null);
        setIconImage(imageIcon.getImage());
        setTitle("Texterly");
        setVisible(true);
        setLocation(960 / 2, 480 / 2);

        yourAreaCodeLabel = new JLabel("Enter area code: ");
        yourAreaCodeLabel.setBounds(codeLabelLoc);
        add(yourAreaCodeLabel);

        yourAreaCode = new JTextField("");
        yourAreaCode.setBounds(areaCodeLoc);
        add(yourAreaCode);

        yourPhoneNumberLabel = new JLabel("Put Phone number without area code: ");
        yourPhoneNumberLabel.setBounds(phoneNumberLoc);
        add(yourPhoneNumberLabel);

        yourPhoneNumberField = new JTextField("");
        yourPhoneNumberField.setBounds(phoneFieldLoc);
        add(yourPhoneNumberField);

        setNumber = new JButton("Set PH");
        setNumber.setBounds(setNumberLoc);
        add(setNumber);

        setAreaCode = new JButton("Set Area Code");
        setAreaCode.setBounds(setAreaCodeLoc);
        add(setAreaCode);

        otherAreaCodeLabel = new JLabel("Enter area code: ");
        otherAreaCodeLabel.setBounds(codeLabelLoc);
        add(otherAreaCodeLabel);

        otherAreaCode = new JTextField("");
        otherAreaCode.setBounds(areaCodeLoc);
        otherAreaCode.setVisible(false);
        add(otherAreaCode);

        otherPhoneNumberLabel = new JLabel("Put Phone number without area code: ");
        otherPhoneNumberLabel.setBounds(phoneNumberLoc);
        otherPhoneNumberLabel.setVisible(false);
        add(otherPhoneNumberLabel);

        otherPhoneNumberField = new JTextField("");
        otherPhoneNumberField.setBounds(phoneFieldLoc);
        otherPhoneNumberField.setVisible(false);
        add(otherPhoneNumberField);

        otherSetNumber = new JButton("Set OPH");
        otherSetNumber.setBounds(setNumberLoc);
        otherSetNumber.setVisible(false);
        add(otherSetNumber);

        otherSetAreaCode = new JButton("Set Area Code");
        otherSetAreaCode.setBounds(setAreaCodeLoc);
        otherSetAreaCode.setVisible(false);
        add(otherSetAreaCode);


        next = new JButton("next");
        next.setBounds(nextLoc);
        next.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                yourPhoneNumberField.setVisible(false);
                yourPhoneNumberLabel.setVisible(false);

                yourAreaCode.setVisible(false);
                yourAreaCodeLabel.setVisible(false);

                setNumber.setVisible(false);
                setAreaCode.setVisible(false);

                otherAreaCodeLabel.setVisible(true);
                otherAreaCode.setVisible(true);

                otherPhoneNumberField.setVisible(true);
                otherPhoneNumberLabel.setVisible(true);

                otherSetNumber.setVisible(true);
                otherSetAreaCode.setVisible(true);
            }
        });
        add(next);

        back = new JButton("back");
        back.setBounds(backLoc);
        back.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                yourPhoneNumberField.setVisible(true);
                yourPhoneNumberLabel.setVisible(true);

                yourAreaCode.setVisible(true);
                yourAreaCodeLabel.setVisible(true);

                setNumber.setVisible(true);
                setAreaCode.setVisible(true);

                otherAreaCodeLabel.setVisible(false);
                otherAreaCode.setVisible(false);

                otherPhoneNumberField.setVisible(false);
                otherPhoneNumberLabel.setVisible(false);

                otherSetNumber.setVisible(false);
                otherSetAreaCode.setVisible(false);
            }
        });
        add(back);

        addComponentListener(new ComponentAdapter() {
            public void componentResized(ComponentEvent e) {
                Point point = new Point(getWidth(), getHeight());
                sizePoints.add(point);
                if (sizePoints.size() > 1) {
                    sizePoints.remove(0);
                    reDoButton();
                }
                System.out.println("Size of the SizePoints: " + sizePoints.size() + " Size of screen " + "(" + sizePoints.get(sizePoints.size()-1).x + "," + sizePoints.get(sizePoints.size()-1).y + ")");
                //It's going to change so make it -1
            }
        });
    }

    // Class-level variables to store the previous dimensions of the board
    private int previousWidth = getWidth();
    private int previousHeight = getHeight();

    /**
     * Checks if the dimensions of the board have changed since the last check.
     *
     * @return true if the board dimensions have changed, false otherwise.
     */

    public void reDoButton() {
        for (Point sizePoint : sizePoints) {
            int currentWidth = sizePoint.x;
            int currentHeight = sizePoint.y;

            // Define component locations based on current size
            updateComponentBounds(currentWidth, currentHeight);

            // Special case for fullscreen size
            if (currentWidth == 1550 && currentHeight == 830) {
                System.out.println("Fullscreen!");
                updateComponentBounds(getWidth(), getHeight());
            }
        }
    }

    private void updateComponentBounds(int width, int height) {
        codeLabelLoc = new Rectangle(width * 0, 0, 100, 40);
        areaCodeLoc = new Rectangle((width * 110) / 900, 0, 50, 40);
        phoneNumberLoc = new Rectangle((width * 160) / 900, 0, 300, 40);
        phoneFieldLoc = new Rectangle((width * 300) / 900, 0, 200, 40);
        setNumberLoc = new Rectangle((width * 590) / 900, 0, 100, 40);
        setAreaCodeLoc = new Rectangle((width * 700) / 900, 0, 100, 40);
        nextLoc = new Rectangle((width*800)/900, (height * 500) / 900, 100, 50);
        backLoc = new Rectangle((width*800)/900, (height * 400) / 900,100,50);

        // Set bounds for UI components
        yourAreaCodeLabel.setBounds(codeLabelLoc);
        yourAreaCode.setBounds(areaCodeLoc);
        yourPhoneNumberLabel.setBounds(phoneNumberLoc);
        yourPhoneNumberField.setBounds(phoneFieldLoc);
        setNumber.setBounds(setNumberLoc);
        setAreaCode.setBounds(setAreaCodeLoc);
        next.setBounds(nextLoc);

        otherAreaCodeLabel.setBounds(codeLabelLoc);
        otherAreaCode.setBounds(areaCodeLoc);
        otherPhoneNumberLabel.setBounds(phoneNumberLoc);
        otherPhoneNumberField.setBounds(phoneFieldLoc);
        otherSetNumber.setBounds(setNumberLoc);
        otherSetAreaCode.setBounds(setAreaCodeLoc);
        back.setBounds(backLoc);

        revalidate();
        repaint();
    }


    public static void main(String[] args)
    {

        SwingUtilities.invokeLater(UIMessaging::new);
    }
}

