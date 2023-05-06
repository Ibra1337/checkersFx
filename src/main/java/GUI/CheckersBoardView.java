package GUI;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import resources.BoardLogic;

public class CheckersBoardView extends Application {

    private final int BOARD_SIZE = 8;
    private final int TILE_SIZE = 50;

    BoardLogic bl = new BoardLogic();

    public CheckersBoardView(BoardLogic bl) {
        this.bl = bl;
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        displayBoard(primaryStage , bl.getBoard());
    }

    private void clrBoard()
    {
        for (int i = 0 ; i < 8;i++)
            for (int j=0 ; j< 8 ;j++)
                if (bl.getBoard()[i][j]==3)
                    bl.getBoard()[i][j] = 0;
    }

    public void test(Stage stage , CircleButton b )
    {
        System.out.println("click");
        int[] p = {b.getX(), b.getY()};
        bl.disp();
        //make it displaying not remove ch
        clrBoard();
        displayBoard(stage , bl.putOnBoardAvMoves(p) , b);
    }

    public void displayBoard(Stage primaryStage , int[][] board , CircleButton performer)
    {
        GridPane root = new GridPane();
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
                    if (Math.abs(board[i][j] )<=2 )b.setOnAction( e -> test(primaryStage ,b ) );
                    if (board[i][j] == 3) b.setOnAction(e ->  playerMove(primaryStage , performer, b) ); ;
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

    public void displayBoard(Stage primaryStage , int[][] board)
    {
        GridPane root = new GridPane();
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
                    if (Math.abs(board[i][j] )<=2 )b.setOnAction( e -> test(primaryStage ,b ) );
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


    public void playerMove(Stage stage , CircleButton curr , CircleButton dest)
    {
        System.out.println("move");
        System.out.println(curr.getX() +":"+ curr.getY());
        System.out.println(dest.getX()+":" + curr.getY());
        bl.makeMove(curr.getX() , curr.getY() , dest.getX() , dest.getY()  );

        System.out.println("af");
        bl.disp();
        clrBoard();
        displayBoard(stage,bl.getBoard());

    }

    public void la ()
    {
        launch();
    }



}