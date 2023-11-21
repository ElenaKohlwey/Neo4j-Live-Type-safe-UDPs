package org.ek.nl.descriptors.nodes;

import org.rle.neo4jdescriptor.enuminterface.StringEnum;

public enum ItemKind implements StringEnum {
  ARMOUR("Armour"),
  WEAPON("Weapon"),
  SHIELD("Shield"),
  POTION("Potion");

  private final String mDbValue;

  ItemKind(String dbValue) {
    this.mDbValue = dbValue;
  }

  public String dbValue() {
    return mDbValue;
  }
}
