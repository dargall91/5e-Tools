package com.server.repositories.projections;

public interface AndroidPlayerCharacterProjection {
    int getId();
    String getName();
    int getAc();
    int getAcBonus();
    int getRolledInitiative();
    int getInitiativeBonus();
    boolean isCombatant();
}
