package ru.ekipogh.sud.behavior;

/**
 * Created by Ektril Pogh on 24.03.2018.
 */
public class Sequence extends BTreeNode {
    @Override
    public int update() {
        for (BTreeNode child :
                children) {
            int status = child.update();
            if (status == RUNNING) {
                return RUNNING;
            } else if (status == FAIL) {
                return FAIL;
            }
        }
        return SUCCESS;
    }
}
