-- challenge ratings
INSERT INTO ChallengeRating(id, cr, xp, proficiencyBonus) VALUES
    (1, '0', 0, 2),
    (2, '0', 10, 2),
    (3, '1/8', 25, 2),
    (4, '1/4', 50, 2),
    (5, '1/2', 100, 2),
    (6, '1', 200, 2),
    (7, '2', 450, 2),
    (8, '3', 700, 2),
    (9, '4', 1100, 2),
    (10, '5', 1800, 3),
    (11, '6', 2300, 3),
    (12, '7', 2900, 3),
    (13, '8', 3900, 3),
    (14, '9', 5000, 4),
    (15, '10', 5900, 4),
    (16, '11', 7200, 4),
    (17, '12', 8400, 4),
    (18, '13', 10000, 5),
    (19, '14', 11500, 5),
    (20, '15', 13000, 5),
    (21, '16', 15000, 5),
    (22, '17', 18000, 6),
    (23, '18', 20000, 6),
    (24, '19', 22000, 6),
    (25, '20', 25000, 6),
    (26, '21', 33000, 7),
    (27, '22', 41000, 7),
    (28, '23', 50000, 7),
    (29, '24', 62000, 7),
    (30, '25', 75000, 8),
    (31, '26', 90000, 8),
    (32, '27', 105000, 8),
    (33, '28', 120000, 8),
    (34, '29', 135000, 9),
    (35, '30', 155000, 9)
ON DUPLICATE KEY
UPDATE cr = VALUES(cr), xp = VALUES(xp), proficiencyBonus = VALUES(proficiencyBonus);

-- campaign - deactivate all campaigns aside from index 1
INSERT INTO Campaign(id, name, madness, active, inflatedHitPoints) VALUES
    (1, "Darkest Dungeons & Dragons", 1, 1, 1)
ON DUPLICATE KEY
UPDATE name = VALUES(name), madness = VALUES(madness), active = VALUES(active), inflatedHitPoints = VALUES(inflatedHitPoints);

UPDATE Campaign
SET active = 0
WHERE id > 1;

-- xp thresholds
INSERT INTO XpThresholds(level, easy, medium, hard, deadly, budget) VALUES
    (1, 25, 50, 75, 100, 300),
    (2, 50, 100, 150, 200, 600),
    (3, 75, 150, 225, 400, 1200),
    (4, 125, 250, 375, 500, 1700),
    (5, 250, 500, 750, 1100, 3500),
    (6, 300, 600, 900, 1400, 4000),
    (7, 350, 750, 1100, 1700, 5000),
    (8, 450, 900, 1400, 2100, 6000),
    (9, 550, 1100, 1600, 2400, 7500),
    (10, 600, 1200, 1900, 2800, 9000),
    (11, 800, 1600, 2400, 3600, 10500),
    (12, 1000, 2000, 3000, 4500, 11500),
    (13, 1100, 2200, 3400, 5100, 13500),
    (14, 1250, 2500, 3800, 5700, 15000),
    (15, 1400, 2800, 4300, 6400, 18000),
    (16, 1600, 3200, 4800, 7200, 20000),
    (17, 2000, 3900, 5900, 8800, 25000),
    (18, 2100, 4200, 6300, 9500, 27000),
    (19, 2400, 4900, 7300, 10900, 30000),
    (20, 2800, 5700, 8500, 12700, 40000)
ON DUPLICATE KEY
UPDATE easy = VALUES(easy), medium = VALUES(medium), hard = VALUES(hard), deadly = VALUES(deadly), budget = VALUES(budget);

--test users
INSERT INTO User(id, username, password, admin) VALUES
    (1, 'admin', 'f!v3e7Oo15', 1)
ON DUPLICATE KEY
UPDATE username = VALUES(username), password = VALUES(password), admin = VALUES(admin);

-- classes
INSERT INTO CharacterClass(id, name, hitDie, averageHitDie, fullCaster, halfCaster, artificer, warlock) VALUES
    (1, 'Artificer', 8, 5, 0, 0, 1, 0),
    (2, 'Barbarian', 12, 7, 0, 0, 0, 0),
    (3, 'Bard', 8, 5, 1, 0, 0, 0),
    (4, 'Cleric', 8, 5, 1, 0, 0, 0),
    (5, 'Druid', 8, 5, 1, 0, 0, 0),
    (6, 'Fighter', 10, 6, 0, 0, 0, 0),
    (7, 'Monk', 8, 5, 0, 0, 0, 0),
    (8, 'Paladin', 10, 6, 0, 1, 0, 0),
    (9, 'Ranger', 10, 6, 0, 1, 0, 0),
    (10, 'Rogue', 8, 5, 0, 0, 0, 0),
    (11, 'Sorcerer', 6, 4, 1, 0, 0, 0),
    (12, 'Warlock', 8, 5, 0, 0, 0, 1),
    (13, 'Wizard', 6, 4, 1, 0, 0, 0)
ON DUPLICATE KEY
UPDATE name = VALUES(name), hitDie = VALUES(hitDie), averageHitDie = VALUES(averageHitDie);

