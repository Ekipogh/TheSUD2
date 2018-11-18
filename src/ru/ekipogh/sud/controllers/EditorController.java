package ru.ekipogh.sud.controllers;

import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.embed.swing.SwingNode;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;
import org.fife.ui.rsyntaxtextarea.RSyntaxTextArea;
import ru.ekipogh.sud.*;
import ru.ekipogh.sud.behavior.*;
import ru.ekipogh.sud.objects.*;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.*;

/**
 * Created by Ektril Pogh on 13.06.2018.
 */
public class EditorController {
    //Game tab
    //Common tab
    public TextField gameName;
    public TextArea gameDescription;
    //Init script tab
    public SwingNode initScriptNode;
    //Game scripts tab
    public ListView<String> gameScriptsList;
    public SwingNode gameScriptsNode;
    //Timers tab
    public ListView<SudTimer> timersList;
    public TextField timerName;
    public TextField timerStep;
    public SwingNode timerScriptNode;
    //Items Tab
    public ListView<Item> itemsList;
    //Common tab
    public Text itemId;
    public TextField itemName;
    public TextArea itemDescription;
    public CheckBox isItemInventory;
    public ChoiceBox<ItemTypes> itemType;
    public CheckBox isItemLocked;
    public ChoiceBox<String> itemEquipmentSlot;
    //Scripts tab
    public ListView<String> itemScriptsList;
    public SwingNode itemScriptNode;
    public CheckBox itemScriptEnabled;
    //Categories tab
    public ListView<ItemCategory> itemsCategories;
    public ListView<ItemCategory> itemCategories;
    //Parameters tab
    public TableView<Map.Entry<String, String>> itemParameters;
    public TableColumn<Map.Entry<String, String>, String> itemKeyColumn;
    public TableColumn<Map.Entry<String, String>, String> itemValueColumn;
    //Locations tab
    public ListView<Location> locationsList;
    //Common tab
    public Text locationId;
    public TextField locationName;
    public TextArea locationDescription;
    //Exits tab
    public ChoiceBox<Location> locationNorthExit;
    public ChoiceBox<Location> locationSouthExit;
    public ChoiceBox<Location> locationWestExit;
    public ChoiceBox<Location> locationEastExit;
    public ChoiceBox<Location> locationUpExit;
    public ChoiceBox<Location> locationDownExit;
    //Scripts tab
    public ListView<String> locationScriptsList;
    public SwingNode locationScriptNode;
    public CheckBox locationScriptEnabled;
    //Items tab
    public ListView<Item> locationAllItemsList;
    public ListView<SudPair<Item, Integer>> locationItemsList;
    //Categories tab
    public ListView<LocationCategory> locationsCategories;
    public ListView<GameObjectCategory> locationCategories;
    //Parameters tab
    public TableView<Map.Entry<String, String>> locationParameters;
    public TableColumn<Map.Entry<String, String>, String> locationKeyColumn;
    public TableColumn<Map.Entry<String, String>, String> locationValueColumn;
    //Characters tab
    //Common tab
    public ListView<GameCharacter> charactersList;
    public Text characterId;
    public TextField characterName;
    public TextArea characterDescription;
    public ChoiceBox<Location> characterLocation;
    //Scripts tab
    public ListView<String> characterScriptsList;
    public SwingNode characterScriptNode;
    public CheckBox characterScriptEnabled;
    //Items tab
    public ListView<Item> characterAllItemsList;
    public ListView<SudPair<Item, Integer>> characterItemsList;
    //Equipment tab
    public TableView<Map.Entry<String, Item>> characterEquipmentTable;
    public ListView<Item> equipmentAllList;
    public TableColumn<Map.Entry<String, Item>, String> equipmentTableSlot;
    public TableColumn<Map.Entry<String, Item>, String> equipmentTableItem;
    //Parameters tab
    public TableView<Map.Entry<String, String>> characterParameters;
    public TableColumn<Map.Entry<String, String>, String> characterKeyColumn;
    public TableColumn<Map.Entry<String, String>, String> characterValueColumn;
    //Categories tab
    public ListView<CharacterCategory> charactersCategories;
    public ListView<GameObjectCategory> characterCategories;
    //Behavior tab
    public TreeView<BTreeNode> characterBehaviorTree;
    public SwingNode characterBehaviorScriptNode;
    public ContextMenu characterBehaviorTreeMenu;
    //Player
    //Common tab
    public TextField playerName;
    public TextArea playerDescription;
    public ChoiceBox<Location> playerLocation;
    //Scripts tab
    public ListView<String> playerScriptsList;
    public SwingNode playerScriptNode;
    public CheckBox playerScriptEnabled;
    //Items
    public ListView<Item> playerAllItemsList;
    public ListView<SudPair<Item, Integer>> playerItemsList;
    //Equipment
    public TableView<Map.Entry<String, Item>> playerEquipmentTable;
    public TableColumn<Map.Entry<String, Item>, String> playerEquipmentTableSlot;
    public TableColumn<Map.Entry<String, Item>, String> playerEquipmentTableItem;
    public ListView<Item> playerEquipmentAllList;
    //Categories
    public ListView<CharacterCategory> playerAllCategories;
    public ListView<GameObjectCategory> playerCategories;
    //Parameters
    public TableView<Map.Entry<String, String>> playerParameters;
    public TableColumn<Map.Entry<String, String>, String> playerKeyColumn;
    public TableColumn<Map.Entry<String, String>, String> playerValueColumn;
    //Behavior
    public TreeView<BTreeNode> playerBehaviorTree;
    public ContextMenu playerBehaviorTreeMenu;
    public SwingNode playerBehaviorScriptNode;
    //Categories
    //Locations
    public ListView<LocationCategory> locationCategoriesList;
    public TextField locationCategoryName;
    public ListView<String> locationCategoriesScriptsList;
    public SwingNode locationCategoryScriptNode;
    public CheckBox locationCategoryScriptEnabled;
    //Items
    public ListView<ItemCategory> itemCategoriesList;
    public TextField itemCategoryName;
    public ListView<String> itemCategoriesScriptsList;
    public SwingNode itemCategoryScriptNode;
    public CheckBox itemCategoryScriptEnabled;
    //Character
    public ListView<CharacterCategory> characterCategoriesList;
    public TextField characterCategoryName;
    public ListView<String> characterCategoriesScriptsList;
    public SwingNode characterCategoryScriptNode;
    public CheckBox characterCategoryScriptEnabled;
    //Equipment Slots tab
    public TableView<String[]> equipmentSlotsTable;
    public TableColumn<String[], String> equipmentSlotsTablePathColumn;
    public TableColumn<String[], ImageView> equipmentSlotsTableIconColumn;
    public TableColumn<String[], String> equipmentSlotsTableNameColumn;


    private GameFile gameFile;

    private static ObservableValue<String> mapKey(TableColumn.CellDataFeatures<Map.Entry<String, String>, String> p) {
        return new SimpleStringProperty(p.getValue().getKey());
    }

    private static ObservableValue<String> mapValue(TableColumn.CellDataFeatures<Map.Entry<String, String>, String> p) {
        return new SimpleStringProperty(p.getValue().getValue());
    }

    private static ObservableValue<String> mapItemKey(TableColumn.CellDataFeatures<Map.Entry<String, Item>, String> p) {
        return new SimpleStringProperty(p.getValue().getKey());
    }

    private static ObservableValue<String> mapItemValue(TableColumn.CellDataFeatures<Map.Entry<String, Item>, String> p) {
        return new SimpleStringProperty(p.getValue().getValue().toString());
    }


