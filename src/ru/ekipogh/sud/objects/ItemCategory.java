package ru.ekipogh.sud.objects;

import ru.ekipogh.sud.Script;

import java.io.Serializable;

/**
 * Created by ekipogh on 03.08.2015.
 * licensed under WTFPL
 */
public class ItemCategory extends GameObjectCategory implements Serializable {
    public static final long serialVersionUID = 1L;
    public ItemCategory(String name) {
        super(name);
        this.scripts.put("_onTake", new Script("", true));
        this.scripts.put("_onDrop", new Script("", true));
        this.scripts.put("_onEquip", new Script("", true));
        this.scripts.put("_onUse", new Script("", true));
        this.scripts.put("_onUnequip", new Script("", true));
    }

    @Override
    public String toString() {
        return "IC: " + this.name;
    }
}
