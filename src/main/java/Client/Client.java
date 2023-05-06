package Client;

import GUI.CheckersBoardView;
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
            String s = "kupa";

            mm.addPlayer(p);
            p  = (IPlayer) reg.lookup(p.getId().toString());
            System.out.println(p.inGAme());
            while (!p.inGAme())
            {
                Thread.sleep(1000);
                System.out.println("w8 for oponent");
            }
            CheckersBoardView view = new CheckersBoardView();
            view.la();
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
