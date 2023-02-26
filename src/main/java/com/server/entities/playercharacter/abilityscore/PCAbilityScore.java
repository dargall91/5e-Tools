package com.server.entities.playercharacter.abilityscore;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;

@MappedSuperclass
@JsonIgnoreProperties(ignoreUnknown = true)
public abstract class PCAbilityScore {
    private int id;
    private int score = 10;
    private boolean proficient = false;

    public PCAbilityScore() { }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    @Transient
    public int getScoreModifier() {
        return (int) Math.floor((score - 10) / 2.0);
    }

    public boolean isProficient() {
        return proficient;
    }

    public void setProficient(boolean proficient) {
        this.proficient = proficient;
    }
}
