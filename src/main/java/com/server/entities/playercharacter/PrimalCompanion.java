package com.server.entities.playercharacter;

import jakarta.persistence.*;

@Entity
public class PrimalCompanion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String name = "Primal Companion";
    private int damage;
    private int temporaryHitPoints;
    private int acBonus;
    private int hitDiceUsed;
    private int deathSaveSuccesses;
    private int deathSaveFailures;
    private int maxHpReduction;
    @OneToOne
    @JoinColumn(name = "primalCompanionTypeId")
    private PrimalCompanionType primalCompanionType;

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getDamage() {
        return damage;
    }

    public int getTemporaryHitPoints() {
        return temporaryHitPoints;
    }

    public int getAcBonus() {
        return acBonus;
    }

    public int getHitDiceUsed() {
        return hitDiceUsed;
    }

    public int getDeathSaveSuccesses() {
        return deathSaveSuccesses;
    }

    public int getDeathSaveFailures() {
        return deathSaveFailures;
    }

    public int getMaxHpReduction() {
        return maxHpReduction;
    }

    public PrimalCompanionType getPrimalCompanionType() {
        return primalCompanionType;
    }

    public void setPrimalCompanionType(PrimalCompanionType primalCompanionType) {
        this.primalCompanionType = primalCompanionType;
    }

    public int getBaseArmorClass() {
        return 13;
    }

    public String getSenses() {
        return "Darkvision 60 ft., passive perception <perception>";
    }

    public String getLanguages() {
        return "Understands the languages you speak";
    }

    public String getAlignment() {
        return "Neutral";
    }

    public String getType() {
        return "Beast";
    }
}
