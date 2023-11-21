CREATE
    (k1:Knight {name:"Knight 1"}),
    (k2:Knight {name:"Knight 2"}),
    (k3:Knight {name:"Knight 3"}),
    (k4:Knight {name:"Knight 4"}),
    (i1:Item {type: "weapon", name: "Sword"}),
    (i2:Item {type: "weapon", name: "Granade"}),
    (i3:Item {type: "weapon", name: "Bow"}),
    (i4:Item {type: "weapon", name: "Axe"}),
    (i5:Item {type: "armour", name: "Armour"}),
    (i6:Item {type: "shield", name: "Shield 1"}),
    (i7:Item {type: "shield", name: "Shield 2"}),
    (:Item {type: "weapon", name: "new Sword"}),
    (:Item {type: "armour", name: "new Armour"}),
    (:Item {type: "shield", name: "new Shield"})

CREATE
    (k1)-[:USES]->(i1),
    (k2)-[:USES]->(i2),
    (k2)-[:USES]->(i3),
    (k2)-[:USES]->(i5),
    (k3)-[:USES]->(i6),
    (k4)-[:USES]->(i7),
    (k4)-[:USES]->(i4)
