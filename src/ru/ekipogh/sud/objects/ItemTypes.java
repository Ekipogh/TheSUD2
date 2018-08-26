package ru.ekipogh.sud.objects;

/**
 * Created by ekipogh on 04.05.2015.
 */
public enum ItemTypes {
    GENERIC("Общее"), EQUIPPABLE("Экипируемое"), CONSUMABLE("Употребляемое"), STORABLE("Хранимое");

    private final String typeString;

    ItemTypes(String typeString) {
        this.typeString = typeString;
    }

    @Override
    public String toString() {
        return typeString;
    }
}
