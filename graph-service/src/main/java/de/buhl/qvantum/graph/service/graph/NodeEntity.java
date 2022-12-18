package de.buhl.qvantum.graph.service.graph;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
@Entity
@Table(name = "node")
@ToString(onlyExplicitlyIncluded = true)
@EntityListeners(AuditingEntityListener.class)
public class NodeEntity {

  @Id
  @ToString.Include
  @SequenceGenerator(name = "node_seq")
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "node_seq")
  private Long id;

  @ToString.Include
  @Column(nullable = false)
  private String name;

  @OneToMany(mappedBy = "source", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
  private List<EdgeEntity> edges = new ArrayList<>();

  @CreatedDate
  @Column(nullable = false)
  private Instant createdAt;
  @LastModifiedDate
  @Column(nullable = false)
  private Instant updatedAt;

  public void addEdge(final EdgeEntity edgeEntity, final NodeEntity dest) {
    edges.add(edgeEntity);
    edgeEntity.setSource(this);
    edgeEntity.setDestination(dest);
  }
}
