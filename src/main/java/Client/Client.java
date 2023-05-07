package Client;

import GUI.CheckersBoardView;
import Server.IGame;
import Server.IMatchmaking;
import javafx.application.Application;
import javafx.stage.Stage;
import resources.IPlayer;
import resources.Player;

import java.rmi.AlreadyBoundException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class Client extends Application {


    public static void main(String[] args) {


        launch();

    }

    public static void waitForRound( IGame g , Registry reg ,int col) throws RemoteException, InterruptedException, NotBoundException {
        while ( g.getPlayersRound() != col)
        {
            g = getGame(g , reg);
            System.out.println("waiting for my round");
            Thread.sleep(1000);

        }
    }

//    public static void round(CheckersBoardView bw , int color , Stage stage , Registry reg ) throws RemoteException, InterruptedException, NotBoundException {
//        IGame g = getGame(bw.getGame() , reg);
//        waitForRound(g , reg , color);
//        bw.displayBoard(stage , g.getBl().getBoard());
//    }

    public static IGame getGame(IGame game , Registry reg) throws RemoteException, NotBoundException {
        return (IGame) reg.lookup(game.getId());
    }
    @Override
    public void start(Stage stage) throws Exception {
        int player = 1;
        Registry reg = LocateRegistry.getRegistry("192.168.220.1" , 1099);
        CheckersBoardView bw = new CheckersBoardView(stage , reg , player);


    }
}
