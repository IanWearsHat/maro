package Game.panel;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

public class GridBox {
    private BufferedImage image;
    private int tileIndex;
    private int x;
    private int y;
    private int size;
    private int boxID;

    public GridBox(BufferedImage image, int tileIndex, int x, int y, int size, int boxID) {
        this.image = image;
        this.tileIndex = (tileIndex == 26) ? 0 : tileIndex;
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

    public void move(int xOffset, int yOffset) {
        x += xOffset;
        y += yOffset;
    }

    public void setImage(BufferedImage image, int tileIndex) {
        this.image = image;
        this.tileIndex = (tileIndex == 26) ? 0 : tileIndex;
    }

    public int getTileIndex() {
        return tileIndex;
    }

    public void draw(Graphics surface) {
        surface.drawImage(image, x, y, null);
        // surface.setColor(Color.RED);
        // surface.fillRect(x, y, size, size);
    }
}