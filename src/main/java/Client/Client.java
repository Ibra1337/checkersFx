package Client;

import GUI.CheckersBoardView;
import resources.IPlayer;
import resources.Player;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class Client {


    public static void main(String[] args) {



        try {
            Registry reg = LocateRegistry.getRegistry("192.168.220.1" , 1099);
            Player p = new Player(1);

        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }


    }
}
