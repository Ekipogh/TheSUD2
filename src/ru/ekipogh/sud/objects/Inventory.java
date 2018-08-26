package ru.ekipogh.sud.objects;

import ru.ekipogh.sud.SudPair;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.function.Consumer;
import java.util.stream.Stream;

/**
 * Created by ekipogh on 22.12.2015.
 * licensed under WTFPL
 */
public class Inventory implements Iterable, Serializable {
    public static final long serialVersionUID = 1L;
    private List<SudPair<Item, Integer>> items;

    public Inventory() {
        this.items = new ArrayList<>();
    }

    public void remove(Item item, int amount) {
        SudPair<Item, Integer> pair = null;
        for (SudPair<Item, Integer> p : items) {
            if (p.getKey().equals(item)) {
                pair = p;
                break;
            }
        }
        if (pair != null) {
            int newAmount = pair.getValue() - amount;
            if (newAmount <= 0) {
                items.remove(pair);
            } else {
                pair.setValue(newAmount);
            }
        }
    }

    public void add(Item item, int amount) {
        for (SudPair<Item, Integer> entry : items) {
            if (entry.getKey().equals(item)) {
                int newAmount = entry.getValue() + amount;
                entry.setValue(newAmount);
                return;
            }
        }
        items.add(new SudPair<>(item, amount));
    }

    public int size() {
        return items.size();
    }

    public Item get(int i) {
        return items.get(i).getKey();
    }

    public int amount(int i) {
        return items.get(i).getValue();
    }

    public void add(Item item) {
        add(item, 1);
    }

    public void remove(Item item) {
        remove(item, 1);
    }

    public Stream<SudPair<Item, Integer>> stream() {
        return items.stream();
    }

    public boolean contains(Item item) {
        for (SudPair<Item, Integer> pair : items) {
            if (pair.getKey().equals(item)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public Iterator iterator() {
        return items.iterator();
    }

    @Override
    public void forEach(Consumer action) {
        items.forEach(action);
    }

    public int getAmount(Item item) {
        for (SudPair<Item, Integer> pair : items) {
            if (pair.getKey().equals(item)) {
                return pair.getValue();
            }
        }
        return 0;
    }

    public void setAmount(Item item, int amount) {
        for (SudPair<Item, Integer> pair : items) {
            if (pair.getKey().equals(item)) {
                pair.setValue(amount);
                return;
            }
        }
    }

    public SudPair<Item, Integer> getPair(Item item) {
        for (SudPair<Item, Integer> pair : items) {
            if (pair.getKey().equals(item)) {
                return pair;
            }
        }
        return null;
    }

    public boolean hasItem(Item item) {
        for (SudPair pair : items) {
            if (pair.getKey().equals(item)) {
                return true;
            }
        }
        return false;
    }

    public List<SudPair<Item, Integer>> getItems() {
        return items;
    }

    public void clear() {
        items.clear();
    }
}
