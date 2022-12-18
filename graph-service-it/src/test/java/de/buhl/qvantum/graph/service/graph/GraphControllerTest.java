package de.buhl.qvantum.graph.service.graph;

import de.buhl.qvantum.graph.service.integration.openapi.ApiVersion;
import de.buhl.qvantum.graph.service.testbase.BaseSpringIT;
import de.buhl.qvantum.graph.service.testbase.EdgeTestdataBuilder;
import de.buhl.qvantum.graph.service.testbase.NodeTestdataBuilder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
public class GraphControllerTest extends BaseSpringIT {

  @Autowired
  private MockMvc mockMvc;

  @Autowired
  private NodeRepository nodeRepository;

  @Autowired
  private EdgeRepository edgeRepository;

  @BeforeEach
  public void setup() {
    NodeEntity node1 = new NodeTestdataBuilder(nodeRepository).name("A").build();
    NodeEntity node2 = new NodeTestdataBuilder(nodeRepository).name("B").build();
    NodeEntity node3 = new NodeTestdataBuilder(nodeRepository).name("C").build();

    new EdgeTestdataBuilder(edgeRepository, node1, node2).build();
    new EdgeTestdataBuilder(edgeRepository, node2, node3).build();
  }

  @Test
  public void givenNodes_whenGetGraph_ThenReturnGraph() throws Exception {
    mockMvc.perform(
      get(ApiVersion.V1 + "/graph/get")
        .accept(MediaType.APPLICATION_JSON)
    )
      .andDo(print())
      .andExpect(status().isOk())
      .andExpect(jsonPath("$.nodes.*", hasSize(3)));
  }

}
