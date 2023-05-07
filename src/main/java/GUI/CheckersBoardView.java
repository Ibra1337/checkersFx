package GUI;

import Server.Game;
import Server.IGame;
import Server.IMatchmaking;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import resources.BoardLogic;
import resources.IPlayer;
import resources.Player;

import java.rmi.AlreadyBoundException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

public class CheckersBoardView extends Application {

    private final int BOARD_SIZE = 8;
    private final int TILE_SIZE = 50;


    IGame game;

    Registry reg;
    int player =1;


    public CheckersBoardView() throws RemoteException {
        try {
            reg = LocateRegistry.getRegistry("192.168.220.1" , 1099);
            IPlayer p = new Player(player);
            IMatchmaking mm = (IMatchmaking) reg.lookup("mm");
            System.out.println(p.getId());
            mm.addPlayer(p);
            p  = (IPlayer) reg.lookup(p.getId());
            System.out.println(p.getId());
            while (!p.inGAme())
            {
                p  = (IPlayer) reg.lookup(p.getId());
                Thread.sleep(1000);
                System.out.println("w8 for oponent");
            }
            System.out.println("game found");
            game = (IGame) reg.lookup(p.getGameId());
            BoardLogic bl = game.getBl();

        } catch (RemoteException e) {
            throw new RuntimeException(e);
        } catch (NotBoundException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (AlreadyBoundException e) {
            e.printStackTrace();
        }

    };



    public CheckersBoardView(IGame game , Registry reg) throws RemoteException {
        this.game = game;
        this.reg = reg;
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        BoardLogic bl = game.getBl();
        displayBoard(primaryStage , bl.getBoard());
        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
            while (!Thread.interrupted())
            {
                try {
                    IGame g = (IGame) reg.lookup(game.getId());
                    if (g.getPlayersRound() == player)
                    {
                        displayBoard(primaryStage , g.getBl().getBoard());
                        while (g.getPlayersRound()!=player)
                        {
                            Thread.sleep(1000);
                            System.out.println("w8 for opponent movment");
                        }
                    }else
                    {
                        System.out.println("opponents move");
                    }
                    Thread.sleep(1000);
                } catch (RemoteException e) {
                    e.printStackTrace();
                } catch (NotBoundException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }
            }
        });
    }

    private void clrBoard() throws RemoteException {
        BoardLogic bl = game.getBl();
        for (int i = 0 ; i < 8;i++)
            for (int j=0 ; j< 8 ;j++)
                if (bl.getBoard()[i][j]==3)
                    bl.getBoard()[i][j] = 0;
    }

    public void test(Stage stage , CircleButton b ) throws RemoteException, NotBoundException {
        System.out.println("click");
        int[] p = {b.getX(), b.getY()};
        IGame g = (IGame) reg.lookup(game.getId() );
        System.out.println("=======================serv======================");
        g.getBl().disp();
        //make it displaying not remove ch
        clrBoard();
        displayBoard(stage , g.getBl().putOnBoardAvMoves(p) , b);
    }

    /**
     * displays board after clicking on piece
     * @param primaryStage
     * @param board
     * @param performer
     */
    public void displayBoard(Stage primaryStage , int[][] board , CircleButton performer)
    {
        GridPane root = new GridPane();
        for (int i = 0; i < board.length ; i++) {
            for (int j = 0; j < 8; j++) {
                Color bg = Color.GRAY;
                if (board[i][j] != 0) {
                    Color checkerColor = null;
                    if (board[i][j] == -1 )
                        checkerColor = Color.WHITE;
                    if (board[i][j] == 1 )
                        checkerColor = Color.BLACK;
                    if (board[i][j] == 3) {
                        checkerColor = Color.BLUE;
                        bg = Color.BLUE;
                    }
                    CircleButton b = new CircleButton( "" ,bg , checkerColor , i , j);
                    if (Math.abs(board[i][j] )<=2 )b.setOnAction( e -> {
                        try {
                            test(primaryStage ,b );
                        } catch (RemoteException ex) {
                            ex.printStackTrace();
                        } catch (NotBoundException ex) {
                            ex.printStackTrace();
                        }
                    });
                    if (board[i][j] == 3) b.setOnAction(e -> {
                        try {
                            playerMove(primaryStage , performer, b);
                        } catch (RemoteException ex) {
                            ex.printStackTrace();
                        } catch (NotBoundException ex) {
                            ex.printStackTrace();
                        }
                    }); ;
                    root.add( b,j ,i );
                } else {
                    Rectangle rect = new Rectangle(TILE_SIZE, TILE_SIZE);
                    if ((i + j) % 2 == 0) {
                        rect.setFill(Color.GREY);
                    } else {
                        rect.setFill(Color.BLACK);
                    }
                    root.add(rect, j, i);
                }
            }
        }

        Scene scene = new Scene(root, TILE_SIZE * BOARD_SIZE, TILE_SIZE * BOARD_SIZE);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Checkers Board");
        primaryStage.show();
    }

    /**
     * displays board at the begonning of round
     * @param primaryStage
     * @param board
     */
    public void displayBoard(Stage primaryStage , int[][] board) throws RemoteException {
        GridPane root = new GridPane();
        BoardLogic bl = game.getBl();
        for (int i = 0; i < bl.getBoard().length ; i++) {
            for (int j = 0; j < 8; j++) {
                Color bg = Color.GRAY;
                if (board[i][j] != 0) {
                    Color checkerColor = null;
                    if (board[i][j] == -1 )
                        checkerColor = Color.WHITE;
                    if (board[i][j] == 1 )
                        checkerColor = Color.BLACK;
                    if (board[i][j] == 3) {
                        checkerColor = Color.BLUE;
                        bg = Color.BLUE;
                    }
                    CircleButton b = new CircleButton( "" ,bg , checkerColor , i , j);
                    if (Math.abs(board[i][j] )<=2 )b.setOnAction( e -> {
                        try {
                            test(primaryStage ,b );
                        } catch (RemoteException ex) {
                            ex.printStackTrace();
                        } catch (NotBoundException ex) {
                            ex.printStackTrace();
                        }
                    });
                    if (board[i][j] == 3) ;
                    root.add( b,j ,i );
                } else {
                    Rectangle rect = new Rectangle(TILE_SIZE, TILE_SIZE);
                    if ((i + j) % 2 == 0) {
                        rect.setFill(Color.GREY);
                    } else {
                        rect.setFill(Color.BLACK);
                    }
                    root.add(rect, j, i);
                }
            }
        }

        Scene scene = new Scene(root, TILE_SIZE * BOARD_SIZE, TILE_SIZE * BOARD_SIZE);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Checkers Board");
        primaryStage.show();
    }


    public void playerMove(Stage stage , CircleButton curr , CircleButton dest) throws RemoteException, NotBoundException {
        game = (IGame) reg.lookup(game.getId());
        BoardLogic bl = game.getBl();
        displayBoard(stage ,game.getBl().getBoard() );
        System.out.println("move");
        System.out.println(curr.getX() +":"+ curr.getY());
        System.out.println(dest.getX()+":" + curr.getY());
        bl.makeMove(curr.getX() , curr.getY() , dest.getX() , dest.getY()  );

        System.out.println("af");
        clrBoard();
        game.setBL(bl);
        System.out.println(game.getId());
        System.out.println("==================NewBL===================");
        game.getBl().disp();
        game.playerRoundChange();
        reg.rebind(game.getId() , game);
        displayBoard(stage,bl.getBoard());

    }

    public void la ()
    {
        launch();
    }



}