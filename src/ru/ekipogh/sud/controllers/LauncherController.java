package ru.ekipogh.sud.controllers;

/**
 * Created by Ektril Pogh on 13.06.2018.
 */
public class LauncherController {
    public void startEditor() {
        ScreenController.activate("editor");
    }

    public void startPlayer() {
        ScreenController.activate("player");
    }
}
