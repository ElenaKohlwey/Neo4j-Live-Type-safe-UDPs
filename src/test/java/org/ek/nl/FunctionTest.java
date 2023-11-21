package org.ek.nl;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.InputStream;
import java.util.stream.Stream;
import org.ek.nl.Functions.FunctionName;
import org.ek.nl.utility.TestBase;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.neo4j.driver.Session;

/**
 * @author Elena Kohlwey
 * This test class populates the test db once with the content
 * of functionTest.cypher. It checks the functionality of
 * the function getQuest.
 */
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class FunctionTest extends TestBase {

  @Override
  protected InputStream inputStreamOfCypherFile() {
    // read in the cypher file in the folder test/resources
    return getClass().getResourceAsStream("/functionTest.cypher");
  }

  @Override
  protected Stream<Class<?>> procedureClasses() {
    // state the class (or classes) that these tests apply to
    return Stream.of(Functions.class);
  }

  @Override
  protected String[] initialCypher() {
    /*  state an array of cypher queries (e.g. call of a procedure)
    that shall be called before any test is executed */
    return null;
  }

  @Test
  void involvedKnight() {
    String quest;
    try (Session session = driver().session()) {
      quest =
        session
          .run(
            String.format(
              "MATCH (a:Knight {name: 'Knight 1'}) RETURN %s(a) AS quest",
              FunctionName.GET_QUEST
            )
          )
          .single()
          .get("quest")
          .asString();
    }

    assertEquals("Quest", quest);
  }

  @Test
  void freeKnight() {
    String quest;
    try (Session session = driver().session()) {
      quest =
        session
          .run(
            String.format(
              "MATCH (a:Knight {name: 'Knight 2'}) RETURN %s(a) AS quest",
              FunctionName.GET_QUEST
            )
          )
          .single()
          .get("quest")
          .asString();
    }

    assertEquals("none", quest);
  }

  @Test
  void involvedKnightDescriptors() {
    String quest;
    try (Session session = driver().session()) {
      quest =
        session
          .run(
            String.format(
              "MATCH (a:Knight {name: 'Knight 1'}) RETURN %s(a) AS quest",
              FunctionName.GET_QUEST_DESC
            )
          )
          .single()
          .get("quest")
          .asString();
    }

    assertEquals("Quest", quest);
  }
}
