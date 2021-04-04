package panel;

import javax.swing.JFrame;
import java.awt.Dimension;

@SuppressWarnings("serial")
public class Window extends JFrame {
    public static final int WIDTH = 1280;
    public static final int HEIGHT = 720;

    public Window() {
        setPreferredSize(new Dimension(WIDTH, HEIGHT));
        setFocusable(true);
        requestFocus();
    }
}
