package org.ek.nl.descriptors.reps;

import org.rle.neo4jdescriptor.annotation.RepositoryMember;
import org.rle.neo4jdescriptor.entity.LabelDescriptor;
import org.rle.neo4jdescriptor.repository.LabelRepository;

public class LabelRep extends LabelRepository {

  public LabelRep() {
    super();
  }

  @RepositoryMember
  public static final LabelDescriptor Knight = new LabelDescriptor("Knight");

  @RepositoryMember
  public static final LabelDescriptor Quest = new LabelDescriptor("Quest");

  @RepositoryMember
  public static final LabelDescriptor Item = new LabelDescriptor("Item");
}
