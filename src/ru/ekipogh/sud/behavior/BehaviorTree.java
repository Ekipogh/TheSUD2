package ru.ekipogh.sud.behavior;

import java.io.Serializable;

/**
 * Created by Ektril Pogh on 04.03.2018.
 */
public class BehaviorTree extends BTreeNode implements Serializable {
    public static final long serialVersionUID = 1L;

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

    @Override
    public String toString() {
        return "Behavior Root";
    }
}
