package ru.ekipogh.sud.behavior;


import javax.swing.tree.TreeNode;
import java.util.Collection;
import java.util.Collections;
import java.util.Enumeration;

/**
 * Created by Ektril Pogh on 04.03.2018.
 */
public class BehaviorTree extends BTreeNode {
    @Override
    public int update() {
        return children.get(0).update();
    }

    @Override
    public void addChild(BTreeNode child) {
        if (children.size() != 0) {
            System.err.println("Root node: to many children");
            return;
        }
        children.add(child);

    }
}
