package ru.ekipogh.sud;

/**
 * Created by dedov_d on 24.04.2015.
 */
public class Player {
    private String name;
    private short sex;
    private Location location;

    public Player(String name) {
        this.name = name;
        this.sex = 0;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public Location getLocation() {
        return location;
    }

    public void setSex(short sex) {
        if (sex >= 0 && sex <= 2)
            this.sex = sex;
    }

    public void setSex(int sex) {
        setSex((short) (sex));
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        if (name.length() < 21)
            this.name = name;

    }

    public short getSex() {
        return sex;
    }
}
