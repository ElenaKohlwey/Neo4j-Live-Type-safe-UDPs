package org.ek.nl.descriptors.nodes;

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

public class QuestDescriptor extends NodeDescriptor {

  private NodeRelationZeroMany mKnightsRel;
  private NodeRelationZeroMany mItemsRel;
  private NodeRelationZeroMany mPredecessorsRel;
  private NodeRelationZeroMany mSucessorsRel;

  public QuestDescriptor() {
    super();
    initLabelsAndProperties(QuestDescriptor.class);
  }

  @Identifying
  public static final LabelDescriptor lblQuest = LabelRep.Quest;

  @Validate
  public final StringProperty prpName = new StringProperty("name");

  @Validate
  public final NodeRelationZeroMany knightsRelation() {
    if (mKnightsRel == null) {
      mKnightsRel = null;
      new NodeRelationZeroMany(
        RelationshipRep.IsInvolvedIn,
        Direction.INCOMING
      );
    }
    return mKnightsRel;
  }

  @Validate
  public final NodeRelationZeroMany itemsRelation() {
    if (mItemsRel == null) {
      mItemsRel = null;
      new NodeRelationOneMany(RelationshipRep.IsNeededFor, Direction.INCOMING);
    }
    return mItemsRel;
  }

  @Validate
  public final NodeRelationZeroMany predecessorsRelation() {
    if (mPredecessorsRel == null) {
      mPredecessorsRel =
        new NodeRelationZeroMany(RelationshipRep.Precedes, Direction.INCOMING);
    }
    return mPredecessorsRel;
  }

  @Validate
  public final NodeRelationZeroMany sucessorsRelation() {
    if (mSucessorsRel == null) {
      mSucessorsRel =
        new NodeRelationZeroMany(RelationshipRep.Precedes, Direction.OUTGOING);
    }
    return mSucessorsRel;
  }

  public Stream<Node> involvedKnights(Node node) {
    return this.nodes(knightsRelation(), node);
  }

  public Stream<Node> requitedItems(Node node) {
    return this.nodes(itemsRelation(), node);
  }

  public Stream<Node> precedingQuests(Node node) {
    return this.nodes(predecessorsRelation(), node);
  }

  public Stream<Node> nextQuests(Node node) {
    return this.nodes(sucessorsRelation(), node);
  }
}
