package ru.ekipogh.sud;

import javax.swing.tree.DefaultMutableTreeNode;
import java.util.function.Consumer;

/**
 * Created by dedov_d on 08.09.2015.
 */
public class SudTreeNode extends DefaultMutableTreeNode {
    private final Consumer lambda;

    public SudTreeNode(Object userObject, Consumer action) {
        super(userObject);
        this.lambda = action;
    }

    public void invoke() {
        lambda.accept(null);
    }
}
