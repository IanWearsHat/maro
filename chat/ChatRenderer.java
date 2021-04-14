package chat;

import java.util.ArrayList;
import java.util.logging.Logger;
import java.awt.Graphics;
import java.awt.Graphics2D; 

public class ChatRenderer {
    private static final Logger LOGGER = Logger.getLogger( ChatRenderer.class.getName() );
    private int maxMessages; // maximum number of messages before they start deleting, starting from the earliest sent

    private ArrayList<String> chatHistory;

    public ChatRenderer() {
        chatHistory = new ArrayList<String>();
    }

    public void addChat(String message) {
        chatHistory.add(message);
    }

    public void render(Graphics g) {
        g.drawString("hi", 0, 50);
    }
}
