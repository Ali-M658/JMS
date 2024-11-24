package JMS.UI;

import javax.swing.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

public class LayoutManager
{
    private final JFrame frame;
    private final UIComponents components;

    public LayoutManager(JFrame frame, UIComponents components)
    {
        this.frame = frame;
        this.components = components;
    }

    public void attachResizeListener()
    {
        frame.addComponentListener(new ComponentAdapter()
        {
            @Override
            public void componentResized(ComponentEvent e)
            {
                updateLayout(frame.getWidth(), frame.getHeight());
            }
        });
    }

    public void updateLayout(int width, int height)
    {
        components.yourAreaCodeLabel.setBounds(0, 0, 100, 40);
        components.yourAreaCode.setBounds((width * 110) / 900, 0, 50, 40);
        components.yourPhoneNumberLabel.setBounds((width * 260) / 900, 0, 300, 40);
        components.yourPhoneNumberField.setBounds((width * 500) / 900, 0, 200, 40);

        components.otherAreaCodeLabel.setBounds(0, 0, 100, 40);
        components.otherAreaCode.setBounds((width * 110) / 900, 0, 50, 40);
        components.otherPhoneNumberLabel.setBounds((width * 260) / 900, 0, 300, 40);
        components.otherPhoneNumberField.setBounds((width * 500) / 900, 0, 200, 40);

        components.next.setBounds((width * 800) / 900, (height * 500) / 900, 100, 50);
        components.back.setBounds((width * 800) / 900, (height * 400) / 900, 100, 50);

        components.loadConfig.setBounds((width * 500) / 900, (height * 500) / 900, 200,50);
        frame.revalidate();
        frame.repaint();
    }
}