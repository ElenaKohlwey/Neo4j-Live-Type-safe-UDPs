package org.ek.nl.descriptors.relations;

import org.ek.nl.descriptors.nodes.QuestDescriptor;
import org.ek.nl.descriptors.reps.NodeRep;
import org.ek.nl.descriptors.reps.RelationshipTypeRep;
import org.rle.neo4jdescriptor.annotation.EndNode;
import org.rle.neo4jdescriptor.annotation.Identifying;
import org.rle.neo4jdescriptor.annotation.StartNode;
import org.rle.neo4jdescriptor.entity.RelationshipDescriptor;
import org.rle.neo4jdescriptor.entity.RelationshipTypeDescriptor;

public class PrecedesDescriptor extends RelationshipDescriptor {

  public PrecedesDescriptor() {
    super();
    initTypeAndProperties(PrecedesDescriptor.class);
  }

  @Identifying
  public static final RelationshipTypeDescriptor relationshipType =
    RelationshipTypeRep.PRECEDES;

  @EndNode
  public final QuestDescriptor successorDescriptor() {
    return NodeRep.Quest;
  }

  @StartNode
  public final QuestDescriptor predecessorDescriptor() {
    return NodeRep.Quest;
  }
}
