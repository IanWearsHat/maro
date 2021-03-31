package panel;

import javax.swing.JFrame;
import java.awt.Dimension;

@SuppressWarnings("serial")
public class Window extends JFrame {
    // Dimension size = Toolkit. getDefaultToolkit(). getScreenSize();
    // to get dimensions of the user's screen
    public static final int WIDTH = 1600;
    public static final int HEIGHT = 900;
    public static final int SCALE = 2;

    public Window() {
        super();
        setPreferredSize(new Dimension(WIDTH, HEIGHT));
        setFocusable(true);
        setResizable(false);
        requestFocus();
    }

    private void init() {
    }

    public void runFrame() {
        init();
    }

}
