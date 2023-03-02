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

  <CAccordion class="mt-2" accordion-color="black" always-open>
    <CAccordionItem v-for="(character, index) in characterList" :key="index" :item-key="index">
      <CAccordionHeader style="{color: #00FF00;}">
        {{ character.name }}
      </CAccordionHeader>
      <CAccordionBody>
        <CRow v-for="classLevel in character.classLevelList" :key="classLevel.id">
          <CCol v-if="classLevel.baseClass">
            <strong>Base Class:</strong> {{ classLevel.characterClass.name }}
          </CCol>
          <CCol v-else>
            <strong>Multiclass:</strong> {{ classLevel.characterClass.name }}
          </CCol>
          <CCol>
            <strong>Level:</strong> {{ classLevel.levels }}
          </CCol>
          <CCol>
            <strong>Used Hit Dice:</strong> {{ classLevel.usedHitDice }} / {{ classLevel.levels }}
          </CCol>
        </CRow>
        <CRow>
          <CCol>
            <strong>Hit Points:</strong> {{ character.ac }} / MAX
          </CCol>
          <CCol>
            <strong>AC:</strong> {{ character.ac }}
          </CCol>
        </CRow>
        <CRow>
          <CCol>
            <strong>Death Saving Throws:</strong>
          </CCol>
          <CCol>
            <strong>Successes:</strong> {{ character.ac }}
          </CCol>
          <CCol>
            <strong>Failures:</strong> {{ character.ac }}
          </CCol>
        </CRow>
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

<style>
  .accordion-button:focus {
      box-shadow: none;
      border-color: rgba(0,0,0,.125);
  }

  .accordion-button.collapsed {
    background: lightgray;
  }

  .accordion-button:not(.collapsed) {
      color: black;
      background-color: darkgray;
  }

  .accordion-button:after {
    background-image: url("data:image/svg+xml,<svg xmlns='http://www.w3.org/2000/svg' viewBox='0 0 16 16' fill='%23000000'><path fill-rule='evenodd' d='M1.646 4.646a.5.5 0 0 1 .708 0L8 10.293l5.646-5.647a.5.5 0 0 1 .708.708l-6 6a.5.5 0 0 1-.708 0l-6-6a.5.5 0 0 1 0-.708z'/></svg>") !important;
  }
</style>