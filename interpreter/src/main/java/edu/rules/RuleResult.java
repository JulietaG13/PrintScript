package edu.rules;

import edu.inventory.Inventory;
import edu.reader.InterpreterReader;

public class RuleResult {
  private final boolean result;
  private final InterpreterReader reader;
  private final Inventory inventory;

  public RuleResult(boolean result, InterpreterReader reader, Inventory inventory) {
    this.result = result;
    this.reader = reader;
    this.inventory = inventory;
  }

  public boolean result() {
    return result;
  }

  public InterpreterReader reader() {
    return reader;
  }

  public Inventory inventory() {
    return inventory;
  }
}
