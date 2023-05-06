package Server;

import resources.IPlayer;

import java.io.Serializable;
import java.rmi.Remote;
import java.rmi.RemoteException;

public interface IGame extends Remote , Serializable {

    IPlayer getBlack() throws RemoteException;

    IPlayer getWhite() throws RemoteException;


}
