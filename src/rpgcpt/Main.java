package rpgcpt;

import java.io.File;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.JFrame;

public class Main extends JFrame {

    GamePanel gp = new GamePanel();

    public Main() {
        setSize(730, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
        setResizable(false);
        add(gp);
        playSound();
        
    }

    public static void main(String[] args) {
        Main m = new Main();
    }

    public void playSound() {
        /*
        This method imports our soundtrack and continues to loop it until
        the program is closed
        */
        try {
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(new File("src/resources/soundtrack.wav").getAbsoluteFile());
            Clip clip = AudioSystem.getClip();
            clip.open(audioInputStream);
            clip.loop(ABORT);
        } catch(Exception ex) {
            System.out.println("Error with playing sound.");
            ex.printStackTrace();
        }
    }
}