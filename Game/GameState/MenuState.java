package Game.GameState;

import java.awt.Graphics2D;

import java.awt.event.*;

import Game.Resources.*;
import java.awt.Color;
import java.awt.Font;

public class MenuState extends GameState{
    
    public Background bg;
    private GameStateManger gsm;
    
    private int currentChoice = 0;

    //options for menu
    private String[] options = {
        "Start",
        "Help",
        "Quit"
    };

    private Color titleColor;
    private Font titleFont;

    private Font font;

    public MenuState(GameStateManger gsm){
        this.gsm = gsm;

        try{
            bg = new Background("Jason's Secret Stuff" +"\\"+ "BestGirl7.png", 1);
            //bg.setVector(-1, 0);

            titleColor = new Color(225,117, 87);
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
        // TODO Auto-generated method stub
        //draw bg
        bg.draw(g);

        g.setColor(titleColor);
        g.setFont(titleFont);
        g.drawString("MAROMARO", 80,70);

        g.setFont(font);
        for(int i = 0; i < options.length; i++){
            if(i == currentChoice){
                g.setColor(Color.BLACK);
            }
            else{
                g.setColor(Color.RED);
            }
            g.drawString(options[i], 145, 140 + (i * 55));
        }
    }

    private void select(){
        if(currentChoice == 0){
           // 
            gsm.setState(GameStateManger.LEVELMENUSTATE);
        }
        if(currentChoice == 1){
            // 
        }
         if(currentChoice == 2){
             //Quits
            System.exit(0);
        }
    }

    @Override
    public void keyPressed(int k) {
        // TODO Auto-generated method stub
        if(k == KeyEvent.VK_ENTER){
            select();
        }
        if(k == KeyEvent.VK_UP){
            currentChoice--;
            if (currentChoice == -1){
                currentChoice = options.length-1;
            }
        }
        if(k == KeyEvent.VK_DOWN){
            currentChoice++;
            if (currentChoice == options.length){
                currentChoice = 0;
            }
        }
    }

    @Override
    public void keyReleased(int k) {
        // TODO Auto-generated method stub
        
    }

}
