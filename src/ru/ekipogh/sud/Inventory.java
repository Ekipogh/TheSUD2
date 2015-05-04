package ru.ekipogh.sud;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Дмитрий on 04.05.2015.
 */
public class Inventory implements Serializable {
    List<Item> items;

    public Inventory() {
        items = new ArrayList<Item>();
    }
}
