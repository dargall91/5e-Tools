package com.server.entities;

import jakarta.persistence.*;

@Entity
public class PlayerCharacter {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String name;
    private int ac;
    private int initiativeBonus;
    private int rolledInitiative;
    private int campaignId;
    private boolean dead;
    private boolean combatant = false;

    public PlayerCharacter() { }

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getAc() {
        return ac;
    }

    public int getInitiativeBonus() {
        return initiativeBonus;
    }

    public int getRolledInitiative() {
        return rolledInitiative;
    }

    public void setDead(boolean dead) {
        this.dead = dead;
    }

    public boolean isDead() {
        return dead;
    }

    public boolean isCombatant() {
        return combatant;
    }

    public void setCombatant(boolean combatant) {
        this.combatant = combatant;
    }

    public int getCampaignId() {
        return campaignId;
    }
}
