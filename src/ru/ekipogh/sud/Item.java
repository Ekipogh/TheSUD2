package ru.ekipogh.sud;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Дмитрий on 04.05.2015.
 */
public class Item extends GameObject implements Serializable, Comparable, Cloneable {
    public static final long serialVersionUID = 1L;
    private ItemTypes type;
    private String equipmentSlot;
    private static List<ItemCategory> itemCategories = new ArrayList<>();
    private List<ItemCategory> categories;
    private boolean locked;
    private boolean isContainer;
    private int baseId;

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

    public Item(String name) {
        this.isContainer = false;
        this.locked = false;
        this.name = name;
        this.type = ItemTypes.GENERIC;
        this.description = "";
        this.id = Sequencer.getNewID();
        this.scripts = new HashMap<>();
        this.scripts.put("_onTake", new Script("", true));
        this.scripts.put("_onDrop", new Script("", true));
        this.scripts.put("_onEquip", new Script("", true));
        this.scripts.put("_onUse", new Script("", true));
        this.scripts.put("_onUnequip", new Script("", true));
        this.scripts.put("_onUnlock", new Script("caller.setLocked(false);", true));
        this.scripts.put("_onStash", new Script("", true));
        this.values = new HashMap<>();
        this.categories = new ArrayList<>();
    }

    public static void setItemCategories(List<ItemCategory> categories) {
        Item.itemCategories = categories;
    }

    public static List<ItemCategory> getItemCategories() {
        return Item.itemCategories;
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

    public static void addItemCategory(ItemCategory itemCategory) {
        Item.itemCategories.add(itemCategory);
    }

    public static void deleteItemCategory(ItemCategory itemCategory) {
        Item.itemCategories.remove(itemCategory);
    }

    public List<ItemCategory> getCategories() {
        return categories;
    }

    public void removeCategory(ItemCategory category) {
        this.categories.remove(category);
    }

    public static void clearItemsCategories() {
        Item.itemCategories = new ArrayList<>();
    }

    public void addCategory(ItemCategory category) {
        if (!categories.contains(category)) {
            categories.add(category);
        }
    }
    @Override
    public int compareTo(Object i) {
        if (this.id == ((Item) i).getId()) return 0;
        return this.name.compareTo(((Item) i).getName());
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) return false;
        return obj.getClass() == Item.class && this.id == ((Item) obj).id || this.baseId == ((Item) obj).id;
    }
    @Override //used to instantiate new containers
    protected Object clone() throws CloneNotSupportedException {
        Object toReturn = super.clone();
        ((Item) toReturn).baseId = id;
        ((Item) toReturn).id = Sequencer.getNewID();
        ((Item) toReturn).inventory = new Inventory();
        return toReturn;
    }

    public void setScriptEnabled(String scriptName, boolean enabled) {
        Script script;
        if ((script = getScript(scriptName)) != null) {
            script.setEnabled(enabled);
            return;
        } else {
            for (ItemCategory itemCategory : categories) {
                if ((script = itemCategory.getScript(scriptName)) != null) {
                    script.setEnabled(enabled);
                    return;
                }
            }
        }
    }
}
