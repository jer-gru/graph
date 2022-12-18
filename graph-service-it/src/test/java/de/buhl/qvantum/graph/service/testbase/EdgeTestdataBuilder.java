package de.buhl.qvantum.graph.service.testbase;

import de.buhl.qvantum.graph.service.graph.EdgeEntity;
import de.buhl.qvantum.graph.service.graph.EdgeRepository;
import de.buhl.qvantum.graph.service.graph.NodeEntity;

import java.time.Instant;

public class EdgeTestdataBuilder {

  private final EdgeEntity edgeEntity;

  private final EdgeRepository edgeRepository;

  public EdgeTestdataBuilder(final EdgeRepository edgeRepository, final NodeEntity source, final NodeEntity dest) {
    this.edgeRepository = edgeRepository;
    edgeEntity = new EdgeEntity();
    edgeEntity.setSource(source);
    edgeEntity.setDestination(dest);
    edgeEntity.setCreatedAt(Instant.now());
    edgeEntity.setUpdatedAt(Instant.now());
  }

  public EdgeEntity build() {
    return edgeRepository.save(edgeEntity);
  }
}
