package ru.ekipogh.sud.objects;

import javax.swing.*;
import java.io.Serializable;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by ekipogh on 07.05.2015.
 */
public class Equipment implements Serializable {
    public static final long serialVersionUID = 1L;
    private Map<String, Item> slots;
    private static Map<String, String> slotNames;

    static {
        slotNames = new HashMap<>();
    }

    public Equipment() {
        slots = new HashMap<>();
        for (String slotsName : slotNames.keySet()) {
            slots.put(slotsName, null);
        }
    }

    public static void setSlotNames(Map<String, String> slotNames) {
        Equipment.slotNames = slotNames;
    }

    public static Map<String, String> getSlotMap() {
        return slotNames;
    }

    public static Collection<String> getSlotNames() {
        return slotNames.keySet();
    }

    public static void addSlotName(String imagePath, String slotName) {
        slotNames.put(slotName, imagePath);
    }

    public static String getImage(String slot) {
        return slotNames.get(slot);
    }

    public static void clearSlots() {
        slotNames.clear();
    }

    public Map<String, Item> getSlots() {
        return slots;
    }

    public Item getItemAtSlot(String slot) {
        return slots.get(slot);
    }

    public boolean equip(Item item) {
        String slot = item.getEquipmentSlot();
        if (slots.get(slot) != null) {
            return false;
        }
        slots.put(slot, item);
        return true;
    }

    public void uneqip(Item item) {
        String slot = item.getEquipmentSlot();
        slots.remove(slot);
    }

    public void clear() {
        slots.clear();
    }

    public void setItemAtSlot(String slot, Item item) {
        slots.put(slot, item);
    }
}
