package ru.ekipogh.sud;

import javax.swing.*;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreePath;
import javax.swing.tree.TreeSelectionModel;
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
    private JPopupMenu popupMenu;
    private final String ONTAKE = "_onTake";
    private final String ONEQUIP = "_onEquip";
    private final String ONUSE = "_onUse";
    private final String ONENTER = "_onEnter";
    private final String ONLEAVE = "_onLeave";
    private final String ONPLAYERARRIVE = "_onPlayerArrive";
    private final String ONPLAYERLEAVE = "_onPlayerLeave";

    private static Location currentLocation;
    private List<Item> items; //TODO: нужно ли? Наверное нужно для скриптов. Скорее нужно
    private String gamePath;
    private String savePath;

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
        loadSave.addActionListener(e -> loadSave());
        saveSave.addActionListener(e -> saveSave());
        saveSaveAs.addActionListener(e -> saveSaveAs());

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

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                Main.launcher.setVisible(true);
                super.windowClosing(e);
            }
        });

        jsInputField.addActionListener(e -> runScript());
    }

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

    private void showPlayerPopUp(MouseEvent e) {
        JMenuItem menuItem;
        popupMenu.removeAll();
        for (Map.Entry<String, Script> entry : player.getScripts().entrySet()) {
            if (!entry.getKey().startsWith("_on") && entry.getValue().isEnabled()) {
                menuItem = new JMenuItem(entry.getKey());
                menuItem.addActionListener(ev -> Script.run(player.getScript(entry.getKey()).getText(), player));
                popupMenu.add(menuItem);
            }
        }
        menuItem = new JMenuItem("Описание");
        menuItem.addActionListener(ev -> output.println(parseDescription(player.getDescription(), player)));
        popupMenu.add(menuItem);
        popupMenu.show(playerName, e.getX(), e.getY());
    }

    private void runScript() {
        Script.run(jsInputField.getText(), player);
    }

    private void showCharDescription(GameCharacter character) {
        output.println(parseDescription(character.getDescription(), character));
    }

    private void showLocationPopup(MouseEvent event) {
        if (SwingUtilities.isRightMouseButton(event)) {
            JMenuItem menuItem;
            popupMenu.removeAll();
            for (Map.Entry<String, Script> entry : currentLocation.getScripts().entrySet())
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

    private void showItemDescription(Item item) {
        output.println(parseDescription(item.getDescription(), item));
    }

    private void useItem(Item item) {
        Script.run(item.getScript(ONUSE).getText(), item);
        for (ItemCategory category : item.getCategories())
            Script.run(category.getScript(ONUSE).getText(), item);
        ((DefaultMutableTreeNode) itemsTreeModel.getRoot()).remove((DefaultMutableTreeNode) itemsTree.getSelectionPath().getParentPath().getLastPathComponent());
        itemsTreeModel.reload();
    }

    //надеть предмет
    private void equipItem(Item item) {
        currentLocation.removeItem(item);
        player.equip(item);
        ((DefaultMutableTreeNode) itemsTreeModel.getRoot()).remove((DefaultMutableTreeNode) itemsTree.getSelectionPath().getParentPath().getLastPathComponent());
        itemsTreeModel.reload();
        Script.run(item.getScript(ONEQUIP).getText(), item);
        for (ItemCategory category : item.getCategories())
            Script.run(category.getScript(ONEQUIP).getText(), item);
    }

    //положить предмет в инвентарь игрока
    private void moveItemToPlayerInventory(Item item) {
        ((DefaultMutableTreeNode) itemsTreeModel.getRoot()).remove((DefaultMutableTreeNode) itemsTree.getSelectionPath().getParentPath().getLastPathComponent());
        itemsTreeModel.reload();
        currentLocation.removeItem(item);
        player.addToInventory(item);
        Script.run(item.getScript(ONTAKE).getText(), item);
        for (ItemCategory category : item.getCategories())
            Script.run(category.getScript(ONTAKE).getText(), item);
    }

    //передвижение игрока
    private void move(int direction) {
        Location playerLocation = null;
        switch (direction) {
            case NORTH:
                playerLocation = player.getLocation().getNorth(); //Север
                break;
            case SOUTH:
                playerLocation = player.getLocation().getSouth(); //Юг
                break;
            case EAST:
                playerLocation = player.getLocation().getEast(); //Восток
                break;
            case WEST:
                playerLocation = player.getLocation().getWest(); //Запад
                break;
            case UP:
                playerLocation = player.getLocation().getUp();
                break;
            case DOWN:
                playerLocation = player.getLocation().getDown();
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
        ((DefaultMutableTreeNode) charactersTreeModel.getRoot()).removeAllChildren();
        charactersTreeModel.reload();
        characters.stream().filter(c -> currentLocation.equals(c.getLocation())).forEach(c -> {
            DefaultMutableTreeNode characterNode = new DefaultMutableTreeNode(c);
            DefaultMutableTreeNode top = (DefaultMutableTreeNode) charactersTreeModel.getRoot();
            charactersTreeModel.insertNodeInto(characterNode, top, top.getChildCount());
            c.getScripts().entrySet().stream().filter(entry -> !entry.getKey().startsWith("_on") && entry.getValue().isEnabled()).forEach(entry -> charactersTreeModel.insertNodeInto(new SudTreeNode(entry.getKey(), l -> Script.run(entry.getValue().getText(), c)), characterNode, characterNode.getChildCount()));
            /*if (c.getCategories() != null)
                c.getCategories().getScripts().entrySet().stream().filter(entry -> !entry.getKey().startsWith("_on") && entry.getValue().isEnabled()).forEach(entry -> charactersTreeModel.insertNodeInto(new SudTreeNode(entry.getKey(), l -> Script.run(c.getScript(entry.getKey()).getText(), c)), characterNode, characterNode.getChildCount()));*/
            for (CharacterCategory characterCategory : c.getCategories()) {
                characterCategory.getScripts().entrySet().stream().filter(entry -> !entry.getKey().startsWith("_on") && entry.getValue().isEnabled()).forEach(entry -> charactersTreeModel.insertNodeInto(new SudTreeNode(entry.getKey(), l -> Script.run(entry.getValue().getText(), c)), characterNode, characterNode.getChildCount()));
            }
            charactersTreeModel.insertNodeInto(new SudTreeNode("Описание", l -> showCharDescription(c)), characterNode, characterNode.getChildCount());
        });
    }

    //Обновляем список предметов в локации
    public void updateItems() {
        ((DefaultMutableTreeNode) itemsTreeModel.getRoot()).removeAllChildren();
        itemsTreeModel.reload();
        currentLocation.getInventory().forEach(i -> {
            DefaultMutableTreeNode itemNode = new DefaultMutableTreeNode(i);
            DefaultMutableTreeNode top = (DefaultMutableTreeNode) itemsTreeModel.getRoot();
            itemsTreeModel.insertNodeInto(itemNode, top, top.getChildCount());
            i.getScripts().entrySet().stream().filter(entry -> !entry.getKey().startsWith("_on") && entry.getValue().isEnabled()).forEach(entry -> itemsTreeModel.insertNodeInto(new SudTreeNode(entry.getKey(), l -> Script.run(entry.getValue().getText(), i)), itemNode, itemNode.getChildCount()));
            for (ItemCategory category : i.getCategories())
                category.getScripts().entrySet().stream().filter(entry -> !entry.getKey().startsWith("_on") && entry.getValue().isEnabled()).forEach(entry -> itemsTreeModel.insertNodeInto(new SudTreeNode(entry.getKey(), l -> Script.run(entry.getValue().getText(), i)), itemNode, itemNode.getChildCount()));
            itemsTreeModel.insertNodeInto(new SudTreeNode("Описание", l -> showItemDescription(i)), itemNode, itemNode.getChildCount());
            if (i.getType() == ItemTypes.EQUIPPABLE)
                itemsTreeModel.insertNodeInto(new SudTreeNode("Экипировать", l -> equipItem(i)), itemNode, itemNode.getChildCount());
            if (i.getType() == ItemTypes.EQUIPPABLE || i.getType() == ItemTypes.STORABLE)
                itemsTreeModel.insertNodeInto(new SudTreeNode("Взять", l -> moveItemToPlayerInventory(i)), itemNode, itemNode.getChildCount());
            if (i.getType() == ItemTypes.CONSUMABLE)
                itemsTreeModel.insertNodeInto(new SudTreeNode("Использовать", l -> useItem(i)), itemNode, itemNode.getChildCount());
        });
    }

    private String parseDescription(String description, Object object) {
        String results = "";
        Matcher m = Pattern.compile("\\<\\<(.*?)\\>\\>").matcher(description);
        if (m.find()) {
            results = m.replaceAll(String.valueOf(Script.run(m.group(1), object)));
        }
        return results;
    }

    //Дизаблим кнопки передвижения соответствующие null выходам и выходам, у которых заблокирован доступ
    private void directionButtonsEnable() {
        northButton.setEnabled((currentLocation.getNorth() != null && currentLocation.isNorthOpened()));
        southButton.setEnabled((currentLocation.getSouth() != null && currentLocation.isSouthOpened()));
        eastButton.setEnabled((currentLocation.getEast() != null && currentLocation.isEastOpened()));
        westButton.setEnabled((currentLocation.getWest() != null && currentLocation.isWestOpened()));
    }

    public static GameCharacter getPlayer() {
        return player;
    }
}
