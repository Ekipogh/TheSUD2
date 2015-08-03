package ru.ekipogh.sud;

import java.util.HashMap;

/**
 * Created by dedov_d on 03.08.2015.
 */
public class LocationCategory {
    private String name;
    private HashMap<String, String> scripts;

    public LocationCategory(String name) {
        this.name = name;
        this.scripts = new HashMap<>();
        scripts.put("onEnter", "");
        scripts.put("onLeave", "");
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
}
