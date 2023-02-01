package com.server.entities.encounter;

import com.server.entities.monster.Monster;
import jakarta.persistence.*;

@Entity
public class EncounterMonster {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @OneToOne
    @JoinColumn(name="monsterId")
    private Monster monster;
    private int quantity = 1;
    private int initiative = 1;
    private boolean invisible = false;
    private boolean reinforcement = false;
    private boolean minion = false;

    public EncounterMonster() { }

    public EncounterMonster(Monster monster) {
        this.monster = monster;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Monster getMonster() {
        return monster;
    }

    public void setMonster(Monster monster) {
        this.monster = monster;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getInitiative() {
        return initiative;
    }

    public void setInitiative(int initiative) {
        this.initiative = initiative;
    }

    public boolean isInvisible() {
        return invisible;
    }

    public void setInvisible(boolean invisible) {
        this.invisible = invisible;
    }

    public boolean isReinforcement() {
        return reinforcement;
    }

    public void setReinforcement(boolean reinforcement) {
        this.reinforcement = reinforcement;
    }

    public boolean isMinion() {
        return minion;
    }

    public void setMinion(boolean minion) {
        this.minion = minion;
    }
}
