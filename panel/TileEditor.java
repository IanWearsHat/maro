package panel;

import java.util.ArrayList;

import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.KeyStroke;
import javax.swing.Action;
import javax.swing.AbstractAction;
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
import java.awt.event.MouseWheelListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.ActionEvent;
import java.awt.image.BufferedImage;
import java.io.BufferedWriter;
import java.io.BufferedReader;
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
    
    private ArrayList<Integer> saveList;
    private int saveNumber = -1;

    public TileEditor() {
        saveList = new ArrayList<Integer>();

        /*  This allows the user to paint the tile that their mouse is hovering over. 
            If the user left clicks, the selected tile (chosen by the options bar on the left) will be painted.
            If the user right clicks, air will be painted. */
        this.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                if (saveList.size() > 0) {
                    int indexDiff = saveList.get(saveList.size() - 1) - saveNumber;
                    int endValue = saveList.get(saveList.size() - 1 - indexDiff);

                    while (saveList.get(saveList.size() - 1) != endValue) {
                        try {
                            Path path = Paths.get(String.valueOf(saveList.get(saveList.size() - 1)) + ".map");
                            Files.delete(path);
                            saveList.remove(saveList.size() - 1);
                        }
                        catch (Exception exception) {}
                    }
                }

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
            public void mouseReleased(MouseEvent e) {
                try {
                    saveNumber++;
                    if (saveList.size() == 25) {
                        Path path = Paths.get(String.valueOf(saveList.get(0)) + ".map");
                        Files.delete(path);
                        saveList.remove(0);
                    }
                    createFile(String.valueOf(saveNumber)); // for the control z feature.
                    saveList.add(saveNumber);
                    
                }
                catch (Exception exception) {}
            }
        });

        Action undo = new AbstractAction() {
            public void actionPerformed(ActionEvent e) {
                if (saveNumber != saveList.get(0)) {
                    Path path = Paths.get(String.valueOf(saveNumber - 1) + ".map");
                    importFile(path);
                    saveNumber--;
                }
            }
        };
        this.getInputMap().put(KeyStroke.getKeyStroke(90, java.awt.event.InputEvent.CTRL_DOWN_MASK), "undo");
        this.getActionMap().put("undo", undo);

        Action redo = new AbstractAction() {
            public void actionPerformed(ActionEvent e) {
                if (saveNumber != saveList.get(saveList.size() - 1)) {
                    Path path = Paths.get(String.valueOf(saveNumber + 1) + ".map");
                    importFile(path);
                    saveNumber++;
                }
            }
        };
        this.getInputMap().put(KeyStroke.getKeyStroke(89, java.awt.event.InputEvent.CTRL_DOWN_MASK), "redo");
        this.getActionMap().put("redo", redo);

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

        this.addMouseWheelListener(new MouseWheelListener() {
            public void mouseWheelMoved(MouseWheelEvent e) {
                int moveInt = e.getWheelRotation();
                if (moveInt < 0) {
                    // scale up
                    drawer.scale(1);
                    
                }
                else if (moveInt > 0) {
                    // scale down
                    drawer.scale(0);
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
                    case 'n':
                        drawer.addTopRow();
                        break;
                    case 'm':
                        drawer.removeTopRow();
                        break;
                    case 'u':
                        drawer.addBottomRow();
                        break;
                    case 'i':
                        drawer.removeBottomRow();
                        break;
                    case 'h':
                        drawer.removeLeftColumn();
                        break;
                    case 'j':
                        drawer.addLeftColumn();
                        break;
                    case 'k':
                        drawer.addRightColumn();
                        break;
                    case 'l':
                        drawer.removeRightColumn();
                        break;
                }
            }
        });

        drawer = new TileDrawer();
        drawer.loadTiles();
        drawer.initializeGrid();
    }

    public void importFile(Path path) {
        try {
            BufferedReader br = Files.newBufferedReader(path);
            int colCount = Integer.parseInt(br.readLine());
            int rowCount = Integer.parseInt(br.readLine());
            int[][] map = new int[rowCount][colCount];

            String delims = "\\s+";

            for (int row = 0; row < rowCount; row++){
                String line = br.readLine();
                String[] tokens = line.split(delims);
                for(int col = 0; col < colCount; col++){
                    map[row][col] = Integer.parseInt(tokens[col]);
                }
            }

            drawer.importFile(colCount, rowCount, map);
        }
        catch(Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * This method exports a .map file with a specified name to a specified directory.
     * <p>
     * This is called by the Export button in the dropdown menu for the File Button in the JToolBar at the top.
     * <p>
     * If the file already exists in the directory, the user will be asked if they want to overwrite it or not.
     * <p>
     * If they overwrite it, the file getes deleted and the new file gets created. Else, it'll just stop running.
     * 
     * @param filePath The directory to export to.
     * @param fileName The name of the file.
     */
    public void exportFile(String filePath, String fileName) {
        try {
            Path inputPath = Paths.get(filePath + File.separator + fileName + ".map");
            if (Files.exists(inputPath)) {
                int option = JOptionPane.showConfirmDialog(
                    this, "File already exists. Would you like to overwrite it?", "Overwrite", JOptionPane.YES_NO_OPTION);

                if (option == JOptionPane.YES_OPTION) { Files.delete(inputPath); }
                else { throw new Exception("stop"); }
            }
            createFile(fileName);

            /*  Workaround for not being able to create the file directly in the directory. The file is created here in the working directory.
                This then moves the file from this directory to the intended specified directory. */
            
            Path filePathDest = Paths.get(fileName + ".map");
            Files.move(filePathDest, inputPath);
            JOptionPane.showMessageDialog(this, "File " + fileName + " sucessfully exported to " + filePath + " !");
        }
        catch(Exception e) {}
    }

    private void createFile(String fileName) {
        try {
            File file = new File(fileName + ".map");
            file.deleteOnExit();

            BufferedWriter writer = new BufferedWriter(new FileWriter(file));
            String toWrite = String.valueOf(drawer.getColCount()) + "\n" + String.valueOf(drawer.getRowCount()) + "\n";
            writer.write(toWrite);
            
            /*  This writes every single grid box's tile ID to the .map file. It goes row by row until it hits the last box. 
                It adds a space after every tile ID except at the end of a row. When it hits the end of a row, it adds a new line. */
            ArrayList<ArrayList<GridBox>> boxList = drawer.getBoxList();
            for (int row = 0, rowCount = drawer.getRowCount(); row < rowCount; row++) {
                for (int col = 0, colCount = drawer.getColCount(); col < colCount; col++) {
                    toWrite = String.valueOf(boxList.get(row).get(col).getTileIndex());
                    if (col + 1 < colCount) { toWrite += " "; }
                    writer.write(toWrite);
                }
                if (row + 1 < rowCount) { writer.write("\n"); }
            }
            writer.close();
        }
        catch (Exception e) {

        }
    }

    public void removeSaves() {

    }

    public void reset() {
        drawer.initializeGrid();
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