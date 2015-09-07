package ru.ekipogh.sud;

import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by dedov_d on 27.04.2015.
 */
public class PlayerFrame extends JFrame {
    private final int NORTH = 0;
    private final int SOUTH = 1;
    private final int EAST = 2;
    private final int WEST = 3;

    private static GameCharacter player;
    private static List<Location> locations;
    private static List<GameCharacter> characters;
    private MyTextPane output;
    private JButton northButton;
    private JButton southButton;
    private JButton westButton;
    private JButton eastButton;
    private JLabel playerName;
    private JPanel rootPanel;
    private JList<Item> itemsList;
    private JList<GameCharacter> charactersList;
    private JButton inventoryButton;
    private JLabel locationPic;
    private JPanel locationPicPanel;
    private JTextField jsInputField;
    private JTextArea playerDescriptionArea;
    private DefaultListModel<Item> itemsListModel;
    private DefaultListModel<GameCharacter> charactersListModel;
    private JPopupMenu popupMenu;
    private final String ONTAKE = "_onTake";
    private final String ONEQUIP = "_onEquip";
    private final String ONUSE = "_onUse";
    private final String ONENTER = "_onEnter";
    private final String ONLEAVE = "_onLeave";
    private final String ONPLAYERARRIVE = "_onPlayerArrive";
    private final String ONPLAYERLEAVE = "_onPlayerLeave";

    private static Location currentLocation;
    private List<Item> items;
    private String gamePath;

