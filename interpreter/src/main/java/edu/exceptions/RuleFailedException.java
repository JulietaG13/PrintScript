package edu.exceptions;

public class RuleFailedException extends Exception {

  public RuleFailedException(String ruleName, String nodeName) {
    super(createMessage(ruleName, nodeName));
  }

  public RuleFailedException(String ruleName, String nodeName, Throwable cause) {
    super(createMessage(ruleName, nodeName), cause);
  }

  private static String createMessage(String ruleName, String nodeName) {
    return "Rule '" + ruleName + "' failed for node: " + nodeName;
  }
}
