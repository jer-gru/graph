package de.buhl.qvantum.graph.service.graph;

import de.buhl.qvantum.graph.service.integration.openapi.ApiVersion;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.Builder;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@Tag(name = "graph")
@RequestMapping(path = ApiVersion.V1 + "/graph")
class GraphController {

  @NonNull
  private final GraphService graphService;

  @PostMapping("/add/node/{nodeName}")
  @Operation(summary = "Add node to graph.")
  @ApiResponses({
    @ApiResponse(responseCode = "406", description = "Node already present.")
  })
  void addNode(@PathVariable final String nodeName) {
    graphService.addNode(nodeName);
  }

  @PostMapping("/add/edge/{source}/{destination}")
  @Operation(summary = "Add edge to graph")
  @ApiResponses({
    @ApiResponse(responseCode = "404", description = "Node not found."),
    @ApiResponse(responseCode = "406", description = "Edge already present.")
  })
  void addEdge(@PathVariable final String source, @PathVariable final String destination) {
    graphService.addEdge(source, destination);
  }

  @GetMapping("/get")
  @Operation(summary = "Get full graph.")
  GraphDto getFullGraph() {
    return mapGraph(graphService.getGraph());
  }

  @GetMapping("/get/sorted")
  @Operation(summary = "Get nodes in topological order.")
  GraphDto getSortedGraph() {
    return mapGraph(graphService.getSortedGraph());
  }

  private GraphDto mapGraph(List<NodeEntity> nodes) {
    return GraphDto.builder()
      .name("Qvantum Graph")
      .nodes(nodes.stream()
        .map(this::mapNode)
        .collect(Collectors.toList()))
      .build();
  }

  private NodeDto mapNode(final NodeEntity node) {
    return NodeDto.builder()
      .name(node.getName())
      .edges(node.getEdges().stream()
        .map(EdgeEntity::getDestination)
        .map(NodeEntity::getName)
        .collect(Collectors.toList()))
      .build();
  }

  @Value
  @Builder
  private static class GraphDto {
    String name;
    List<NodeDto> nodes;
  }

  @Value
  @Builder
  private static class NodeDto {
    String name;
    List<String> edges;
  }

}
