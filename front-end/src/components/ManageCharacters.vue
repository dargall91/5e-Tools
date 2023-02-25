<template>
    <h2 class="mt-2">Manage Characters</h2>

    <CFormSelect>
        <option>Select a Campaign</option>
        <option v-for="(item) in campaign.campaignList.value" value="{{ item.id }}" :key="item.id">{{ item.name }}</option>
    </CFormSelect>
</template>

<script lang="ts">
    import { defineComponent } from 'vue'
    import { storeToRefs } from 'pinia'
    import { useUserStore } from '@/stores/UserStore'
    import { useCampaignStore } from '@/stores/CampaignStore'
    import { CFormSelect } from '@coreui/vue'
    
    export default defineComponent ({
        name: "ManageCharacters",
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
            }
        },
        methods: {
            
        },
        mounted() {
            this.getCampaignList();
            this.getActiveCampaign();
        }
    });
</script>