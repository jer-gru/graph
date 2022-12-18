package de.buhl.qvantum.graph.service.error;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class NodeNotFoundException extends RuntimeException {

  public NodeNotFoundException() {
    super("Node not found.");
  }

  public NodeNotFoundException(final String nodeName) {
    super("Node not found: " + nodeName);
  }


}
