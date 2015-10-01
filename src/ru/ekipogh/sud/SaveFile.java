package ru.ekipogh.sud;

import org.mozilla.javascript.Context;
import org.mozilla.javascript.NativeJavaObject;
import org.mozilla.javascript.Scriptable;

import java.io.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by dedov_d on 23.09.2015.
 */
public class SaveFile implements Serializable { //Version .1
    private List<Location> locations;
    private GameCharacter player;
    private List<GameCharacter> characters;
    private List<Item> items;
    private static Scriptable scope;
    private Map<String, Object> scopeObjects;

    public List<Location> getLocations() {
        return locations;
    }

    public void setLocations(List<Location> locations) {
        this.locations = locations;
    }

    public GameCharacter getPlayer() {
        return player;
    }

    public void setPlayer(GameCharacter player) {
        this.player = player;
    }

    public List<GameCharacter> getCharacters() {
        return characters;
    }

    public void setCharacters(List<GameCharacter> characters) {
        this.characters = characters;
    }

    public List<Item> getItems() {
        return items;
    }

    public void setItems(List<Item> items) {
        this.items = items;
    }

    public static void setScope(Scriptable scope) {
        SaveFile.scope = scope;
    }

    public static SaveFile open(String savePath) {
        try {
            FileInputStream fis = new FileInputStream(savePath);
            ObjectInputStream oin = new ObjectInputStream(fis);
            SaveFile result = (SaveFile) oin.readObject();
            return result;
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void save(String savePath) {
        scopeObjects = new HashMap<>();
        Context cx = Context.enter();
        Scriptable cleanScope = cx.initSafeStandardObjects();
        try {
            for (Object o : scope.getIds()) {
                boolean isFunction = (boolean) Script.run("isFunctionTest(\"" + o.toString() + "\")", null);
                if (!isFunction) {
                    Object so = scope.get(o.toString(), scope);
                    if (so != null) {
                        if (so instanceof NativeJavaObject)
                            ((NativeJavaObject) so).setParentScope(cleanScope);
                        scopeObjects.put(o.toString(), so);
                    }
                }
            }
            FileOutputStream fos = new FileOutputStream(savePath);
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(this);
            oos.flush();
            oos.close();
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(1);
        }
    }

    public Map getScopeObjects() {
        return scopeObjects;
    }
}
