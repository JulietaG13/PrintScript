package edu.utils;

import edu.ast.statements.Kind;
import java.util.HashMap;
import java.util.Map;

public class KindProvider {
  private static final Map<String, Kind> kinds = new HashMap<>();

  static {
    kinds.put("let", Kind.LET);
    kinds.put("const", Kind.CONST);
  }

  public static Kind getKind(String kindName) {
    if (kinds.containsKey(kindName)) {
      return kinds.get(kindName);
    }
    throw new IllegalArgumentException("Unknown kind: " + kindName);
  }
}
