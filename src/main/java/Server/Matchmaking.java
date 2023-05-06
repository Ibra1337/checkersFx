package Server;

import resources.IPlayer;

import java.io.Serializable;
import java.rmi.AlreadyBoundException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.*;

public class Matchmaking implements IMatchmaking , Serializable {

    List<Game> games ;
    LinkedList<UUID> blackQueue;
    LinkedList<UUID> whiteQueue;
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
            blackQueue.add(player.getId());
        else
            whiteQueue.add(player.getId());
        if (blackQueue.size() >=1 && whiteQueue.size() >=1) {
            games.add(new Game((IPlayer) reg.lookup(blackQueue.poll().toString()), (IPlayer) reg.lookup(whiteQueue.poll().toString())));
            System.out.println("game has been created: " +blackQueue.size() +";"+ whiteQueue.size());
        }

    }


}
