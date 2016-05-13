package ru.ekipogh.sud.objects;

import ru.ekipogh.sud.Script;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by ekipogh on 03.07.2015.
 * licensed under WTFPL
 */
public class GameCharacter extends GameObject implements Serializable {
    public static final long serialVersionUID = 1L;
    private static List<CharacterCategory> characterCategories = new ArrayList<>();
    private Location location;
    private Equipment equipment;


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

    public static void clearCharactersCategories() {
        characterCategories = new ArrayList<>();
    }

    public GameCharacter(String name) {
        super(name);
        this.inventory = new Inventory();
        this.equipment = new Equipment();
        this.scripts.put("_onPlayerArrive", new Script("", true));
        this.scripts.put("_onPlayerLeave", new Script("", true));
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

    public void setEquipedItem(String slot, Item item) {
        equipment.setItemAtSlot(slot, item);
    }
}
