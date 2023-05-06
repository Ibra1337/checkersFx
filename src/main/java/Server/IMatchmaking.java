package Server;

import resources.IPlayer;

import java.rmi.AlreadyBoundException;
import java.rmi.NotBoundException;
import java.rmi.Remote;
import java.rmi.RemoteException;

public interface IMatchmaking extends Remote {

    void addPlayer(IPlayer id) throws RemoteException, NotBoundException, AlreadyBoundException;





}
