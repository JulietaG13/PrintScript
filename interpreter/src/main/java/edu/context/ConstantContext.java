package edu.context;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class ConstantContext implements Context {
  private final Set<String> constants;

  public ConstantContext(Set<String> constants) {
    this.constants = Collections.unmodifiableSet(new HashSet<>(constants));
  }

  public ConstantContext addConstant(String constant) {
    Set<String> newConstants = new HashSet<>(constants);
    newConstants.add(constant);
    return new ConstantContext(newConstants);
  }

  public boolean hasConstant(String constant) {
    return constants.contains(constant);
  }

  public Set<String> getConstants() {
    return constants;
  }
}
