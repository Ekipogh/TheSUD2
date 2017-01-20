package ru.ekipogh.sud;

import ru.ekipogh.sud.objects.*;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by ekipogh on 27.04.2015.
 licensed under WTFPL
 */
public class GameFile implements Serializable {
    public static final long serialVersionUID = 1L;
    private GameCharacter player;
    private List<Location> locations;
    private String gameName;
    private String gameStartMessage;
    private Map<String, String> slotNames;
    private List<Item> items;
    private List<GameCharacter> characters;
    private int sequencerID;
    private String initScript;
    private String path;
    private HashMap<String, Script> commonScripts;

    public ArrayList<SudTimer> getTimers() {
        return timers;
    }

    private ArrayList<SudTimer> timers;

    public String getInitScript() {
        return initScript;
    }

    public void setInitScript(String initScript) {
        this.initScript = initScript;
    }

    public List<LocationCategory> getLocationCategories() {
        return locationCategories;
    }

    public void setLocationCategories(List<LocationCategory> locationCategories) {
        this.locationCategories = locationCategories;
    }

    public List<ItemCategory> getItemCategories() {
        return itemCategories;
    }

    public void setItemCategories(List<ItemCategory> itemCategories) {
        this.itemCategories = itemCategories;
    }

    public List<CharacterCategory> getCharacterCategories() {
        return characterCategories;
    }

    public void setCharacterCategories(List<CharacterCategory> characterCategories) {
        this.characterCategories = characterCategories;
    }

    private List<LocationCategory> locationCategories;
    private List<ItemCategory> itemCategories;
    private List<CharacterCategory> characterCategories;

    public List<Item> getItems() {
        return items;
    }

    public void setItems(List<Item> items) {
        this.items = items;
    }

    public String getGameName() {
        return gameName;
    }

    public void setGameName(String gameName) {
        this.gameName = gameName;
    }

    public String getGameStartMessage() {
        return gameStartMessage;
    }

    public void setGameStartMessage(String gameStartMessage) {
        this.gameStartMessage = gameStartMessage;
    }

    public GameCharacter getPlayer() {
        return player;
    }

    public void setPlayer(GameCharacter player) {
        this.player = player;
    }

    public void save(String path) {
        this.path = path;
        try {
            FileOutputStream fos = new FileOutputStream(path);
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(this);
            oos.flush();
            oos.close();
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(1);
        }
    }

    public static GameFile open(String path) {
        try {
            FileInputStream fis = new FileInputStream(path);
            ObjectInputStream oin = new ObjectInputStream(fis);
            return (GameFile) oin.readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<Location> getLocations() {
        return locations;
    }

    public void setLocations(List<Location> locations) {
        this.locations = locations;
    }

    public void setSlotNames(Map<String, String> slotNames) {
        this.slotNames = slotNames;
    }

    public Map<String, String> getSlotNames() {
        return slotNames;
    }

    public int getSequencerID() {
        return sequencerID;
    }

    public void setSequencerID(int sequencerID) {
        this.sequencerID = sequencerID;
    }

    public List<GameCharacter> getCharacters() {
        return characters;
    }

    public void setCharacters(List<GameCharacter> characters) {
        this.characters = characters;
    }

    public String getPath() {
        return path;
    }

    public void setCommonScripts(HashMap<String, Script> commonScripts) {
        this.commonScripts = commonScripts;
    }

    public HashMap<String, Script> getCommonScripts() {
        return commonScripts;
    }

    public void setTimers(ArrayList<SudTimer> timers) {
        this.timers = timers;
    }
}
