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

  public List<String> getMessages() {
    return messages;
  }

  public void printReport() {
    if (messages.isEmpty()) {
      System.out.println("No issues found.");
    } else {
      System.out.println("Static Code Analysis Report:");
      for (String message : messages) {
        System.out.println(message);
      }
    }
  }

  public Optional<List<String>> getReport() {
    if (messages.isEmpty()) {
      return Optional.empty();
    } else {
      return Optional.of(messages);
    }
  }
}
