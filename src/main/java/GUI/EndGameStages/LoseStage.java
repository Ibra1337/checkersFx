package GUI.EndGameStages;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class LoseStage extends Stage {

    public LoseStage(VBox root) {
        Text text = new Text("You lose 😔");
        text.setFont(Font.font("Arial", 24));
        ImageView imageView = new ImageView(new Image("https://emojipedia-us.s3.dualstack.us-west-1.amazonaws.com/thumbs/240/apple/271/white-frowning-face_2639-fe0f.png"));
        imageView.setFitWidth(50);
        imageView.setFitHeight(50);

        Button continueButton = new Button("Continue");
        continueButton.setOnAction(event -> System.exit(0));

        root = new VBox(text, continueButton);
        root.setSpacing(20);
        root.setStyle("-fx-alignment: center; -fx-padding: 20px;");

        Scene scene = new Scene(root);

        this.setScene(scene);
        this.show();

    }
}
