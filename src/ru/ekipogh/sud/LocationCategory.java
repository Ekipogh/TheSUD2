package ru.ekipogh.sud;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by dedov_d on 03.08.2015.
 */
public class LocationCategory implements Serializable {
    private String name;
    private Map<String, Script> scripts;

    public LocationCategory(String name) {
        this.name = name;
        this.scripts = new HashMap<>();
        this.scripts.put("_onEnter", new Script("", true));
        this.scripts.put("_onLeave", new Script("", true));
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "LC: " + this.name;
    }

    public Map<String, Script> getScripts() {
        return scripts;
    }

    public Script getScript(String scriptName) {
        return scripts.get(scriptName);
    }

    public void setScript(String scriptName, Script script) {
        scripts.put(scriptName, script);
    }

    public void removeScript(String scriptName) {
        this.scripts.remove(scriptName);
    }
}