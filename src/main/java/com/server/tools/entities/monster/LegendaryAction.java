package com.server.tools.entities.monster;

import jakarta.persistence.Entity;

@Entity
public class LegendaryAction extends MonsterFeature {
    private int cost;

    public int getCost() {
        return cost;
    }

    public void setCost(int cost) {
        this.cost = cost;
    }
}
