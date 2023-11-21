///////////// ELENA /////////////

//01 Show Schema
call db.schema.visualization

//02 Show Full Graph
MATCH (a) RETURN a

//03 Show Graph Start
MATCH (q0:Quest:End {name: "Quest0"})-[:PRECEDES]->(q:Quest)--(b) WHERE NOT b:Quest return *

//04 GetQuest
MATCH (k:Knight) RETURN k.name, org.ek.nl.getQuest(k)

//05 Add Queen Guinevere
MATCH (k:Knight {name:"Sir Gawain"}) CREATE(k)-[:IS_INVOLVED_IN]->(q:Queen {name: "Guinevere"})

//06 UseItem -> false
MATCH (k:Knight {name:"Sir Gawain"})
MATCH (i:Item {name:"Vengeful Mace of Smiting"})
CALL org.ek.nl.useItem(k,i)
YIELD usageSuccessful RETURN usageSuccessful


///////////// JENS /////////////
