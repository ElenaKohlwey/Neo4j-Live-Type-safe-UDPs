package org.ek.nl;

import java.util.stream.Stream;
import org.ek.nl.descriptors.reps.NodeRep;
import org.ek.nl.descriptors.reps.RelationshipRep;
import org.neo4j.graphdb.Transaction;
import org.neo4j.procedure.Context;
import org.neo4j.procedure.Mode;
import org.neo4j.procedure.Procedure;
import org.rle.neo4jdescriptor.ValidationPrintoutWrapper;
import org.rle.neo4jdescriptor.ValidationProcedureBase;
import org.rle.neo4jdescriptor.ValidationReportWrapper;
import org.rle.neo4jdescriptor.repository.NodeRepository;
import org.rle.neo4jdescriptor.repository.RelationshipRepository;

/**
 * This class implements the database validation procedures.
 *
 * @author Jens Deininger
 */
public class Validation extends ValidationProcedureBase {

  @Context
  public Transaction tx;

  private static final NodeRep mNodeRepo = new NodeRep();

  private static final RelationshipRep mRelationRepo = new RelationshipRep();

  @Override
  protected NodeRepository nodeRepository() {
    return mNodeRepo;
  }

  @Override
  protected RelationshipRepository relationshipRepository() {
    return mRelationRepo;
  }

  @Override
  protected Transaction getTransaction() {
    return tx;
  }

  public static class ProcedureName {

    private ProcedureName() {}

    public static final String PRINT_VALIDATION = "org.ek.nl.validate";

    public static final String VALIDATION_REPORT = "org.ek.nl.validationReport";
  }

  @Procedure(mode = Mode.READ, name = ProcedureName.PRINT_VALIDATION)
  public Stream<ValidationPrintoutWrapper> printValidation() {
    return super.streamValidationPrint();
  }

  @Procedure(mode = Mode.READ, name = ProcedureName.VALIDATION_REPORT)
  public Stream<ValidationReportWrapper> validationReport() {
    return super.streamValidationDto();
  }
}
