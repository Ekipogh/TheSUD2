package ru.ekipogh.sud;

/**
 * Created by dedov_d on 22.12.2015.
 */
public class SudPair<L, R> {
    private L key;

    public void setValue(R value) {
        this.value = value;
    }

    public void setKey(L key) {
        this.key = key;
    }

    private R value;

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
}
