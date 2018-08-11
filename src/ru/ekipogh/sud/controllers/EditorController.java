package ru.ekipogh.sud.controllers;

import javafx.embed.swing.SwingNode;
import javafx.event.ActionEvent;
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
    public TextField timerStep;
    public SwingNode timerScriptNode;

    private GameFile gameFile;

    @FXML
    public void initialize() {
        initScriptNode.setContent(new RSyntaxTextArea());
        gameScriptsNode.setContent(new RSyntaxTextArea());
        timerScriptNode.setContent(new RSyntaxTextArea());
        gameScriptsList.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) ->
                selectGameScript(newValue));
        timersList.getSelectionModel().selectedItemProperty().addListener(((observable, oldValue, newValue) ->
                selectTimer(newValue)));
        gameFile = new GameFile();
    }

    private void selectGameScript(String scriptName) {
        ((RSyntaxTextArea) gameScriptsNode.getContent()).setText(gameFile.getCommonScripts().get(scriptName).getText());
    }

    private void selectTimer(SudTimer timer) {
        if (timer != null) {
            String name = timer.getTimerName();
            if (name != null) {
                int step = timer.getStep();
                String script = timer.getScript();
                timerName.setText(name);
                timerStep.setText(String.valueOf(step));
                ((RSyntaxTextArea) timerScriptNode.getContent()).setText(script);
            }
        }
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
        gameDescription.setText(gameFile.getGameStartMessage());
        ((RSyntaxTextArea) initScriptNode.getContent()).setText(gameFile.getInitScript());
        gameScriptsList.getItems().addAll(gameFile.getCommonScripts().keySet());
        timersList.getItems().addAll(gameFile.getTimers());
    }

    private void cleanEditor() {
        gameScriptsList.getItems().clear();
        timersList.getItems().clear();
    }

    public void saveGameName() {
        String name = gameName.getText();
        gameFile.setGameName(name);
    }

    public void saveGameDescription() {
        String description = gameDescription.getText();
        gameFile.setGameStartMessage(description);
    }

    public void saveInitScript() {
        String initScript = ((RSyntaxTextArea) initScriptNode.getContent()).getText();
        gameFile.setInitScript(initScript);
    }

    public void saveGameScript() {
        String gameScriptName = gameScriptsList.getSelectionModel().getSelectedItem();
        if (gameScriptName != null) {
            String gameScriptText = ((RSyntaxTextArea) gameScriptsNode.getContent()).getText();
            gameFile.setCommonScript(gameScriptName, gameScriptText);
        }
    }

    public void saveTimerName() {
        SudTimer timer = timersList.getSelectionModel().getSelectedItem();
        if (timer != null) {
            String name = timerName.getText();
            timer.setTimerName(name);
            timersList.refresh();
        }
    }

    public void saveTimerStep() {
        SudTimer timer = timersList.getSelectionModel().getSelectedItem();
        if (timer != null) {
            String step = timerStep.getText();
            timer.setStep(Integer.parseInt(step));
            timersList.refresh();
        }
    }

    public void saveTimerScript() {
        SudTimer timer = timersList.getSelectionModel().getSelectedItem();
        if (timer != null) {
            String script = ((RSyntaxTextArea) timerScriptNode.getContent()).getText();
            timer.setScript(script);
            timersList.refresh();
        }
    }

    public void addTimer() {
        SudTimer timer = new SudTimer();
        gameFile.getTimers().add(timer);
        timersList.getItems().setAll(gameFile.getTimers());
    }

    public void removeTimer() {
        SudTimer timer = timersList.getSelectionModel().getSelectedItem();
        if (timer != null) {
            gameFile.getTimers().remove(timer);
            timersList.getItems().setAll(gameFile.getTimers());
        }
    }
}
