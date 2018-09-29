package ru.ekipogh.sud.behavior;

import org.mozilla.javascript.Undefined;
import ru.ekipogh.sud.Script;
import ru.ekipogh.sud.objects.GameObject;

import java.io.Serializable;

/**
 * Created by Ektril Pogh on 27.05.2018.
 */
public class TaskNode extends BTreeNode implements Serializable {
    public static final long serialVersionUID = 1L;
    private Script script;
    private GameObject character;


    public TaskNode(GameObject character) {
        super();
        this.character = character;
        this.script = new Script("", true);
    }

    public Script getScript() {
        return script;
    }

    public void setScript(Script script) {
        this.script = script;
    }

    @Override
    int update() {
        Object result = Script.run(script.getText(), character);
        if (result.getClass() != Undefined.class) {
            return ((Double) result).intValue();
        }
        return 1;
    }

    @Override
    public String toString() {
        return "Task";
    }
}
