import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.Dimension;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.nio.file.Path;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JToolBar;
import javax.swing.filechooser.FileNameExtensionFilter;

import panel.FileHandler;
import panel.OptionsBar;
import panel.TileDrawer;
import panel.TileEditor;
import panel.Window;

public class Main {
    private static final Logger LOGGER = Logger.getLogger( Main.class.getName() );
    public static void main(String[] args) {
        // TODO: needs a button that allows the user to toggle between "zoom mode" and "tile select mode", and maybe hotkeys to toggle between the two modes
        System.out.println("maromaromaromaro");
        Window w = new Window();
        JPanel p = new JPanel(new BorderLayout());

        TileDrawer drawer = new TileDrawer();
        TileEditor editor = new TileEditor(drawer);
        p.add(editor, BorderLayout.CENTER);

        OptionsBar optionsBar = new OptionsBar(editor, new GridLayout(0, 2));
        optionsBar.setPreferredSize(new Dimension(140, 100));
        new FileHandler(drawer, optionsBar, w);

        p.add(optionsBar, BorderLayout.LINE_START);

        //setting up toolbar
        JToolBar bar = new JToolBar();
        bar.setFloatable(false);

        JButton fileButton = new JButton("File");  
        bar.add(fileButton);
        JButton editButton = new JButton("Edit");
        bar.add(editButton);
        JButton viewButton = new JButton("View");
        bar.add(viewButton);

        // creation of the file menu as well as the options for it
        JPopupMenu fileMenu = new JPopupMenu();

        JMenu setupMenu = new JMenu("Set up project");

        JMenuItem setDimensionsOption = new JMenuItem("Set dimensions");
        setDimensionsOption.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae) {
                editor.setDimensions();
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
        fileMenu.add(setupMenu);
        importMenu.add(importTileOption);
        importMenu.add(importBGOption);
        importMenu.add(importMapOption);
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

        // adding the toolbar to the panel
        p.add(bar, BorderLayout.PAGE_START);

        
        //adding the panel to the window
        w.add(p);
        w.setVisible(true);
        w.pack();

        editor.firstInit(editor.getWidth(), editor.getHeight());

        Thread editorThread = new Thread(editor);
        editorThread.start();

        w.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                editorThread.interrupt();
                System.exit(0); 
            }
            public void windowActivated(WindowEvent e) { editor.start(); }
            public void windowDeactivated(WindowEvent e) { editor.stop(); }
        });

    }
}
