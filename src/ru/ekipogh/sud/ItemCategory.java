package ru.ekipogh.sud;

import java.util.HashMap;

/**
 * Created by dedov_d on 03.08.2015.
 */
public class ItemCategory {
    private String name;
    private HashMap<String, String> scripts;

    public ItemCategory(String name) {
        this.name = name;
        this.scripts = new HashMap<>();
        this.scripts.put("onTake", "");
        this.scripts.put("onDrop", "");
        this.scripts.put("onEquip", "");
        this.scripts.put("onUse", "");
        this.scripts.put("onUnequip", "");
    }
}
