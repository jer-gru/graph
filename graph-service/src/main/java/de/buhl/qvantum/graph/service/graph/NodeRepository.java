package de.buhl.qvantum.graph.service.graph;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface NodeRepository extends JpaRepository<NodeEntity, Long> {

  Optional<NodeEntity> findByName(final String name);

}
