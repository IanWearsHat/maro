
import javax.swing.JFrame;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

@SuppressWarnings("serial")
public class Game extends JFrame{
    boolean move = false;
    public Game(){
        setBounds(0,0, 1000,800);
        setVisible(true);

        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) { 
                System.out.println("Closing"); 
                System.exit(0);  
            }
            public void windowDeiconified(WindowEvent e) {}
            public void windowIconified(WindowEvent e) {}
        });

        addKeyListener(new KeyAdapter() {
            int key;
            public void keyPressed(KeyEvent e) {
                key = e.getKeyCode();
                switch(key) {
                    case KeyEvent.VK_Z:
                        // System.out.print("Z");
                        move = true;
                        
                        break;
                    case KeyEvent.VK_SPACE:
                        // System.out.print(" ");
                        break;
                }
            }
            public void keyReleased(KeyEvent e) {
                key = e.getKeyCode();
                switch(key) {
                    case KeyEvent.VK_Z:
                        // System.out.print("Z");
                        move = false;
                        break;
                    case KeyEvent.VK_SPACE:
                        // System.out.print(" ");
                        break;
                }
            }
        });

        while (true) {
            System.out.print("");
            while (move) {
                System.out.println("Z");
            }
        }
    }

    public void paint(Graphics graphics){
        super.paint(graphics);
        graphics.setColor(Color.red);
        graphics.fillOval(500, 400, 100, 50);

        
    }
}
