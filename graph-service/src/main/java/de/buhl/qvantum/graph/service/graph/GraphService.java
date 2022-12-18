package de.buhl.qvantum.graph.service.graph;

import de.buhl.qvantum.graph.service.error.EdgeAlreadyPresentException;
import de.buhl.qvantum.graph.service.error.NodeAlreadyPresentException;
import de.buhl.qvantum.graph.service.error.NodeNotFoundException;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
class GraphService {

  @NonNull
  private final NodeRepository nodeRepository;
  @NonNull
  private final EdgeRepository edgeRepository;

  @Transactional
  void addNode(final String name) {
    final Optional<NodeEntity> optionalNode = nodeRepository.findByName(name);

    if (optionalNode.isPresent()) {
      throw new NodeAlreadyPresentException(optionalNode.get());
    }

    nodeRepository.save(buildNodeEntity(name));
  }


  private NodeEntity buildNodeEntity(final String name) {
    final NodeEntity nodeEntity = new NodeEntity();
    nodeEntity.setName(name);
    return nodeEntity;
  }

  @Transactional
  void addEdge(final String source, final String destination) {
    final Optional<NodeEntity> optionalSourceNode = nodeRepository.findByName(source);
    final Optional<NodeEntity> optionalDestinationNode = nodeRepository.findByName(destination);

    if (!optionalSourceNode.isPresent()) {
      throw new NodeNotFoundException(source);
    }
    if (!optionalDestinationNode.isPresent()) {
      throw new NodeNotFoundException(destination);
    }

    Optional<EdgeEntity> optionalEdge = edgeRepository.findBySourceAndDestination(optionalSourceNode.get(), optionalDestinationNode.get());

    if (optionalEdge.isPresent()) {
      throw new EdgeAlreadyPresentException(optionalEdge.get());
    }

    edgeRepository.save(buildEdgeEntity(optionalSourceNode.get(), optionalDestinationNode.get()));
  }

  private EdgeEntity buildEdgeEntity(final NodeEntity source, final NodeEntity destination) {
    final EdgeEntity edgeEntity = new EdgeEntity();
    edgeEntity.setSource(source);
    edgeEntity.setDestination(destination);
    source.addEdge(edgeEntity, destination);
    return edgeEntity;
  }

  @Transactional
  List<NodeEntity> getGraph() {
    return nodeRepository.findAll();
  }

  @Transactional
  List<NodeEntity> getSortedGraph() {
    final List<NodeEntity> ordered = new ArrayList<>();

    final List<NodeEntity> nodes = nodeRepository.findAll();


    final Map<NodeEntity, Boolean> visited = nodes.stream()
        .collect(Collectors.toMap(n -> n, b -> false));

    nodes.forEach(n -> {
      if (!visited.get(n)) {
        sort(n, visited, ordered);
      }
    });

    Collections.reverse(ordered);
    return ordered;
  }

  private void sort(NodeEntity node, Map<NodeEntity, Boolean> visited, List<NodeEntity> ordered) {
    visited.replace(node, true);

    node.getEdges()
      .forEach(e -> {
        if (!visited.get(e.getDestination())) {
          sort(e.getDestination(), visited, ordered);
        }
      });

    ordered.add(node);
  }

}
