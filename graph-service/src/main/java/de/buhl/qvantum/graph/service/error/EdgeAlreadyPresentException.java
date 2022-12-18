package de.buhl.qvantum.graph.service.error;

import de.buhl.qvantum.graph.service.graph.EdgeEntity;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_ACCEPTABLE)
public class EdgeAlreadyPresentException extends RuntimeException {

  public EdgeAlreadyPresentException() {
    super("Edge already present.");
  }

  public EdgeAlreadyPresentException(final EdgeEntity edgeEntity) {
    super("Edge already present: " + edgeEntity);
  }

}
