package edu.cases;

import static edu.testrunner.TestRunner.isSuccess;
import static edu.testrunner.TestRunner.runTest;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import edu.check.InvalidIfConditionException;
import edu.testrunner.TestCase;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.Map;
import org.junit.jupiter.api.Test;

public class IfTests {
  private static final String path = "src/test/java/resources/ifstatement/";

  private static final ByteArrayOutputStream testOutput = new ByteArrayOutputStream();
  private static final PrintStream originalOut = System.out;

  // @BeforeAll
  static void setup() {
    System.setOut(new PrintStream(testOutput));
  }

  // @AfterAll
  static void cleanup() {
    System.setOut(originalOut);
    testOutput.reset();
  }

  @Test
  public void ifSimple() {
    TestCase test = new TestCase(path + "input-if-simple.ps", path + "expected-if-simple.ps");
    Map<String, Object> result = runTest(test, "1.1");
    assertTrue(isSuccess(result));
  }

  @Test
  public void ifElseSimple() {
    TestCase test =
        new TestCase(path + "input-if-else-simple.ps", path + "expected-if-else-simple.ps");
    Map<String, Object> result = runTest(test, "1.1");
    assertTrue(isSuccess(result));
  }

  @Test
  public void ifVar() {
    TestCase test = new TestCase(path + "input-if-var.ps", path + "expected-if-var.ps");
    Map<String, Object> result = runTest(test, "1.1");
    assertTrue(isSuccess(result));
  }

  @Test
  public void ifElseVar() {
    TestCase test = new TestCase(path + "input-if-else-var.ps", path + "expected-if-else-var.ps");
    Map<String, Object> result = runTest(test, "1.1");
    assertTrue(isSuccess(result));
  }

  /* --- EXCEPTIONS --- */

  @Test
  public void invalidCondition1() {
    TestCase test =
        new TestCase(path + "exc-invalid-condition1.ps", path + "exc-invalid-condition1.ps");
    assertThrows(InvalidIfConditionException.class, () -> runTest(test, "1.1"));
  }

  @Test
  public void invalidCondition2() {
    TestCase test =
        new TestCase(path + "exc-invalid-condition2.ps", path + "exc-invalid-condition2.ps");
    assertThrows(InvalidIfConditionException.class, () -> runTest(test, "1.1"));
  }
}
