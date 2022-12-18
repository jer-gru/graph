package de.buhl.qvantum.graph.service.graph;

import de.buhl.qvantum.graph.service.testbase.BaseSpringIT;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class GraphServiceTest extends BaseSpringIT {

  @Autowired
  private GraphService graphService;

  @Autowired
  private NodeRepository nodeRepository;

  @Autowired
  private EdgeRepository edgeRepository;

  @Test
  @Transactional
  public void givenNodesAndEdges_whenAddNodesAndEdges_thenGetGraph() {

    graphService.addNode("A");
    graphService.addNode("B");
    graphService.addNode("C");

    graphService.addEdge("A", "B");
    graphService.addEdge("B", "C");

    final List<NodeEntity> nodes = graphService.getGraph();

    assertEquals(3, nodes.size());

    assertEquals("A", nodes.get(0).getName());
    assertEquals("B", nodes.get(0).getEdges().get(0).getDestination().getName());
  }

  @Test
  @Transactional
  public void givenNodesAndEdges_whenAddNodesAndEdges_thenGetSortedGraph() {

    graphService.addNode("D");
    graphService.addNode("B");
    graphService.addNode("A");
    graphService.addNode("C");

    graphService.addEdge("A", "B");
    graphService.addEdge("A", "C");
    graphService.addEdge("B", "C");
    graphService.addEdge("C", "D");

    final List<NodeEntity> nodes = graphService.getSortedGraph();

    assertEquals(4, nodes.size());

    assertEquals("A", nodes.get(0).getName());
    assertEquals("B", nodes.get(0).getEdges().get(0).getDestination().getName());
    assertEquals("D", nodes.get(3).getName());
  }

}
