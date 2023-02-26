package com.server.entities.playercharacter;

import com.server.entities.playercharacter.abilityscore.*;
import jakarta.persistence.*;

import java.util.List;

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
    private int userId;
    private boolean dead;
    private boolean combatant = false;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name="id")
    private PCStrength strength = new PCStrength();
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name="id")
    private PCDexterity dexterity = new PCDexterity();
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name="id")
    private PCConstitution constitution = new PCConstitution();
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name="id")
    private PCIntelligence intelligence = new PCIntelligence();
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name="id")
    private PCWisdom wisdom = new PCWisdom();
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name="id")
    private PCCharisma charisma = new PCCharisma();
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "id")
    private Resolve resolve = new Resolve();
    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "playerCharacterId")
    private List<ClassLevel> classLevelList;

    public PlayerCharacter() { }

    public PlayerCharacter(int userId, int campaignId, boolean madness, String name) {
        this.name = name;
        this.userId = userId;
        this.campaignId = campaignId;

        if (madness) {
            resolve = new Resolve();
        }
    }

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

    public PCStrength getStrength() {
        return strength;
    }

    public PCDexterity getDexterity() {
        return dexterity;
    }

    public PCConstitution getConstitution() {
        return constitution;
    }

    public PCIntelligence getIntelligence() {
        return intelligence;
    }

    public PCWisdom getWisdom() {
        return wisdom;
    }

    public PCCharisma getCharisma() {
        return charisma;
    }

    public Resolve getResolve() {
        return resolve;
    }

    public void setResolve(Resolve resolve) {
        this.resolve = resolve;
    }

    public int getUserId() {
        return userId;
    }

    public List<ClassLevel> getClassLevelList() {
        return classLevelList;
    }
}
