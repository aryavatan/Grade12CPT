package rpgcpt;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;

/**
 * This class is responsible for the beginning title screen, it imports the
 * images and also has a draw method for displaying the title screen
 * @author Aidan Wong
 */
public class Title {

    static BufferedImage title;

    public Title() {
        initTitle();
    }

    private static void initTitle() {

        try {
            title = ImageIO.read(new File("src/resources/TitlePage.png"));
        } catch (IOException ex) {
            Logger.getLogger(Title.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
    
    public void draw(Graphics g){
        g.drawImage(title, 0, 0, null);
    }
}