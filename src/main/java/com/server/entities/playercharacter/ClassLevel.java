package com.server.entities.playercharacter;

import jakarta.persistence.*;

@Entity
public class ClassLevel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private boolean baseClass;
    private int levels;
    private int usedHitDice = 0;
    private boolean eldritchKnight;
    private boolean arcaneTrickster;
    private boolean beastMaster;
    @OneToOne
    @JoinColumn(name = "classId")
    private CharacterClass characterClass;

    public int getId() {
        return id;
    }

    public boolean isBaseClass() {
        return baseClass;
    }

    public int getLevels() {
        return levels;
    }

    public int getUsedHitDice() {
        return usedHitDice;
    }

    public CharacterClass getCharacterClass() {
        return characterClass;
    }

    public boolean isEldritchKnight() {
        return eldritchKnight;
    }

    public boolean isArcaneTrickster() {
        return arcaneTrickster;
    }

    public boolean isBeastMaster() {
        return beastMaster;
    }
}
