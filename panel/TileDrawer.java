package panel;

import java.util.ArrayList;
import java.util.Collections;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;

public class TileDrawer {
    // TODO: colCount, rowCount, and tileSize need to be inputted from the user
    // needs to be able to handle a user-imported tilesheet, with user-imported backgrounds, with a default gridbox as its own tile in resources
    // integrate with the game itself as well as sockets

    //  you have the client send their .map file to the server through file streams and whatnot and the server saves that .map file
    //  then when another client wants to see the "marketplace", it asks the server for a list of all the .map files that have been submitted to the server
    //  the server responds with the list, 
    //  the client sends a request for say, "levelpoggywoggy.map", and the server sends over "levelpoggywoggy.map" over a socket stream
    //  the client then loads up levelpoggywoggy.map
    //  some maps could be locked behind a password

    // "not moving" tile map that basically is what will be exported
    private ArrayList<ArrayList<GridBox>> boxList;

    private int colCount = 10;
    private int rowCount = 10;
    private double scale = 1;
    private int tileSize = 30;
    private int tileSizeScaled = (int) (tileSize * scale);
    
    public boolean moveUp = false;
    public boolean moveDown = false;
    public boolean moveLeft = false;
    public boolean moveRight = false;
    private int moveSpeed = 15;

    private int drawX = 0;
    private int drawY = 0;

    private BufferedImage[][] tiles;
    private int numTilesAcross;
    private BufferedImage tileSheet;
    private String tileSheetPath = "resources" + "\\" + "grasstileset.gif";
    private BufferedImage defaultBox;
    private String defaultBoxPath = "resources" + "\\" + "defaultBox.gif";

    public TileDrawer() {}

