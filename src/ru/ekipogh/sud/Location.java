package ru.ekipogh.sud;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by dedov_d on 23.04.2015.
 */
public class Location implements Serializable {
    private String name; //required location Name
    private int id;  //required location ID
    private String description;  //optional location Description
    private Location[] exits;  //items can be null
    private List<Item> inventory;
    private Map<String, String> scripts;
    private String picturePath;
    private boolean available;

    public String getPicturePath() {
        return picturePath;
    }

    public void setPicturePath(String picturePath) {
        this.picturePath = picturePath;
    }

    public List<Item> getInventory() {
        return inventory;
    }

    public int getId() {
        return id;
    }

    public Location(String name) {
        this.name = name;
        this.id = Sequencer.getNewID();
        this.description = "";
        this.exits = new Location[4];
        this.inventory = new ArrayList<>();
        this.scripts = new HashMap<>();
        scripts.put("onEnter", "");
        scripts.put("onLeave", "");
        this.available = true;
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
        return name;
    }

    public boolean equals(Location location) {
        return location != null && this.id == location.id;
    }

    public boolean isAvailable() {
        return available;
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }
}
