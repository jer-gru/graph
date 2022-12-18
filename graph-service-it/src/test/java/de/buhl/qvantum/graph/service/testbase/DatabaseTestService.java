package de.buhl.qvantum.graph.service.testbase;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;
import javax.persistence.Table;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
class DatabaseTestService {

  final private List<String> tableNames = new ArrayList<>();
  @Autowired
  private EntityManager entityManager;

  @PostConstruct
  public void postConstruct() {
    tableNames.addAll(entityManager.getMetamodel().getEntities()
      .stream()
      .map(type -> type.getJavaType().getAnnotation(Table.class))
      .filter(Objects::nonNull)
      .map(Table::name)
      .collect(Collectors.toList()));
  }

  @Transactional
  public void truncateTables() {
    entityManager.flush();
    entityManager.createNativeQuery("SET REFERENTIAL_INTEGRITY FALSE").executeUpdate();
    for (final String tableName : tableNames) {
      entityManager.createNativeQuery("TRUNCATE TABLE " + tableName).executeUpdate();
    }
    entityManager.createNativeQuery("SET REFERENTIAL_INTEGRITY TRUE").executeUpdate();
  }
}
