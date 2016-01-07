package ru.ekipogh.sud;

import javax.swing.*;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreePath;
import javax.swing.tree.TreeSelectionModel;
import java.awt.event.*;
import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by dedov_d on 27.04.2015.
 */
public class PlayerFrame extends JFrame {
    private static final int UP = 4;
    private static final int DOWN = 5;
    private static final int NORTH = 0;
    private static final int SOUTH = 1;
    private static final int EAST = 2;
    private static final int WEST = 3;

    private static GameCharacter player;
    private static List<Location> locations;
    private static List<GameCharacter> characters;
    private final DefaultTreeModel charactersTreeModel;
    private final DefaultTreeModel itemsTreeModel;
    private MyTextPane output;
    private JButton northButton;
    private JButton southButton;
    private JButton westButton;
    private JButton eastButton;
    private JLabel playerName;
    private JPanel rootPanel;
    private JButton inventoryButton;
    private JLabel locationPic;
    private JPanel locationPicPanel;
    private JTextField jsInputField;
    private JTextArea playerDescriptionArea;
    private JTree charactersTree;
    private JTree itemsTree;
    private JButton upButton;
    private JButton downButton;
    private JButton proceedButton;
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
    private String savePath;
    private boolean paused = false;
    private HashMap<String, Script> commonScripts;

