package ru.ekipogh.sud;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import ru.ekipogh.sud.controllers.ScreenController;
import ru.ekipogh.sud.frames.EditorFrame;

public class Main extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {
        Pane launcher = FXMLLoader.load(getClass().getResource("fxml/launcher.fxml"));
        Pane editor = FXMLLoader.load(getClass().getResource("fxml/editor.fxml"));
        Pane player = FXMLLoader.load(getClass().getResource("fxml/player.fxml"));

        Scene scene = new Scene(launcher);
        ScreenController.setMain(scene);
        ScreenController.addScreen("launcher", launcher);
        ScreenController.addScreen("editor", editor);
        ScreenController.addScreen("player", player);
        primaryStage.setTitle("The SUD2");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }

    // TODO: remove this
    public static EditorFrame editor;

}
