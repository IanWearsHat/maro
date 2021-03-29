package panel;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.awt.Graphics2D;
import java.awt.event.*;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class Window extends JFrame {
    public static final int width = 1600;
    public static final int height = 900;
    public static final int scale = 2;

    private int fps = 60;
    private long targetTime = 1000/fps;

    private BufferedImage image;
    private Graphics2D g;

    public Window() {
        super();
        setPreferredSize(new Dimension(width, height));
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
