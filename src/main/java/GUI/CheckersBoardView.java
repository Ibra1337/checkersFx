package GUI;

import Server.IGame;
import Server.IMatchmaking;
import javafx.application.Platform;
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
import java.rmi.registry.Registry;

public class CheckersBoardView extends Scene {

    private final int BOARD_SIZE = 8;
    private final int TILE_SIZE = 50;


    IGame game;

    Registry reg;
    int player =1;
    Stage stage;
    private boolean movePerformed = false;


    public CheckersBoardView(Stage stage , Registry reg ,int player) throws RemoteException {

        super( new GridPane());
        this.stage = stage;
        this.player = player;

        try {
            this.reg = reg;
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

            System.out.println("game found " + p.getGameId() );
            game = (IGame) reg.lookup(p.getGameId());
            BoardLogic bl = game.getBl();
            displayBoard(stage , bl.getBoard());
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        } catch (NotBoundException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (AlreadyBoundException e) {
            e.printStackTrace();
        }
        if (player ==1 )
            movePerformed=false;
        else
        {
            movePerformed = true;
            plat();
        }

    };


    public Stage getStage() {
        return stage;
    }

    private void clrBoard(BoardLogic bl) throws RemoteException {
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
        clrBoard(g.getBl());
        displayBoard(stage , g.getBl().putOnBoardAvMoves(p) , b);
    }

    /**
     * displays board after clicking on piece
     * @param primaryStage
     * @param board
     * @param performer
     */
    public void displayBoard(Stage primaryStage , int[][] board , CircleButton performer) throws RemoteException {
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
                    if (Math.abs(board[i][j] )<=2 )
                        if (board[i][j] == player)
                        {
                            b.setOnAction( e -> {
                                try {
                                    test(primaryStage, b);
                                } catch (RemoteException ex) {
                                    ex.printStackTrace();
                                } catch (NotBoundException ex) {
                                    ex.printStackTrace();
                                }
                            });

                        }else
                        {
                            b.setDisable(true);
                            b.setStyle( "-fx-opacity:  1 ; -fx-background-color: darkgray");
                                    /*
                                     -fx-opacity: 0.5; /* reduce opacity to make it darker */
                            /*-fx-background-color: /* add a background color of your choice );
                             */
                        }
                    if (board[i][j] == 3) b.setOnAction(e -> {
                        try {
                            playerMove(primaryStage , performer, b);
                        } catch (RemoteException ex) {
                            ex.printStackTrace();
                        } catch (NotBoundException ex) {
                            ex.printStackTrace();
                        } catch (InterruptedException ex) {
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
                    if (Math.abs(board[i][j] )<=2 )
                        if (board[i][j] == player && game.getPlayersRound()==player)
                        {
                            b.setOnAction( e -> {
                                try {
                                    test(primaryStage, b);
                                } catch (RemoteException ex) {
                                    ex.printStackTrace();
                                } catch (NotBoundException ex) {
                                    ex.printStackTrace();
                                }
                            });

                    }else
                        {
                            b.setDisable(true);
                            b.setStyle( "-fx-opacity:  1 ; -fx-background-color: darkgray");
                                    /*
                                     -fx-opacity: 0.5; /* reduce opacity to make it darker */
                                    /*-fx-background-color: /* add a background color of your choice );
                                     */
                        }
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

    public void displayBoard(Stage stage , int[][] board , boolean fromButton) throws NotBoundException, RemoteException, InterruptedException {


        waitForOpponentsMove(game,reg);
    }


    public void playerMove(Stage stage , CircleButton curr , CircleButton dest) throws RemoteException, NotBoundException, InterruptedException {
        game = (IGame) reg.lookup(game.getId());
        BoardLogic bl = game.getBl();
        displayBoard(stage ,game.getBl().getBoard() );
        System.out.println("move");
        System.out.println(curr.getX() +":"+ curr.getY());
        System.out.println(dest.getX()+":" + curr.getY());
        bl.makeMove(curr.getX() , curr.getY() , dest.getX() , dest.getY()  );

        System.out.println("af");
        clrBoard(bl);
        game.setBL(bl);
        System.out.println(game.getId());
        System.out.println("==================NewBL===================");
        game.getBl().disp();
        game.playerRoundChange();
        System.out.println("Waiting for move from "+ game.getPlayersRound() );
        reg.rebind(game.getId() , game);
        movePerformed = true;
        displayBoard(stage,bl.getBoard() );
       plat();
    }


    private void plat()
    {
        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    waitForOpponentsMove(game , reg);
                } catch (RemoteException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (NotBoundException e) {
                    e.printStackTrace();
                }
            }
        });
        Platform.runLater(t);
    }

    public void waitForOpponentsMove(IGame game , Registry reg) throws RemoteException, InterruptedException, NotBoundException {
        while (game.getPlayersRound() != player)
        {
            Thread.sleep(1000);
            game = (IGame) reg.lookup(game.getId());
        }
        movePerformed = false;
        System.out.println("opponent performed movelogeexit" +
                "");
        displayBoard(stage,game.getBl().getBoard());

    }

    public IGame getGame() {
        return game;
    }
}