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
    private String picturePath;
    private boolean available;
    private static List<LocationCategory> categories = new ArrayList<>();
    private LocationCategory category;
    private Map<String, Object> values;
    private Map<String, Script> scripts;

    public static List<LocationCategory> getCategories() {
        return categories;
    }

    public static void setCategories(List<LocationCategory> categories) {
        Location.categories = categories;
    }

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
        this.available = true;
        this.values = new HashMap<>();
        this.scripts = new HashMap<>();
        this.scripts.put("_onEnter", new Script("", true));
        this.scripts.put("_onLeave", new Script("", true));
    }

    public void setValue(String valueName, Object value) {
        this.values.put(valueName, value);
    }

    public Object getValue(String valueName) {
        return values.get(valueName);
    }

    public Map getValues() {
        return values;
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

    public static void addNewCategory(LocationCategory locationCategory) {
        categories.add(locationCategory);
    }

    public static void deleteCategory(LocationCategory locationCategory) {
        categories.remove(locationCategory);
    }

    public LocationCategory getCategory() {
        return category;
    }

    public void removeCategory() {
        this.category = null;
    }

    public void setCategory(LocationCategory category) {
        this.category = category;
    }

    public static void clearCategories() {
        categories = new ArrayList<>();
    }

    public Map<String, Script> getScripts() {
        return scripts;
    }

    public Script getScript(String scriptName) {
        return scripts.get(scriptName);
    }

    public void setScript(String scriptName, Script script) {
        scripts.put(scriptName, script);
    }

    public void removeScript(String scriptName) {
        this.scripts.remove(scriptName);
    }
}
