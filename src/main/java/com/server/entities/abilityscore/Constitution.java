package com.server.entities.abilityscore;

import com.server.entities.monster.Monster;
import jakarta.persistence.Entity;

@Entity
public class Constitution extends AbilityScore {
    public Constitution() { }

    public Constitution(Constitution constitution) {
        super(constitution);
    }
}