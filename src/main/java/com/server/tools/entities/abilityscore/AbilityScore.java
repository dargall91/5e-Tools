package com.server.tools.entities.abilityscore;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;

@MappedSuperclass
public abstract class AbilityScore {
    private int id;
    private int score;
    private boolean proficiency;

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

    public boolean isProficiency() {
        return proficiency;
    }

    public void setProficiency(boolean proficiency) {
        this.proficiency = proficiency;
    }
}
