<template>
    <h2 class="mt-2">Character Creator</h2>

    <!-- Campiagn Selector -->
    <CRow>
        <CCol sm="3" md="2" class="mt-2">
            <CFormLabel for="campaign" class="fw-bold align-text-bottom">Campaign:</CFormLabel>
        </CCol>
        <CCol sm="8" md="6">
            <CFormSelect @change="setCampaign(parseInt($event.target.value))" id="campaign">
                <option :value="0">Select a Campaign</option>
                <option v-for="(item) in campaign.campaignList.value" :value="item.id" :key="item.id">{{ item.name }}</option>
            </CFormSelect>
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

    <!-- Ability Scores - STR, DEX, CON -->
    <CRow>
        <CCol>
            <CCard class="mt-2">
                <CCardHeader>Strength (STR)</CCardHeader>
                <CCardBody>
                    <CRow>
                        <CCol class="mt-2">
                            <CFormLabel class="fw-bold">Score:</CFormLabel>
                        </CCol>
                        <CCol>
                            <CFormSelect @change="setStrength(parseInt($event.target.value))">
                                <option v-for="score in numberList" :value="score" :key="score">{{ score }}</option>
                            </CFormSelect>
                        </CCol>
                    </CRow>
                </CCardBody>
            </CCard>
        </CCol>
        <CCol>
            <CCard class="mt-2">
                <CCardHeader>Dexterity (DEX)</CCardHeader>
                <CCardBody>
                    <CRow>
                        <CCol class="mt-2">
                            <CFormLabel class="fw-bold">Score:</CFormLabel>
                        </CCol>
                        <CCol>
                            <CFormSelect @change="setDexterity(parseInt($event.target.value))">
                                <option v-for="score in numberList" :value="score" :key="score">{{ score }}</option>
                            </CFormSelect>
                        </CCol>
                    </CRow>
                </CCardBody>
            </CCard>
        </CCol>
        <CCol>
            <CCard class="mt-2">
                <CCardHeader>Constitution (CON)</CCardHeader>
                <CCardBody>
                    <CRow>
                        <CCol class="mt-2">
                            <CFormLabel class="fw-bold">Score:</CFormLabel>
                        </CCol>
                        <CCol>
                            <CFormSelect @change="setConstitution(parseInt($event.target.value))">
                                <option v-for="score in numberList" :value="score" :key="score">{{ score }}</option>
                            </CFormSelect>
                        </CCol>
                    </CRow>
                </CCardBody>
            </CCard>
        </CCol>
    </CRow>

    <!-- Ability Scores - INT, WIS, CHA -->
    <CRow>
        <CCol>
            <CCard class="mt-2">
                <CCardHeader>Intelligence (INT)</CCardHeader>
                <CCardBody>
                    <CRow>
                        <CCol class="mt-2">
                            <CFormLabel class="fw-bold">Score:</CFormLabel>
                        </CCol>
                        <CCol>
                            <CFormSelect @change="setIntelligence(parseInt($event.target.value))">
                                <option v-for="score in numberList" :value="score" :key="score">{{ score }}</option>
                            </CFormSelect>
                        </CCol>
                    </CRow>
                </CCardBody>
            </CCard>
        </CCol>
        <CCol>
            <CCard class="mt-2">
                <CCardHeader>Wisdom (WIS)</CCardHeader>
                <CCardBody>
                    <CRow>
                        <CCol class="mt-2">
                            <CFormLabel class="fw-bold">Score:</CFormLabel>
                        </CCol>
                        <CCol>
                            <CFormSelect @change="setWisdom(parseInt($event.target.value))">
                                <option v-for="score in numberList" :value="score" :key="score">{{ score }}</option>
                            </CFormSelect>
                        </CCol>
                    </CRow>
                </CCardBody>
            </CCard>
        </CCol>
        <CCol>
            <CCard class="mt-2">
                <CCardHeader>Charisma (CHA)</CCardHeader>
                <CCardBody>
                    <CRow>
                        <CCol class="mt-2">
                            <CFormLabel class="fw-bold">Score:</CFormLabel>
                        </CCol>
                        <CCol>
                            <CFormSelect @change="setCharisma(parseInt($event.target.value))">
                                <option v-for="score in numberList" :value="score" :key="score">{{ score }}</option>
                            </CFormSelect>
                        </CCol>
                    </CRow>
                </CCardBody>
            </CCard>
        </CCol>
    </CRow>

    <!-- Resolve -->
    <CRow v-if="madness">
        <CCol sm="4">
            <CCard class="mt-2">
                <CCardHeader>Resolve (RES)</CCardHeader>
                <CCardBody>
                    <CRow>
                        <CCol class="mt-2">
                            <CFormLabel class="fw-bold">Score:</CFormLabel>
                        </CCol>
                        <CCol>
                            <CFormSelect @change="setResolve(parseInt($event.target.value))">
                                <option v-for="score in numberList" :value="score" :key="score">{{ score }}</option>
                            </CFormSelect>
                        </CCol>
                    </CRow>
                </CCardBody>
            </CCard>
        </CCol>
    </CRow>

