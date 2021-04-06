package panel;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

/**
 * This class represents a single grid box. 
 * <p>
 * One of these will draw only one image (tile) onto the screen with draw().
 * <p>
 * It holds an x and y position, the image (tile) it will draw, the image's tileIndex from the tileSheet, and the size of the tile.
 */
public class GridBox {
    private BufferedImage image;
    private int tileIndex;
    private int x;
    private int y;
    private int size;

    public GridBox(BufferedImage image, int tileIndex, int x, int y, int size) {
        this.image = image;
        this.tileIndex = tileIndex;
        this.x = x;
        this.y = y;
        this.size = size;
    }

    public void draw(Graphics surface) {
        surface.drawImage(image, x, y, size, size, null);
    }

    /*  Moves the box by a xOffset and a yOffset. Used with the movement key inputs wasd to simulate movement around an environment. */
    public void move(int xOffset, int yOffset) {
        x += xOffset;
        y += yOffset;
    }
    
    public void scale(int x, int y, int size) {
        this.x = x;
        this.y = y;
        this.size = size;
    }

    /**
     * Checks if the user's mouse is hovering over itself.
     * <p>
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

    public boolean inWindow() {
        if (x + size > 0 && x < TileEditor.length && y + size > 0 && y < TileEditor.height) {
            return true;
        }
        return false;
    }

    /**
     * Sets the box's image to image and the tileIndex to tileIndex.
     * <p>
     * If the tileIndex is 26 (default gridbox in the tilesheet), set this.tileIndex to 0 (air).  
     * @param image The tile to be rendered.
     * @param tileIndex The tileIndex that tile is.
     */
    public void setImage(BufferedImage image, int tileIndex) {
        this.image = image;
        this.tileIndex = tileIndex;
    }

    public int getTileIndex() {
        return tileIndex;
    }

    public int getX() {
        return x;
    }
    
    public int getY() {
        return y;
    }
}
