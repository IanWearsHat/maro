package Game.GameState;

import java.awt.Color;
import java.awt.Graphics2D;

import Game.Main.GamePanel;
import Game.Resources.TileMap;

public class Level1State extends GameState{

    private GameStateManger gsm;
    private TileMap tileMap;

    public Level1State(GameStateManger gsm){
        this.gsm = gsm;
        init();
    }

    @Override
    public void init() {
        // TODO Auto-generated method stub
        tileMap = new TileMap(75);
        tileMap.loadTiles("Resources" +"\\"+ "TileSet" + "\\" + "grasstileset4.gif");
        tileMap.loadMap("Resources" +"\\"+ "Map" + "\\" + "level1-1.map");
        tileMap.setPosition(0, 0);
    }

    @Override
    public void update() {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void draw(Graphics2D g) {
        // TODO Auto-generated method stub

        //clear screen
        g.setColor(Color.BLUE);
        g.fillRect(0, 0, GamePanel.width, GamePanel.height);

        //draw tile map
        tileMap.draw(g);


    }

    @Override
    public void keyPressed(int k) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void keyReleased(int k) {
        // TODO Auto-generated method stub
        
    }
    
}
