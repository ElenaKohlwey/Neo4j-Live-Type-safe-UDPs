package org.ek.nl.descriptors.relations;

import org.ek.nl.descriptors.nodes.*;
import org.ek.nl.descriptors.reps.NodeRep;
import org.ek.nl.descriptors.reps.RelationshipTypeRep;
import org.neo4j.graphdb.Node;
import org.neo4j.graphdb.Relationship;
import org.rle.neo4jdescriptor.annotation.EndNode;
import org.rle.neo4jdescriptor.annotation.Identifying;
import org.rle.neo4jdescriptor.annotation.StartNode;
import org.rle.neo4jdescriptor.annotation.Validate;
import org.rle.neo4jdescriptor.entity.RelationshipDescriptor;
import org.rle.neo4jdescriptor.entity.RelationshipTypeDescriptor;
import org.rle.neo4jdescriptor.property.prop_basic.StringProperty;

public class UsesDescriptor extends RelationshipDescriptor {

  public UsesDescriptor() {
    super();
    initTypeAndProperties(UsesDescriptor.class);
  }

  @Identifying
  public static final RelationshipTypeDescriptor relationshipType =
    RelationshipTypeRep.USES;

  @Validate
  public final StringProperty prpSince = new StringProperty("since");

  public Relationship create(Node knightNode, Node itemNode, String sinceProp)
    throws IllegalStateException {
    if (
      !NodeRep.Knight.identifier().matches(knightNode) ||
      !NodeRep.Item.identifier().matches(itemNode)
    ) {
      throw new IllegalStateException("error");
    }
    Relationship rel = knightNode.createRelationshipTo(
      itemNode,
      RelationshipTypeRep.USES
    );
    prpSince.setValueOn(rel, sinceProp);
    return rel;
  }

  @StartNode
  public final KnightDescriptor knightNode() {
    return NodeRep.Knight;
  }

  @EndNode
  public final ItemDescriptor itemNode() {
    return NodeRep.Item;
  }
}
