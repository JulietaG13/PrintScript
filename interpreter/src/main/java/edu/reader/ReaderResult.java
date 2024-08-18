package edu.reader;

public class ReaderResult {
  private final Reader reader;
  private final Object value;

  public ReaderResult(Reader reader, Object value) {
    this.reader = reader;
    this.value = value;
  }

  public Reader getReader() {
    return reader;
  }

  public Object getValue() {
    return value;
  }
}
