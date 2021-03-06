package ru.ekipogh.sud.objects;

import ru.ekipogh.sud.Script;

import java.io.Serializable;

/**
 * Created by ekipogh on 03.08.2015.
 */
public class LocationCategory extends GameObjectCategory implements Serializable {
    public static final long serialVersionUID = 1L;
    public LocationCategory(String name) {
        super(name);
        this.scripts.put("_onEnter", new Script("", true));
        this.scripts.put("_onLeave", new Script("", true));
    }

    @Override
    public String toString() {
        return "LC: " + this.name;
    }
}