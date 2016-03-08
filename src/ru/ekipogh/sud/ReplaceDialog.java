package ru.ekipogh.sud;

import org.fife.ui.rsyntaxtextarea.RSyntaxTextArea;

import javax.swing.*;
import java.awt.event.*;

public class ReplaceDialog extends JDialog {
    private boolean caseSensitive;
    private JPanel contentPane;
    private JButton findButton;
    private JButton buttonCancel;
    private JTextField whatTextField;
    private JTextField withTextField;
    private JButton replaceButton;
    private JButton replaceAllButton;
    private JCheckBox caseSensitiveCheckBox;
    private RSyntaxTextArea area;

    public ReplaceDialog() {
        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(findButton);
        pack();

        caseSensitive = false;

        buttonCancel.addActionListener(e -> onCancel());
        caseSensitiveCheckBox.addActionListener(e -> setCaseSensitive());
        findButton.addActionListener(e -> find());
        replaceButton.addActionListener(e -> replace());
        replaceAllButton.addActionListener(e -> replaceAll());

// call onCancel() when cross is clicked
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                onCancel();
            }
        });

// call onCancel() on ESCAPE
        contentPane.registerKeyboardAction(e -> onCancel(), KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
    }

    private void replaceAll() {
        String text = area.getText();
        String what = whatTextField.getText();
        String with = withTextField.getText();
        text = text.replaceAll(what, with);
        area.setText(text);
    }

    private void replace() {
        if (area.getSelectedText() != null) {
            area.replaceSelection(withTextField.getText());
        } else {
            find();
        }
    }

    private void find() {
        int startFrom = area.getCaretPosition();
        String text = area.getText();
        String toSearch = whatTextField.getText();
        if (!caseSensitive) {
            text = text.toLowerCase();
            toSearch = toSearch.toLowerCase();
        }
        int offset = text.indexOf(toSearch, startFrom);
        if (offset > -1) {
            area.select(offset, offset + toSearch.length());
        }
    }

    private void setCaseSensitive() {
        caseSensitive = caseSensitiveCheckBox.isSelected();
    }

    private void onCancel() {
// add your code here if necessary
        dispose();
    }

    public void setArea(RSyntaxTextArea area) {
        this.area = area;
    }
}
