package ru.ekipogh.sud.behavior;

import javax.swing.tree.TreeNode;
import java.util.Collections;
import java.util.Enumeration;

/**
 * Created by Ektril Pogh on 24.03.2018.
 */
public class Selector extends BTreeNode {
    @Override
    public int update() {
        for (BTreeNode child :
                children) {
            int status = child.update();
            if (status == RUNNING) {
                return RUNNING;
            } else if (status == SUCCESS) {
                return SUCCESS;
            }
        }
        return FAIL;
    }
}
