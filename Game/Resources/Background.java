package Game.Resources;

import java.awt.image.*;
import javax.imageio.ImageIO;

import Game.Main.Game;
import Game.Main.GamePanel;

import java.awt.Graphics2D;

public class Background {

   private BufferedImage image;
   
   private double x;
   private double y;
   private double dx;
   private double dy;

   private double moveScale;

    public Background(String s, double ms){
        try{
            image = ImageIO.read(
                Background.class.getResource(s)
            );
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

    public void draw(Graphics2D g){
        g.drawImage(image, (int)x, (int) y, null);
        if(x < 0){
            g.drawImage(image, (int)x + GamePanel.width, (int) y, null);
        }

        if(x>0){
            g.drawImage(image, (int)x - GamePanel.width, (int) y, null);
        }
    }

}
