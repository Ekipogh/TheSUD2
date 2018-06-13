package ru.ekipogh.sud.behavior;

import java.io.Serializable;

/**
 * Created by Ektril Pogh on 24.03.2018.
 */
public class Selector extends BTreeNode implements Serializable {
    public static final long serialVersionUID = 1L;

    @Override
    public int update() {
        for (BTreeNode child : children) {
            int status = child.update();
            if (status == RUNNING) {
                return RUNNING;
            } else if (status == SUCCESS) {
                return SUCCESS;
            }
        }
        return FAIL;
    }

    @Override
    public String toString() {
        return "Selector";
    }
}
