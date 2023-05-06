package resources;

import java.io.Serializable;

public class Player implements IPlayer , Serializable {

    private int color;
    private boolean inGame = false;

    public Player(int color) {
        this.color = color;
    }

    @Override
    public void setColor(int color) {
        this.color = color;
    }

    @Override
    public int getColor() {
        return color;
    }

    @Override
    public synchronized boolean inGAme() {
        return inGame;
    }

    @Override
    public synchronized void setInGame(boolean state) {
        inGame = state;
    }


}
