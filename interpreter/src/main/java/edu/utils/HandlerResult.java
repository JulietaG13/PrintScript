package edu.utils;

import edu.inventory.Inventory;
import edu.reader.InterpreterReader;

public class HandlerResult {
  private final InterpreterReader interpreterReader;
  private final Inventory inventory;

  public HandlerResult(InterpreterReader interpreterReader, Inventory inventory) {
    this.interpreterReader = interpreterReader;
    this.inventory = inventory;
  }

  public InterpreterReader getInterpreterReader() {
    return interpreterReader;
  }

  public Inventory getInventory() {
    return inventory;
  }
}
