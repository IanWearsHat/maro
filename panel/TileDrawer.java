package panel;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;

import java.awt.*;
import java.awt.image.*;
import javax.imageio.ImageIO;

import java.util.ArrayList;

public class TileDrawer {
    // "not moving" tile map that basically is what will be exported
    private ArrayList<ArrayList<Integer>> tileMap;
    private ArrayList<GridBox> boxList;

    private int colCount;
    private int rowCount;
    private int boxID = 0;
    private final int SPACEBETWEENBOXES = 2;
    private final int TILESIZE = 75;

    // private int TILESIZE = 75;
    private BufferedImage[][] tiles;
    private int numTilesAcross = 21;

    private int x = SPACEBETWEENBOXES;
    private int y = SPACEBETWEENBOXES;

    private int xOffset;
    private int yOffset;

    private BufferedImage tileSheet;

    String tileSheetPath = "resources" + "\\" + "resized.gif";
    
    public TileDrawer() {
        tileMap = new ArrayList<ArrayList<Integer>>();
        boxList = new ArrayList<GridBox>();

        colCount = 10;
        rowCount = 10;

        loadTiles();
        initializeGrid();
    }

    private void initializeGrid() {
        for (int i = 0; i < colCount; i++) {
            for (int j = 0; j < rowCount; j++) {
                boxList.add(new GridBox(tiles[1][2], x, y, TILESIZE, boxID));
                boxID++;
                y += TILESIZE + SPACEBETWEENBOXES;
            }
            y = SPACEBETWEENBOXES;
            x += TILESIZE + SPACEBETWEENBOXES;
        }
    }

    public void loadTiles() {
        try {
            tileSheet = ImageIO.read(TileDrawer.class.getResource(tileSheetPath));
            System.out.println(tileSheet.getWidth());
            numTilesAcross = tileSheet.getWidth() / TILESIZE;
            tiles = new BufferedImage[2][numTilesAcross];

            BufferedImage subimage;
            for(int col = 0; col < numTilesAcross; col++){
                subimage = tileSheet.getSubimage(
                    col * TILESIZE, 
                    0, 
                    TILESIZE, 
                    TILESIZE);
                    tiles[0][col] = subimage;
                    subimage = tileSheet.getSubimage(
                        col*TILESIZE, 
                        TILESIZE, 
                        TILESIZE, 
                        TILESIZE);
                    tiles[1][col] = subimage;
            }

        }
        catch(Exception e) {
            e.printStackTrace();
        }
    }

    public void addColumn() {
        for (int i = 0; i < rowCount; i++) {
            boxList.add(new GridBox(tiles[1][1], x, y, TILESIZE, boxID));
            boxID++;
            y += TILESIZE + SPACEBETWEENBOXES;
        }
        y = SPACEBETWEENBOXES;
        x += TILESIZE + SPACEBETWEENBOXES;

        colCount++;
    }

    public void removeColumn() {
        int initialSize = boxList.size();
        for (int i = (boxList.size() - 1); i > (initialSize - 1) - rowCount; i--) {
            boxList.remove(i);
            boxID--;
        }
        x -= TILESIZE + SPACEBETWEENBOXES;

        colCount--;
    }

    public void draw(Graphics surface) {
        for (int i = 0; i < boxList.size(); i++) {
            boxList.get(i).draw(surface);
        }
    }

}