package panel;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;


public class GridBox {
    private BufferedImage image;
    private int x;
    private int y;
    private int size;
    private int boxID;

    public GridBox(BufferedImage image, int x, int y, int size, int boxID) {
        this.image = image;
        this.x = x;
        this.y = y;
        this.size = size;
        this.boxID = boxID;
    }

    public boolean mouseOver(int mouseX, int mouseY) {
        if (mouseX <= (x + size) && mouseX >= x && mouseY <= (y + size) && mouseY >= y) {
            return true;
        }
        return false;
    }

    public void setImage(BufferedImage image) {
        this.image = image;
    }

    public void draw(Graphics surface) {
        surface.drawImage(image, x, y, null);
        // surface.setColor(Color.RED);
        // surface.fillRect(x, y, size, size);
    }
}