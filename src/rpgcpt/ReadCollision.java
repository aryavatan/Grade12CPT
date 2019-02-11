package rpgcpt;


import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * This purpose of this class is to read the text file corresponding to the
 * collision on our game and to make sense of the  information there, it reads
 * the numbers in the text file and places them in a int[][], and then the array
 * is used during movement throughout the map so that there are boundaries on where
 * the player can go.
 * @author Arya Vatan-Abadi
 */
public class ReadCollision {

    private FileReader fr;
    private BufferedReader br; 
    private int[][] collision;

    public ReadCollision() {
        try {
            this.fr = new FileReader("Collision.txt");
            this.br = new BufferedReader(fr);
            this.collision = readCollisionFile();
            br.close();
        } catch (FileNotFoundException ex) {
            Logger.getLogger(ReadCollision.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(ReadCollision.class.getName()).log(Level.SEVERE, null, ex);
        }   
    }
    
    private int[][] readCollisionFile() throws IOException{
        String line = br.readLine();
        line = line.replaceAll(" ", "");
        int[][]collision = new int[75][74];
        int row = 0;
        int column = 0;
        while(line != null){
            line = line.replaceAll("\t", "");
            for (int i = 0; i < line.length(); i++) {
                char num = line.charAt(i);
                if(num=='1' || num=='0'){
                    collision[row][i] = Integer.parseInt(num+"");
                }
            }
            row++;
            line = br.readLine();
        }
        return collision;
    }
    
    public int[][] getCollision() throws IOException{
        return this.collision;
    }
}