    private PlayerFrame() {
        super("The SUD2");
        setContentPane(rootPanel);
        pack();
        setLocationRelativeTo(null);
        setExtendedState(JFrame.MAXIMIZED_BOTH);

        charactersTreeModel = new DefaultTreeModel(new DefaultMutableTreeNode("Персонажы"));
        charactersTree.setModel(charactersTreeModel);
        charactersTree.getSelectionModel().setSelectionMode(TreeSelectionModel.SINGLE_TREE_SELECTION);

        itemsTreeModel = new DefaultTreeModel((new DefaultMutableTreeNode("Предметы")));
        itemsTree.setModel(itemsTreeModel);
        itemsTree.getSelectionModel().setSelectionMode(TreeSelectionModel.SINGLE_TREE_SELECTION);

        popupMenu = new JPopupMenu();

        northButton.addActionListener(e -> move(NORTH));
        southButton.addActionListener(e -> move(SOUTH));
        westButton.addActionListener(e -> move(WEST));
        eastButton.addActionListener(e -> move(EAST));
        upButton.addActionListener(e -> move(UP));
        downButton.addActionListener(e -> move(DOWN));

        proceedButton.addActionListener(e -> unPause());

        playerName.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                if (SwingUtilities.isRightMouseButton(e)) {
                    showPlayerPopUp(e);
                }
            }
        });

        JMenuBar menuBar = new JMenuBar();
        JMenu menu = new JMenu("Файл");
        JMenuItem newGame = new JMenuItem("Заново");
        JMenuItem loadSave = new JMenuItem("Загрузить сохранение");
        JMenuItem saveSave = new JMenuItem("Сохранить игру");
        JMenuItem saveSaveAs = new JMenuItem("Сохранить игру как");

        newGame.addActionListener(e -> loadGameFile(gamePath));
        newGame.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N, KeyEvent.CTRL_MASK));
        loadSave.addActionListener(e -> loadSave());
        loadSave.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O, KeyEvent.CTRL_MASK));
        saveSave.addActionListener(e -> saveSave());
        saveSave.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, KeyEvent.CTRL_MASK));
        saveSaveAs.addActionListener(e -> saveSaveAs());
        saveSaveAs.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, KeyEvent.CTRL_MASK + InputEvent.SHIFT_MASK));

        menu.add(newGame);
        menu.add(loadSave);
        menu.add(saveSave);
        menu.add(saveSaveAs);

        menuBar.add(menu);

        setJMenuBar(menuBar);

        setVisible(true);

        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        //меню персонажей
        charactersTree.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                TreePath treePath = charactersTree.getSelectionPath();
                if (treePath != null) {
                    DefaultMutableTreeNode selected = (DefaultMutableTreeNode) treePath.getLastPathComponent();
                    if (selected != null && selected instanceof SudTreeNode && e.getClickCount() == 2 && selected.isLeaf())
                        ((SudTreeNode) selected).invoke();
                }
            }
        });

        //меню предметов
        itemsTree.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                TreePath treePath = itemsTree.getSelectionPath();
                if (treePath != null) {
                    DefaultMutableTreeNode selected = (DefaultMutableTreeNode) treePath.getLastPathComponent();
                    if (selected != null && selected instanceof SudTreeNode && e.getClickCount() == 2 && selected.isLeaf())
                        ((SudTreeNode) selected).invoke();
                }
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

        //закрытие окна
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                Main.launcher.setVisible(true);
                super.windowClosing(e);
            }
        });

        jsInputField.addActionListener(e -> runScript());
    }

    //снимаем игру с паузы
    private void unPause() {
        paused = false;
        proceedButton.setEnabled(false);
        itemsTree.setEnabled(true);
        charactersTree.setEnabled(true);
        inventoryButton.setEnabled(true);
        updateButtons();
        output.setEnabled(true);
    }

    //метод меню Сохранить как
    private void saveSaveAs() {
        JFileChooser fc = new JFileChooser(System.getProperty("user.dir"));
        FileFilter ff = new FileNameExtensionFilter("TheSUD game save", "sudsav");
        fc.setFileFilter(ff);
        int response = fc.showSaveDialog(this);
        if (response == JFileChooser.APPROVE_OPTION) {
            savePath = fc.getSelectedFile().getPath();
            if (!savePath.endsWith(".sudsav"))
                savePath += ".sudsav";
        }
        save();
    }

    //метод меню Сохранить
    private void saveSave() {
        if (savePath == null) {
            JFileChooser fc = new JFileChooser(System.getProperty("user.dir"));
            FileFilter ff = new FileNameExtensionFilter("TheSUD game save", "sudsav");
            fc.setFileFilter(ff);
            int response = fc.showSaveDialog(this);
            if (response == JFileChooser.APPROVE_OPTION) {
                savePath = fc.getSelectedFile().getPath();
                if (!savePath.endsWith(".sudsav"))
                    savePath += ".sudsav";
            } else return;
        }
        save();
    }

    //Сохраняем текущую игру в файл
    private void save() {
        SaveFile saveFile = new SaveFile();
        saveFile.setPlayer(player);
        saveFile.setCharacters(characters);
        saveFile.setLocations(locations);
        saveFile.setItems(items);

        //костыль,это не нужно сохранять, сделать костыл попроще
        Script.setProperty("game", null);
        Script.setProperty("sout", null);
        Script.setProperty("out", null);

        SaveFile.setScope(Script.getScope());
        System.out.println("Saving gamestate to " + savePath);
        saveFile.save(savePath);
        System.out.println("Saved!");

        Script.setProperty("game", this);
        Script.setProperty("sout", System.out);
        Script.setProperty("out", output);
    }

    //загружаем игру из файла
    private void loadSave() {
        JFileChooser fc = new JFileChooser(System.getProperty("user.dir"));
        FileFilter ff = new FileNameExtensionFilter("TheSUD game", "sudsav");
        fc.setFileFilter(ff);
        int response = fc.showOpenDialog(this);
        if (response == JFileChooser.APPROVE_OPTION) {
            this.savePath = fc.getSelectedFile().getPath();
            SaveFile saveFile = SaveFile.open(savePath);
            player = saveFile.getPlayer();
            characters = saveFile.getCharacters();
            locations = saveFile.getLocations();
            currentLocation = player.getLocation();
            items = saveFile.getItems();
            System.out.println("Loading gamestate from " + savePath);
            Script.set(saveFile.getScopeObjects());
            System.out.println("Loaded!");
            proceed();
        }
    }

    //отображаем менб игрока
    private void showPlayerPopUp(MouseEvent e) {
        if (!paused) {
            JMenuItem menuItem;
            popupMenu.removeAll();
            for (Map.Entry<String, Script> entry : player.getScripts().entrySet()) { //добавляем в менб опции действий игрока
                if (!entry.getKey().startsWith("_on") && entry.getValue().isEnabled()) {
                    menuItem = new JMenuItem(entry.getKey());
                    menuItem.addActionListener(ev -> Script.run(player.getScript(entry.getKey()).getText(), player));
                    popupMenu.add(menuItem);
                }
            }
            if (!player.getDescription().isEmpty()) {
                menuItem = new JMenuItem("Описание");
                menuItem.addActionListener(ev -> parseDescription(player.getDescription()));
                popupMenu.add(menuItem);
            }
            popupMenu.show(playerName, e.getX(), e.getY());
        }
    }

    private void runScript() {
        //todo: implement proper console
        Script.run(jsInputField.getText(), player);
    }

    //отображаем описание персонажа
    private void showCharDescription(GameCharacter character) {
        parseDescription(character.getDescription());
    }

    //меню локации todo: нужно бы переделать
    private void showLocationPopup(MouseEvent event) {
        if (!paused) {
            if (SwingUtilities.isRightMouseButton(event)) {
                JMenuItem menuItem;
                popupMenu.removeAll();
                for (Map.Entry<String, Script> entry : currentLocation.getScripts().entrySet()) //добавляем в меню опции с командами локации и командами категорий локации
                    if (!entry.getKey().startsWith("_on") && entry.getValue().isEnabled()) {
                        menuItem = new JMenuItem(entry.getKey());
                        menuItem.addActionListener(e -> Script.run(currentLocation.getScript(entry.getKey()).getText(), currentLocation));
                        popupMenu.add(menuItem);
                    }
                for (LocationCategory category : currentLocation.getCategories()) {
                    for (Map.Entry<String, Script> entry : category.getScripts().entrySet()) {
                        if (!entry.getKey().startsWith("_on")) {
                            menuItem = new JMenuItem(entry.getKey());
                            menuItem.addActionListener(e -> Script.run(entry.getValue().getText(), currentLocation));
                            popupMenu.add(menuItem);
                        }
                    }
                }
                if (!currentLocation.getDescription().isEmpty()) { //напоследок команда с отображением описания локации
                    menuItem = new JMenuItem("Описание");
                    menuItem.addActionListener(e -> showLocationDescription());
                    popupMenu.add(menuItem);
                }
                popupMenu.show(locationPic, event.getX(), event.getY());
            }
        }
    }

    //отображаем описание локации
    private void showLocationDescription() {
        parseDescription(currentLocation.getDescription());
    }

    //вызываем окна инвенторя
    private void showInventoryScreen() {
        new InventoryFrame(this);
    }

    //отображаем описание предмета
    private void showItemDescription(Item item) {
        parseDescription(item.getDescription());
    }

    //используем предмет
    private void useItem(Item item, Inventory inventory) {
        inventory.remove(item);
        Script.run(item.getScript(ONUSE).getText(), item);
        for (ItemCategory category : item.getCategories())
            Script.run(category.getScript(ONUSE).getText(), item);
        Script.run(commonScripts.get("_onPlayerUsesItem").getText(), item);
        updateItems();
    }

    //надеть предмет
    private void equipItem(Item item, Inventory inventory) {
        inventory.remove(item); //убираем предмет из инвенторя откуда он был экирирован
        player.equip(item);
        updateItems();
        Script.run(item.getScript(ONEQUIP).getText(), item); //выполняем скрипты связаные с экипировкой предмета
        for (ItemCategory category : item.getCategories())
            Script.run(category.getScript(ONEQUIP).getText(), item);
        Script.run(commonScripts.get("_onPlayerEquipsItem").getText(), item);
    }

    //положить предмет в инвентарь игрока
    private void takeItem(Item item, Inventory inventory) {
        inventory.remove(item); //убираем предмет из инвенторя откуда он был взят
        player.addItem(item);
        Script.run(item.getScript(ONTAKE).getText(), item);
        for (ItemCategory category : item.getCategories())
            Script.run(category.getScript(ONTAKE).getText(), item);
        Script.run(commonScripts.get("_onPlayerTakesItem").getText(), item);
        updateItems();
    }

    //передвижение игрока
    private void move(int direction) {
        Location playerLocation = null;
        String scripName = null;
        switch (direction) {
            case NORTH:
                playerLocation = player.getLocation().getNorth(); //Север
                scripName = "_onPlayerMovesNorth";
                break;
            case SOUTH:
                playerLocation = player.getLocation().getSouth(); //Юг
                scripName = "_onPlayerMovesSouth";
                break;
            case EAST:
                playerLocation = player.getLocation().getEast(); //Восток
                scripName = "_onPlayerMovesEast";
                break;
            case WEST:
                playerLocation = player.getLocation().getWest(); //Запад
                scripName = "_onPlayerMovesWest";
                break;
            case UP:
                playerLocation = player.getLocation().getUp(); //Вверх
                scripName = "_onPlayerMovesUp";
                break;
            case DOWN:
                playerLocation = player.getLocation().getDown(); //Вниз
                scripName = "_onPlayerMovesDown";
                break;
        }
        //Скрипты локаций
        Script.run(currentLocation.getScript(ONLEAVE).getText(), currentLocation);
        for (LocationCategory category : currentLocation.getCategories())
            Script.run(category.getScript(ONLEAVE).getText(), currentLocation);
        //Скрипты НПЦ
        characters.stream().filter(c -> currentLocation.equals(c.getLocation())).forEach(c -> {
            for (CharacterCategory characterCategory : c.getCategories()) {
                Script.run(characterCategory.getScript(ONPLAYERLEAVE).getText(), c);
            }
            Script.run(c.getScript(ONPLAYERLEAVE).getText(), c);
        });

        currentLocation = playerLocation != null ? playerLocation : currentLocation;
        player.setLocation(currentLocation);

        //common scripts
        Script.run(commonScripts.get("_onPlayerMoves").getText(), playerLocation);
        Script.run(commonScripts.get(scripName).getText(), playerLocation);

        updateCharacters();

        characters.stream().filter(c -> currentLocation.equals(c.getLocation())).forEach(c -> {
            for (CharacterCategory characterCategory : c.getCategories()) {
                Script.run(characterCategory.getScript(ONPLAYERARRIVE).getText(), c);
            }
            Script.run(c.getScript(ONPLAYERARRIVE).getText(), c);
        });

        Script.run(currentLocation.getScript(ONENTER).getText(), currentLocation);
        for (LocationCategory category : currentLocation.getCategories())
            Script.run(category.getScript(ONENTER).getText(), currentLocation);

        proceed(); //продолжить, выполнить сценарии и игровую логику

    }

    public PlayerFrame(String pathToGame) {
        this();
        loadGameFile(pathToGame);
    }

    //загрузка файла игры
    private void loadGameFile(String pathToGame) {
        //существование файла игры гарантируется лаунчером, пробуем загрухить из него данные
        GameFile gameFile = GameFile.open(pathToGame);

        //инициализация переменных игры из файла
        initialize(gameFile);

        proceed();
    }

    //инициализация параметров игры
    private void initialize(GameFile gameFile) {
        player = gameFile.getPlayer();
        commonScripts = gameFile.getCommonScripts();
        locations = gameFile.getLocations();
        characters = gameFile.getCharacters();
        items = gameFile.getItems();
        currentLocation = player.getLocation();
        playerName.setText(player.getName());
        Map<String, String> slotNames = gameFile.getSlotNames();
        Equipment.setSlotNames(slotNames);
        gamePath = gameFile.getPath();
        initJS(gameFile.getInitScript());
        output.println("<b>" + gameFile.getGameName() + "</b>");
        output.println(gameFile.getGameStartMessage());
    }

    //инициализируем JavaScript
    private void initJS(String initScript) {
        Script.init();
        Script.setProperty("out", output);
        Script.setProperty("sout", System.out);
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

    public void update() {
        updateItems();
        updateCharacters();
        updateButtons();
    }

    //выполнение сценариев и игровой логики
    public void proceed() {
        output.println("<b>" + currentLocation.getName() + "</b>");
        if (!currentLocation.getDescription().isEmpty())
            parseDescription(currentLocation.getDescription());

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
        updateButtons();
        parseDescription(player.getDescription());
        //отоброжаем выходы
        String exits = "<font color=\"DarkGrey\"><b>Выходы: ";
        Location north = currentLocation.getNorth();
        Location south = currentLocation.getSouth();
        Location east = currentLocation.getEast();
        Location west = currentLocation.getWest();
        Location up = currentLocation.getUp();
        Location down = currentLocation.getDown();
        if (north != null && currentLocation.isNorthOpened()) {
            exits += "c ";
        }
        if (south != null && currentLocation.isSouthOpened()) {
            exits += "ю ";
        }
        if (east != null && currentLocation.isEastOpened()) {
            exits += "в ";
        }
        if (west != null && currentLocation.isWestOpened()) {
            exits += "з ";
        }
        if (up != null && currentLocation.isUpOpened()) {
            exits += "вв ";
        }
        if (down != null && currentLocation.isDownOpened()) {
            exits += "вн ";
        }
        exits += "</b></font>";
        output.println(exits);
    }

    //обновляем дерево персонажей
    public void updateCharacters() {
        ((DefaultMutableTreeNode) charactersTreeModel.getRoot()).removeAllChildren();
        charactersTreeModel.reload();
        characters.stream().filter(c -> currentLocation.equals(c.getLocation())).forEach(c -> {
            DefaultMutableTreeNode characterNode = new DefaultMutableTreeNode(c);
            DefaultMutableTreeNode top = (DefaultMutableTreeNode) charactersTreeModel.getRoot();
            charactersTreeModel.insertNodeInto(characterNode, top, top.getChildCount());
            c.getScripts().entrySet().stream().filter(entry -> !entry.getKey().startsWith("_on") && entry.getValue().isEnabled()).forEach(entry -> charactersTreeModel.insertNodeInto(new SudTreeNode(entry.getKey(), l -> Script.run(entry.getValue().getText(), c)), characterNode, characterNode.getChildCount()));
            for (CharacterCategory characterCategory : c.getCategories()) {
                characterCategory.getScripts().entrySet().stream().filter(entry -> !entry.getKey().startsWith("_on") && entry.getValue().isEnabled()).forEach(entry -> charactersTreeModel.insertNodeInto(new SudTreeNode(entry.getKey(), l -> Script.run(entry.getValue().getText(), c)), characterNode, characterNode.getChildCount()));
            }
            if (!c.getDescription().isEmpty())
                charactersTreeModel.insertNodeInto(new SudTreeNode("Описание", l -> showCharDescription(c)), characterNode, characterNode.getChildCount());
        });

        expandAllNodes(charactersTree);
    }

    //заполняем дерево предметов
    public void fillItemsTree(Inventory inventory, boolean container) {
        DefaultMutableTreeNode node; //куда добавляем строки
        if (!container) {
            node = (DefaultMutableTreeNode) itemsTreeModel.getRoot(); //в начало
        } else {
            DefaultMutableTreeNode root = ((DefaultMutableTreeNode) itemsTreeModel.getRoot());
            node = (DefaultMutableTreeNode) itemsTreeModel.getChild(root, itemsTreeModel.getChildCount(root) - 1); //в ноду предмета
        }
        //for (Item item : items)
        //inventory.sort(Item::compareTo);
        //items.stream().sorted().forEach(item -> {
        for (int i = 0; i < inventory.size(); i++) {
            Item item = inventory.get(i); //предмет для добавления
            String itemName = item.getName(); //название предмета
            int amount = inventory.amount(i); //количество предметов
//            int count = 1;
            /*if (item.isStackable()) {
                for (int j = i + 1; j < inventory.size(); j++) {
                    if (inventory.get(j).equals(item)) {
                        count++;
                    } else {
                        break;
                    }
                }
                i += count - 1;
            }*/
            SudTreeNode itemNode = new SudTreeNode(item, null); //нода предмета
            itemNode.setText(itemName);
            itemNode.setCount(amount);
            itemsTreeModel.insertNodeInto(itemNode, node, node.getChildCount()); //вставляем ноду предмета в выбранную выше ноду

            //далее скрипты и действия

            //скрипты
            item.getScripts().entrySet().stream().filter(entry -> !entry.getKey().startsWith("_on") && entry.getValue().isEnabled()).forEach(entry -> itemsTreeModel.insertNodeInto(new SudTreeNode(entry.getKey(), l -> Script.run(entry.getValue().getText(), item)), itemNode, itemNode.getChildCount()));
            for (ItemCategory category : item.getCategories())
                category.getScripts().entrySet().stream().filter(entry -> !entry.getKey().startsWith("_on") && entry.getValue().isEnabled()).forEach(entry -> itemsTreeModel.insertNodeInto(new SudTreeNode(entry.getKey(), l -> Script.run(entry.getValue().getText(), item)), itemNode, itemNode.getChildCount()));
            if (!item.getDescription().isEmpty()) {
                itemsTreeModel.insertNodeInto(new SudTreeNode("Описание", l -> showItemDescription(item)), itemNode, itemNode.getChildCount());
            }
            //действия
            switch (item.getType()) {
                case EQUIPPABLE:
                    itemsTreeModel.insertNodeInto(new SudTreeNode("Экипировать", l -> equipItem(item, inventory)), itemNode, itemNode.getChildCount());
                    itemsTreeModel.insertNodeInto(new SudTreeNode("Взять", l -> takeItem(item, inventory)), itemNode, itemNode.getChildCount());
                    break;
                case STORABLE:
                    if (item.isStackable() && amount > 1) {
                        itemsTreeModel.insertNodeInto(new SudTreeNode("Взять...", l -> takeAmountOfItem(item, inventory, itemNode.getCount())), itemNode, itemNode.getChildCount());
                    } else {
                        itemsTreeModel.insertNodeInto(new SudTreeNode("Взять", l -> takeItem(item, inventory)), itemNode, itemNode.getChildCount());
                    }
                    break;
                case CONSUMABLE:
                    itemsTreeModel.insertNodeInto(new SudTreeNode("Использовать", l -> useItem(item, inventory)), itemNode, itemNode.getChildCount());
                    break;
            }
            if (item.isContainer()) {
                if (!item.isLocked()) {
                    fillItemsTree(item.getInventory(), true);
                } else {
                    itemsTreeModel.insertNodeInto(new SudTreeNode("Отпереть", l -> unlockContainer(item)), itemNode, itemNode.getChildCount());
                }
            }
            //если влокации есть контейенры, для каждого добавить опцию положить в ...
            if (!container) {
                inventory.stream().filter(itemC -> itemC.getKey().isContainer() && !itemC.getKey().isLocked() && !itemC.getKey().equals(item) && !itemC.getKey().getInventory().contains(item)).forEach(itemContained -> itemsTreeModel.insertNodeInto(new SudTreeNode("Положить в " + itemContained.getKey().getName(), l -> stashItem(itemContained.getKey(), item)), itemNode, itemNode.getChildCount()));
            }
        }
    }

    private void takeAmountOfItem(Item item, Inventory items, int count) {
        SpinnerNumberModel sModel = new SpinnerNumberModel(1, 1, count, 1);
        JSpinner spinner = new JSpinner(sModel);
        int option = JOptionPane.showOptionDialog(null, spinner, "Enter valid number", JOptionPane.OK_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null, null, null);
        if (option == JOptionPane.CANCEL_OPTION) {
            return;
        } else if (option == JOptionPane.OK_OPTION) {
            int amount = (int) spinner.getModel().getValue();
            for (int i = 0; i < amount; i++) {
                takeItem(item, items);
            }
        }
    }

    //кладем предмет в выбранный контейнер

    private void stashItem(Item container, Item item) {
        Script.run(item.getScript("_onStash").getText(), new Object[]{item, container});
        Script.run(commonScripts.get("_onPlayerStashesItem").getText(), new Object[]{item, container});
        container.addItem(item);
        currentLocation.removeItem(item);
        updateItems();
    }

    //открываем контейнер (скриптовая функция)
    private void unlockContainer(Item item) {
        Script.run(item.getScript("_onUnlock").getText(), item);
        Script.run(commonScripts.get("_onPlayerUnlocksItem").getText(), item);
        updateItems();
    }

    //Обновляем список предметов в локации
    public void updateItems() {
        ((DefaultMutableTreeNode) itemsTreeModel.getRoot()).removeAllChildren();
        itemsTreeModel.reload();
        fillItemsTree(currentLocation.getInventory(), false);

        expandAllNodes(itemsTree);
    }

    private static void expandAllNodes(JTree tree) {
        for (int i = 0; i < tree.getRowCount(); ++i) {
            tree.expandRow(i);
        }
    }

    private void parseDescription(String description) {
        description = description.replace("\"", "\\\"");
        description = description.replace("\n", "");
        description = (String) Script.run("parser(\"" + description + "\");", null);
        if (!"undefined".equals(description)) {
            output.println(description);
        }
        //Script.run("parser(\"" + description + "\");", null);
    }

    //Дизаблим кнопки передвижения соответствующие null выходам и выходам, у которых заблокирован доступ
    private void updateButtons() {
        Location north = currentLocation.getNorth();
        Location south = currentLocation.getSouth();
        Location east = currentLocation.getEast();
        Location west = currentLocation.getWest();
        Location up = currentLocation.getUp();
        Location down = currentLocation.getDown();
        boolean northEnabled = currentLocation.getNorth() != null && currentLocation.isNorthOpened();
        boolean southEnabled = currentLocation.getSouth() != null && currentLocation.isSouthOpened();
        boolean eastEnabled = currentLocation.getEast() != null && currentLocation.isEastOpened();
        boolean westEnabled = currentLocation.getWest() != null && currentLocation.isWestOpened();
        boolean upEnabled = currentLocation.getUp() != null && currentLocation.isUpOpened();
        boolean downEnabled = currentLocation.getDown() != null && currentLocation.isDownOpened();
        northButton.setEnabled(northEnabled);
        southButton.setEnabled(southEnabled);
        eastButton.setEnabled(eastEnabled);
        westButton.setEnabled(westEnabled);
        upButton.setEnabled(upEnabled);
        downButton.setEnabled(downEnabled);
        if (northEnabled)
            northButton.setToolTipText(north.getName());
        if (southEnabled)
            southButton.setToolTipText(south.getName());
        if (eastEnabled)
            eastButton.setToolTipText(east.getName());
        if (westEnabled)
            westButton.setToolTipText(west.getName());
        if (upEnabled)
            upButton.setToolTipText(up.getName());
        if (downEnabled)
            downButton.setToolTipText(down.getName());
    }

    @SuppressWarnings("unused")
    public void alert(String text) {
        JOptionPane.showMessageDialog(this, text);
    }

    public String prompt(String title, String def) {
        return JOptionPane.showInputDialog(this, title, def);
    }

    @SuppressWarnings("unused")
    public String prompt(String title) {
        return prompt(title, "");
    }

    @SuppressWarnings("unused")
    public String prompt() {
        return prompt("Введите значение", "");
    }

    @SuppressWarnings("unused")
    public boolean confirm(String question) {
        int result = JOptionPane.showConfirmDialog(this, question, "Подтвердите", JOptionPane.OK_CANCEL_OPTION);
        return result == JOptionPane.OK_OPTION;
    }

    @SuppressWarnings("unused")
    public void pause() {
        proceedButton.setEnabled(true);
        itemsTree.setEnabled(false);
        charactersTree.setEnabled(false);
        northButton.setEnabled(false);
        southButton.setEnabled(false);
        eastButton.setEnabled(false);
        westButton.setEnabled(false);
        upButton.setEnabled(false);
        downButton.setEnabled(false);
        inventoryButton.setEnabled(false);
        output.setEnabled(false);
        paused = true;
    }

    public static GameCharacter getPlayer() {
        return player;
    }

    public HashMap<String, Script> getCommonScripts() {
        return commonScripts;
    }
}
