// create quests
CREATE
    (start:Quest:End { name: "Start", duration: 0 }),
    (killerRabbit:Quest { name: "Kill the Killer Rabbit", duration: 7 }),
    (oger:Quest { name: "Kill Oger", duration: 3 }),
    (doomCavern:Quest { name: "Clear Cavern of Doom", duration: 15 }),
    (swampWitch:Quest { name: "Visit Swamp Witch", duration: 6 }),
    (forestOfThorns:Quest { name: "Cross Forest of Thorns", duration: 20 }),
    (holyGrail:Quest { name: "Find the Holy Grail", duration: 16 }),
    (end:Quest:End { name: "End", duration: 0 })

// create PRECEDES edges
CREATE
    (start)-[:PRECEDES]->(oger),
    (start)-[:PRECEDES]->(swampWitch),
    (swampWitch)-[:PRECEDES]->(killerRabbit),
    (swampWitch)-[:PRECEDES]->(forestOfThorns),
    (oger)-[:PRECEDES]->(doomCavern),
    (killerRabbit)-[:PRECEDES]->(forestOfThorns),
    (forestOfThorns)-[:PRECEDES]->(doomCavern),
    (killerRabbit)-[:PRECEDES]->(holyGrail),
    (forestOfThorns)-[:PRECEDES]->(holyGrail),
    (holyGrail)-[:PRECEDES]->(end)

// create items
CREATE
    (i1:Item { type: "weapon", name: "Excalibur" }),
    (i2:Item { type: "weapon", name: "Holy Handgrenade" }),
    (i3:Item { type: "weapon", name: "Dragonslayer Bow" }),
    (i4:Item { type: "weapon", name: "Vengeful Mace of Smiting" }),
    (i5:Item { type: "weapon", name: "Bloody Axe of Ogre Decapitation" }),
    (i6:Item { type: "weapon", name: "Sneaky Dagger of Backstabbing" }),
    (i7:Item { type: "potion", name: "Magic Potion of Strength" }),
    (i8:Item { type: "potion", name: "Magic Potion of Wisdom" }),
    (i9:Item { type: "armour", name: "Leather Armour" }),
    (i10:Item { type: "armour", name: "Full Plate" }),
    (i11:Item { type: "armour", name: "Rigid Leather" }),
    (i12:Item { type: "shield", name: "Shield of Taunting" }),
    (i13:Item { type: "shield", name: "Buckler" }),
    (i14:Item { type: "shield", name: "Kite shield" }),
    (i15:Item { type: "shield", name: "Tower shield" })

// create knights
CREATE
    (k1:Knight { name: "Sir Lancelot" }),
    (k2:Knight { name: "Sir Gawain" }),
    (k3:Knight { name: "Sir Percival" }),
    (k4:Knight { name: "Sir Geraint" })

// create USES relations
CREATE
    (k1)-[:USES { since:'Since forever' }]->(i1),
    (k1)-[:USES { since:'Since forever' }]->(i3),
    (k1)-[:USES { since:'Since forever' }]->(i9),
    (k1)-[:USES { since:'Since forever' }]->(i12),
    (k2)-[:USES { since:'Since forever' }]->(i2),
    (k2)-[:USES { since:'Since forever' }]->(i8),
    (k2)-[:USES { since:'Since forever' }]->(i10),
    (k2)-[:USES { since:'Since forever' }]->(i13),
    (k3)-[:USES { since:'Since forever' }]->(i5),
    (k3)-[:USES { since:'Since forever' }]->(i11),
    (k3)-[:USES { since:'Since forever' }]->(i14),
    (k4)-[:USES { since:'Since forever' }]->(i6),
    (k4)-[:USES { since:'Since forever' }]->(i7),
    (k4)-[:USES { since:'Since forever' }]->(i15)

// create IS_NEEDED_FOR relations
CREATE
    (i1)-[:IS_NEEDED_FOR]->(forestOfThorns),
    (i2)-[:IS_NEEDED_FOR]->(killerRabbit),
    (i3)-[:IS_NEEDED_FOR]->(swampWitch),
    (i5)-[:IS_NEEDED_FOR]->(oger),
    (i7)-[:IS_NEEDED_FOR]->(doomCavern),
    (i7)-[:IS_NEEDED_FOR]->(forestOfThorns),
    (i8)-[:IS_NEEDED_FOR]->(swampWitch),
    (i9)-[:IS_NEEDED_FOR]->(forestOfThorns),
    (i1)-[:IS_NEEDED_FOR]->(holyGrail),
    (i15)-[:IS_NEEDED_FOR]->(holyGrail)

CREATE
    (k1)-[:IS_INVOLVED_IN]->(swampWitch),
    (k2)-[:IS_INVOLVED_IN]->(swampWitch),
    (k3)-[:IS_INVOLVED_IN]->(oger)
