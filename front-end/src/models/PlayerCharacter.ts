export interface PlayerCharacterMasterData {
	characterClasses: CharacterClass[],
	proficiencyBonuses: ProficiencyBonus[],
	stressStatuses: StressStatus[],
	spellSlots: SpellSlots[],
	warlockSpellSlots: WarlockSpellSlots[]
}

export interface PlayerCharacter {
	id: number,
	campaignId: number,
	userId: number,
	name: string,
	ac: number,
	acBonus: number,
	damage: number,
	temporaryHitPoints: number,
	maxHpReduction: number,
	initiativeBonus: number,
	rolledInitiative: number,
	dead: boolean,
	deathSaveSuccesses: number,
	deathSaveFailures: number,
	stress: number,
	meditationDiceUsed: number,
	dwarvenToughness: boolean,
	toughFeat: boolean,
	stressStatus: StressStatus,
	combatant: boolean,
	strength: Strength,
	dexterity: Dexterity,
	constitution: Constitution,
	intelligence: Intelligence,
	wisdom: Wisdom,
	charisma: Charisma,
	resolve: Resolve | null,
	classLevelList: ClassLevel[],
	spellSlots: SpellSlots | null,
	warlockSpellSlots: WarlockSpellSlots | null
	firstSlotsUsed: number,
	secondSlotsUsed: number,
	thirdSlotsUsed: number,
	fourthSlotsUsed: number,
	fifthSlotsUsed: number,
	sixthSlotsUsed: number,
	seventhSlotsUsed: number,
	eighthSlotsUsed: number,
	ninthSlotsUsed: number,
	warlockSlotsUsed: number,
};

interface AbilityScore {
	id: number,
	score: number,
	proficient: boolean
}

export interface Strength extends AbilityScore {
	athletics: number,
};

export interface Dexterity extends AbilityScore {
	acrobatics: number,
	sleightOfHand: number,
	stealth: number,
};

export interface Constitution extends AbilityScore {
};

export interface Intelligence extends AbilityScore {
	arcana: number,
	history: number,
	investigation: number,
	nature: number,
	religion: number,
};

export interface Wisdom extends AbilityScore {
	animalHandling: number,
	insight: number,
	medicine: number,
	perception: number,
	survival: number
};

export interface Charisma extends AbilityScore {
	deception: number,
	intimidation: number,
	performance: number,
	persuasion: number,
};

export interface Resolve extends AbilityScore {
};

export interface ClassLevel {
	id: number,
	baseClass: boolean,
	levels: number,
	usedHitDice: number,
	eldtritchKnight: boolean,
	aracneTrickster: boolean,
	beastMaster: boolean,
	characterClass: CharacterClass
};

export interface CharacterClass {
	id: number,
	name: string,
	hitDie: number,
	averageHitDie: number,
	fullCaster: boolean,
	halfCaster: boolean,
	artificer: boolean,
	warlock: boolean
};

export interface StressStatus {
	id: number,
	name: string,
	type: string,
	description: string,
	minRoll: number,
	maxRoll: number
};

export interface ProficiencyBonus {
	level: number,
	bonus: number
}

export interface SpellSlots {
	casterLevel: number,
	first: number,
	second: number,
	third: number,
	fourth: number,
	fifth: number,
	sixth: number,
	seventh: number,
	eighth: number,
	ninth: number
}

export interface WarlockSpellSlots {
	warlockLevel: number,
	quantity: number,
	slotLevel: number
}