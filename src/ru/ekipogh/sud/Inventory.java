package ru.ekipogh.sud;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

/**
 * Created by dedov_d on 22.12.2015.
 */
public class Inventory {
    private List<SudPair<Item, Integer>> items;

    public Inventory() {
        this.items = new ArrayList<>();
    }

    public void remove(Item item, int amount) {
        items.stream().filter(entry -> entry.getKey().equals(item)).forEach(entry -> {
            int newAmount = entry.getValue() - amount;
            if (newAmount == 0) {
                items.remove(entry);
            } else {
                entry.setValue(newAmount);
            }
        });
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
        return items.contains(item);
    }
}
