package Server;

import resources.BoardLogic;
import resources.IPlayer;

import java.io.Serializable;
import java.rmi.NotBoundException;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.registry.Registry;

public interface IGame extends Remote , Serializable {

    static final long serialVersionUID = -4526579883456479758L;

    IPlayer getBlack() throws RemoteException;

    IPlayer getWhite() throws RemoteException;

    BoardLogic getBl()throws RemoteException;

    public void setBL(BoardLogic bl)throws RemoteException;

    String getId()throws RemoteException;

    void  playerRoundChange() throws RemoteException;

    public int getPlayersRound() throws  RemoteException;

    public void disconnect() throws RemoteException;

    public boolean disconnectOccurred() throws RemoteException;

    public void removeFromServer(Registry reg) throws RemoteException, NotBoundException;
}
