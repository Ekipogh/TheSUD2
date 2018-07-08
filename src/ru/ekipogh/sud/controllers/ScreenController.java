package ru.ekipogh.sud.controllers;

import javafx.scene.Scene;
import javafx.scene.layout.Pane;

import java.util.HashMap;

/**
 * Created by Ektril Pogh on 13.06.2018.
 */
public class ScreenController {
    private static HashMap<String, Pane> screenMap = new HashMap<>();

    public static Scene getMain() {
        return main;
    }

    private static Scene main;

    public static void setMain(Scene scene) {
        main = scene;
    }

    public static void addScreen(String name, Pane pane) {
        screenMap.put(name, pane);
    }

    static void activate(String name) {
        main.setRoot(screenMap.get(name));
    }
}
