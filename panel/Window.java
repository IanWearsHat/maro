package panel;

import javax.swing.JFrame;
import java.awt.Dimension;

@SuppressWarnings("serial")
public class Window extends JFrame {
    // Dimension size = Toolkit. getDefaultToolkit(). getScreenSize();
    // TODO: we need to get the dimensions of the user's screen still and make it so we can resize it
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
