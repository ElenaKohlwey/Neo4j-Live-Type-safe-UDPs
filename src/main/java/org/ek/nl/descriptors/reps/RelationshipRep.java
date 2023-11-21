package org.ek.nl.descriptors.reps;

import org.ek.nl.descriptors.relations.IsInvolvedInDescriptor;
import org.ek.nl.descriptors.relations.IsNeededForDescriptor;
import org.ek.nl.descriptors.relations.PrecedesDescriptor;
import org.ek.nl.descriptors.relations.UsesDescriptor;
import org.rle.neo4jdescriptor.annotation.RepositoryMember;

public class RelationshipRep {

  private RelationshipRep() {
    super();
  }

  @RepositoryMember
  public static final IsInvolvedInDescriptor IsInvolvedIn = new IsInvolvedInDescriptor();

  @RepositoryMember
  public static final UsesDescriptor Uses = new UsesDescriptor();

  @RepositoryMember
  public static final PrecedesDescriptor Precedes = new PrecedesDescriptor();

  @RepositoryMember
  public static final IsNeededForDescriptor IsNeededFor = new IsNeededForDescriptor();
}
