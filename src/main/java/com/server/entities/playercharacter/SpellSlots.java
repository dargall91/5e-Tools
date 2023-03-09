package com.server.entities.playercharacter;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class SpellSlots {
    @Id
    private int casterLevel;
    private int first;
    private int second;
    private int third;
    private int fourth;
    private int fifth;
    private int sixth;
    private int seventh;
    private int eighth;
    private int ninth;

    public int getCasterLevel() {
        return casterLevel;
    }

    public int getFirst() {
        return first;
    }

    public int getSecond() {
        return second;
    }

    public int getThird() {
        return third;
    }

    public int getFourth() {
        return fourth;
    }

    public int getFifth() {
        return fifth;
    }

    public int getSixth() {
        return sixth;
    }

    public int getSeventh() {
        return seventh;
    }

    public int getEighth() {
        return eighth;
    }

    public int getNinth() {
        return ninth;
    }
}
