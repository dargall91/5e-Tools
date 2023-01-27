package com.server.tools.entities.monster;

import com.server.tools.entities.abilityscore.*;
import jakarta.persistence.*;

import java.util.List;

@Entity
public class Monster {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String name;
    private String displayName;
    private boolean archived;
    private int legendaryActionCount;
    private String size;
    private String type;
    private String senses;
    private String languages;
    private int armorClass;
    private String speed;
    private int hitPoints;
    @OneToOne
    private ChallengeRating challengeRating;
    private String alignment;
    private int bonusInitiative;
    @OneToOne
    @MapsId
    private Strength strength;
    @OneToOne
    @MapsId
    private Dexterity dexterity;
    @OneToOne
    @MapsId
    private Constitution constitution;
    @OneToOne
    @MapsId
    private Intelligence intelligence;
    @OneToOne
    @MapsId
    private Wisdom wisdom;
    @OneToOne
    @MapsId
    private Charisma charisma;
    @OneToMany
    @JoinColumn(name="id")
    private List<Ability> abilities;
    @OneToMany
    @JoinColumn(name="id")
    private List<Action> actions;
    @OneToMany
    @JoinColumn(name="id")
    private List<LegendaryAction> legendaryActions;
}
