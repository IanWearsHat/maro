package Game.GameState;

import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.event.ActionListener;
import java.awt.event.ComponentListener;
import java.awt.event.ComponentEvent;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.security.Key;
import java.awt.RenderingHints;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.BorderLayout;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JToolBar;
import javax.swing.SwingUtilities;
import javax.swing.filechooser.FileNameExtensionFilter;

import Game.Main.Game;
import Game.Resources.TileMap;
import Game.panel.FileHandler;
import Game.panel.OptionsBar;
import Game.panel.TileDrawer;
import Game.panel.TileEditor;

public class TileMapState extends GameState{

    private int mouseX;
    private int mouseY;
    private TileDrawer drawer;
    private int selectedTile;
    private GameStateManger gs;
    private JPanel gp;
    private OptionsBar optionsBar;
    private TileEditor tileEditor;
    private JToolBar toolBar;
    private Font basic = new Font("TimesRoman", Font.PLAIN, 30);
    private boolean didInit = false;


    public TileMapState(GameStateManger gs, JPanel gp){

        this.gs = gs;
        this.gp = gp;

        init();

        didInit = true;

    }

    @Override
    public void init() {
        JPanel mainPanel = new JPanel(new BorderLayout());

        drawer = new TileDrawer();
        tileEditor = new TileEditor(drawer);
        optionsBar = new OptionsBar(tileEditor, new GridLayout(0, 2));
        toolBar = createToolBar();

        mainPanel.add(tileEditor, BorderLayout.CENTER);
        mainPanel.add(optionsBar, BorderLayout.LINE_START);
        mainPanel.add(toolBar, BorderLayout.PAGE_START);

        gp.add(mainPanel);

        tileEditor.setPanelDimensions(tileEditor.getWidth(), tileEditor.getHeight());
        tileEditor.firstInit();

        gp.addComponentListener(new ComponentListener() {
            @Override
            public void componentResized(ComponentEvent e) {
                tileEditor.setPanelDimensions(tileEditor.getWidth(), tileEditor.getHeight());
            }

            @Override
            public void componentHidden(ComponentEvent e) {}

            @Override
            public void componentMoved(ComponentEvent e) {}

            @Override
            public void componentShown(ComponentEvent e) {}
        });
        
    }

    private JToolBar createToolBar() {
        JToolBar bar = new JToolBar();

        JButton fileButton = new JButton("File");  
        bar.add(fileButton);
        JButton editButton = new JButton("Edit");
        bar.add(editButton);
        JButton viewButton = new JButton("View");
        bar.add(viewButton);
        JButton zoomButton = new JButton("Zoom");
        bar.add(zoomButton);
        JButton leaveButton = new JButton("Exit");
        bar.add(leaveButton);

        // creation of the file menu as well as the options for it
        JPopupMenu fileMenu = new JPopupMenu();

        JMenu setupMenu = new JMenu("Set up project");
        JMenuItem setDimensionsOption = new JMenuItem("Set dimensions");
        setDimensionsOption.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae) {
                tileEditor.setDimensionsOption();
            }
        });

        JMenu importMenu = new JMenu("Import");
        JMenuItem importMapOption = new JMenuItem("Import map file");
        importMapOption.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae) {
                FileHandler.importMapFromDirectory();
            }
        });

        JMenuItem importTileOption = new JMenuItem("Import tile sheet");
        importTileOption.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae) {
                FileHandler.importTileSet();
            }
        });

        JMenuItem importBGOption = new JMenuItem("Import background");
        importTileOption.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae) {
                FileHandler.importBG();
            }
        });
        
        JMenuItem exportOption = new JMenuItem("Export map file");
        exportOption.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae) {
                FileHandler.exportFile();
            }
        });

        setupMenu.add(setDimensionsOption);
        importMenu.add(importTileOption);
        importMenu.add(importBGOption);
        importMenu.add(importMapOption);

        fileMenu.add(setupMenu);
        fileMenu.add(importMenu);
        fileMenu.add(exportOption);

        // makes it so the file button drops down the menu for file
        fileButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae) {
                fileMenu.show(fileButton, 0, fileButton.getHeight());
            }
        });

        // creation of edit menu and all things under edit
        JPopupMenu editMenu = new JPopupMenu();

        JMenuItem resetMapOption = new JMenuItem("Reset tile map");
        resetMapOption.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae) {
                drawer.reset();
            }
        });

        editMenu.add(resetMapOption);

        editButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae) {
                editMenu.show(editButton, 0, editButton.getHeight());
            }
        });

        zoomButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae) {
                tileEditor.zoomState = !tileEditor.zoomState;
            }
        });

        leaveButton.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent arg0) {
                // TODO Auto-generated method stub
                gp.remove(bar);
                gp.remove(optionsBar);
                gp.remove(tileEditor);
                gs.setState(GameStateManger.MENUSTATE);
                didInit = false;
            }
            
        });

        bar.setFloatable(false);
        return bar;
    }

    @Override
    public void update() {
        // TODO Auto-generated method stub
        if(didInit == true){

        }
        if(didInit == false){
            init();

            didInit = true;
        }
    }

    @Override
    public void draw(Graphics2D g) {
        gp.paintComponents(g);
        Graphics2D g2 = g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setFont(basic);
    
        drawer.draw(g2);
        // TODO Auto-generated method stub
        
    }

    @Override
    public void keyPressed(int k) {
        // TODO Auto-generated method stub
        switch(k) {
            case KeyEvent.VK_UP:
                drawer.moveUp = true;
                System.out.println("GAYYYYYY");
                break;
            case KeyEvent.VK_DOWN:
                drawer.moveDown = true;
                break;
            case KeyEvent.VK_LEFT:
                drawer.moveLeft = true;
                break;
            case KeyEvent.VK_R:
                drawer.moveRight = true;
                break;
        }
    }

    @Override
    public void keyReleased(int k) {
        // TODO Auto-generated method stub
        switch(k) {
            case KeyEvent.VK_UP:
                drawer.moveUp = false;
                break;
            case KeyEvent.VK_DOWN:
                drawer.moveDown = false;
                break;
            case KeyEvent.VK_LEFT:
                drawer.moveLeft = false;
                break;
            case KeyEvent.VK_R:
                drawer.moveRight = false;
                break;
        }
        
    }

    @Override
    public void mousePressed(MouseEvent e) {
        // TODO Auto-generated method stub
         mouseX = e.getX();
         mouseY = e.getY();

                if (SwingUtilities.isLeftMouseButton(e)) {
                    drawer.updateTile(selectedTile, mouseX, mouseY);
                    System.out.println("pressed");
                }
                else if (SwingUtilities.isRightMouseButton(e)) {
                    drawer.updateTile(0, mouseX, mouseY);
                }
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        // TODO Auto-generated method stub
        mouseX = e.getX();
        mouseY = e.getY();
        
        if (SwingUtilities.isLeftMouseButton(e)) {
            drawer.updateTile(selectedTile, mouseX, mouseY);
            System.out.println("Drag");
        }
        else if (SwingUtilities.isRightMouseButton(e)) {
            drawer.updateTile(0, mouseX, mouseY);
        }
    }

}