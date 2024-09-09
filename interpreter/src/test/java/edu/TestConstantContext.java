package edu;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import edu.context.ConstantContext;
import java.util.HashSet;
import java.util.Set;
import org.junit.jupiter.api.Test;

public class TestConstantContext {
  private ConstantContext context;

  public void setUp() {
    Set<String> initialConstants = new HashSet<>();
    initialConstants.add("PI");
    initialConstants.add("E");
    context = new ConstantContext(initialConstants);
  }

  @Test
  public void testHasConstant() {
    setUp();
    assertTrue(context.hasConstant("PI"));
    assertFalse(context.hasConstant("GOLDEN_RATIO"));
  }

  @Test
  public void testAddConstant() {
    setUp();
    context = context.addConstant("GOLDEN_RATIO");

    assertTrue(context.hasConstant("GOLDEN_RATIO"));
    assertTrue(context.hasConstant("PI"));
    assertFalse(context.hasConstant("NEW_CONSTANT"));
  }

  @Test
  public void testGetConstants() {
    setUp();
    Set<String> expectedConstants = new HashSet<>();
    expectedConstants.add("PI");
    expectedConstants.add("E");

    Set<String> actualConstants = context.getConstants();
    assertEquals(expectedConstants, actualConstants);

    boolean exceptionThrown = false;
    try {
      actualConstants.add("NEW_CONSTANT");
    } catch (UnsupportedOperationException e) {
      exceptionThrown = true;
    }
    assertTrue(exceptionThrown);
  }

  @Test
  public void testImmutableContext() {
    setUp();
    ConstantContext newContext = context.addConstant("SPEED_OF_LIGHT");

    assertFalse(context.hasConstant("SPEED_OF_LIGHT"));
    assertTrue(newContext.hasConstant("SPEED_OF_LIGHT"));

    assertTrue(context.hasConstant("PI"));
    assertTrue(newContext.hasConstant("PI"));
  }
}
