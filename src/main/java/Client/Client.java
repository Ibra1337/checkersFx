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
            CheckersBoardView bw = new CheckersBoardView();
            bw.la();
        } catch (RemoteException e) {
            e.printStackTrace();
        }


    }
}
