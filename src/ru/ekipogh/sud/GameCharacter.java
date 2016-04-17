package ru.ekipogh.sud;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by dedov_d on 03.07.2015.
 */
public class GameCharacter implements Serializable {
    public static final long serialVersionUID = 5151657683640298947L;
    private String name;
    private Location location;
    private Inventory inventory;
    private static List<CharacterCategory> characterCategories = new ArrayList<>();
    private Map<String, Object> values;
    private int id;

    private Map<String, Script> scripts;
    private List<CharacterCategory> categories;

    public void setDescription(String description) {
        this.description = description;
    }

    private String description;

    public static void setCharacterCategories(List<CharacterCategory> characterCategories) {
        GameCharacter.characterCategories = characterCategories;
    }

    public static List<CharacterCategory> getCharacterCategories() {
        return characterCategories;
    }

    private Equipment equipment;

    public GameCharacter(String name) {
        this.name = name;
        this.inventory = new Inventory();
        this.equipment = new Equipment();
        this.scripts = new HashMap<>();
        this.scripts.put("_onPlayerArrive", new Script("", true));
        this.scripts.put("_onPlayerLeave", new Script("", true));
        this.values = new HashMap<>();
        this.id = Sequencer.getNewID();
        this.categories = new ArrayList<>();
    }

    public Inventory getInventory() {
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
        this.name = name;

    }

    public void addItem(Item item) {
        addItem(item, 1);
    }

    public boolean equip(Item item) {
        return equipment.equip(item);
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

    public static void addCharacterCategory(CharacterCategory characterCategory) {
        characterCategories.add(characterCategory);
    }

    public static void deleteCategory(CharacterCategory characterCategory) {
        characterCategories.remove(characterCategory);
    }

    public List<CharacterCategory> getCategories() {
        return categories;
    }

    public void removeCategory(CharacterCategory category) {
        this.categories.remove(category);
    }

    public static void clearCharactersCategories() {
        characterCategories = new ArrayList<>();
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

    public void setCategories(List<CharacterCategory> categories) {
        this.categories = categories;
    }

    public void addCategory(CharacterCategory category) {
        if (!categories.contains(category))
            categories.add(category);
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

    public void addItem(Item item, int count) {
        inventory.add(item, count);
    }

    public void removeItem(Item item, int count) {
        inventory.remove(item, count);
    }

    public void setEquipedItem(String slot, Item item) {
        equipment.setItemAtSlot(slot, item);
    }

    public void setScriptEnabled(String scriptName, boolean enabled) {
        Script script;
        if ((script = getScript(scriptName)) != null) {
            script.setEnabled(enabled);
            return;
        } else {
            for (CharacterCategory characterCategory : categories) {
                if ((script = characterCategory.getScript(scriptName)) != null) {
                    script.setEnabled(enabled);
                    return;
                }
            }
        }
    }
}
