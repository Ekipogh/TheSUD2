package ru.ekipogh.sud.objects;

import ru.ekipogh.sud.Script;
import ru.ekipogh.sud.behavior.BehaviorTree;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by ekipogh on 09.05.2016.
 * licensed under WTFPL
 */
public class GameObjectCategory implements Serializable {
    public static final long serialVersionUID = 1L;
    protected String name;
    Map<String, Script> scripts;
    private BehaviorTree btree;

    GameObjectCategory(String name) {
        this.name = name;
        scripts = new HashMap<>();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public void setScriptName(String newScriptName, String oldScriptName) {
        Script script = scripts.get(oldScriptName);
        scripts.remove(oldScriptName);
        scripts.put(newScriptName, script);
    }

    public BehaviorTree getBtree() {
        return btree;
    }

    public void restoreBTree() {
        this.btree = new BehaviorTree();
    }
}
