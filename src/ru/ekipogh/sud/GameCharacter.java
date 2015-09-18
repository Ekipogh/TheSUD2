package ru.ekipogh.sud;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by dedov_d on 03.07.2015.
 */
public class GameCharacter implements Serializable { //TODO: Нужни ли различные классы для игрока и неписей?
    private String name;
    private Location location;
    private List<Item> inventory;
    private static List<CharacterCategory> categories = new ArrayList<>();
    private Map<String, Object> values;
    private int id;

    private Map<String, Script> scripts;
    private CharacterCategory category;

    public void setDescription(String description) {
        this.description = description;
    }

    private String description;

    public static void setCategories(List<CharacterCategory> categories) {
        GameCharacter.categories = categories;
    }

    public static List<CharacterCategory> getCategories() {
        return categories;
    }

    private Equipment equipment;

    public GameCharacter(String name) {
        this.name = name;
        this.inventory = new ArrayList<>();
        this.equipment = new Equipment();
        this.scripts = new HashMap<>();
        this.scripts.put("_onPlayerArrive", new Script("", true));
        this.scripts.put("_onPlayerLeave", new Script("", true));
        this.values = new HashMap<>();
        this.id = Sequencer.getNewID();
    }

    public List<Item> getInventory() {
        return inventory;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public Location getLocation() {
        return location;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        if (name.length() < 21)
            this.name = name;

    }

    public void addToInventory(Item item) {
        inventory.add(item);
    }

    public void equip(Item item) {
        equipment.equip(item);
    }

    public Item getEquipedItem(String slot) {
        return equipment.getItemAtSlot(slot);
    }

    public void unequip(Item item) {
        inventory.add(item);
        equipment.uneqip(item);
    }

    public String toString() {
        return this.name;
    }

    public static void addNewCategory(CharacterCategory characterCategory) {
        categories.add(characterCategory);
    }

    public static void deleteCategory(CharacterCategory characterCategory) {
        categories.remove(characterCategory);
    }

    public CharacterCategory getCategory() {
        return category;
    }

    public void removeCategory() {
        this.category = null;
    }

    public static void clearCategories() {
        categories = new ArrayList<>();
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

    public int getId() {
        return id;
    }

    public String getDescription() {
        return description;
    }

    public void setCategory(CharacterCategory category) {
        this.category = category;
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
}
