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

    @Override
    public void start(Stage stage) throws Exception {

        CheckersBoardView bw = new CheckersBoardView(stage , 1);
    }
}
