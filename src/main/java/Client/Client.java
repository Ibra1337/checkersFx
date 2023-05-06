package Client;

import GUI.CheckersBoardView;
import Server.IMatchmaking;
import resources.IPlayer;
import resources.Player;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class Client {


    public static void main(String[] args) {


        try {
            Registry reg = LocateRegistry.getRegistry("192.168.220.1" , 1099);
            Player p = new Player(1);
            IMatchmaking mm = (IMatchmaking) reg.lookup("mm");
            mm.addPlayer(p);

            while (!p.inGAme())
            {
                Thread.sleep(1000);
                System.out.println("w8 for oponent");
            }
            CheckersBoardView view = new CheckersBoardView();
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        } catch (NotBoundException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


    }
}
