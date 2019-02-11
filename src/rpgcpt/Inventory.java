package rpgcpt;


public class Inventory {

    int castleKey = 0;


    public Inventory() {
    }

    public int getCastleKey() {
        return castleKey;
    }


    public void addKey() {
        castleKey++;
    }

}