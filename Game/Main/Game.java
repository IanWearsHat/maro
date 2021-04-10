package Game.Main;

import javax.swing.JFrame;

public class Game extends JFrame{

    public Game(){
        setContentPane(new GamePanel(this));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        pack();
        setVisible(true);
    }
    
}
