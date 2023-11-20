CREATE
    (k1:Knight {name: "Knight 1"}),
    (k2:Knight {name: "Knight 2"}),
    (q:Quest {name: "Quest"})
CREATE
    (k1)-[:IS_INVOLVED_IN]->(q)