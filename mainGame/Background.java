package mainGame;

import javax.swing.JPanel;
import javax.swing.KeyStroke;
import javax.swing.Action;
import javax.imageio.ImageIO;
import javax.swing.AbstractAction;
import javax.swing.SwingUtilities;
import javax.swing.event.MouseInputListener;

import chat.ChatRenderer;

import java.util.ArrayList;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D; 
import java.awt.RenderingHints;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseMotionAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Background extends JPanel implements Runnable {

    private Font basic = new Font("TimesRoman", Font.PLAIN, 30);
    private BufferedImage bg;
    private String bgPath = "Resources" + "\\" + "upscaledBG.gif";
    private final int FRAME_DELAY = 1000/60; //60 fps

    private ChatRenderer chatRenderer;

    public Background() {
        try {
            chatRenderer = new ChatRenderer();
            bg = ImageIO.read(Background.class.getResource(bgPath));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setFont(basic);

        g2.drawImage(bg, 0, 0, null);
        chatRenderer.render(g2);
    }

    public void run() {
        while (true) {
            repaint();
            try {
                Thread.sleep(FRAME_DELAY);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public ChatRenderer getChat() {
        return chatRenderer;
    }
}
