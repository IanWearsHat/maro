package Game.GameState;

import java.awt.Color;
import java.awt.Graphics2D;

import Game.Main.GamePanel;

public class Level1State extends GameState{

    private GameStateManger gsm;

    public Level1State(GameStateManger gsm){
        this.gsm = gsm;
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
        // TODO Auto-generated method stub
        g.setColor(Color.WHITE);
        g.fillRect(0, 0, GamePanel.width, GamePanel.height);
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
