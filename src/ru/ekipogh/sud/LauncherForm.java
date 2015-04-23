package ru.ekipogh.sud;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by dedov_d on 23.04.2015.
 */
public class LauncherForm  extends JFrame{
    private JPanel rootPanel;
    private JButton editorButton;

    public LauncherForm(){
        super("TheSUD 2");

        setContentPane(rootPanel);

        pack();
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        editorButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                LauncherForm.this.setVisible(false);
                Main.editor = new EditorForm();
            }
        });

        setVisible(true);
    }
}
