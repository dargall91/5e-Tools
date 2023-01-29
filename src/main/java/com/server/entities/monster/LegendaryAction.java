package com.server.entities.monster;

import jakarta.persistence.Entity;

@Entity
public class LegendaryAction extends MonsterFeature {
    private int cost = 1;

    public int getCost() {
        return cost;
    }

    public void setCost(int cost) {
        this.cost = cost;
    }
}
