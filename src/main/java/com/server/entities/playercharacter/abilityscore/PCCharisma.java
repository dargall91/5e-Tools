package com.server.entities.playercharacter.abilityscore;

import jakarta.persistence.Entity;

@Entity
public class PCCharisma extends PCAbilityScore {
    private int deception;
    private int intimidation;
    private int performance;
    private int persuasion;

    public PCCharisma() { }

    public int getDeception() {
        return deception;
    }

    public void setDeception(int deception) {
        this.deception = deception;
    }

    public int getIntimidation() {
        return intimidation;
    }

    public void setIntimidation(int intimidation) {
        this.intimidation = intimidation;
    }

    public int getPerformance() {
        return performance;
    }

    public void setPerformance(int performance) {
        this.performance = performance;
    }

    public int getPersuasion() {
        return persuasion;
    }

    public void setPersuasion(int persuasion) {
        this.persuasion = persuasion;
    }
}
