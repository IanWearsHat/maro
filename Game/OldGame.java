package Game;

import java.awt.Canvas;
import javax.swing.JFrame;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import javax.imageio.ImageIO;

public class OldGame extends JFrame implements Runnable {

    private Canvas canvas = new Canvas();
    private RenderHandler renderer;
    private BufferedImage testImage;

    public OldGame() {

        // Make our program shutdown when we exit out
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Set's the position and size of the frame.
        setBounds(0, 0, 1500, 750);

        // Puts our frame in the center of the screen.
        setLocationRelativeTo(null);

        // Add our graphics componet
        add(canvas);

        // Makes the frame visable to the user.
        setVisible(true);

        // Creates our object for buffer strategy. We now have the ablity to add 3
        // buffers to buffer stretegy.
        canvas.createBufferStrategy(3);

        renderer = new RenderHandler(getWidth(), getHeight());

        testImage = loadImage("Jason's Secret Stuff" +"\\"+ "BestGirl3.png");


    }

    public void update() {
    }

    private BufferedImage loadImage(String path) {
        try {
            BufferedImage loadedImage = ImageIO.read(OldGame.class.getResource(path));
            BufferedImage formattedImage = new BufferedImage(loadedImage.getWidth(), loadedImage.getHeight(),BufferedImage.TYPE_INT_RGB);
            formattedImage.getGraphics().drawImage(loadedImage,0,0,null);
            return formattedImage;
        } catch (IOException e) {
            // TODO Auto-generated catch block
            System.out.println("hi");
            e.printStackTrace();
            return null;
        }
        
    }

    public void render(){
            BufferStrategy bufferStrategy = canvas.getBufferStrategy();
            //Get's current buffer 
            bufferStrategy = canvas.getBufferStrategy();
            //Get's graphics from buffer
            Graphics graphics = bufferStrategy.getDrawGraphics();
            super.paint(graphics);

            renderer.renderImage(testImage, 0, 0);
            renderer.render(graphics);

            graphics.dispose();
            bufferStrategy.show();
    }

    @Override
    public void run() {
        BufferStrategy bufferStrategy = canvas.getBufferStrategy();
        int i = 0;

        long lastTime = System.nanoTime();
        double nanoSecondsCoversion = 1000000000/60;//60 frames per second.
        double changeInSeconds = 0;
        while(true){
            long now = System.nanoTime();

            //Frames per second
            changeInSeconds += (now-lastTime) / nanoSecondsCoversion;


            while(changeInSeconds >= 1){
                update();
                changeInSeconds = 0;
            }

            render();
            lastTime = now;
        }

    }

    

    // public void paint(Graphics graphics){
        // super.paint(graphics);
        // graphics.setColor(Color.red);
        // graphics.fillOval(500, 400, 100, 50);
    // }


}
