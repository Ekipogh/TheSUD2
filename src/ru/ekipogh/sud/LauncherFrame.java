package ru.ekipogh.sud;

import javax.swing.*;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.io.File;

/**
 * Created by dedov_d on 23.04.2015.
 */
public class LauncherFrame extends JFrame {
    private JPanel rootPanel;
    private JButton editorButton;
    private JButton playerButton;
    private JTextField gamePathField;
    private JButton openFileButton;

    public LauncherFrame() {
        super("TheSUD 2");
        setContentPane(rootPanel);

        pack();
        setLocationRelativeTo(null);

        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        editorButton.addActionListener(e -> {
            LauncherFrame.this.setVisible(false);
            Main.editor = new EditorFrame(gamePathField.getText());
        });

        setVisible(true);
        openFileButton.addActionListener(e -> chooseGameFile());
        playerButton.addActionListener(e -> startGame());
    }

    private void startGame() {
        String pathToGame = gamePathField.getText();
        File gameFile = new File(pathToGame);
        if (gameFile.exists()) {
            this.setVisible(false);
            new PlayerFrame(pathToGame);
        } else
            JOptionPane.showMessageDialog(this, "Файла с игрой не существует, выберите другой!");
    }

    private void chooseGameFile() {
        JFileChooser fc = new JFileChooser(System.getProperty("user.dir"));
        FileFilter ff = new FileNameExtensionFilter("TheSUD game", "sud");
        fc.setFileFilter(ff);
        int response = fc.showOpenDialog(openFileButton);
        if (response == JFileChooser.APPROVE_OPTION) {
            gamePathField.setText(fc.getSelectedFile().getPath());
        }
    }
}
