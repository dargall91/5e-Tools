import { defineStore } from 'pinia'
import agent from '@/api/agent';
import { PlayerCharacter } from '@/models/PlayerCharacter';

export const useCharacterStore = defineStore({
  id: 'character',
  state: () => ({
    characterList: [] as PlayerCharacter[]
  }),
  actions: {
    async getCharacterList(userId: number, campaignId: number) {
      if (userId === 0 || campaignId === 0) {
        this.clearCharacterList();
        return;
      }
      
      await agent.playerCharacter.getCharacterList(userId, campaignId)
        .then((data) => {
          this.characterList = data;
        });
    },
    clearCharacterList() {
      this.characterList = [];
    },
    setStrength(score: number, index: number) {
      this.characterList[index].strength.score = score;
    },
    setStrengthSave(proficient: string, index: number) {
      this.characterList[index].strength.proficient = proficient === "true";
    },
    setAthletics(level: number, index: number) {
      this.characterList[index].strength.athletics = level;
    },
    setDexterity(score: number, index: number) {
      this.characterList[index].dexterity.score = score;
    },
    setDexteritySave(proficient: string, index: number) {
      this.characterList[index].dexterity.proficient = proficient === "true";
    },
    setAcrobatics(level: number, index: number) {
      this.characterList[index].dexterity.acrobatics = level;
    },
    setSleightOfHand(level: number, index: number) {
      this.characterList[index].dexterity.sleightOfHand = level;
    },
    setStealth(level: number, index: number) {
      this.characterList[index].dexterity.stealth = level;
    },
    setConstitution(score: number, index: number) {
      this.characterList[index].constitution.score = score;
    },
    setConstitutionSave(proficient: string, index: number) {
      this.characterList[index].constitution.proficient = proficient === "true";
    },
    setIntelligence(score: number, index: number) {
      this.characterList[index].intelligence.score = score;
    },
    setIntelligenceSave(proficient: string, index: number) {
      this.characterList[index].intelligence.proficient = proficient === "true";
    },
    setArcana(level: number, index: number) {
      this.characterList[index].intelligence.arcana = level;
    },
    setHistory(level: number, index: number) {
      this.characterList[index].intelligence.history = level;
    },
    setInvestigation(level: number, index: number) {
      this.characterList[index].intelligence.investigation = level;
    },
    setNature(level: number, index: number) {
      this.characterList[index].intelligence.nature = level;
    },
    setReligion(level: number, index: number) {
      this.characterList[index].intelligence.religion = level;
    },
    setWisdom(score: number, index: number) {
      this.characterList[index].wisdom.score = score;
    },
    setWisdomSave(proficient: string, index: number) {
      this.characterList[index].wisdom.proficient = proficient === "true";
    },
    setAnimalHandling(level: number, index: number) {
      this.characterList[index].wisdom.animalHandling = level;
    },
    setInsight(level: number, index: number) {
      this.characterList[index].wisdom.insight = level;
    },
    setMedicine(level: number, index: number) {
      this.characterList[index].wisdom.medicine = level;
    },
    setPerception(level: number, index: number) {
      this.characterList[index].wisdom.perception = level;
    },
    setSurvival(level: number, index: number) {
      this.characterList[index].wisdom.survival = level;
    },
    setCharisma(score: number, index: number) {
      this.characterList[index].charisma.score = score;
    },
    setCharismaSave(proficient: string, index: number) {
      this.characterList[index].charisma.proficient = proficient === "true";
    },
    setDeception(level: number, index: number) {
      this.characterList[index].charisma.deception = level;
    },
    setIntimidation(level: number, index: number) {
      this.characterList[index].charisma.intimidation = level;
    },
    setPerformance(level: number, index: number) {
      this.characterList[index].charisma.performance = level;
    },
    setPersuasion(level: number, index: number) {
      this.characterList[index].charisma.persuasion = level;
    },
    setResolve(score: number, index: number) {
      if (this.characterList[index].resolve != null) {
        this.characterList[index].resolve!.score = score;
      }
    },
    async saveCharacter(index: number) {
      await agent.playerCharacter.updatePlayerCharacter(this.characterList[index])
        .then((data) => {
          this.characterList[index] = data;
        });
    }
  },
  persist: true
})