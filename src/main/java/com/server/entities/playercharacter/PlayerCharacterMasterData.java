package com.server.entities.playercharacter;

import java.util.List;

public class PlayerCharacterMasterData {
    private List<ProficiencyBonus> proficiencyBonuses;
    private List<CharacterClass> characterClasses;
    private List<StressStatus> stressStatuses;
    private List<SpellSlots> spellSlots;
    private List<WarlockSpellSlots> warlockSpellSlots;

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

    public List<SpellSlots> getSpellSlots() {
        return spellSlots;
    }

    public void setSpellSlots(List<SpellSlots> spellSlots) {
        this.spellSlots = spellSlots;
    }

    public List<WarlockSpellSlots> getWarlockSpellSlots() {
        return warlockSpellSlots;
    }

    public void setWarlockSpellSlots(List<WarlockSpellSlots> warlockSpellSlots) {
        this.warlockSpellSlots = warlockSpellSlots;
    }
}
