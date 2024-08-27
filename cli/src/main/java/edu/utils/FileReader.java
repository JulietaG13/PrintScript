package edu.utils;

import java.io.BufferedReader;
import java.io.IOException;

public class FileReader {
  public static String readFileToString(String filePath) throws IOException {
    StringBuilder content = new StringBuilder();
    try (BufferedReader reader = new BufferedReader(new java.io.FileReader(filePath))) {
      String line;
      while ((line = reader.readLine()) != null) {
        content.append(line).append("\n");
      }
    }
    return content.toString();
  }
}
