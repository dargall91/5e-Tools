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
    }
  },
  persist: true
})