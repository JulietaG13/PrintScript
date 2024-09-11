package edu.cases;

import static edu.testrunner.TestRunner.isSuccess;
import static edu.testrunner.TestRunner.runTest;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import edu.exceptions.InvalidExpressionException;
import edu.testrunner.TestCase;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.Map;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class BinaryExpressionTests {
  private static final String path = "src/test/java/resources/binaryexp/";

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

  /* --- NUMBERS --- */

  @Test
  public void numberSimpleV1() {
    TestCase test =
        new TestCase(path + "number/input-simple.ps", path + "number/expected-simple.ps");
    Map<String, Object> result = runTest(test, "1.0");
    assertTrue(isSuccess(result));
  }

  @Test
  public void numberSimpleV2() {
    TestCase test =
        new TestCase(path + "number/input-simple.ps", path + "number/expected-simple.ps");
    Map<String, Object> result = runTest(test, "1.1");
    assertTrue(isSuccess(result));
  }

  /* --- STRINGS --- */

  @Test
  public void stringSimpleV1() {
    TestCase test =
        new TestCase(path + "string/input-simple.ps", path + "string/expected-simple.ps");
    Map<String, Object> result = runTest(test, "1.0");
    assertTrue(isSuccess(result));
  }

  @Test
  public void stringSimpleV2() {
    TestCase test =
        new TestCase(path + "string/input-simple.ps", path + "string/expected-simple.ps");
    Map<String, Object> result = runTest(test, "1.1");
    assertTrue(isSuccess(result));
  }

  /* --- MIX --- */

  @Test
  public void mix1Version1() {
    TestCase test = new TestCase(path + "mix/input-mix1.ps", path + "mix/expected-mix1.ps");
    Map<String, Object> result = runTest(test, "1.0");
    assertTrue(isSuccess(result));
  }

  @Test
  public void mix1Version2() {
    TestCase test = new TestCase(path + "mix/input-mix1.ps", path + "mix/expected-mix1.ps");
    Map<String, Object> result = runTest(test, "1.1");
    assertTrue(isSuccess(result));
  }

  @Test
  public void mix2Version1() {
    TestCase test = new TestCase(path + "mix/input-mix2.ps", path + "mix/expected-mix2.ps");
    Map<String, Object> result = runTest(test, "1.0");
    assertTrue(isSuccess(result));
  }

  @Test
  public void mix2Version2() {
    TestCase test = new TestCase(path + "mix/input-mix2.ps", path + "mix/expected-mix2.ps");
    Map<String, Object> result = runTest(test, "1.1");
    assertTrue(isSuccess(result));
  }

  @Test
  public void mix3Version1() {
    TestCase test = new TestCase(path + "mix/input-mix3.ps", path + "mix/expected-mix3.ps");
    Map<String, Object> result = runTest(test, "1.0");
    assertTrue(isSuccess(result));
  }

  @Test
  public void mix3Version2() {
    TestCase test = new TestCase(path + "mix/input-mix3.ps", path + "mix/expected-mix3.ps");
    Map<String, Object> result = runTest(test, "1.1");
    assertTrue(isSuccess(result));
  }

  /* --- EXCEPTIONS --- */

  @Test
  public void invalid1() {
    TestCase test =
        new TestCase(path + "exceptions/exc-invalid1.ps", path + "exceptions/exc-invalid1.ps");
    assertThrows(InvalidExpressionException.class, () -> runTest(test, "1.1"));
  }

  @Test
  public void invalid2() {
    TestCase test =
        new TestCase(path + "exceptions/exc-invalid2.ps", path + "exceptions/exc-invalid2.ps");
    assertThrows(InvalidExpressionException.class, () -> runTest(test, "1.1"));
  }

  @Test
  public void invalid3() {
    TestCase test =
        new TestCase(path + "exceptions/exc-invalid3.ps", path + "exceptions/exc-invalid3.ps");
    assertThrows(InvalidExpressionException.class, () -> runTest(test, "1.1"));
  }

  @Test
  public void invalid4() {
    TestCase test =
        new TestCase(path + "exceptions/exc-invalid4.ps", path + "exceptions/exc-invalid4.ps");
    assertThrows(InvalidExpressionException.class, () -> runTest(test, "1.1"));
  }
}
