package edu;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import edu.context.VariableContext;
import java.math.BigDecimal;
import java.util.HashMap;
import org.junit.jupiter.api.Test;

public class VariableContextTest {

  @Test
  public void testSetAndGetNumberVariable() {
    VariableContext context =
        new VariableContext(new HashMap<>(), new HashMap<>(), new HashMap<>());

    context = context.setNumberVariable("x", new BigDecimal(42));

    assertTrue(context.hasNumberVariable("x"));
    assertEquals(new BigDecimal(42), context.getNumberVariable("x"));
  }

  @Test
  public void testSetAndGetStringVariable() {
    VariableContext context =
        new VariableContext(new HashMap<>(), new HashMap<>(), new HashMap<>());

    context = context.setStringVariable("str", "Hello, World!");

    assertTrue(context.hasStringVariable("str"));
    assertEquals("Hello, World!", context.getStringVariable("str"));
  }

  @Test
  public void testSetAndGetBooleanVariable() {
    VariableContext context =
        new VariableContext(new HashMap<>(), new HashMap<>(), new HashMap<>());

    context = context.setBooleanVariable("flag", true);

    assertTrue(context.hasBooleanVariable("flag"));
    assertEquals(true, context.getBooleanVariable("flag"));
  }

  @Test
  public void testGetNonExistentNumberVariable() {
    VariableContext context =
        new VariableContext(new HashMap<>(), new HashMap<>(), new HashMap<>());

    Exception exception =
        assertThrows(
            RuntimeException.class,
            () -> {
              context.getNumberVariable("nonExistent");
            });

    assertEquals("Variable of type 'Number' not found: nonExistent", exception.getMessage());
  }

  @Test
  public void testGetNonExistentStringVariable() {
    VariableContext context =
        new VariableContext(new HashMap<>(), new HashMap<>(), new HashMap<>());

    Exception exception =
        assertThrows(
            RuntimeException.class,
            () -> {
              context.getStringVariable("nonExistent");
            });

    assertEquals("Variable of type 'String' not found: nonExistent", exception.getMessage());
  }

  @Test
  public void testGetNonExistentBooleanVariable() {
    VariableContext context =
        new VariableContext(new HashMap<>(), new HashMap<>(), new HashMap<>());

    Exception exception =
        assertThrows(
            RuntimeException.class,
            () -> {
              context.getBooleanVariable("nonExistent");
            });

    assertEquals("Variable of type 'Boolean' not found: nonExistent", exception.getMessage());
  }

  @Test
  public void testWriteNumberVariable() {
    VariableContext context =
        new VariableContext(new HashMap<>(), new HashMap<>(), new HashMap<>());

    context = context.write("numVar", new BigDecimal(99));

    assertTrue(context.hasNumberVariable("numVar"));
    assertEquals(new BigDecimal(99), context.getNumberVariable("numVar"));
  }

  @Test
  public void testWriteStringVariable() {
    VariableContext context =
        new VariableContext(new HashMap<>(), new HashMap<>(), new HashMap<>());

    context = context.write("strVar", "Test String");

    assertTrue(context.hasStringVariable("strVar"));
    assertEquals("Test String", context.getStringVariable("strVar"));
  }

  @Test
  public void testWriteBooleanVariable() {
    VariableContext context =
        new VariableContext(new HashMap<>(), new HashMap<>(), new HashMap<>());

    context = context.write("boolVar", false);

    assertTrue(context.hasBooleanVariable("boolVar"));
    assertEquals(false, context.getBooleanVariable("boolVar"));
  }

  @Test
  public void testWriteUnsupportedType() {
    VariableContext context =
        new VariableContext(new HashMap<>(), new HashMap<>(), new HashMap<>());

    Exception exception =
        assertThrows(
            RuntimeException.class,
            () -> {
              context.write("unsupportedVar", new Object());
            });

    assertEquals("Unsupported variable type: Object", exception.getMessage());
  }
}
