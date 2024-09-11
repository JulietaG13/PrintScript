package edu;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class Report {
  private List<String> messages;

  public Report() {
    this.messages = new ArrayList<>();
  }

  public void addMessage(String message) {
    messages.add(message);
  }

  public Optional<List<String>> getReport() {
    if (messages.isEmpty()) {
      return Optional.empty();
    } else {
      return Optional.of(messages);
    }
  }
}