    @FXML
    public void initialize() {
        System.out.println("Editor initializing");
        //Syntax areas
        //Common tab
        initScriptNode.setContent(new RSyntaxTextArea());
        gameScriptsNode.setContent(new RSyntaxTextArea());
        timerScriptNode.setContent(new RSyntaxTextArea());
        //Items tab
        itemScriptNode.setContent(new RSyntaxTextArea());
        //Location tab
        locationScriptNode.setContent(new RSyntaxTextArea());
        //Character tab
        characterScriptNode.setContent(new RSyntaxTextArea());
        characterBehaviorScriptNode.setContent(new RSyntaxTextArea());
        //Player tab
        playerScriptNode.setContent(new RSyntaxTextArea());
        playerBehaviorScriptNode.setContent(new RSyntaxTextArea());
        //Categories tab
        locationCategoryScriptNode.setContent(new RSyntaxTextArea());
        itemCategoryScriptNode.setContent(new RSyntaxTextArea());
        characterCategoryScriptNode.setContent(new RSyntaxTextArea());

        //Lists listeners
        //Common tab
        gameScriptsList.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) ->
                selectGameScript(newValue));
        timersList.getSelectionModel().selectedItemProperty().addListener(((observable, oldValue, newValue) ->
                selectTimer(newValue)));
        //Items tab
        itemsList.getSelectionModel().selectedItemProperty().addListener(((observable, oldValue, newValue) ->
                selectItem(newValue)));
        itemScriptsList.getSelectionModel().selectedItemProperty().addListener(((observable, oldValue, newValue) ->
                selectItemScript(newValue)));
        //Location tab
        locationsList.getSelectionModel().selectedItemProperty().addListener(((observable, oldValue, newValue) ->
                selectLocation(newValue)));
        locationScriptsList.getSelectionModel().selectedItemProperty().addListener(((observable, oldValue, newValue) ->
                selectLocationScript(newValue)));
        //Characters tab
        charactersList.getSelectionModel().selectedItemProperty().addListener(((observable, oldValue, newValue) ->
                selectCharacter(newValue)));
        characterScriptsList.getSelectionModel().selectedItemProperty().addListener(((observable, oldValue, newValue) ->
                selectCharacterScript(newValue)));
        characterBehaviorTree.getSelectionModel().selectedItemProperty().addListener(((observable, oldValue, newValue) ->
                selectCharacterBehaviorNode(newValue)));
        //Player tab
        playerScriptsList.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) ->
                selectPlayerScript(newValue));
        playerBehaviorTree.getSelectionModel().selectedItemProperty().addListener(((observable, oldValue, newValue) ->
                selectPlayerBehaviorNode(newValue)));
        //Categories tab
        locationCategoriesList.getSelectionModel().selectedItemProperty().addListener(((observable, oldValue, newValue) -> selectLocationCategory(newValue)));
        locationCategoriesScriptsList.getSelectionModel().selectedItemProperty().addListener(((observable, oldValue, newValue) -> selectLocationCategoryScript(newValue)));
        itemCategoriesList.getSelectionModel().selectedItemProperty().addListener(((observable, oldValue, newValue) -> selectItemCategory(newValue)));
        itemCategoriesScriptsList.getSelectionModel().selectedItemProperty().addListener(((observable, oldValue, newValue) -> selectItemCategoryScript(newValue)));
        characterCategoriesList.getSelectionModel().selectedItemProperty().addListener(((observable, oldValue, newValue) -> selectCharacterCategory(newValue)));
        characterCategoriesScriptsList.getSelectionModel().selectedItemProperty().addListener(((observable, oldValue, newValue) -> selectCharacterCategoryScript(newValue)));

        gameFile = new GameFile();
        ScreenController.setController("editor", this);

        for (BehaviorTree.TYPES type : BehaviorTree.TYPES.values()) {
            MenuItem itemCharacter = new MenuItem(type.toString());
            itemCharacter.setOnAction(event -> addBehaviorNode(type, false));
            MenuItem itemPlayer = new MenuItem(type.toString());
            itemPlayer.setOnAction(event -> addBehaviorNode(type, true));
            characterBehaviorTreeMenu.getItems().add(itemCharacter);
            playerBehaviorTreeMenu.getItems().add(itemPlayer);
        }

        System.out.println("Editor initialized");
    }

    private void selectCharacterCategoryScript(String scriptName) {
        CharacterCategory category = characterCategoriesList.getSelectionModel().getSelectedItem();
        if (category != null && scriptName != null) {
            Script script = category.getScript(scriptName);
            ((RSyntaxTextArea) characterCategoryScriptNode.getContent()).setText(script.getText());
            characterCategoryScriptEnabled.setSelected(script.isEnabled());
        }
    }

    private void selectCharacterCategory(CharacterCategory category) {
        if (category != null) {
            String name = category.getName();
            characterCategoryName.setText(name);
            characterCategoriesScriptsList.getItems().setAll(category.getScripts().keySet());
        }
    }

    private void selectItemCategoryScript(String scriptName) {
        ItemCategory category = itemCategoriesList.getSelectionModel().getSelectedItem();
        if (category != null && scriptName != null) {
            Script script = category.getScript(scriptName);
            ((RSyntaxTextArea) itemCategoryScriptNode.getContent()).setText(script.getText());
            itemCategoryScriptEnabled.setSelected(script.isEnabled());
        }
    }

    private void selectItemCategory(ItemCategory category) {
        if (category != null) {
            String name = category.getName();
            itemCategoryName.setText(name);
            itemCategoriesScriptsList.getItems().setAll(category.getScripts().keySet());
        }
    }

    private void selectLocationCategoryScript(String scriptName) {
        LocationCategory category = locationCategoriesList.getSelectionModel().getSelectedItem();
        if (category != null && scriptName != null) {
            Script script = category.getScript(scriptName);
            ((RSyntaxTextArea) locationCategoryScriptNode.getContent()).setText(script.getText());
            locationCategoryScriptEnabled.setSelected(script.isEnabled());
        }
    }

    private void selectLocationCategory(LocationCategory category) {
        if (category != null) {
            String name = category.getName();
            locationCategoryName.setText(name);
            locationCategoriesScriptsList.getItems().setAll(category.getScripts().keySet());
        }
    }

    private void selectPlayerBehaviorNode(TreeItem<BTreeNode> treeItem) {
        if (treeItem != null) {
            BTreeNode node = treeItem.getValue();
            if (node.getClass() == TaskNode.class) {
                ((RSyntaxTextArea) playerBehaviorScriptNode.getContent()).setText(((TaskNode) node).getScript().getText());
            } else {
                ((RSyntaxTextArea) playerBehaviorScriptNode.getContent()).setText("");
            }
        }
    }

    private void selectPlayerScript(String scriptName) {
        Script script = gameFile.getPlayer().getScript(scriptName);
        String scriptText = script.getText();
        boolean enabled = script.isEnabled();
        ((RSyntaxTextArea) playerScriptNode.getContent()).setText(scriptText);
        playerScriptEnabled.setSelected(enabled);
    }

    private void selectCharacterBehaviorNode(TreeItem<BTreeNode> treeItem) {
        if (treeItem != null) {
            BTreeNode node = treeItem.getValue();
            if (node.getClass() == TaskNode.class) {
                ((RSyntaxTextArea) characterBehaviorScriptNode.getContent()).setText(((TaskNode) node).getScript().getText());
            } else {
                ((RSyntaxTextArea) characterBehaviorScriptNode.getContent()).setText("");
            }
        }
    }

    private void addBehaviorNode(BehaviorTree.TYPES type, boolean isPlayer) {
        GameCharacter character;
        TreeItem<BTreeNode> node;
        TreeItem<BTreeNode> root;

        if (isPlayer && gameFile != null) {
            character = gameFile.getPlayer();
            node = playerBehaviorTree.getSelectionModel().getSelectedItem();
            root = playerBehaviorTree.getRoot();
        } else {
            character = charactersList.getSelectionModel().getSelectedItem();
            node = characterBehaviorTree.getSelectionModel().getSelectedItem();
            root = characterBehaviorTree.getRoot();
        }
        if (character != null && node != null && node.getValue().getClass() != TaskNode.class) {
            if (!node.equals(root) || (node.equals(root) && root.getChildren().size() == 0)) {
                switch (type) {
                    case SELECTOR:
                        Selector selector = new Selector();
                        node.getValue().addChild(selector);
                        TreeItem<BTreeNode> treeItem = new TreeItem<>(selector);
                        treeItem.setExpanded(true);
                        node.getChildren().add(treeItem);
                        break;
                    case SEQUENCE:
                        Sequence sequence = new Sequence();
                        node.getValue().addChild(sequence);
                        TreeItem<BTreeNode> treeItem1 = new TreeItem<>(sequence);
                        treeItem1.setExpanded(true);
                        node.getChildren().add(treeItem1);
                        break;
                    case TASK:
                        TaskNode taskNode = new TaskNode(character);
                        node.getValue().addChild(taskNode);
                        node.getChildren().add(new TreeItem<>(taskNode));
                        break;
                }
            }
        }
    }


    //Selectors
    private void selectGameScript(String scriptName) {
        ((RSyntaxTextArea) gameScriptsNode.getContent()).setText(gameFile.getCommonScripts().get(scriptName).getText());
    }

    private void selectTimer(SudTimer timer) {
        if (timer != null) {
            String name = timer.getTimerName();
            if (name != null) {
                int step = timer.getStep();
                String script = timer.getScript();
                timerName.setText(name);
                timerStep.setText(String.valueOf(step));
                ((RSyntaxTextArea) timerScriptNode.getContent()).setText(script);
            }
        }
    }

    private void selectItem(Item item) {
        if (item != null) {
            //Common tab
            int id = item.getId();
            String name = item.getName();
            String description = item.getDescription();
            boolean isInventory = item.isContainer();
            boolean isLocked = item.isLocked();
            ItemTypes type = item.getType();
            String slot = item.getEquipmentSlot();

            itemId.setText(String.valueOf(id));
            itemName.setText(name);
            itemDescription.setText(description);
            isItemInventory.setSelected(isInventory);
            isItemLocked.setSelected(isLocked);
            itemType.getSelectionModel().select(type);
            itemEquipmentSlot.getSelectionModel().select(slot);
            //Scripts tab
            Set<String> scriptNames = item.getScripts().keySet();
            itemScriptsList.getItems().setAll(scriptNames);
            //Categories tab
            itemCategories.getItems().clear();
            List<GameObjectCategory> categories = item.getCategories();
            categories.forEach(gameObjectCategory -> itemCategories.getItems().add(((ItemCategory) gameObjectCategory)));
            //Parameters tab
            HashMap<String, String> values = item.getValues();
            ObservableList<Map.Entry<String, String>> items = FXCollections.observableArrayList(values.entrySet());
            itemParameters.getItems().setAll(items);
            itemParameters.getColumns().setAll(itemKeyColumn, itemValueColumn);
        }
    }

    private void selectLocation(Location location) {
        if (location != null) {
            //Common tab
            int id = location.getId();
            String name = location.getName();
            String description = location.getDescription();

            locationId.setText(String.valueOf(id));
            locationName.setText(name);
            locationDescription.setText(description);
            //Exist tab
            locationNorthExit.getSelectionModel().select(location.getNorth());
            locationSouthExit.getSelectionModel().select(location.getSouth());
            locationWestExit.getSelectionModel().select(location.getWest());
            locationEastExit.getSelectionModel().select(location.getEast());
            locationUpExit.getSelectionModel().select(location.getUp());
            locationDownExit.getSelectionModel().select(location.getDown());
            //Scripts tab
            locationScriptsList.getItems().setAll(location.getScripts().keySet());
            selectLocationScript(null);
            locationScriptsList.refresh();
            //Items tab
            locationItemsList.getItems().setAll(location.getInventory().getItems());
            locationItemsList.refresh();
            //Categories tab
            locationCategories.getItems().setAll(location.getCategories());
            locationCategories.refresh();
            //Parameters tab
            HashMap<String, String> values = location.getValues();
            ObservableList<Map.Entry<String, String>> items = FXCollections.observableArrayList(values.entrySet());
            locationParameters.getItems().setAll(items);
            locationParameters.getColumns().setAll(locationKeyColumn, locationValueColumn);
        }
    }

    private void selectCharacter(GameCharacter character) {
        if (character != null) {
            int id = character.getId();
            String name = character.getName();
            String description = character.getDescription();
            Location location = character.getLocation();

            //Common tab
            characterId.setText(String.valueOf(id));
            characterName.setText(name);
            characterDescription.setText(description);
            characterLocation.getSelectionModel().select(location);
            //Scripts tab
            characterScriptsList.getItems().setAll(character.getScripts().keySet());
            selectCharacterScript(null);
            characterScriptsList.refresh();
            //Items tab
            characterItemsList.getItems().setAll(character.getInventory().getItems());
            //Equipment tab
            Map<String, Item> equipment = character.getEquipment().getSlots();
            ObservableList<Map.Entry<String, Item>> equipmentItems = FXCollections.observableArrayList(equipment.entrySet());
            characterEquipmentTable.getItems().setAll(equipmentItems);
            characterEquipmentTable.getColumns().setAll(equipmentTableSlot, equipmentTableItem);
            //Parameters tab
            HashMap<String, String> values = character.getValues();
            ObservableList<Map.Entry<String, String>> items = FXCollections.observableArrayList(values.entrySet());
            characterParameters.getItems().setAll(items);
            characterParameters.getColumns().setAll(characterKeyColumn, characterValueColumn);
            //Categories tab
            characterCategories.getItems().setAll(character.getCategories());
            characterCategories.refresh();
            //Behavior tab
            BehaviorTree tree = character.getBtree();
            TreeItem<BTreeNode> root = new TreeItem<>(tree);
            root.setExpanded(true);
            selectCharacterBehaviorNode(root);
            characterBehaviorTree.setRoot(root);
            populateBehaviorTree(tree, root);
        }
    }

    private void populateBehaviorTree(BTreeNode node, TreeItem<BTreeNode> item) {
        for (BTreeNode child : node.getChildren()) {
            TreeItem<BTreeNode> treeItem = new TreeItem<>(child);
            treeItem.setExpanded(true);
            item.getChildren().add(treeItem);
            populateBehaviorTree(child, treeItem);
        }
    }

    private void selectCharacterScript(String scriptName) {
        GameCharacter character = charactersList.getSelectionModel().getSelectedItem();
        if (character != null && scriptName != null) {
            Script script = character.getScript(scriptName);
            String text = script.getText();
            ((RSyntaxTextArea) characterScriptNode.getContent()).setText(text);
            characterScriptEnabled.setSelected(script.isEnabled());
        } else {
            ((RSyntaxTextArea) characterScriptNode.getContent()).setText("");
            characterScriptEnabled.setSelected(false);
        }
    }

    private void selectItemScript(String scriptName) {
        Item item = itemsList.getSelectionModel().getSelectedItem();
        if (item != null && scriptName != null) {
            Script script = item.getScript(scriptName);
            ((RSyntaxTextArea) itemScriptNode.getContent()).setText(script.getText());
            itemScriptEnabled.setSelected(script.isEnabled());
        } else {
            ((RSyntaxTextArea) itemScriptNode.getContent()).setText("");
            itemScriptEnabled.setSelected(false);
        }
    }

    public void openFile() {
        GameFile newGameFile = GameFile.open();
        if (newGameFile != null) {
            gameFile = newGameFile;
            editorInit();
        }
    }

    private void editorInit() {
        System.out.println("Editor init");
        //Common tab
        gameName.setText(gameFile.getGameName());
        gameDescription.setText(gameFile.getGameStartMessage());
        ((RSyntaxTextArea) initScriptNode.getContent()).setText(gameFile.getInitScript());
        gameScriptsList.getItems().setAll(gameFile.getCommonScripts().keySet());
        timersList.getItems().setAll(gameFile.getTimers());
        //Items tab
        itemsList.getItems().setAll(gameFile.getItems());
        initEquipment();
        initItemCategories();
        initItemParametersTable();
        //Characters
        initEquipmentTable();
        initCharacterParametersTable();
        //Player
        initPlayerEquipmentTable();
        initPlayerParametersTable();
        //Location tab
        locationsList.getItems().setAll(gameFile.getLocations());
        locationNorthExit.getItems().setAll(gameFile.getLocations());
        locationNorthExit.getItems().add(null);
        locationSouthExit.getItems().setAll(gameFile.getLocations());
        locationSouthExit.getItems().add(null);
        locationWestExit.getItems().setAll(gameFile.getLocations());
        locationWestExit.getItems().add(null);
        locationEastExit.getItems().setAll(gameFile.getLocations());
        locationEastExit.getItems().add(null);
        locationUpExit.getItems().setAll(gameFile.getLocations());
        locationUpExit.getItems().add(null);
        locationDownExit.getItems().setAll(gameFile.getLocations());
        locationDownExit.getItems().add(null);
        locationAllItemsList.getItems().setAll(gameFile.getItems());
        locationsCategories.getItems().setAll(gameFile.getLocationCategories());
        initLocationParametersTable();
        //Characters tab
        charactersList.getItems().setAll(gameFile.getCharacters());
        characterLocation.getItems().setAll(gameFile.getLocations());
        characterLocation.getItems().add(null);
        characterAllItemsList.getItems().setAll(gameFile.getItems());
        List<Item> equipable = new ArrayList<>();
        for (Item item : gameFile.getItems()) {
            if (item.getType() == ItemTypes.EQUIPPABLE) {
                equipable.add(item);
            }
        }
        equipmentAllList.getItems().setAll(equipable);
        Sequencer.setID(gameFile.getSequencerID());
        charactersCategories.getItems().setAll(gameFile.getCharacterCategories());
        //Player tab
        initPlayer();
        playerEquipmentAllList.getItems().setAll(gameFile.getItems());
        //Categories
        //Locations
        locationCategoriesList.getItems().setAll(gameFile.getLocationCategories());
        itemCategoriesList.getItems().setAll(gameFile.getItemCategories());
        characterCategoriesList.getItems().setAll(gameFile.getCharacterCategories());
        //Slots tab
        initSlotsTable();
    }

    private void initSlotsTable() {
        equipmentSlotsTable.getItems().clear();
        try {
            equipmentSlotsTablePathColumn.setCellValueFactory(param -> new SimpleStringProperty(param.getValue()[1]));
            equipmentSlotsTablePathColumn.setCellFactory(TextFieldTableCell.forTableColumn());
            equipmentSlotsTableIconColumn.setCellValueFactory(param -> {
                SimpleObjectProperty<ImageView> imageProperty = new SimpleObjectProperty<>();
                try {
                    String gameFileDir = new File(gameFile.getPath()).getParent();
                    File imageFile = new File(gameFileDir, param.getValue()[1]);
                    if (!imageFile.isFile()) {
                        imageFile = new File("data/empty.png");
                    }
                    imageProperty.set(new ImageView(new Image(new FileInputStream(imageFile))));
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                    System.exit(1);
                }
                return imageProperty;
            });
            equipmentSlotsTableNameColumn.setCellValueFactory(param -> new SimpleStringProperty(param.getValue()[0]));
            equipmentSlotsTableNameColumn.setCellFactory(TextFieldTableCell.forTableColumn());
            for (Map.Entry<String, String> slot : gameFile.getSlotNames().entrySet()) {
                equipmentSlotsTable.getItems().add(new String[]{slot.getKey(), slot.getValue()});
            }
        } catch (ClassCastException e) {
            e.printStackTrace();
        }
    }

    private void initPlayerParametersTable() {
        playerKeyColumn.setCellValueFactory(EditorController::mapKey);
        playerValueColumn.setCellValueFactory(EditorController::mapValue);
        playerValueColumn.setCellFactory(TextFieldTableCell.forTableColumn());
    }

    private void initPlayer() {
        playerName.setText(gameFile.getPlayer().getName());
        playerDescription.setText(gameFile.getPlayer().getDescription());
        playerLocation.getItems().setAll(gameFile.getLocations());
        playerLocation.getItems().add(null);
        playerLocation.getSelectionModel().select(gameFile.getPlayer().getLocation());
        playerScriptsList.getItems().setAll(gameFile.getPlayer().getScripts().keySet());
        playerAllItemsList.getItems().setAll(gameFile.getItems());
        playerItemsList.getItems().setAll(gameFile.getPlayer().getInventory().getItems());
        Map<String, Item> equipment = gameFile.getPlayer().getEquipment().getSlots();
        ObservableList<Map.Entry<String, Item>> equipmentItems = FXCollections.observableArrayList(equipment.entrySet());
        playerEquipmentTable.getItems().setAll(equipmentItems);
        playerEquipmentTable.getColumns().setAll(playerEquipmentTableSlot, playerEquipmentTableItem);
        playerAllCategories.getItems().setAll(gameFile.getCharacterCategories());
        playerCategories.getItems().setAll(gameFile.getPlayer().getCategories());
        HashMap<String, String> values = gameFile.getPlayer().getValues();
        ObservableList<Map.Entry<String, String>> items = FXCollections.observableArrayList(values.entrySet());
        playerParameters.getItems().setAll(items);
        playerParameters.getColumns().setAll(playerKeyColumn, playerValueColumn);

        BehaviorTree tree = gameFile.getPlayer().getBtree();
        TreeItem<BTreeNode> root = new TreeItem<>(tree);
        root.setExpanded(true);
        selectPlayerBehaviorNode(root);
        playerBehaviorTree.setRoot(root);
        populateBehaviorTree(tree, root);
    }

    private void initLocationParametersTable() {
        locationKeyColumn.setCellValueFactory(EditorController::mapKey);
        locationValueColumn.setCellValueFactory(EditorController::mapValue);
        locationValueColumn.setCellFactory(TextFieldTableCell.forTableColumn());
    }

    private void initItemParametersTable() {
        itemKeyColumn.setCellValueFactory(EditorController::mapKey);
        itemValueColumn.setCellValueFactory(EditorController::mapValue);
        itemValueColumn.setCellFactory(TextFieldTableCell.forTableColumn());
    }

    private void initEquipmentTable() {
        equipmentTableSlot.setCellValueFactory(EditorController::mapItemKey);
        equipmentTableItem.setCellValueFactory(EditorController::mapItemValue);
    }

    private void initPlayerEquipmentTable() {
        playerEquipmentTableSlot.setCellValueFactory(EditorController::mapItemKey);
        playerEquipmentTableItem.setCellValueFactory(EditorController::mapItemValue);
    }

    private void initCharacterParametersTable() {
        characterKeyColumn.setCellValueFactory(EditorController::mapKey);
        characterValueColumn.setCellValueFactory(EditorController::mapValue);
        characterValueColumn.setCellFactory(TextFieldTableCell.forTableColumn());
    }

    private void initItemCategories() {
        List<ItemCategory> categories = gameFile.getItemCategories();
        categories.forEach(itemsCategories.getItems()::add);
    }

    private void initEquipment() {
        Map<String, String> slots = gameFile.getSlotNames();
        Equipment.setSlotNames(slots);
        itemEquipmentSlot.getItems().setAll(slots.keySet());
    }

    public void saveGameName() {
        String name = gameName.getText();
        gameFile.setGameName(name);
    }

    public void saveGameDescription() {
        String description = gameDescription.getText();
        gameFile.setGameStartMessage(description);
    }

    public void saveInitScript() {
        String initScript = ((RSyntaxTextArea) initScriptNode.getContent()).getText();
        gameFile.setInitScript(initScript);
    }

    public void saveGameScript() {
        String gameScriptName = gameScriptsList.getSelectionModel().getSelectedItem();
        if (gameScriptName != null) {
            String gameScriptText = ((RSyntaxTextArea) gameScriptsNode.getContent()).getText();
            gameFile.setCommonScript(gameScriptName, gameScriptText);
        }
    }

    public void saveTimerName() {
        SudTimer timer = timersList.getSelectionModel().getSelectedItem();
        if (timer != null) {
            String name = timerName.getText();
            timer.setTimerName(name);
            timersList.refresh();
        }
    }

    public void saveTimerStep() {
        SudTimer timer = timersList.getSelectionModel().getSelectedItem();
        if (timer != null) {
            String step = timerStep.getText();
            timer.setStep(Integer.parseInt(step));
            timersList.refresh();
        }
    }

    public void saveTimerScript() {
        SudTimer timer = timersList.getSelectionModel().getSelectedItem();
        if (timer != null) {
            String script = ((RSyntaxTextArea) timerScriptNode.getContent()).getText();
            timer.setScript(script);
            timersList.refresh();
        }
    }

    public void addTimer() {
        SudTimer timer = new SudTimer();
        gameFile.getTimers().add(timer);
        timersList.getItems().setAll(gameFile.getTimers());
    }

    public void removeTimer() {
        SudTimer timer = timersList.getSelectionModel().getSelectedItem();
        if (timer != null) {
            gameFile.getTimers().remove(timer);
            timersList.getItems().setAll(gameFile.getTimers());
        }
    }

    public void saveItemName() {
        Item item = itemsList.getSelectionModel().getSelectedItem();
        if (item != null) {
            String name = itemName.getText();
            item.setName(name);
            itemsList.refresh();
        }
    }

    public void saveItemDescription() {
        Item item = itemsList.getSelectionModel().getSelectedItem();
        if (item != null) {
            String description = itemDescription.getText();
            item.setDescription(description);
        }
    }

    public void setItemIsInventory() {
        Item item = itemsList.getSelectionModel().getSelectedItem();
        if (item != null) {
            item.setContainer(isItemInventory.isSelected());
            isItemLocked.setDisable(!isItemInventory.isSelected());
        }
    }

    public void setItemIsLocked() {
        Item item = itemsList.getSelectionModel().getSelectedItem();
        if (item != null) {
            item.setLocked(isItemLocked.isSelected());
        }
    }

    public void setItemType() {
        Item item = itemsList.getSelectionModel().getSelectedItem();
        if (item != null) {
            ItemTypes oldType = item.getType();
            ItemTypes type = itemType.getSelectionModel().getSelectedItem();
            if (oldType == ItemTypes.EQUIPPABLE && oldType != type) {
                for (GameCharacter character : gameFile.getCharacters()) {
                    character.unequip(item);
                }
            }
            gameFile.getPlayer().unequip(item);
            item.setType(type);
            itemEquipmentSlot.setDisable(type != ItemTypes.EQUIPPABLE);
            if (type == ItemTypes.EQUIPPABLE) {
                equipmentAllList.getItems().add(item);
                playerEquipmentAllList.getItems().add(item);
            }
        }
    }

    public void setItemEquipmentSlot() {
        Item item = itemsList.getSelectionModel().getSelectedItem();
        if (item != null) {
            String slot = itemEquipmentSlot.getValue();
            item.setEquipmentSlot(slot);
            gameFile.getPlayer().unequip(item);
            for (GameCharacter character : gameFile.getCharacters()) {
                character.unequip(item);
            }
        }
    }

    public void saveItemScript() {
        Item item = itemsList.getSelectionModel().getSelectedItem();
        String scriptName = itemScriptsList.getSelectionModel().getSelectedItem();
        if (item != null && scriptName != null) {
            String scriptText = ((RSyntaxTextArea) itemScriptNode.getContent()).getText();
            Script script = item.getScript(scriptName);
            script.setText(scriptText);
        }
    }

    public void setItemScriptEnabled() {
        Item item = itemsList.getSelectionModel().getSelectedItem();
        String scriptName = itemScriptsList.getSelectionModel().getSelectedItem();
        if (item != null && scriptName != null && !scriptName.startsWith("_")) {
            boolean enabled = itemScriptEnabled.isSelected();
            item.getScript(scriptName).setEnabled(enabled);
        }
    }

    public void addCategoryToItem() {
        Item item = itemsList.getSelectionModel().getSelectedItem();
        ItemCategory category = itemsCategories.getSelectionModel().getSelectedItem();
        if (item != null && category != null) {
            item.addCategory(category);
            itemCategories.refresh();
        }
    }

    public void removeCategoryFromItem() {
        Item item = itemsList.getSelectionModel().getSelectedItem();
        ItemCategory category = itemCategories.getSelectionModel().getSelectedItem();
        if (item != null && category != null) {
            item.removeCategory(category);
        }
    }

    public void addItem() {
        Item item = new Item("New Item");
        gameFile.getItems().add(item);
        itemsList.getItems().add(item);
        locationAllItemsList.getItems().add(item);
        characterAllItemsList.getItems().add(item);
        playerAllItemsList.getItems().add(item);
        itemsList.refresh();
        itemsList.getSelectionModel().select(item);
    }

    public void removeItem() {
        Item item = itemsList.getSelectionModel().getSelectedItem();
        if (item != null) {
            itemsList.getItems().remove(item);
            locationAllItemsList.getItems().remove(item);
            characterAllItemsList.getItems().remove(item);
            playerAllItemsList.getItems().remove(item);
            equipmentAllList.getItems().remove(item);
            playerEquipmentAllList.getItems().remove(item);
            //Remove from all locations
            for (Location location : gameFile.getLocations()) {
                location.removeItem(item, -1);
            }
            //Remove from all characters
            for (GameCharacter character : gameFile.getCharacters()) {
                character.removeItem(item, -1);
                character.unequip(item);
            }
            //Remove from all containers
            for (Item container : gameFile.getItems()) {
                container.removeItem(item, -1);
            }
            //Remove from player
            gameFile.getPlayer().removeItem(item, -1);
            gameFile.getPlayer().unequip(item);
            gameFile.getItems().remove(item);
            playerItemsList.getItems().setAll(gameFile.getPlayer().getInventory().getItems());
        }
    }

    public void addItemScript() {
        Item item = itemsList.getSelectionModel().getSelectedItem();
        if (item != null) {
            //Show input dialog
            TextInputDialog inputDialog = new TextInputDialog("New Script");
            inputDialog.setTitle("Enter script name");
            Optional<String> result = inputDialog.showAndWait();
            result.ifPresent(scriptName -> {
                item.getScripts().put(scriptName, new Script("", true));
                itemScriptsList.getItems().add(scriptName);
                itemScriptsList.getSelectionModel().select(scriptName);
            });
            itemScriptsList.refresh();
        }
    }

    public void removeItemScript() {
        Item item = itemsList.getSelectionModel().getSelectedItem();
        String scriptName = itemScriptsList.getSelectionModel().getSelectedItem();
        if (item != null && scriptName != null && !scriptName.startsWith("_")) {
            item.getScripts().remove(scriptName);
            itemScriptsList.getItems().remove(scriptName);
        }
    }

    public void addItemParameter() {
        Item item = itemsList.getSelectionModel().getSelectedItem();
        if (item != null) {
            TextInputDialog inputDialog = new TextInputDialog("New Parameter");
            inputDialog.setTitle("Enter parameter name");
            Optional<String> result = inputDialog.showAndWait();
            result.ifPresent(parameterName -> {
                item.setValue(parameterName, "");
                selectItem(item);
            });
        }
    }

    public void removeItemParameter() {
        Item item = itemsList.getSelectionModel().getSelectedItem();
        if (item != null) {
            Map.Entry<String, String> entry = itemParameters.getSelectionModel().getSelectedItem();
            if (entry != null) {
                String key = entry.getKey();
                item.removeValue(key);
                selectItem(item);
            }
        }
    }

    public void saveLocationScript() {
        Location location = locationsList.getSelectionModel().getSelectedItem();
        String scriptName = locationScriptsList.getSelectionModel().getSelectedItem();
        if (location != null && scriptName != null) {
            String scriptText = ((RSyntaxTextArea) locationScriptNode.getContent()).getText();
            Script script = location.getScript(scriptName);
            script.setText(scriptText);
        }
    }

    public void addSeveralItemsToLocation() {
        int amount = Utils.showSpinnerDialog();
        Item item = locationAllItemsList.getSelectionModel().getSelectedItem();
        Location location = locationsList.getSelectionModel().getSelectedItem();
        if (item != null && location != null && amount != 0) {
            addItemToLocation(location, item, amount);
        }
    }

    public void addOneItemToLocation() {
        Item item = locationAllItemsList.getSelectionModel().getSelectedItem();
        Location location = locationsList.getSelectionModel().getSelectedItem();
        if (item != null && location != null) {
            addItemToLocation(location, item, 1);
        }
    }

    private void addItemToLocation(Location location, Item item, int amount) {
        location.addItem(item, amount);
        selectLocation(location);
    }

    public void showItemInventory() {
        SudPair<Item, Integer> pair = locationItemsList.getSelectionModel().getSelectedItem();
        if (pair != null) {
            Item item = pair.getKey();
            ((ItemContainerController) ScreenController.getController("itemContainer")).init(item, gameFile);
            ScreenController.activate("itemContainer");
        }
    }

    public void removeSeveralItemsFromLocation() {
        int amount = Utils.showSpinnerDialog();
        SudPair<Item, Integer> pair = locationItemsList.getSelectionModel().getSelectedItem();
        Location location = locationsList.getSelectionModel().getSelectedItem();
        if (pair != null && location != null && amount != 0) {
            Item item = pair.getKey();
            removeItemFromLocation(location, item, amount);
            locationItemsList.getSelectionModel().select(pair);
        }
    }

    public void removeOneItemFromLocation() {
        SudPair<Item, Integer> pair = locationItemsList.getSelectionModel().getSelectedItem();
        Location location = locationsList.getSelectionModel().getSelectedItem();
        if (pair != null && location != null) {
            Item item = pair.getKey();
            removeItemFromLocation(location, item, 1);
            locationItemsList.getSelectionModel().select(pair);
        }
    }

    private void removeItemFromLocation(Location location, Item item, int amount) {
        location.removeItem(item, amount);
        selectLocation(location);
    }

    public void addCategoryToLocation() {
        Location location = locationsList.getSelectionModel().getSelectedItem();
        GameObjectCategory category = locationsCategories.getSelectionModel().getSelectedItem();
        if (location != null && category != null) {
            location.addCategory(category);
        }
    }

    public void removeCategoryFromLocation() {
        Location location = locationsList.getSelectionModel().getSelectedItem();
        GameObjectCategory category = locationCategories.getSelectionModel().getSelectedItem();
        if (location != null && category != null) {
            location.removeCategory(category);
        }
    }

    public void addLocationParameter() {
        Location location = locationsList.getSelectionModel().getSelectedItem();
        if (location != null) {
            TextInputDialog inputDialog = new TextInputDialog("New Parameter");
            inputDialog.setTitle("Enter parameter name");
            Optional<String> result = inputDialog.showAndWait();
            result.ifPresent(parameterName -> {
                location.setValue(parameterName, "");
                selectLocation(location);
            });
        }
    }

    public void removeLocationParameter() {
        Location location = locationsList.getSelectionModel().getSelectedItem();
        if (location != null) {
            Map.Entry<String, String> entry = locationParameters.getSelectionModel().getSelectedItem();
            if (entry != null) {
                String key = entry.getKey();
                location.removeValue(key);
                selectLocation(location);
            }
        }
    }

    public void changeValue(TableColumn.CellEditEvent<Map.Entry<String, String>, String> event) {
        event.getRowValue().setValue(event.getNewValue());
    }

    public void addLocation() {
        Location location = new Location("New Location");
        locationsList.getItems().add(location);
        gameFile.getLocations().add(location);
        locationsList.refresh();
        locationsList.getSelectionModel().select(location);
        characterLocation.getItems().add(location);
        playerLocation.getItems().add(location);
        locationNorthExit.getItems().add(location);
        locationSouthExit.getItems().add(location);
        locationEastExit.getItems().add(location);
        locationWestExit.getItems().add(location);
        locationUpExit.getItems().add(location);
        locationDownExit.getItems().add(location);
    }

    public void removeLocation() {
        Location location = locationsList.getSelectionModel().getSelectedItem();
        if (location != null) {
            locationsList.getItems().remove(location);
            gameFile.getLocations().remove(location);
            locationNorthExit.getItems().remove(location);
            locationSouthExit.getItems().remove(location);
            locationWestExit.getItems().remove(location);
            locationEastExit.getItems().remove(location);
            locationUpExit.getItems().remove(location);
            locationDownExit.getItems().remove(location);
            playerLocation.getItems().remove(location);
        }
    }

    public void saveLocationName() {
        Location location = locationsList.getSelectionModel().getSelectedItem();
        if (location != null) {
            String name = locationName.getText();
            location.setName(name);
            locationsList.refresh();
        }
    }

    public void saveLocationDescription() {
        Location location = locationsList.getSelectionModel().getSelectedItem();
        if (location != null) {
            String description = locationDescription.getText();
            location.setDescription(description);
        }
    }

    public void saveLocationNorth() {
        Location location = locationsList.getSelectionModel().getSelectedItem();
        if (location != null) {
            Location north = locationNorthExit.getValue();
            location.setNorth(north);
        }
    }

    public void saveLocationSouth() {
        Location location = locationsList.getSelectionModel().getSelectedItem();
        if (location != null) {
            Location south = locationSouthExit.getValue();
            location.setSouth(south);
        }
    }

    public void saveLocationWest() {
        Location location = locationsList.getSelectionModel().getSelectedItem();
        if (location != null) {
            Location west = locationWestExit.getValue();
            location.setWest(west);
        }
    }

    public void saveLocationEast() {
        Location location = locationsList.getSelectionModel().getSelectedItem();
        if (location != null) {
            Location east = locationEastExit.getValue();
            location.setEast(east);
        }
    }

    public void saveLocationDown() {
        Location location = locationsList.getSelectionModel().getSelectedItem();
        if (location != null) {
            Location down = locationDownExit.getValue();
            location.setDown(down);
        }
    }

    public void saveLocationUp() {
        Location location = locationsList.getSelectionModel().getSelectedItem();
        if (location != null) {
            Location up = locationUpExit.getValue();
            location.setUp(up);
        }
    }

    private void selectLocationScript(String scriptName) {
        Location location = locationsList.getSelectionModel().getSelectedItem();
        if (location != null && scriptName != null) {
            Script script = location.getScript(scriptName);
            String text = script.getText();
            ((RSyntaxTextArea) locationScriptNode.getContent()).setText(text);
            locationScriptEnabled.setSelected(script.isEnabled());
        } else {
            ((RSyntaxTextArea) locationScriptNode.getContent()).setText("");
            locationScriptEnabled.setSelected(false);
        }
    }

    public void setLocationScriptEnabled() {
        Location location = locationsList.getSelectionModel().getSelectedItem();
        String scriptName = locationScriptsList.getSelectionModel().getSelectedItem();
        if (location != null && scriptName != null && !scriptName.startsWith("_")) {
            locationScriptEnabled.setSelected(location.getScript(scriptName).isEnabled());
        }
    }

    public void addCharacter() {
        GameCharacter character = new GameCharacter("Enter name");
        charactersList.getItems().add(character);
        gameFile.getCharacters().add(character);
        charactersList.getSelectionModel().select(character);
    }

    public void removeCharacter() {
        GameCharacter character = charactersList.getSelectionModel().getSelectedItem();
        if (character != null) {
            charactersList.getItems().remove(character);
            gameFile.getCharacters().remove(character);
        }
    }

    public void saveCharacterName() {
        GameCharacter character = charactersList.getSelectionModel().getSelectedItem();
        if (character != null) {
            String name = characterName.getText();
            character.setName(name);
            charactersList.refresh();
        }
    }

    public void saveCharacterDescription() {
        GameCharacter character = charactersList.getSelectionModel().getSelectedItem();
        if (character != null) {
            String description = characterDescription.getText();
            character.setDescription(description);
            charactersList.refresh();
        }
    }

    public void setCharacterLocation() {
        GameCharacter character = charactersList.getSelectionModel().getSelectedItem();
        if (character != null) {
            Location location = characterLocation.getValue();
            character.setLocation(location);
        }
    }

    public void addCharacterScript() {
        GameCharacter character = charactersList.getSelectionModel().getSelectedItem();
        if (character != null) {
            TextInputDialog inputDialog = new TextInputDialog("New Script");
            inputDialog.setTitle("Enter script name");
            Optional<String> result = inputDialog.showAndWait();
            result.ifPresent(scriptName -> {
                character.getScripts().put(scriptName, new Script("", true));
                characterScriptsList.getItems().add(scriptName);
                characterScriptsList.getSelectionModel().select(scriptName);
            });
            characterScriptsList.refresh();
        }
    }

    public void removeCharacterScript() {
        GameCharacter character = charactersList.getSelectionModel().getSelectedItem();
        String scriptName = characterScriptsList.getSelectionModel().getSelectedItem();
        if (character != null && scriptName != null && !scriptName.startsWith("_")) {
            character.removeScript(scriptName);
            characterScriptsList.getItems().remove(scriptName);
        }
    }

    public void addLocationScript() {
        Location location = locationsList.getSelectionModel().getSelectedItem();
        if (location != null) {
            TextInputDialog inputDialog = new TextInputDialog("New Script");
            inputDialog.setTitle("Enter script name");
            Optional<String> result = inputDialog.showAndWait();
            result.ifPresent(scriptName -> {
                location.getScripts().put(scriptName, new Script("", true));
                locationScriptsList.getItems().add(scriptName);
                locationScriptsList.getSelectionModel().select(scriptName);
            });
            locationScriptsList.refresh();
        }
    }

    public void removeLocationScript() {
        Location location = locationsList.getSelectionModel().getSelectedItem();
        String scriptName = locationScriptsList.getSelectionModel().getSelectedItem();
        if (location != null && scriptName != null && !scriptName.startsWith("_")) {
            location.removeScript(scriptName);
            locationScriptsList.getItems().remove(scriptName);
        }
    }

    public void saveCharacterScript() {
        GameCharacter character = charactersList.getSelectionModel().getSelectedItem();
        String scriptName = characterScriptsList.getSelectionModel().getSelectedItem();
        if (character != null && scriptName != null) {
            String text = ((RSyntaxTextArea) characterScriptNode.getContent()).getText();
            Script script = character.getScript(scriptName);
            script.setText(text);
        }
    }

    public void setCharacterScriptEnabled() {
        GameCharacter character = charactersList.getSelectionModel().getSelectedItem();
        String scriptName = characterScriptsList.getSelectionModel().getSelectedItem();
        if (character != null && scriptName != null && !scriptName.startsWith("_")) {
            boolean enabled = characterScriptEnabled.isSelected();
            character.setScriptEnabled(scriptName, enabled);
        }
    }


    public void addSeveralItemsToCharacter() {
        int amount = Utils.showSpinnerDialog();
        Item item = characterAllItemsList.getSelectionModel().getSelectedItem();
        GameCharacter character = charactersList.getSelectionModel().getSelectedItem();
        if (item != null && character != null && amount != 0) {
            addItemToCharacter(character, item, amount);
        }
    }

    public void addOneItemToCharacter() {
        Item item = characterAllItemsList.getSelectionModel().getSelectedItem();
        GameCharacter character = charactersList.getSelectionModel().getSelectedItem();
        if (item != null && character != null) {
            addItemToCharacter(character, item, 1);
        }
    }

    private void addItemToCharacter(GameCharacter character, Item item, int amount) {
        character.addItem(item, amount);
        selectCharacter(character);
    }

    public void removeSeveralItemsFromCharacter() {
        int amount = Utils.showSpinnerDialog();
        SudPair<Item, Integer> pair = characterItemsList.getSelectionModel().getSelectedItem();
        GameCharacter character = charactersList.getSelectionModel().getSelectedItem();
        if (pair != null && character != null && amount != 0) {
            Item item = pair.getKey();
            removeItemFromCharacter(character, item, amount);
            characterItemsList.getSelectionModel().select(pair);
        }
    }

    public void removeOneItemFromCharacter() {
        SudPair<Item, Integer> pair = characterItemsList.getSelectionModel().getSelectedItem();
        GameCharacter character = charactersList.getSelectionModel().getSelectedItem();
        if (pair != null && character != null) {
            Item item = pair.getKey();
            removeItemFromCharacter(character, item, 1);
            characterItemsList.getSelectionModel().select(pair);
        }
    }

    private void removeItemFromCharacter(GameCharacter character, Item item, int amount) {
        character.removeItem(item, amount);
        selectCharacter(character);
    }

    public void equipItem() {
        GameCharacter character = charactersList.getSelectionModel().getSelectedItem();
        Item item = equipmentAllList.getSelectionModel().getSelectedItem();
        if (character != null && item != null) {
            character.equip(item);
            selectCharacter(character);
        }
    }

    public void unequipItem() {
        GameCharacter character = charactersList.getSelectionModel().getSelectedItem();
        if (character != null) {
            Map.Entry<String, Item> entry = characterEquipmentTable.getSelectionModel().getSelectedItem();
            if (entry != null) {
                Item item = entry.getValue();
                character.unequip(item);
                selectCharacter(character);
            }
        }
    }

    public void addCharacterParameter() {
        GameCharacter character = charactersList.getSelectionModel().getSelectedItem();
        if (character != null) {
            TextInputDialog inputDialog = new TextInputDialog("New Parameter");
            inputDialog.setTitle("Enter parameter name");
            Optional<String> result = inputDialog.showAndWait();
            result.ifPresent(parameterName -> {
                character.setValue(parameterName, "");
                selectCharacter(character);
            });
        }
    }

    public void removeCharacterParameter() {
        GameCharacter character = charactersList.getSelectionModel().getSelectedItem();
        if (character != null) {
            Map.Entry<String, String> entry = characterParameters.getSelectionModel().getSelectedItem();
            if (entry != null) {
                String key = entry.getKey();
                character.removeValue(key);
                selectCharacter(character);
            }
        }
    }

    public void addCategoryToCharacter() {
        GameCharacter character = charactersList.getSelectionModel().getSelectedItem();
        CharacterCategory category = charactersCategories.getSelectionModel().getSelectedItem();
        if (character != null && category != null) {
            character.addCategory(category);
            selectCharacter(character);
        }
    }

    public void removeCategoryFromCharacter() {
        GameCharacter character = charactersList.getSelectionModel().getSelectedItem();
        GameObjectCategory category = characterCategories.getSelectionModel().getSelectedItem();
        if (character != null && category != null) {
            character.removeCategory(category);
            selectCharacter(character);
        }
    }

    public void removeCharacterBehaviorTreeItem() {
        GameCharacter character = charactersList.getSelectionModel().getSelectedItem();
        TreeItem<BTreeNode> item = characterBehaviorTree.getSelectionModel().getSelectedItem();
        if (character != null && item != null && !item.equals(characterBehaviorTree.getRoot())) {
            TreeItem<BTreeNode> parent = item.getParent();
            BTreeNode node = item.getValue();
            node.getParent().getChildren().remove(node);
            parent.getChildren().remove(item);
            selectCharacter(character);
        }
    }

    public void saveCharacterBehaviorScript() {
        GameCharacter character = charactersList.getSelectionModel().getSelectedItem();
        TreeItem<BTreeNode> treeItem = characterBehaviorTree.getSelectionModel().getSelectedItem();
        if (character != null && treeItem != null) {
            TaskNode node = (TaskNode) treeItem.getValue();
            String scriptText = ((RSyntaxTextArea) characterBehaviorScriptNode.getContent()).getText();
            Script script = node.getScript();
            script.setText(scriptText);
        }
    }

    public void savePlayerName() {
        if (gameFile != null) {
            String name = playerName.getText();
            gameFile.getPlayer().setName(name);
        }

    }

    public void
    setPlayerLocation() {
        if (gameFile != null) {
            Location location = playerLocation.getSelectionModel().getSelectedItem();
            gameFile.getPlayer().setLocation(location);
        }

    }

    public void savePlayerDescription() {
        String description = playerDescription.getText();
        gameFile.getPlayer().setDescription(description);
    }

    public void addPlayerScript() {
        if (gameFile != null) {
            TextInputDialog inputDialog = new TextInputDialog("New Script");
            inputDialog.setTitle("Enter script name");
            Optional<String> result = inputDialog.showAndWait();
            result.ifPresent(scriptName -> {
                gameFile.getPlayer().getScripts().put(scriptName, new Script("", true));
                playerScriptsList.getItems().add(scriptName);
                playerScriptsList.getSelectionModel().select(scriptName);
            });
            playerScriptsList.refresh();
        }

    }

    public void removePlayerScript() {
        String scriptName = playerScriptsList.getSelectionModel().getSelectedItem();
        if (scriptName != null && gameFile != null) {
            gameFile.getPlayer().getScripts().remove(scriptName);
            playerScriptsList.getItems().remove(scriptName);
        }
    }

    public void savePlayerScript() {
        String scriptText = ((RSyntaxTextArea) playerScriptNode.getContent()).getText();
        String scriptName = playerScriptsList.getSelectionModel().getSelectedItem();
        if (scriptName != null && gameFile != null) {
            Script script = gameFile.getPlayer().getScript(scriptName);
            script.setText(scriptText);
        }
    }

    public void setPlayerScriptEnabled() {
        String scriptName = playerScriptsList.getSelectionModel().getSelectedItem();
        boolean enabled = playerScriptEnabled.isSelected();
        if (scriptName != null && gameFile != null) {
            gameFile.getPlayer().setScriptEnabled(scriptName, enabled);
        }
    }

    public void addSeveralItemsToPlayer() {
        int amount = Utils.showSpinnerDialog();
        Item item = playerAllItemsList.getSelectionModel().getSelectedItem();
        if (item != null && amount != 0) {
            addItemToPlayer(item, amount);
        }
    }

    private void addItemToPlayer(Item item, int amount) {
        if (gameFile != null) {
            gameFile.getPlayer().addItem(item, amount);
            playerItemsList.getItems().setAll(gameFile.getPlayer().getInventory().getItems());
        }

    }

    public void addOneItemToPlayer() {
        Item item = playerAllItemsList.getSelectionModel().getSelectedItem();
        if (item != null) {
            addItemToPlayer(item, 1);
        }
    }

    public void removeOneItemFromPlayer() {
        SudPair<Item, Integer> pair = playerItemsList.getSelectionModel().getSelectedItem();
        if (pair != null) {
            Item item = pair.getKey();
            removeItemFromPlayer(item, 1);
            playerItemsList.getSelectionModel().select(pair);
        }
    }

    private void removeItemFromPlayer(Item item, int i) {
        if (gameFile != null) {
            gameFile.getPlayer().removeItem(item, i);
            playerItemsList.getItems().setAll(gameFile.getPlayer().getInventory().getItems());
        }

    }

    public void removeSeveralItemsFromPlayer() {
        int amount = Utils.showSpinnerDialog();
        SudPair<Item, Integer> pair = playerItemsList.getSelectionModel().getSelectedItem();
        if (pair != null && amount != 0) {
            Item item = pair.getKey();
            removeItemFromPlayer(item, amount);
            playerItemsList.getSelectionModel().select(pair);
        }
    }

    public void playerUnequipItem() {
        Map.Entry<String, Item> entry = playerEquipmentTable.getSelectionModel().getSelectedItem();
        if (entry != null && gameFile != null) {
            Item item = entry.getValue();
            gameFile.getPlayer().unequip(item);
            initPlayer();
        }

    }

    public void playerEquipItem() {
        Item item = playerEquipmentAllList.getSelectionModel().getSelectedItem();
        if (item != null && gameFile != null) {
            gameFile.getPlayer().equip(item);
            initPlayer();
        }
    }

    public void addCategoryToPlayer() {
        CharacterCategory category = playerAllCategories.getSelectionModel().getSelectedItem();
        if (category != null && gameFile != null) {
            gameFile.getPlayer().addCategory(category);
            initPlayer();
        }
    }

    public void removeCategoryFromPlayer() {
        GameObjectCategory category = playerCategories.getSelectionModel().getSelectedItem();
        if (category != null && gameFile != null) {
            gameFile.getPlayer().removeCategory(category);
            initPlayer();
        }
    }

    public void addPlayerParameter() {
        if (gameFile != null) {
            TextInputDialog inputDialog = new TextInputDialog("New Parameter");
            inputDialog.setTitle("Enter parameter name");
            Optional<String> result = inputDialog.showAndWait();
            result.ifPresent(parameterName -> {
                gameFile.getPlayer().setValue(parameterName, "");
                initPlayer();
            });
        }
    }

    public void removePlayerParameter() {
        if (gameFile != null) {
            Map.Entry<String, String> entry = playerParameters.getSelectionModel().getSelectedItem();
            if (entry != null) {
                String key = entry.getKey();
                gameFile.getPlayer().removeValue(key);
                initPlayer();
            }
        }
    }

    public void removePlayerBehaviorTreeItem() {
        TreeItem<BTreeNode> item = playerBehaviorTree.getSelectionModel().getSelectedItem();
        if (gameFile != null && item != null && !item.equals(playerBehaviorTree.getRoot())) {
            TreeItem<BTreeNode> parent = item.getParent();
            BTreeNode node = item.getValue();
            node.getParent().getChildren().remove(node);
            parent.getChildren().remove(item);
            initPlayer();
        }
    }

    public void savePlayerBehaviorScript() {
        TreeItem<BTreeNode> treeItem = playerBehaviorTree.getSelectionModel().getSelectedItem();
        if (gameFile != null && treeItem != null) {
            TaskNode node = (TaskNode) treeItem.getValue();
            String scriptText = ((RSyntaxTextArea) playerBehaviorScriptNode.getContent()).getText();
            Script script = node.getScript();
            script.setText(scriptText);
        }
    }

    public void saveLocationCategoryName() {
        LocationCategory category = locationCategoriesList.getSelectionModel().getSelectedItem();
        String name = locationCategoryName.getText();
        if (category != null) {
            category.setName(name);
            locationCategoriesList.refresh();
        }
    }

    public void addLocationCategory() {
        if (gameFile != null) {
            LocationCategory category = new LocationCategory("LocationCategory:New");
            gameFile.getLocationCategories().add(category);
            locationCategoriesList.getItems().add(category);
        }
    }

    public void removeLocationCategory() {
        LocationCategory category = locationCategoriesList.getSelectionModel().getSelectedItem();
        if (category != null) {
            gameFile.getLocationCategories().remove(category);
            locationCategoriesList.getItems().remove(category);
            for (Location location : gameFile.getLocations()) {
                location.removeCategory(category);
            }
        }
    }

    public void addLocationCategoryScript() {
        LocationCategory category = locationCategoriesList.getSelectionModel().getSelectedItem();
        if (category != null) {
            String scriptName = Utils.showPromptDialog("Enter Script name");
            if (scriptName != null) {
                Script script = new Script("", true);
                category.setScript(scriptName, script);
                locationCategoriesScriptsList.getItems().add(scriptName);
            }
        }
    }

    public void removeLocationCategoryScript() {
        LocationCategory category = locationCategoriesList.getSelectionModel().getSelectedItem();
        String scriptName = locationCategoriesScriptsList.getSelectionModel().getSelectedItem();
        if (category != null && scriptName != null && !scriptName.startsWith("_")) {
            category.removeScript(scriptName);
            locationCategoriesScriptsList.getItems().remove(scriptName);
        }
    }

    public void saveLocationCategoryScript() {
        LocationCategory category = locationCategoriesList.getSelectionModel().getSelectedItem();
        String scriptName = locationCategoriesScriptsList.getSelectionModel().getSelectedItem();
        if (category != null && scriptName != null) {
            Script script = category.getScript(scriptName);
            String scriptText = ((RSyntaxTextArea) locationCategoryScriptNode.getContent()).getText();
            script.setText(scriptText);
        }
    }

    public void setLocationCategoryScriptEnabled() {
        LocationCategory category = locationCategoriesList.getSelectionModel().getSelectedItem();
        String scriptName = locationCategoriesScriptsList.getSelectionModel().getSelectedItem();
        if (category != null && scriptName != null && !scriptName.startsWith("_")) {
            Script script = category.getScript(scriptName);
            boolean enabled = locationCategoryScriptEnabled.isSelected();
            script.setEnabled(enabled);
        }
    }

    public void addCharacterCategory() {
        if (gameFile != null) {
            CharacterCategory category = new CharacterCategory("CharacterCategory:New");
            gameFile.getCharacterCategories().add(category);
            characterCategoriesList.getItems().add(category);
        }
    }

    public void removeCharacterCategory() {
        CharacterCategory category = characterCategoriesList.getSelectionModel().getSelectedItem();
        if (category != null) {
            gameFile.getCharacterCategories().remove(category);
            characterCategoriesList.getItems().remove(category);
            for (GameCharacter character : gameFile.getCharacters()) {
                character.removeCategory(category);
            }
        }
    }

    public void saveCharacterCategoryName() {
        CharacterCategory category = characterCategoriesList.getSelectionModel().getSelectedItem();
        String name = characterCategoryName.getText();
        if (category != null) {
            category.setName(name);
            characterCategoriesList.refresh();
        }
    }

    public void addCharacterCategoryScript() {
        CharacterCategory category = characterCategoriesList.getSelectionModel().getSelectedItem();
        if (category != null) {
            String scriptName = Utils.showPromptDialog("Enter Script name");
            if (scriptName != null) {
                Script script = new Script("", true);
                category.setScript(scriptName, script);
                characterCategoriesScriptsList.getItems().add(scriptName);
            }
        }
    }

    public void removeCharacterCategoryScript() {
        CharacterCategory category = characterCategoriesList.getSelectionModel().getSelectedItem();
        String scriptName = characterCategoriesScriptsList.getSelectionModel().getSelectedItem();
        if (category != null && scriptName != null && !scriptName.startsWith("_")) {
            category.removeScript(scriptName);
            characterCategoriesScriptsList.getItems().remove(scriptName);
        }
    }

    public void saveCharacterCategoryScript() {
        CharacterCategory category = characterCategoriesList.getSelectionModel().getSelectedItem();
        String scriptName = characterCategoriesScriptsList.getSelectionModel().getSelectedItem();
        if (category != null && scriptName != null) {
            Script script = category.getScript(scriptName);
            String scriptText = ((RSyntaxTextArea) characterCategoryScriptNode.getContent()).getText();
            script.setText(scriptText);
        }
    }

    public void setCharacterCategoryScriptEnabled() {
        CharacterCategory category = characterCategoriesList.getSelectionModel().getSelectedItem();
        String scriptName = characterCategoriesScriptsList.getSelectionModel().getSelectedItem();
        if (category != null && scriptName != null && !scriptName.startsWith("_")) {
            Script script = category.getScript(scriptName);
            boolean enabled = characterCategoryScriptEnabled.isSelected();
            script.setEnabled(enabled);
        }
    }

    public void addItemCategory() {
        if (gameFile != null) {
            ItemCategory category = new ItemCategory("ItemCategory:New");
            gameFile.getItemCategories().add(category);
            itemCategoriesList.getItems().add(category);
        }
    }

    public void removeItemCategory() {
        ItemCategory category = itemCategoriesList.getSelectionModel().getSelectedItem();
        if (category != null) {
            gameFile.getItemCategories().remove(category);
            itemCategoriesList.getItems().remove(category);
            for (Item item : gameFile.getItems()) {
                item.removeCategory(category);
            }
        }
    }

    public void saveItemCategoryName() {
        ItemCategory category = itemCategoriesList.getSelectionModel().getSelectedItem();
        String name = itemCategoryName.getText();
        if (category != null) {
            category.setName(name);
            itemCategoriesList.refresh();
        }
    }

    public void addItemCategoryScript() {
        ItemCategory category = itemCategoriesList.getSelectionModel().getSelectedItem();
        if (category != null) {
            String scriptName = Utils.showPromptDialog("Enter Script name");
            if (scriptName != null) {
                Script script = new Script("", true);
                category.setScript(scriptName, script);
                itemCategoriesScriptsList.getItems().add(scriptName);
            }
        }
    }

    public void removeItemCategoryScript() {
        ItemCategory category = itemCategoriesList.getSelectionModel().getSelectedItem();
        String scriptName = itemCategoriesScriptsList.getSelectionModel().getSelectedItem();
        if (category != null && scriptName != null && !scriptName.startsWith("_")) {
            category.removeScript(scriptName);
            itemCategoriesScriptsList.getItems().remove(scriptName);
        }
    }

    public void saveItemCategoryScript() {
        ItemCategory category = itemCategoriesList.getSelectionModel().getSelectedItem();
        String scriptName = itemCategoriesScriptsList.getSelectionModel().getSelectedItem();
        if (category != null && scriptName != null) {
            Script script = category.getScript(scriptName);
            String scriptText = ((RSyntaxTextArea) itemCategoryScriptNode.getContent()).getText();
            script.setText(scriptText);
        }
    }

    public void setItemCategoryScriptEnabled() {
        ItemCategory category = itemCategoriesList.getSelectionModel().getSelectedItem();
        String scriptName = itemCategoriesScriptsList.getSelectionModel().getSelectedItem();
        if (category != null && scriptName != null && !scriptName.startsWith("_")) {
            Script script = category.getScript(scriptName);
            boolean enabled = itemCategoryScriptEnabled.isSelected();
            script.setEnabled(enabled);
        }
    }

    public void saveSlotName(TableColumn.CellEditEvent<String[], String> stringCellEditEvent) {
        String oldValue = stringCellEditEvent.getOldValue();
        String newValue = stringCellEditEvent.getNewValue();
        String path = gameFile.getSlotNames().get(oldValue);
        gameFile.getSlotNames().remove(oldValue);
        gameFile.getSlotNames().put(newValue, path);
        Equipment.getSlotMap().remove(oldValue);
        Equipment.getSlotMap().put(newValue, path);
        //Change player equipment
        Item playerItem = gameFile.getPlayer().getEquipment().getItemAtSlot(oldValue);
        if (gameFile.getPlayer().getEquipment().getSlots().remove(oldValue) != null) {
            gameFile.getPlayer().getEquipment().getSlots().put(newValue, playerItem);
        }
        //Change characters equipment
        for (GameCharacter character : gameFile.getCharacters()) {
            Item characterItem = character.getEquipment().getItemAtSlot(oldValue);
            if (character.getEquipment().getSlots().remove(oldValue) != null) {
                character.getEquipment().getSlots().put(newValue, characterItem);
            }
        }
        //Change items slots
        gameFile.getItems().stream().
                filter(item -> item.getType() == ItemTypes.EQUIPPABLE).
                filter(item -> item.getEquipmentSlot() == oldValue).forEach(item -> item.setEquipmentSlot(newValue));
        initPlayer();
    }

    public void saveSlotIcon(TableColumn.CellEditEvent<String[], String> stringCellEditEvent) {
        String newValue = stringCellEditEvent.getNewValue();
        String[] row = stringCellEditEvent.getRowValue();
        gameFile.getSlotNames().put(row[0], newValue);
        Equipment.getSlotMap().put(row[0], newValue);
        initSlotsTable();
    }

    public void addSlot() {
        gameFile.getSlotNames().put("New Slot", "data/empty.png");
        Equipment.getSlotMap().put("New Slot", "data/empty.png");
        initSlotsTable();
        initEquipment();
    }

    public void removeSlot() {
        String[] row = equipmentSlotsTable.getSelectionModel().getSelectedItem();
        String slot = row[0];
        gameFile.getSlotNames().remove(slot);
        Equipment.getSlotMap().remove(slot);
        gameFile.getPlayer().getEquipment().getSlots().remove(slot);
        for (GameCharacter character : gameFile.getCharacters()) {
            character.getEquipment().getSlots().remove(slot);
        }
        initEquipment();
        initSlotsTable();
        initPlayer();
        gameFile.getItems().stream().filter(item -> item.getEquipmentSlot().equals(slot)).forEach(item -> {
            item.setType(ItemTypes.GENERIC);
            item.setEquipmentSlot(null);
        });
    }

    public void saveFile() {
        gameFile.save();
    }
}
