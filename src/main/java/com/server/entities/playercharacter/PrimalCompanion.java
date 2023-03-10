package com.server.entities.playercharacter;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;

@Entity
public class PrimalCompanion {
    @Id
    private int id;
    private String name;
    private int damage;
    private int temporaryHitPoints;
    private int hitDiceUsed;
    @OneToOne
    @JoinColumn(name = "id")
    private PrimalCompanionType primalCompanionType;
}
