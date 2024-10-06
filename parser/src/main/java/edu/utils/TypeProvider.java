package edu.utils;

import edu.LexicalRange;
import edu.ast.statements.Type;
import edu.exceptions.IncompatibleTypesException;
import edu.exceptions.InvalidTypeException;
import java.util.HashMap;
import java.util.Map;

public class TypeProvider {
  private static final Map<String, Type> types = new HashMap<>();
  private static final Map<Type, String> names = new HashMap<>();

  static {
    add("number", Type.NUMBER);
    add("string", Type.STRING);
    add("boolean", Type.BOOLEAN);
  }

  public static Type getType(String typeName, LexicalRange range) {
    if (types.containsKey(typeName)) {
      return types.get(typeName);
    }
    throw new InvalidTypeException(typeName, range);
  }

  public static String getName(Type type, LexicalRange range) {
    if (names.containsKey(type)) {
      return names.get(type);
    }
    throw new InvalidTypeException(type, range);
  }

  public static Type getTypeFromContent(String content, LexicalRange range) {
    if (isNumber(content)) {
      return Type.NUMBER;
    }
    if (isString(content)) {
      return Type.STRING;
    }
    if (isBoolean(content)) {
      return Type.BOOLEAN;
    }
    throw new IncompatibleTypesException(content, range);
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

  private static boolean isBoolean(String str) {
    return str.equals("true") || str.equals("false");
  }

  private static void add(String string, Type type) {
    types.put(string, type);
    names.put(type, string);
  }
}
