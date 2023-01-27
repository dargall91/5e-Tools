package com.server.tools.entities.abilityscore;

import jakarta.persistence.Entity;

@Entity
public class Strength extends AbilityScore {
    private int athletics;

    public int getAthletics() {
        return athletics;
    }

    public void setAthletics(int athletics) {
        this.athletics = athletics;
    }
}
