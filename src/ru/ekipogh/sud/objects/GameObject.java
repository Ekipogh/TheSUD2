package ru.ekipogh.sud.objects;

import ru.ekipogh.sud.Script;
import ru.ekipogh.sud.Sequencer;

import java.io.Serializable;
import java.util.*;

/**
 * Created by ekipogh on 09.05.2016.
 * licensed under WTFPL
 */
public class GameObject implements Serializable {
    public static final long serialVersionUID = 1L;
    protected String name;
    Inventory inventory;
    private Map<String, Object> values;
    protected int id;
    Map<String, Script> scripts;
    private String description;
    private HashMap<String, Boolean> scriptsEnabled;
    private List<GameObjectCategory> categories;

    public GameObject() {
        this.name = "";
        this.description = "";
        this.inventory = new Inventory();
        this.scripts = new HashMap<>();
        this.values = new HashMap<>();
        this.scriptsEnabled = new HashMap<>();
        this.id = Sequencer.getNewID();
        this.categories = new ArrayList<>();
    }

    public GameObject(String name) {
        this.name = name;
        this.description = "";
        this.inventory = new Inventory();
        this.scripts = new HashMap<>();
        this.values = new HashMap<>();
        this.scriptsEnabled = new HashMap<>();
        this.id = Sequencer.getNewID();
        this.categories = new ArrayList<>();
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public Inventory getInventory() {
        return inventory;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;

    }

    public void addItem(Item item) {
        addItem(item, 1);
    }

    public void addItem(Item item, int count) {
        inventory.add(item, count);
    }

    public void removeItem(Item item, int count) {
        inventory.remove(item, count);
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

    public int getId() {
        return id;
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

    public List<GameObjectCategory> getCategories() {
        return categories;
    }

    public void removeCategory(GameObjectCategory category) {
        this.categories.remove(category);
    }

    public void setCategories(List<GameObjectCategory> categories) {
        this.categories = categories;
    }

    public GameObjectCategory getCategory(String categoryName) {
        for (GameObjectCategory category : categories) {
            if (category.getName().equals(categoryName)) {
                return category;
            }
        }
        return null;
    }

    public void addCategory(GameObjectCategory category) {
        if (!categories.contains(category))
            categories.add(category);
    }

    public boolean isScriptEnabled(String scriptName) {
        for (Map.Entry<String, Script> entry : scripts.entrySet()) {
            if (entry.getKey().equals(scriptName)) {
                return entry.getValue().isEnabled();
            }
        }
        for (GameObjectCategory category : categories) {
            for (Map.Entry<String, Script> entry : category.scripts.entrySet()) {
                if (entry.getKey().equals(scriptName)) {
                    Boolean enabled = scriptsEnabled.get(scriptName);
                    if (enabled == null) {
                        scriptsEnabled.put(scriptName, category.getScript(scriptName).isEnabled());
                    }
                    return scriptsEnabled.get(scriptName);
                }
            }
        }
        System.err.println("Did not found that scrept " + scriptName);
        return false;
    }


    public void setScriptEnabled(String scriptName, boolean enabled) {
        for (Map.Entry<String, Script> entry : scripts.entrySet()) {
            if (entry.getKey().equals(scriptName)) {
                entry.getValue().setEnabled(enabled);
                return;
            }
        }
        for (GameObjectCategory category : categories) {
            for (Map.Entry<String, Script> entry : category.scripts.entrySet()) {
                if (entry.getKey().equals(scriptName)) {
                    this.scriptsEnabled.put(scriptName, enabled);
                    return;
                }
            }
        }
        System.out.println("Не нашел такого " + scriptName);
    }

    public void setAllScriptsEnabled(String categoryName, boolean enabled) {
        GameObjectCategory category = null;
        for (GameObjectCategory c : categories) {
            if (c.getName().equals(categoryName)) {
                category = c;
            }
        }
        for (String scriptName : category.getScripts().keySet()) {
            this.setScriptEnabled(scriptName, enabled);
        }
    }

    public void removeCategoryByName(String categoryName) {
        GameObjectCategory category = this.getCategory(categoryName);
        if(category!=null){
            categories.remove(category);
        }
    }

    public String toString() {
        return this.name;
    }
}
