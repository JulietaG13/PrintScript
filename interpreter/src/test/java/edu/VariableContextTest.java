package edu;

import org.junit.jupiter.api.Test;
import java.util.Map;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class VariableContextTest {

  @Test
  public void testSetAndGetNumberVariable() {
    VariableContext context = new VariableContext(Map.of(), Map.of());
    context = context.setNumberVariable("x", 10);

    assertTrue(context.hasNumberVariable("x"));
    assertEquals(10, context.getNumberVariable("x"));
  }

  @Test
  public void testSetAndGetStringVariable() {
    VariableContext context = new VariableContext(Map.of(), Map.of());
    context = context.setStringVariable("y", "Hello");

    assertTrue(context.hasStringVariable("y"));
    assertEquals("Hello", context.getStringVariable("y"));
  }

  @Test
  public void testGetNonExistentNumberVariableThrowsException() {
    VariableContext context = new VariableContext(Map.of(), Map.of());

    Exception exception = assertThrows(RuntimeException.class, () -> {
      context.getNumberVariable("z");
    });

    assertEquals("Variable numérica no encontrada: z", exception.getMessage());
  }

  @Test
  public void testGetNonExistentStringVariableThrowsException() {
    VariableContext context = new VariableContext(Map.of(), Map.of());

    Exception exception = assertThrows(RuntimeException.class, () -> {
      context.getStringVariable("w");
    });

    assertEquals("Variable numérica no encontrada: w", exception.getMessage());
  }

  @Test
  public void testImmutabilityOfContext() {
    VariableContext context = new VariableContext(Map.of("x", 5), Map.of("y", "Test"));
    context = context.setNumberVariable("x", 10);
    context = context.setStringVariable("y", "Updated");

    assertEquals(10, context.getNumberVariable("x"));
    assertEquals("Updated", context.getStringVariable("y"));
    assertEquals(1, context.getNumberVariables().size());
    assertEquals(1, context.getStringVariables().size());
  }
}
