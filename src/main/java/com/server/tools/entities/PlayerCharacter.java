package com.server.tools.entities;

import jakarta.persistence.*;

@Entity
public class PlayerCharacter {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(updatable = false)
    private String name;
    private int ac;
    private int initiativeBonus;
    private int rolledInitiative;
    private int campaignId;

    protected PlayerCharacter() { }

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

    public int getCampaignId() {
        return campaignId;
    }
}
