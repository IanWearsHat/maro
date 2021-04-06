package panel;

import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.Box;
import javax.swing.KeyStroke;
import javax.swing.Action;
import javax.swing.AbstractAction;
import javax.swing.SwingUtilities;
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
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.image.BufferedImage;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * The JPanel that holds everything about the tiles themselves. This means the tile drawing and user inputs are handled here.
 * This implements Runnable because it has Graphics. This means the framerate for the drawing is handled here.
 */
@SuppressWarnings("serial")
public class TileEditor extends JPanel implements Runnable {
    // TODO: needs a way to display to the user info like the number of columns and rows, etc.
    private static final Logger LOGGER = Logger.getLogger( TileEditor.class.getName() );
    public static int length;
    public static int height;
    private final long FRAME_DELAY = 1000/60L; //60 fps
    private boolean animate = true;

    private int mouseX;
    private int mouseY;
    
    private TileDrawer drawer;
    private int selectedTile;
    
    private ArrayList<Integer> saveList;
    private int saveNumber;

    public TileEditor(TileDrawer drawer) {
        saveList = new ArrayList<Integer>();
        saveNumber = -1;
        selectedTile = 2;

        /*  This allows the user to paint the tile that their mouse is hovering over. 
            If the user left clicks, the selected tile (chosen by the options bar on the left) will be painted.
            If the user right clicks, air will be painted. */
        this.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                if (drawer.hasTiles()) {
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
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                if (drawer.hasTiles()) {
                    try {
                        saveNumber++;
                        if (saveList.size() == 25) {
                            Path path = Paths.get(String.valueOf(saveList.get(0)) + ".map");
                            Files.delete(path);
                            saveList.remove(0);
                        }
                        FileHandler.createMapFile(String.valueOf(saveNumber)); // for the control z feature.
                        saveList.add(saveNumber);
                        
                    }
                    catch (Exception exception) {}
                }
            }
        });

        Action undo = new AbstractAction() {
            public void actionPerformed(ActionEvent e) {
                if (saveNumber != saveList.get(0)) {
                    Path path = Paths.get(String.valueOf(saveNumber - 1) + ".map");
                    FileHandler.importMap(path);
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
                    FileHandler.importMap(path);
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
                if (drawer.hasTiles()) {
                    mouseX = e.getX();
                    mouseY = e.getY();

                    if (SwingUtilities.isLeftMouseButton(e)) {
                        drawer.updateTile(selectedTile, mouseX, mouseY);
                    }
                    else if (SwingUtilities.isRightMouseButton(e)) {
                        drawer.updateTile(0, mouseX, mouseY);
                    }
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
                    case 'r':
                        LOGGER.log(Level.INFO, "painting");
                        repaint();
                        break;
                }
            }
        });

        this.drawer = drawer;
    }

    public void firstInit(int length, int height) { // https://stackoverflow.com/questions/6555040/multiple-input-in-joptionpane-showinputdialog/6555051
        TileEditor.length = length;
        TileEditor.height = height;
        
        JTextField colField = new JTextField(4);
        JTextField rowField = new JTextField(4);
        JTextField tileSizeField = new JTextField(4);
        JButton importTilesButton = new JButton("Import tile sheet");
        JButton importBGButton = new JButton("Import background");

        importTilesButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae) {
                FileHandler.importTileSet();
            }
        });

        importBGButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae) {
                FileHandler.importBG();
            }
        });

        JPanel p = new JPanel();
        p.add(new JLabel("# of columns in editor (max 2000):"));
        p.add(colField);
        p.add(Box.createHorizontalStrut(15)); // a spacer
        p.add(new JLabel("# of rows in editor (max 10):"));
        p.add(rowField);
        p.add(Box.createHorizontalStrut(15));
        p.add(new JLabel("Pixel dimensions of tile: "));
        p.add(tileSizeField);
        p.add(Box.createHorizontalStrut(15));
        p.add(importTilesButton);
        p.add(Box.createHorizontalStrut(15));
        p.add(importBGButton);

        int colCount = 10;
        int rowCount = 10;
        int tileSize = 30;
        boolean run = true;
        do {
            int result = JOptionPane.showConfirmDialog(null, p, "Welcome", JOptionPane.OK_CANCEL_OPTION);
            if (result == JOptionPane.OK_OPTION) {
                try {
                    colCount = Integer.parseInt(colField.getText());
                    rowCount = Integer.parseInt(rowField.getText());
                    tileSize = Integer.parseInt(tileSizeField.getText());
                    if (colCount <= 2000 && rowCount <= 10) {
                        run = false;
                        drawer.setDimensions(colCount, rowCount, tileSize);
                    }
                }
                catch (Exception e) {
                    JOptionPane.showMessageDialog(null, "Please enter a valid number.", "ERROR", JOptionPane.ERROR_MESSAGE);
                }
            }
            else {
                run = false;
            }
        } while (run);
    }

    public void setDimensions() {
        JTextField colField = new JTextField(4);
        JTextField rowField = new JTextField(4);
        JTextField tileSizeField = new JTextField(4);

        JPanel p = new JPanel();
        p.add(new JLabel("# of columns in editor (max 2000):"));
        p.add(colField);
        p.add(Box.createHorizontalStrut(15)); // a spacer
        p.add(new JLabel("# of rows in editor (max 10):"));
        p.add(rowField);
        p.add(Box.createHorizontalStrut(15));
        p.add(new JLabel("Pixel dimensions of tile: "));
        p.add(tileSizeField);

        int colCount = 10;
        int rowCount = 10;
        int tileSize = 30;
        boolean run = true;
        do {
            int result = JOptionPane.showConfirmDialog(null, p, "Set number of columns and rows", JOptionPane.OK_CANCEL_OPTION);
            if (result == JOptionPane.OK_OPTION) {
                try {
                    colCount = Integer.parseInt(colField.getText());
                    rowCount = Integer.parseInt(rowField.getText());
                    tileSize = Integer.parseInt(tileSizeField.getText());
                    if (colCount <= 2000 && rowCount <= 10) {
                        run = false;
                        drawer.setDimensions(colCount, rowCount, tileSize);
                    }
                }
                catch (Exception e) {
                    JOptionPane.showMessageDialog(null, "Please enter a valid number.", "ERROR", JOptionPane.ERROR_MESSAGE);
                }
            }
            else {
                run = false;
            }
        } while (run);
    }

    /*  Called by repaint() in the run method, meaning it's called every frame. */
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

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

    public int getTileCount() {
        return drawer.getTileCount();
    }
}
