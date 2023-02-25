package com.server.entities.playercharacter.abilityscore;

import jakarta.persistence.Entity;

@Entity
public class PCStrength extends PCAbilityScore {
    private int athletics;

    public PCStrength() { }

    public int getAthletics() {
        return athletics;
    }

    public void setAthletics(int athletics) {
        this.athletics = athletics;
    }
}
