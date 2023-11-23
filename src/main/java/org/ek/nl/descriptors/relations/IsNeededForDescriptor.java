package org.ek.nl.descriptors.relations;

import org.ek.nl.descriptors.nodes.*;
import org.ek.nl.descriptors.reps.NodeRep;
import org.ek.nl.descriptors.reps.RelationshipTypeRep;
import org.rle.neo4jdescriptor.annotation.EndNode;
import org.rle.neo4jdescriptor.annotation.Identifying;
import org.rle.neo4jdescriptor.annotation.StartNode;
import org.rle.neo4jdescriptor.entity.RelationshipDescriptor;
import org.rle.neo4jdescriptor.entity.RelationshipTypeDescriptor;

public class IsNeededForDescriptor extends RelationshipDescriptor {

  public IsNeededForDescriptor() {
    super();
    initTypeAndProperties(IsNeededForDescriptor.class);
  }

  @Identifying
  public static final RelationshipTypeDescriptor relationshipType =
    RelationshipTypeRep.IS_NEEDED_FOR;

  @StartNode
  public final ItemDescriptor itemNodeDescriptor() {
    return NodeRep.Item;
  }

  @EndNode
  public final QuestDescriptor questNodeDescriptor() {
    return NodeRep.Quest;
  }
}
