package edu.common;

import static edu.common.Keyword.*;

import java.util.HashMap;
import java.util.Map;

public class KeywordProvider {
  private static final Map<String, Keyword> keywords = new HashMap<>();
  private static final Map<Keyword, String> strings = new HashMap<>();

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
