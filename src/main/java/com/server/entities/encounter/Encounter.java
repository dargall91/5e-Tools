package com.server.entities.encounter;

import jakarta.persistence.*;

import java.util.List;

@Entity
public class Encounter {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private int campaignId;
    private String name;
    @OneToOne
    @JoinColumn(name="id")
    private Music music;
    private boolean lairAction;
    private boolean archived;
    @OneToMany
    @JoinColumn(name="id")
    private List<EncounterMonster> monsterList;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
