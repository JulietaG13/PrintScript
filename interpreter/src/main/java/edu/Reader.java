package edu;

public class Reader {
  private final VariableContext context;

  public Reader(VariableContext context) {
    this.context = context;
  }

  public Number getNumberVariable(String name) {
    Number value = context.getNumberVariable(name);
    if (value == null) {
      throw new RuntimeException("Variable numérica no encontrada: " + name);
    }
    return value;
  }

  public String getStringVariable(String name) {
    String value = context.getStringVariable(name);
    if (value == null) {
      throw new RuntimeException("Variable de cadena no encontrada: " + name);
    }
    return value;
  }

  public void setNumberVariable(String name, Number value) {
    context.setNumberVariable(name, value);
  }

  public void setStringVariable(String name, String value) {
    context.setStringVariable(name, value);
  }

  public boolean hasNumberVariable(String name) {
    return context.hasNumberVariable(name);
  }

  public boolean hasStringVariable(String name) {
    return context.hasStringVariable(name);
  }
}
