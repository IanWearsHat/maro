package panel;

import java.util.ArrayList;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
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

/**
 * The JPanel that holds everything about the tiles themselves. This means the tile drawing and user inputs are handled here.
 * This implements Runnable because it has Graphics. This means the framerate for the drawing is handled here.
 */
@SuppressWarnings("serial")
public class TileEditor extends JPanel implements Runnable {

    private final long FRAME_DELAY = 1000/60; //60 fps
    private boolean animate = true;
    private Font basic = new Font("TimesRoman", Font.PLAIN, 30);

    private int mouseX;
    private int mouseY;
    
    private TileDrawer drawer;
    private int selectedTile = 21;

    public TileEditor() {

        /*  This allows the user to paint the tile that their mouse is hovering over. 
            If the user left clicks, the selected tile (chosen by the options bar on the left) will be painted.
            If the user right clicks, air will be painted. */
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

        /*  This allows the user to drag their mouse across multiple tiles and paint them all. 
            If the user left click drags, the selected tile (chosen by the options bar on the left) will be painted.
            If the user right click drags, air will be painted. */
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

        /*  This allows the user to use wasd to move around the environment.
            This way of setting the booleans in TileDrawer was chosen so that movement is smooth.
            If keyReleased directly moves the x and y positions of the grid, the movement wouldn't be smooth. */
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

    /**
     * This method exports a .map file with a specified name to a specified directory.
     * This is called by the Export button in the dropdown menu for the File Button in the JToolBar at the top.
     * 
     * @param filePath The directory to export to.
     * @param fileName The name of the file.
     */
    public void exportFile(String filePath, String fileName) {
        try {
            /*  Creates a .map file with the fileName and writes the column count and row count, each on its own line. */
            BufferedWriter writer = new BufferedWriter(new FileWriter(fileName + ".map"));
            String toWrite = String.valueOf(drawer.getColCount()) + "\n" + String.valueOf(drawer.getRowCount()) + "\n";
            writer.write(toWrite);
            
            /*  This writes every single grid box's tile ID to the .map file. It goes row by row until it hits the last box. 
                It adds a space after every tile ID except at the end of a row. When it hits the end of a row, it adds a new line. */
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

            /*  Workaround for not being able to create the file directly in the directory. The file is created here in the working directory.
                This then moves the file from this directory to the intended specified directory. */
            Path p = Paths.get(filePath + File.separator + fileName + ".map");
            Path filePathDest = Paths.get(fileName + ".map");
            Files.move(filePathDest, p);
        }
        catch(Exception e) {
            e.printStackTrace();
        }
    }

    /*  Called by repaint() in the run method, meaning it's called every frame. */
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setFont(basic);

        drawer.draw(g2);
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
                /* Needed for the button inputs to work. 
                All button inputs work only on the "focused" component. 
                If you click on the options button, "focus" is now on the options button, not the TileEditor,
                so the buttons don't work for TileEditor.
                */
                requestFocusInWindow(); 
                repaint();
            }
            try {
                Thread.sleep(FRAME_DELAY);
            } catch (InterruptedException e) {
            }
        }
    }

    public void setSelectedTile(int tileIndex) {
        selectedTile = tileIndex;
    }

    public BufferedImage[][] getTiles() {
        return drawer.getTiles();
    }
}