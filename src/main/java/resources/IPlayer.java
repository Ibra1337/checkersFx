package resources;

import java.io.Serializable;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.UUID;

public interface IPlayer  extends Remote , Serializable {

    public void setColor(int color) throws RemoteException;

    public int getColor() throws RemoteException;

    public boolean inGAme();

    public void  setInGame();

    public UUID getId();

}
