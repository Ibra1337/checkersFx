package Server;

import resources.IPlayer;

import java.util.LinkedList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Queue;

public class Matchmaking implements IMatchmaking {

    List<Game> games ;
    Queue<IPlayer> blackQueue;
    Queue<IPlayer> whiteQueue;


    public Matchmaking(List<Game> games) {
        blackQueue = new PriorityQueue<>();
        whiteQueue = new PriorityQueue<>();
        this.games = games;
    }
    @Override
    public void addPlayer(IPlayer player)
    {
        System.out.println("ading player");
        if (player.getColor() <0 )
            blackQueue.add(player);
        else
            whiteQueue.add(player);
        if (blackQueue.size() >=1 && whiteQueue.size() >=1) {
            games.add(new Game(blackQueue.poll(), whiteQueue.poll()));
            System.out.println("game has been created: " +blackQueue.size() +";"+ whiteQueue.size());
        }
    }


}
