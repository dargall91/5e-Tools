package com.server.entities.monster.abilityscore;

import jakarta.persistence.Entity;

@Entity
public class Wisdom extends AbilityScore {
    private int animalHandling;
    private int insight;
    private int medicine;
    private int perception;
    private int survival;

    public Wisdom() { }

    public Wisdom(Wisdom wisdom) {
        super(wisdom);
        animalHandling = wisdom.getAnimalHandling();
        insight = wisdom.getInsight();
        medicine = wisdom.getMedicine();
        perception = wisdom.getPerception();
        survival = wisdom.getSurvival();
    }

    public int getAnimalHandling() {
        return animalHandling;
    }

    public void setAnimalHandling(int animalHandling) {
        this.animalHandling = animalHandling;
    }

    public int getInsight() {
        return insight;
    }

    public void setInsight(int insight) {
        this.insight = insight;
    }

    public int getMedicine() {
        return medicine;
    }

    public void setMedicine(int medicine) {
        this.medicine = medicine;
    }

    public int getPerception() {
        return perception;
    }

    public void setPerception(int perception) {
        this.perception = perception;
    }

    public int getSurvival() {
        return survival;
    }

    public void setSurvival(int survival) {
        this.survival = survival;
    }
}
