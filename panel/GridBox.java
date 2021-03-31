package panel;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

/**
 * This class represents a single grid box. One of these will draw only one image (tile) onto the screen with draw().
 * It holds an x and y position, the image (tile) it will draw, the image's tileIndex from the tileSheet, and the size of the tile.
 */
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

    public void draw(Graphics surface) {
        surface.drawImage(image, x, y, null);
    }

    /*  Moves the box by a xOffset and a yOffset. Used with the movement key inputs wasd to simulate movement around an environment. */
    public void move(int xOffset, int yOffset) {
        x += xOffset;
        y += yOffset;
    }

    /**
     * Checks if the user's mouse is hovering over itself.
     * In TileDrawer, if this returns true, then the box that returns true will change its image
     * to whatever tile the user selected.
     * 
     * @param mouseX X position of the mouse
     * @param mouseY Y position of the mouse
     * @return true if mouse over, false if not
     */
    public boolean mouseOver(int mouseX, int mouseY) {
        if (mouseX <= (x + size) && mouseX >= x && mouseY <= (y + size) && mouseY >= y) {
            return true;
        }
        return false;
    }

    /*  Sets the box's image to image and the tileIndex to tileIndex.
        If the tileIndex is 26 (default gridbox in the tilesheet), set this.tileIndex to 0 (air).  */
    public void setImage(BufferedImage image, int tileIndex) {
        this.image = image;
        this.tileIndex = (tileIndex == 26) ? 0 : tileIndex;
    }

    public int getTileIndex() {
        return tileIndex;
    }
}