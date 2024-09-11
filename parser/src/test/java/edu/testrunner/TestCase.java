package edu.testrunner;

import static edu.ParserTestUtil.createInputStreamFromFile;

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
}
