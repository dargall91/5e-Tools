<template>
  <h2 class="mt-2">Manage Characters</h2>

  <CRow>
    <CCol sm="3" md="2" class="mt-2">
      <CFormLabel for="campaign" class="fw-bold align-text-bottom">Campaign:</CFormLabel>
    </CCol>
    <CCol sm="8" md="6">
      <CFormSelect @change="onCampaignChanged(parseInt($event.target.value))" id="campaign" :model-value="campaignStore.selectedCampaign.value.id.toString()">
        <option :value="0">Select a Campaign</option>
        <option v-for="(item) in campaignStore.campaignList.value" :value="item.id" :key="item.id">{{ item.name }}</option>
      </CFormSelect>
    </CCol>
  </CRow>

  {{ characterStore.characterList }}
  <CAccordion class="mt-2" accordion-color="black" always-open>
    <CAccordionItem v-for="(character, characterIndex) in characterStore.characterList.value" :key="characterIndex" :item-key="characterIndex">
      <CAccordionHeader>
        {{ character.name }}
      </CAccordionHeader>
      <CAccordionBody>
        <CRow class="mt-1" v-for="classLevel, classLevelIndex in character.classLevelList" :key="classLevel.id">
          <CCol xs="6" md="4" lg="3" v-if="classLevel.baseClass">
            <strong>Base Class:</strong> {{ classLevel.characterClass.name }}
          </CCol>
          <CCol xs="6" md="5" lg="3" v-else>
            <strong>Multiclass:</strong> {{ classLevel.characterClass.name }}
          </CCol>
          <CCol xs="3" lg="2">
            <strong>Level:</strong> {{ classLevel.levels }}
          </CCol>
          <CCol xs="12" md="5" lg="7">
            <strong>Used Hit Dice:</strong> {{ classLevel.usedHitDice }} / {{ classLevel.levels }}d{{ classLevel.characterClass.hitDie }}
            <CButton size="sm" color="danger" @click="characterStoreFunctions.adjustHitDie(characterIndex, classLevelIndex, -1)">-1</CButton>
            <CButton size="sm" color="success" @click="characterStoreFunctions.adjustHitDie(characterIndex, classLevelIndex, 1)">+1</CButton>
          </CCol>
        </CRow>

        <CButton class="me-1" size="sm" color="dark">Long Rest</CButton>
        <CButton size="sm" color="dark">Level Up</CButton>

        <CRow class="mt-1">
          <CCol xs="7" md="5" lg="4">
            <strong>Hit Points:</strong> {{ characterStoreFunctions.getMaxHitPoints(characterIndex, campaignStore.selectedCampaign.value) - character.damage }} / {{ characterStoreFunctions.getMaxHitPoints(characterIndex, campaignStore.selectedCampaign.value) }}
          </CCol>
          <CCol xs="5">
            <strong>Temporary HP:</strong> {{ character.temporaryHitPoints }}
          </CCol>
        </CRow>

        <CRow>
          <CCol xs="7" md="5" lg="4">
            <CButton size="sm" color="danger" @click="characterStoreFunctions.adjustDamage(characterIndex, 10, campaignStore.selectedCampaign.value)">-10</CButton>
            <CButton size="sm" color="danger" @click="characterStoreFunctions.adjustDamage(characterIndex, 5, campaignStore.selectedCampaign.value)">-5</CButton>
            <CButton size="sm" color="danger" @click="characterStoreFunctions.adjustDamage(characterIndex, 1, campaignStore.selectedCampaign.value)">-1</CButton>
            <CButton size="sm" color="success" @click="characterStoreFunctions.adjustDamage(characterIndex, -1, campaignStore.selectedCampaign.value)">+1</CButton>
            <CButton size="sm" color="success" @click="characterStoreFunctions.adjustDamage(characterIndex, -5, campaignStore.selectedCampaign.value)">+5</CButton>
            <CButton size="sm" color="success" @click="characterStoreFunctions.adjustDamage(characterIndex, -10, campaignStore.selectedCampaign.value)">+10</CButton>
          </CCol>
          <CCol xs="5">
            <CButton size="sm" color="danger" @click="characterStoreFunctions.adjustTemporyHitPoints(characterIndex, -5)">-5</CButton>
            <CButton size="sm" color="danger" @click="characterStoreFunctions.adjustTemporyHitPoints(characterIndex, -1)">-1</CButton>
            <CButton size="sm" color="success" @click="characterStoreFunctions.adjustTemporyHitPoints(characterIndex, 1)">+1</CButton>
            <CButton size="sm" color="success" @click="characterStoreFunctions.adjustTemporyHitPoints(characterIndex, 5)">+5</CButton>
          </CCol>
        </CRow>

        <CRow class="mt-1">
          <CCol xs="5" sm="4" md="3" lg="2">
            <strong>AC:</strong> {{ character.ac }} 
            <CButton size="sm" color="danger" @click="characterStoreFunctions.adjustAc(characterIndex, -1)">-1</CButton>
            <CButton size="sm" color="success" @click="characterStoreFunctions.adjustAc(characterIndex, 1)">+1</CButton>
          </CCol>
          <CCol xs="7" sm="5" md="4" lg="3">
            <strong>AC Bonuses:</strong> {{ character.acBonus }} 
            <CButton size="sm" color="danger" @click="characterStoreFunctions.adjustAcBonus(characterIndex, -1)">-1</CButton>
            <CButton size="sm" color="success" @click="characterStoreFunctions.adjustAcBonus(characterIndex, 1)">+1</CButton>
          </CCol>
          <CCol class="mt-1" xs="12" sm="3">
            <strong>Total AC:</strong> {{ character.ac + character.acBonus }} 
          </CCol>
        </CRow>

        <CRow class="mt-1">
          <CCol md="12" lg="4" xl="3">
            <strong>Death Saving Throws: </strong>
            <CButton size="sm" color="dark" @click="characterStoreFunctions.resetDeathSaves(characterIndex)">Reset</CButton>
          </CCol>
          <CCol xs="6" lg="3" xxl="2">
            <strong>Successes:</strong> {{ character.deathSaveSuccesses }}
            <CButton size="sm" color="danger" @click="characterStoreFunctions.adjustDeathSaveSuccesses(characterIndex, -1)">-1</CButton>
            <CButton size="sm" color="success" @click="characterStoreFunctions.adjustDeathSaveSuccesses(characterIndex, 1)">+1</CButton>
          </CCol>
          <CCol xs="6" lg="3" xl="2">
            <strong>Failures:</strong> {{ character.deathSaveFailures }}
            <CButton size="sm" color="danger" @click="characterStoreFunctions.adjustDeathSaveFailures(characterIndex, -1)">-1</CButton>
            <CButton size="sm" color="success" @click="characterStoreFunctions.adjustDeathSaveFailures(characterIndex, 1)">+1</CButton>
          </CCol>
        </CRow>

        <div  v-if="campaignStore.selectedCampaign.value.madness">
          <CRow class="mt-1">
            <CCol xs="4" md="4" lg="4">
              <strong>Stress:</strong> {{ character.stress }} / {{ characterStoreFunctions.getStressThreshold(characterIndex, campaignStore.selectedCampaign.value) }}
            </CCol>
            <!-- <CCol xs="4">
              <strong>Threshold:</strong> {{ getCharacterStore.getStressThreshold(characterIndex, campaignStore.selectedCampaign.value) }}
            </CCol>
            <CCol xs="4">
              <strong>Maximum:</strong> {{ getCharacterStore.getStressMaximum(characterIndex, campaignStore.selectedCampaign.value) }}
            </CCol> -->
          </CRow>

          <CRow>
            <CCol xs="7" md="5" lg="4">
              <CButton size="sm" color="danger" @click="characterStoreFunctions.adjustStress(characterIndex, -10, campaignStore.selectedCampaign.value)">-10</CButton>
              <CButton size="sm" color="danger" @click="characterStoreFunctions.adjustStress(characterIndex, -5, campaignStore.selectedCampaign.value)">-5</CButton>
              <CButton size="sm" color="danger" @click="characterStoreFunctions.adjustStress(characterIndex, -1, campaignStore.selectedCampaign.value)">-1</CButton>
              <CButton size="sm" color="success" @click="characterStoreFunctions.adjustStress(characterIndex, 1, campaignStore.selectedCampaign.value)">+1</CButton>
              <CButton size="sm" color="success" @click="characterStoreFunctions.adjustStress(characterIndex, 5, campaignStore.selectedCampaign.value)">+5</CButton>
              <CButton size="sm" color="success" @click="characterStoreFunctions.adjustStress(characterIndex, 10, campaignStore.selectedCampaign.value)">+10</CButton>
            </CCol>
          </CRow>
            
          <CCard class="mt-1" v-if="character.stressStatus.id > 1">
            <CCardHeader>
              {{ character.stressStatus.type }}: {{ character.stressStatus.name }}
            </CCardHeader>
            <CCardBody>
              <span v-html="character.stressStatus.description" />
            </CCardBody>
          </CCard>
        </div>

        <CAccordion class="mt-1">
          <CAccordionItem>
            <CAccordionHeader>
              Ability Scores
            </CAccordionHeader>
            <CAccordionBody>
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
                          <CFormSelect @change="characterStoreFunctions.setStrength(parseInt($event.target.value), characterIndex)" :modelValue="character.strength.score.toString()">
                            <option v-for="score in numberList" :value="score" :key="score">{{ score }}</option>
                          </CFormSelect>
                        </CCol>
                      </CRow>
                      <CRow>
                        <CCol class="mt-2">
                          <CFormLabel class="fw-bold">Saving Throws:</CFormLabel>
                        </CCol>
                        <CCol sm="auto">
                          <CFormSelect @change="characterStoreFunctions.setStrengthSave($event.target.value, characterIndex)" :modelValue="character.strength.proficient.toString()">
                            <option v-for="proficiency in savingThrowLevels" :value="proficiency.proficient" :key="proficiency.proficient.toString()">{{ proficiency.value }}</option>
                          </CFormSelect>
                        </CCol>
                      </CRow>
                      <CRow>
                        <CCol class="mt-2">
                          <CFormLabel class="fw-bold">Athletics:</CFormLabel>
                        </CCol>
                        <CCol sm="auto">
                          <CFormSelect @change="characterStoreFunctions.setAthletics(parseInt($event.target.value), characterIndex)" :modelValue="character.strength.athletics.toString()">
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
                          <CFormSelect @change="characterStoreFunctions.setDexterity(parseInt($event.target.value), characterIndex)" :modelValue="character.dexterity.score.toString()">
                            <option v-for="score in numberList" :value="score" :key="score">{{ score }}</option>
                          </CFormSelect>
                        </CCol>
                      </CRow>
                      <CRow>
                        <CCol class="mt-2">
                          <CFormLabel class="fw-bold">Saving Throws:</CFormLabel>
                        </CCol>
                        <CCol sm="auto">
                          <CFormSelect @change="characterStoreFunctions.setDexteritySave($event.target.value, characterIndex)" :modelValue="character.dexterity.proficient.toString()">
                            <option v-for="proficiency in savingThrowLevels" :value="proficiency.proficient" :key="proficiency.proficient.toString">{{ proficiency.value }}</option>
                          </CFormSelect>
                        </CCol>
                      </CRow>
                      <CRow>
                        <CCol class="mt-2">
                          <CFormLabel class="fw-bold">Acrobatics:</CFormLabel>
                        </CCol>
                        <CCol sm="auto">
                          <CFormSelect @change="characterStoreFunctions.setAcrobatics(parseInt($event.target.value), characterIndex)" :modelValue="character.dexterity.acrobatics.toString()">
                            <option v-for="profciency in skillProficiencyLevel" :value="profciency.level" :key="profciency.level">{{ profciency.value }}</option>
                          </CFormSelect>
                        </CCol>
                      </CRow>
                      <CRow>
                        <CCol class="mt-2">
                          <CFormLabel class="fw-bold">Sleight of Hand:</CFormLabel>
                        </CCol>
                        <CCol sm="auto">
                          <CFormSelect @change="characterStoreFunctions.setSleightOfHand(parseInt($event.target.value), characterIndex)" :modelValue="character.dexterity.sleightOfHand.toString()">
                            <option v-for="profciency in skillProficiencyLevel" :value="profciency.level" :key="profciency.level">{{ profciency.value }}</option>
                          </CFormSelect>
                        </CCol>
                      </CRow>
                      <CRow>
                        <CCol class="mt-2">
                          <CFormLabel class="fw-bold">Stealth:</CFormLabel>
                        </CCol>
                        <CCol sm="auto">
                          <CFormSelect @change="characterStoreFunctions.setStealth(parseInt($event.target.value), characterIndex)" :modelValue="character.dexterity.stealth.toString()">
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
                          <CFormSelect @change="characterStoreFunctions.setConstitution(parseInt($event.target.value), characterIndex)" :modelValue="character.constitution.score.toString()">
                            <option v-for="score in numberList" :value="score" :key="score">{{ score }}</option>
                          </CFormSelect>
                        </CCol>
                      </CRow>
                      <CRow>
                        <CCol class="mt-2">
                          <CFormLabel class="fw-bold">Saving Throws:</CFormLabel>
                        </CCol>
                        <CCol sm="auto">
                          <CFormSelect @change="characterStoreFunctions.setConstitutionSave($event.target.value, characterIndex)" :modelValue="character.constitution.proficient.toString()">
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
                      <CFormSelect @change="characterStoreFunctions.setIntelligence(parseInt($event.target.value), characterIndex)" :modelValue="character.intelligence.score.toString()">
                        <option v-for="score in numberList" :value="score" :key="score">{{ score }}</option>
                      </CFormSelect>
                    </CCol>
                  </CRow>
                  <CRow>
                    <CCol class="mt-2">
                      <CFormLabel class="fw-bold">Saving Throws:</CFormLabel>
                    </CCol>
                    <CCol sm="auto">
                      <CFormSelect @change="characterStoreFunctions.setIntelligenceSave($event.target.value, characterIndex)" :modelValue="character.intelligence.proficient.toString()">
                        <option v-for="proficiency in savingThrowLevels" :value="proficiency.proficient" :key="proficiency.proficient.toString">{{ proficiency.value }}</option>
                      </CFormSelect>
                    </CCol>
                  </CRow>
                  <CRow>
                    <CCol class="mt-2">
                      <CFormLabel class="fw-bold">Arcana:</CFormLabel>
                    </CCol>
                    <CCol sm="auto">
                      <CFormSelect @change="characterStoreFunctions.setArcana(parseInt($event.target.value), characterIndex)" :modelValue="character.intelligence.arcana.toString()">
                        <option v-for="profciency in skillProficiencyLevel" :value="profciency.level" :key="profciency.level">{{ profciency.value }}</option>
                      </CFormSelect>
                    </CCol>
                  </CRow>
                  <CRow>
                    <CCol class="mt-2">
                      <CFormLabel class="fw-bold">History:</CFormLabel>
                    </CCol>
                    <CCol sm="auto">
                      <CFormSelect @change="characterStoreFunctions.setHistory(parseInt($event.target.value), characterIndex)" :modelValue="character.intelligence.history.toString()">
                        <option v-for="profciency in skillProficiencyLevel" :value="profciency.level" :key="profciency.level">{{ profciency.value }}</option>
                      </CFormSelect>
                    </CCol>
                  </CRow>
                  <CRow>
                    <CCol class="mt-2">
                      <CFormLabel class="fw-bold">Investigation:</CFormLabel>
                    </CCol>
                    <CCol sm="auto">
                      <CFormSelect @change="characterStoreFunctions.setInvestigation(parseInt($event.target.value), characterIndex)" :modelValue="character.intelligence.investigation.toString()">
                        <option v-for="profciency in skillProficiencyLevel" :value="profciency.level" :key="profciency.level">{{ profciency.value }}</option>
                      </CFormSelect>
                    </CCol>
                  </CRow>
                  <CRow>
                    <CCol class="mt-2">
                      <CFormLabel class="fw-bold">Nature:</CFormLabel>
                    </CCol>
                    <CCol sm="auto">
                      <CFormSelect @change="characterStoreFunctions.setNature(parseInt($event.target.value), characterIndex)" :modelValue="character.intelligence.nature.toString()">
                        <option v-for="profciency in skillProficiencyLevel" :value="profciency.level" :key="profciency.level">{{ profciency.value }}</option>
                      </CFormSelect>
                    </CCol>
                  </CRow>
                  <CRow>
                    <CCol class="mt-2">
                      <CFormLabel class="fw-bold">Religion:</CFormLabel>
                    </CCol>
                    <CCol sm="auto">
                      <CFormSelect @change="characterStoreFunctions.setReligion(parseInt($event.target.value), characterIndex)" :modelValue="character.intelligence.religion.toString()">
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
                          <CFormSelect @change="characterStoreFunctions.setWisdom(parseInt($event.target.value), characterIndex)" :modelValue="character.wisdom.score.toString()">
                            <option v-for="score in numberList" :value="score" :key="score">{{ score }}</option>
                          </CFormSelect>
                        </CCol>
                      </CRow>
                      <CRow>
                        <CCol class="mt-2">
                          <CFormLabel class="fw-bold">Saving Throws:</CFormLabel>
                        </CCol>
                        <CCol sm="auto">
                          <CFormSelect @change="characterStoreFunctions.setWisdomSave($event.target.value, characterIndex)" :modelValue="character.wisdom.proficient.toString()">
                            <option v-for="proficiency in savingThrowLevels" :value="proficiency.proficient" :key="proficiency.proficient.toString">{{ proficiency.value }}</option>
                          </CFormSelect>
                        </CCol>
                      </CRow>
                      <CRow>
                        <CCol class="mt-2">
                          <CFormLabel class="fw-bold">Animal Handling:</CFormLabel>
                        </CCol>
                        <CCol sm="auto">
                          <CFormSelect @change="characterStoreFunctions.setAnimalHandling(parseInt($event.target.value), characterIndex)" :modelValue="character.wisdom.animalHandling.toString()">
                            <option v-for="profciency in skillProficiencyLevel" :value="profciency.level" :key="profciency.level">{{ profciency.value }}</option>
                          </CFormSelect>
                        </CCol>
                      </CRow>
                      <CRow>
                        <CCol class="mt-2">
                          <CFormLabel class="fw-bold">Insight:</CFormLabel>
                        </CCol>
                        <CCol sm="auto">
                          <CFormSelect @change="characterStoreFunctions.setInsight(parseInt($event.target.value), characterIndex)" :modelValue="character.wisdom.insight.toString()">
                            <option v-for="profciency in skillProficiencyLevel" :value="profciency.level" :key="profciency.level">{{ profciency.value }}</option>
                          </CFormSelect>
                        </CCol>
                      </CRow>
                      <CRow>
                        <CCol class="mt-2">
                          <CFormLabel class="fw-bold">Medicine:</CFormLabel>
                        </CCol>
                        <CCol sm="auto">
                          <CFormSelect @change="characterStoreFunctions.setMedicine(parseInt($event.target.value), characterIndex)" :modelValue="character.wisdom.medicine.toString()">
                            <option v-for="profciency in skillProficiencyLevel" :value="profciency.level" :key="profciency.level">{{ profciency.value }}</option>
                          </CFormSelect>
                        </CCol>
                      </CRow>
                      <CRow>
                        <CCol class="mt-2">
                          <CFormLabel class="fw-bold">Survival:</CFormLabel>
                        </CCol>
                        <CCol sm="auto">
                          <CFormSelect @change="characterStoreFunctions.setSurvival(parseInt($event.target.value), characterIndex)" :modelValue="character.wisdom.survival.toString()">
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
                          <CFormSelect @change="characterStoreFunctions.setCharisma(parseInt($event.target.value), characterIndex)" :modelValue="character.charisma.score.toString()">
                            <option v-for="score in numberList" :value="score" :key="score">{{ score }}</option>
                          </CFormSelect>
                        </CCol>
                      </CRow>
                      <CRow>
                        <CCol class="mt-2">
                          <CFormLabel class="fw-bold">Saving Throws:</CFormLabel>
                        </CCol>
                        <CCol sm="auto">
                          <CFormSelect @change="characterStoreFunctions.setCharismaSave($event.target.value, characterIndex)" :modelValue="character.charisma.proficient.toString()">
                            <option v-for="proficiency in savingThrowLevels" :value="proficiency.proficient" :key="proficiency.proficient.toString">{{ proficiency.value }}</option>
                          </CFormSelect>
                        </CCol>
                      </CRow>
                      <CRow>
                        <CCol class="mt-2">
                          <CFormLabel class="fw-bold">Deception:</CFormLabel>
                        </CCol>
                        <CCol sm="auto">
                          <CFormSelect @change="characterStoreFunctions.setDeception(parseInt($event.target.value), characterIndex)" :modelValue="character.charisma.deception.toString()">
                            <option v-for="profciency in skillProficiencyLevel" :value="profciency.level" :key="profciency.level">{{ profciency.value }}</option>
                          </CFormSelect>
                        </CCol>
                      </CRow>
                      <CRow>
                        <CCol class="mt-2">
                          <CFormLabel class="fw-bold">Intimidation:</CFormLabel>
                        </CCol>
                        <CCol sm="auto">
                          <CFormSelect @change="characterStoreFunctions.setIntimidation(parseInt($event.target.value), characterIndex)" :modelValue="character.charisma.intimidation.toString()">
                            <option v-for="profciency in skillProficiencyLevel" :value="profciency.level" :key="profciency.level">{{ profciency.value }}</option>
                          </CFormSelect>
                        </CCol>
                      </CRow>
                      <CRow>
                        <CCol class="mt-2">
                          <CFormLabel class="fw-bold">Performance:</CFormLabel>
                        </CCol>
                        <CCol sm="auto">
                          <CFormSelect @change="characterStoreFunctions.setPerformance(parseInt($event.target.value), characterIndex)" :modelValue="character.charisma.performance.toString()">
                            <option v-for="profciency in skillProficiencyLevel" :value="profciency.level" :key="profciency.level">{{ profciency.value }}</option>
                          </CFormSelect>
                        </CCol>
                      </CRow>
                      <CRow>
                        <CCol class="mt-2">
                          <CFormLabel class="fw-bold">Persuasion:</CFormLabel>
                        </CCol>
                        <CCol sm="auto">
                          <CFormSelect @change="characterStoreFunctions.setPersuasion(parseInt($event.target.value), characterIndex)" :modelValue="character.charisma.persuasion.toString()">
                            <option v-for="profciency in skillProficiencyLevel" :value="profciency.level" :key="profciency.level">{{ profciency.value }}</option>
                          </CFormSelect>
                        </CCol>
                      </CRow>
                    </CCardBody>
                  </CCard>
                </CCol>

                <!-- Resolve -->
                <CCol xs="6" sm="4" v-if="campaignStore.selectedCampaign.value.madness">
                  <CCard class="mt-2">
                    <CCardHeader>Resolve (RES)</CCardHeader>
                    <CCardBody>
                      <CRow>
                        <CCol  class="mt-2">
                          <CFormLabel class="fw-bold">Score:</CFormLabel>
                        </CCol>
                        <CCol sm="auto">
                          <CFormSelect @change="characterStoreFunctions.setResolve(parseInt($event.target.value), characterIndex)" :modelValue="character.resolve?.score.toString()">
                            <option v-for="score in numberList" :value="score" :key="score">{{ score }}</option>
                          </CFormSelect>
                        </CCol>
                      </CRow>
                    </CCardBody>
                  </CCard>
                </CCol>
              </CRow>
            </CAccordionBody>
          </CAccordionItem>
        </CAccordion>
        
      </CAccordionBody>
    </CAccordionItem>
  </CAccordion>
