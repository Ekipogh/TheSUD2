package ru.ekipogh.sud;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

/**
 * Created by dedov_d on 27.04.2015.
 */
public class PlayerFrame extends JFrame {
    private final int NORTH = 0;
    private final int SOUTH = 1;
    private final int EAST = 2;
    private final int WEST = 3;

    private static Player player;
    private static List<Location> locations;
    private JTextPane output;
    private JButton northButton;
    private JButton southButton;
    private JButton westButton;
    private JButton eastButton;
    private JLabel playerName;
    private JPanel rootPanel;

    private static Location currentLocation;

    public PlayerFrame() {
        super("The SUD2");
        setContentPane(rootPanel);
        pack();
        setLocationRelativeTo(null);

        northButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                move(NORTH);
            }
        });
        southButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                move(SOUTH);
            }
        });
        westButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                move(WEST);
            }
        });
        eastButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                move(EAST);
            }
        });

        setVisible(true);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
    }

    //передвижение игрока
    private void move(int direction) {
        Location playerLocation = null;
        switch (direction) {
            case 0:
                playerLocation = player.getLocation().getNorth(); //Север
                break;
            case 1:
                playerLocation = player.getLocation().getSouth(); //Юг
                break;
            case 2:
                playerLocation = player.getLocation().getEast(); //Восток
                break;
            case 3:
                playerLocation = player.getLocation().getWest(); //Запад
                break;
        }
        currentLocation = playerLocation != null ? playerLocation : currentLocation;
        player.setLocation(currentLocation);
        proceed(); //продолжить, выполнить сценарии и игровую логику
    }

    public PlayerFrame(String pathToGame) {
        this();
        loadGameFile(pathToGame);
    }

    //загрузка файла игры
    private void loadGameFile(String pathToGame) {
        //существование файла игры гарантируется лаунчером, пробуем загрухить из него данные
        SaveFile saveFile = SaveFile.open(pathToGame);

        //инициализация переменных игры из файла
        initialize(saveFile);

        proceed();
    }

    private void initialize(SaveFile saveFile) {
        player = saveFile.getPlayer();
        locations = saveFile.getLocations();
        currentLocation = player.getLocation();

        playerName.setText(player.getName());
    }

    //выполнение сценариев и игровой логики
    private void proceed() {
        output.setText(output.getText() + "\n" + currentLocation.getName());

        //Дизаблим не используемые кнопки передвижения
        directionButtonsEnable();

    }

    //Дизаблим кнопки передвижения соответствующие null выходам
    private void directionButtonsEnable() {
        if (currentLocation.getNorth() == null)
            northButton.setEnabled(false);
        else
            northButton.setEnabled(true);
        if (currentLocation.getSouth() == null)
            southButton.setEnabled(false);
        else
            southButton.setEnabled(true);
        if (currentLocation.getEast() == null)
            eastButton.setEnabled(false);
        else
            eastButton.setEnabled(true);
        if (currentLocation.getWest() == null)
            westButton.setEnabled(false);
        else
            westButton.setEnabled(true);
    }
}
