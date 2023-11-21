package org.ek.nl.descriptors.relations;

import org.ek.nl.descriptors.nodes.*;
import org.ek.nl.descriptors.reps.NodeRep;
import org.ek.nl.descriptors.reps.RelationshipTypeRep;
import org.rle.neo4jdescriptor.annotation.EndNode;
import org.rle.neo4jdescriptor.annotation.Identifying;
import org.rle.neo4jdescriptor.annotation.StartNode;
import org.rle.neo4jdescriptor.entity.RelationshipDescriptor;
import org.rle.neo4jdescriptor.entity.RelationshipTypeDescriptor;

public class IsInvolvedInDescriptor extends RelationshipDescriptor {

  public IsInvolvedInDescriptor() {
    super();
    initTypeAndProperties(IsInvolvedInDescriptor.class);
  }

  @Identifying
  public static final RelationshipTypeDescriptor relationshipType =
    RelationshipTypeRep.IS_INVOLVED_IN;

  @StartNode
  public final KnightDescriptor knightNode() {
    return NodeRep.Knight;
  }

  @EndNode
  public final QuestDescriptor questNode() {
    return NodeRep.Quest;
  }
}
