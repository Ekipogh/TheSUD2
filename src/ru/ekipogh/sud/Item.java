package ru.ekipogh.sud;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Дмитрий on 04.05.2015.
 */
public class Item implements Serializable {
    private int id;
    private ItemTypes type;
    private String name;
    private String description;
    private String equipmentSlot;
    private Map<String, String> scripts;
    private static List<ItemCategory> categories = new ArrayList<>();
    private ItemCategory category;

    public Item(String name) {
        this.name = name;
        this.type = ItemTypes.GENERIC;
        this.id = Sequencer.getNewID();
        this.scripts = new HashMap<>();
        this.scripts.put("_onTake", "");
        this.scripts.put("_onDrop", "");
        this.scripts.put("_onEquip", "");
        this.scripts.put("_onUse", "");
        this.scripts.put("_onUnequip", "");
    }

    public static void setCategories(List<ItemCategory> categories) {
        Item.categories = categories;
    }

    public static List<ItemCategory> getCategories() {
        return categories;
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

    public Map<String, String> getScripts() {
        return scripts;
    }

    public void setScript(String scriptName, String scriptText) {
        scripts.put(scriptName, scriptText);
    }

    public String getScript(String scriptName) {
        return scripts.get(scriptName);
    }

    public void removeScript(String scriptName) {
        scripts.remove(scriptName);
    }

    public void addScript(String scriptName, String scriptText) {
        scripts.put(scriptName, scriptText);
    }

    public int getId() {
        return id;
    }

    public static void addNewCategory(ItemCategory itemCategory) {
        categories.add(itemCategory);
    }

    public static void deleteCategory(ItemCategory itemCategory) {
        categories.remove(itemCategory);
    }

    public ItemCategory getCategory() {
        return category;
    }

    public void removeCategory() {
        this.category = null;
    }

    public void setCategory(ItemCategory category) {
        this.category = category;
    }

    public static void clearCategories() {
        categories = new ArrayList<>();
    }
}
