package panel;

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

    private int x = SPACEBETWEENBOXES;
    private int y = SPACEBETWEENBOXES;

    private int xOffset;
    private int yOffset;

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
                boxList.add(new GridBox(tiles[0][12], 11, x, y, tileSize, boxID));
                boxID++;
                y += tileSize + SPACEBETWEENBOXES;
            }
            y = SPACEBETWEENBOXES;
            x += tileSize + SPACEBETWEENBOXES;
        }
    }

    public void addColumn() {
        for (int i = 0; i < rowCount; i++) {
            boxList.add(new GridBox(tiles[1][1], 22, x, y, tileSize, boxID));
            boxID++;
            y += tileSize + SPACEBETWEENBOXES;
        }
        y = SPACEBETWEENBOXES;
        x += tileSize + SPACEBETWEENBOXES;

        colCount++;
    }

    public void removeColumn() {
        int initialSize = boxList.size();
        for (int i = (boxList.size() - 1); i > (initialSize - 1) - rowCount; i--) {
            boxList.remove(i);
            boxID--;
        }
        x -= tileSize + SPACEBETWEENBOXES;

        colCount--;
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
        for (int i = 0; i < boxList.size(); i++) {
            boxList.get(i).draw(surface);
        }
    }

}