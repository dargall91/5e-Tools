package com.server.entities;

import com.server.entities.encounter.Encounter;
import com.server.entities.monster.Monster;
import jakarta.persistence.*;

import java.util.List;

@Entity
public class Campaign {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String name;
    private boolean madness;
    @OneToMany
    @JoinColumn(name="campaignId")
    List<Monster> monsterList;
    @OneToMany
    @JoinColumn(name="campaignId")
    List<Encounter> encounterList;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isMadness() {
        return madness;
    }

    public void setMadness(boolean madness) {
        this.madness = madness;
    }
}
