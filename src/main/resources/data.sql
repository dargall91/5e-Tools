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
ONE DUPLICATE KEY
UPDATE cr = VALUES(cr), xp = VALUES(xp), proficiencyBonus = VALUES(proficiencyBonus);

-- campaign - deactivate all campaigns aside from index 1
INSERT INTO Campaign(id, name, madness, active) VALUES
    (1, "Darkest Dungeons & Dragons", 1, 1)
UPDATE name = VALUES(name), madness = VALUES(madness), active = VALUES(active);

UPDATE Campaign
SET active = 0
WHERE id > 1;

-- make sure at least one music track exists
INSERT INTO Music(id, name, fileName) VALUES
    (1, "BFG - Division", "BFG-Division.mp3")
ON DUPLICATE KEY UPDATE name = VALUES(name);

-- xp thresholds
INSERT INTO XpThresholds(level, easy, medium, hard, deadly) VALUES
    (1, 25, 50, 75, 100),
    (2, 50, 100, 150, 200),
    (3, 75, 150, 225, 400),
    (4, 125, 250, 375, 500),
    (5, 250, 500, 750, 1100),
    (6, 300, 600, 900, 1400),
    (7, 350, 750, 1100, 1700),
    (8, 450, 900, 1400, 2100),
    (9, 550, 1100, 1600, 2400),
    (10, 600, 1200, 1900, 2800),
    (11, 800, 1600, 2400, 3600),
    (12, 1000, 2000, 3000, 4500),
    (13, 1100, 2200, 3400, 5100),
    (14, 1250, 2500, 3800, 5700),
    (15, 1400, 2800, 4300, 6400),
    (16, 1600, 3200, 4800, 7200),
    (17, 2000, 3900, 5900, 8800),
    (18, 2100, 4200, 6300, 9500),
    (19, 2400, 4900, 7300, 10900),
    (20, 2800, 5700, 8500, 12700)