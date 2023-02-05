package com.server.entities.encounter;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class XpThresholds {
    @Id
    private int level;
    private int easy;
    private int medium;
    private int hard;
    private int deadly;

    public int getLevel() {
        return level;
    }

    public int getEasy() {
        return easy;
    }

    public int getMedium() {
        return medium;
    }

    public int getHard() {
        return hard;
    }

    public int getDeadly() {
        return deadly;
    }
}
