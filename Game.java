import java.awt.Canvas;
import javax.swing.JFrame;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class Game extends JFrame{

    private Canvas canvas = new Canvas();

    public Game(){

        //Make our program shutdown when we exit out
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //Set's the position and size of the frame.
        setBounds(0,0, 1000,800);
        
        //Puts our frame in the center of the screen.
        setLocationRelativeTo(null);

        //Add our graphics componet
        add(canvas);

        //Makes the frame visable to the user.
        setVisible(true);

        //Creates our object for buffer strategy. We now have the ablity to add 3 buffers to buffer stretegy.
        canvas.createBufferStrategy(3);
        BufferStrategy bufferStrategy = canvas.getBufferStrategy();

        int i = 0;
        int x= 50;

        while(true){
            i++;
            if (i ==10){
                i = 0;
                x++;
            }
            if (x == 500){
                x = 0;
            }
            //Get's current buffer 
            bufferStrategy = canvas.getBufferStrategy();
            //Get's graphics from buffer
            Graphics graphics = bufferStrategy.getDrawGraphics();

            super.paint(graphics);

            //setbackground color
            graphics.setColor(Color.CYAN);
            graphics.fillRect(0, 0, getWidth(), getHeight());

            //Painting the oval
            graphics.setColor(Color.red);
            graphics.fillOval(x, 400, 100, 50);

            graphics.dispose();
            bufferStrategy.show();
        }

    }

    

    // public void paint(Graphics graphics){
        // super.paint(graphics);
        // graphics.setColor(Color.red);
        // graphics.fillOval(500, 400, 100, 50);
    // }


}
