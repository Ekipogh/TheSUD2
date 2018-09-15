package ru.ekipogh.sud.behavior;

import javax.swing.tree.TreeNode;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;

/**
 * Created by Ektril Pogh on 24.03.2018.
 */
public abstract class BTreeNode implements Serializable {
    public static final long serialVersionUID = 1L;
    ArrayList<BTreeNode> children = new ArrayList<>();
    private BTreeNode parent = null;
    String name;

    final static int SUCCESS = 0;
    final static int RUNNING = 2;
    final static int FAIL = 1;

    abstract int update();

    public void addChild(BTreeNode child) {
        child.parent = this;
        children.add(child);
    }

    public void removeRecurcivly(BTreeNode node) {
        children.remove(node);
        children.forEach(child -> removeRecurcivly(node));
    }
}
