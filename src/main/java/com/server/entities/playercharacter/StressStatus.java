package com.server.entities.playercharacter;

import jakarta.persistence.*;

@Entity
public class StressStatus {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String name;
    private String type;
    @Column(length = Integer.MAX_VALUE)
    private String description;
    private int minRoll;
    private int maxRoll;

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getType() {
        return type;
    }

    public String getDescription() {
        return description;
    }

    public int getMinRoll() {
        return minRoll;
    }

    public int getMaxRoll() {
        return maxRoll;
    }
}
