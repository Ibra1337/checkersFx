package Client;

import GUI.CheckersBoardView;
import Server.IGame;
import Server.IMatchmaking;
import resources.IPlayer;
import resources.Player;

import java.rmi.AlreadyBoundException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class Client {


    public static void main(String[] args) {


        try {
            Registry reg = LocateRegistry.getRegistry("192.168.220.1" , 1099);
            IPlayer p = new Player(1);
            IMatchmaking mm = (IMatchmaking) reg.lookup("mm");
            System.out.println(p.getId());
            mm.addPlayer(p);
            p  = (IPlayer) reg.lookup(p.getId().toString());
            System.out.println(p.getId());
            while (!p.inGAme())
            {
                p  = (IPlayer) reg.lookup(p.getId().toString());
                Thread.sleep(1000);
                System.out.println("w8 for oponent");
            }
            while (true) {
                IGame game = (IGame) reg.lookup(p.getGameId().toString());
                CheckersBoardView view = new CheckersBoardView(game, reg);
                view.la();
            }
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        } catch (NotBoundException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (AlreadyBoundException e) {
            e.printStackTrace();
        }


    }
}
