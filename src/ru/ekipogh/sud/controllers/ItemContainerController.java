package ru.ekipogh.sud.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import ru.ekipogh.sud.GameFile;
import ru.ekipogh.sud.SudPair;
import ru.ekipogh.sud.Utils;
import ru.ekipogh.sud.objects.Item;

/**
 * Created by Ektril Pogh on 26.08.2018.
 */
public class ItemContainerController {
    private Item container;
    public ListView<Item> itemsList;
    public ListView<SudPair<Item, Integer>> itemItemsList;

    @FXML
    public void initialize() {
        ScreenController.setController("itemContainer", this);
    }

    public void init(Item item, GameFile gameFile) {
        container = item;
        itemsList.getItems().setAll(gameFile.getItems());
        refresh();
    }

    private void refresh() {
        itemItemsList.getItems().setAll(container.getInventory().getItems());
        itemItemsList.refresh();
    }

    public void addSeveralItems() {
        int amount = Utils.showSpinnerDialog();
        Item item = itemsList.getSelectionModel().getSelectedItem();
        if (item != null && amount != 0) {
            addItems(item, amount);
        }
    }

    public void addOneItem() {
        Item item = itemsList.getSelectionModel().getSelectedItem();
        if (item != null) {
            addItems(item, 1);
        }
    }

    private void addItems(Item item, int amount) {
        container.addItem(item, amount);
        refresh();
    }

    public void removeSeveralItems() {
        int amount = Utils.showSpinnerDialog();
        SudPair<Item, Integer> pair = itemItemsList.getSelectionModel().getSelectedItem();
        if (pair != null && amount != 0) {
            Item item = pair.getKey();
            removeItem(item, amount);
            itemItemsList.getSelectionModel().select(pair);
        }
    }

    public void removeOneItem() {
        SudPair<Item, Integer> pair = itemItemsList.getSelectionModel().getSelectedItem();
        if (pair != null) {
            Item item = pair.getKey();
            removeItem(item, 1);
            itemItemsList.getSelectionModel().select(pair);
        }
    }

    private void removeItem(Item item, int amount) {
        container.removeItem(item, amount);
        refresh();
    }

    public void backToEditor() {
        ScreenController.activate("editor");
    }
}
