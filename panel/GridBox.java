package panel;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;


public class GridBox {
    private int x;
    private int y;
    private int size;
    private int boxNumber;

    public GridBox(int x, int y, int size, int boxNumber) {
        this.x = x;
        this.y = y;
        this.size = size;
        this.boxNumber = boxNumber;
    }

    public boolean checkMouseOver() {
        return false;
    }

    public void draw(Graphics surface) {
        surface.setColor(Color.RED);
        surface.fillRect(x, y, size, size);
    }
}
