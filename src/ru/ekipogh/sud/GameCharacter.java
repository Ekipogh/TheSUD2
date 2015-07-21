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
    private short sex;
    private Location location;
    private List<Item> inventory;

    private Map<String, String> scripts;

    public Equipment getEquipment() {
        return equipment;
    }

    public void setEquipment(Equipment equipment) {
        this.equipment = equipment;
    }

    private Equipment equipment;

    public GameCharacter(String name) {
        this.name = name;
        this.sex = 0;
        this.inventory = new ArrayList<>();
        this.equipment = new Equipment();
        this.scripts = new HashMap<>();
        this.scripts.put("onPlayerArrive", "");
        this.scripts.put("onPlayerLeave", "");
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

    private void setSex(short sex) {
        if (sex >= 0 && sex <= 2)
            this.sex = sex;
    }

    public void setSex(int sex) {
        setSex((short) (sex));
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        if (name.length() < 21)
            this.name = name;

    }

    public short getSex() {
        return sex;
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

    public String toString() {
        return this.name;
    }
}
