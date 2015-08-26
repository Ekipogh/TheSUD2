package ru.ekipogh.sud;

import java.util.HashMap;

/**
 * Created by dedov_d on 03.08.2015.
 */
public class CharacterCategory {
    private String name;
    private HashMap<String, String> scripts;

    public CharacterCategory(String name) {
        this.name = name;
        this.scripts = new HashMap<>();
        this.scripts.put("_onPlayerArrive", "");
        this.scripts.put("_onPlayerLeave", "");
    }
}
