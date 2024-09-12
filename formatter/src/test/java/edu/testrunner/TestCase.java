package edu.testrunner;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

public class TestCase {
  private final String inputPath;
  private final String expectedPath;

  public TestCase(String inputPath, String expectedPath) {
    this.inputPath = inputPath;
    this.expectedPath = expectedPath;
  }

  public InputStream getInput() {
    return createInputStreamFromFile(inputPath);
  }

  public InputStream getExpected() {
    return createInputStreamFromFile(expectedPath);
  }

  public String getInputPath() {
    return inputPath;
  }

  public String getExpectedPath() {
    return expectedPath;
  }

  private static InputStream createInputStreamFromFile(String filename) {
    try {
      return new FileInputStream(filename);
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }
}
