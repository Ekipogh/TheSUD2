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
import ru.ekipogh.sud.GameFile;
import ru.ekipogh.sud.Script;
import ru.ekipogh.sud.Sequencer;
import ru.ekipogh.sud.SudTimer;
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
    public TableColumn<Map.Entry<String, String>, String> keyColumn;
    public TableColumn<Map.Entry<String, String>, String> valueColumn;

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

        //Lists listeners
        //Common tab
        gameScriptsList.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) ->
                selectGameScript(newValue));
        timersList.getSelectionModel().selectedItemProperty().addListener(((observable, oldValue, newValue) ->
                selectTimer(newValue)));
        //Items tab
        itemsList.getSelectionModel().selectedItemProperty().addListener(((observable, oldValue, newValue) ->
                selectItem(newValue)));
        itemScriptsList.getSelectionModel().selectedItemProperty().addListener(((observable, oldValue, newValue) -> selectItemScript(newValue)));

        gameFile = new GameFile();
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
            itemParameters.getColumns().setAll(keyColumn, valueColumn);
        }
    }

    private void selectItemScript(String scriptName) {
        Item item = itemsList.getSelectionModel().getSelectedItem();
        if (item != null && scriptName != null) {
            Script script = item.getScript(scriptName);
            ((RSyntaxTextArea) itemScriptNode.getContent()).setText(script.getText());
            itemScriptEnabled.setSelected(script.isEnabled());
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

        Sequencer.setID(gameFile.getSequencerID());
    }

    private void initItemParametersTable() {
        keyColumn.setCellValueFactory(EditorController::mapKey);

        valueColumn.setCellValueFactory(EditorController::mapValue);
        valueColumn.setCellFactory(TextFieldTableCell.forTableColumn());
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

    public void changeValue(TableColumn.CellEditEvent<Map.Entry<String, String>, String> event) {
        event.getRowValue().setValue(event.getNewValue());
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
        itemsList.refresh();
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
}
