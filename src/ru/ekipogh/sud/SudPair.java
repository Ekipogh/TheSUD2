package ru.ekipogh.sud;

import java.io.Serializable;

/**
 * Created by ekipogh on 22.12.2015.
 */
public class SudPair<L, R> implements Serializable {
    private L key;
    private R value;

    public void setValue(R value) {
        this.value = value;
    }

    public void setKey(L key) {
        this.key = key;
    }

    public SudPair(L left, R right) {
        this.key = left;
        this.value = right;
    }

    public L getKey() {
        return key;
    }

    public R getValue() {
        return value;
    }

    @Override
    public int hashCode() {
        return key.hashCode() ^ value.hashCode();
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof SudPair)) return false;
        SudPair pairo = (SudPair) o;
        return this.key.equals(pairo.getKey()) &&
                this.value.equals(pairo.getValue());
    }

    @Override
    public String toString() {
        return key + "(" + value + ")";
    }
}
