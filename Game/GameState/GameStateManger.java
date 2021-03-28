package Game.GameState;

import java.util.ArrayList;

import Game.Main.GamePanel;

public class GameStateManger {
    
    private ArrayList<GameState> gameStates;
    private int currentState;

    public static final int MENUSTATE = 0;
    public static final int LEVELMENUSTATE = 1;
    public static final int LEVEL1STATE = 2;
    


    public GameStateManger(){
        gameStates = new ArrayList<GameState>();

        currentState = MENUSTATE;
        gameStates.add(new MenuState(this));
        gameStates.add(new LevelMenuState(this));
        gameStates.add(new Level1State(this));


    }

    public void setState(int state){
        currentState = state;
        gameStates.get(currentState);
    }

    public void update(){
        gameStates.get(currentState).update();
    }

    public void draw(java.awt.Graphics2D g){
        gameStates.get(currentState).draw(g);
    }

    public void keyPressed(int k){
        gameStates.get(currentState).keyPressed(k);
    }

    public void keyReleased(int k){
        gameStates.get(currentState).keyReleased(k);
    }

}
