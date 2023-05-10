package GUI.EndGameStages;

import javafx.application.Platform;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class DisconnectStage extends Stage {

    public DisconnectStage() {
        VBox root = new VBox();
        root.setAlignment(Pos.CENTER);
        root.setSpacing(20);

        Text message = new Text("Opponent disconnected :(");
        Button continueButton = new Button("Continue");
        continueButton.setOnAction(event -> close());

        root.getChildren().addAll(message, continueButton);

        Scene scene = new Scene(root, 300, 200);
        setScene(scene);
        setTitle("Opponent Disconnected");
        setOnCloseRequest(event -> {
            event.consume();
            Platform.exit();
        });

        this.show();
    }
}
