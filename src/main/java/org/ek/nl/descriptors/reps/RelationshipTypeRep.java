package org.ek.nl.descriptors.reps;

import org.rle.neo4jdescriptor.annotation.RepositoryMember;
import org.rle.neo4jdescriptor.entity.RelationshipTypeDescriptor;
import org.rle.neo4jdescriptor.repository.RelationshipTypeRepository;

public class RelationshipTypeRep extends RelationshipTypeRepository {

  public RelationshipTypeRep() {
    super();
  }

  @RepositoryMember
  public static final RelationshipTypeDescriptor PRECEDES = new RelationshipTypeDescriptor(
    "PRECEDES"
  );

  @RepositoryMember
  public static final RelationshipTypeDescriptor IS_NEEDED_FOR = new RelationshipTypeDescriptor(
    "IS_NEEDED_FOR"
  );

  @RepositoryMember
  public static final RelationshipTypeDescriptor USES = new RelationshipTypeDescriptor(
    "USES"
  );

  @RepositoryMember
  public static final RelationshipTypeDescriptor IS_INVOLVED_IN = new RelationshipTypeDescriptor(
    "IS_INVOLVED_IN"
  );
}
