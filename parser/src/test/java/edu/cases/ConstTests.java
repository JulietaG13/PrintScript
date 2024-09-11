package edu.cases;

import static edu.testrunner.TestRunner.isSuccess;
import static edu.testrunner.TestRunner.runTest;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import edu.check.ConstReassignmentException;
import edu.testrunner.TestCase;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.Map;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class ConstTests {
  private static final String path = "src/test/java/resources/consts/";

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

  /* --- BOOLS --- */

  @Test
  public void boolInitTrue() {
    TestCase test =
        new TestCase(path + "bool/input-init-true.ps", path + "bool/expected-init-true.ps");
    Map<String, Object> result = runTest(test, "1.1");
    assertTrue(isSuccess(result));
  }

  @Test
  public void boolInitFalse() {
    TestCase test =
        new TestCase(path + "bool/input-init-false.ps", path + "bool/expected-init-false.ps");
    Map<String, Object> result = runTest(test, "1.1");
    assertTrue(isSuccess(result));
  }

  /* --- NUMBERS --- */

  @Test
  public void numberInt() {
    TestCase test =
        new TestCase(path + "number/input-init-int.ps", path + "number/expected-init-int.ps");
    Map<String, Object> result = runTest(test, "1.1");
    assertTrue(isSuccess(result));
  }

  @Test
  public void numberFloat() {
    TestCase test =
        new TestCase(path + "number/input-init-float.ps", path + "number/expected-init-float.ps");
    Map<String, Object> result = runTest(test, "1.1");
    assertTrue(isSuccess(result));
  }

  /* --- STRINGS --- */

  @Test
  public void stringInit() {
    TestCase test = new TestCase(path + "string/input-init.ps", path + "string/expected-init.ps");
    Map<String, Object> result = runTest(test, "1.1");
    assertTrue(isSuccess(result));
  }

  /* --- EXCEPTIONS --- */

  @Test
  public void exceptionRedeclaration1() {
    TestCase test =
        new TestCase(
            path + "exceptions/exc-reassignment1.ps", path + "exceptions/exc-reassignment1.ps");
    assertThrows(ConstReassignmentException.class, () -> runTest(test, "1.1"));
  }

  @Test
  public void exceptionRedeclaration2() {
    TestCase test =
        new TestCase(
            path + "exceptions/exc-reassignment2.ps", path + "exceptions/exc-reassignment2.ps");
    assertThrows(ConstReassignmentException.class, () -> runTest(test, "1.1"));
  }
}
