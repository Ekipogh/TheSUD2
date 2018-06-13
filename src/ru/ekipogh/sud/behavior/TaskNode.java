package ru.ekipogh.sud.behavior;

import ru.ekipogh.sud.Script;
import ru.ekipogh.sud.objects.GameCharacter;

import java.io.Serializable;

/**
 * Created by Ektril Pogh on 27.05.2018.
 */
public class TaskNode extends BTreeNode implements Serializable {
    public static final long serialVersionUID = 1L;
    private Script script;
    private GameCharacter character;

    public TaskNode() {
        this.script = new Script("", true);
    }

    public Script getScript() {
        return script;
    }

    @Override
    int update() {
        System.out.println(Script.run(script.getText(), character));
        return 0;
    }
}
