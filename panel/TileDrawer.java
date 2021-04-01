package panel;

import java.util.ArrayList;
import java.util.Collections;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;

public class TileDrawer {
    // TODO: colCount, rowCount, and tileSize need to be inputted from the user
    // We need to be able to scale the entire thing too
    // There also needs to be a way to add a row above and below and remove a row above and below

    // "not moving" tile map that basically is what will be exported
    private ArrayList<ArrayList<GridBox>> boxList;

    private int colCount = 3;
    private int rowCount = 2;
    private int tileSize = 75;
    
    public boolean moveUp = false;
    public boolean moveDown = false;
    public boolean moveLeft = false;
    public boolean moveRight = false;
    private int moveSpeed = 15;

    private int drawX = 0;
    private int drawY = 0;

    private BufferedImage tileSheet;
    private String tileSheetPath = "resources" + "\\" + "resized.gif";
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
                drawX += tileSize;
            }
            drawX = 0;
            drawY += tileSize;
        }
    }

    private void printMap() {
        for (int row = 0; row < rowCount; row++) {
            for (int col = 0; col < colCount; col++) {
                System.out.print(String.valueOf(boxList.get(row).get(col).getTileIndex()));
                if (col + 1 < colCount) { System.out.print(" "); }

            }
            if (row + 1 < rowCount) { System.out.println("\n"); }
        }
        System.out.println("\n");
    }

    /*  "Creates" a left column() by making a right column first, then moving it to the left of the first column
        Then, to update the data structure of the arraylist of arraylists, we use Collections.rotate() to move everything up by one. */
    public void addLeftColumn() {
        addRightColumn();
        for (int row = 0; row < rowCount; row++) {
            boxList.get(row).get(colCount - 1).move(-tileSize * colCount, 0);
            Collections.rotate(boxList.get(row), 1); //https://stackoverflow.com/questions/4938626/moving-items-around-in-an-arraylist 
        }
    }

    public void removeLeftColumn() {
        int initialSize = boxList.get(0).size();
        // so you don't remove a column when there's only one column left
        if (initialSize != 1) {
            for (int row = 0; row < rowCount; row++) {
                boxList.get(row).remove(0);
            }
            drawX -= tileSize;

            colCount--;
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
            drawY += tileSize;
        }
        drawY -= rowCount * tileSize;
        drawX += tileSize;

        colCount++;
    }

    public void removeRightColumn() {
        int initialSize = boxList.get(0).size();
        // so you don't remove a column when there's only one column left
        if (initialSize != 1) {
            int lastCol = (boxList.get(0).size() - 1);
            for (int row = 0; row < rowCount; row++) {
                boxList.get(row).remove(lastCol);
            }
            drawX -= tileSize;

            colCount--;
        }
    }

    public void addTopRow() {
        addBottomRow();
        for (int row = boxList.size() - 1; row > 0; row--) {
            Collections.copy(boxList.get(row), boxList.get(row-1));
        }

        int firstX = boxList.get(0).get(0).getX();
        drawX = firstX;

        int firstY = -tileSize + boxList.get(0).get(0).getY();
        drawY = firstY;
        for (int col = 0; col < colCount; col++) {
            boxList.get(0).set(col, new GridBox(tiles[1][5], 26, drawX, drawY, tileSize));
            drawX += tileSize;
        }
    }

    public void removeTopRow() {
        boxList.remove(0);
        rowCount--;
    }

    public void addBottomRow() {
        int firstX = boxList.get(0).get(0).getX();
        drawX = firstX;

        int firstY = tileSize + boxList.get((boxList.size() - 1)).get(0).getY();
        drawY = firstY;

        ArrayList<GridBox> newLine = new ArrayList<GridBox>();
        for (int col = 0; col < colCount; col++) {
            newLine.add(new GridBox(tiles[1][5], 26, drawX, drawY, tileSize));
            drawX += tileSize;
        }
        boxList.add(newLine);

        rowCount++;
    }

    public void removeBottomRow() {
        int initialSize = boxList.size();
        if (initialSize != 1) {
            boxList.remove(boxList.size() - 1);
            rowCount--;
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

    public ArrayList<ArrayList<GridBox>> getBoxList() {
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

        for (int row = 0; row < boxList.size(); row++) {
            for (int col = 0; col < boxList.get(row).size(); col++) {
                boxList.get(row).get(col).draw(surface);
            }
        }
    }

}