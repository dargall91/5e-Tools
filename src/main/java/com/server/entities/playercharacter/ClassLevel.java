package com.server.entities.playercharacter;

import jakarta.persistence.*;

@Entity
public class ClassLevel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private boolean firstLevel;
    private int levels;
    @OneToOne
    @JoinColumn(name = "classId")
    private CharacterClass characterClass;

    public int getId() {
        return id;
    }

    public boolean isFirstLevel() {
        return firstLevel;
    }

    public int getLevels() {
        return levels;
    }

    public CharacterClass getCharacterClass() {
        return characterClass;
    }
}
