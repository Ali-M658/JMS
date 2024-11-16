package JMS;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ActionHandlers
{
    private final JFrame frame;
    private final UIComponents components;
    private int nextCounter = 0;

    public ActionHandlers(JFrame frame, UIComponents components)
    {
        this.frame = frame;
        this.components = components;
    }

    public void attachActionListeners() {
        components.next.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e) {
                nextCounter++;
                handleNextAction();
            }
        });

        components.back.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e) {
                nextCounter--;
                handleBackAction();
            }
        });
    }

    private void handleNextAction()
    {
        if (nextCounter == 1)
        {
            toggleVisibility(false, true); //using instance booleanse to toggle it
            //more on responsibility and less function
        } else if (nextCounter == 2)
        {
            String yourPhone = components.yourPhoneNumberField.getText();
            String otherPhone = components.otherPhoneNumberField.getText();
            new TextCenter(yourPhone, otherPhone).setVisible(true);
            frame.dispose();
        }
    }

    private void handleBackAction() {
        if (nextCounter == 0) {
            toggleVisibility(true, false);
        }
    }

    private void toggleVisibility(boolean isFirstStep, boolean isSecondStep)
    {
        components.yourPhoneNumberLabel.setVisible(isFirstStep);
        components.yourPhoneNumberField.setVisible(isFirstStep);
        components.yourAreaCodeLabel.setVisible(isFirstStep);
        components.yourAreaCode.setVisible(isFirstStep);
        //good work
        components.otherPhoneNumberLabel.setVisible(isSecondStep);
        components.otherPhoneNumberField.setVisible(isSecondStep);
        components.otherAreaCodeLabel.setVisible(isSecondStep);
        components.otherAreaCode.setVisible(isSecondStep);
    }
}
