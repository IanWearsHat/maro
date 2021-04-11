package chat;

import java.util.logging.Logger;

import javax.swing.JPanel;

@SuppressWarnings("serial")
public class ChatPanel extends JPanel {
    private static final Logger LOGGER = Logger.getLogger( ChatPanel.class.getName() );
    private int maxMessages; // maximum number of messages before they start deleting, starting from the earliest sent

    public ChatPanel() {

    }
    
}
