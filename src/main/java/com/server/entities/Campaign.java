package com.server.entities;

import com.server.entities.encounter.Encounter;
import com.server.entities.monster.Monster;
import jakarta.persistence.*;

import java.util.List;

@Entity
public class Campaign {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String name;
    private boolean madness;
    private boolean active = false;
    private boolean inflatedHitPoints;
    @OneToMany
    @JoinColumn(name="campaignId")
    List<Monster> monsterList;
    @OneToMany
    @JoinColumn(name="campaignId")
    List<Encounter> encounterList;

    public Campaign() { }

    public Campaign(String name) {
        this.name = name;
    }

    public Integer getId() {
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

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public boolean isInflatedHitPoints() {
        return inflatedHitPoints;
    }
}
