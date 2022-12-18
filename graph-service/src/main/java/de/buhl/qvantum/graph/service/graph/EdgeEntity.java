package de.buhl.qvantum.graph.service.graph;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import java.time.Instant;

@Getter
@Setter
@Entity
@Table(name = "edge")
@ToString(onlyExplicitlyIncluded = true)
@EntityListeners(AuditingEntityListener.class)
public class EdgeEntity {

  @Id
  @ToString.Include
  @SequenceGenerator(name = "edge_seq")
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "edge_seq")
  private Long id;

  @ToString.Include
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "source_id", referencedColumnName = "id", nullable = false)
  private NodeEntity source;

  @ToString.Include
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "destination_id", referencedColumnName = "id", nullable = false)
  private NodeEntity destination;

  @CreatedDate
  @Column(nullable = false)
  private Instant createdAt;
  @LastModifiedDate
  @Column(nullable = false)
  private Instant updatedAt;


}
