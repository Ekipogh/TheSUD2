package ru.ekipogh.sud;

import java.io.Serializable;
import java.util.HashMap;
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

    public Item(String name) {
        this.name = name;
        this.type = ItemTypes.GENERIC;
        this.id = Sequencer.getNewID();
        this.scripts = new HashMap<>();
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
}
