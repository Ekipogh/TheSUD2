package ru.ekipogh.sud;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by dedov_d on 03.08.2015.
 */
public class CharacterCategory implements Serializable, Cloneable {

    public static final long serialVersionUID = -6814041336950848981L;
    private String name;
    private Map<String, Script> scripts;

    public CharacterCategory(String name) {
        this.name = name;
        this.scripts = new HashMap<>();
        this.scripts.put("_onPlayerArrive", new Script("", true));
        this.scripts.put("_onPlayerLeave", new Script("", true));
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

    public Object clone() throws CloneNotSupportedException { //aint an option (
        Object toReturn = super.clone();
//       ((CharacterCategory) toReturn).scripts = new HashMap<>();
        for (Map.Entry<String, Script> entry : scripts.entrySet()) {
            ((CharacterCategory) toReturn).setScript(entry.getKey(), new Script(entry.getValue().getText(), entry.getValue().isEnabled()));
        }
        return toReturn;
    }
}
