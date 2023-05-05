package Server;

import resources.IPlayer;

import java.rmi.Remote;

public interface IMatchmaking extends Remote {

    void addPlayer(IPlayer player);





}
