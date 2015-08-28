package ru.ekipogh.sud;

import java.io.Serializable;
import java.util.HashMap;

/**
 * Created by dedov_d on 03.08.2015.
 */
public class CharacterCategory implements Serializable {
    private String name;
    private HashMap<String, String> scripts;

    public CharacterCategory(String name) {
        this.name = name;
        this.scripts = new HashMap<>();
        this.scripts.put("_onPlayerArrive", "");
        this.scripts.put("_onPlayerLeave", "");
    }

    public String getName() {
        return name;
    }

    public HashMap<String, String> getScripts() {
        return scripts;
    }

    public void deleteScript(String scriptName) {
        this.scripts.remove(scriptName);
    }

    public void addScript(String scriptName, String scriptText) {
        this.scripts.put(scriptName, scriptText);
    }


    public String getScript(String scriptName) {
        return this.scripts.get(scriptName);
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setScript(String scriptName, String scriptText) {
        this.scripts.put(scriptName, scriptText);
    }

    @Override
    public String toString() {
        return "CC:" + this.name;
    }
}
