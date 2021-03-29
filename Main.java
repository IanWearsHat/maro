import java.awt.*;
import java.awt.event.*;

import javax.swing.JButton;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JToolBar;

public class Main {
    public static void main(String[] args) {
        System.out.println("maromaromaromaro");
        Window w = new Window();
        JPanel p = new JPanel(new BorderLayout());

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

        // setting up placeholder buttons, b1 represents options for tile selection, b2 represents the actual screen where you edit tiles
        JButton b1 = new JButton("b1");
        //                  new Dimension(length, height)
        b1.setPreferredSize(new Dimension(200, 100));
        JButton b2 = new JButton("b2");

        // adding the toolbar and placeholder buttons to the panel
        p.add(bar, BorderLayout.PAGE_START);

        p.add(b1, BorderLayout.LINE_START);
        p.add(b2, BorderLayout.CENTER);

        //adding the panel to the window
        w.add(p);
        w.pack();
        w.setVisible(true);
    }
}