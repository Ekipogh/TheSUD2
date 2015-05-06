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

    public Player(String name) {
        this.name = name;
        this.sex = 0;
        this.inventory = new ArrayList<Item>();
    }

    public List<Item> getInventory() {
        return inventory;
    }

    public void setInventory(List<Item> inventory) {
        this.inventory = inventory;
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
        //TODO: stub: implement equipment!!!
    }
}
