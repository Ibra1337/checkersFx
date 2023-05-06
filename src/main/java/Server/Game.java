package Server;

import resources.BoardLogic;
import resources.IPlayer;

import java.io.Serializable;
import java.util.UUID;

public class Game implements Serializable , IGame {

    private IPlayer blackPlayer;
    private IPlayer whitePlayer;
    private BoardLogic bl;
    private UUID gameId;


    public Game(IPlayer blackPlayer, IPlayer whitePlayer) {
        this.blackPlayer = blackPlayer;
        this.whitePlayer = whitePlayer;
        gameId=UUID.randomUUID();
        System.out.println(blackPlayer.getId().toString() + " ; "+blackPlayer.inGAme());
        System.out.println(whitePlayer.getId().toString() + " : " + whitePlayer.inGAme());
        bl = new BoardLogic();
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
    public UUID getId() {
        return gameId;
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
