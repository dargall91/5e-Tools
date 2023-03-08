package com.server.entities.playercharacter;

import java.util.List;

public class PlayerCharacterMasterData {
    private List<ProficiencyBonus> proficiencyBonuses;
    private List<CharacterClass> characterClasses;
    private List<StressStatus> stressStatuses;

    public List<ProficiencyBonus> getProficiencyBonuses() {
        return proficiencyBonuses;
    }

    public void setProficiencyBonuses(List<ProficiencyBonus> proficiencyBonuses) {
        this.proficiencyBonuses = proficiencyBonuses;
    }

    public List<CharacterClass> getCharacterClasses() {
        return characterClasses;
    }

    public void setCharacterClasses(List<CharacterClass> characterClasses) {
        this.characterClasses = characterClasses;
    }

    public List<StressStatus> getStressStatuses() {
        return stressStatuses;
    }

    public void setStressStatuses(List<StressStatus> stressStatuses) {
        this.stressStatuses = stressStatuses;
    }
}
