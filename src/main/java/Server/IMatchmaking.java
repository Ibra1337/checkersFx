package Server;

import resources.IPlayer;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface IMatchmaking extends Remote {

    void addPlayer(IPlayer player) throws RemoteException;





}
