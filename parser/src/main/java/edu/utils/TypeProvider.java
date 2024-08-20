package edu.utils;

import edu.ast.statements.Type;

import java.util.HashMap;
import java.util.Map;

public class TypeProvider {
  private static final Map<String, Type> types = new HashMap<>();
  private static final Map<Type, String> names = new HashMap<>();

  static {
    add("Number", Type.NUMBER);
    add("String", Type.STRING);
  }

  public static Type getType(String typeName) {
    if (types.containsKey(typeName)) {
      return types.get(typeName);
    }
    throw new RuntimeException(); // TODO
  }

  public static String getName(Type type) {
    if (names.containsKey(type)) {
      return names.get(type);
    }
    throw new RuntimeException(); // TODO
  }

  public static Type getTypeFromContent(String content) {
    if (isNumber(content)) {
      return Type.NUMBER;
    }
    if (isString(content)) {
      return Type.STRING;
    }
    throw new RuntimeException(); // TODO
  }

  private static boolean isNumber(String str) {
    if (str == null || str.isEmpty()) {
      return false;
    }
    boolean foundDot = false;
    for (char c : str.toCharArray()) {
      if (!Character.isDigit(c)) {
        if (c == '.' && !foundDot) {
          foundDot = true;
        } else {
          return false;
        }
      }
    }
    return true;
  }

  private static boolean isString(String str) {
    return str.startsWith("\"") && str.endsWith("\"");
  }

  private static void add(String string, Type type) {
    types.put(string, type);
    names.put(type, string);
  }
}