-- stress statuses
INSERT INTO StressStatus(id, name, type, description, minRoll, maxRoll) VALUES
    (1, 'Normal', 'None', '', 0, 0),
    (2, 'Irrational', 'Affliction', 'At the start of your turn, roll on the Afflictions & Virtues table and use that result for this turn. If the result is Irrational or a Virtue, the DM chooses one of the following effects:<ul><li>You must move as far as possible towards your nearest ally, and make a single melee weapon attack on that ally, if able.</li> <li>You must move as far as possible towards your nearest ally, stopping just before entering melee range, and make a single ranged weapon attack on that ally, if able.</li></ul>Regardless of the DM\'s choice, if multiple allies are equally close to you, choose one at random.', 1, 15),
    (3, 'Paranoid', 'Affliction', 'You cannot be the target of, or gain any benefits of, your allies\' spells, actions, abilities, or items. You cannot end your turn within 30 feet of an ally, using as much movement as possible to try to get away from them.', 16, 25),
    (4, 'Selfish', 'Affliction', 'You cannot cast spells targeting allies other than spells that cause damage, you may not use the help action, you cannot give items to your allies, and you cannot use non-damaging items on your allies.', 26, 35),
    (5, 'Abusive', 'Affliction', 'At the start of your turn, all allies within 50 feet of you that can hear you gain 1d6 stress points. If a character rolls a 6 on this roll, they suffer disadvantage on their next attack roll, saving throw, or ability check.', 36, 45),
    (6, 'Fearful', 'Affliction', 'At the start of your turn, randomly select one enemy creature. Until the end of your  turn, you are afraid of that creature. Gaining the frightened condition as a result of this affliction does not cause you to gain stress (though other affects of stress as a result of fear still apply).', 46, 55),
    (7, 'Hopeless', 'Affliction', 'You cannot have or gain advantage on any rolls. When you gain stress points, you gain an additional 1d6 stress. When you lose stress points, the amount lost is reduced by 1d4 (to a minimum of 1).', 56, 65),
    (8, 'Masochistic', 'Affliction', 'You may not attempt to disengage from combat. Attacks made against you may re-roll one damage die.', 66, 75),
    (9, 'Powerful', 'Virtue', 'Add your proficiency bonus and your Resolve modifier to all your damage rolls. At the start of your turn, roll 1d4. On a 4, all allies currently within 20 feet of you add their proficiency bonus and their Resolve modifier to their damage rolls until the beginning of your next turn.', 76, 80),
    (10, 'Courageous', 'Virtue', 'Whenever you gain stress, reduce the amount of stress gained by 1d4 + your Resolve modifier (to a minimum of 1). At the start of each of your turns, roll 1d4. On a 4, you and all allies within 20 feet of you lose stress equal to 1d4 + your Resolve modifier + your proficiency bonus.', 81, 85),
    (11, 'Stalwart', 'Virtue', 'All damage dealt to you is reduced by your proficiency bonus + your Resolve modifier, to a minimum of 1. This reduction is applied before any other damage modifiers, such as the Rouge\'s Uncanny Dodge class feature. At the start of your turn, roll 1d4. On a 4, all allies currently within 20 feet of you reduce all damage dealt to them by their resolve modifier + their proficiency bonus until the beginning of your next turn.', 86, 90),
    (12, 'Vigorous', 'Virtue', 'You have advantage on all initiative rolls. You add your Resolve modifier (minimum of 1) to your AC and to all non-Resolve saving throws. At the start of your turn, roll 1d4. On a 4, you and allies within 20 feet of you gain temporary hit points equal to 1d8 + your proficiency modifier + your Resolve modifier.', 91, 95),
    (13, 'Focused', 'Virtue', 'Add your proficiency bonus to all of your attack rolls and your Spell Save DC. In addition, the number you need to roll on the d20 on attack rolls to land a critical hit is reduced by 1. At the start of your turn, roll 1d4. On a 4, all allies currently within 20 feet of you add their proficiency bonus to their attack rolls and their Spell Save DC and the number they need to roll on the d20 on attack rolls to land a critical hit is reduced by 1.', 96, 100)
ON DUPLICATE KEY
UPDATE name = VALUES(name), type = VALUES(type), description = VALUES(description), minRoll = VALUES(minRoll), maxRoll = VALUES(maxRoll);

-- proficiency bonus table
INSERT INTO ProficiencyBonus(level, bonus) VALUES
    (1, 2),
    (2, 2),
    (3, 2),
    (4, 2),
    (5, 3),
    (6, 3),
    (7, 3),
    (8, 3),
    (9, 4),
    (10, 4),
    (11, 4),
    (12, 4),
    (13, 5),
    (14, 5),
    (15, 5),
    (16, 5),
    (17, 6),
    (18, 6),
    (19, 6),
    (20, 6)
ON DUPLICATE KEY
UPDATE bonus = VALUES(bonus);

