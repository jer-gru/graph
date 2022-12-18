package de.buhl.qvantum.graph.service.testbase;

import de.buhl.qvantum.graph.service.graph.NodeEntity;
import de.buhl.qvantum.graph.service.graph.NodeRepository;

import java.time.Instant;

public class NodeTestdataBuilder {

  private final NodeEntity node;

  private final NodeRepository nodeRepository;

  public NodeTestdataBuilder(final NodeRepository nodeRepository) {
    this.nodeRepository = nodeRepository;
    node = new NodeEntity();
    node.setCreatedAt(Instant.now());
    node.setUpdatedAt(Instant.now());
  }

  public NodeTestdataBuilder name(final String name) {
    node.setName(name);
    return this;
  }

  public NodeEntity build() {
    return nodeRepository.save(node);
  }

}
