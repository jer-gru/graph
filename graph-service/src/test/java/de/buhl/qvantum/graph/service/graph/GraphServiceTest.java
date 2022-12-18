package de.buhl.qvantum.graph.service.graph;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class GraphServiceTest {

  @InjectMocks
  private GraphService graphService;

  @Mock
  private NodeRepository nodeRepository;
  @Mock
  private EdgeRepository edgeRepository;

  @Test
  public void givenNodesWithEdges_whenTopologicalSorting_thenReturnOrderedGraph() {

    // given
    final NodeEntity nodeEntity1 = new NodeEntity();
    nodeEntity1.setName("C");
    final NodeEntity nodeEntity2 = new NodeEntity();
    nodeEntity2.setName("B");
    final NodeEntity nodeEntity3 = new NodeEntity();
    nodeEntity3.setName("A");
    final NodeEntity nodeEntity4 = new NodeEntity();
    nodeEntity4.setName("D");

    nodeEntity1.setEdges(Collections.singletonList(buildEdgeEntity(nodeEntity1, nodeEntity4)));
    nodeEntity2.setEdges(Collections.singletonList(buildEdgeEntity(nodeEntity2, nodeEntity4)));
    nodeEntity3.setEdges(Arrays.asList(buildEdgeEntity(nodeEntity3, nodeEntity1), buildEdgeEntity(nodeEntity3, nodeEntity2)));
    nodeEntity4.setEdges(Collections.emptyList());

    final List<NodeEntity> nodeEntities = Arrays.asList(nodeEntity1, nodeEntity2, nodeEntity3, nodeEntity4);

    // when

    when(nodeRepository.findAll()).thenReturn(nodeEntities);

    // then

    final List<NodeEntity> sorted = graphService.getSortedGraph();

    assertEquals(4, sorted.size());

    assertEquals("A", sorted.get(0).getName());
    assertEquals("B", sorted.get(1).getName());
    assertEquals("C", sorted.get(2).getName());
    assertEquals("D", sorted.get(3).getName());

  }

  private EdgeEntity buildEdgeEntity(final NodeEntity source, final NodeEntity destination) {
    final EdgeEntity edgeEntity = new EdgeEntity();
    edgeEntity.setSource(source);
    edgeEntity.setDestination(destination);
    return edgeEntity;
  }


}
