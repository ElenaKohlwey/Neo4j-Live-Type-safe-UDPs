package org.ek.nl;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.InputStream;
import java.util.stream.Stream;
import org.ek.nl.utility.TestBase;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.neo4j.driver.Record;
import org.neo4j.driver.Session;
import org.rle.neo4jdescriptor.ValidationPrintoutWrapper;
import org.rle.neo4jdescriptor.report.FullReport;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class ValidityTest extends TestBase {

  @Override
  protected InputStream inputStreamOfCypherFile() {
    // read in the cypher file in the folder test/resources
    return getClass().getResourceAsStream("/graphSetup.cql");
  }

  @Override
  protected Stream<Class<?>> procedureClasses() {
    // state the class (or classes) that these tests apply to
    return Stream.of(Validation.class);
  }

  @Override
  protected String[] initialCypher() {
    /*  state an array of cypher queries (e.g. call of a procedure)
    that shall be called before any test is executed */
    return null;
  }

  @Test
  void testValidity() {
    try (Session session = driver().session()) {
      String cypher = String.format(
        "CALL %s() YIELD %s RETURN %s",
        Validation.ProcedureName.PRINT_VALIDATION,
        ValidationPrintoutWrapper.CONTENT_NAME,
        ValidationPrintoutWrapper.CONTENT_NAME
      );
      Record record = session.run(cypher).next();
      String msg = record
        .get(ValidationPrintoutWrapper.CONTENT_NAME)
        .asString();
      assertEquals(FullReport.SM_NO_PROBLEMS, msg);
    }
  }
}
