package ru.ekipogh.sud.controllers;

import javafx.fxml.FXML;

/**
 * Created by Ektril Pogh on 13.06.2018.
 */
public class LauncherController {
    @FXML
    public void initialize() {
        ScreenController.setController("launcher", this);
    }

    public void startEditor() {
        ScreenController.activate("editor");
    }

    public void startPlayer() {
        ScreenController.activate("player");
    }
}
