package com.server.entities.monster.abilityscore;

import jakarta.persistence.Entity;

@Entity
public class Intelligence extends AbilityScore {
    private int arcana;
    private int history;
    private int investigation;
    private int nature;
    private int religion;

    public Intelligence() { }

    public Intelligence(Intelligence intelligence) {
        super(intelligence);
        arcana = intelligence.getArcana();
        history = intelligence.getHistory();
        investigation = intelligence.getInvestigation();
        nature = intelligence.getNature();
        religion = intelligence.getReligion();
    }

    public int getArcana() {
        return arcana;
    }

    public void setArcana(int arcana) {
        this.arcana = arcana;
    }

    public int getHistory() {
        return history;
    }

    public void setHistory(int history) {
        this.history = history;
    }

    public int getInvestigation() {
        return investigation;
    }

    public void setInvestigation(int investigation) {
        this.investigation = investigation;
    }

    public int getNature() {
        return nature;
    }

    public void setNature(int nature) {
        this.nature = nature;
    }

    public int getReligion() {
        return religion;
    }

    public void setReligion(int religion) {
        this.religion = religion;
    }
}
