package Server;

import resources.IPlayer;

import java.io.Serializable;
import java.rmi.AlreadyBoundException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.*;

public class Matchmaking implements IMatchmaking , Serializable {

    List<IGame> games ;
    LinkedList<String> blackQueue;
    LinkedList<String> whiteQueue;
    Registry reg ;


    public Matchmaking(List<IGame> games , Registry reg) {
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
            IPlayer black = (IPlayer) reg.lookup(blackQueue.poll().toString());
            IPlayer white = (IPlayer) reg.lookup(whiteQueue.poll().toString());
            black.setInGame();
            white.setInGame();
            IGame game = new Game(black,white);

            black.setGameId(game.getId());
            white.setGameId(game.getId());
            reg.bind(game.getId().toString() , game);
            games.add(game);
            System.out.println("game has been created: " +blackQueue.size() +";"+ whiteQueue.size());


        }

    }


}
