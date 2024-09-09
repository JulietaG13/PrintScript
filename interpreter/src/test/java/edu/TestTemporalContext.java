package edu;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import edu.context.TemporalContext;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.Test;

public class TestTemporalContext {

  private TemporalContext context;

  public void setUp() {
    context = new TemporalContext();
  }

  @Test
  public void testStoreValueAndRetrieve() {
    setUp();
    String key = "testKey";
    String value = "testValue";
    context = context.storeValue(key, value);

    assertTrue(context.hasValue(key));
    assertEquals(value, context.getValue(key));
  }

  @Test
  public void testHasValue() {
    setUp();
    String key = "testKey";
    String value = "testValue";
    context = context.storeValue(key, value);

    assertTrue(context.hasValue(key));
    assertFalse(context.hasValue("anotherKey"));
  }

  @Test
  public void testImmutableContext() {
    setUp();
    String key1 = "key1";
    String value1 = "value1";

    context = context.storeValue(key1, value1);

    String key2 = "key2";
    String value2 = "value2";

    TemporalContext newContext = context.storeValue(key2, value2);

    assertTrue(context.hasValue(key1));
    assertFalse(context.hasValue(key2));

    assertTrue(newContext.hasValue(key1));
    assertTrue(newContext.hasValue(key2));
  }

  @Test
  public void testGetAllValues() {
    setUp();
    Map<String, Object> expectedValues = new HashMap<>();
    expectedValues.put("key1", "value1");
    expectedValues.put("key2", "value2");

    for (Map.Entry<String, Object> entry : expectedValues.entrySet()) {
      context = context.storeValue(entry.getKey(), entry.getValue());
    }

    Map<String, Object> allValues = context.getAllValues();
    assertEquals(expectedValues, allValues);
  }

  @Test
  public void testConstructorWithInitialValues() {
    Map<String, Object> initialValues = new HashMap<>();
    initialValues.put("key1", "value1");
    initialValues.put("key2", 123);

    TemporalContext contextWithValues = new TemporalContext(initialValues);

    assertEquals("value1", contextWithValues.getValue("key1"));
    assertEquals(123, contextWithValues.getValue("key2"));
    assertFalse(contextWithValues.hasValue("key3"));
  }
}
