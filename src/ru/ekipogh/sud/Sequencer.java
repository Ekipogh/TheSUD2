package ru.ekipogh.sud;

/**
 * Created by ekipogh on 27.05.2015.
 licensed under WTFPL
 */
public class Sequencer {
    private static int id = 0;

    public static int getNewID() {
        return id++;
    }

    public static void setID(int id) {
        Sequencer.id = id;
    }

    public static int getCurrentId() {
        return id;
    }

    public static void reset() {
        id = 0;
    }
}
