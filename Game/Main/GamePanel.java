package Game.Main;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.event.*;
import java.awt.BorderLayout;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JToolBar;
import javax.swing.event.MouseInputListener;
import javax.swing.filechooser.FileNameExtensionFilter;

import Game.GameState.GameStateManger;
import Game.panel.OptionsBar;
import Game.panel.TileEditor;


public class GamePanel extends JPanel implements Runnable,KeyListener,MouseInputListener{

    public static final int width = 1200;
    public static final int height = 600;
    public static final int scale = 2;

    //game thread/ Frames per second stuff
    private Thread thread; 
    private boolean running;
    private int fps = 60;
    private long targetTime = 1000/fps;

    //tile editor
    private TileEditor editor = new TileEditor();
    private OptionsBar optionsBar = new OptionsBar(editor, editor.getTiles(), new GridLayout(0, 2));
    private JToolBar bar = new JToolBar();
    private JButton fileButton = new JButton("File");
    private JPopupMenu fileMenu = new JPopupMenu();
    private JButton editButton = new JButton("Edit");
    private boolean canSwith = true;

    //image
    private BufferedImage image;
    private Graphics2D g;

    //game state
    private GameStateManger gam;
    private Game game;


    public GamePanel(Game game){
        super(new BorderLayout());
        this.game = game;
        setPreferredSize(new Dimension(width, height));
        setFocusable(true);
        requestFocus();
    }

    public void addNotify() {
        super.addNotify();
        if(thread == null){
            thread = new Thread(this);
            addKeyListener(this);
            addMouseListener(this);
            addMouseMotionListener(this);
            thread.start();
        }
    }

    private void init(){
        image = new BufferedImage(width,height,BufferedImage.TYPE_INT_RGB);
        
        g = (Graphics2D) image.getGraphics();

        running = true;
        
        gam = new GameStateManger();

    }

    @Override
    public void run() {
        // TODO Auto-generated method stub
        init();

        long start;
        long elapsed;
        long wait;

        while(running){

            start = System.nanoTime();

            update();
            check();
            draw();
            drawToScreen();

            elapsed = System.nanoTime() - start;

            wait = targetTime - elapsed/1000000000;

            try{
                Thread.sleep(wait);
            }
            catch(Exception e){
                e.printStackTrace();
            }
        }
    }

    private void check(){

        if(gam.getCurrentState() == 3 && canSwith == true){
        }
        else if(gam.getCurrentState() !=3){
            canSwith = true;
            remove(bar);
            remove(fileMenu);
            remove(editor);
        }
    }

    private void drawToScreen() {
        
        if(gam.getCurrentState() !=3){
        Graphics g2 = getGraphics();
        g2.drawImage(image, 0, 0, width, height, null);
        g2.dispose();
        }

        if (gam.getCurrentState() == 3){
            repaint();
        }
    }

    private void draw() {

        if(gam.getCurrentState() !=3){
        paintComponent(g);
        gam.draw(g);
        }

        if (gam.getCurrentState() == 3 && canSwith == true){

        editor = new TileEditor();
        optionsBar = new OptionsBar(editor, editor.getTiles(), new GridLayout(0, 2));
        optionsBar.setPreferredSize(new Dimension(180, 100));

        bar = new JToolBar();
        fileButton = new JButton("File");
        fileMenu = new JPopupMenu();
        editButton = new JButton("Edit");

        add(editor, BorderLayout.CENTER);

        optionsBar.setPreferredSize(new Dimension(180, 100));
        add(optionsBar, BorderLayout.LINE_START);

        //setting up toolbar
        bar.setFloatable(false);
 
        bar.add(fileButton);
        bar.add(editButton);  
        bar.addSeparator();

        // creation of the file menu as well as the options for it

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

                    int returnVal = chooser.showSaveDialog(game);
                    if (returnVal == JFileChooser.APPROVE_OPTION) {
                        String filePath = chooser.getCurrentDirectory().getPath();
                        String fileName = chooser.getSelectedFile().getName();
                        editor.exportFile(filePath, fileName);
                        
                        JOptionPane.showMessageDialog(game, "File " + fileName + " sucessfully exported to " + filePath + " !");
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
        super.add(bar, BorderLayout.PAGE_START);

        paintComponent(g);
        //gam.draw(g);
        }

    }

    private void update() {
        gam.update();
    }

    @Override
    public void keyPressed(KeyEvent arg0) {
        // TODO Auto-generated method stub
        gam.keyPressed(arg0.getKeyCode());
    }

    @Override
    public void keyReleased(KeyEvent arg0) {
        // TODO Auto-generated method stub
        gam.keyReleased(arg0.getKeyCode());
    }

    @Override
    public void keyTyped(KeyEvent arg0) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void mouseClicked(MouseEvent arg0) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void mouseEntered(MouseEvent arg0) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void mouseExited(MouseEvent arg0) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void mousePressed(MouseEvent arg0) {
        // TODO Auto-generated method stub
        gam.mousePressed(arg0);
    }

    @Override
    public void mouseReleased(MouseEvent arg0) {
        // TODO Auto-generated method stub
    }

    @Override
    public void mouseDragged(MouseEvent arg0) {
        // TODO Auto-generated method stub
        gam.mouseDragged(arg0);
    }

    @Override
    public void mouseMoved(MouseEvent arg0) {
        // TODO Auto-generated method stub
        
    }
}
