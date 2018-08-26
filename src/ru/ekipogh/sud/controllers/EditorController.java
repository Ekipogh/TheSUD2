package ru.ekipogh.sud.controllers;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.embed.swing.SwingNode;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Window;
import org.fife.ui.rsyntaxtextarea.RSyntaxTextArea;
import ru.ekipogh.sud.*;
import ru.ekipogh.sud.objects.*;

import java.io.File;
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

    private GameFile gameFile;

    private static ObservableValue<String> mapKey(TableColumn.CellDataFeatures<Map.Entry<String, String>, String> p) {
        return new SimpleStringProperty(p.getValue().getKey());
    }

    private static ObservableValue<String> mapValue(TableColumn.CellDataFeatures<Map.Entry<String, String>, String> p) {
        return new SimpleStringProperty(p.getValue().getValue());
    }

    @FXML
    public void initialize() {
        //Syntax areas
        //Common tab
        initScriptNode.setContent(new RSyntaxTextArea());
        gameScriptsNode.setContent(new RSyntaxTextArea());
        timerScriptNode.setContent(new RSyntaxTextArea());
        //Items tab
        itemScriptNode.setContent(new RSyntaxTextArea());
        //Location tab
        locationScriptNode.setContent(new RSyntaxTextArea());

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
        gameFile = new GameFile();
        ScreenController.setController("editor", this);
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
            itemScriptsList.getItems().clear();
            itemScriptsList.getItems().addAll(scriptNames);
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

    private String chooseFile(Window window) {
        FileChooser chooser = new FileChooser();
        chooser.setTitle("Open a game file");
        chooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("SUD game", "*.sud"),
                new FileChooser.ExtensionFilter("All files", "*.*"));
        File file = chooser.showOpenDialog(window);
        if (file != null) {
            return file.getAbsolutePath();
        }
        return null;
    }

    public void openFile() {
        String gameFilePath = chooseFile(ScreenController.getMain().getWindow());
        if (gameFilePath != null) {
            gameFile = GameFile.open(gameFilePath);
            editorInit();
        }
    }

    private void editorInit() {
        cleanEditor();
        //Common tab
        gameName.setText(gameFile.getGameName());
        gameDescription.setText(gameFile.getGameStartMessage());
        ((RSyntaxTextArea) initScriptNode.getContent()).setText(gameFile.getInitScript());
        gameScriptsList.getItems().addAll(gameFile.getCommonScripts().keySet());
        timersList.getItems().addAll(gameFile.getTimers());
        //Items tab
        itemsList.getItems().addAll(gameFile.getItems());
        initEquipment();
        initItemCategories();
        initItemParametersTable();
        //Location tab
        locationsList.getItems().addAll(gameFile.getLocations());
        for (Location location : locationsList.getItems()) {
            locationNorthExit.getItems().add(location);
            locationSouthExit.getItems().add(location);
            locationWestExit.getItems().add(location);
            locationEastExit.getItems().add(location);
            locationUpExit.getItems().add(location);
            locationDownExit.getItems().add(location);
        }
        locationAllItemsList.getItems().addAll(gameFile.getItems());
        locationsCategories.getItems().setAll(gameFile.getLocationCategories());
        initLocationParametersTable();
        Sequencer.setID(gameFile.getSequencerID());
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

    private void initItemCategories() {
        List<ItemCategory> categories = gameFile.getItemCategories();
        categories.forEach(itemsCategories.getItems()::add);
    }

    private void initEquipment() {
        Map<String, String> slots = gameFile.getSlotNames();
        Equipment.setSlotNames(slots);
        slots.keySet().forEach(itemEquipmentSlot.getItems()::add);
    }

    private void cleanEditor() {
        gameScriptsList.getItems().clear();
        timersList.getItems().clear();
        itemsList.getItems().clear();
        itemScriptsList.getItems().clear();
        itemEquipmentSlot.getItems().clear();
        itemsCategories.getItems().clear();
        locationNorthExit.setItems(FXCollections.observableArrayList(new Location[]{null}));
        locationSouthExit.setItems(FXCollections.observableArrayList(new Location[]{null}));
        locationWestExit.setItems(FXCollections.observableArrayList(new Location[]{null}));
        locationEastExit.setItems(FXCollections.observableArrayList(new Location[]{null}));
        locationUpExit.setItems(FXCollections.observableArrayList(new Location[]{null}));
        locationDownExit.setItems(FXCollections.observableArrayList(new Location[]{null}));
        Equipment.clearSlots();
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
            ItemTypes type = itemType.getSelectionModel().getSelectedItem();
            item.setType(type);
            itemEquipmentSlot.setDisable(type != ItemTypes.EQUIPPABLE);
        }
    }

    public void setItemEquipmentSlot() {
        Item item = itemsList.getSelectionModel().getSelectedItem();
        if (item != null) {
            String slot = itemEquipmentSlot.getValue();
            item.setEquipmentSlot(slot);
        }
    }

    public void saveItemScript() {
        Item item = itemsList.getSelectionModel().getSelectedItem();
        String scriptName = itemScriptsList.getSelectionModel().getSelectedItem();
        if (item != null && scriptName != null) {
            String scriptText = ((RSyntaxTextArea) itemScriptNode.getContent()).getText();
            boolean enabled = itemScriptEnabled.isSelected();
            item.setScript(scriptName, new Script(scriptText, enabled));
        }
    }

    public void setItemScriptEnabled() {
        Item item = itemsList.getSelectionModel().getSelectedItem();
        String scriptName = itemScriptsList.getSelectionModel().getSelectedItem();
        if (item != null && scriptName != null) {
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
            itemCategories.refresh();
        }
    }

    public void addItem() {
        Item item = new Item("New Item");
        gameFile.getItems().add(item);
        itemsList.getItems().add(item);
        locationAllItemsList.getItems().add(item);
        itemsList.refresh();
        itemsList.getSelectionModel().select(item);
    }

    public void removeItem() {
        Item item = itemsList.getSelectionModel().getSelectedItem();
        if (item != null) {
            itemsList.getItems().remove(item);
            gameFile.getItems().remove(item);
            itemsList.refresh();
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
            });
            itemScriptsList.refresh();
        }
    }

    public void removeItemScript() {
        Item item = itemsList.getSelectionModel().getSelectedItem();
        String scriptName = itemScriptsList.getSelectionModel().getSelectedItem();
        if (item != null && scriptName != null) {
            item.getScripts().remove(scriptName);
            itemScriptsList.getItems().remove(scriptName);
            itemScriptsList.refresh();
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
            location.setScript(scriptName, new Script(scriptText, true));
        }
    }

    public void addSeveralItems() {
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

    public void removeSeveralItems() {
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
    }

    public void removeLocation() {
        Location location = locationsList.getSelectionModel().getSelectedItem();
        if (location != null) {
            locationsList.getItems().remove(location);
            gameFile.getLocations().remove(location);
            locationsList.refresh();
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
        if (location != null && scriptName != null) {
            locationScriptEnabled.setSelected(location.getScript(scriptName).isEnabled());
        }
    }
}
