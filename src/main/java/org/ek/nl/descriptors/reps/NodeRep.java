package org.ek.nl.descriptors.reps;

import org.ek.nl.descriptors.nodes.ItemDescriptor;
import org.ek.nl.descriptors.nodes.KnightDescriptor;
import org.ek.nl.descriptors.nodes.QuestDescriptor;
import org.rle.neo4jdescriptor.annotation.RepositoryMember;
import org.rle.neo4jdescriptor.repository.NodeRepository;

public class NodeRep extends NodeRepository {

  public NodeRep() {
    super();
  }

  @RepositoryMember
  public static final ItemDescriptor Item = new ItemDescriptor();

  @RepositoryMember
  public static final QuestDescriptor Quest = new QuestDescriptor();

  @RepositoryMember
  public static final KnightDescriptor Knight = new KnightDescriptor();
}
