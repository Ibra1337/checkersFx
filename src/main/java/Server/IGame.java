package Server;

import resources.BoardLogic;
import resources.IPlayer;

import java.io.Serializable;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.UUID;

public interface IGame extends Remote , Serializable {

    IPlayer getBlack() throws RemoteException;

    IPlayer getWhite() throws RemoteException;

    BoardLogic getBl();

    public void setBL(BoardLogic bl);

    UUID getId();


}
