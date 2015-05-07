package ru.ekipogh.sud;

import java.io.Serializable;

/**
 * Created by Дмитрий on 04.05.2015.
 */
public class Item implements Serializable {
    private ItemTypes type;
    private String name;
    private String description;
    private String equipmentSlot;

    public Item(String name) {
        this.name = name;
        this.type = ItemTypes.GENERIC;
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
        for (String s : Equipment.getSlotNames()) {
            if (s.equals(equipmentSlot))
                this.equipmentSlot = equipmentSlot;
        }
    }
}
