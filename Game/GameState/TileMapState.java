package Game.GameState;

import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.event.ActionListener;
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
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JToolBar;
import javax.swing.SwingUtilities;
import javax.swing.filechooser.FileNameExtensionFilter;

import Game.Main.Game;
import Game.Resources.TileMap;
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
    private Font basic = new Font("TimesRoman", Font.PLAIN, 30);


    public TileMapState(GameStateManger gs, JPanel gp){

        this.gs = gs;
        this.gp = gp;

        TileEditor editor = new TileEditor();
        gp.add(editor, BorderLayout.CENTER);

        OptionsBar optionsBar = new OptionsBar(editor, editor.getTiles(), new GridLayout(0, 2));
        //                          new Dimension(length, height)
        optionsBar.setPreferredSize(new Dimension(180, 100));
        gp.add(optionsBar, BorderLayout.LINE_START);

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

                    int returnVal = chooser.showSaveDialog(gp);
                    if (returnVal == JFileChooser.APPROVE_OPTION) {
                        String filePath = chooser.getCurrentDirectory().getPath();
                        String fileName = chooser.getSelectedFile().getName();
                        editor.exportFile(filePath, fileName);
                        
                        JOptionPane.showMessageDialog(gp, "File " + fileName + " sucessfully exported to " + filePath + " !");
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
        gp.add(bar, BorderLayout.PAGE_START);

        drawer = new TileDrawer();
        drawer.loadTiles();
        drawer.initializeGrid();
    }

    @Override
    public void init() {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void update() {
        // TODO Auto-generated method stub
        
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