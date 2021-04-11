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
    private int[] SpellCost = {};

    //private ArrayList<MagicSpell>  magicSpell;

    //animations
    private ArrayList<BufferedImage[]> sprites;

    //animated actions;
    private static final int IDLE = 0;
    private static final int WALKING = 1;
    private static final int RUNINING = 2;
    private static final int PERFORMINGMAGIC = 3;

    public Player(TileMap tile) {
        super(tile);

        width = 30;
        height = 30;
        cwidth = 20;
        cheight = 20;

        moveSpeed = 0.3;
        maxSpeed = 1.6;
        stopSpeed = 0.4;
        fallSpeed = 0.15;
        maxFallSpeed = 4.0;
        jumpStart = -4.8;
        stopJumpSpeed = 0.3;

        facingRight = true;

        health = maxHealth = 5;
        fire = maxFire = 2500;



        //TODO Auto-generated constructor stub
    }
 
}