<template>
  <h2 class="mt-2">Manage Characters</h2>

  <CRow>
    <CCol sm="3" md="2" class="mt-2">
      <CFormLabel for="campaign" class="fw-bold align-text-bottom">Campaign:</CFormLabel>
    </CCol>
    <CCol sm="8" md="6">
      <CFormSelect @change="setCampaign(parseInt($event.target.value))" id="campaign" :modelValue="'0'">
        <option :value="0">Select a Campaign</option>
        <option v-for="(item) in campaign.campaignList.value" :value="item.id" :key="item.id">{{ item.name }}</option>
      </CFormSelect>
    </CCol>
  </CRow>

  <CAccordion always-open>
    <CAccordionItem  v-for="(character, index) in characterList" :key="index" :item-key="index">
      <CAccordionHeader >
        {{ character.name }}
      </CAccordionHeader>
      <CAccordionBody>
        Some Body
      </CAccordionBody>
    </CAccordionItem>
  </CAccordion>
</template>

<script lang="ts">
  import { defineComponent } from 'vue'
  import { storeToRefs } from 'pinia'
  import { useUserStore } from '@/stores/UserStore'
  import { useCampaignStore } from '@/stores/CampaignStore'
  import { CAccordion, CAccordionBody, CAccordionHeader, CAccordionItem, CCol, CFormLabel, CFormSelect, CRow } from '@coreui/vue'
  import { PlayerCharacter } from '@/models/PlayerCharacter'
  import agent from '@/api/agent'
  import { reactive } from 'vue'
  
  export default defineComponent ({
    name: "ManageCharacters",
    components: { CFormSelect, CAccordion, CRow, CCol, CFormLabel, CAccordionHeader, CAccordionBody, CAccordionItem },
    setup() {
      return {
        user: storeToRefs(useUserStore()),
        campaign: storeToRefs(useCampaignStore()),
        selectedCampaign: 0,
        characterList: reactive([] as PlayerCharacter[])
      };
    },
    data() {
      return {
        getCampaignList: useCampaignStore().getCampaignList,
        getActiveCampaign: useCampaignStore().getActiveCampaign,
      }
    },
    methods: {
      async setCampaign(id: number) {
        this.selectedCampaign = id;

        if (this.selectedCampaign != 0) {
          await agent.playerCharacter.getCharacterList(this.user.user.value?.id as number, this.selectedCampaign)
            .then((data) => {
              Object.assign(this.characterList, data);
            });
        } else {
          this.characterList = [];
        }
      },
    },
    async mounted() {
      this.getCampaignList();
      this.getActiveCampaign();
    }
  });
</script>