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
    private int acBonus;
    private int damage = 0;
    private int temporaryHitPoints;
    private int initiativeBonus;
    private int rolledInitiative;
    private int campaignId;
    private int userId;
    private int deathSaveSuccesses;
    private int deathSaveFailures;
    private int stress;
    @ManyToOne
    @JoinColumn(name = "stressStatusId")
    private StressStatus stressStatus;
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

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getAc() {
        return ac;
    }

    public int getAcBonus() {
        return acBonus;
    }

    public int getDamage() {
        return damage;
    }

    public int getTemporaryHitPoints() {
        return temporaryHitPoints;
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

    public int getDeathSaveSuccesses() {
        return deathSaveSuccesses;
    }

    public int getDeathSaveFailures() {
        return deathSaveFailures;
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

    public int getStress() {
        return stress;
    }

    public StressStatus getStressStatus() {
        return stressStatus;
    }

    public void setStressStatus(StressStatus stressStatus) {
        this.stressStatus = stressStatus;
    }
}
