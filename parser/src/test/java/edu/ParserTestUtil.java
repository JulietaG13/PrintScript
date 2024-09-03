package edu;

import java.io.ByteArrayInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

public class ParserTestUtil {

  public static InputStream createInputStreamFromString(String code) {
    return new ByteArrayInputStream(code.getBytes());
  }

  public static InputStream createInputStreamFromFile(String filename) {
    try {
      return new FileInputStream(filename);
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }
}
