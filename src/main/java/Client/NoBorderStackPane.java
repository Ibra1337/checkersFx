package Client;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class NoBorderStackPane extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        StackPane root = new StackPane();
        root.setStyle("-fx-border-width: 0;");
        Scene scene = new Scene(root, 300, 200, Color.TRANSPARENT);
        primaryStage.setScene(scene);
        primaryStage.setTitle("No Border StackPane");
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
