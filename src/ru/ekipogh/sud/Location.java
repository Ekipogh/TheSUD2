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
    private Location[] exits;  //items can be null 0 = north 1 = south 2 = east 3 = west 4 = up 5 = down
    private boolean[] exitsOpened; //is direction available
    private List<Item> inventory; //stores items
    @Deprecated
    private String picturePath; //picture of location
    private static List<LocationCategory> locationCategories = new ArrayList<>(); //categories of locations
    private Map<String, Object> values; //values and names of custom variables
    private Map<String, Script> scripts; //
    private List<LocationCategory> categories;

    public static List<LocationCategory> getLocationCategories() {
        return locationCategories;
    }

    public static void setLocationCategories(List<LocationCategory> categories) {
        Location.locationCategories = categories;
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
        this.exits = new Location[6];
        this.exitsOpened = new boolean[6];
        this.inventory = new ArrayList<>();
        this.values = new HashMap<>();
        this.scripts = new HashMap<>();
        this.scripts.put("_onEnter", new Script("", true));
        this.scripts.put("_onLeave", new Script("", true));
        this.categories = new ArrayList<>();
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

    public void setUp(Location up) {
        exits[4] = up;
    }

    public void setDown(Location down) {
        exits[5] = down;
    }

    public void setNorthOpened(boolean opened) {
        exitsOpened[0] = opened;
    }

    public void setSouthOpened(boolean opened) {
        exitsOpened[1] = opened;
    }

    public void setEastOpened(boolean opened) {
        exitsOpened[2] = opened;
    }

    public void setWestOpened(boolean opened) {
        exitsOpened[3] = opened;
    }

    public void setUpOpened(boolean opened) {
        exitsOpened[4] = opened;
    }

    public void setDownOpened(boolean opened) {
        exitsOpened[5] = opened;
    }

    public boolean isNorthOpened() {
        return exitsOpened[0];
    }

    public boolean isSouthOpened() {
        return exitsOpened[1];
    }

    public boolean isEastOpened() {
        return exitsOpened[2];
    }

    public boolean isWestOpened() {
        return exitsOpened[3];
    }

    public boolean isUpOpened() {
        return exitsOpened[4];
    }

    public boolean isDownOpened() {
        return exitsOpened[5];
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

    public Location getUp() {
        return exits[4];
    }

    public Location getDown() {
        return exits[5];
    }

    public void removeFromExits(Location toRemove) {
        if (toRemove != null) {
            for (int i = 0; i < exits.length; i++) {
                if (toRemove.equals(exits[i])) {
                    this.exits[i] = null;
                    this.exitsOpened[i] = false;
                }
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

    public static void addLocationCategory(LocationCategory locationCategory) {
        locationCategories.add(locationCategory);
    }

    public static void deleteLocationCategory(LocationCategory locationCategory) {
        locationCategories.remove(locationCategory);
    }

    public List<LocationCategory> getCategories() {
        return categories;
    }

    public void removeCategory(LocationCategory category) {
        this.categories.remove(category);
    }

    public void addCategory(LocationCategory category) {
        if (!categories.contains(category)) {
            categories.add(category);
        }
    }

    public static void clearLocationsCategories() {
        locationCategories = new ArrayList<>();
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

    public void setCategories(List<LocationCategory> categories) {
        this.categories = categories;
    }
}
