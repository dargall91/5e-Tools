package com.server.entities.playercharacter;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class PrimalCompanionType {
    @Id
    private int id;
    private String name;
    private String size;
    private String speed;
    private int baseHitPoints;
    private int hitDie;
    private int strength;
    private int dexterity;
    private int constitution;
    private int intelligence;
    private int wisdom;
    private int charisma;
    private String abilityName;
    @Column(length = Integer.MAX_VALUE)
    private String abilityDescription;
    private String actionName;
    @Column(length = Integer.MAX_VALUE)
    private String actionDescription;

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getSize() {
        return size;
    }

    public String getSpeed() {
        return speed;
    }

    public int getBaseHitPoints() {
        return baseHitPoints;
    }

    public int getHitDie() {
        return hitDie;
    }

    public int getStrength() {
        return strength;
    }

    public int getDexterity() {
        return dexterity;
    }

    public int getConstitution() {
        return constitution;
    }

    public int getIntelligence() {
        return intelligence;
    }

    public int getWisdom() {
        return wisdom;
    }

    public int getCharisma() {
        return charisma;
    }

    public String getAbilityName() {
        return abilityName;
    }

    public String getAbilityDescription() {
        return abilityDescription;
    }

    public String getActionName() {
        return actionName;
    }

    public String getActionDescription() {
        return actionDescription;
    }

    public int getBaseArmorClass() {
        return 13;
    }

    public String getSenses() {
        return "Darkvision 60 ft., passive perception {{ 10 + Math.floor((wisdom - 10) / 2) + " +
                "characterStoreFunctions.getProficiencyBonus(characterIndex) }}";
    }

    public String getLanguages() {
        return "Understands the languages you speak";
    }

    public String getAlignment() {
        return "Neutral";
    }

    public String getType() {
        return "Beast";
    }
}
