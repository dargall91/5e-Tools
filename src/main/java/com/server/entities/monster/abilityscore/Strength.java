package com.server.entities.monster.abilityscore;

import jakarta.persistence.Entity;

@Entity
public class Strength extends AbilityScore {
    private int athletics;

    public Strength() { }

    public Strength(Strength strength) {
        super(strength);
        athletics = strength.getAthletics();
    }

    public int getAthletics() {
        return athletics;
    }

    public void setAthletics(int athletics) {
        this.athletics = athletics;
    }
}
