package GUI;

import GUI.EndGameStages.LoseStage;
import GUI.EndGameStages.DisconnectStage;
import GUI.EndGameStages.WinStage;
import Server.IGame;
import Server.IMatchmaking;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
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

public class CheckersBoardView extends Stage {

    private final int BOARD_SIZE = 8;
    private final int TILE_SIZE = 50;


    IGame game;

    Registry reg;
    int player =1;
    Stage stage;
    Thread t ;
    private boolean movePerformed = false;
    CheckersBoardView bw ;

    public CheckersBoardView(Stage stage , Registry reg ,int player) throws RemoteException {

        bw = this;
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
        } catch (NotBoundException | AlreadyBoundException | InterruptedException e) {
            e.printStackTrace();
        }
        t = new Thread(w8ForOponentTask());
        if (player ==1 )
            movePerformed=false;
        else
        {
            movePerformed = true;
            t.start();
        }

        setOnCloseRequest(ev -> {
            exit();
        });

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

    public void test(Stage stage , APiece b ) throws RemoteException, NotBoundException {
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
    public void displayBoard(Stage primaryStage , int[][] board , APiece performer) throws RemoteException {
        GridPane root = new GridPane();
        for (int i = 0; i < board.length ; i++) {
            for (int j = 0; j < 8; j++) {
                Color bg = Color.GRAY;
                if (board[i][j] != 0) {
                    Color checkerColor = null;
                    if (board[i][j] == -1 || board[i][j] ==-2)
                        checkerColor = Color.WHITE;
                    if (board[i][j] == 1  || board[i][j] ==2 )
                        checkerColor = Color.BLACK;
                    if (board[i][j] == 3) {
                        checkerColor = Color.BLUE;
                        bg = Color.BLUE;
                    }
                    APiece b ;
                    if(Math.abs(board[i][j] )== 2 )
                    {
                        b = new QueenButton("" , bg,checkerColor,i , j);
                    }else
                        b = new Man( "" ,bg , checkerColor , i , j);
                    if (Math.abs(board[i][j] )<=2 )
                        if (board[i][j] == player || board[i][j] == player*2)
                        {
                            b.setOnAction( e -> {
                                try {
                                    test(primaryStage, b);
                                } catch (RemoteException ex) {
                                    ex.printStackTrace();
                                    }
                                catch (NotBoundException ex) {
                                    ex.printStackTrace();
                                    }
                                });


                        }else
                        {
                            b.setDisable(true);
                            b.setStyle( "-fx-opacity:  1 ; -fx-background-color: darkgray");

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
        primaryStage.setOnCloseRequest(ev -> {
            exit();
        });
        primaryStage.show();
    }


    private void exit()
    {
        try {
            t.interrupt();
            game.setDisconnect(true);
            reg.rebind(game.getId() , game);
            System.out.println("eXitingGame Dissconnect");
            System.out.println(game.disconnectOccurred());
        } catch (RemoteException e) {
            e.printStackTrace();
        }

    }

    /**
     * displays board at the begonning of round
     * @param primaryStage
     * @param board
     */
    public void displayBoard(Stage primaryStage , int[][] board) throws RemoteException {
        System.out.println(game.getId());
        GridPane root = new GridPane();
        BoardLogic bl = game.getBl();
        for (int i = 0; i < bl.getBoard().length ; i++) {
            for (int j = 0; j < 8; j++) {
                Color bg = Color.GRAY;
                if (board[i][j] != 0) {
                    Color checkerColor = null;
                    if (board[i][j] == -1 || board[i][j] == -2)
                        checkerColor = Color.WHITE;
                    if (board[i][j] == 1 || board[i][j] == 2)
                        checkerColor = Color.BLACK;
                    if (board[i][j] == 3) {
                        checkerColor = Color.BLUE;
                        bg = Color.BLUE;
                    }
                    APiece b ;
                        if(Math.abs(board[i][j] )== 2 )
                        {
                            b = new QueenButton("" , bg,checkerColor,i , j);
                        }else
                            b = new Man( "" ,bg , checkerColor , i , j);
                    if (Math.abs(board[i][j] )<=2 )
                        if ((board[i][j] == player || board[i][j] == 2*player )  && game.getPlayersRound()==player)
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
        primaryStage.setOnCloseRequest(ev -> {
               exit();
        });

        primaryStage.setTitle("Checkers Board");
        primaryStage.show();


    }



    public void displayBoard(Stage stage , int[][] board , boolean fromButton) throws NotBoundException, RemoteException, InterruptedException {


        waitForOpponentsMove(game,reg);
    }


    public void playerMove(Stage stage , APiece curr , APiece dest) throws RemoteException, NotBoundException, InterruptedException {
        game = (IGame) reg.lookup(game.getId());
        BoardLogic bl = game.getBl();
        displayBoard(stage ,game.getBl().getBoard() );
        System.out.println("move");
        System.out.println(curr.getX() +":"+ curr.getY());
        System.out.println(dest.getX()+":" + curr.getY());
        bl.manMakeMove(curr.getX() , curr.getY() , dest.getX() , dest.getY()  );

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
        if (bl.endOfGame())
        {
            WinStage ws = new WinStage(new VBox());
            this.getStage().close();
            ws.show();

        }else if(game.disconnectOccurred())
        {
            handleDisconnect(game);
        } else {
            displayBoard(stage, bl.getBoard());
            t= new Thread(w8ForOponentTask());
            t.start();
        }
    }


    public Task<Integer> w8ForOponentTask()
    {
        Task<Integer> task = new Task<Integer>() {
            @Override
            protected Integer call() throws Exception {
                waitForOpponentsMove(game,reg);
                return 0;
            }
        } ;
        task.setOnScheduled( e->{
            try {
                waitForOpponentsMove(game,reg);
            } catch (RemoteException ex) {
                ex.printStackTrace();
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            } catch (NotBoundException ex) {
                ex.printStackTrace();
            }
        });
        return task;
    }


    public void waitForOpponentsMove(IGame game , Registry reg) throws RemoteException, InterruptedException, NotBoundException {
        while (game.getPlayersRound() != player&& !game.disconnectOccurred() && !t.isInterrupted())
        {
            game = (IGame) reg.lookup(game.getId());
            Thread.sleep(1000);
        }
        movePerformed = false;
        if (game.getBl().endOfGame() )
        {
            this.getStage().hide();
            LoseStage ls = new LoseStage(new VBox());
            ls.show();
        }else if (game.disconnectOccurred()) {
            handleDisconnect(game);
        }
        else {
            displayBoard(stage, game.getBl().getBoard());
        }
    }


    public void handleDisconnect(IGame game) throws NotBoundException, RemoteException {
        this.getStage().close();
        bw.getStage().close();
        DisconnectStage disS = new DisconnectStage();
        disS.show();
    }


    public IGame getGame() {
        return game;
    }
}