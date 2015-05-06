package ru.ekipogh.sud;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by dedov_d on 23.04.2015.
 */
public class Location implements Serializable {
    private String name; //required location Name
    private int id;  //required location ID
    private String description;  //optional location Description
    private Location[] exits;  //items can be null
    private List<Item> inventory;

    public List<Item> getInventory() {
        return inventory;
    }

    public int getId() {
        return id;
    }

    public Location(String name) {
        this.name = name;
        this.id = Sequencer.getNewLocationID();
        this.description = "";
        this.exits = new Location[4];
        this.inventory = new ArrayList<Item>();
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setNorth(Location north) {
        exits[0] = north;
    }

    public void setSouth(Location south) {
        exits[1] = south;
    }

    public void setEast(Location east) {
        exits[2] = east;
    }

    public void setWest(Location west) {
        exits[3] = west;
    }

    public Location getNorth() {
        return exits[0];
    }

    public Location getSouth() {
        return exits[1];
    }

    public Location getEast() {
        return exits[2];
    }

    public Location getWest() {
        return exits[3];
    }

    public void removeFromExits(Location toRemove) {
        if (toRemove != null) {
            for (int i = 0; i < exits.length; i++) {
                if (toRemove.equals(exits[i]))
                    exits[i] = null;
            }
        }
    }

    public void addItem(Item item) {
        this.inventory.add(item);
    }

    public void removeItem(Item item) {
        inventory.remove(item);
    }

    private static class Sequencer {
        private static int id = 0;

        public static int getNewLocationID() {
            return id++;
        }

        public static void setID(int id) throws Exception {
            if (Sequencer.id == 0)
                Sequencer.id = id;
            else
                throw new Exception("Sequencer id has been set already");
        }
    }

    public String toString() {
        return name;
    }

    public boolean equals(Location location) {
        return location != null && this.id == location.id;
    }
}
