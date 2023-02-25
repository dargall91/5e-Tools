package com.server.entities.playercharacter.abilityscore;

import jakarta.persistence.Entity;

@Entity
public class Resolve extends PCAbilityScore {
    /**
     * Players cannot have proficiency in Resolve
     * @param proficient
     */
    @Override
    public void setProficient(boolean proficient) {
        super.setProficient(false);
    }
}
