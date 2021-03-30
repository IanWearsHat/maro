import java.awt.*;
import java.awt.event.*;
import java.io.File;
import javafx.stage.FileChooser;

import javax.swing.JButton;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JToolBar;

import panel.OptionsBar;
import panel.TileEditor;
import panel.TileDrawer;
import panel.Window;

public class Main {
    public static void main(String[] args) {
        System.out.println("maromaromaromaro");
        Window w = new Window();
        JPanel p = new JPanel(new BorderLayout());

        TileEditor editor = new TileEditor();
        p.add(editor, BorderLayout.CENTER);

        OptionsBar optionsBar = new OptionsBar(editor, editor.getTiles(), new GridLayout(0, 2));
        //                          new Dimension(length, height)
        optionsBar.setPreferredSize(new Dimension(180, 100));
        p.add(optionsBar, BorderLayout.LINE_START);

        //setting up toolbar
        JToolBar bar = new JToolBar();
        bar.setFloatable(false);

        JButton fileB = new JButton("File");  
        bar.add(fileB);
        JButton editB = new JButton("Edit");
        bar.add(editB);  
        bar.addSeparator();

        

        // creation of the file menu as well as the options for it
        JPopupMenu fileMenu = new JPopupMenu();

        JMenuItem importOption = new JMenuItem("Import map file");
        importOption.addActionListener( new ActionListener() {
            public void actionPerformed(ActionEvent ae) {
                System.out.println("importing");
            }
        });
        
        JMenuItem exportOption = new JMenuItem("Export map file");
        exportOption.addActionListener( new ActionListener() {
            public void actionPerformed(ActionEvent ae) {
                try {
                    FileChooser fileChooser = new FileChooser();
                    fileChooser.setTitle("Open Resource File");
                    fileChooser.getExtensionFilters().addAll(
                            new ExtensionFilter("Text Files", "*.txt"),
                            new ExtensionFilter("Image Files", "*.png", "*.jpg", "*.gif"),
                            new ExtensionFilter("Audio Files", "*.wav", "*.mp3", "*.aac"),
                            new ExtensionFilter("All Files", "*.*"));
                    File selectedFile = fileChooser.showOpenDialog(mainStage);
                    if (selectedFile != null) {
                       mainStage.display(selectedFile);
                    }
                }
                catch (Exception e) {
        
                }
                editor.exportFile("map");
                System.out.println("exporting");

            }
        });

        fileMenu.add(importOption);
        fileMenu.add(exportOption);

        //makes it so the file button drops down the menu for file
        fileB.addActionListener( new ActionListener() {
            public void actionPerformed(ActionEvent ae) {
                fileMenu.show(fileB, 0, fileB.getHeight());
            }
        });
        bar.add(fileMenu);

        // adding the toolbar to the panel
        p.add(bar, BorderLayout.PAGE_START);




        
        //adding the panel to the window
        w.add(p);
        w.setFocusable(true);
        w.pack();
        w.setVisible(true);

        w.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) { System.exit(0); }
            public void windowDeiconified(WindowEvent e) { editor.start(); }
            public void windowIconified(WindowEvent e) { editor.stop(); }
        });

        new Thread(editor).start();
    }
}