package ru.ekipogh.sud;

import javax.swing.tree.DefaultMutableTreeNode;
import java.util.function.Consumer;

/**
 * Created by ekipogh on 08.09.2015.
 */
public class SudTreeNode extends DefaultMutableTreeNode {
    private final Consumer lambda;
    private String text;

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    private int count = 1;

    public void setText(String text) {
        this.text = text;
    }

    public SudTreeNode(Object userObject, Consumer action) {
        super(userObject);
        this.text = count > 1 ? userObject.toString() + " (" + count + ")" : userObject.toString();
        this.lambda = action;
    }

    public void invoke() {
        lambda.accept(null);
    }

    public String toString() {
        return count > 1 ? text + " (" + count + ")" : text;
    }
}
