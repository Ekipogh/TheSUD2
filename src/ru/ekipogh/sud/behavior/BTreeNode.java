package ru.ekipogh.sud.behavior;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by Ektril Pogh on 24.03.2018.
 */
public abstract class BTreeNode implements Serializable {
    public static final long serialVersionUID = 1L;
    ArrayList<BTreeNode> children = new ArrayList<>();
    private BTreeNode parent = null;

    final static int SUCCESS = 0;
    final static int RUNNING = 2;
    final static int FAIL = 1;

    abstract int update();

    public void addChild(BTreeNode child) {
        child.parent = this;
        children.add(child);
    }

    public ArrayList<BTreeNode> getChildren() {
        return children;
    }

    public BTreeNode getParent() {
        return parent;
    }
}
