package rpgcpt;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;

/**
 * This class is responsible for creating the world and all things in it. It
 * imports and draws the map onto the screen, draws the player on the screen,
 * draws enemies on the screen, and draws items on the screen. it also responsible
 * for player movement throughout the map.
 * @author Arya Vatan-Abadi & Aidan Wong
 */
public class World {
    
    static final int MOVE_UP = 0, MOVE_DOWN = 1, MOVE_LEFT = 2, MOVE_RIGHT = 3;
    
    static BufferedImage map, playerUp, playerDown, playerRight, playerLeft, enemy, chest;
    static int mapX = 0;
    static int mapY = 0;
    static int collisionX = 16;
    static int collisionY = 45;
    static boolean moveUp = false;
    static boolean moveLeft = false;
    static boolean moveRight = false;
    boolean mobExist = true;
    boolean mobExist1 = true;
    boolean mobExist2 = true;
    boolean mobExist3 = true;
    boolean itemExist = true;
    static int[][] collision;
    
    public World() {
        initMap();
        initCollision();
    }
    
    public void draw(Graphics g) {
        //720,586
        g.drawImage(map, mapX, mapY, null);
        //System.out.println(mapX + " " + mapY);
        
        // Mobs
        if (mobExist == true) {
            g.drawImage(enemy, mapX + 710, mapY + 1605, 30, 35,null);
        }
        if (mobExist1 == true) {
            g.drawImage(enemy, mapX + 1638, mapY + 1605, 30, 35,null);
        }
        if (mobExist2 == true) {
            g.drawImage(enemy, mapX + 1766, mapY + 1253, 30, 35,null);
        }
        if (mobExist3 == true) {
            g.drawImage(enemy, mapX + 1414, mapY + 965, 30, 35,null);
        }
        
        // Items
        if (itemExist == true) {
            //g.fillOval(mapX + 1990, mapY + 357, 20, 20);
            g.drawImage(chest, mapX + 1980, mapY + 357, 32, 32, null);
        }
        
        // Player
        drawPlayer(g);
    }
    
    /**
     * This method determines what direction the player is traveling in and
     * draws a player sprite that corresponds with this information
     */
    private void drawPlayer(Graphics g) {
        if (moveUp) {
            g.drawImage(playerUp, 352, 293, 23, 33, null);
        } else if (moveRight) {
            g.drawImage(playerRight, 352, 293, 35, 33, null);
        } else if (moveLeft) {
            g.drawImage(playerLeft, 352, 293, 35, 33, null);
        } else {
            g.drawImage(playerDown, 352, 293, 23, 33, null);
        }
    }
    
    private static void initMap() {
        //Imports all images needed
        try {
            map = ImageIO.read(new File("src/resources/GameMap.png"));
            playerUp = ImageIO.read(new File("src/resources/PlayerSprites/PlayerUp.png"));
            playerDown = ImageIO.read(new File("src/resources/PlayerSprites/PlayerDown.png"));
            playerRight = ImageIO.read(new File("src/resources/PlayerSprites/PlayerRight.png"));
            playerLeft = ImageIO.read(new File("src/resources/PlayerSprites/PlayerLeft.png"));
            enemy = ImageIO.read(new File("src/resources/enemy.png"));
            chest = ImageIO.read(new File("src/resources/chest.png"));
        } catch (IOException ex) {
            Logger.getLogger(Map.class.getName()).log(Level.SEVERE, null, ex);
        }
        mapX = map.getWidth() / 74 * -5;
        mapY = map.getHeight() / 75 * -36;
    }
    
    /**
     * Calling to the ReadCollision class, this method will receive the int[][]
     * corresponding to the games collision.
     */
    private void initCollision() {
        ReadCollision c = new ReadCollision();
        try {
            collision = c.getCollision();
        } catch (IOException ex) {
            Logger.getLogger(Map.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
      
    public int getMapX() {
        return mapX;
    }
    
    public int getMapY() {
        return mapY;
    }
    
    public void setMobExist(boolean mobExist) {
        this.mobExist = mobExist;
    }
    
    public void setMobExist1(boolean mobExist1) {
        this.mobExist1 = mobExist1;
    }
    
    public void setMobExist2(boolean mobExist2) {
        this.mobExist2 = mobExist2;
    }
    
    public void setMobExist3(boolean mobExist3) {
        this.mobExist3 = mobExist3;
    }
    
    public void setItemExist(boolean itemExist) {
        this.itemExist = itemExist;
    }
    
    public boolean isItemExist() {
        return itemExist;
    }
    
    public void moveMap() {}
    
    /**
     * This method is responsible for receiving the key presses from the GamePanel
     * class and then determining which direction the player wishes to travel on
     * the map. This method also checks every player move with the collision array 
     * to make sure that the player can indeed move onto their desired tile destination.
     * Additionally, this method also updates boolean variables which correspond to
     * the direction a player is traveling in, which in turn is used in the drawPlayer
     * method as it helps that method know which character sprite to draw.-
     */
    public void playerMovement(int move) {
        int speedX = map.getWidth() / 74;
        int speedY = map.getHeight() / 75;
        
        switch (move) {
            case MOVE_UP:
                if (collision[collisionY - 1][collisionX] == 0) {
                    collisionY--;
                    mapY += speedY;
                }
                
                moveUp = true;
                moveRight = false;
                moveLeft = false;
                
                break;
            case MOVE_DOWN:
                if (collision[collisionY + 1][collisionX] == 0) {
                    collisionY++;
                    mapY -= speedY;
                }
                
                moveUp = false;
                moveRight = false;
                moveLeft = false;
                
                break;
            case MOVE_LEFT:
                if (collision[collisionY][collisionX - 1] == 0) {
                    collisionX--;
                    mapX += speedX;
                }
                
                moveUp = false;
                moveRight = false;
                moveLeft = true;
                
                break;
            case MOVE_RIGHT:
                if (collision[collisionY][collisionX + 1] == 0) {
                    collisionX++;
                    mapX -= speedX;
                }
                
                moveUp = false;
                moveRight = true;
                moveLeft = false;
                
                break;
        }
    }
    
}
