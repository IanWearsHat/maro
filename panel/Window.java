package panel;

import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;
import java.awt.Dimension;

@SuppressWarnings("serial")
public class Window extends JFrame {
    private static final Logger LOGGER = Logger.getLogger( Window.class.getName() );
    public static final int WIDTH = 1280;
    public static final int HEIGHT = 720;

    public Window() {
        setPreferredSize(new Dimension(WIDTH, HEIGHT));
        setFocusable(true);
        requestFocus();
    }
}
