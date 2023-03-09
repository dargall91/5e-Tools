package com.server.entities.playercharacter;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class WarlockSpellSlots {
    @Id
    private int warlockLevel;
    private int quantity;
    private int slotLevel;

    public int getWarlockLevel() {
        return warlockLevel;
    }

    public int getQuantity() {
        return quantity;
    }

    public int getSlotLevel() {
        return slotLevel;
    }
}
