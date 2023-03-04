<template>
  <h2 class="mt-2">Character Creator</h2>

  <div :key="key">
    <CForm @submit.prevent="submit">
      <!-- Campiagn Selector -->
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

      <!-- Name -->
      <CRow class="mt-2">
        <CCol sm="3" md="2" class="mt-2">
          <CFormLabel for="name" class="fw-bold align-text-bottom">Name:</CFormLabel>
        </CCol>
        <CCol sm="8" md="6">
          <CFormInput id="name" v-model="playerCharacter.name" type="text" min="1"></CFormInput>
        </CCol>
      </CRow>
    
      <!-- AC -->
      <CRow class="mt-2">
        <CCol sm="3" md="2" class="mt-2">
          <CFormLabel for="armorClass" class="fw-bold align-text-bottom">Armor Class:</CFormLabel>
        </CCol>
        <CCol sm="8" md="6">
          <CFormInput id="armorClass" min="1" max="30" v-model.number="playerCharacter.ac" type="number"></CFormInput>
        </CCol>
      </CRow>

      <!-- Base Class Selector -->
      <CRow class="mt-2">
        <CCol sm="3" md="2" class="mt-2">
          <CFormLabel for="baseclass" class="fw-bold">Base Class:</CFormLabel>
        </CCol>
        <CCol sm="5" md="4" lg="3">
          <CFormSelect @change="setClass(parseInt($event.target.value))" id="baseclass">
            <option :value="0">Select a Base Class</option>
            <option v-for="(item) in classList" :value="item.id" :key="item.id">{{ item.name }}</option>
          </CFormSelect>
        </CCol>
        <CCol sm="2" md="1" class="mt-2">
          <CFormLabel for="baselevel" class="fw-bold">Level:</CFormLabel>
        </CCol>
        <CCol sm="2" md="2" lg="1">
          <CFormSelect @change="setBaseLevel(parseInt($event.target.value))" id="baselevel">
            <option v-for="level in numberList" :value="level" :key="level">{{ level }}</option>
          </CFormSelect>
        </CCol>
      </CRow>

      <!-- multiclass -->
      <CRow v-for="(multiClass, index) in multiClassList" :id="index.toString()" :key="index" class="mt-2">
        <CCol sm="3" md="2" class="mt-2">
          <CFormLabel :for="'multiClass' + index" class="fw-bold">Multiclass:</CFormLabel>
        </CCol>
        <CCol sm="5" md="4" lg="3">
          <CFormSelect @change="setMultiClass(parseInt($event.target.value), index)" :id="'multiClass' + index" :key="index">
            <option :value="0">Select a Multiclass</option>
            <option v-for="(item) in classList" :value="item.id" :key="item.id">{{ item.name }}</option>
          </CFormSelect>
        </CCol>
        <CCol sm="2" md="1" class="mt-2">
          <CFormLabel :for="'multiClassLevel' + index" class="fw-bold">Level:</CFormLabel>
        </CCol>
        <CCol sm="2" md="2" lg="1">
          <CFormSelect @change="setMultiClassLevel(parseInt($event.target.value), index)" :id="'multiClassLevel' + index" :key="index">
            <option v-for="level in numberList" :value="level" :key="level">{{ level }}</option>
          </CFormSelect>
        </CCol>
        <CCol>
          <CButton color="dark" type="button" @click="removeMulticlass(index)" class="btn btn-primary">Remove Mulitclass</CButton>
        </CCol>
      </CRow>

      <CButton v-if="multiClassList.length < 12" color="dark" type="button" @click="addMulticlass" class="mt-2 btn btn-primary">Add Mulitclass</CButton>

      <!-- Ability Scores -->
      <CRow>
        <!-- Strength -->
        <CCol xs="6" sm="4">
          <CCard class="mt-2">
            <CCardHeader>Strength (STR)</CCardHeader>
            <CCardBody>
              <CRow>
                <CCol class="mt-2">
                  <CFormLabel class="fw-bold">Score:</CFormLabel>
                </CCol>
                <CCol sm="auto">
                  <CFormSelect @change="setStrength(parseInt($event.target.value))" :modelValue="playerCharacter.strength?.score.toString()">
                    <option v-for="score in numberList" :value="score" :key="score">{{ score }}</option>
                  </CFormSelect>
                </CCol>
              </CRow>
              <CRow>
                <CCol class="mt-2">
                  <CFormLabel class="fw-bold">Saving Throws:</CFormLabel>
                </CCol>
                <CCol sm="auto">
                  <CFormSelect @change="setStrengthSave($event.target.value)" :modelValue="'false'">
                    <option v-for="proficiency in savingThrowLevels" :value="proficiency.proficient" :key="proficiency.proficient.toString">{{ proficiency.value }}</option>
                  </CFormSelect>
                </CCol>
              </CRow>
              <CRow>
                <CCol class="mt-2">
                  <CFormLabel class="fw-bold">Athletics:</CFormLabel>
                </CCol>
                <CCol sm="auto">
                  <CFormSelect @change="setAthletics(parseInt($event.target.value))" :modelValue="'0'">
                    <option v-for="profciency in skillProficiencyLevel" :value="profciency.level" :key="profciency.level">{{ profciency.value }}</option>
                  </CFormSelect>
                </CCol>
              </CRow>
            </CCardBody>
          </CCard>
        </CCol>

        <!-- Dexterity -->
        <CCol xs="6" sm="4">
          <CCard class="mt-2">
            <CCardHeader>Dexterity (DEX)</CCardHeader>
            <CCardBody>
              <CRow>
                <CCol class="mt-2">
                  <CFormLabel class="fw-bold">Score:</CFormLabel>
                </CCol>
                <CCol sm="auto">
                  <CFormSelect @change="setDexterity(parseInt($event.target.value))" :modelValue="playerCharacter.dexterity?.score.toString()">
                    <option v-for="score in numberList" :value="score" :key="score">{{ score }}</option>
                  </CFormSelect>
                </CCol>
              </CRow>
              <CRow>
                <CCol class="mt-2">
                  <CFormLabel class="fw-bold">Saving Throws:</CFormLabel>
                </CCol>
                <CCol sm="auto">
                  <CFormSelect @change="setDexteritySave($event.target.value)" :modelValue="'false'">
                    <option v-for="proficiency in savingThrowLevels" :value="proficiency.proficient" :key="proficiency.proficient.toString">{{ proficiency.value }}</option>
                  </CFormSelect>
                </CCol>
              </CRow>
              <CRow>
                <CCol class="mt-2">
                  <CFormLabel class="fw-bold">Acrobatics:</CFormLabel>
                </CCol>
                <CCol sm="auto">
                  <CFormSelect @change="setAcrobatics(parseInt($event.target.value))" :modelValue="'0'">
                    <option v-for="profciency in skillProficiencyLevel" :value="profciency.level" :key="profciency.level">{{ profciency.value }}</option>
                  </CFormSelect>
                </CCol>
              </CRow>
              <CRow>
                <CCol class="mt-2">
                  <CFormLabel class="fw-bold">Sleight of Hand:</CFormLabel>
                </CCol>
                <CCol sm="auto">
                  <CFormSelect @change="setSleightOfHand(parseInt($event.target.value))" :modelValue="'0'">
                    <option v-for="profciency in skillProficiencyLevel" :value="profciency.level" :key="profciency.level">{{ profciency.value }}</option>
                  </CFormSelect>
                </CCol>
              </CRow>
              <CRow>
                <CCol class="mt-2">
                  <CFormLabel class="fw-bold">Stealth:</CFormLabel>
                </CCol>
                <CCol sm="auto">
                  <CFormSelect @change="setStealth(parseInt($event.target.value))" :modelValue="'0'">
                    <option v-for="profciency in skillProficiencyLevel" :value="profciency.level" :key="profciency.level">{{ profciency.value }}</option>
                  </CFormSelect>
                </CCol>
              </CRow>
            </CCardBody>
          </CCard>
        </CCol>

        <!-- Constitution -->
        <CCol xs="6" sm="4">
          <CCard class="mt-2">
            <CCardHeader>Constitution (CON)</CCardHeader>
            <CCardBody>
              <CRow>
                <CCol class="mt-2">
                  <CFormLabel class="fw-bold">Score:</CFormLabel>
                </CCol>
                <CCol sm="auto">
                  <CFormSelect @change="setConstitution(parseInt($event.target.value))" :modelValue="playerCharacter.constitution?.score.toString()">
                    <option v-for="score in numberList" :value="score" :key="score">{{ score }}</option>
                  </CFormSelect>
                </CCol>
              </CRow>
              <CRow>
                <CCol class="mt-2">
                  <CFormLabel class="fw-bold">Saving Throws:</CFormLabel>
                </CCol>
                <CCol sm="auto">
                  <CFormSelect @change="setConstitutionSave($event.target.value)" :modelValue="'false'">
                    <option v-for="proficiency in savingThrowLevels" :value="proficiency.proficient" :key="proficiency.proficient.toString">{{ proficiency.value }}</option>
                  </CFormSelect>
                </CCol>
              </CRow>
            </CCardBody>
          </CCard>
        </CCol>

        <!-- Intelligence  -->
        <CCol xs="6" sm="4">
        <CCard class="mt-2">
        <CCardHeader>Intelligence (INT)</CCardHeader>
        <CCardBody>
          <CRow>
            <CCol class="mt-2">
              <CFormLabel class="fw-bold">Score:</CFormLabel>
            </CCol>
            <CCol sm="auto">
              <CFormSelect @change="setIntelligence(parseInt($event.target.value))" :modelValue="playerCharacter.intelligence?.score.toString()">
                <option v-for="score in numberList" :value="score" :key="score">{{ score }}</option>
              </CFormSelect>
            </CCol>
          </CRow>
          <CRow>
            <CCol class="mt-2">
              <CFormLabel class="fw-bold">Saving Throws:</CFormLabel>
            </CCol>
            <CCol sm="auto">
              <CFormSelect @change="setIntelligenceSave($event.target.value)" :modelValue="'false'">
                <option v-for="proficiency in savingThrowLevels" :value="proficiency.proficient" :key="proficiency.proficient.toString">{{ proficiency.value }}</option>
              </CFormSelect>
            </CCol>
          </CRow>
          <CRow>
            <CCol class="mt-2">
              <CFormLabel class="fw-bold">Arcana:</CFormLabel>
            </CCol>
            <CCol sm="auto">
              <CFormSelect @change="setArcana(parseInt($event.target.value))" :modelValue="'0'">
                <option v-for="profciency in skillProficiencyLevel" :value="profciency.level" :key="profciency.level">{{ profciency.value }}</option>
              </CFormSelect>
            </CCol>
          </CRow>
          <CRow>
            <CCol class="mt-2">
              <CFormLabel class="fw-bold">History:</CFormLabel>
            </CCol>
            <CCol sm="auto">
              <CFormSelect @change="setHistory(parseInt($event.target.value))" :modelValue="'0'">
                <option v-for="profciency in skillProficiencyLevel" :value="profciency.level" :key="profciency.level">{{ profciency.value }}</option>
              </CFormSelect>
            </CCol>
          </CRow>
          <CRow>
            <CCol class="mt-2">
              <CFormLabel class="fw-bold">Investigation:</CFormLabel>
            </CCol>
            <CCol sm="auto">
              <CFormSelect @change="setInvestigation(parseInt($event.target.value))" :modelValue="'0'">
                <option v-for="profciency in skillProficiencyLevel" :value="profciency.level" :key="profciency.level">{{ profciency.value }}</option>
              </CFormSelect>
            </CCol>
          </CRow>
          <CRow>
            <CCol class="mt-2">
              <CFormLabel class="fw-bold">Nature:</CFormLabel>
            </CCol>
            <CCol sm="auto">
              <CFormSelect @change="setNature(parseInt($event.target.value))" :modelValue="'0'">
                <option v-for="profciency in skillProficiencyLevel" :value="profciency.level" :key="profciency.level">{{ profciency.value }}</option>
              </CFormSelect>
            </CCol>
          </CRow>
          <CRow>
            <CCol class="mt-2">
              <CFormLabel class="fw-bold">Religion:</CFormLabel>
            </CCol>
            <CCol sm="auto">
              <CFormSelect @change="setReligion(parseInt($event.target.value))" :modelValue="'0'">
                <option v-for="profciency in skillProficiencyLevel" :value="profciency.level" :key="profciency.level">{{ profciency.value }}</option>
              </CFormSelect>
            </CCol>
          </CRow>
        </CCardBody>
        </CCard>
        </CCol>

        <!-- Wisdom -->
        <CCol xs="6" sm="4">
          <CCard class="mt-2">
              <CCardHeader>Wisdom (WIS)</CCardHeader>
            <CCardBody>
              <CRow>
                <CCol class="mt-2">
                  <CFormLabel class="fw-bold">Score:</CFormLabel>
                </CCol>
                <CCol sm="auto">
                  <CFormSelect @change="setWisdom(parseInt($event.target.value))" :modelValue="playerCharacter.wisdom?.score.toString()">
                    <option v-for="score in numberList" :value="score" :key="score">{{ score }}</option>
                  </CFormSelect>
                </CCol>
              </CRow>
              <CRow>
                <CCol class="mt-2">
                  <CFormLabel class="fw-bold">Saving Throws:</CFormLabel>
                </CCol>
                <CCol sm="auto">
                  <CFormSelect @change="setWisdomSave($event.target.value)" :modelValue="'false'">
                    <option v-for="proficiency in savingThrowLevels" :value="proficiency.proficient" :key="proficiency.proficient.toString">{{ proficiency.value }}</option>
                  </CFormSelect>
                </CCol>
              </CRow>
              <CRow>
                <CCol class="mt-2">
                  <CFormLabel class="fw-bold">Animal Handling:</CFormLabel>
                </CCol>
                <CCol sm="auto">
                  <CFormSelect @change="setAnimalHandling(parseInt($event.target.value))" :modelValue="'0'">
                    <option v-for="profciency in skillProficiencyLevel" :value="profciency.level" :key="profciency.level">{{ profciency.value }}</option>
                  </CFormSelect>
                </CCol>
              </CRow>
              <CRow>
                <CCol class="mt-2">
                  <CFormLabel class="fw-bold">Insight:</CFormLabel>
                </CCol>
                <CCol sm="auto">
                  <CFormSelect @change="setInsight(parseInt($event.target.value))" :modelValue="'0'">
                    <option v-for="profciency in skillProficiencyLevel" :value="profciency.level" :key="profciency.level">{{ profciency.value }}</option>
                  </CFormSelect>
                </CCol>
              </CRow>
              <CRow>
                <CCol class="mt-2">
                  <CFormLabel class="fw-bold">Medicine:</CFormLabel>
                </CCol>
                <CCol sm="auto">
                  <CFormSelect @change="setMedicine(parseInt($event.target.value))" :modelValue="'0'">
                    <option v-for="profciency in skillProficiencyLevel" :value="profciency.level" :key="profciency.level">{{ profciency.value }}</option>
                  </CFormSelect>
                </CCol>
              </CRow>
              <CRow>
                <CCol class="mt-2">
                  <CFormLabel class="fw-bold">Survival:</CFormLabel>
                </CCol>
                <CCol sm="auto">
                  <CFormSelect @change="setSurvival(parseInt($event.target.value))" :modelValue="'0'">
                    <option v-for="profciency in skillProficiencyLevel" :value="profciency.level" :key="profciency.level">{{ profciency.value }}</option>
                  </CFormSelect>
                </CCol>
              </CRow>
            </CCardBody>
          </CCard>
        </CCol>

        <!-- Charisma -->
        <CCol xs="6" sm="4">
          <CCard class="mt-2">
            <CCardHeader>Charisma (CHA)</CCardHeader>
            <CCardBody>
              <CRow>
                <CCol class="mt-2">
                  <CFormLabel class="fw-bold">Score:</CFormLabel>
                </CCol>
                <CCol sm="auto">
                  <CFormSelect @change="setCharisma(parseInt($event.target.value))" :modelValue="playerCharacter.charisma?.score.toString()">
                    <option v-for="score in numberList" :value="score" :key="score">{{ score }}</option>
                  </CFormSelect>
                </CCol>
              </CRow>
              <CRow>
                <CCol class="mt-2">
                  <CFormLabel class="fw-bold">Saving Throws:</CFormLabel>
                </CCol>
                <CCol sm="auto">
                  <CFormSelect @change="setCharismaSave($event.target.value)" :modelValue="'false'">
                    <option v-for="proficiency in savingThrowLevels" :value="proficiency.proficient" :key="proficiency.proficient.toString">{{ proficiency.value }}</option>
                  </CFormSelect>
                </CCol>
              </CRow>
              <CRow>
                <CCol class="mt-2">
                  <CFormLabel class="fw-bold">Deception:</CFormLabel>
                </CCol>
                <CCol sm="auto">
                  <CFormSelect @change="setDeception(parseInt($event.target.value))" :modelValue="'0'">
                    <option v-for="profciency in skillProficiencyLevel" :value="profciency.level" :key="profciency.level">{{ profciency.value }}</option>
                  </CFormSelect>
                </CCol>
              </CRow>
              <CRow>
                <CCol class="mt-2">
                  <CFormLabel class="fw-bold">Intimidation:</CFormLabel>
                </CCol>
                <CCol sm="auto">
                  <CFormSelect @change="setIntimidation(parseInt($event.target.value))" :modelValue="'0'">
                    <option v-for="profciency in skillProficiencyLevel" :value="profciency.level" :key="profciency.level">{{ profciency.value }}</option>
                  </CFormSelect>
                </CCol>
              </CRow>
              <CRow>
                <CCol class="mt-2">
                  <CFormLabel class="fw-bold">Performance:</CFormLabel>
                </CCol>
                <CCol sm="auto">
                  <CFormSelect @change="setPerformance(parseInt($event.target.value))" :modelValue="'0'">
                    <option v-for="profciency in skillProficiencyLevel" :value="profciency.level" :key="profciency.level">{{ profciency.value }}</option>
                  </CFormSelect>
                </CCol>
              </CRow>
              <CRow>
                <CCol class="mt-2">
                  <CFormLabel class="fw-bold">Persuasion:</CFormLabel>
                </CCol>
                <CCol sm="auto">
                  <CFormSelect @change="setPersuasion(parseInt($event.target.value))" :modelValue="'0'">
                    <option v-for="profciency in skillProficiencyLevel" :value="profciency.level" :key="profciency.level">{{ profciency.value }}</option>
                  </CFormSelect>
                </CCol>
              </CRow>
            </CCardBody>
          </CCard>
        </CCol>

        <!-- Resolve -->
        <CCol xs="6" sm="4" v-if="madness">
          <CCard class="mt-2">
            <CCardHeader>Resolve (RES)</CCardHeader>
            <CCardBody>
              <CRow>
                <CCol  class="mt-2">
                  <CFormLabel class="fw-bold">Score:</CFormLabel>
                </CCol>
                <CCol sm="auto">
                  <CFormSelect @change="setResolve(parseInt($event.target.value))" :modelValue="playerCharacter.resolve?.score.toString()">
                    <option v-for="score in numberList" :value="score" :key="score">{{ score }}</option>
                  </CFormSelect>
                </CCol>
              </CRow>
            </CCardBody>
          </CCard>
        </CCol>
      </CRow>

      <CButton color="dark" type="submit" class="btn btn-primary mt-2">Create {{ playerCharacter.name }}</CButton>
    </CForm>
  </div>

  <!-- Error Toast -->
  <CToaster placement="top-end">
    <CToast v-for="toast in errorToasts" :key="toast" color="danger">
      <CToastHeader closeButton>
        <span class="me-auto fw-bold">{{ toast.title }}</span>
      </CToastHeader>
      <CToastBody>
        {{ toast.body }}
      </CToastBody>
    </CToast>
  </CToaster>

  <!-- Success Toast -->
  <CToaster placement="top-end">
    <CToast v-for="toast in successToasts" :key="toast" color="success">
      <CToastHeader closeButton>
        <span class="me-auto fw-bold">{{ toast.title }}</span>
      </CToastHeader>
      <CToastBody>
        {{ toast.body }}
      </CToastBody>
    </CToast>
  </CToaster>

