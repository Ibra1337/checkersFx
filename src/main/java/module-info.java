module com.example.checkersfx {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.rmi;


    opens GUI to javafx.fxml;
    exports GUI;
    exports Server;
    exports Client;
    exports GUI.EndGameStages;
    opens GUI.EndGameStages to javafx.fxml;
}