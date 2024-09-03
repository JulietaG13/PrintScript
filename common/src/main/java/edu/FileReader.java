package edu;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

public class FileReader {

  public static InputStream openFile(String filePath) throws IOException {
    return new FileInputStream(filePath);
  }

  public static String readFileToString(String filePath) throws IOException {
    StringBuilder content = new StringBuilder();
    try (BufferedReader reader = new BufferedReader(new java.io.FileReader(filePath))) {
      String line;
      while ((line = reader.readLine()) != null) {
        content.append(line).append(System.lineSeparator());
      }
    }
    return content.toString();
  }
}
