package org.ek.nl;

import org.ek.nl.schema.GraphRelationshipTypes;
import org.neo4j.graphdb.Direction;
import org.neo4j.graphdb.Node;
import org.neo4j.graphdb.Relationship;
import org.neo4j.procedure.Description;
import org.neo4j.procedure.Name;
import org.neo4j.procedure.UserFunction;

/**
 * @author Elena Kohlwey, Jens Deininger
 * This class contains a working example of a function
 * (once without and once with the use of Descriptors).
 * The function receives a (Knight) node. It returns the
 * name of the (single) quest that the Knight is involved in.
 */
public class Functions {

  // class for Function names
  public static class FunctionName {

    private FunctionName() {}

    public static final String GET_QUEST = "org.ek.nl.getQuest";
  }

  @UserFunction(name = FunctionName.GET_QUEST)
  @Description(
    "Fetches the Quest that the knight is involved in - if it exists"
  )
  public String getQuest(@Name("knight") Node knight) {
    // fetch all outgoing relationships of type IS_INVOLVED_IN from knight
    Iterable<Relationship> isInvolvedInRels = knight.getRelationships(
      Direction.OUTGOING,
      GraphRelationshipTypes.IS_INVOLVED_IN
    );

    // fetch quest node from relationship Iterable if not empty and return name
    if (isInvolvedInRels.iterator().hasNext()) {
      Node quest = isInvolvedInRels.iterator().next().getEndNode();
      return (String) quest.getProperty("name", "");
    } else {
      return "none"; // return "none" if no quest is available
    }
  }
}
