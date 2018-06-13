package ru.ekipogh.sud.controllers;

import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;

import java.io.File;

/**
 * Created by Ektril Pogh on 13.06.2018.
 */
public class LauncherController {
    public TextField filePath;

    public void chooseFile(ActionEvent actionEvent) {
        FileChooser chooser = new FileChooser();
        chooser.setTitle("Choose a game file");
        chooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("SUD game", "*.sud"),
                new FileChooser.ExtensionFilter("All Files", "*.*"));
        File file = chooser.showOpenDialog(((Button) actionEvent.getSource()).getScene().getWindow());
        if (file != null) {
            filePath.setText(file.getAbsolutePath());
        }
    }

    public void startEditor() {
        ScreenController.activate("editor");
    }

    public void startPlayer() {
        ScreenController.activate("player");
    }
}
