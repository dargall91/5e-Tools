package com.server.entities.monster.abilityscore;

import jakarta.persistence.Entity;

@Entity
public class Charisma extends AbilityScore {
    private int deception;
    private int intimidation;
    private int performance;
    private int persuasion;

    public Charisma() { }

    public Charisma(Charisma charisma) {
        super(charisma);
        deception = charisma.getDeception();
        intimidation = charisma.getIntimidation();
        performance = charisma.getPerformance();
        persuasion = charisma.getPersuasion();
    }

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
