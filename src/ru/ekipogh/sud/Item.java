package ru.ekipogh.sud;

/**
 * Created by Дмитрий on 04.05.2015.
 */
public class Item {
    private ItemTypes type;
    private String name;
    private String description;

    public Item(String name) {
        this.name = name;
        this.type = ItemTypes.GENERIC;
    }

    public ItemTypes getType() {
        return type;
    }

    public void setType(ItemTypes type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return name;
    }
}
