package org.ek.nl;

import java.util.HashSet;
import java.util.stream.Stream;
import org.ek.nl.schema.GraphRelationshipTypes;
import org.neo4j.graphdb.Direction;
import org.neo4j.graphdb.Node;
import org.neo4j.graphdb.Relationship;
import org.neo4j.graphdb.Transaction;
import org.neo4j.procedure.Context;
import org.neo4j.procedure.Description;
import org.neo4j.procedure.Mode;
import org.neo4j.procedure.Name;
import org.neo4j.procedure.Procedure;

/**
 * @author Elena Kohlwey, Jens Deininger
 * This class contains a working example of a procedure
 * (once without and once with the use of Descriptors).
 * The procedure checks whether the given (Knight) node is
 * able to use the given (Item) node.
 */
public class Procedures {

  @Context
  public Transaction tx;

  public static class ProcedureName {

    private ProcedureName() {}

    public static final String USE_ITEM = "org.ek.nl.useItem";
  }

  public static final String MESSAGE_SUCCESS_NEW = "%s is now using '%s' (%s).";
  public static final String MESSAGE_SUCCESS_OLD =
    "%s has already been using '%s' (%s).";
  public static final String MESSAGE_FAIL_TWO_WEAPONS =
    "%s is already using two weapons and hence cannot start using '%s' (%s).";
  public static final String MESSAGE_FAIL_ONE_WEAPON_ONE_SHIELD =
    "%s is already using one weapon and a shield and hence cannot start using '%s' (%s).";
  public static final String MESSAGE_FAIL_ONE_SHIELD =
    "%s is already using a shield and hence cannot start using '%s' (%s).";
  public static final String MESSAGE_FAIL_ARMOUR =
    "%s is already using an armour and hence cannot start using '%s' (%s).";

  /** This procedure checks whether the given knight can
   * use the newItem and if so returns true. Returns false otherwise.
   */
  @SuppressWarnings("java:S131") // suppresses default case warning for switch
  @Procedure(mode = Mode.WRITE, name = ProcedureName.USE_ITEM)
  @Description("Create a graph by seed and config node")
  public Stream<Output> useItem(
    @Name("knight") Node knight,
    @Name("newItem") Node newItem
  ) {
    boolean usageSuccessful = false;
    HashSet<Node> items = new HashSet<>();
    int countArmour = 0;
    int countWeapon = 0;
    int countShield = 0;

    // fetch knight name
    String knightName = (String) knight.getProperty("name");

    // fetch newItem info
    String newItemType = (String) newItem.getProperty("type");
    String newItemName = (String) newItem.getProperty("name");

    // fetch items of knight and count relevant types
    Iterable<Relationship> usesRels = knight.getRelationships(
      Direction.OUTGOING,
      GraphRelationshipTypes.USES
    );

    for (Relationship usesRel : usesRels) {
      Node item = usesRel.getEndNode();
      String itemType = (String) item.getProperty("type");
      switch (itemType) {
        case "armour":
          countArmour++;
          break;
        case "weapon":
          countWeapon++;
          break;
        case "shield":
          countShield++;
          break;
        default:
          break;
      }
      items.add(item);
    }

    // if item is already in use by knight return true
    if (items.contains(newItem)) {
      return Stream.of(
        new Output(
          true,
          String.format(
            MESSAGE_SUCCESS_OLD,
            knightName,
            newItemName,
            newItemType
          )
        )
      );
    }

    String message = "Error";

    // check whether item could additionally be used
    switch (newItemType) {
      case "armour":
        if (countArmour == 0) {
          knight.createRelationshipTo(newItem, GraphRelationshipTypes.USES);
          usageSuccessful = true;
          message =
            String.format(
              MESSAGE_SUCCESS_NEW,
              knightName,
              newItemName,
              newItemType
            );
        } else {
          message =
            String.format(
              MESSAGE_FAIL_ARMOUR,
              knightName,
              newItemName,
              newItemType
            );
        }
        break;
      case "weapon":
        if (countWeapon + countShield < 2) {
          knight.createRelationshipTo(newItem, GraphRelationshipTypes.USES);
          usageSuccessful = true;
          message =
            String.format(
              MESSAGE_SUCCESS_NEW,
              knightName,
              newItemName,
              newItemType
            );
        } else if (countWeapon == 2) {
          message =
            String.format(
              MESSAGE_FAIL_TWO_WEAPONS,
              knightName,
              newItemName,
              newItemType
            );
        } else {
          message =
            String.format(
              MESSAGE_FAIL_ONE_WEAPON_ONE_SHIELD,
              knightName,
              newItemName,
              newItemType
            );
        }
        break;
      case "shield":
        if (countWeapon < 2 && countShield == 0) {
          knight.createRelationshipTo(newItem, GraphRelationshipTypes.USES);
          usageSuccessful = true;
          message =
            String.format(
              MESSAGE_SUCCESS_NEW,
              knightName,
              newItemName,
              newItemType
            );
        } else if (countWeapon == 2) {
          message =
            String.format(
              MESSAGE_FAIL_TWO_WEAPONS,
              knightName,
              newItemName,
              newItemType
            );
        } else {
          message =
            String.format(
              MESSAGE_FAIL_ONE_SHIELD,
              knightName,
              newItemName,
              newItemType
            );
        }
        break;
      case "potion":
        usageSuccessful = true;
        break;
    }
    return Stream.of(new Output(usageSuccessful, message));
  }

  @SuppressWarnings("java:S1104") // complains about there being public non static non final fields and no accessors. But Neo4j needs those in its wrapper objects
  public class Output {

    public static final String USAGE_SUCCESSFUL = "usageSuccessful";
    public static final String MESSAGE = "message";

    /* The name of the public field below must always be the same as the static String above!
     * This String is needed in the cypher to get to the content of the returned values. */
    public boolean usageSuccessful;
    public String message;

    public Output(boolean usageSuccessful, String message) {
      this.usageSuccessful = usageSuccessful;
      this.message = message;
    }
  }
}
