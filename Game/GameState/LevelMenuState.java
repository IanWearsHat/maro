package Game.GameState;

import java.awt.Graphics2D;
import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.*;
import javax.swing.Timer;

import Game.Main.Game;
import Game.Resources.Background;
import Game.GameState.*;

public class LevelMenuState extends GameState implements ActionListener{

    private Background bg;

    private GameStateManger gsm;
    private int currentChoiceRow = 0;
    private int currentChoiceCol = 0;
    private Color titleColor;
    private Font titleFont;
    private Color levelNameColor;
    private int w = 0;
    private float alpha = 1f;
    private Font font;
    private Timer fadeOut= new Timer(20, this);

    private String[][] levelOptions  = {
        {"Level 1","Level 2", "Level 3"},
        {"Level 4","Level 5", "Level 6"},
        {"Exit"}

    }
    ;


    public LevelMenuState(GameStateManger gsm){
        this.gsm = gsm;

        try{
            bg = new Background("Jason's Secret Stuff" +"\\"+ "BestGirl7.png", 1);
            //bg.setVector(-1, 0);

            titleColor = new Color(225,117, 87);
            levelNameColor = new Color(225,117,87);
            titleFont = new Font("Times New Roman",Font.PLAIN, 60);

            font = new Font("Arial", Font.PLAIN, 48);


        }
        catch(Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void init() {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void update() {
        // TODO Auto-generated method stub
        bg.update();
    }

    @Override
    public void draw(Graphics2D g) {
        g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alpha));
        bg.setComposite(alpha);
        // TODO Auto-generated method stub
        bg.draw(g);
        

        g.setFont(font);
        for(int i = 0; i < levelOptions.length; i++){
            for(int j = 0; j < levelOptions[i].length; j++){
                if(i != 2){
                    g.setColor(Color.WHITE);
                    g.fillRect(125 + (j*300), 100 + (i*200), 200,100);
                }
                if(i == currentChoiceRow && j == currentChoiceCol){
                    if(i != 2){
                    g.setColor(Color.CYAN);
                    g.drawRect(125 + (j*300), 100 + (i*200), 200,100);
                    }
                    g.setColor(Color.BLACK);
                }
                
                else{
                    g.setColor(Color.RED);
                }
                g.drawString(levelOptions[i][j], 145 + (j*300), 140 + (i * 200));
            }
        }
    }

    private void select(){
        if (currentChoiceCol == 0 && currentChoiceRow == 0){
            gsm.setState(GameStateManger.LEVEL1STATE);
        }
        if (currentChoiceCol == 1 && currentChoiceRow == 0){
            gsm.setState(GameStateManger.LEVEL1STATE);
        }
        if (currentChoiceCol == 2 && currentChoiceRow == 0){
            gsm.setState(GameStateManger.LEVEL1STATE);
        }
        if (currentChoiceCol == 0 && currentChoiceRow == 1){
            gsm.setState(GameStateManger.LEVEL1STATE);
        }
        if (currentChoiceCol == 1 && currentChoiceRow == 1){
            gsm.setState(GameStateManger.LEVEL1STATE);
        }
        if (currentChoiceCol == 2 && currentChoiceRow == 1){
            gsm.setState(GameStateManger.LEVEL1STATE);
        }
        if(currentChoiceCol == 0 && currentChoiceRow == 2){
            currentChoiceCol = 0;
            currentChoiceRow = 0;
            w = 0;
            fadeOut.start();
            //gsm.setState(GameStateManger.MENUSTATE);
        }
    }

    @Override
    public void keyPressed(int k) {
        // TODO Auto-generated method stub
        System.out.println(w);

        if(k == KeyEvent.VK_ENTER){
            select();
        }
        if(k == KeyEvent.VK_UP){
            currentChoiceRow--;
            if (currentChoiceRow == -1){
                currentChoiceRow = levelOptions.length-1;
                currentChoiceCol = 0;
            }
        }
        if(k == KeyEvent.VK_DOWN){
            currentChoiceRow++;
            if (currentChoiceRow == levelOptions.length){
                currentChoiceRow = 0;
            }
            if(currentChoiceRow == 2){
                currentChoiceCol = 0;
            }
        }
        if(k == KeyEvent.VK_LEFT){
            if(currentChoiceRow == 2){
                w += 1;
            }
            currentChoiceCol--;
            if (currentChoiceCol == -1){
                currentChoiceCol = levelOptions[currentChoiceRow].length-1;
            }
        }
        if(k == KeyEvent.VK_RIGHT){
            if(currentChoiceRow == 2){
                w += 1;
            }
            currentChoiceCol++;
            if (currentChoiceCol >= levelOptions[currentChoiceRow].length){
                currentChoiceCol = 0;
            }
        }
    }

    @Override
    public void keyReleased(int k) {
        // TODO Auto-generated method stub
        
    }
    
    public void actionPerformed(ActionEvent e) {
        alpha += -0.01f;
        if (alpha <= 0) {
          alpha = 0;
          fadeOut.stop();
          alpha = 1f;
          gsm.setState(GameStateManger.MENUSTATE);
        }
      }
    
}
