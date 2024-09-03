package edu;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Iterator;
import java.util.NoSuchElementException;

public class Reader implements Iterator<String> {
  private final BufferedReader reader;
  private String nextLine;

  public Reader(InputStream stream) {
    this.reader = new BufferedReader(new InputStreamReader(stream));
    advance();
  }

  private void advance() {
    try {
      nextLine = reader.readLine();
    } catch (IOException e) {
      nextLine = null;
      throw new RuntimeException("Error reading from input stream", e);
    }
  }

  @Override
  public boolean hasNext() {
    return nextLine != null;
  }

  @Override
  public String next() {
    if (!hasNext()) {
      throw new NoSuchElementException();
    }
    String currentLine = nextLine;
    advance();
    return currentLine;
  }
}
