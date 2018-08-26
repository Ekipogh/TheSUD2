package ru.ekipogh.sud.objects;

import ru.ekipogh.sud.Script;

import java.io.Serializable;

/**
 * Created by ekipogh on 03.08.2015.
 */
public class CharacterCategory extends GameObjectCategory implements Serializable {
    public static final long serialVersionUID = 1L;

    public CharacterCategory(String name) {
        super(name);
        this.scripts.put("_onPlayerArrive", new Script("", true));
        this.scripts.put("_onPlayerLeave", new Script("", true));
    }

    @Override
    public String toString() {
        return "CC:" + this.name;
    }
}
