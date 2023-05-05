package resources;

import java.rmi.Remote;

public interface IPlayer  extends Remote {

    public void setColor(int color);

    public int getColor();


}
