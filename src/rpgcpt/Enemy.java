package rpgcpt;

import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.JOptionPane;

public class Enemy{
    
    private BufferedImage background, enemy;
    private final String[] menuOptions = {"Attack", "Block", "Heal"};
    private int index;
    private int enemyHP,playerHP,playerStamina;
    boolean finishBattle = false; 
    static final int MOVE_UP = 0, MOVE_DOWN = 1, ACTION_SPACE = 2;
        

    public Enemy(){
        index = 0;
        init();
        enemyHP = 200;
        playerHP = 100;
        playerStamina = 100;
    }
    
    private void init(){
        /*
        Imports the images of the combat screen and enemy sprite
        */
        try {
            background = ImageIO.read(new File("src/resources/FightingScreen.jpg"));
            enemy = ImageIO.read(new File("src/resources/enemy.png"));
        } catch (IOException e) {
            System.out.println("ERROR: FIGHTING SCREEN COULD NOT BE READ");
        }
    }
    
    public void draw(Graphics g){
        /*
        Draws all components of the combat screen such as health bars and sprites
        */
        g.drawImage(background, 0, 0, null);
        g.drawImage(enemy, 475, 65, 190, 220, null);
        
        g.setColor(Color.red);
        //Enemy HP Bar
        g.fillRect(25, 65, (int)(275*enemyHP/200.0), 30);
        //Player HP Bar
        g.fillRect(25, 460, (int)(350*playerHP/100.0), 30);
        //Player Stamina Bar
        g.setColor(Color.cyan);
        g.fillRect(25, 540, (int)(350*playerStamina/100.0), 30);
        
        //Draws the indicator which is used to select an option during battle
        g.setColor(Color.black);
        int size = 25;
        if(menuOptions[index].equals("Attack")){
            g.fillRect(510, 435, size, size);
        }
        else if(menuOptions[index].equals("Block")){
            g.fillRect(510, 493, size, size);
        }
        else if(menuOptions[index].equals("Heal")){
            g.fillRect(510, 548, size, size);
        }
    }
    
    public void menu(int move){
        switch(move){
            case MOVE_UP:
                if(index>0){
                    index--;
                }
                break;
            case MOVE_DOWN:
                if(index<2){
                    index++;
                }
                break;
            case ACTION_SPACE:
                battleAction(menuOptions[index]);
                break;
            default:
                break;
        }
    }
    
    private void battleAction(String option){
        //Player Turn
        if(option.equals("Attack")){
            if(playerStamina>=10){
                enemyHP-=5;
                playerStamina-=10;
            }
            else
                playerStamina = 0;
        }
        else if(option.equals("Block")){
            if(playerStamina<=95){
                playerStamina+=5;
            }
            playerHP += 5;
        }
        else if(option.equals("Heal")){
            if(playerHP<=95){
                playerHP+=15;
                playerStamina--;
            }
        }
        
        //Enemy Turn
        playerHP-=5;
        
        checkVictory();
    }

    public boolean isFinishBattle() {
        return finishBattle;
    }
    
    private void checkVictory(){
        /*
        if the player wins the battle, they can continue on
        otherwise the game will end
        */
        Component frame = null;
        if(playerHP<=0){
            //Player lost the battle
            JOptionPane.showMessageDialog(frame, "You died!");
            System.exit(0);
        }
        else if(enemyHP<=0){
            JOptionPane.showMessageDialog(frame, "You Won!");
            finishBattle = true;
        }
    }
    
}