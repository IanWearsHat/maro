package Game.panel;

import javax.swing.JPanel;
import javax.swing.KeyStroke;
import javax.swing.Action;
import javax.swing.AbstractAction;
import javax.swing.SwingUtilities;
import javax.swing.event.MouseInputListener;

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
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

// this should handle whatever is in the center of the screen, or the tileEditor, or where b2 currently is in Main.java

@SuppressWarnings("serial")
public class TileEditor extends JPanel implements Runnable {

    private final int FRAME_DELAY = 1000/60; //60 fps
    private Font basic = new Font("TimesRoman", Font.PLAIN, 30);

    private boolean animate = true;
    private int mouseX;
    private int mouseY;
    
    public TileDrawer drawer;
    private int selectedTile = 21;

    public TileEditor() {
        /*  */

        this.addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {
                mouseX = e.getX();
                mouseY = e.getY();

                if (SwingUtilities.isLeftMouseButton(e)) {
                    drawer.updateTile(selectedTile, mouseX, mouseY);
                }
                else if (SwingUtilities.isRightMouseButton(e)) {
                    drawer.updateTile(0, mouseX, mouseY);
                }

            }
        });

        this.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                mouseX = e.getX();
                mouseY = e.getY();

                if (SwingUtilities.isLeftMouseButton(e)) {
                    drawer.updateTile(selectedTile, mouseX, mouseY);
                }
                else if (SwingUtilities.isRightMouseButton(e)) {
                    drawer.updateTile(0, mouseX, mouseY);
                }
            }

            @Override
            public void mouseReleased(MouseEvent e) {}
        });

        this.addKeyListener(new KeyListener() {
            public void keyTyped(KeyEvent e) {}
            public void keyReleased(KeyEvent e) {
                switch(e.getKeyChar()) {
                    case 'w':
                        drawer.moveUp = false;
                        break;
                    case 's':
                        drawer.moveDown = false;
                        break;
                    case 'a':
                        drawer.moveLeft = false;
                        break;
                    case 'd':
                        drawer.moveRight = false;
                        break;
                }
            }

            public void keyPressed(KeyEvent e) { 
                switch(e.getKeyChar()) {
                    case 'w':
                        drawer.moveUp = true;
                        break;
                    case 's':
                        drawer.moveDown = true;
                        break;
                    case 'a':
                        drawer.moveLeft = true;
                        break;
                    case 'd':
                        drawer.moveRight = true;
                        break;
                    case 'k':
                        drawer.addColumn();
                        break;
                    case 'l':
                        drawer.removeColumn();
                        break;
                }
            }
        });

        drawer = new TileDrawer();
        drawer.loadTiles();
        drawer.initializeGrid();
    }

    public void exportFile(String filePath, String fileName) {
        try {
            
            BufferedWriter writer = new BufferedWriter(new FileWriter(fileName + ".map"));
            String toWrite = String.valueOf(drawer.getColCount()) + "\n" + String.valueOf(drawer.getRowCount()) + "\n";
            writer.write(toWrite);
            
            ArrayList<GridBox> boxList = drawer.getBoxList();
            int boxI;
            for (int i = 0, rowCount = drawer.getRowCount(); i < rowCount; i++) {
                boxI = i;
                for (int j = 0, colCount = drawer.getColCount(); j < colCount; j++) {
                    toWrite = String.valueOf(boxList.get(boxI).getTileIndex());
                    if (j + 1 < colCount) { toWrite += " "; }
                    writer.write(toWrite);
                    boxI += rowCount;
                }
                if (i + 1 < rowCount) { writer.write("\n"); }
            }
            writer.close();

            Path p = Paths.get(filePath + File.separator + fileName + ".map");
            Path filePathDest = Paths.get(fileName + ".map");
            Files.move(filePathDest, p);
        }
        catch(Exception e) {
            e.printStackTrace();
        }
    }

    public void setSelectedTile(int tileIndex) {
        selectedTile = tileIndex;
    }

    public BufferedImage[][] getTiles() {
        return drawer.getTiles();
    }
    
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setFont(basic);

        drawer.draw(g2);
        requestFocusInWindow();
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