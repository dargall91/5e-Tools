package com.server.entities.monster.abilityscore;

import jakarta.persistence.Entity;

@Entity
public class Constitution extends AbilityScore {
    public Constitution() { }

    public Constitution(Constitution constitution) {
        super(constitution);
    }
}
