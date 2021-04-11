package Game.Entity;

import Game.Main.GamePanel;
import Game.Resources.Tile;
import Game.Resources.TileMap;

import java.awt.Rectangle;

import javax.swing.text.DefaultStyledDocument.ElementSpec;

public abstract class MapObject {
    
    //protected so that subclass can actual see 
    //tile stuff
    protected TileMap tilemap;
    protected int tileSize;
    protected double xmap;
    protected double ymap;

    //position and vector
    protected double x;
    protected double y;
    protected double dx;
    protected double dy;
    
    //dimensions
    protected int width;
    protected int height;

    // collison box
    protected int cwidth;
    protected int cheight;

    //collision
    protected int currRow;
    protected int currCol; 
    protected double xdest;
    protected double ydest;
    protected double xtemp;
    protected double ytemp;
    protected boolean topLeft;
    protected boolean topRight;
    protected boolean bottomLeft;
    protected boolean bottomRight;

    //Animation
    protected Animation animation;
    protected int currentAction;
    protected int previousAction;
    protected boolean facingRight;

    //movement

    protected boolean left;
    protected boolean right;
    protected boolean up;
    protected boolean down;
    protected boolean jumping;
    protected boolean falling;
    
    // movement attribute
    protected double moveSpeed;
    protected double maxSpeed;
    protected double stopSpeed;
    protected double fallSpeed;
    protected double maxFallSpeed;
    protected double jumpStart;
    protected double stopJumpSpeed;

    public MapObject(TileMap tile){
        tilemap = tile;
        tileSize = tile.getTileSize();
    }

    public boolean intersects(MapObject o){
        Rectangle r1 = getRectangle();
        Rectangle r2 = o.getRectangle();
        return r1.intersects(r2);
    }

    public Rectangle getRectangle(){
        return new Rectangle(
            (int) x - cwidth,
            (int) y - cheight,
            cwidth,
            cheight
        );
    }

    public void calculateCorners(double x, double y){

        int leftTile = (int) (x-cwidth / 2) / tileSize;
        int rightTile = (int) (x+cwidth / 2 - 1) / tileSize;
        int topTile = (int) (y-cheight / 2) / tileSize;
        int bottomTile = (int) (y+cheight / 2 - 1) / tileSize;

        int tl = tilemap.getType(topTile, leftTile);
        int tr = tilemap.getType(topTile, rightTile);
        int bl = tilemap.getType(bottomTile, leftTile);
        int br = tilemap.getType(bottomTile, rightTile);

        topLeft = tl == Tile.BLOCKED;
        topRight = tr ==Tile.BLOCKED;
        bottomRight = br == Tile.BLOCKED;
        bottomLeft = bl == Tile.BLOCKED;

    }

    public void checkTileMapCollision(){

        currCol = (int) x /tileSize;
        currRow = (int) y /tileSize;

        xdest = x + dx;
        ydest = y + dy;

        xtemp = x;
        ytemp = y;

        calculateCorners(x, ydest);
        if(dy < 0){
            if(topLeft || topRight){
                dy = 0;
                ytemp = currRow *tileSize + cheight /2;
            }
            else{
                ytemp += dy;
            }
        }
        if(dy > 0){
            if(bottomLeft || bottomRight){
                dy = 0;
                falling = false;
                ytemp = (currRow+1) * tileSize - cheight/2;
            }
            else{
                ytemp += dy;
            }
        }

        calculateCorners(xdest, y);
        if (dx < 0){
            if (topLeft || bottomLeft){
                dx = 0;
                xtemp = currCol * tileSize + cwidth /2;
            }
            else{
                xtemp+= dx;
            }
        }
        if(dx > 0){
            if (topRight || bottomRight){
                dx = 0;
                xtemp = (currCol+1) * tileSize + cwidth /2;
            }
            else{
                xtemp+= dx;
            }
        }

        if(!falling){
            calculateCorners(x, ydest + 1);
            if (!bottomLeft && !bottomRight){
                falling = true;
            }
        }


    }
    public int getX(){return (int) x;}
    public int getY(){return (int) y;}
    public int getWidth(){return width;}
    public int getHeight(){return height;}
    public int getCWidth(){return cwidth;}
    public int getCHeight(){return cheight;}
    public void setPosition(double x, double y){
        this.x = x;
        this.y = y;
    }
    public void setVector(double dx, double dy){
        this.dx = dx;
        this.dy = dy;
    }
    
    public void setMapPosition(){
        xmap = tilemap.getX();
        ymap = tilemap.getY();
    }

    public void setLeft(boolean b) {left = b;}
    public void setRight(boolean b) {right = b;}
    public void setUp(boolean b) {up = b;}
    public void setDown(boolean b) {down = b;}
    public void setJumping(boolean b){jumping = b;}

    //Method to determine if object/tile is on the screen
    public boolean onScreen(){
        return x + xmap + width < 0 ||
            x + xmap - width > GamePanel.width ||
            y + ymap + height < 0 ||
            y + ymap -height > GamePanel.height;
    }

}
