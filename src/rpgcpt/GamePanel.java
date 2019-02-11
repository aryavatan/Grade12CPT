package rpgcpt;

import java.awt.*;
import java.awt.event.*;
import javax.swing.JPanel;
import javax.swing.JOptionPane;

public class GamePanel extends JPanel implements Runnable {

    //Double Buffering
    private Image dbImage;
    private Graphics dbg;
    
    //JPanel variables
    static final int GWIDTH = 730, GHEIGHT = 600;
    static final Dimension gameDim = new Dimension(GWIDTH, GHEIGHT);
    
    //Game Variables
    private Thread game;
    private int gameState = 0;
    private volatile boolean running = false;

    Boolean monster = true;
    Boolean monster1 = true;
    Boolean monster2 = true;
    Boolean monster3 = true;
    String[] buttons = {"Attack", "Defend", "Run"};
    
    //Game Objects
    Title title;
    Inventory main;
    World mainMap;
    Enemy combat;

    public GamePanel() {
        /*
        Creates all objects needed to begin the program
        Also handles all the KeyListener methods
            -All of our key inputs are recieved here and sent to their respective
            methods and classes
        */
        title = new Title();
        main = new Inventory();
        mainMap = new World();
        combat = new Enemy();

        setPreferredSize(gameDim);
        setBackground(Color.WHITE);
        setFocusable(true);
        requestFocus();
        
        //Handle all key inputs from user
        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_A) {
                    if (gameState == 1) {
                        mainMap.playerMovement(mainMap.MOVE_LEFT);
                    }
                }
                if (e.getKeyCode() == KeyEvent.VK_D) {
                    if (gameState == 1) {
                        mainMap.playerMovement(mainMap.MOVE_RIGHT);
                    }
                }
                if (e.getKeyCode() == KeyEvent.VK_W) {
                    if (gameState == 1) {
                        mainMap.playerMovement(mainMap.MOVE_UP);
                    }
                    if (gameState == 3) {
                        combat.menu(combat.MOVE_UP);
                    }
                }
                if (e.getKeyCode() == KeyEvent.VK_S) {
                    if (gameState == 1) {
                        mainMap.playerMovement(mainMap.MOVE_DOWN);
                    }
                    if (gameState == 3) {
                        combat.menu(combat.MOVE_DOWN);
                    }
                }
                
                if(e.getKeyCode() == KeyEvent.VK_SPACE) {
                    if (gameState == 0) {
                        gameState = 1;
                    }
                    if (gameState == 3) {
                        combat.menu(combat.ACTION_SPACE);
                    }
                }
                if (e.getKeyCode() == KeyEvent.VK_I && gameState != 3) {
                    JOptionPane.showMessageDialog(null, "Castle Key: " + main.getCastleKey(), "Inventory", JOptionPane.INFORMATION_MESSAGE);
                }

                if (mainMap.getMapX() == -1632 && mainMap.getMapY() == -64 && mainMap.isItemExist() == true && e.getKeyCode() == KeyEvent.VK_SPACE) {
                    JOptionPane.showMessageDialog(null, "Item Picked Up: Castle Key", "Picked Up", JOptionPane.INFORMATION_MESSAGE);
                    main.addKey();
                    mainMap.setItemExist(false);
                }
            }

            @Override
            public void keyReleased(KeyEvent e) {

            }

            @Override
            public void keyTyped(KeyEvent e) {

            }
        });

    }

    // Event sequences
    public void run() {
        /*
        Handles all the seperate game states as well as enemy placements
        on the map
        */
        while (running) {

            gameUpdate();
            gameRender();
            paintScreen();
            
            // Mob Collisions
            if (mainMap.getMapX() == -352 && mainMap.getMapY() == -1312 && monster == true) { 
                gameState = 3;
                monster = false;
                mainMap.setMobExist(false);
            }
            if (mainMap.getMapX() == -1280 && mainMap.getMapY() == -1312 && monster1 == true) {
                gameState = 3;
                monster1 = false;
                mainMap.setMobExist1(false);
            }
            if (mainMap.getMapX() == -1408 && mainMap.getMapY() == -960 && monster2 == true) {
                gameState = 3;
                monster2 = false;
                mainMap.setMobExist2(false);
            }
            if (mainMap.getMapX() == -1056 && mainMap.getMapY() == -672 && monster3 == true) {
                gameState = 3;
                monster3 = false;
                mainMap.setMobExist3(false);
            }
            
            // Reset object "combat"
            if (combat.isFinishBattle() == true) {
                gameState = 1;
                combat = new Enemy();
            }
            
            if (mainMap.getMapX() == -288 && mainMap.getMapY() == -352 && main.getCastleKey()== 1) {
                 JOptionPane.showMessageDialog(null, "Thanks for playing the beta!", "BETA 7.4", JOptionPane.INFORMATION_MESSAGE);
                 System.exit(0);
            }
        }
    }

    private void gameUpdate() {
        if (running && game != null) {
            
            //update game state
            mainMap.moveMap();
            
        }
    }

    private void gameRender() {
        if (dbImage == null) {  // Create the buffer
            dbImage = createImage(GWIDTH, GHEIGHT);
            if (dbImage == null) {
                System.err.println("dbImage is still null!");
                return;
            } else {
                dbg = dbImage.getGraphics();
            }
        }
        
        //Clear the screen
        dbg.setColor(Color.WHITE);
        dbg.fillRect(0, 0, GWIDTH, GHEIGHT);
        
        //Draw Game elements
        if (gameState == 0) {
            drawTitle(dbg);
        }
        if (gameState == 1) {
            drawMain(dbg);
        }
        if (gameState == 3){
            drawCombat(dbg);
        }
    }

    /* Draw all game content in these methods */
     
    public void drawCombat(Graphics g){
        combat.draw(g);
    }

    public void drawTitle(Graphics g) {
        title.draw(g);
    }

    public void drawMain(Graphics g) {
        mainMap.draw(g);
    }

    private void paintScreen() {
        Graphics g;
        try {
            g = this.getGraphics();
            if (dbImage != null && g != null) {
                g.drawImage(dbImage, 0, 0, null);
            }
            Toolkit.getDefaultToolkit().sync(); //For some operating sys
            g.dispose();
        } catch (Exception e) {
            System.err.println(e);
        }
    }

    public void addNotify() {
        super.addNotify();
        StartGame();
    }

    public void StartGame() {
        if (game == null || !running) {
            game = new Thread(this);
            game.start();
            running = true;
        }
    }

    public void StopGame() {
        if (running) {
            running = false;
        }
    }

}