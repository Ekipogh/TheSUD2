package ru.ekipogh.sud;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by ekipo on 09.05.2016.
 */
public class GameObject implements Serializable {
    public static final long serialVersionUID = 1L;
    protected String name;
    protected Inventory inventory;
    protected Map<String, Object> values;
    protected int id;
    protected Map<String, Script> scripts;
    protected String description;
    protected HashMap<String, Boolean> scriptsEnabled;

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

    public String toString() {
        return this.name;
    }
}
