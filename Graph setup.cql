// clear database
MATCH (n)
DETACH DELETE n;

// create quests
CREATE
    (q0:Quest:End { name: "Quest0", duration: 0 }),
    (q1:Quest { name: "Quest1", duration: 13 }),
    (q2:Quest { name: "Quest2", duration: 8 }),
    (q3:Quest { name: "Quest3", duration: 7 }),
    (q4:Quest { name: "Quest4", duration: 6 }),
    (q5:Quest { name: "Quest5", duration: 3 }),
    (q6:Quest { name: "Quest6", duration: 11 }),
    (q7:Quest { name: "Quest7", duration: 11 }),
    (q8:Quest { name: "Quest8", duration: 15 }),
    (q9:Quest { name: "Quest9", duration: 6 }),
    (q10:Quest { name: "Quest10", duration: 20 }),
    (q11:Quest { name: "Quest11", duration: 16 }),
    (q12:Quest:End { name: "Quest12", duration: 0 })

// create PRECEDES edges
CREATE
    (q0)-[:PRECEDES]->(q5),
    (q0)-[:PRECEDES]->(q9),
    (q1)-[:PRECEDES]->(q11),
    (q1)-[:PRECEDES]->(q10),
    (q1)-[:PRECEDES]->(q7),
    (q1)-[:PRECEDES]->(q2),
    (q1)-[:PRECEDES]->(q3),
    (q2)-[:PRECEDES]->(q12),
    (q3)-[:PRECEDES]->(q7),
    (q3)-[:PRECEDES]->(q10),
    (q3)-[:PRECEDES]->(q6),
    (q4)-[:PRECEDES]->(q3),
    (q4)-[:PRECEDES]->(q7),
    (q4)-[:PRECEDES]->(q11),
    (q4)-[:PRECEDES]->(q8),
    (q5)-[:PRECEDES]->(q8),
    (q5)-[:PRECEDES]->(q4),
    (q5)-[:PRECEDES]->(q10),
    (q5)-[:PRECEDES]->(q2),
    (q6)-[:PRECEDES]->(q12),
    (q7)-[:PRECEDES]->(q12),
    (q8)-[:PRECEDES]->(q10),
    (q8)-[:PRECEDES]->(q7),
    (q8)-[:PRECEDES]->(q2),
    (q8)-[:PRECEDES]->(q1),
    (q9)-[:PRECEDES]->(q12),
    (q10)-[:PRECEDES]->(q12),
    (q11)-[:PRECEDES]->(q7),
    (q11)-[:PRECEDES]->(q3)

// create items
CREATE
    (i1:Item { type: "weapon", name: "Excalibur" }),
    (i2:Item { type: "weapon", name: "Holy Handgrenade" }),
    (i3:Item { type: "weapon", name: "Dragonslayer Bow" }),
    (i4:Item { type: "weapon", name: "Vengeful Mace of Smiting" }),
    (i5:Item { type: "weapon", name: "Bloody Axe of Ogre Decapitation" }),
    (i6:Item { type: "weapon", name: "Sneaky Dagger of Backstabbing" }),
    (i8:Item { type: "potion", name: "Magic Potion of Strength" }),
    (i9:Item { type: "potion", name: "Magic Potion of Wisdom" }),
    (i10:Item { type: "armour", name: "chainmail" }),
    (i11:Item { type: "armour", name: "full plate" }),
    (i12:Item { type: "armour", name: "rigid leather" }),
    (i13:Item { type: "shield", name: "Shield of Taunting" }),
    (i14:Item { type: "shield", name: "Buckler" }),
    (i15:Item { type: "shield", name: "Kite shield" }),
    (i16:Item { type: "shield", name: "Tower shield" })

// create knights
CREATE
    (k1:Knight { name: "Sir Lancelot" }),
    (k2:Knight { name: "Sir Gawain" }),
    (k3:Knight { name: "Sir Percival" }),
    (k4:Knight { name: "Sir Geraint" })

// create USES relations
CREATE
    (k1)-[:USES]->(i1),
    (k1)-[:USES]->(i3),
    (k1)-[:USES]->(i10),
    (k1)-[:USES]->(i13),
    (k2)-[:USES]->(i2),
    (k2)-[:USES]->(i9),
    (k2)-[:USES]->(i11),
    (k2)-[:USES]->(i14),
    (k3)-[:USES]->(i4),
    (k3)-[:USES]->(i5),
    (k3)-[:USES]->(i12),
    (k3)-[:USES]->(i15),
    (k4)-[:USES]->(i6),
    (k4)-[:USES]->(i7),
    (k4)-[:USES]->(i8),
    (k4)-[:USES]->(i16)

// create IS_NEEDED_FOR relations
CREATE
    (i1)-[:IS_NEEDED_FOR]->(q1),
    (i1)-[:IS_NEEDED_FOR]->(q2),
    (i1)-[:IS_NEEDED_FOR]->(q10),
    (i2)-[:IS_NEEDED_FOR]->(q1),
    (i2)-[:IS_NEEDED_FOR]->(q3),
    (i3)-[:IS_NEEDED_FOR]->(q3),
    (i3)-[:IS_NEEDED_FOR]->(q9),
    (i4)-[:IS_NEEDED_FOR]->(q4),
    (i5)-[:IS_NEEDED_FOR]->(q5),
    (i5)-[:IS_NEEDED_FOR]->(q6),
    (i6)-[:IS_NEEDED_FOR]->(q7),
    (i7)-[:IS_NEEDED_FOR]->(q11),
    (i8)-[:IS_NEEDED_FOR]->(q8),
    (i8)-[:IS_NEEDED_FOR]->(q10),
    (i9)-[:IS_NEEDED_FOR]->(q9),
    (i10)-[:IS_NEEDED_FOR]->(q10)

CREATE
    (k1)-[:IS_INVOLVED_IN]->(q9),
    (k2)-[:IS_INVOLVED_IN]->(q9),
    (k3)-[:IS_INVOLVED_IN]->(q5)
