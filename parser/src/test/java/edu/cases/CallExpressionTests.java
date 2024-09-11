package edu.cases;

import static edu.testrunner.TestRunner.isSuccess;
import static edu.testrunner.TestRunner.runTest;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import edu.exceptions.InvalidStatementException;
import edu.exceptions.UnexpectedTokenException;
import edu.testrunner.TestCase;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.Map;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class CallExpressionTests {
  private static final String path = "src/test/java/resources/callexp/";

  private static final ByteArrayOutputStream testOutput = new ByteArrayOutputStream();
  private static final PrintStream originalOut = System.out;

  @BeforeAll
  static void setup() {
    System.setOut(new PrintStream(testOutput));
  }

  @AfterAll
  static void cleanup() {
    System.setOut(originalOut);
    testOutput.reset();
  }

  @Test
  public void printV1() {
    TestCase test = new TestCase(path + "input-print.ps", path + "expected-print.ps");
    Map<String, Object> result = runTest(test, "1.0");
    assertTrue(isSuccess(result));
  }

  @Test
  public void printV2() {
    TestCase test = new TestCase(path + "input-print.ps", path + "expected-print.ps");
    Map<String, Object> result = runTest(test, "1.1");
    assertTrue(isSuccess(result));
  }

  @Test
  public void manyArgsV1() {
    TestCase test = new TestCase(path + "input-many-args.ps", path + "expected-many-args.ps");
    Map<String, Object> result = runTest(test, "1.0");
    assertTrue(isSuccess(result));
  }

  @Test
  public void manyArgsV2() {
    TestCase test = new TestCase(path + "input-many-args.ps", path + "expected-many-args.ps");
    Map<String, Object> result = runTest(test, "1.1");
    assertTrue(isSuccess(result));
  }

  /* --- EXCEPTIONS --- */

  @Test
  public void invalid1() {
    TestCase test = new TestCase(path + "exc-invalid1.ps", path + "exc-invalid1.ps");
    assertThrows(InvalidStatementException.class, () -> runTest(test, "1.1"));
  }

  @Test
  public void unexpectedToken1() {
    TestCase test = new TestCase(path + "exc-unexpected1.ps", path + "exc-unexpected1.ps");
    assertThrows(UnexpectedTokenException.class, () -> runTest(test, "1.1"));
  }

  @Test
  public void unexpectedToken2() {
    TestCase test = new TestCase(path + "exc-unexpected2.ps", path + "exc-unexpected2.ps");
    assertThrows(UnexpectedTokenException.class, () -> runTest(test, "1.1"));
  }
}
