package ru.ekipogh.sud.behavior;

import javax.swing.tree.TreeNode;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;

/**
 * Created by Ektril Pogh on 24.03.2018.
 */
public abstract class BTreeNode implements TreeNode {
    ArrayList<BTreeNode> children = new ArrayList<>();
    BTreeNode parent = null;

    int SUCCESS = 0;
    int RUNNING = 2;
    int FAIL = 1;

    abstract int update();

    public void addChild(BTreeNode child) {
        children.add(child);
    }


    @Override
    public TreeNode getChildAt(int childIndex) {
        return children.get(childIndex);
    }

    @Override
    public int getChildCount() {
        //return children.size();
        return 1;
    }

    @Override
    public TreeNode getParent() {
        return parent;
    }

    @Override
    public int getIndex(TreeNode node) {
        return children.indexOf(node);
    }

    @Override
    public boolean getAllowsChildren() {
        return true;
    }

    @Override
    public boolean isLeaf() {
        return false;
    }

    @Override
    public Enumeration children() {
        return Collections.enumeration(children);
    }
}
