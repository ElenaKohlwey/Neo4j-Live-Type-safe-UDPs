package org.ek.nl.schema;

import org.neo4j.graphdb.RelationshipType;

public enum GraphRelationshipTypes implements RelationshipType {
  PRECEDES,
  IS_NEEDED_FOR,
  USES,
  IS_INVOLVED_IN,
}
