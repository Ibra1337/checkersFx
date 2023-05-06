package resources;

import java.io.Serializable;

public class Player implements IPlayer , Serializable {

    int color;

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
}
