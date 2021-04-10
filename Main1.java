import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.Dimension;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JToolBar;
import javax.swing.filechooser.FileNameExtensionFilter;

import Game.Main.Game;
import Game.panel.OptionsBar;
import Game.panel.TileEditor;
import Game.panel.Window;

public class Main1 {
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

        JButton fileButton = new JButton("File");  
        bar.add(fileButton);
        JButton editButton = new JButton("Edit");
        bar.add(editButton);  
        bar.addSeparator();

        

        // creation of the file menu as well as the options for it
        JPopupMenu fileMenu = new JPopupMenu();

        JMenuItem importOption = new JMenuItem("Import map file");
        importOption.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae) {
                System.out.println("importing");
            }
        });
        
        JMenuItem exportOption = new JMenuItem("Export map file");
        exportOption.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae) {
                try {
                    JFileChooser chooser = new JFileChooser();
                    FileNameExtensionFilter filter = new FileNameExtensionFilter("Map file (*.map)", "map");
                    chooser.setFileFilter(filter);

                    int returnVal = chooser.showSaveDialog(w);
                    if (returnVal == JFileChooser.APPROVE_OPTION) {
                        String filePath = chooser.getCurrentDirectory().getPath();
                        String fileName = chooser.getSelectedFile().getName();
                        editor.exportFile(filePath, fileName);
                        
                        JOptionPane.showMessageDialog(w, "File " + fileName + " sucessfully exported to " + filePath + " !");
                    }
                }
                catch (Exception e) {
        
                }

            }
        });

        fileMenu.add(importOption);
        fileMenu.add(exportOption);

        //makes it so the file button drops down the menu for file
        fileButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae) {
                fileMenu.show(fileButton, 0, fileButton.getHeight());
            }
        });
        bar.add(fileMenu);

        // adding the toolbar to the panel
        p.add(bar, BorderLayout.PAGE_START);

        w.setFocusable(true);
        w.pack();
        w.setVisible(true);
        
        //adding the panel to the window
        w.add(p);
        w.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) { System.exit(0); }
            public void windowDeiconified(WindowEvent e) { editor.start(); }
            public void windowIconified(WindowEvent e) { editor.stop(); }
        });

        new Thread(editor).start();
    }
}