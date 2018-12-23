package ru.ekipogh.sud.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TreeView;
import javafx.scene.web.WebView;
import ru.ekipogh.sud.GameFile;
import ru.ekipogh.sud.objects.GameCharacter;

/**
 * Created by Ektril Pogh on 13.06.2018.
 */
public class PlayerController {
    public WebView output;
    public TreeView items;
    public TreeView characters;
    public Label playerName;
    public TextArea playerDescription;
    private GameFile gameFile;
    private GameCharacter player;

    @FXML
    public void initialize() {
        ScreenController.setController("itemContainer", this);
    }

    public void openGame() {
        this.gameFile = GameFile.open();
        gamePlayerInit();
    }

    private void gamePlayerInit() {
        if (gameFile != null) {
            // Player
            this.player = gameFile.getPlayer();
            this.playerName.setText(player.getName());
            this.playerDescription.setText(player.getDescription());
        }
    }
}
