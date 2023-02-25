package com.server.entities;

import com.server.entities.abilityscore.*;
import jakarta.persistence.*;

@Entity
public class PlayerCharacter {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String name;
    private int ac;
    private int initiativeBonus;
    private int rolledInitiative;
    private int campaignId;
    private boolean dead;
    private boolean combatant = false;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name="id")
    private Strength strength = new Strength();
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name="id")
    private Dexterity dexterity = new Dexterity();
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name="id")
    private Constitution constitution = new Constitution();
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name="id")
    private Intelligence intelligence = new Intelligence();
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name="id")
    private Wisdom wisdom = new Wisdom();
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name="id")
    private Charisma charisma = new Charisma();
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "id")
    private Resolve resolve;
    @OneToOne
    @JoinColumn(name = "userId")
    private User user;

    public PlayerCharacter() { }

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getAc() {
        return ac;
    }

    public int getInitiativeBonus() {
        return initiativeBonus;
    }

    public int getRolledInitiative() {
        return rolledInitiative;
    }

    public void setDead(boolean dead) {
        this.dead = dead;
    }

    public boolean isDead() {
        return dead;
    }

    public boolean isCombatant() {
        return combatant;
    }

    public void setCombatant(boolean combatant) {
        this.combatant = combatant;
    }

    public int getCampaignId() {
        return campaignId;
    }

    public Strength getStrength() {
        return strength;
    }

    public Dexterity getDexterity() {
        return dexterity;
    }

    public Constitution getConstitution() {
        return constitution;
    }

    public Intelligence getIntelligence() {
        return intelligence;
    }

    public Wisdom getWisdom() {
        return wisdom;
    }

    public Charisma getCharisma() {
        return charisma;
    }

    public Resolve getResolve() {
        return resolve;
    }

    public void setResolve(Resolve resolve) {
        this.resolve = resolve;
    }

    public User getUser() {
        return user;
    }
}
