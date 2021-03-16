
import javax.swing.JFrame;
import java.awt.Color;
import java.awt.Graphics;

public class Game extends JFrame{

    public Game(){
        setBounds(0,0, 1000,800);
        setVisible(true);
    }

    public void paint(Graphics graphics){
        super.paint(graphics);
        graphics.setColor(Color.red);
        graphics.fillOval(500, 400, 100, 50);
    }
}
