package edu.reader;

public class ReaderResult {
  private final InterpreterReader interpreterReader;
  private final Object value;

  public ReaderResult(InterpreterReader interpreterReader, Object value) {
    this.interpreterReader = interpreterReader;
    this.value = value;
  }

  public InterpreterReader getReader() {
    return interpreterReader;
  }

  public Object getValue() {
    return value;
  }
}
