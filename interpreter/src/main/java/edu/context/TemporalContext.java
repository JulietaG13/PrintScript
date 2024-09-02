package edu.context;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class TemporalContext implements Context {
  private final Map<String, Object> temporaryValues;

  public TemporalContext() {
    this.temporaryValues = new HashMap<>();
  }

  public TemporalContext(Map<String, Object> temporaryValues) {
    this.temporaryValues = Collections.unmodifiableMap(new HashMap<>(temporaryValues));
  }

  public TemporalContext storeValue(String key, Object value) {
    Map<String, Object> newTemporaryValues = new HashMap<>(this.temporaryValues);
    newTemporaryValues.put(key, value);
    return new TemporalContext(newTemporaryValues);
  }

  public Object getValue(String key) {
    if (!temporaryValues.containsKey(key)) {
      throw new RuntimeException("Temporary value not found: " + key);
    }
    return temporaryValues.get(key);
  }

  public boolean hasValue(String key) {
    return temporaryValues.containsKey(key);
  }

  public Map<String, Object> getAllValues() {
    return temporaryValues;
  }
}
