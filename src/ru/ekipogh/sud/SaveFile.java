package ru.ekipogh.sud;

import java.io.*;
import java.util.List;
import java.util.Map;

/**
 * Created by dedov_d on 27.04.2015.
 */
public class SaveFile implements Serializable {
    private GameCharacter player;
    private List<Location> locations;
    private String gameName;
    private String gameStartMessage;
    private Map<String, String> slotNames;
    private List<Item> items;
    private List<GameCharacter> characters;
    private int sequencerID;

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

    public static SaveFile open(String path) {
        try {
            FileInputStream fis = new FileInputStream(path);
            ObjectInputStream oin = new ObjectInputStream(fis);
            return (SaveFile) oin.readObject();
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
}