-- spell slots
INSERT INTO SpellSlots(casterLevel, first, second, third, fourth, fifth, sixth, seventh, eighth, ninth) VALUES
    (1, 2, 0, 0, 0, 0, 0, 0, 0, 0),
    (2, 3, 0, 0, 0, 0, 0, 0, 0, 0),
    (3, 4, 2, 0, 0, 0, 0, 0, 0, 0),
    (4, 4, 3, 0, 0, 0, 0, 0, 0, 0),
    (5, 4, 3, 2, 0, 0, 0, 0, 0, 0),
    (6, 4, 3, 3, 0, 0, 0, 0, 0, 0),
    (7, 4, 3, 3, 1, 0, 0, 0, 0, 0),
    (8, 4, 3, 3, 2, 0, 0, 0, 0, 0),
    (9, 4, 3, 3, 3, 1, 0, 0, 0, 0),
    (10, 4, 3, 3, 3, 2, 0, 0, 0, 0),
    (11, 4, 3, 3, 3, 2, 1, 0, 0, 0),
    (12, 4, 3, 3, 3, 2, 1, 0, 0, 0),
    (13, 4, 3, 3, 3, 2, 1, 1, 0, 0),
    (14, 4, 3, 3, 3, 2, 1, 1, 0, 0),
    (15, 4, 3, 3, 3, 2, 1, 1, 1, 0),
    (16, 4, 3, 3, 3, 2, 1, 1, 1, 0),
    (17, 4, 3, 3, 3, 2, 1, 1, 1, 1),
    (18, 4, 3, 3, 3, 2, 1, 1, 1, 1),
    (19, 4, 3, 3, 3, 2, 2, 1, 1, 1),
    (20, 4, 3, 3, 3, 2, 2, 2, 1, 1)
ON DUPLICATE KEY
UPDATE first = VALUES(first), second = VALUES(second), third = VALUES(third), fourth = VALUES(fourth),
fifth = VALUES(fifth), sixth = VALUES(sixth), seventh = VALUES(seventh), eighth = VALUES(eighth), ninth = VALUES(ninth);

-- warlock spell slots
INSERT INTO WarlockSpellSlots(warlockLevel, quantity, slotLevel) VALUES
    (1, 1, 1),
    (2, 2, 1),
    (3, 2, 2),
    (4, 2, 2),
    (5, 2, 3),
    (6, 2, 3),
    (7, 2, 4),
    (8, 2, 4),
    (9, 2, 5),
    (10, 2, 5),
    (11, 3, 5),
    (12, 3, 5),
    (13, 3, 5),
    (14, 3, 5),
    (15, 3, 5),
    (16, 3, 5),
    (17, 4, 5),
    (18, 4, 5),
    (19, 4, 5),
    (20, 4, 5)
ON DUPLICATE KEY UPDATE
quantity = VALUES(quantity), slotLevel = VALUES(slotLevel);

-- primal companion types
INSERT INTO PrimalCompanionType(id, name, size, speed, baseHitPoints, hitDie, strength, dexterity, constitution,
intelligence, wisdom, charisma, abilityName, abilityDescription, actionName, actionDescription) VALUES
    (1, 'Beast of the Land', 'Medium', '40 ft., climb 40 ft.', 5, 8, 14, 14, 15, 8, 14, 11,
    'Charge', 'If the beast moves at least 20 feet straight toward a target and then hits it with a maul attack on the same turn, the target takes an extra 1d6 slashing damage. If the target is a creature, it must succeed on a <wisSpellSave> DC Strength saving throw or be knocked prone.',
    'Maul', '<em>Melee Weapon Attack:</em> +<toHitBonus> to hit, reach 5 ft., one target.<br/><em>Hit:</em> 1d8 + <strDamage> slashing damage.'),
    (2, 'Beast of the Sea', 'Medium', '5 ft., swim 60 ft.', 5, 8, 14, 14, 15, 8, 14, 11,
    'Amphibious', 'The beast can breathe both air and water.',
    'Binding Strike', '<em>Melee Weapon Attack:</em> +<toHitBonus> to hit, reach 5 ft., one target.<br/><em>Hit:</em> 1d8 + <strDamage> piercing or bludgeoning damage (your choice), and the target is grappled (escape DC <wisSpellSave>). Until this grapple ends, the beast can\'t use this attack on another target.'),
    (3, 'Beast of the Sky', 'Small', '10 ft., fly 60 ft.', 4, 6, 6, 16, 13, 8, 14, 11,
    'Flyby', 'The beast doesn\'t provoke opportunity attacks when it flies out of an enemy\'s reach.',
    'Binding Strike', '<em>Melee Weapon Attack:</em> +<toHitBonus> to hit, reach 5 ft., one target.<br/><em>Hit:</em> 1d8 + <dexDamage> slashing damage.')
ON DUPLICATE KEY UPDATE
name = VALUES(name), strength = VALUES(strength), dexterity = VALUES(dexterity), constitution = VALUES(constitution),
intelligence = VALUES(intelligence), wisdom = VALUES(wisdom), charisma = VALUES(charisma), abilityName = VALUES(abilityName),
abilityDescription = VALUES(abilityDescription), actionName = VALUES(actionName), actionDescription = VALUES(actionDescription);