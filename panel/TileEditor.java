package panel;

import javax.swing.JPanel;
import java.util.ArrayList;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D; 
import java.awt.RenderingHints;
import java.awt.event.MouseAdapter;
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

    private final int FRAME_DELAY = 50; // 50 ms = 20 FPS
    private Font basic = new Font("TimesRoman", Font.PLAIN, 30);

    private boolean animate = true;
    private int mouseX;
    private int mouseY;
    
    public TileDrawer drawer;
    private int selectedTile = 21;

    public TileEditor() {
        /*  */
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
            }

            @Override
            public void mouseReleased(MouseEvent e) {}
        });

        addKeyListener(new KeyListener() {
            public void keyTyped(KeyEvent e) {}
            public void keyReleased(KeyEvent e) {}
            public void keyPressed(KeyEvent e) { 
                switch(e.getKeyChar()) {
                    case 'a':
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
            ArrayList<GridBox> boxList = drawer.getBoxList();
            int boxI;
            for (int i = 0, rowCount = drawer.getRowCount(); i < rowCount; i++) {
                boxI = i;
                for (int j = 0, colCount = drawer.getColCount(); j < colCount; j++) {
                    String toWrite = String.valueOf(boxList.get(boxI).getTileIndex());
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