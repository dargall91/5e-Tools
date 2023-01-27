package com.server.tools.entities.monster;

import com.server.tools.entities.Campaign;
import com.server.tools.entities.abilityscore.*;
import jakarta.persistence.*;

import java.util.List;

@Entity
public class Monster {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @OneToOne
    @JoinColumn(name="id")
    private Campaign campaign;
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
    @JoinColumn(name="id")
    private ChallengeRating challengeRating;
    private String alignment;
    private int bonusInitiative;
    @OneToOne
    @JoinColumn(name="id")
    private Strength strength;
    @OneToOne
    @JoinColumn(name="id")
    private Dexterity dexterity;
    @OneToOne
    @JoinColumn(name="id")
    private Constitution constitution;
    @OneToOne
    @JoinColumn(name="id")
    private Intelligence intelligence;
    @OneToOne
    @JoinColumn(name="id")
    private Wisdom wisdom;
    @OneToOne
    @JoinColumn(name="id")
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
