package edu;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Map;
import java.util.Set;
import org.junit.jupiter.api.Test;

public class VariableContextTest {

  @Test
  public void testSetAndGetNumberVariable() {
    VariableContext context = new VariableContext(Map.of(), Map.of(), Map.of(), Set.of());
    context = context.setNumberVariable("x", 10);

    assertTrue(context.hasNumberVariable("x"));
    assertEquals(10, context.getNumberVariable("x"));
  }

  @Test
  public void testSetAndGetStringVariable() {
    VariableContext context = new VariableContext(Map.of(), Map.of(), Map.of(), Set.of());
    context = context.setStringVariable("y", "Hello");

    assertTrue(context.hasStringVariable("y"));
    assertEquals("Hello", context.getStringVariable("y"));
  }

  @Test
  public void testGetNonExistentNumberVariableThrowsException() {
    VariableContext context = new VariableContext(Map.of(), Map.of(), Map.of(), Set.of());

    Exception exception =
        assertThrows(
            RuntimeException.class,
            () -> {
              context.getNumberVariable("z");
            });

    assertEquals("Number type variable not found: z", exception.getMessage());
  }

  @Test
  public void testGetNonExistentStringVariableThrowsException() {
    VariableContext context = new VariableContext(Map.of(), Map.of(), Map.of(), Set.of());

    Exception exception =
        assertThrows(
            RuntimeException.class,
            () -> {
              context.getStringVariable("w");
            });

    assertEquals("Number type variable not found: w", exception.getMessage());
  }

  @Test
  public void testImmutabilityOfContext() {
    VariableContext context =
        new VariableContext(Map.of("x", 5), Map.of("y", "Test"), Map.of(), Set.of());
    context = context.setNumberVariable("x", 10);
    context = context.setStringVariable("y", "Updated");

    assertEquals(10, context.getNumberVariable("x"));
    assertEquals("Updated", context.getStringVariable("y"));
    assertEquals(1, context.getNumberVariables().size());
    assertEquals(1, context.getStringVariables().size());
  }

  @Test
  public void testSetAndGetBooleanVariable() {
    VariableContext context = new VariableContext(Map.of(), Map.of(), Map.of(), Set.of());
    context = context.setBooleanVariable("isTrue", true);

    assertTrue(context.hasBooleanVariable("isTrue"));
    assertEquals(true, context.getBooleanVariable("isTrue"));
  }

  @Test
  public void testSetConstantAndCheckImmutability() {
    VariableContext context = new VariableContext(Map.of(), Map.of(), Map.of(), Set.of());
    context = context.setStringVariable("y", "Hello").setConstant("y");

    assertTrue(context.isConstant("y"));
  }

  @Test
  public void testGetNonExistentBooleanVariableThrowsException() {
    VariableContext context = new VariableContext(Map.of(), Map.of(), Map.of(), Set.of());

    Exception exception =
        assertThrows(
            RuntimeException.class,
            () -> {
              context.getBooleanVariable("boolVar");
            });

    assertEquals("Boolean type variable not found: boolVar", exception.getMessage());
  }

  @Test
  public void testSetAndCheckConstants() {
    VariableContext context = new VariableContext(Map.of(), Map.of(), Map.of(), Set.of());
    context = context.setStringVariable("constantVar", "ConstValue").setConstant("constantVar");

    assertTrue(context.isConstant("constantVar"));
  }

  @Test
  public void testConstantDoesNotAffectOtherVariables() {
    VariableContext context = new VariableContext(Map.of("x", 1), Map.of(), Map.of(), Set.of());
    context = context.setNumberVariable("x", 10).setConstant("x");

    assertEquals(10, context.getNumberVariable("x"));
    // Verify that setting a new variable does not affect the constant
    context = context.setStringVariable("y", "Test");
    assertEquals("Test", context.getStringVariable("y"));
  }
}
