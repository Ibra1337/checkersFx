package Server;

import resources.BoardLogic;
import resources.IPlayer;

import java.io.Serializable;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.UUID;

public class Game extends UnicastRemoteObject implements   IGame {

    private IPlayer blackPlayer;
    private IPlayer whitePlayer;
    private BoardLogic bl;
    private String gameId;
    private int playersRound =1;
    private boolean disconnect = false;

    public Game(IPlayer blackPlayer, IPlayer whitePlayer) throws RemoteException {
        super();
        this.blackPlayer = blackPlayer;
        this.whitePlayer = whitePlayer;
        gameId=UUID.randomUUID().toString();
        System.out.println(blackPlayer.getId().toString() + " ; "+blackPlayer.inGAme());
        System.out.println(whitePlayer.getId().toString() + " : " + whitePlayer.inGAme());
        bl = new BoardLogic();
    }

    @Override
    public int getPlayersRound() {
        return playersRound;
    }

    @Override
    public void disconnect() throws RemoteException {
        disconnect = false;
    }

    @Override
    public boolean disconnectOccurred() throws RemoteException {
        return disconnect;
    }

    @Override
    public void removeFromServer(Registry reg) throws RemoteException, NotBoundException {
        reg.unbind(blackPlayer.getGameId());
        reg.unbind(whitePlayer.getId());
        reg.unbind(gameId);
    }

    @Override
    public IPlayer getBlack() {
        return blackPlayer;
    }

    @Override
    public IPlayer getWhite() {
        return whitePlayer;
    }

    @Override
    public String getId() {
        return gameId;
    }

    @Override
    public void playerRoundChange() throws RemoteException {
        playersRound = playersRound*(-1);
    }



    public void setBl(BoardLogic bl) {
        this.bl = bl;
    }

    public void setBL(BoardLogic bl) {
        this.bl = bl;
    }

    @Override
    public BoardLogic getBl() {
        return bl;
    }

    public void updateBoard(BoardLogic bl )
    {
        this.bl = bl;
    }


}
