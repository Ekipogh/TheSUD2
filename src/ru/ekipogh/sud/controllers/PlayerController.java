package ru.ekipogh.sud.controllers;

import javafx.fxml.FXML;

/**
 * Created by Ektril Pogh on 13.06.2018.
 */
public class PlayerController {
    @FXML
    public void initialize() {
        ScreenController.setController("itemContainer", this);
    }
}
