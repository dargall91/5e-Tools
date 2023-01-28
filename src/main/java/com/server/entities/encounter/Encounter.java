package com.server.entities.encounter;

import com.server.entities.Campaign;
import jakarta.persistence.*;

import java.util.List;

@Entity
public class Encounter {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @OneToOne
    @JoinColumn(name="id")
    private Campaign campaign;
    private String name;
    @OneToOne
    @JoinColumn(name="id")
    private Music music;
    private boolean lairAction;
    private boolean archived;
    @OneToMany
    @JoinColumn(name="id")
    private List<EncounterMonster> monsters;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
