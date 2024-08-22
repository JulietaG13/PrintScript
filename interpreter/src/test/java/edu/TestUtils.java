package edu;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import edu.common.Operator;
import edu.utils.OperatorProvider;
import org.junit.jupiter.api.Test;

public class TestUtils {

  @Test
  public void testGetOperatorValid() {
    assertEquals(Operator.SUM, OperatorProvider.getOperator("+"));
    assertEquals(Operator.SUBTRACT, OperatorProvider.getOperator("-"));
    assertEquals(Operator.MULTIPLY, OperatorProvider.getOperator("*"));
    assertEquals(Operator.DIVIDE, OperatorProvider.getOperator("/"));
  }

  @Test
  public void testGetOperatorInvalid() {
    Exception exception =
        assertThrows(
            IllegalArgumentException.class,
            () -> {
              OperatorProvider.getOperator("%");
            });
    assertEquals("Unknown operator: %", exception.getMessage());
  }
}