    private PlayerFrame() {
        super("The SUD2");
        setContentPane(rootPanel);
        pack();
        setLocationRelativeTo(null);

        itemsListModel = new DefaultListModel<>();
        itemsList.setModel(itemsListModel);
        charactersListModel = new DefaultListModel<>();
        charactersList.setModel(charactersListModel);
        popupMenu = new JPopupMenu();

        northButton.addActionListener(e -> move(NORTH));
        southButton.addActionListener(e -> move(SOUTH));
        westButton.addActionListener(e -> move(WEST));
        eastButton.addActionListener(e -> move(EAST));


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
                        showItemsPopup(e);
                }
            }
        });
        //меню персонажей
        charactersList.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                showCharPopup(e);

            }
        });
        inventoryButton.addActionListener(e -> showInventoryScreen());

        //меню локации
        locationPicPanel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                showLocationPopup(e);
            }
        });

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                Main.launcher.setVisible(true);
                super.windowClosing(e);
            }
        });

        jsInputField.addActionListener(e -> runScript());
    }

    private void runScript() {
        Script.run(jsInputField.getText(), player);
    }

    private void showCharPopup(MouseEvent e) {
        if (SwingUtilities.isRightMouseButton(e)) {
            JList list = (JList) e.getSource();
            int row = list.locationToIndex(e.getPoint());
            list.setSelectedIndex(row);
            if (!charactersList.isSelectionEmpty()) {
                JMenuItem menuItem;
                GameCharacter character = charactersListModel.getElementAt(row);
                popupMenu.removeAll();

                for (String scriptName : character.getScripts().keySet()) {
                    if (!scriptName.startsWith("_on")) {
                        menuItem = new JMenuItem(scriptName);
                        menuItem.addActionListener(ev -> Script.run(character.getScript(scriptName), character));
                        popupMenu.add(menuItem);
                    }
                }
                if (character.getCategory() != null) {
                    for (String scriptName : character.getCategory().getScripts().keySet()) {
                        if (!scriptName.startsWith("_on")) {
                            menuItem = new JMenuItem(scriptName);
                            menuItem.addActionListener(ev -> Script.run(character.getCategory().getScript(scriptName), character));
                            popupMenu.add(menuItem);
                        }
                    }
                }
                menuItem = new JMenuItem("Описание");
                menuItem.addActionListener(ev -> showCharDescription(character));
                popupMenu.add(menuItem);
                popupMenu.show(charactersList, e.getX(), e.getY());
            }
        }
    }

    private void showCharDescription(GameCharacter character) {
        output.println(parseDescription(character.getDescription(), character));
    }

    private void showLocationPopup(MouseEvent event) {
        if (SwingUtilities.isRightMouseButton(event)) {
            JMenuItem menuItem;
            popupMenu.removeAll();
            for (String scriptName : currentLocation.getScripts().keySet()) {
                if (!scriptName.startsWith("_on")) {
                    menuItem = new JMenuItem(scriptName);
                    menuItem.addActionListener(e -> Script.run(currentLocation.getScript(scriptName), currentLocation));
                    popupMenu.add(menuItem);
                }
            }
            menuItem = new JMenuItem("Описание");
            menuItem.addActionListener(e -> showLocationDescription());
            popupMenu.add(menuItem);
            popupMenu.show(locationPic, event.getX(), event.getY());
        }
    }

    private void showLocationDescription() {
        output.println(parseDescription(currentLocation.getDescription(), currentLocation));
    }

    private void showInventoryScreen() {
        new InventoryFrame(this);
    }

    //показываем менюшку для листов
    private void showItemsPopup(MouseEvent e) {
        JMenuItem menuItem;
        popupMenu.removeAll();
        int index = itemsList.getSelectedIndex();
        final Item selected = itemsListModel.getElementAt(index);
        if (selected != null) {
            ItemTypes type = selected.getType();
            //можно положить в инвентарь съедобное и экипируемое
            if (type == ItemTypes.EQUIPPABLE || type == ItemTypes.CONSUMABLE || type == ItemTypes.STORABLE) {
                menuItem = new JMenuItem("Взять");
                menuItem.addActionListener(e1 -> moveItemToPlayerInventory(selected));
                popupMenu.add(menuItem);
            }
            if (type == ItemTypes.EQUIPPABLE) {
                menuItem = new JMenuItem("Экипировать");
                menuItem.addActionListener(e1 -> equipItem(selected));
                popupMenu.add(menuItem);
            }
            if (type == ItemTypes.CONSUMABLE) {
                menuItem = new JMenuItem("Использовать");
                menuItem.addActionListener(e1 -> useItem(selected));
                popupMenu.add(menuItem);
            }
            //Кастомные скрипты
            selected.getScripts().entrySet().forEach(entry -> {
                if (!entry.getKey().startsWith("_on")) {
                    JMenuItem menu = new JMenuItem(entry.getKey());
                    menu.addActionListener(e1 -> Script.run(entry.getValue(), selected));
                    popupMenu.add(menu);
                }
            });
            //Скрипты категории
            if (selected.getCategory() != null) {
                selected.getCategory().getScripts().entrySet().forEach(entry -> {
                    if (!entry.getKey().startsWith("_on")) {
                        JMenuItem menu = new JMenuItem(entry.getKey());
                        menu.addActionListener(e1 -> Script.run(entry.getValue(), selected));
                        popupMenu.add(menu);
                    }
                });
            }

            menuItem = new JMenuItem("Описание");
            menuItem.addActionListener(ev -> showItemDescription(selected));
            popupMenu.add(menuItem);
            popupMenu.show(itemsList, e.getX(), e.getY());
        }
    }

    private void showItemDescription(Item item) {
        output.println(parseDescription(item.getDescription(), item));
    }

    private void useItem(Item item) {
        Script.run(item.getScript(ONUSE), item);
        if (item.getCategory() != null)
            Script.run(item.getCategory().getScript(ONUSE), item);
        itemsListModel.removeElement(item);
    }

    //надеть предмет
    private void equipItem(Item item) {
        currentLocation.removeItem(item);
        player.equip(item);
        itemsListModel.removeElement(item);
        Script.run(item.getScript(ONEQUIP), item);
        if (item.getCategory() != null)
            Script.run(item.getCategory().getScript(ONEQUIP), item);
    }

    //положить предмет в инвентарь игрока
    private void moveItemToPlayerInventory(Item item) {
        itemsListModel.removeElement(item);
        currentLocation.removeItem(item);
        player.addToInventory(item);
        Script.run(item.getScript(ONTAKE), item);
        if (item.getCategory() != null)
            Script.run(item.getCategory().getScript(ONTAKE), item);
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
        //Скрипты локаций
        Script.run(currentLocation.getScript(ONLEAVE), currentLocation);
        if (currentLocation.getCategory() != null)
            Script.run(currentLocation.getCategory().getScript(ONLEAVE), currentLocation);
        //Скрипты НПЦ
        for (int i = 0; i < charactersListModel.size(); i++) {
            GameCharacter character = charactersListModel.getElementAt(i);
            Script.run(character.getScript(ONPLAYERLEAVE), character);
        }
        for (int i = 0; i < charactersListModel.size(); i++) {
            GameCharacter character = charactersListModel.getElementAt(i);
            if (character.getCategory() != null)
                Script.run(character.getCategory().getScript(ONPLAYERLEAVE), character);
            Script.run(character.getScript(ONPLAYERLEAVE), character);
        }

        currentLocation = playerLocation != null ? playerLocation : currentLocation;
        player.setLocation(currentLocation);

        updateCharacters();
        for (int i = 0; i < charactersListModel.size(); i++) {
            GameCharacter character = charactersListModel.getElementAt(i);
            if (character.getCategory() != null)
                Script.run(character.getCategory().getScript(ONPLAYERARRIVE), character);
            Script.run(character.getScript(ONPLAYERARRIVE), character); //TODO: Двойной вызов updateCharacters()
        }

        Script.run(currentLocation.getScript(ONENTER), currentLocation);
        if (currentLocation.getCategory() != null)
            Script.run(currentLocation.getCategory().getScript(ONENTER), currentLocation);
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
        characters = saveFile.getCharacters();
        items = saveFile.getItems();
        currentLocation = player.getLocation();
        playerName.setText(player.getName());
        Map<String, String> slotNames = saveFile.getSlotNames();
        Equipment.setSlotNames(slotNames);
        gamePath = saveFile.getPath();
        initJS(saveFile.getInitScript());
        output.println("<b>" + saveFile.getGameName() + "</b>");
        output.println(saveFile.getGameStartMessage());
    }

    private void initJS(String initScript) {
        Script.init();
        Script.setProperty("out", output);
        Script.setProperty("items", items);
        Script.setProperty("player", player);
        Script.setProperty("locations", locations);
        Script.setProperty("characters", characters);
        Script.setProperty("currentLocation", currentLocation);
        Script.setProperty("game", this);
        Script.setProperty("gameDir", new File(gamePath).getParent());
        Script.initFunctions();
        Script.run(initScript, null);
    }

    //выполнение сценариев и игровой логики
    private void proceed() {
        output.println("<b>" + currentLocation.getName() + "</b>");
        if (!currentLocation.getDescription().isEmpty())
            output.println(parseDescription(currentLocation.getDescription(), currentLocation));

        //изменяем рисунок локации
        if (currentLocation.getPicturePath() == null || currentLocation.getPicturePath().isEmpty())
            locationPic.setText(currentLocation.getName());
        else
            locationPic.setIcon(new ImageIcon(currentLocation.getPicturePath()));

        //Заполняем список предметов в локации
        updateItems();
        //Заполняем список персонажей в локации
        updateCharacters();

        //Дизаблим не используемые кнопки передвижения
        directionButtonsEnable();
        playerDescriptionArea.setText(parseDescription(player.getDescription(), player));
    }

    public void updateCharacters() {
        charactersListModel.clear();
        characters.stream().filter(c -> currentLocation.equals(c.getLocation())).forEach(charactersListModel::addElement);
    }

    //Обновляем список предметов в локации
    public void updateItems() {
        itemsListModel.clear();
        currentLocation.getInventory().forEach(itemsListModel::addElement);
    }

    private String parseDescription(String description, Object object) {
        String results = "";
        Matcher m = Pattern.compile("\\<\\<(.*?)\\>\\>").matcher(description);
        if (m.find()) {
            results = m.replaceAll(String.valueOf(Script.run(m.group(1), object)));
        }
        //results = text.replaceAll("\\<\\<(.*?)\\>\\>", "$1" + "f");
        return results;
    }

    //Дизаблим кнопки передвижения соответствующие null выходам и выходам, у которых заблокирован доступ
    private void directionButtonsEnable() {
        northButton.setEnabled((currentLocation.getNorth() != null && currentLocation.getNorth().isAvailable()));
        southButton.setEnabled((currentLocation.getSouth() != null && currentLocation.getSouth().isAvailable()));
        eastButton.setEnabled((currentLocation.getEast() != null && currentLocation.getEast().isAvailable()));
        westButton.setEnabled((currentLocation.getWest() != null && currentLocation.getWest().isAvailable()));
    }

    public static GameCharacter getPlayer() {
        return player;
    }
}
