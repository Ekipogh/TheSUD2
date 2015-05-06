package ru.ekipogh.sud;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
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
    private JList<Item> itemsList;
    private JList charactersList;
    private JButton inventoryButton;
    private DefaultListModel<Item> itemsListModel;
    private JPopupMenu popupMenu;

    private static Location currentLocation;
    private List<Item> items;

    public PlayerFrame() {
        super("The SUD2");
        setContentPane(rootPanel);
        pack();
        setLocationRelativeTo(null);

        itemsListModel = new DefaultListModel<Item>();
        itemsList.setModel(itemsListModel);
        //charactersListModel = new DefaultListModel<Character>();
        //charactersList.setModel(charactersListModel);
        popupMenu = new JPopupMenu();

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
        itemsList.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                System.out.println("Showing poopup");
                //показываем меню действий для предмета
                if (SwingUtilities.isRightMouseButton(e)) {
                    JList list = (JList) e.getSource();
                    int row = list.locationToIndex(e.getPoint());
                    list.setSelectedIndex(row);
                    if (!itemsList.isSelectionEmpty())
                        showPopup(e);
                }
            }
        });
        inventoryButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showInventoryScreen();
            }
        });
    }

    private void showInventoryScreen() {
        new InventoryFrame();
    }

    //показываем менюшку для листов
    private void showPopup(MouseEvent e) {
        JMenuItem menuItem;
        popupMenu.removeAll();
        int index = itemsList.getSelectedIndex();
        final Item selected = itemsListModel.getElementAt(index);
        if (selected != null) {
            ItemTypes type = selected.getType();
            //можно положить в инвентарь съедобное и экипируемое
            if (type == ItemTypes.EQUIPPABLE || type == ItemTypes.CONSUMABLE) {
                menuItem = new JMenuItem("Взять");
                menuItem.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        moveItemToPlayerInventory(selected);
                    }
                });
                popupMenu.add(menuItem);
            }
            if (type == ItemTypes.EQUIPPABLE) {
                menuItem = new JMenuItem("Экипировать");
                menuItem.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        equipItem(selected);
                    }
                });
                popupMenu.add(menuItem);
            }
            popupMenu.show(itemsList, e.getX(), e.getY());
        }
    }

    //надеть предмет
    private void equipItem(Item item) {
        currentLocation.removeItem(item);
        player.equip(item);
        itemsListModel.removeElement(item);
    }

    //положить предмет в инвентарь игрока
    private void moveItemToPlayerInventory(Item item) {
        itemsListModel.removeElement(item);
        currentLocation.removeItem(item);
        player.addToInventory(item);
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

    //инициализация параметров игры
    private void initialize(SaveFile saveFile) {
        player = saveFile.getPlayer();
        locations = saveFile.getLocations();
        items = saveFile.getItems();
        currentLocation = player.getLocation();
        output.setText(saveFile.getGameName() + "\n" + saveFile.getGameStartMessage());
        playerName.setText(player.getName());
    }

    //выполнение сценариев и игровой логики
    private void proceed() {
        output.setText(output.getText() + "\n" + currentLocation.getName() + "\n" + currentLocation.getDescription());

        //Заполняем список предметов в локации
        itemsListModel.clear();
        currentLocation.getInventory().forEach(itemsListModel::addElement);
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

    public static Player getPlayer() {
        return player;
    }
}
