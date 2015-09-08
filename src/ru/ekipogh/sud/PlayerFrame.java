package ru.ekipogh.sud;

import javax.swing.*;
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
    private final int NORTH = 0;
    private final int SOUTH = 1;
    private final int EAST = 2;
    private final int WEST = 3;

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
        setSize(1024, 768);

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

    private void showItemDescription(Item item) {
        output.println(parseDescription(item.getDescription(), item));
    }

    private void useItem(Item item) {
        Script.run(item.getScript(ONUSE), item);
        if (item.getCategory() != null)
            Script.run(item.getCategory().getScript(ONUSE), item);
        ((DefaultMutableTreeNode) itemsTreeModel.getRoot()).remove((DefaultMutableTreeNode) itemsTree.getSelectionPath().getParentPath().getLastPathComponent());
        itemsTreeModel.reload();
    }

    //надеть предмет
    private void equipItem(Item item) {
        currentLocation.removeItem(item);
        player.equip(item);
        ((DefaultMutableTreeNode) itemsTreeModel.getRoot()).remove((DefaultMutableTreeNode) itemsTree.getSelectionPath().getParentPath().getLastPathComponent());
        itemsTreeModel.reload();
        Script.run(item.getScript(ONEQUIP), item);
        if (item.getCategory() != null)
            Script.run(item.getCategory().getScript(ONEQUIP), item);
    }

    //положить предмет в инвентарь игрока
    private void moveItemToPlayerInventory(Item item) {
        ((DefaultMutableTreeNode) itemsTreeModel.getRoot()).remove((DefaultMutableTreeNode) itemsTree.getSelectionPath().getParentPath().getLastPathComponent());
        itemsTreeModel.reload();
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
        characters.stream().filter(c -> currentLocation.equals(c.getLocation())).forEach(c -> {
            if (c.getCategory() != null)
                Script.run(c.getCategory().getScript(ONPLAYERLEAVE), c);
            Script.run(c.getScript(ONPLAYERLEAVE), c);
        });

        currentLocation = playerLocation != null ? playerLocation : currentLocation;
        player.setLocation(currentLocation);

        updateCharacters();

        characters.stream().filter(c -> currentLocation.equals(c.getLocation())).forEach(c -> {
            if (c.getCategory() != null)
                Script.run(c.getCategory().getScript(ONPLAYERARRIVE), c);
            Script.run(c.getScript(ONPLAYERARRIVE), c);
        });

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
        ((DefaultMutableTreeNode) charactersTreeModel.getRoot()).removeAllChildren();
        charactersTreeModel.reload();
        characters.stream().filter(c -> currentLocation.equals(c.getLocation())).forEach(c -> {
            DefaultMutableTreeNode characterNode = new DefaultMutableTreeNode(c);
            DefaultMutableTreeNode top = (DefaultMutableTreeNode) charactersTreeModel.getRoot();
            charactersTreeModel.insertNodeInto(characterNode, top, top.getChildCount());
            c.getScripts().keySet().stream().filter(scriptName -> !scriptName.startsWith("_on")).forEach(scriptName -> charactersTreeModel.insertNodeInto(new SudTreeNode(scriptName, l -> Script.run(c.getScript(scriptName), c)), characterNode, characterNode.getChildCount()));
            if (c.getCategory() != null)
                c.getCategory().getScripts().keySet().stream().filter(scriptName -> !scriptName.startsWith("_on")).forEach(scriptName -> charactersTreeModel.insertNodeInto(new SudTreeNode(scriptName, l -> Script.run(c.getCategory().getScript(scriptName), c)), characterNode, characterNode.getChildCount()));
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
            i.getScripts().entrySet().stream().filter(script -> !script.getKey().startsWith("_on")).forEach(script -> itemsTreeModel.insertNodeInto(new SudTreeNode(script.getKey(), l -> Script.run(script.getValue(), i)), itemNode, itemNode.getChildCount()));
            if (i.getCategory() != null)
                i.getCategory().getScripts().entrySet().stream().filter(script -> !script.getKey().startsWith("_on")).forEach(script -> itemsTreeModel.insertNodeInto(new SudTreeNode(script.getKey(), l -> Script.run(script.getValue(), i)), itemNode, itemNode.getChildCount()));
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
