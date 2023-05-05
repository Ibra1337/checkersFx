package Server;

import resources.BoardLogic;
import resources.IPlayer;

public class Game {

    private IPlayer blackPlayer;
    private IPlayer whitePlayer;
    private BoardLogic bl;


    public Game(IPlayer blackPlayer, IPlayer whitePlayer) {
        this.blackPlayer = blackPlayer;
        this.whitePlayer = whitePlayer;
       bl = new BoardLogic();
    }


}
