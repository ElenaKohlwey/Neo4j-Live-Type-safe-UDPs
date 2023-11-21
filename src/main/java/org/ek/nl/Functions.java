package org.ek.nl;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;
import org.ek.nl.descriptors.reps.NodeRep;
import org.ek.nl.schema.GraphLabels;
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

    public static final String GET_QUEST_HANDLE_ERRORS =
      "org.ek.nl.getQuestHandleErrors";

    public static final String GET_QUEST_DESC = "org.ek.nl.getQuestDesc";
  }

  public static class FunctionDescription {

    private FunctionDescription() {}

    public static final String GET_QUEST =
      "Fetches the Quest that the knight is involved in - if it exists";
  }

  @UserFunction(name = FunctionName.GET_QUEST)
  @Description(FunctionDescription.GET_QUEST)
  public String getQuest(@Name("knight") Node knight) {
    // fetch all outgoing relationships of type IS_INVOLVED_IN from knight
    Iterable<Relationship> isInvolvedInRels = knight.getRelationships(
      Direction.OUTGOING,
      GraphRelationshipTypes.IS_INVOLVED_IN
    );

    // return quest name if quest exists
    if (isInvolvedInRels.iterator().hasNext()) {
      Node quest = isInvolvedInRels.iterator().next().getEndNode();
      return (String) quest.getProperty("name", "");
    }
    return "none"; // return "none" if no quest is available
  }

  @UserFunction(name = FunctionName.GET_QUEST_HANDLE_ERRORS)
  @Description(FunctionDescription.GET_QUEST)
  public String getQuestHandleErrors(@Name("knight") Node knight)
    throws UnsupportedOperationException {
    if (!knight.hasLabel(GraphLabels.Knight)) {
      throw new UnsupportedOperationException("Node is not a Knight");
    }

    // fetch all outgoing relationships of type IS_INVOLVED_IN from knight
    Iterable<Relationship> isInvolvedInRels = knight.getRelationships(
      Direction.OUTGOING,
      GraphRelationshipTypes.IS_INVOLVED_IN
    );

    List<Relationship> quests = StreamSupport
      .stream(isInvolvedInRels.spliterator(), false)
      .collect(Collectors.toList());

    if (quests.size() > 1) {
      throw new UnsupportedOperationException(
        "Knight is involved in more than one node"
      );
    }
    Node questNode = quests.get(0).getEndNode();

    if (!questNode.hasLabel(GraphLabels.Quest)) {
      throw new UnsupportedOperationException(
        "Knight is involved in something other than a quest"
      );
    }
    Object namePropVal = questNode.getProperty("name", null);
    if (namePropVal == null) {
      throw new UnsupportedOperationException("Quest has no name property");
    }
    if (!(namePropVal instanceof String)) {
      throw new UnsupportedOperationException(
        "value of name property is not a string"
      );
    }
    return (String) namePropVal;
  }

  @UserFunction(name = FunctionName.GET_QUEST_DESC)
  @Description(FunctionDescription.GET_QUEST)
  public String getQuestDesc(@Name("knight") Node knight) {
    // fetch single quest of knight if it exists
    Optional<Node> quest = NodeRep.Knight.quest(knight);

    // return quest name if quest exists
    if (quest.isPresent()) {
      return NodeRep.Quest.prpName.getValueOn(quest.get());
    }
    return "none"; // return "none" if no quest is available
  }
}
