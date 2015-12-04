package ru.ekipogh.sud;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Дмитрий on 04.05.2015.
 */
public class Item implements Serializable, Comparable {
    private int id;
    private ItemTypes type;
    private String name;
    private String description;
    private String equipmentSlot;
    private static List<ItemCategory> itemCategories = new ArrayList<>();
    private List<ItemCategory> categories;
    private Map<String, Object> values;
    private Map<String, Script> scripts;
    private boolean locked;
    private List<Item> inventory;
    private boolean isContainer;
    private boolean stackable;

    public boolean isLocked() {
        return locked;
    }

    public void setLocked(boolean locked) {
        this.locked = locked;
    }

    public List<Item> getInventory() {
        return inventory;
    }

    public void setInventory(List<Item> inventory) {
        this.inventory = inventory;
    }

    public boolean isContainer() {
        return isContainer;
    }

    public void setContainer(boolean container) {
        if (container) {
            inventory = new ArrayList<>();
        } else {
            inventory = null;
        }
        isContainer = container;
    }

    public Item(String name) {
        this.isContainer = false;
        this.stackable = false;
        this.locked = false;
        this.name = name;
        this.type = ItemTypes.GENERIC;
        this.description = "";
        this.id = Sequencer.getNewID();
        this.scripts = new HashMap<>();
        this.scripts.put("_onTake", new Script("", true));
        this.scripts.put("_onDrop", new Script("", true));
        this.scripts.put("_onEquip", new Script("", true));
        this.scripts.put("_onUse", new Script("", true));
        this.scripts.put("_onUnequip", new Script("", true));
        this.scripts.put("_onUnlock", new Script("caller.setLocked(false);", true));
        this.values = new HashMap<>();
        this.categories = new ArrayList<>();
    }

    public static void setItemCategories(List<ItemCategory> categories) {
        Item.itemCategories = categories;
    }

    public static List<ItemCategory> getItemCategories() {
        return Item.itemCategories;
    }

    public ItemTypes getType() {
        return type;
    }

    public void setType(ItemTypes type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return name;
    }

    public String getEquipmentSlot() {
        return equipmentSlot;
    }

    public void setEquipmentSlot(String equipmentSlot) {
        Equipment.getSlotNames().stream().filter(s -> s.equals(equipmentSlot)).forEach(s -> this.equipmentSlot = equipmentSlot);
    }

    public int getId() {
        return id;
    }

    public static void addItemCategory(ItemCategory itemCategory) {
        Item.itemCategories.add(itemCategory);
    }

    public static void deleteItemCategory(ItemCategory itemCategory) {
        Item.itemCategories.remove(itemCategory);
    }

    public List<ItemCategory> getCategories() {
        return categories;
    }

    public void removeCategory(ItemCategory category) {
        this.categories.remove(category);
    }

    public void setCategories(List<ItemCategory> categories) {
        this.categories = categories;
    }

    public void setValue(String valueName, Object value) {
        this.values.put(valueName, value);
    }

    public Object getValue(String valueName) {
        return values.get(valueName);
    }

    public Map getValues() {
        return values;
    }

    public static void clearItemsCategories() {
        Item.itemCategories = new ArrayList<>();
    }

    public Map<String, Script> getScripts() {
        return scripts;
    }

    public Script getScript(String scriptName) {
        return scripts.get(scriptName);
    }

    public void setScript(String scriptName, Script script) {
        scripts.put(scriptName, script);
    }

    public void removeScript(String scriptName) {
        this.scripts.remove(scriptName);
    }

    public void addCategory(ItemCategory category) {
        if (!categories.contains(category)) {
            categories.add(category);
        }
    }

    public void addItem(Item item) {
        inventory.add(item);
    }

    public void removeItem(Item item) {
        inventory.remove(item);
    }

    public boolean isStackable() {
        return stackable;
    }

    public void setStackable(boolean stackable) {
        this.stackable = stackable;
    }

    @Override
    public int compareTo(Object i) {
        if (this.id == ((Item) i).getId()) return 0;
        return this.name.compareTo(((Item) i).getName());
    }
}
