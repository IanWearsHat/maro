package Game.panel;

import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JToolBar;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.ComponentListener;
import java.awt.event.ComponentEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.BorderLayout;

@SuppressWarnings("serial")
public class Window extends JFrame {
    private static final Logger LOGGER = Logger.getLogger(Window.class.getName());
    public static final int WIDTH = 1280;
    public static final int HEIGHT = 720;

    private JPanel mainPanel;

    public static TileDrawer tileDrawer;
    public static TileEditor tileEditor;
    public static OptionsBar optionsBar;
    public static JToolBar toolbar;

    public Window() {
        mainPanel = new JPanel(new BorderLayout());
        tileDrawer = new TileDrawer();
        tileEditor = new TileEditor(tileDrawer);
        optionsBar = new OptionsBar(tileEditor, new GridLayout(0, 2));
        toolbar = createToolBar();

        mainPanel.add(tileEditor, BorderLayout.CENTER);
        mainPanel.add(optionsBar, BorderLayout.LINE_START);
        mainPanel.add(toolbar, BorderLayout.PAGE_START);

        add(mainPanel);
        setPreferredSize(new Dimension(WIDTH, HEIGHT));
        setFocusable(true);
        requestFocus();
        setVisible(true);
        pack();

        tileEditor.setPanelDimensions(tileEditor.getWidth(), tileEditor.getHeight());
        tileEditor.firstInit();
        Thread editorThread = new Thread(tileEditor);
        editorThread.start();

        addComponentListener(new ComponentListener() {
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

        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                editorThread.interrupt();
                System.exit(0); 
            }
            public void windowActivated(WindowEvent e) { tileEditor.start(); }
            public void windowDeactivated(WindowEvent e) { tileEditor.stop(); }
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
                tileDrawer.reset();
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

        bar.setFloatable(false);
        return bar;
    }
}