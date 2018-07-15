package ru.ekipogh.sud.controllers;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.embed.swing.SwingNode;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.FileChooser;
import javafx.stage.Window;
import org.fife.ui.rsyntaxtextarea.RSyntaxTextArea;
import ru.ekipogh.sud.GameFile;
import ru.ekipogh.sud.SudTimer;

import java.io.File;

/**
 * Created by Ektril Pogh on 13.06.2018.
 */
public class EditorController {
    //Game tab
    //Common tab
    public TextField gameName;
    public TextArea gameDescription;
    //Init script tab
    public SwingNode initScriptNode;
    //Game scripts tab
    public ListView<String> gameScriptsList;
    public SwingNode gameScriptsNode;
    //Timers tab
    public ListView<SudTimer> timersList;
    public TextField timerName;
    public TextField timeStep;
    public SwingNode timerScriptNode;

    private GameFile gameFile;

    @FXML
    public void initialize() {
        initScriptNode.setContent(new RSyntaxTextArea());
        gameScriptsNode.setContent(new RSyntaxTextArea());
        timerScriptNode.setContent(new RSyntaxTextArea());
        gameScriptsList.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            selectGameScript(newValue);
        });
    }

    private void selectGameScript(String scriptName) {
        ((RSyntaxTextArea) gameScriptsNode.getContent()).setText(gameFile.getCommonScripts().get(scriptName).getText());
    }

    private String chooseFile(Window window) {
        FileChooser chooser = new FileChooser();
        chooser.setTitle("Open a game file");
        chooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("SUD game", "*.sud"),
                new FileChooser.ExtensionFilter("All files", "*.*"));
        File file = chooser.showOpenDialog(window);
        if (file != null) {
            return file.getAbsolutePath();
        }
        return null;
    }

    public void openFile() {
        String gameFilePath = chooseFile(ScreenController.getMain().getWindow());
        if (gameFilePath != null) {
            gameFile = GameFile.open(gameFilePath);
            editorInit();
        }
    }

    private void editorInit() {
        cleanEditor();
        gameName.setText(gameFile.getGameName());
        ((RSyntaxTextArea) initScriptNode.getContent()).setText(gameFile.getInitScript());
        gameScriptsList.getItems().addAll(gameFile.getCommonScripts().keySet());
        timersList.getItems().addAll(gameFile.getTimers());
    }

    private void cleanEditor() {
        gameScriptsList.getItems().clear();
        timersList.getItems().clear();
    }
}
