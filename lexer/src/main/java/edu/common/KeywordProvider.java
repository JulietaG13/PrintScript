package edu.common;

import java.util.HashMap;
import java.util.Map;

import static edu.common.Keyword.*;

public class KeywordProvider {
  private final static Map<String, Keyword> keywords = new HashMap<>();
  private final static Map<Keyword, String> strings = new HashMap<>();

  static {
    add("let", LET);
    add("String", STRING);
    add("Number", NUMBER);
  }

  public static Keyword getKeyword(String name) {
    if (keywords.containsKey(name)) {
      return keywords.get(name);
    }
    throw new RuntimeException(); // TODO
  }

  public static String getKeywordName(Keyword keyword) {
    if (strings.containsKey(keyword)) {
      return strings.get(keyword);
    }
    throw new RuntimeException(); // TODO
  }

  private static void add(String string, Keyword keyword) {
    keywords.put(string, keyword);
    strings.put(keyword, string);
  }
}