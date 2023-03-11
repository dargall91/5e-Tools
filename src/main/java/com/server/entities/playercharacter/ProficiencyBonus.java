package com.server.entities.playercharacter;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class ProficiencyBonus {
    @Id
    private int level;
    private int bonus;

    public int getLevel() {
        return level;
    }

    public int getBonus() {
        return bonus;
    }
}
