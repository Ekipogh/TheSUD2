package ru.ekipogh.sud.objects;

import ru.ekipogh.sud.Script;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by ekipogh on 23.04.2015.
 * licensed under WTFPL
 */
public class Location extends GameObject implements Serializable {
    private static final long serialVersionUID = 1L;
    private Location[] exits;  //items can be null 0 = north 1 = south 2 = east 3 = west 4 = up 5 = down
    private boolean[] exitsOpened; //is direction available

    private static List<LocationCategory> locationCategories = new ArrayList<>(); //categories of locations

    public static List<LocationCategory> getLocationCategories() {
        return locationCategories;
    }

    public static void setLocationCategories(List<LocationCategory> categories) {
        Location.locationCategories = categories;
    }

    public static void addLocationCategory(LocationCategory locationCategory) {
        locationCategories.add(locationCategory);
    }

    public static void deleteLocationCategory(LocationCategory locationCategory) {
        locationCategories.remove(locationCategory);
    }

    public static void clearLocationsCategories() {
        locationCategories = new ArrayList<>();
    }

    public static LocationCategory getLocationCategory(String categoryName) {
        for (LocationCategory c : locationCategories) {
            if (c.getName().equals(categoryName)) {
                return c;
            }
        }
        return null;
    }

    public Location(String name) {
        super(name);
        this.exits = new Location[6];
        this.exitsOpened = new boolean[6];
        this.scripts.put("_onEnter", new Script("", true));
        this.scripts.put("_onLeave", new Script("", true));
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

    public boolean equals(Location location) {
        return location != null && this.id == location.id;
    }
}
