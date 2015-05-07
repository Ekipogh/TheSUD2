package ru.ekipogh.sud;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by dedov_d on 24.04.2015.
 */
public class Player implements Serializable {
    private String name;
    private short sex;
    private Location location;
    private List<Item> inventory;

    public Equipment getEquipment() {
        return equipment;
    }

    public void setEquipment(Equipment equipment) {
        this.equipment = equipment;
    }

    private Equipment equipment;

    public Player(String name) {
        this.name = name;
        this.sex = 0;
        this.inventory = new ArrayList<Item>();
        equipment = new Equipment();
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

    public void setSex(short sex) {
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
}
