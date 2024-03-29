package com.server.entities.monster;

import com.server.entities.monster.abilityscore.*;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
public class Monster {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private int campaignId;
    private String name;
    private String displayName;
    private boolean archived = false;
    private int legendaryActionCount = 0;
    private String size = "Medium";
    private String type = "Humanoid";
    @Column(length = Integer.MAX_VALUE)
    private String senses = "Passive Perception 10";
    @Column(length = Integer.MAX_VALUE)
    private String languages = "Common";
    private int armorClass = 10;
    private String speed = "30 ft";
    private int hitPoints = 0;
    @OneToOne
    @JoinColumn(name="challengeRatingId")
    private ChallengeRating challengeRating;
    private String alignment = "Neutral";
    private int bonusInitiative = 0;
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
    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "monsterId")
    private List<Ability> abilities = new ArrayList<>();
    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "monsterId")
    private List<Action> actions = new ArrayList<>();
    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "monsterId")
    private List<LegendaryAction> legendaryActions = new ArrayList<>();

    public Monster() { }

    public Monster(String name, int campaignId, ChallengeRating challengeRating) {
        this.name = name;
        this.campaignId = campaignId;
        displayName = name;
        this.challengeRating = challengeRating;
    }

    /**
     * Creates a new monster using an existing monster as a template
     * @param template the source monster to copy
     * @param name the new monster's name
     */
    public Monster(Monster template, String name) {
        this.name = name;
        displayName = name;
        campaignId = template.getCampaignId();
        legendaryActionCount = template.getLegendaryActionCount();
        size = template.getSize();
        type = template.getType();
        senses = template.getSenses();
        languages = template.getLanguages();
        armorClass = template.getArmorClass();
        hitPoints = template.getHitPoints();
        challengeRating = template.getChallengeRating();
        alignment = template.getAlignment();
        bonusInitiative = template.getBonusInitiative();
        strength = new Strength(template.getStrength());
        dexterity = new Dexterity(template.getDexterity());
        constitution = new Constitution(template.getConstitution());
        intelligence = new Intelligence(template.getIntelligence());
        wisdom = new Wisdom(template.getWisdom());
        charisma = new Charisma(template.getCharisma());

        for (Ability ability : template.getAbilities()) {
            Ability copy = new Ability();
            copy.setName(ability.getName());
            copy.setDescription(ability.getDescription());
            abilities.add(copy);
        }

        for (Action action : template.getActions()) {
            Action copy = new Action();
            copy.setName(action.getName());
            copy.setDescription(action.getDescription());
            actions.add(copy);
        }

        for (LegendaryAction legendaryAction : template.getLegendaryActions()) {
            LegendaryAction copy = new LegendaryAction();
            copy.setName(legendaryAction.getName());
            copy.setDescription(legendaryAction.getDescription());
            copy.setCost(legendaryAction.getCost());
            legendaryActions.add(copy);
        }
    }

    public Integer getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCampaignId() {
        return campaignId;
    }

    public void setCampaignId(int campaignId) {
        this.campaignId = campaignId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public boolean isArchived() {
        return archived;
    }

    public void setArchived(boolean archived) {
        this.archived = archived;
    }

    public int getLegendaryActionCount() {
        return legendaryActionCount;
    }

    public void setLegendaryActionCount(int legendaryActionCount) {
        this.legendaryActionCount = legendaryActionCount;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getSenses() {
        return senses;
    }

    public void setSenses(String senses) {
        this.senses = senses;
    }

    public String getLanguages() {
        return languages;
    }

    public void setLanguages(String languages) {
        this.languages = languages;
    }

    public int getArmorClass() {
        return armorClass;
    }

    public void setArmorClass(int armorClass) {
        this.armorClass = armorClass;
    }

    public String getSpeed() {
        return speed;
    }

    public void setSpeed(String speed) {
        this.speed = speed;
    }

    public int getHitPoints() {
        return hitPoints;
    }

    public void setHitPoints(int hitPoints) {
        this.hitPoints = hitPoints;
    }

    public ChallengeRating getChallengeRating() {
        return challengeRating;
    }

    public void setChallengeRating(ChallengeRating challengeRating) {
        this.challengeRating = challengeRating;
    }

    public String getAlignment() {
        return alignment;
    }

    public void setAlignment(String alignment) {
        this.alignment = alignment;
    }

    public int getBonusInitiative() {
        return bonusInitiative;
    }

    public void setBonusInitiative(int bonusInitiative) {
        this.bonusInitiative = bonusInitiative;
    }

    public Strength getStrength() {
        return strength;
    }

    public void setStrength(Strength strength) {
        this.strength = strength;
    }

    public Dexterity getDexterity() {
        return dexterity;
    }

    public void setDexterity(Dexterity dexterity) {
        this.dexterity = dexterity;
    }

    public Constitution getConstitution() {
        return constitution;
    }

    public void setConstitution(Constitution constitution) {
        this.constitution = constitution;
    }

    public Intelligence getIntelligence() {
        return intelligence;
    }

    public void setIntelligence(Intelligence intelligence) {
        this.intelligence = intelligence;
    }

    public Wisdom getWisdom() {
        return wisdom;
    }

    public void setWisdom(Wisdom wisdom) {
        this.wisdom = wisdom;
    }

    public Charisma getCharisma() {
        return charisma;
    }

    public void setCharisma(Charisma charisma) {
        this.charisma = charisma;
    }

    public List<Ability> getAbilities() {
        return abilities;
    }

    public void setAbilities(List<Ability> abilities) {
        this.abilities = abilities;
    }

    public List<Action> getActions() {
        return actions;
    }

    public void setActions(List<Action> actions) {
        this.actions = actions;
    }

    public List<LegendaryAction> getLegendaryActions() {
        return legendaryActions;
    }

    public void setLegendaryActions(List<LegendaryAction> legendaryActions) {
        this.legendaryActions = legendaryActions;
    }
}
