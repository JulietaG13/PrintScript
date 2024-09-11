package edu.cases;

import static edu.testrunner.TestRunner.isSuccess;
import static edu.testrunner.TestRunner.runTest;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import edu.check.VariableAlreadyDeclaredException;
import edu.check.VariableTypeMismatchException;
import edu.exceptions.InvalidTypeException;
import edu.testrunner.TestCase;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.Map;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class LetTests {
  private static final String path = "src/test/java/resources/let/";

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
  public void boolSimple() {
    TestCase test = new TestCase(path + "bool/input-simple.ps", path + "bool/expected-simple.ps");
    Map<String, Object> result = runTest(test, "1.1");
    assertTrue(isSuccess(result));
  }

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

  @Test
  public void numberIntV1() {
    TestCase test =
        new TestCase(path + "number/input-init-int.ps", path + "number/expected-init-int.ps");
    Map<String, Object> result = runTest(test, "1.0");
    assertTrue(isSuccess(result));
  }

  @Test
  public void numberIntV2() {
    TestCase test =
        new TestCase(path + "number/input-init-int.ps", path + "number/expected-init-int.ps");
    Map<String, Object> result = runTest(test, "1.1");
    assertTrue(isSuccess(result));
  }

  @Test
  public void numberFloatV1() {
    TestCase test =
        new TestCase(path + "number/input-init-float.ps", path + "number/expected-init-float.ps");
    Map<String, Object> result = runTest(test, "1.0");
    assertTrue(isSuccess(result));
  }

  @Test
  public void numberFloatV2() {
    TestCase test =
        new TestCase(path + "number/input-init-float.ps", path + "number/expected-init-float.ps");
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

  @Test
  public void stringInitV1() {
    TestCase test = new TestCase(path + "string/input-init.ps", path + "string/expected-init.ps");
    Map<String, Object> result = runTest(test, "1.0");
    assertTrue(isSuccess(result));
  }

  @Test
  public void stringInitV2() {
    TestCase test = new TestCase(path + "string/input-init.ps", path + "string/expected-init.ps");
    Map<String, Object> result = runTest(test, "1.1");
    assertTrue(isSuccess(result));
  }

  /* --- EXCEPTIONS --- */

  @Test
  public void exceptionMismatch1() {
    TestCase test =
        new TestCase(path + "exceptions/exc-mismatch1.ps", path + "exceptions/exc-mismatch1.ps");
    assertThrows(VariableTypeMismatchException.class, () -> runTest(test, "1.1"));
  }

  @Test
  public void exceptionMismatch2() {
    TestCase test =
        new TestCase(path + "exceptions/exc-mismatch2.ps", path + "exceptions/exc-mismatch2.ps");
    assertThrows(VariableTypeMismatchException.class, () -> runTest(test, "1.1"));
  }

  @Test
  public void exceptionMismatch3() {
    TestCase test =
        new TestCase(path + "exceptions/exc-mismatch3.ps", path + "exceptions/exc-mismatch3.ps");
    assertThrows(VariableTypeMismatchException.class, () -> runTest(test, "1.1"));
  }

  @Test
  public void exceptionMismatch4() {
    TestCase test =
        new TestCase(path + "exceptions/exc-mismatch4.ps", path + "exceptions/exc-mismatch4.ps");
    assertThrows(VariableTypeMismatchException.class, () -> runTest(test, "1.1"));
  }

  @Test
  public void exceptionRedeclaration1() {
    TestCase test =
        new TestCase(
            path + "exceptions/exc-redeclaration1.ps", path + "exceptions/exc-redeclaration1.ps");
    assertThrows(VariableAlreadyDeclaredException.class, () -> runTest(test, "1.1"));
  }

  @Test
  public void exceptionRedeclaration2() {
    TestCase test =
        new TestCase(
            path + "exceptions/exc-redeclaration2.ps", path + "exceptions/exc-redeclaration2.ps");
    assertThrows(VariableAlreadyDeclaredException.class, () -> runTest(test, "1.1"));
  }

  @Test
  public void exceptionInvalidType1() {
    TestCase test =
        new TestCase(
            path + "exceptions/exc-invalid-type1.ps", path + "exceptions/exc-invalid-type1.ps");
    assertThrows(InvalidTypeException.class, () -> runTest(test, "1.1"));
  }

  @Test
  public void exceptionInvalidType2() {
    TestCase test =
        new TestCase(
            path + "exceptions/exc-invalid-type2.ps", path + "exceptions/exc-invalid-type2.ps");
    assertThrows(InvalidTypeException.class, () -> runTest(test, "1.1"));
  }
}
