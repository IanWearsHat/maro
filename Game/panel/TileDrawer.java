package Game.panel;

import java.util.ArrayList;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;

public class TileDrawer {
    // "not moving" tile map that basically is what will be exported
    private ArrayList<GridBox> boxList;

    private int colCount = 10;
    private int rowCount = 10;
    private int boxID = 0;
    private final int SPACEBETWEENBOXES = 0;
    private int tileSize = 75;
    
    public boolean moveUp = false;
    public boolean moveDown = false;
    public boolean moveLeft = false;
    public boolean moveRight = false;
    private int moveSpeed = 15;

    private int drawX = SPACEBETWEENBOXES;
    private int drawY = SPACEBETWEENBOXES;

    private BufferedImage tileSheet;

    String tileSheetPath = "resources" + "\\" + "resized.gif";

    private BufferedImage[][] tiles;
    private int numTilesAcross;


    public TileDrawer() {
        boxList = new ArrayList<GridBox>();
    }

    public void loadTiles() {
        try {
            tileSheet = ImageIO.read(TileDrawer.class.getResource(tileSheetPath));
            numTilesAcross = tileSheet.getWidth() / tileSize;
            tiles = new BufferedImage[2][numTilesAcross];

            BufferedImage subimage;
            for(int col = 0; col < numTilesAcross; col++){
                subimage = tileSheet.getSubimage(
                    col * tileSize, 
                    0, 
                    tileSize, 
                    tileSize);
                    tiles[0][col] = subimage;
                    subimage = tileSheet.getSubimage(
                        col*tileSize, 
                        tileSize, 
                        tileSize, 
                        tileSize);
                    tiles[1][col] = subimage;
            }

        }
        catch(Exception e) {
            e.printStackTrace();
        }
    }

    public void initializeGrid() {
        for (int i = 0; i < colCount; i++) {
            for (int j = 0; j < rowCount; j++) {
                boxList.add(new GridBox(tiles[1][5], 26, drawX, drawY, tileSize, boxID));
                boxID++;
                drawY += tileSize + SPACEBETWEENBOXES;
            }
            drawY = SPACEBETWEENBOXES;
            drawX += tileSize + SPACEBETWEENBOXES;
        }
    }

    public void addColumn() {
        for (int i = 0; i < rowCount; i++) {
            boxList.add(new GridBox(tiles[1][5], 26, drawX, drawY, tileSize, boxID));
            boxID++;
            drawY += tileSize + SPACEBETWEENBOXES;
        }
        drawY -= rowCount * tileSize;
        drawX += tileSize + SPACEBETWEENBOXES;

        colCount++;
    }

    public void removeColumn() {
        int initialSize = boxList.size();
        if (initialSize != rowCount) {
            for (int i = (boxList.size() - 1); i > (initialSize - 1) - rowCount; i--) {
                boxList.remove(i);
                boxID--;
            }
            drawX -= tileSize + SPACEBETWEENBOXES;

            colCount--;
        }
    }

    public void updateTile(int selectedTile, int mouseX, int mouseY) {
        for (int i = 0; i < boxList.size(); i++) {
            if (boxList.get(i).mouseOver(mouseX, mouseY)) {
                int r = selectedTile / numTilesAcross;
                int c = selectedTile % numTilesAcross;

                boxList.get(i).setImage(tiles[r][c], selectedTile);
                break;
            }
        }
    }
    
    private void moveTiles(int xOffset, int yOffset) {
        for (int i = 0; i < boxList.size(); i++) {
            boxList.get(i).move(xOffset, yOffset);
        }
    }

    public int getRowCount() {
        return rowCount;
    }

    public int getColCount() {
        return colCount;
    }

    public BufferedImage[][] getTiles() {
        return tiles;
    }

    public ArrayList<GridBox> getBoxList() {
        return boxList;
    }

    public void draw(Graphics surface) {
        if (moveUp) { 
            moveTiles(0, 1 * moveSpeed);
            drawY += 1 * moveSpeed;
        }
    
        if (moveDown) {
            moveTiles(0, -1 * moveSpeed);
            drawY += -1 * moveSpeed;
        }
    
        if (moveLeft) {
            moveTiles(1 * moveSpeed, 0);
            drawX += 1 * moveSpeed;
        }

        if (moveRight) {
            moveTiles(-1 * moveSpeed, 0);
            drawX += -1 * moveSpeed;
        }

        for (int i = 0; i < boxList.size(); i++) {
            boxList.get(i).draw(surface);
        }
    }

}