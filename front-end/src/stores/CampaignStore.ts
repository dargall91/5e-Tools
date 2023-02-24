import { defineStore } from 'pinia'
import agent from '@/api/agent';
import { Campaign } from '@/models/Campaign';

export const useCampaignStore = defineStore({
    id: 'campaign',
    state: () => ({
        campaignList: [] as Campaign[],
        activeCampaign: null as Campaign | null
    }),
    actions: {
        async getCampaignList() {
            await agent.campaign.getCampaignList().then((data) => {
                this.campaignList = data;
            })
        },
        async getActiveCampaign() {
            await agent.campaign.getActiveCampaign().then((data) => {
                this.activeCampaign = data;
            })
        }
    }
});