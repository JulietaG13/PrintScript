package edu.utils;

import edu.inventory.Inventory;
import edu.reader.InterpreterReader;

public class DeclarationHelperResult {
  private final String varName;
  private final Object value;
  private final InterpreterReader reader;
  private final Inventory inventory;

  public DeclarationHelperResult(
      InterpreterReader reader, Inventory inventory, String varName, Object value) {
    this.reader = reader;
    this.value = value;
    this.varName = varName;
    this.inventory = inventory;
  }

  public String getVarName() {
    return varName;
  }

  public Object getVarValue() {
    return value;
  }

  public InterpreterReader getReader() {
    return reader;
  }

  public Inventory getInventory() {
    return inventory;
  }
}
