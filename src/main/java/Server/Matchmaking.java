package Server;

import resources.IPlayer;

import java.io.Serializable;
import java.rmi.AlreadyBoundException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.LinkedList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Queue;

public class Matchmaking implements IMatchmaking , Serializable {

    List<Game> games ;
    LinkedList<IPlayer> blackQueue;
    LinkedList<IPlayer> whiteQueue;
    Registry reg ;


    public Matchmaking(List<Game> games , Registry reg) {
        blackQueue = new LinkedList<>();
        whiteQueue = new LinkedList<>();
        this.reg = reg;
        this.games = games;
    }

    @Override
    public void addPlayer(IPlayer player) throws RemoteException, NotBoundException, AlreadyBoundException {


        reg.bind(player.getId().toString() , player);
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
