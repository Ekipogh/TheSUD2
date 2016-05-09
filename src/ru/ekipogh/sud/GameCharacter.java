package ru.ekipogh.sud;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by dedov_d on 03.07.2015.
 */
public class GameCharacter extends GameObject implements Serializable {
    public static final long serialVersionUID = 5151657683640298947L;
    private static List<CharacterCategory> characterCategories = new ArrayList<>();
    private Location location;
    private Equipment equipment;
    private List<CharacterCategory> categories;


    public static void setCharacterCategories(List<CharacterCategory> characterCategories) {
        GameCharacter.characterCategories = characterCategories;
    }

    public static List<CharacterCategory> getCharacterCategories() {
        return characterCategories;
    }

    public static void addCharacterCategory(CharacterCategory characterCategory) {
        characterCategories.add(characterCategory);
    }

    public static void deleteCategory(CharacterCategory characterCategory) {
        characterCategories.remove(characterCategory);
    }

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

    public void setLocation(Location location) {
        this.location = location;
    }

    public Location getLocation() {
        return location;
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

    public List<CharacterCategory> getCategories() {
        return categories;
    }

    public void removeCategory(CharacterCategory category) {
        this.categories.remove(category);
    }

    public static void clearCharactersCategories() {
        characterCategories = new ArrayList<>();
    }

    public void setCategories(List<CharacterCategory> categories) {
        this.categories = categories;
    }

    public void addCategory(CharacterCategory category) {
        if (!categories.contains(category))
            categories.add(category);
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
                if (characterCategory.getScript(scriptName) != null) {
                    scriptsEnabled.put(scriptName, enabled);
                    return;
                }
            }
        }
        System.out.println("Не нашел такого " + scriptName);
    }

    public boolean isScriptEnabled(String scriptName) {
        Script script = scripts.get(scriptName);
        if (script != null) {
            return script.isEnabled();
        }
        for (CharacterCategory characterCategory : categories) {
            script = characterCategory.getScript(scriptName);
            if (script != null) {
                Boolean toReturn = scriptsEnabled.get(scriptName);
                if (toReturn != null) {
                    return toReturn;
                } else {
                    return script.isEnabled();
                }
            }
        }
        return false;
    }

    public CharacterCategory getCategory(String categoryName) {
        for (CharacterCategory characterCategory : categories) {
            if (characterCategory.getName().equals(categoryName)) {
                return characterCategory;
            }
        }
        return null;
    }
}
