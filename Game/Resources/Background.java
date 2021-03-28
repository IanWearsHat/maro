package Game.Resources;

import java.awt.image.*;
import javax.imageio.ImageIO;

import Game.Main.Game;
import Game.Main.GamePanel;
import java.awt.AlphaComposite;

import java.awt.Graphics2D;
import java.awt.RenderingHints;

public class Background {

   private BufferedImage image;
   
   private double x;
   private double y;
   private double dx;
   private double dy;
   private float alpha = 1f;

   private double moveScale;

    public Background(String s, double ms){
        try{
            image = ImageIO.read(
                Background.class.getResource(s)
            );
            moveScale = ms;
            System.out.println(image.getWidth());
            System.out.println(image.getHeight());
        }
        catch(Exception e){
           e.printStackTrace(); 
        }
    }

    public void setPosition(double x, double y){
        this.x = x * moveScale % GamePanel.width;
        this.y = y * moveScale % GamePanel.height;
    }

    public void setVector(double dx, double dy){
        this.dx = dx;
        this.dy = dy;
    }

    public void update(){
        x+=dx;
        y+=dy;
    }

    public void setComposite(float alpha){
        this.alpha = alpha;
    }

    public void draw(Graphics2D g){
        
        RenderingHints hints = new RenderingHints(RenderingHints.KEY_RENDERING,
        RenderingHints.VALUE_RENDER_QUALITY);
        hints.put(RenderingHints.KEY_ANTIALIASING,
        RenderingHints.VALUE_ANTIALIAS_ON);

        g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alpha));
        g.drawImage(image, (int)x, (int) y, null);
        g.setRenderingHints(hints);

        if(x < 0){
            g.drawImage(image, (int)x + GamePanel.width, (int) y, null);
        }

        if(x>0){
            g.drawImage(image, (int)x - GamePanel.width, (int) y, null);
        }
    }

}
