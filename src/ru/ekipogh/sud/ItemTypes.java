package ru.ekipogh.sud;

/**
 * Created by Дмитрий on 04.05.2015.
 */
public enum ItemTypes {
    GENERIC("Общее"), EQUIPPABLE("Экипируемое"), CONSUMABLE("Употребляемое");

    private final String typeString;

    ItemTypes(String typeString) {
        this.typeString = typeString;
    }

    @Override
    public String toString() {
        return typeString;
    }
}
