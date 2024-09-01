package edu;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.util.Iterator;

public class ParserTestUtil {

  public static Iterator<String> createIteratorFromString(String code) {
    return new BufferedReader(new java.io.StringReader(code)).lines().iterator();
  }

  public static Iterator<String> createIteratorFromFile(String filename) {
    try {
      return new BufferedReader(new java.io.FileReader(filename)).lines().iterator();
    } catch (FileNotFoundException e) {
      throw new RuntimeException(e);
    }
  }
}
