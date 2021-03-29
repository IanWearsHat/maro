package panel;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;

public class TileDrawer {
    
    private int[][] tileMap;
    
    public TileDrawer() {

    }

    public void draw(Graphics surface) {
        surface.setColor(Color.RED);
        surface.fillRect(50, 50, 50, 50);
        surface.setColor(Color.BLACK);
        ((Graphics2D) surface).setStroke(new BasicStroke(2.0f));
        surface.drawRect(50, 50, 50, 50);
    }

}
