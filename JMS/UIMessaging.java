package JMS;

import javax.swing.*;

public class UIMessaging extends JFrame {
    public UIMessaging() {
        setTitle("Texterly");
        setSize(900, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(true);
        setLayout(null);
        setLocationRelativeTo(null);

        // Initialize UI components and layout
        UIComponents components = new UIComponents();
        LayoutManager layoutManager = new LayoutManager(this, components);
        ActionHandlers handlers = new ActionHandlers(this, components);
        //components, layout manager, handlers
        // Add components to the frame
        components.addComponents(this);

        // Add listeners
        layoutManager.attachResizeListener();
        handlers.attachActionListeners();

        setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(UIMessaging::new);
    }
}
