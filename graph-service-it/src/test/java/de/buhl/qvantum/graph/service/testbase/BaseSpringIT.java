package de.buhl.qvantum.graph.service.testbase;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@Slf4j
@ActiveProfiles("test")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class BaseSpringIT {

  @Autowired
  private DatabaseTestService databaseTestService;

  @BeforeEach
  public void truncateDbTables() {
    databaseTestService.truncateTables();
  }


}
