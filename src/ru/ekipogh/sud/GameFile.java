package ru.ekipogh.sud;

import ru.ekipogh.sud.objects.*;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by ekipogh on 27.04.2015.
 */
public class GameFile implements Serializable {
    public static final long serialVersionUID = 1L;
    private String gameName;
    private String gameStartMessage;
    private GameCharacter player;
    private List<GameCharacter> characters;
    private List<Location> locations;
    private List<Item> items;
    private Map<String, String> slotNames;
    private int sequencerID;
    private String initScript;
    private String path;
    private HashMap<String, Script> commonScripts;
    private ArrayList<SudTimer> timers;
    private List<CharacterCategory> characterCategories;
    private List<LocationCategory> locationCategories;
    private List<ItemCategory> itemCategories;

    public GameFile() {
        this.player = new GameCharacter("Player");
        this.locations = new ArrayList<>();
        this.items = new ArrayList<>();
        this.characters = new ArrayList<>();
        this.slotNames = new HashMap<>();
        this.sequencerID = 0;
        this.commonScripts = new HashMap<>();
        this.timers = new ArrayList<>();
        this.characterCategories = new ArrayList<>();
        this.locationCategories = new ArrayList<>();
        this.itemCategories = new ArrayList<>();
        this.path = "";
    }

    public void setInitScript(String initScript) {
        this.initScript = initScript;
    }

    public String getInitScript() {
        return initScript;
    }

    public void setLocationCategories(List<LocationCategory> locationCategories) {
        this.locationCategories = locationCategories;
    }

    public List<LocationCategory> getLocationCategories() {
        return locationCategories;
    }

    public void setItemCategories(List<ItemCategory> itemCategories) {
        this.itemCategories = itemCategories;
    }

    public List<ItemCategory> getItemCategories() {
        return itemCategories;
    }

    public void setCharacterCategories(List<CharacterCategory> characterCategories) {
        this.characterCategories = characterCategories;
    }

    public List<CharacterCategory> getCharacterCategories() {
        return characterCategories;
    }

    public void setItems(List<Item> items) {
        this.items = items;
    }

    public List<Item> getItems() {
        return items;
    }

    public void setGameName(String gameName) {
        this.gameName = gameName;
    }

    public String getGameName() {
        return gameName;
    }

    public void setGameStartMessage(String gameStartMessage) {
        this.gameStartMessage = gameStartMessage;
    }

    public String getGameStartMessage() {
        return gameStartMessage;
    }

    public void setPlayer(GameCharacter player) {
        this.player = player;
    }

    public GameCharacter getPlayer() {
        return player;
    }

    public void setLocations(List<Location> locations) {
        this.locations = locations;
    }

    public List<Location> getLocations() {
        return locations;
    }

    public void save() {
        if (path.isEmpty()) {
            this.path = Utils.chooseFile();
        }
        try {
            File file = new File(path);
            if (file.canWrite()) {
                System.out.println("Saving to " + path);
                FileOutputStream fos = new FileOutputStream(path);
                ObjectOutputStream oos = new ObjectOutputStream(fos);
                oos.writeObject(this);
                oos.flush();
                oos.close();
                System.out.println("Saved");
            }
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(1);
        }
    }

    public static GameFile open() {
        String path = Utils.chooseFile();
        try {
            File file = new File(path);
            if (file.isFile()) {
                FileInputStream fis = new FileInputStream(path);
                ObjectInputStream oin = new ObjectInputStream(fis);
                GameFile gameFile = (GameFile) oin.readObject();
                gameFile.setPath(path);
                return gameFile;
            } else {
                System.err.println("File not found \"" + path + "\"");
            }
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void setSlotNames(Map<String, String> slotNames) {
        this.slotNames = slotNames;
    }

    public Map<String, String> getSlotNames() {
        return slotNames;
    }

    public void setSequencerID(int sequencerID) {
        this.sequencerID = sequencerID;
    }

    public int getSequencerID() {
        return sequencerID;
    }

    public void setCharacters(List<GameCharacter> characters) {
        this.characters = characters;
    }

    public List<GameCharacter> getCharacters() {
        return characters;
    }

    private void setPath(String path) {
        this.path = path;
    }

    public String getPath() {
        return path;
    }

    public void setCommonScripts(HashMap<String, Script> commonScripts) {
        this.commonScripts = commonScripts;
    }

    public void setCommonScript(String scriptName, String scriptText) {
        this.commonScripts.put(scriptName, new Script(scriptText, true));
    }

    public HashMap<String, Script> getCommonScripts() {
        return commonScripts;
    }

    public void setTimers(ArrayList<SudTimer> timers) {
        this.timers = timers;
    }

    public ArrayList<SudTimer> getTimers() {
        return timers;
    }
}
