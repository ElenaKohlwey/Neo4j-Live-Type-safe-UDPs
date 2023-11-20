package org.ek.nl;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;
import org.ek.nl.Procedures.Output;
import org.ek.nl.Procedures.ProcedureName;
import org.ek.nl.utility.TestBase;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.neo4j.driver.Session;

/**
 * @author Elena Kohlwey
 * This test class populates the test db once with the content
 * of procedureTest.cypher. It checks the functionality of
 * the procedure useItem.
 */
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class ProcedureTest extends TestBase {

  @Override
  protected InputStream inputStreamOfCypherFile() {
    // read in the cypher file in the folder test/resources
    return getClass().getResourceAsStream("/procedureTest.cypher");
  }

  @Override
  protected Stream<Class<?>> procedureClasses() {
    // state the class (or classes) that these tests apply to
    return Stream.of(Procedures.class);
  }

  @Override
  protected String[] initialCypher() {
    /*  state an array of cypher queries (e.g. call of a procedure)
    that shall be called before any test is executed */
    return null;
  }

  static final String KNIGHT_1 = "Knight 1";
  static final String KNIGHT_2 = "Knight 2";
  static final String KNIGHT_3 = "Knight 3";
  static final String TYPE_WEAPON = "weapon";
  static final String TYPE_ARMOUR = "armour";
  static final String TYPE_SHIELD = "shield";
  static final String WEAPON_NAME = "new Sword";
  static final String WEAPON_OLD_NAME = "Bow";
  static final String ARMOUR_NAME = "new Armour";
  static final String SHIELD_NAME = "new Shield";

  HashMap<String, String[]> combos;
  HashMap<String, Boolean> results;

  @BeforeAll
  public void initializeResults() {
    combos = new HashMap<>();
    results = new HashMap<>();
    String KEY_1 =
      "Add new weapon to a knight who has one weapon and no shield.";
    combos.put(KEY_1, new String[] { KNIGHT_1, TYPE_WEAPON, WEAPON_NAME });
    results.put(KEY_1, true);
    String KEY_2 =
      "Add new weapon to a knight who has two weapons and no shield.";
    combos.put(KEY_2, new String[] { KNIGHT_2, TYPE_WEAPON, WEAPON_NAME });
    results.put(KEY_2, false);
    String KEY_3 =
      "Add a weapon to a knight who already has this weapon within his two weapons.";
    combos.put(KEY_3, new String[] { KNIGHT_2, TYPE_WEAPON, WEAPON_OLD_NAME });
    results.put(KEY_3, true);
    String KEY_4 = "Add an armour to a knight who does not have an armour.";
    combos.put(KEY_4, new String[] { KNIGHT_1, TYPE_ARMOUR, ARMOUR_NAME });
    results.put(KEY_4, true);
    String KEY_5 = "Add an armour to a knight who already has an armour.";
    combos.put(KEY_5, new String[] { KNIGHT_2, TYPE_ARMOUR, ARMOUR_NAME });
    results.put(KEY_5, false);
    String KEY_6 = "Add a shield to a knight who has one weapon and no shield.";
    combos.put(KEY_6, new String[] { KNIGHT_1, TYPE_SHIELD, SHIELD_NAME });
    results.put(KEY_6, true);
    String KEY_7 = "Add a shield to a knight who already has two weapons.";
    combos.put(KEY_7, new String[] { KNIGHT_2, TYPE_SHIELD, SHIELD_NAME });
    results.put(KEY_7, false);
    String KEY_8 = "Add a shield to a knight who already has two weapons.";
    combos.put(KEY_8, new String[] { KNIGHT_3, TYPE_SHIELD, SHIELD_NAME });
    results.put(KEY_8, false);
  }

  // resets database after each test
  public void resetDatabase() {
    // delete all nodes from the db
    try (Session session = driver().session()) {
      session.run("MATCH (a) DETACH DELETE a");
    }

    // read in cypher file and populate db
    StringWriter sw = new StringWriter();
    InputStream cypherInputStream = getClass()
      .getResourceAsStream("/procedureTest.cypher");
    if (cypherInputStream != null) {
      try {
        new BufferedReader(new InputStreamReader(cypherInputStream))
          .transferTo(sw);
      } catch (Exception e) {}
      try (Session session = driver().session()) {
        session.run(sw.toString());
      }
      try {
        cypherInputStream.close();
      } catch (Exception e) {}
    }
  }

  @Test
  void testUsage() {
    boolean usageSuccessful;
    for (Map.Entry<String, String[]> entry : combos.entrySet()) {
      String key = entry.getKey();
      String[] combo = entry.getValue();
      boolean value = results.get(key);

      try (Session session = driver().session()) {
        usageSuccessful =
          session
            .run(
              String.format(
                "MATCH (knight:Knight {name: '%s'}), (item:Item {type:'%s', name: '%s'}) CALL %s(knight,item) YIELD %s RETURN %s AS %s",
                combo[0],
                combo[1],
                combo[2],
                ProcedureName.USE_ITEM,
                Output.USAGE_SUCCESSFUL,
                Output.USAGE_SUCCESSFUL,
                Output.USAGE_SUCCESSFUL
              )
            )
            .single()
            .get(Output.USAGE_SUCCESSFUL)
            .asBoolean();
      }

      assertEquals(value, usageSuccessful);

      resetDatabase();
    }
  }
}
