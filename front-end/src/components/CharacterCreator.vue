<template>
    <h2 class="mt-2">Character Creator</h2>

    <CFormSelect @change="setCampaign($event.target.value)" :model="campaignId">
        <option :value="0">Select a Campaign</option>
        <option v-for="(item) in campaign.campaignList.value" :value="item.id" :key="item.id">{{ item.name }}</option>
    </CFormSelect>

    <div class="mt-2" v-if="campaignId != 0">
        <CFormSelect @change="setClass($event.target.value)" :model="selectedClasses[0]">
            <option :value="0">Select a Base Class</option>
            <option v-for="(item) in classList" :value="item.id" :key="item.id">{{ item.name }}</option>
        </CFormSelect>
    </div>
</template>

<script lang="ts">
    import { defineComponent } from 'vue'
    import { storeToRefs } from 'pinia'
    import { useUserStore } from '@/stores/UserStore'
    import { useCampaignStore } from '@/stores/CampaignStore'
    import { CFormSelect } from '@coreui/vue'
    import { CharacterClass } from '@/models/PlayerCharacter'
    import agent from '@/api/agent';
    
    export default defineComponent ({
        name: "CharacterCreator",
        components: { CFormSelect },
        setup() {
            return {
                user: storeToRefs(useUserStore()),
                campaign: storeToRefs(useCampaignStore())
            };
        },
        data() {
            return {
                getCampaignList: useCampaignStore().getCampaignList,
                getActiveCampaign: useCampaignStore().getActiveCampaign,
                campaignId: 0 as number,
                classList: [] as CharacterClass[],
                selectedClasses: [] as number[]
            }
        },
        methods: {
            setCampaign(id: number) {
                this.campaignId = id;
            },
            async getClassList() {
                await agent.playerCharacter.getClassList().then((data) => {
                    this.classList = data;
                })
            },
            setClass(id: number) {
                console.log(this.selectedClasses[0]);
                this.selectedClasses[0] = id;
                console.log(this.selectedClasses[0]);
            }
        },
        mounted() {
            this.getCampaignList();
            this.getActiveCampaign();
            this.getClassList();
        }
    });
</script>