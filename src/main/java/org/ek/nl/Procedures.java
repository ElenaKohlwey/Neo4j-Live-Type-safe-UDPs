package org.ek.nl;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.apache.commons.lang3.NotImplementedException;
import org.ek.nl.descriptors.nodes.ItemKind;
import org.ek.nl.descriptors.reps.NodeRep;
import org.ek.nl.descriptors.reps.RelationshipRep;
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

    public static final String USE_ITEM_DESC = "org.ek.nl.useItemDesc";
  }

  public static class ProcedureDescription {

    private ProcedureDescription() {}

    public static final String USE_ITEM =
      "Make the specified Knight use the specified Item";
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
  @Description(ProcedureDescription.USE_ITEM)
  public Stream<Output> useItem(
    @Name("knight") Node knight,
    @Name("newItem") Node newItem
  ) {
    boolean usageSuccessful = false;
    Set<Node> items;
    String msg = null;
    String now = new SimpleDateFormat("yyyyMMdd_HHmmss")
      .format(Calendar.getInstance().getTime());
    int countArmour = 0;
    int countWeapon = 0;
    int countShield = 0;

    // fetch knight name
    String knightName = (String) knight.getProperty("name");

    // fetch newItem info
    String newItemType = (String) newItem.getProperty("type");
    String newItemName = (String) newItem.getProperty("name");

    // fetch items of knight
    Iterable<Relationship> usesRels = knight.getRelationships(
      Direction.OUTGOING,
      GraphRelationshipTypes.USES
    );
    items = new HashSet<>();
    for (Relationship usesRel : usesRels) {
      Node item = usesRel.getEndNode();
      items.add(item);
    }

    // check if knight is already using the newItem
    if (items.contains(newItem)) {
      msg =
        String.format(
          MESSAGE_SUCCESS_OLD,
          knightName,
          newItemName,
          newItemType
        );
      return Stream.of(new Output(true, msg));
    }

    // count items by ItemKind
    for (Node item : items) {
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
    }

    // check whether item could additionally be used
    switch (newItemType) {
      case "armour":
        if (countArmour == 0) {
          Relationship newRel = knight.createRelationshipTo(
            newItem,
            GraphRelationshipTypes.USES
          );
          newRel.setProperty("since", now);
          usageSuccessful = true;
          msg = MESSAGE_SUCCESS_NEW;
        } else {
          msg = MESSAGE_FAIL_ARMOUR;
        }
        break;
      case "weapon":
        if (countWeapon + countShield < 2) {
          Relationship newRel = knight.createRelationshipTo(
            newItem,
            GraphRelationshipTypes.USES
          );
          newRel.setProperty("since", now);
          usageSuccessful = true;
          msg = MESSAGE_SUCCESS_NEW;
        } else if (countWeapon >= 2) {
          msg = MESSAGE_FAIL_TWO_WEAPONS;
        } else {
          msg = MESSAGE_FAIL_ONE_WEAPON_ONE_SHIELD;
        }
        break;
      case "shield":
        if (countWeapon < 2 && countShield == 0) {
          Relationship newRel = knight.createRelationshipTo(
            newItem,
            GraphRelationshipTypes.USES
          );
          newRel.setProperty("since", now);
          usageSuccessful = true;
          msg = MESSAGE_SUCCESS_NEW;
        } else if (countWeapon >= 2) {
          msg = MESSAGE_FAIL_TWO_WEAPONS;
        } else {
          msg = MESSAGE_FAIL_ONE_SHIELD;
        }
        break;
      case "potion":
        usageSuccessful = true;
        break;
      default:
        break;
    }
    msg = String.format(msg, knightName, newItemName, newItemType);
    return Stream.of(new Output(usageSuccessful, msg));
  }

  @Procedure(mode = Mode.WRITE, name = ProcedureName.USE_ITEM_DESC)
  @Description(ProcedureDescription.USE_ITEM)
  public Stream<Output> useItemDesc(
    @Name("knight") Node knight,
    @Name("newItem") Node newItem
  ) {
    boolean usageSuccessful = false;
    Set<Node> items;
    String msg = null;
    String now = new SimpleDateFormat("yyyyMMdd_HHmmss")
      .format(Calendar.getInstance().getTime());
    int countArmour = 0;
    int countWeapon = 0;
    int countShield = 0;

    // fetch knight name
    String knightName = NodeRep.Knight.prpName.getValueOn(knight);

    // fetch newItem info
    ItemKind newItemType = NodeRep.Item.prpItemKind.getValueOn(newItem);
    String newItemName = NodeRep.Item.prpName.getValueOn(newItem);

    // fetch items of knight
    items = NodeRep.Knight.usedItems(knight).collect(Collectors.toSet());

    // check if knight is already using the newItem
    if (items.contains(newItem)) {
      msg =
        String.format(
          MESSAGE_SUCCESS_OLD,
          knightName,
          newItemName,
          newItemType.dbValue()
        );
      return Stream.of(new Output(true, msg));
    }

    // count items by ItemKind
    for (Node item : items) {
      ItemKind itemKind = NodeRep.Item.prpItemKind.getValueOn(item);
      switch (itemKind) {
        case ARMOUR:
          countArmour++;
          break;
        case WEAPON:
          countWeapon++;
          break;
        case SHIELD:
          countShield++;
          break;
        default:
          break;
      }
    }

    // check whether item could additionally be used
    switch (newItemType) {
      case ARMOUR:
        if (countArmour == 0) {
          RelationshipRep.Uses.create(knight, newItem, now);
          usageSuccessful = true;
          msg = MESSAGE_SUCCESS_NEW;
        } else {
          msg = MESSAGE_FAIL_ARMOUR;
        }
        break;
      case WEAPON:
        if (countWeapon + countShield < 2) {
          RelationshipRep.Uses.create(knight, newItem, now);
          usageSuccessful = true;
          msg = MESSAGE_SUCCESS_NEW;
        } else if (countWeapon >= 2) {
          msg = MESSAGE_FAIL_TWO_WEAPONS;
        } else {
          msg = MESSAGE_FAIL_ONE_WEAPON_ONE_SHIELD;
        }
        break;
      case SHIELD:
        if (countWeapon < 2 && countShield == 0) {
          RelationshipRep.Uses.create(knight, newItem, now);
          usageSuccessful = true;
          msg = MESSAGE_SUCCESS_NEW;
        } else if (countWeapon >= 2) {
          msg = MESSAGE_FAIL_TWO_WEAPONS;
        } else {
          msg = MESSAGE_FAIL_ONE_SHIELD;
        }
        break;
      case POTION:
        usageSuccessful = true;
        break;
      default:
        throw new NotImplementedException("Not all enum items used.");
    }
    msg = String.format(msg, knightName, newItemName, newItemType.dbValue());
    return Stream.of(new Output(usageSuccessful, msg));
  }

  @SuppressWarnings("java:S1104") // complains about there being public non static non final fields and no accessors. But Neo4j needs those in its wrapper objects
  public class Output {

    public static final String USAGE_SUCCESSFUL = "usageSuccessful";
    public static final String MESSAGE = "msg";

    /* The name of the public field below must always be the same as the static String above!
     * This String is needed in the cypher to get to the content of the returned values. */
    public boolean usageSuccessful;
    public String msg;

    public Output(boolean usageSuccessful, String msg) {
      this.usageSuccessful = usageSuccessful;
      this.msg = msg;
    }
  }
}
