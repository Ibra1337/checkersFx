package GUI;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.rmi.RemoteException;
import java.rmi.registry.Registry;

public class AskColor extends Stage {
    private Label colorLabel;
    private Button blackButton, whiteButton;
    private int res ;

    public AskColor(Stage stage, Registry reg) {
        initUI(stage , reg);
        System.out.println("ACCreated");
    }

    private void initUI(Stage stage , Registry reg) {
        // Create the label asking for the user's preferred color
        colorLabel = new Label("Choose a color:");

        // Create the black button
        blackButton = new Button("Black");
        blackButton.setOnAction(event -> {
            res =1;
            try {
                CheckersBoardView bw = new CheckersBoardView(this,reg,res);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        });

        // Create the white button
        whiteButton = new Button("White");
        whiteButton.setOnAction(event -> {
            res =-1;
            try {
                CheckersBoardView bw = new CheckersBoardView(this,reg,res);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        });

        // Create a horizontal box to hold the buttons
        HBox buttonsHBox = new HBox(10, blackButton, whiteButton);

        // Create a vertical box to hold the label and buttons
        VBox vbox = new VBox(10, colorLabel, buttonsHBox);
        vbox.setPadding(new Insets(10));

        // Create the scene and set it to the stage
        Scene scene = new Scene(vbox, 300, 200);
        this.setScene(scene);

        this.show();
    }

    public int getColor() {
        return res;
    }
}