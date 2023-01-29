package com.server.entities.abilityscore;

import com.server.entities.monster.Monster;
import jakarta.persistence.*;

@MappedSuperclass
public abstract class AbilityScore {
    private int id;
    private int score = 10;
    private boolean proficient = false;

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

    public boolean isProficient() {
        return proficient;
    }

    public void setProficient(boolean proficient) {
        this.proficient = proficient;
    }
}
