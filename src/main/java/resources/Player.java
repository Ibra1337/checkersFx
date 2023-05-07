package resources;

import java.io.Serializable;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.UUID;

public class Player  implements IPlayer  {

    private int color;
    private boolean inGame = false;
    private String id = UUID.randomUUID().toString();
    private String gameId ;
    public Player(int color) throws RemoteException {
        this.color = color;
    }

    @Override
    public void setColor(int color) {
        this.color = color;
    }

    @Override
    public int getColor() {
        return color;
    }

    @Override
    public synchronized boolean inGAme() {
        return inGame;
    }

    @Override
    public synchronized void setInGame() {
        this.inGame = true;
    }


    @Override
    public String getId() {
        return id;
    }

    @Override
    public String  getGameId() {
        return gameId;
    }

    @Override
    public void setGameId(String id) {
        gameId = id;
    }
}
