package com.server.entities.playercharacter;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class CharacterClass {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String name;
    private int hitDie;
    private int averageHitDie;
    private boolean fullCaster;
    private boolean halfCaster;
    private boolean artificer;
    private boolean warlock;

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getHitDie() {
        return hitDie;
    }

    public int getAverageHitDie() {
        return averageHitDie;
    }

    public boolean isFullCaster() {
        return fullCaster;
    }

    public boolean isHalfCaster() {
        return halfCaster;
    }

    public boolean isArtificer() {
        return artificer;
    }

    public boolean isWarlock() {
        return warlock;
    }
}
