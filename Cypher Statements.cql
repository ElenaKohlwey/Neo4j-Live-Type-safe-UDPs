//01 Show Schema
call db.schema.visualization

//02 Show Full Graph
MATCH (a) RETURN a

//03 Show Graph Start
MATCH (q0:Quest:End {name: "Start"})-[:PRECEDES]->(q:Quest)--(b) WHERE NOT b:Quest return *

//04 GetQuest
MATCH (k:Knight) RETURN k.name, org.ek.nl.getQuest(k)

//05 Add Queen Guinevere
MATCH (k:Knight {name:"Sir Gawain"}) CREATE(k)-[:IS_INVOLVED_IN]->(q:Queen {name: "Guinevere"})

//05b GetQuest Error
MATCH (k:Knight) RETURN k.name, org.ek.nl.getQuestHandleErrors(k)

//06a UseItem -> false
MATCH (k:Knight {name:"Sir Lancelot"})
MATCH (i:Item {name:"Vengeful Mace of Smiting"})
CALL org.ek.nl.useItem(k,i)
YIELD usageSuccessful, msg RETURN usageSuccessful, msg

//06b UseItem -> true
MATCH (k:Knight {name:"Sir Gawain"})
MATCH (i:Item {name:"Vengeful Mace of Smiting"})
CALL org.ek.nl.useItem(k,i)
YIELD usageSuccessful, msg RETURN usageSuccessful, msg

//07 Add two more errors
MATCH (percival:Knight {name: "Sir Percival"})
MATCH (excalibur:Item {name: "Excalibur"})
CREATE (percival)-[:USES]->(excalibur)

//08 Introduce property type and enum errors
MATCH (item:Item {name:"Kite shield"}) SET item.name = 42, item.type = "foobar"

//09 Validate
CALL org.ek.nl.validate() YIELD validationPrintout RETURN validationPrintout
