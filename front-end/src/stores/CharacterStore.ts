import { defineStore } from 'pinia'
import agent from '@/api/agent';
import { PlayerCharacter, PlayerCharacterMasterData } from '@/models/PlayerCharacter';
import { Campaign } from '@/models/Campaign';

const updateDelay = 2000;
let updateTimer: number | undefined;

export const useCharacterStore = defineStore({
  id: 'character',
  state: () => ({
    characterList: [] as PlayerCharacter[],
    masterData: {} as PlayerCharacterMasterData
  }),
  actions: {
    async getMasterData() {
      await agent.playerCharacter.getMasterData().then((data) => {
        this.masterData = data;
      })
    },
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
    async saveCharacter(index: number) {
      await agent.playerCharacter.updatePlayerCharacter(this.characterList[index])
        .then((data) => {
          this.characterList[index] = data;
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
    getMaxHitPoints(index: number, campaign: Campaign): number {
      const conModifier = Math.floor((this.characterList[index].constitution.score - 10) / 2.0);
      let maxHitPoints = 0;

      this.characterList[index].classLevelList.forEach((classLevel) => {
        if (classLevel.baseClass) {
          maxHitPoints += (classLevel.characterClass.hitDie + conModifier) + (classLevel.levels - 1) * (classLevel.characterClass.averageHitDie + conModifier);
        } else {
          maxHitPoints += (classLevel.levels) * (classLevel.characterClass.averageHitDie + conModifier);
          
        }

        if (this.characterList[index].dwarvenToughness) {
          maxHitPoints += classLevel.levels;
        }

        if (this.characterList[index].toughFeat) {
          maxHitPoints += 2 * classLevel.levels;
        }
      });

      if (campaign.inflatedHitPoints) {
        maxHitPoints = Math.ceil(1.5 * maxHitPoints);
      }
      
      return maxHitPoints;
    },
    adjustHitDie(characterIndex: number, classLevelindex: number, amount: number) {
      this.characterList[characterIndex].classLevelList[classLevelindex].usedHitDice += amount;

      if (this.characterList[characterIndex].classLevelList[classLevelindex].usedHitDice >= this.characterList[characterIndex].classLevelList[classLevelindex].levels) {
        this.characterList[characterIndex].classLevelList[classLevelindex].usedHitDice = this.characterList[characterIndex].classLevelList[classLevelindex].levels;
      }

      if (this.characterList[characterIndex].classLevelList[classLevelindex].usedHitDice < 0) {
        this.characterList[characterIndex].classLevelList[classLevelindex].usedHitDice = 0;
      }

      this.setUpdateTimer(characterIndex);
    },
    adjustDamage(index: number, damage: number, campaign: Campaign) {
      if (this.characterList[index].temporaryHitPoints > 0 && damage > 0) {
        this.characterList[index].temporaryHitPoints -= damage;
        if (this.characterList[index].temporaryHitPoints < 0) {
          damage = this.characterList[index].temporaryHitPoints * -1;
          this.characterList[index].temporaryHitPoints = 0;
        } else {
          return;
        }
      }

      this.characterList[index].damage += damage;
      const maxHitPoints = this.getMaxHitPoints(index, campaign);
      if (this.characterList[index].damage > maxHitPoints) {
        this.characterList[index].damage = maxHitPoints;
      }

      if (this.characterList[index].damage < 0) {
        this.characterList[index].damage = 0;
      }

      this.setUpdateTimer(index);
    },
    adjustTemporyHitPoints(index: number, amount: number) {
      this.characterList[index].temporaryHitPoints += amount;

      if (this.characterList[index].temporaryHitPoints < 0) {
        this.characterList[index].temporaryHitPoints = 0;
      }

      this.setUpdateTimer(index);
    },
    adjustAc(index: number, amount: number) {
      this.characterList[index].ac += amount;

      if (this.characterList[index].ac < 1) {
        this.characterList[index].ac = 1;
      }

      this.setUpdateTimer(index);
    },
    adjustAcBonus(index: number, amount: number) {
      this.characterList[index].acBonus += amount;

      if (this.characterList[index].acBonus < 0) {
        this.characterList[index].acBonus = 0;
      }

      this.setUpdateTimer(index);
    },
    resetDeathSaves(index: number) {
      this.characterList[index].deathSaveFailures = 0;
      this.characterList[index].deathSaveSuccesses = 0;
      this.setUpdateTimer(index);
    },
    adjustDeathSaveSuccesses(index: number, amount: number) {
      this.characterList[index].deathSaveSuccesses += amount;

      if (this.characterList[index].deathSaveSuccesses < 0) {
        this.characterList[index].deathSaveSuccesses = 0;
      }

      if (this.characterList[index].deathSaveSuccesses > 3) {
        this.characterList[index].deathSaveSuccesses = 3;
      }

      this.setUpdateTimer(index);
    },
    adjustDeathSaveFailures(index: number, amount: number) {
      this.characterList[index].deathSaveFailures += amount;

      if (this.characterList[index].deathSaveFailures < 0) {
        this.characterList[index].deathSaveFailures = 0;
      }

      if (this.characterList[index].deathSaveFailures > 3) {
        this.characterList[index].deathSaveFailures = 3;
      }

      this.setUpdateTimer(index);
    },
    adjustStress(index: number, amount: number, campaign: Campaign) {
      if (!campaign.madness) {
        return;
      }
      this.characterList[index].stress += amount;

      const stressMaximum = this.getStressMaximum(index, campaign)

      if (this.characterList[index].stress < 0) {
        this.characterList[index].stress = 0;
      }

      if (this.characterList[index].stress > stressMaximum) {
        this.characterList[index].stress = stressMaximum;
      }

      this.setUpdateTimer(index);
    },
    getStressThreshold(index: number, campaign: Campaign) {
      if (!campaign.madness) {
        return 0;
      }

      const resModifier = Math.floor((this.characterList[index].resolve!.score - 10) / 2.0);

      return 100 + 5 * resModifier;
    },
    getStressMaximum(index: number, campaign: Campaign) {
      return this.getStressThreshold(index, campaign) * 2;
    },
    getTotalLevels(index: number) {
      let totalLevels = 0;
      this.characterList[index].classLevelList.forEach((classLevel) => {
        totalLevels += classLevel.levels;
      });

      return totalLevels;
    },
    getProficiencyBonus(index: number) {
      const totalLevels = this.getTotalLevels(index);
      let bonus = 0;
      this.masterData.proficiencyBonuses.forEach((profBonus) => {
        if (profBonus.level === totalLevels) {
          bonus = profBonus.bonus;
        }
      });

      return bonus;
    },
    isJackOfAllTrades(index: number) {
      this.characterList[index].classLevelList.forEach((classLevel) => {
        if (classLevel.characterClass.name === "Bard" && classLevel.levels > 1) {
          return true;
        }
      });

      return false;
    },
    longRest(index: number) {

      this.setUpdateTimer(index);
    },
    levelUp(characterIndex: number, classLevelIndex: number) {
      this.characterList[characterIndex].classLevelList[classLevelIndex].levels++;
    },
    async cancelEdits(index: number) {
      await agent.playerCharacter.getCharacter(this.characterList[index].id).then((data) => {
        this.characterList[index] = data;
      });
    },
    setUpdateTimer(characterIndex: number) {
      clearTimeout(updateTimer);
      updateTimer = setTimeout(() => {
        this.saveCharacter(characterIndex);
      }, updateDelay)
    }
  },
  persist: true
});