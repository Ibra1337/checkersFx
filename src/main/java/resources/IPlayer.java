package resources;

import java.io.Serializable;
import java.rmi.Remote;
import java.rmi.RemoteException;

public interface IPlayer  extends Remote , Serializable {

    public void setColor(int color) throws RemoteException;

    public int getColor() throws RemoteException;

    public boolean inGAme();

    public void setInGame(boolean state);

}
