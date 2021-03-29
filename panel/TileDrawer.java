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
    private final int SPACEBETWEENBOXES = 2;
    private final int BOXSIZE = 50;
    
    public TileDrawer() {
        tileMap = new ArrayList<ArrayList<Integer>>();
        boxList = new ArrayList<GridBox>();

        colCount = (TileEditor.WIDTH / BOXSIZE);
        rowCount = 15;
        setupGrid();
    }

    private void setupGrid() {
        int boxI = 0;
        int x = SPACEBETWEENBOXES;
        int y = SPACEBETWEENBOXES;
        for (int i = 0; i < colCount; i++) {
            for (int j = 0; j < rowCount; j++) {
                boxList.add(new GridBox(x, y, BOXSIZE, boxI));
                boxI++;
                y += BOXSIZE + SPACEBETWEENBOXES;
            }
            y = SPACEBETWEENBOXES;
            x += BOXSIZE + SPACEBETWEENBOXES;
        }
    }

    private void addColumn() {

    }

    public void draw(Graphics surface) {
        for (int i = 0; i < boxList.size(); i++) {
            boxList.get(i).draw(surface);
        }
    }

}
