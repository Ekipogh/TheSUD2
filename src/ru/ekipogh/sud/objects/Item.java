package ru.ekipogh.sud.objects;

import ru.ekipogh.sud.Script;
import ru.ekipogh.sud.Sequencer;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by ekipogh on 04.05.2015.
 licensed under WTFPL
 */
public class Item extends GameObject implements Serializable, Comparable, Cloneable {
    public static final long serialVersionUID = 1L;
    private ItemTypes type;
    private String equipmentSlot;
    private static List<ItemCategory> itemCategories = new ArrayList<>();
    private boolean locked;
    private int baseId;

    private boolean isContainer;

    public static void setItemCategories(List<ItemCategory> categories) {
        Item.itemCategories = categories;
    }

    public static List<ItemCategory> getItemCategories() {
        return Item.itemCategories;
    }

    public static void addItemCategory(ItemCategory itemCategory) {
        Item.itemCategories.add(itemCategory);
    }

    public static void deleteItemCategory(ItemCategory itemCategory) {
        Item.itemCategories.remove(itemCategory);
    }

    public static void clearItemsCategories() {
        Item.itemCategories = new ArrayList<>();
    }

    public Item(String name) {
        super(name);
        this.isContainer = false;
        this.locked = false;
        this.type = ItemTypes.GENERIC;
        this.scripts.put("_onTake", new Script("", true));
        this.scripts.put("_onDrop", new Script("", true));
        this.scripts.put("_onEquip", new Script("", true));
        this.scripts.put("_onUse", new Script("", true));
        this.scripts.put("_onUnequip", new Script("", true));
        this.scripts.put("_onUnlock", new Script("caller.setLocked(false);", true));
        this.scripts.put("_onStash", new Script("", true));
    }

    public boolean isLocked() {
        return locked;
    }

    public void setLocked(boolean locked) {
        this.locked = locked;
    }

    public boolean isContainer() {
        return isContainer;
    }

    public void setContainer(boolean container) {
        isContainer = container;
    }

    public ItemTypes getType() {
        return type;
    }

    public void setType(ItemTypes type) {
        this.type = type;
    }

    public String getEquipmentSlot() {
        return equipmentSlot;
    }

    public void setEquipmentSlot(String equipmentSlot) {
        Equipment.getSlotNames().stream().filter(s -> s.equals(equipmentSlot)).forEach(s -> this.equipmentSlot = equipmentSlot);
    }

    @Override
    public int compareTo(Object i) {
        if (this.id == ((Item) i).getId()) return 0;
        return this.name.compareTo(((Item) i).getName());
    }

    @Override
    public boolean equals(Object obj) {
        return obj != null && (obj.getClass() == Item.class && this.id == ((Item) obj).id || this.baseId == ((Item) obj).id);
    }

    @Override //used to instantiate new containers
    public Object clone() throws CloneNotSupportedException {
        Object toReturn = super.clone();
        ((Item) toReturn).baseId = id;
        ((Item) toReturn).id = Sequencer.getNewID();
        ((Item) toReturn).inventory = new Inventory();
        return toReturn;
    }
}
