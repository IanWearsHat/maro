package panel;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;

import java.util.ArrayList;

public class TileDrawer {
    // "not moving" tile map that basically is what will be exported
    private ArrayList<ArrayList<Integer>> tileMap;
    private ArrayList<GridBox> boxList;


    private int colCount;
    private int rowCount;
    private int boxID = 0;
    private final int SPACEBETWEENBOXES = 2;
    private final int BOXSIZE = 50;

    private int x = SPACEBETWEENBOXES;
    private int y = SPACEBETWEENBOXES;

    private int yOffset;
    private int xOffset;
    
    public TileDrawer() {
        tileMap = new ArrayList<ArrayList<Integer>>();
        boxList = new ArrayList<GridBox>();

        colCount = 10;
        rowCount = 10;

        drawGrid();
    }

    private void drawGrid() {
        for (int i = 0; i < colCount; i++) {
            for (int j = 0; j < rowCount; j++) {
                boxList.add(new GridBox(x, y, BOXSIZE, boxID));
                boxID++;
                y += BOXSIZE + SPACEBETWEENBOXES;
            }
            y = SPACEBETWEENBOXES;
            x += BOXSIZE + SPACEBETWEENBOXES;
        }
    }

    public void addColumn() {
        for (int i = 0; i < rowCount; i++) {
            boxList.add(new GridBox(x, y, BOXSIZE, boxID));
            boxID++;
            y += BOXSIZE + SPACEBETWEENBOXES;
        }
        y = SPACEBETWEENBOXES;
        x += BOXSIZE + SPACEBETWEENBOXES;

        colCount++;
    }

    public void removeColumn() {
        int initialSize = boxList.size();
        for (int i = (boxList.size() - 1); i > (initialSize - 1) - rowCount; i--) {
            boxList.remove(i);
            boxID--;
        }
        x -= BOXSIZE + SPACEBETWEENBOXES;
        
        colCount--;
    }

    public void draw(Graphics surface) {
        for (int i = 0; i < boxList.size(); i++) {
            boxList.get(i).draw(surface);
        }
    }

}