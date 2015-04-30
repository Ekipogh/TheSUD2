package ru.ekipogh.sud;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        editorButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                LauncherFrame.this.setVisible(false);
                Main.editor = new EditorFrame();
            }
        });

        setVisible(true);
        openFileButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                chooseGameFile();
            }
        });
        playerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                startGame();
            }
        });
    }

    private void startGame() {
        String pathToGame = gamePathField.getText();
        File gameFile = new File(pathToGame);
        if (gameFile.exists()) {
            new PlayerFrame(pathToGame);
        } else
            JOptionPane.showMessageDialog(this, "Файла с игрой не существует, выберите другой!");
    }

    private void chooseGameFile() {
        JFileChooser fc = new JFileChooser(System.getProperty("user.dir"));
        int response = fc.showOpenDialog(openFileButton);
        if (response == JFileChooser.APPROVE_OPTION) {
            gamePathField.setText(fc.getSelectedFile().getPath());
        }
    }
}
