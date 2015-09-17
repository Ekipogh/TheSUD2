package ru.ekipogh.sud;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by dedov_d on 03.08.2015.
 */
public class LocationCategory implements Serializable {
    private String name;
    private Map<String, Script> newScripts;

    public LocationCategory(String name) {
        this.name = name;
        this.newScripts = new HashMap<>();
        newScripts.put("_onEnter", new Script("", true));
        newScripts.put("_onLeave", new Script("", true));
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

    public Map<String, Script> getNewScripts() {
        return newScripts;
    }

    public Script getNewScript(String scriptName) {
        return newScripts.get(scriptName);
    }

    public void setNewScript(String scriptName, Script script) {
        newScripts.put(scriptName, script);
    }

    public void removeNewScript(String scriptName) {
        this.newScripts.remove(scriptName);
    }
}