</template>

<script lang="ts">
    import { defineComponent } from 'vue'
    import { storeToRefs } from 'pinia'
    import { useUserStore } from '@/stores/UserStore'
    import { useCampaignStore } from '@/stores/CampaignStore'
    import { CButton, CCard, CCardBody, CCardHeader, CCol, CFormLabel, CFormSelect, CRow } from '@coreui/vue'
    import { CharacterClass, PlayerCharacter, Resolve } from '@/models/PlayerCharacter'
    import agent from '@/api/agent';
    
    export default defineComponent ({
        name: "CharacterCreator",
        components: { CFormSelect, CFormLabel, CRow, CCol, CButton, CCard, CCardBody, CCardHeader },
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
                madness: false as boolean,
                classList: [] as CharacterClass[],
                baseClass: 0,
                baseLevel: 1,
                numberList: [1, 2, 3, 4, 5, 6, 7, 8, 9, 10,
                            11, 12, 13, 14, 15, 16, 17, 18, 19, 20],
                multiClassList: [] as number[],
                multiClassLevelList: [] as number[],
                playerCharacter: { } as PlayerCharacter
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
                        this.playerCharacter.resolve = { } as Resolve;
                        this.playerCharacter.resolve.score = 10;
                    }
                });
            },
            async getClassList() {
                await agent.playerCharacter.getClassList().then((data) => {
                    this.classList = data;
                })
            },
            setClass(id: number) {
                this.baseClass = id;
            },
            setBaseLevel(level: number) {
                this.playerCharacter.classLevelList[0].levels = level;
                console.log(this.playerCharacter);
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
            setDexterity(score: number) {
                this.playerCharacter.dexterity.score = score;
            },
            setConstitution(score: number) {
                this.playerCharacter.consitution.score = score;
            },
            setIntelligence(score: number) {
                this.playerCharacter.intelligence.score = score;
            },
            setWisdom(score: number) {
                this.playerCharacter.wisdom.score = score;
            },
            setCharisma(score: number) {
                this.playerCharacter.charisma.score = score;
            },
            setResolve(score: number) {
                if (this.playerCharacter.resolve != null) {
                    this.playerCharacter.resolve.score = score;
                }
            }
        },
        mounted() {
            this.getCampaignList();
            this.getActiveCampaign();
            this.getClassList();
            this.playerCharacter = {
                campaignId: 0,
                userId: this.user.user.value?.id,
                name: "Name",
                ac: 10,
                initiativeBonus: 0,
                rolledInitiative: 0,
                dead: false,
                combatant: false,
                strength: { score: 10 },
                dexterity: { score: 10 },
                consitution: { score: 10 },
                intelligence: { score: 10 },
                wisdom: { score: 10 },
                charisma: { score: 10 },
                resolve: null,
                classLevelList: [
                    { firstLevel: true, levels: 1, characterClass: { id: 0 }}
                ]
            } as PlayerCharacter;
        },
        
    });
</script>