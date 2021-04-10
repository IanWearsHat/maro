package Game.Resources;

import java.awt.*;
import java.awt.image.*;
import java.awt.RenderingHints;

import javax.imageio.ImageIO;

import Game.Main.GamePanel;

import java.io.*;

public class TileMap {
    
    //position

    private double x;
    private double y;

    //bounds

    private int xmin;
    private int ymin;
    private int xmax;
    private int ymax;

    private double tween;

    //map

    private int[][] map;
    private int tileSize;
    private int numRows;
    private int numCols;
    private int width;
    private int height;

    //tileset
    private BufferedImage tileset;
    private int numTilesAcross;
    private Tile[][] tiles;

    //drawing
    private int rowOffset;
    private int colOffset;
    private int numRowsToDraw;
    private int numColsToDraw;

    public TileMap(int tileSize){
        this.tileSize = tileSize;
        numRowsToDraw = GamePanel.height/tileSize + 2;
        numColsToDraw = GamePanel.width/tileSize + 2;
        tween = 0.07;
    }

    public void loadTiles(String s){
        try{
            tileset = ImageIO.read(TileMap.class.getResource(s));
            System.out.println(tileset.getWidth());
            numTilesAcross = tileset.getWidth() / tileSize;
            tiles = new Tile[2][numTilesAcross];

            BufferedImage subimage;
            for(int col = 0; col < numTilesAcross; col++){
                subimage = tileset.getSubimage(
                    col * tileSize, 
                    0, 
                    tileSize, 
                    tileSize);
                    tiles[0][col] = new Tile(subimage,Tile.NORMAL);
                    subimage = tileset.getSubimage(
                        col*tileSize, 
                        tileSize, 
                        tileSize, 
                        tileSize);
                    tiles[1][col] = new Tile(subimage, Tile.BLOCKED);
            }

        }
        catch(Exception e){
            e.printStackTrace();
        }
    }

    public void loadMap(String s){

        try{

            InputStream in = TileMap.class.getResourceAsStream(s);
            BufferedReader br = new BufferedReader(
                new InputStreamReader(in)
            );
            numCols = Integer.parseInt(br.readLine());
            numRows = Integer.parseInt(br.readLine());
            map = new int[numRows][numCols];
            width = numCols * tileSize;
            height = numRows * tileSize;

            String delims = "\\s+";

            for (int row = 0; row < numRows; row++){
                String line = br.readLine();
                String[] tokens = line.split(delims);
                for(int col = 0; col < numCols; col++){
                    map[row][col] = Integer.parseInt(tokens[col]);
                }
            }

        }
        catch(Exception e){
            e.printStackTrace();
        }

    }

    public int getTileSize(){return tileSize;}
    public int getX(){return (int)x;}
    public int getY(){return (int)y;}
    public int getWidth(){return width;}
    public int getHeight(){return height;}

    public int getType(int row, int col){
        int rc = map[row][col];
        int r = rc/numTilesAcross;
        int c = rc % numTilesAcross;

        return tiles[r][c].getType();
    }

    public void setPosition(double x, double y){

        //allows camera/screen to follow player smoothly

        this.x += (x - this.x)*tween;
        this.y += (y-this.y)*tween;

        fixBounds();

        colOffset = (int) -this.x/tileSize;
        rowOffset = (int) -this.y/tileSize;
    }

    private void fixBounds(){
        if(x<xmin) x = xmin;
        if(x<xmax) x = xmax;
        if(y<ymin) y = ymin;
        if(y<ymax) y = ymax;
    }

    public void draw(Graphics2D g){
        for (int row = rowOffset; row < rowOffset + numRowsToDraw; row++){

            if(row >= numRows) break;
            
            for(int col = colOffset; col < colOffset + numColsToDraw; col++){

                if(col >= numCols) break;

                if(map[row][col] == 0) continue;

                int rc = map[row][col];
                int r = rc/numTilesAcross;
                int c = rc % numTilesAcross;

                // g.setColor(Color.BLACK);
                // g.fillRect((int)x + col * tileSize,(int) y+row*tileSize, tileSize, tileSize);

                g.drawImage(
                    tiles[r][c].getImage(),
                    (int) x + col * tileSize,
                    (int) y + row * tileSize,
                    null
                );
            }
        }
    }
}
