package ru.ekipogh.sud.controllers;

import javafx.embed.swing.SwingNode;
import javafx.fxml.FXML;
import org.fife.ui.rsyntaxtextarea.RSyntaxTextArea;

/**
 * Created by Ektril Pogh on 13.06.2018.
 */
public class EditorController {
    public SwingNode commonScriptNode;
    public SwingNode gameScriptsNode;
    public SwingNode timerScriptNode;

    @FXML
    public void initialize() {
        commonScriptNode.setContent(new RSyntaxTextArea());
        gameScriptsNode.setContent(new RSyntaxTextArea());
        timerScriptNode.setContent(new RSyntaxTextArea());
    }
}
