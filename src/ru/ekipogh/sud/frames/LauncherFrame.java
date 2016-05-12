package ru.ekipogh.sud.frames;

import ru.ekipogh.sud.Main;

import javax.swing.*;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.io.File;
import java.util.prefs.Preferences;

/**
 * Created by ekipogh on 23.04.2015.
 licensed under WTFPL
 */
public class LauncherFrame extends JFrame {
    private JPanel rootPanel;
    private JButton editorButton;
    private JButton playerButton;
    private JTextField gamePathField;
    private JButton openFileButton;

    public LauncherFrame() {
        super("Лаунчер");
        setContentPane(rootPanel);

        pack();
        setLocationRelativeTo(null);

        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        editorButton.addActionListener(e -> startEditor());

        setVisible(true);
        openFileButton.addActionListener(e -> chooseGameFile());
        playerButton.addActionListener(e -> startGame());
    }

    private void startEditor() {
        this.setVisible(false);
        Main.editor = new EditorFrame(gamePathField.getText());
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
        Preferences pref = Preferences.userRoot().node("TheSUD2");
        String lastPath = pref.get("lastDir", System.getProperty("user.dir"));
        JFileChooser fc = new JFileChooser(lastPath);
        FileFilter ff = new FileNameExtensionFilter("TheSUD game", "sud");
        fc.setFileFilter(ff);
        int response = fc.showOpenDialog(openFileButton);
        if (response == JFileChooser.APPROVE_OPTION) {
            String path = fc.getSelectedFile().getPath();
            gamePathField.setText(path);
            pref.put("lastDir", path);
        }
    }
}
