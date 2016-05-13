package ru.ekipogh.sud.frames;

import javax.swing.*;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;

public class NewProjectDialog extends JDialog {
    private JPanel contentPane;
    private JButton buttonOK;
    private JButton buttonCancel;
    private JTextField destDirField;
    private JButton chooseButton;
    private JTextField gameNameField;
    private EditorFrame editorFrame;

    public NewProjectDialog(EditorFrame editorFrame) {
        this.editorFrame = editorFrame;
        setTitle("Новый проект");
        setContentPane(contentPane);
        setAlwaysOnTop(true);
        setResizable(false);
        setLocationRelativeTo(editorFrame);
        setModalityType(ModalityType.MODELESS);
        pack();
        getRootPane().setDefaultButton(buttonOK);
        setVisible(true);

        buttonOK.addActionListener(e -> onOK());

        buttonCancel.addActionListener(e -> onCancel());

        chooseButton.addActionListener(e -> onChoose());

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

    private void onChoose() {
        JFileChooser chooser = new JFileChooser();
        chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        chooser.setAcceptAllFileFilterUsed(false);
        int result = chooser.showOpenDialog(this);
        if (result == JFileChooser.APPROVE_OPTION) {
            this.destDirField.setText(chooser.getSelectedFile().getAbsolutePath());
        } else {
            onCancel();
        }
    }

    private void onOK() {
        //создать папку если она не существует.
        File destDir = new File(destDirField.getText());
        if (destDir.mkdirs()) {
            System.out.println("Created directory " + destDir.getAbsolutePath());
        }
        //создаем папки /scripts /data /saves
        File scripts = new File(destDir.getAbsolutePath() + "/scripts");
        File data = new File(destDir.getAbsolutePath() + "/data");
        File saves = new File(destDir.getAbsolutePath() + "/saves");
        scripts.mkdir();
        data.mkdir();
        saves.mkdir();
        editorFrame.setGameFolder(destDir.getAbsolutePath());
        editorFrame.setGamePath(destDir.getAbsolutePath() + "/" + gameNameField.getText() + ".sud");
        dispose();
    }

    private void onCancel() {
        dispose();
        System.exit(0);
    }
}
