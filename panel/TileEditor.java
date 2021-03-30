package panel;

import java.awt.Color;
import java.awt.Font;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D; 
import java.awt.RenderingHints;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JFrame;
import javax.swing.JPanel;

import java.awt.image.BufferedImage;
import java.io.*;

// this should handle whatever is in the center of the screen, or the tileEditor, or where b2 currently is in Main.java

@SuppressWarnings("serial")
public class TileEditor extends JPanel implements Runnable {

    private final int FRAME_DELAY = 50; // 50 ms = 20 FPS

    private boolean animate = true;
    private int mouseX;
    private int mouseY;

    private Font basic = new Font("TimesRoman", Font.PLAIN, 30);
    
    public TileDrawer drawer;
    private int[][] tileMap;
    private int selectedTile = 21;

    public TileEditor() {
        addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                mouseX = e.getX();
                mouseY = e.getY();

                if (e.getButton() == MouseEvent.BUTTON1) {
                    drawer.updateTile(selectedTile, mouseX, mouseY);
                }
                else if (e.getButton() == MouseEvent.BUTTON3) {
                    drawer.updateTile(0, mouseX, mouseY);
                }
                
                System.out.println("click");
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                System.out.println("release");
            }
        });

        addKeyListener(new KeyListener() {
            public void keyTyped(KeyEvent e) {}
            public void keyReleased(KeyEvent e) {}
            public void keyPressed(KeyEvent e) { 
                if (e.getKeyChar() == 'a') {
                    drawer.addColumn(); 
                }
                else if (e.getKeyChar() == 'l') {
                    drawer.removeColumn();
                }
            }
        });

        drawer = new TileDrawer();
        drawer.loadTiles();
        drawer.initializeGrid();
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setFont(basic);

        drawer.draw(g2);
        requestFocus();
    }

    public void setSelectedTile(int tileIndex) {
        selectedTile = tileIndex;
    }

    public BufferedImage[][] getTiles() {
        return drawer.getTiles();
    }

    public void exportFile() {
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter("map.txt"));
            writer.write("1 0\n1 0");

            writer.close();
        }
        catch(Exception e) {
            e.printStackTrace();
        }
    }

    /** Enables periodic repaint calls. */
    public synchronized void start() {
        animate = true;
    }
    /** Pauses animation. */
    public synchronized void stop() {
        animate = false;
    }

    private synchronized boolean animationEnabled() {
        return animate;
    }

    @Override
    public void run() {
        while (true) {
            if (animationEnabled()) {
                repaint();
            }
            try {
                Thread.sleep(FRAME_DELAY);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }
}