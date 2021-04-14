package Game.Main;

import java.awt.BorderLayout;
import javax.swing.JFrame;

public class Game extends JFrame{

    public Game(){

        setContentPane(new GamePanel(this));
        setLayout(new BorderLayout());
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        setVisible(true);
        pack();
    }
    
}