package panel;

import java.util.ArrayList;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;

public class TileDrawer {
    // "not moving" tile map that basically is what will be exported
    // private ArrayList<GridBox> boxList;
    private ArrayList<ArrayList<GridBox>> boxList;

    private int colCount = 10;
    private int rowCount = 5;
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
        boxList = new ArrayList<ArrayList<GridBox>>();
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
        for (int row = 0; row < rowCount; row++) {
            boxList.add(new ArrayList<GridBox>());
            for (int col = 0; col < colCount; col++) {
                boxList.get(row).add(new GridBox(tiles[1][5], 26, drawX, drawY, tileSize));
                drawX += tileSize + SPACEBETWEENBOXES;
            }
            drawX = SPACEBETWEENBOXES;
            drawY += tileSize + SPACEBETWEENBOXES;
        }
    }

    public void addRightColumn() {
        // gets the x coordinate of the last box of the first row
        int lastX = tileSize + boxList.get(0).get(boxList.get(0).size() - 1).getX();
        drawX = lastX;

        // gets the y coordinate of the last box of the first row
        int lastY = boxList.get(0).get(boxList.get(0).size() - 1).getY();
        drawY = lastY;

        for (int row = 0; row < rowCount; row++) {
            boxList.get(row).add(new GridBox(tiles[1][5], 26, drawX, drawY, tileSize));
            drawY += tileSize + SPACEBETWEENBOXES;
        }
        drawY -= rowCount * tileSize;
        drawX += tileSize + SPACEBETWEENBOXES;

        colCount++;
    }

    public void removeRightColumn() {
        int initialSize = boxList.get(0).size();

        // so you don't remove a column when there's only one column left
        if (initialSize != 1) {
            for (int row = (boxList.size() - 1); row > -1; row--) { //runs from end of list to beginning (rowcount to 0)
                int lastCol = (boxList.get(0).size() - 1);
                boxList.get(row).remove(lastCol);
            }
            drawX -= tileSize + SPACEBETWEENBOXES;

            colCount--;
        }
    }

    public void updateTile(int selectedTile, int mouseX, int mouseY) {
        for (int row = 0; row < boxList.size(); row++) {
            for (int col = 0; col < boxList.get(row).size(); col++) {
                if (boxList.get(row).get(col).mouseOver(mouseX, mouseY)) {
                    int r = selectedTile / numTilesAcross;
                    int c = selectedTile % numTilesAcross;

                    boxList.get(row).get(col).setImage(tiles[r][c], selectedTile);
                    break;
                }
            }
        }
    }
    
    private void moveTiles(int xOffset, int yOffset) {
        for (int row = 0; row < boxList.size(); row++) {
            for (int col = 0; col < boxList.get(row).size(); col++) {
                boxList.get(row).get(col).move(xOffset, yOffset);
            }
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

    // public ArrayList<GridBox> getBoxList() {
    //     return boxList;
    // }

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
            for (int j = 0; j < boxList.get(i).size(); j++) {
                boxList.get(i).get(j).draw(surface);
            }
        }
    }

}