package org.ek.nl.schema;

import org.neo4j.graphdb.Label;

// disabling SonarLint rule since label names need to match database
@java.lang.SuppressWarnings("java:S115")
public enum GraphLabels implements Label {
  Quest,
  Knight,
  Item,
}
