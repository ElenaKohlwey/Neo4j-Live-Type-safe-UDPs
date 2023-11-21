package org.ek.nl.descriptors.nodes;

import org.rle.neo4jdescriptor.property.EnumProperty;

public class ItemKindProperty extends EnumProperty<String, ItemKind> {

  public ItemKindProperty(String key) {
    super(key, key, String.class, ItemKind.class);
  }
}
