package panel;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.awt.Graphics2D;
import java.awt.event.*;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class Window extends JFrame {
    public static final int WIDTH = 1600;
    public static final int HEIGHT = 900;
    public static final int SCALE = 2;

    private int fps = 60;
    private long targetTime = 1000/fps;

    private BufferedImage image;
    private Graphics2D g;

    public Window() {
        super();
        setPreferredSize(new Dimension(WIDTH, HEIGHT));
        setFocusable(true);
        setResizable(false);
        requestFocus();
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    private void init() {
    }

    public void runFrame() {
        init();
    }

}
