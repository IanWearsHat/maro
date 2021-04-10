package Game.Entity;

import Game.Resources.TileMap;
import java.util.ArrayList;
import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;


public class Player extends MapObject {

    // player stuff
    private int health;
    private int maxHealth;
    private int fire;
    private int maxFire;
    private boolean dead;
    private boolean flinching;
    private long flinchTime;

    //Magic

    private int mana;
    private int manaMax;
    private String[] spells = {};
    private int magicSpellDamage;
    //private ArrayList<MagicSpell>  magicSpell;

    //animations
    private ArrayList<BufferedImage[]> sprites;

    //animated actions;
    private static final int IDLE = 0;
    private static final int WALKING = 1;
    private static final int RUNINING = 2;

    public Player(TileMap tile) {
        super(tile);
        //TODO Auto-generated constructor stub
    }
 
}