</template>

<script lang="ts">
  import { defineComponent } from 'vue'
  import { storeToRefs } from 'pinia'
  import { useUserStore } from '@/stores/UserStore'
  import { useCampaignStore } from '@/stores/CampaignStore'
  import { useCharacterStore } from '@/stores/CharacterStore'
  import { CAccordion, CAccordionBody, CAccordionHeader, CAccordionItem, CButton, CCard, CCardBody, CCardHeader, CCol, CFormLabel, CFormSelect, CRow } from '@coreui/vue'
  import { CharacterClass } from '@/models/PlayerCharacter'
  
  export default defineComponent({
    name: "ManageCharacters",
    components: { CFormSelect, CAccordion, CRow, CCol, CFormLabel, CAccordionHeader, CAccordionBody, CAccordionItem, CButton, CCard, CCardHeader, CCardBody },
    setup() {
      return {
        userStore: storeToRefs(useUserStore()),
        campaignStore: storeToRefs(useCampaignStore()),
        characterStore: storeToRefs(useCharacterStore())
      };
    },
    data() {
      return {
        getCampaignList: useCampaignStore().getCampaignList,
        getActiveCampaign: useCampaignStore().getActiveCampaign,
        characterStoreFunctions: useCharacterStore(),
        getCharacterList: useCharacterStore().getCharacterList,
        setSelectedCampaign: useCampaignStore().setSelectedCampaign,
        clearCharacterList: useCharacterStore().clearCharacterList,
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
      }
    },
    methods: {
      async onCampaignChanged(id: number) {
        this.setSelectedCampaign(id);
        if (id != 0) {
          await this.getCharacterList(this.userStore.user.value?.id as number, this.campaignStore.activeCampaign.value.id);
        } else {
          this.clearCharacterList();
        }
      }
    },
    async mounted() {
      if (this.campaignStore.activeCampaign.value.id === 0) {
        await this.getCampaignList();
        await this.getActiveCampaign();
      }

      await this.getCharacterList(this.userStore.user.value.id as number, this.campaignStore.selectedCampaign.value.id);
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

  .accordion-button {
    font-weight: bold;
  }

  .card-header {
    font-weight: bold;
  }
</style>