package Server;

import resources.BoardLogic;
import resources.IPlayer;

import java.io.Serializable;

public class Game implements Serializable , IGame {

    private IPlayer blackPlayer;
    private IPlayer whitePlayer;
    private BoardLogic bl;


    public Game(IPlayer blackPlayer, IPlayer whitePlayer) {
        this.blackPlayer = blackPlayer;
        this.whitePlayer = whitePlayer;
        this.blackPlayer.setInGame();
        this.whitePlayer.setInGame();
        System.out.println(blackPlayer.inGAme());
        System.out.println(whitePlayer.inGAme());
        bl = new BoardLogic();
    }


    @Override
    public IPlayer getBlack() {
        return blackPlayer;
    }

    @Override
    public IPlayer getWhite() {
        return null;
    }
}
