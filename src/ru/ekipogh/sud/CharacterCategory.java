package ru.ekipogh.sud;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by dedov_d on 03.08.2015.
 */
public class CharacterCategory implements Serializable {
    private String name;
    private Map<String, Script> newScripts;

    public CharacterCategory(String name) {
        this.name = name;
        this.newScripts = new HashMap<>();
        this.newScripts.put("_onPlayerArrive", new Script("", true));
        this.newScripts.put("_onPlayerLeave", new Script("", true));
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "CC:" + this.name;
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
