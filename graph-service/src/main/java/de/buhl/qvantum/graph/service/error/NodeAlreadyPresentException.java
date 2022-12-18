package de.buhl.qvantum.graph.service.error;

import de.buhl.qvantum.graph.service.graph.NodeEntity;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_ACCEPTABLE)
public class NodeAlreadyPresentException extends RuntimeException {

  public NodeAlreadyPresentException() {
    super("Node already present.");
  }

  public NodeAlreadyPresentException(final String message) {
    super(message);
  }

  public NodeAlreadyPresentException(final NodeEntity node) {
    super("Node already present: " + node);
  }

}
