export interface PlayerCharacter {
    id: number,
    campaignId: number,
    userId: number,
    name: string,
    ac: number,
    initiativeBonus: number,
    rolledInitiative: number,
    dead: boolean,
    combatant: boolean,
    strength: Strength,
    dexterity: Dexterity,
    constitution: Constitution,
    intelligence: Intelligence,
    wisdom: Wisdom,
    charisma: Charisma,
    resolve: Resolve | null,
    classLevelList: ClassLevel[]
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
    sleightofHand: number,
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
    characterClass: CharacterClass
};

export interface CharacterClass {
    id: number,
    name: string,
    hitDie: number
};