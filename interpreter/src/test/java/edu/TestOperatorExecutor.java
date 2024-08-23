package edu;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import edu.utils.OperatorExecutor;
import edu.utils.OperatorProvider;
import org.junit.jupiter.api.Test;

public class TestOperatorExecutor {

  @Test
  public void testSumOperation() {
    Operator sumOperator = OperatorProvider.getOperator("+");
    Object result = OperatorExecutor.execute(sumOperator, 5, 3);
    assertEquals(8.0, result); // Asumimos que los resultados se devuelven como Double
  }

  @Test
  public void testSubtractOperation() {
    Operator subtractOperator = OperatorProvider.getOperator("-");
    Object result = OperatorExecutor.execute(subtractOperator, 5, 3);
    assertEquals(2.0, result);
  }

  @Test
  public void testMultiplyOperation() {
    Operator multiplyOperator = OperatorProvider.getOperator("*");
    Object result = OperatorExecutor.execute(multiplyOperator, 5, 3);
    assertEquals(15.0, result);
  }

  @Test
  public void testDivideOperation() {
    Operator divideOperator = OperatorProvider.getOperator("/");
    Object result = OperatorExecutor.execute(divideOperator, 6, 3);
    assertEquals(2.0, result);
  }

  @Test
  public void testConcatOperation() {
    Operator sumOperator = OperatorProvider.getOperator("+");
    Object result = OperatorExecutor.execute(sumOperator, "Hello", " World");
    assertEquals("Hello World", result);
  }

  @Test
  public void testUnknownOperator() {
    IllegalArgumentException exception =
        assertThrows(
            IllegalArgumentException.class,
            () -> {
              OperatorProvider.getOperator("^");
            });
    assertEquals("Unknown operator: ^", exception.getMessage());
  }

  @Test
  public void testUnsupportedOperation() {
    Operator multiplyOperator = OperatorProvider.getOperator("*");
    RuntimeException exception =
        assertThrows(
            RuntimeException.class,
            () -> {
              OperatorExecutor.execute(multiplyOperator, "Hello", 3);
            });
    assertEquals("Unsupported operation: Hello * 3", exception.getMessage());
  }
}
