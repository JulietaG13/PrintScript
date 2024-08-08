package parser.utils;

import parser.ast.statements.Type;

import java.util.HashMap;
import java.util.Map;

public class TypeProvider {
  private final static Map<String, Type> types = new HashMap<>();

  static {
    types.put("Number", Type.NUMBER);
    types.put("String", Type.STRING);
  }

  public static Type getType(String typeName) {
    if (types.containsKey(typeName)) {
      return types.get(typeName);
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
}
