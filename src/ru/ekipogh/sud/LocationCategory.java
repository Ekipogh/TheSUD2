package ru.ekipogh.sud;

import java.io.Serializable;
import java.util.HashMap;

/**
 * Created by dedov_d on 03.08.2015.
 */
public class LocationCategory implements Serializable {
    private String name;
    private HashMap<String, String> scripts;

    public LocationCategory(String name) {
        this.name = name;
        this.scripts = new HashMap<>();
        scripts.put("_onEnter", "");
        scripts.put("_onLeave", "");
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setScript(String scriptName, String scriptText) {
        scripts.put(scriptName, scriptText);
    }

    public HashMap<String, String> getScripts() {
        return scripts;
    }

    public String getScript(String scriptName) {
        return scripts.get(scriptName);
    }

    @Override
    public String toString() {
        return "LC: " + this.name;
    }

    public void addScript(String scriptName, String scriptText) {
        this.scripts.put(scriptName, scriptText);
    }

    public void deleteScript(String scriptName) {
        this.scripts.remove(scriptName);
    }
}
