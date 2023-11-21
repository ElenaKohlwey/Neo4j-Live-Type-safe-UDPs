package org.ek.nl.descriptors.nodes;

import java.util.Optional;
import org.ek.nl.descriptors.reps.LabelRep;
import org.ek.nl.descriptors.reps.RelationshipRep;
import org.neo4j.graphdb.Direction;
import org.neo4j.graphdb.Node;
import org.rle.neo4jdescriptor.annotation.Identifying;
import org.rle.neo4jdescriptor.annotation.Validate;
import org.rle.neo4jdescriptor.entity.*;
import org.rle.neo4jdescriptor.property.prop_basic.StringProperty;

public class ItemDescriptor extends NodeDescriptor {

  private NodeRelationZeroMany mQuestRelations;
  private NodeRelationZeroOne mUserRelation;

  public ItemDescriptor() {
    super();
    initLabelsAndProperties(ItemDescriptor.class);
  }

  @Identifying
  public static final LabelDescriptor lblItem = LabelRep.Item;

  @Validate
  public final StringProperty prpName = new StringProperty("Name");

  @Validate
  public final ItemKindProperty prpItemKind = new ItemKindProperty("type");

  @Validate
  public final NodeRelationZeroMany questRelations() {
    if (mQuestRelations == null) {
      mQuestRelations =
        new NodeRelationZeroMany(
          RelationshipRep.IsNeededFor,
          Direction.OUTGOING
        );
    }
    return mQuestRelations;
  }

  @Validate
  public final NodeRelationZeroOne userRelation() {
    if (mUserRelation == null) {
      mUserRelation =
        new NodeRelationZeroOne(RelationshipRep.Uses, Direction.INCOMING);
    }
    return mUserRelation;
  }

  public Optional<Node> user(Node node) {
    return this.node(mUserRelation, node);
  }
}
