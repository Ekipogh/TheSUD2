package ru.ekipogh.sud;

import javax.swing.*;
import java.io.Serializable;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by dedov_d on 07.05.2015.
 */
public class Equipment implements Serializable {
    private Map<String, Item> slots;

    public static void setSlotNames(Map<String, String> slotNames) {
        Equipment.slotNames = slotNames;
    }

    public Map<String, Item> getSlots() {
        return slots;
    }

    //private static String[] slotNames = {"голова", "тело", "ноги", "рука"}; //default equipment slots
    private static Map<String, String> slotNames = new HashMap<>();

    static {
        slotNames.put("голова", "src/data/head.png");
        slotNames.put("торс", "src/data/torso.png");
        slotNames.put("ноги", "src/data/legs.png");
        slotNames.put("рука", "src/data/hand.png");
    }

    public static Map<String, String> getSlotMap() {
        return slotNames;
    }

    public static Collection<String> getSlotNames() {
        return slotNames.keySet();
    }

    public static void addSlotName(String imagePath, String slotName) {
        /*int length = slotNames.length;
        slotNames = Arrays.copyOf(slotNames, length + 1);
        slotNames[length] = slotName;*/
        slotNames.put(slotName, imagePath);
    }

    public Equipment() {
        slots = new HashMap<>();
        for (String slotsName : slotNames.keySet()) {
            slots.put(slotsName, null);
        }
    }

    public Item getItemAtSlot(String slot) {
        return slots.get(slot);
    }

    public void equip(Item item) {
        String slot = item.getEquipmentSlot();
        if (slots.get(slot) != null) {
            JOptionPane.showMessageDialog(null, "Сначала снимите предмет со слота " + slot);
        }
        slots.put(slot, item);
    }

    public static String getImage(String slot) {
        return slotNames.get(slot);
    }

    public void uneqip(Item item) {
        String slot = item.getEquipmentSlot();
        slots.remove(slot);
    }
}
