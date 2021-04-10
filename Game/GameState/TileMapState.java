package Game.GameState;

import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.security.Key;
import java.awt.RenderingHints;
import java.awt.Font;

import javax.swing.SwingUtilities;

import Game.Main.Game;
import Game.Resources.TileMap;
import Game.panel.TileDrawer;

public class TileMapState extends GameState{

    private int mouseX;
    private int mouseY;
    private TileDrawer drawer;
    private int selectedTile;
    private GameStateManger gs;
    private Font basic = new Font("TimesRoman", Font.PLAIN, 30);


    public TileMapState(GameStateManger gs){
        this.gs = gs;
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