    public void loadTiles() {
        try {
            defaultBox = ImageIO.read(TileDrawer.class.getResource(defaultBoxPath));
            tileSheet = ImageIO.read(TileDrawer.class.getResource(tileSheetPath));
            // tileSize = tileSheet.getWidth() / numTilesAcross;
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

    /**
     * Initializes the grid to a default grid with default colCount and rowCount and default air tiles. <p>
     * Like most methods with drawing in this class, it goes row by row starting from the top and goes left to right for each row.
     */
    public void initializeGrid() {
        drawX = 0;
        drawY = 0;
        colCount = 10;
        rowCount = 10;
        boxList = new ArrayList<ArrayList<GridBox>>();
        for (int row = 0; row < rowCount; row++) {
            boxList.add(new ArrayList<GridBox>());
            for (int col = 0; col < colCount; col++) {
                boxList.get(row).add(new GridBox(defaultBox, 0, drawX, drawY, tileSizeScaled));
                drawX += tileSizeScaled;
            }
            drawX = 0;
            drawY += tileSizeScaled;
        }
    }

    /**
     * Takes the colCount, rowCount, and map array from the file that has been imported and draws the map.
     * 
     * @param colCount The number of columns.
     * @param rowCount The number of rows.
     * @param map The map array.
     */
    public void importFile(int colCount, int rowCount, int[][] map) {
        this.colCount = colCount;
        this.rowCount = rowCount;
        
        drawX = boxList.get(0).get(0).getX();
        drawY = boxList.get(0).get(0).getY();

        // deletes the map currently in the editor and replaces it with a blank one.
        boxList = new ArrayList<ArrayList<GridBox>>(); 

        // same as the initialize grid method, but uses each tileIndex from the map array instead of a default air tile.
        for (int row = 0; row < rowCount; row++) {
            boxList.add(new ArrayList<GridBox>());
            for (int col = 0; col < colCount; col++) {
                int rc = map[row][col];
                int r = rc / numTilesAcross;
                int c = rc % numTilesAcross;
                if (r == 0 && c == 0) { boxList.get(row).add(new GridBox(defaultBox, map[row][col], drawX, drawY, tileSizeScaled)); } // if the tile is air (0), use the default gridBox instead to draw
                else { boxList.get(row).add(new GridBox(tiles[r][c], map[row][col], drawX, drawY, tileSizeScaled)); }
                
                drawX += tileSizeScaled;
            }
            drawX -= tileSizeScaled * colCount;
            drawY += tileSizeScaled;
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

    public void scale(int direction) {
        if (direction == 1) {
            if (scale < 2.6) { scale += 0.2; }
        }
        else if (scale > 0.8) { scale -= 0.2; }
        tileSizeScaled = (int) (tileSize * scale);

        int originX = boxList.get(0).get(0).getX();
        int originY = boxList.get(0).get(0).getY();

        for (int row = 0; row < rowCount; row++) {
            for (int col = 0; col < colCount; col++) {
                int x = originX + (tileSizeScaled * col);
                int y = originY + (tileSizeScaled * row);

                boxList.get(row).get(col).scale(x, y, tileSizeScaled);
            }
        }
    }

    /*  "Creates" a left column() by making a right column first, then moving it to the left of the first column
        Then, to update the data structure of the arraylist of arraylists, we use Collections.rotate() to move everything up by one. */
    public void addLeftColumn() {
        addRightColumn();
        for (int row = 0; row < rowCount; row++) {
            boxList.get(row).get(colCount - 1).move(-tileSizeScaled * colCount, 0);
            Collections.rotate(boxList.get(row), 1); //https://stackoverflow.com/questions/4938626/moving-items-around-in-an-arraylist 
        }
    }

    public void removeLeftColumn() {
        // so you don't remove a column when there's only one column left
        if (colCount != 1) {
            for (int row = 0; row < rowCount; row++) {
                boxList.get(row).remove(0);
            }
            drawX -= tileSizeScaled;

            colCount--;
        }
    }

    public void addRightColumn() {
        // gets the x coordinate of the last box of the first row
        int lastX = tileSizeScaled + boxList.get(0).get(colCount - 1).getX();
        drawX = lastX;

        // gets the y coordinate of the last box of the first row
        int lastY = boxList.get(0).get(colCount - 1).getY();
        drawY = lastY;

        for (int row = 0; row < rowCount; row++) {
            boxList.get(row).add(new GridBox(defaultBox, 0, drawX, drawY, tileSizeScaled));
            drawY += tileSizeScaled;
        }
        drawY -= rowCount * tileSizeScaled;
        drawX += tileSizeScaled;

        colCount++;
    }

    public void removeRightColumn() {
        // so you don't remove a column when there's only one column left
        if (colCount != 1) {
            int lastCol = colCount - 1;
            for (int row = 0; row < rowCount; row++) {
                boxList.get(row).remove(lastCol);
            }

            colCount--;
        }
    }

    /* Uses a tetris style method of updating the arraylists. First, a new bottom row is created. Then, starting from the
    second to last row to the first row, we use Collections.copy to copy the list before to the current list.
    Ex. Start with 3 rows. 
        addBottomRow() adds 1 more row of blank space to the bottom, meaning we now have 4 rows.
        3rd row copied to 4th row.
        2nd row copied to 3rd row.
        1st row copied to 2nd row.
        Every box in 1st row replaced with air.
        So now, every row moves one down. */
    public void addTopRow() {
        addBottomRow();
        for (int row = rowCount - 1; row > 0; row--) {
            Collections.copy(boxList.get(row), boxList.get(row-1));
        }

        int firstX = boxList.get(0).get(0).getX();
        drawX = firstX;

        int firstY = -tileSizeScaled + boxList.get(0).get(0).getY();
        drawY = firstY;

        for (int col = 0; col < colCount; col++) {
            boxList.get(0).set(col, new GridBox(defaultBox, 26, drawX, drawY, tileSizeScaled));
            drawX += tileSizeScaled;
        }
    }

    public void removeTopRow() {
        if (rowCount != 1) {
            boxList.remove(0);
            rowCount--;
        }
    }

    public void addBottomRow() {
        int firstX = boxList.get(0).get(0).getX();
        drawX = firstX;

        int firstY = tileSizeScaled + boxList.get((rowCount - 1)).get(0).getY();
        drawY = firstY;

        ArrayList<GridBox> newLine = new ArrayList<GridBox>();
        for (int col = 0; col < colCount; col++) {
            newLine.add(new GridBox(defaultBox, 0, drawX, drawY, tileSizeScaled));
            drawX += tileSizeScaled;
        }
        boxList.add(newLine);

        rowCount++;
    }

    public void removeBottomRow() {
        if (rowCount != 1) {
            boxList.remove(rowCount - 1);
            rowCount--;
        }
    }

    public void updateTile(int selectedTile, int mouseX, int mouseY) {
        for (int row = 0; row < rowCount; row++) {
            for (int col = 0; col < colCount; col++) {
                if (boxList.get(row).get(col).mouseOver(mouseX, mouseY)) {
                    int r = selectedTile / numTilesAcross;
                    int c = selectedTile % numTilesAcross;
                    if (r == 0 && c == 0) { boxList.get(row).get(col).setImage(defaultBox, 0); }
                    else { boxList.get(row).get(col).setImage(tiles[r][c], selectedTile); }
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
        if (moveUp) { moveTiles(0, 1 * moveSpeed); }
        if (moveDown) { moveTiles(0, -1 * moveSpeed); }
        if (moveLeft) { moveTiles(1 * moveSpeed, 0); }
        if (moveRight) { moveTiles(-1 * moveSpeed, 0); }

        for (int row = 0; row < boxList.size(); row++) {
            for (int col = 0; col < boxList.get(row).size(); col++) {
                boxList.get(row).get(col).draw(surface);
            }
        }
    }

}