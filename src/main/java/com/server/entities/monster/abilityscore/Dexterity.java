package com.server.entities.monster.abilityscore;

import jakarta.persistence.Entity;

@Entity
public class Dexterity extends AbilityScore {
    private int acrobatics;
    private int sleightOfHand;
    private int stealth;

    public Dexterity() { }

    public Dexterity(Dexterity dexterity) {
        super(dexterity);
        acrobatics = dexterity.getAcrobatics();
        sleightOfHand = dexterity.getSleightOfHand();
        stealth = dexterity.getStealth();
    }

    public int getAcrobatics() {
        return acrobatics;
    }

    public void setAcrobatics(int acrobatics) {
        this.acrobatics = acrobatics;
    }

    public int getSleightOfHand() {
        return sleightOfHand;
    }

    public void setSleightOfHand(int sleightOfHand) {
        this.sleightOfHand = sleightOfHand;
    }

    public int getStealth() {
        return stealth;
    }

    public void setStealth(int stealth) {
        this.stealth = stealth;
    }
}
