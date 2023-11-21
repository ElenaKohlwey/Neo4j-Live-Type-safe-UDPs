package org.ek.nl.descriptors.nodes;

import java.util.Optional;
import java.util.stream.Stream;
import org.ek.nl.descriptors.reps.LabelRep;
import org.ek.nl.descriptors.reps.RelationshipRep;
import org.neo4j.graphdb.Direction;
import org.neo4j.graphdb.Node;
import org.rle.neo4jdescriptor.annotation.Identifying;
import org.rle.neo4jdescriptor.annotation.Validate;
import org.rle.neo4jdescriptor.entity.*;
import org.rle.neo4jdescriptor.entity.LabelDescriptor;
import org.rle.neo4jdescriptor.entity.NodeDescriptor;
import org.rle.neo4jdescriptor.property.prop_basic.StringProperty;

public class KnightDescriptor extends NodeDescriptor {

  private NodeRelationZeroOne mQuestRelation;
  private NodeRelationZeroMany mItemRelations;

  public KnightDescriptor() {
    super();
    initLabelsAndProperties(KnightDescriptor.class);
  }

  @Identifying
  public static final LabelDescriptor lblKnight = LabelRep.Knight;

  @Validate
  public final StringProperty prpName = new StringProperty("Name");

  @Validate
  public final NodeRelationZeroOne questRelation() {
    if (mQuestRelation == null) {
      mQuestRelation =
        new NodeRelationZeroOne(
          RelationshipRep.IsInvolvedIn,
          Direction.OUTGOING
        );
    }
    return mQuestRelation;
  }

  @Validate
  public final NodeRelationZeroMany itemRelations() {
    if (mItemRelations == null) {
      mItemRelations =
        new NodeRelationZeroMany(RelationshipRep.Uses, Direction.OUTGOING);
    }
    return mItemRelations;
  }

  public Optional<Node> activeQuest(Node node) {
    return this.node(questRelation(), node);
  }

  public Stream<Node> activeItems(Node node) {
    return this.nodes(itemRelations(), node);
  }
}