</template>

<script lang="ts">
  import { defineComponent } from 'vue'
  import { storeToRefs } from 'pinia'
  import { useUserStore } from '@/stores/UserStore'
  import { useCampaignStore } from '@/stores/CampaignStore'
  import { CButton, CCard, CCardBody, CCardHeader, CCol, CForm, CFormInput, CFormLabel, CFormSelect, CRow, CToast, CToastBody, CToaster, CToastHeader } from '@coreui/vue'
  import { CharacterClass, ClassLevel, PlayerCharacter, Resolve } from '@/models/PlayerCharacter'
  import agent from '@/api/agent';
  
  export default defineComponent({
    name: "CharacterCreator",
    components: { CFormSelect, CFormLabel, CRow, CCol, CButton, CCard, CCardBody, CCardHeader, CForm, CFormInput, CToast, CToaster, CToastHeader, CToastBody },
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
        madness: useCampaignStore().activeCampaign?.madness,
        classList: [] as CharacterClass[],
        numberList: [1, 2, 3, 4, 5, 6, 7, 8, 9, 10,
                    11, 12, 13, 14, 15, 16, 17, 18, 19, 20],
        savingThrowLevels: [
          { proficient: false, value: "None" },
          { proficient: true, value: "Proficient" }
        ],
        skillProficiencyLevel: [
          { level: 0, value: "None"},
          { level: 1, value: "Proficient"},
          { level: 2, value: "Expertise"},
        ],
        multiClassList: [] as number[],
        multiClassLevelList: [] as number[],
        playerCharacter: { } as PlayerCharacter,
        errorToasts: [] as any,
        successToasts: [] as any,
        key: 0
      }
    },
    methods: {
      setCampaign(id: number) {
        this.playerCharacter.campaignId = id;

        if (this.playerCharacter.campaignId === 0) {
          this.madness = false;
          this.playerCharacter.resolve = null;
          return;
        }

        this.campaign.campaignList.value.forEach(campaign => {
          if (campaign.id === this.playerCharacter.campaignId) {
            this.madness = campaign.madness;
            this.playerCharacter.resolve = { score: 10, proficient: false } as Resolve;
          }
        });
      },
      async getClassList() {
        await agent.playerCharacter.getClassList().then((data) => {
          this.classList = data;
        })
      },
      setClass(id: number) {
        this.playerCharacter.classLevelList[0].characterClass.id = id;
      },
      setBaseLevel(level: number) {
        this.playerCharacter.classLevelList[0].levels = level;
      },
      addMulticlass() {
        if (this.multiClassList.length < 12) {
          this.multiClassList.push(0);
        }
      },
      removeMulticlass(index: number) {
        this.multiClassList.splice(index, 1);
        this.multiClassLevelList.splice(index, 1);
      },
      setMultiClass(id: number, index: number) {
        this.multiClassList[index] = id;
      },
      setMultiClassLevel(level: number, index: number) {
        this.multiClassLevelList[index] = level;
      },
      setStrength(score: number) {
        this.playerCharacter.strength.score = score;
      },
      setStrengthSave(proficient: string) {
        this.playerCharacter.strength.proficient = proficient === "true";
      },
      setAthletics(level: number) {
        this.playerCharacter.strength.athletics = level;
      },
      setDexterity(score: number) {
        this.playerCharacter.dexterity.score = score;
      },
      setDexteritySave(proficient: string) {
        this.playerCharacter.dexterity.proficient = proficient === "true";
      },
      setAcrobatics(level: number) {
        this.playerCharacter.dexterity.acrobatics = level;
      },
      setSleightOfHand(level: number) {
        this.playerCharacter.dexterity.sleightOfHand = level;
      },
      setStealth(level: number) {
        this.playerCharacter.dexterity.stealth = level;
      },
      setConstitution(score: number) {
        this.playerCharacter.constitution.score = score;
      },
      setConstitutionSave(proficient: string) {
        this.playerCharacter.constitution.proficient = proficient === "true";
      },
      setIntelligence(score: number) {
        this.playerCharacter.intelligence.score = score;
      },
      setIntelligenceSave(proficient: string) {
        this.playerCharacter.intelligence.proficient = proficient === "true";
      },
      setArcana(level: number) {
        this.playerCharacter.intelligence.arcana = level;
      },
      setHistory(level: number) {
        this.playerCharacter.intelligence.history = level;
      },
      setInvestigation(level: number) {
        this.playerCharacter.intelligence.investigation = level;
      },
      setNature(level: number) {
        this.playerCharacter.intelligence.nature = level;
      },
      setReligion(level: number) {
        this.playerCharacter.intelligence.religion = level;
      },
      setWisdom(score: number) {
        this.playerCharacter.wisdom.score = score;
      },
      setWisdomSave(proficient: string) {
        this.playerCharacter.wisdom.proficient = proficient === "true";
      },
      setAnimalHandling(level: number) {
        this.playerCharacter.wisdom.animalHandling = level;
      },
      setInsight(level: number) {
        this.playerCharacter.wisdom.insight = level;
      },
      setMedicine(level: number) {
        this.playerCharacter.wisdom.medicine = level;
      },
      setPerception(level: number) {
        this.playerCharacter.wisdom.perception = level;
      },
      setSurvival(level: number) {
        this.playerCharacter.wisdom.survival = level;
      },
      setCharisma(score: number) {
        this.playerCharacter.charisma.score = score;
      },
      setCharismaSave(proficient: string) {
        this.playerCharacter.charisma.proficient = proficient === "true";
      },
      setDeception(level: number) {
        this.playerCharacter.charisma.deception = level;
      },
      setIntimidation(level: number) {
        this.playerCharacter.charisma.intimidation = level;
      },
      setPerformance(level: number) {
        this.playerCharacter.charisma.performance = level;
      },
      setPersuasion(level: number) {
        this.playerCharacter.charisma.persuasion = level;
      },
      setResolve(score: number) {
        if (this.playerCharacter.resolve != null) {
          this.playerCharacter.resolve.score = score;
        }
      },
      initPlayerCharacter() {
        this.playerCharacter = {
          campaignId: 0,
          userId: this.user.user.value?.id,
          name: "",
          ac: 10,
          acBonus: 0,
          temporaryHitPoints: 0,
          initiativeBonus: 0,
          rolledInitiative: 0,
          dead: false,
          deathSaveSuccesses: 0,
          deathSaveFailures: 0,
          stress: 0,
          stressStatus: { id: 1 },
          combatant: false,
          strength: { 
            score: 10,
            proficient: false,
            athletics: 0
          },
          dexterity: { 
            score: 10,
            proficient: false,
            acrobatics: 0,
            sleightOfHand: 0,
            stealth: 0
          },
          constitution: { 
            score: 10,
            proficient: false
          },
          intelligence: { 
            score: 10,
            proficient: false,
            arcana: 0,
            history: 0,
            investigation: 0,
            nature: 0,
            religion: 0
          },
          wisdom: { 
            score: 10,
            proficient: false,
            animalHandling: 0,
            insight: 0,
            medicine: 0,
            perception: 0,
            survival: 0
          },
          charisma: { 
            score: 10,
            proficient: false,
            deception: 0,
            intimidation: 0,
            performance: 0,
            persuasion: 0
          },
          resolve: null,
          classLevelList: [
            { baseClass: true, levels: 1, characterClass: { id: 0 } }
        ]
        } as PlayerCharacter;
      },
      async submit() {
        let error = false;
        //campiagn validator
        if (this.playerCharacter.campaignId === 0) {
          error = true;
          this.errorToasts.push(
            { title: "Submission Error:", body: "No campaign selected"}
          );
        }

        if (this.playerCharacter.name.trim() === "") {
          error = true;
          this.errorToasts.push(
            { title: "Submission Error:", body: "Name cannot be blank"}
          );
        }

        //validate base class
        if (this.playerCharacter.classLevelList[0].characterClass.id === 0) {
          error = true;
          this.errorToasts.push(
            { title: "Submission Error:", body: "No base class selected"}
          );
        }

        //validate each multiclass
        this.multiClassList.forEach((multiclass, index) => {
          if (multiclass === 0) {
            error = true;
            this.errorToasts.push(
              { title: "Submission Error:", body: "No multiclass selected"}
            );
          }

          //check for duplicate multiclasses, ignore matches where no class was selected
          if (this.multiClassList.includes(multiclass, index + 1) && multiclass != 0) {
            error = true;
            this.errorToasts.push(
              { title: "Submission Error:", body: "Duplicate multiclass selected"}
            );
          }

            //compare to base class, ignore matches where no class was selected
          if (multiclass === this.playerCharacter.classLevelList[0].characterClass.id && multiclass != 0) {
            error = true;
            this.errorToasts.push(
              { title: "Submission Error:", body: "Multiclass cannot match base class"}
            );
          }
        })

        //validate levels
        let totalLevels = this.playerCharacter.classLevelList[0].levels;

        this.multiClassLevelList.forEach(classLevel => {
          totalLevels += classLevel;
        });

        if (totalLevels > 20) {
          error = true;
          this.errorToasts.push(
            { title: "Submission Error:", body: "Total class levels cannot exceed 20"}
          );
        }

        if (!error) {
          this.multiClassList.forEach((multiclass) => {
            var newClassLevel: ClassLevel;
            newClassLevel = {
              id: 0,
              baseClass: false,
              levels: this.multiClassLevelList[this.multiClassList.indexOf(multiclass)],
              usedHitDice: 0,
              thirdCaster: false,
              characterClass: {
                id: multiclass,
                name: "",
                hitDie: 0
              }
            }

            this.playerCharacter.classLevelList.push(newClassLevel);
          });

          await agent.playerCharacter.addPlayerCharacter(this.playerCharacter).then(() => {
            this.successToasts.push(
              { title: "Success!", body: `${this.playerCharacter.name} created`}
            );
            this.key += 1;
            this.initPlayerCharacter();
          });
        }
      }
    },
    mounted() {
      this.getCampaignList();
      this.getActiveCampaign();
      this.getClassList();
      this.initPlayerCharacter();
    } 
  });
</script>