package ru.ekipogh.sud;

import org.fife.ui.rsyntaxtextarea.RSyntaxTextArea;

import javax.swing.*;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

class FindDialog extends JDialog {
    private JPanel contentPane;
    private JButton nextButton;
    private JButton cancelButton;
    private JTextField whatTextField;
    private JRadioButton upRadioButton;
    private JRadioButton downRadioButton;
    private JCheckBox caseCheckBox;
    private RSyntaxTextArea area;
    private boolean caseSensitive;
    private boolean direction;

    public FindDialog() {
        setContentPane(contentPane);
        setAlwaysOnTop(true);
        setResizable(false);
        setLocationRelativeTo(null);
        setModalityType(ModalityType.MODELESS);
        getRootPane().setDefaultButton(nextButton);

        caseSensitive = false;
        direction = true;

        pack();

        cancelButton.addActionListener(e -> onCancel());

        caseCheckBox.addActionListener(e -> setCaseSensitive());

        upRadioButton.addActionListener(e -> setUp());

        downRadioButton.addActionListener(e -> setDown());

        nextButton.addActionListener(e -> findNext());

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

    public void findNext() {
        int startFrom = area.getCaretPosition();
        String text = area.getText();
        String toSearch = whatTextField.getText();
        if (toSearch.equals(area.getSelectedText()) && !direction) {
            startFrom -= toSearch.length() + 1;
        }
        if (!caseSensitive) {
            text = text.toLowerCase();
            toSearch = toSearch.toLowerCase();
        }
        int offset;
        if (direction) {
            offset = text.indexOf(toSearch, startFrom);
        } else {
            offset = text.lastIndexOf(toSearch, startFrom);
        }
        if (offset > -1) {
            area.select(offset, toSearch.length() + offset);
            /*if (!direction) {
                area.setCaretPosition(area.getCaretPosition() - toSearch.length());
            }*/
        } else {
            JOptionPane.showMessageDialog(this, "Не удается найти \"" + toSearch + "\"!");
        }
    }

    private void setDown() {
        direction = true;
    }

    private void setUp() {
        direction = false;
    }

    private void setCaseSensitive() {
        this.caseSensitive = caseCheckBox.isSelected();
    }

    private void onCancel() {
// add your code here if necessary
        dispose();
    }

    public void setArea(RSyntaxTextArea area) {
        this.area = area;
    }
}
