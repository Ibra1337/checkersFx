package Server;

import resources.BoardLogic;
import resources.IPlayer;

import java.io.Serializable;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.UUID;

public class Game extends UnicastRemoteObject implements   IGame {

    private IPlayer blackPlayer;
    private IPlayer whitePlayer;
    private BoardLogic bl;
    private String gameId;
    private int playersRound =1;


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
    public void playerRoundChange() {
        playersRound = playersRound*(-1);
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
