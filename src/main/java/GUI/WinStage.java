package GUI;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class WinStage extends Scene {

    public WinStage(VBox root) {
        super(root);

        Text winText = new Text("You win! 🏆");
        winText.setFont(Font.font("Arial", 24));

        Button continueButton = new Button("Continue");
        continueButton.setOnAction(event -> System.exit(0));

        root = new VBox(winText, continueButton);
        root.setSpacing(20);
        root.setStyle("-fx-alignment: center; -fx-padding: 20px;");




    }
}
