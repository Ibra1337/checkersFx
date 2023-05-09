package GUI;

import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;

public class QueenButton extends APiece {

    private static final int WIDTH = 50;
    private static final int HEIGHT = 50;
    private static final int RADIUS = 15;
    private int x ;
    private int y ;
    private Rectangle rec ;

    public QueenButton(String text , Color checkerCol , Color fieldCol  , int x , int y) {
        setPrefSize(WIDTH, HEIGHT);
        this.x = x;
        this.y = y;
        // remove button padding

        StackPane pane = new StackPane();
        rec = new Rectangle(HEIGHT, WIDTH, checkerCol);
        pane.getChildren().add(rec);
        pane.getChildren().add(new Circle(RADIUS, fieldCol));
        pane.getChildren().add(new Circle(RADIUS/2 , Color.GOLD));
        Node n = pane;
        setGraphic(n);
        // make button background transparent
        setBackground(null);
        setStyle("-fx-background-color: transparent;");

        // remove button border
        setBorder(null);
        setStyle("-fx-border-width: 0;");
        setPadding(Insets.EMPTY);

        setOnAction(event -> {
            System.out.println("Button clicked!");
            // do something else here
        });
    }

    public Rectangle getRec() {
        return rec;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
}

