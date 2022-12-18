package de.buhl.qvantum.graph.service.graph;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

public interface EdgeRepository extends JpaRepository<EdgeEntity, Long> {

  Optional<EdgeEntity> findBySourceAndDestination(final NodeEntity source, final NodeEntity destination);
  List<EdgeEntity> findByDestination(final NodeEntity destination);

}
