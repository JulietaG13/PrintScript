package edu.cases;

import static edu.testrunner.TestRunner.isSuccess;
import static edu.testrunner.TestRunner.runTest;
import static org.junit.jupiter.api.Assertions.assertTrue;

import edu.testrunner.TestCase;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.Map;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class AssignationTests {
  private static final String path = "src/test/java/resources/assignation/";

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
  public void boolInit() {
    TestCase test = new TestCase(path + "bool/input-init.ps", path + "bool/expected-init.ps");
    Map<String, Object> result = runTest(test, "1.1");
    assertTrue(isSuccess(result));
  }

  /* --- NUMBERS --- */

  @Test
  public void numberIntV1() {
    TestCase test = new TestCase(path + "number/input-int.ps", path + "number/expected-int.ps");
    Map<String, Object> result = runTest(test, "1.0");
    assertTrue(isSuccess(result));
  }

  @Test
  public void numberIntV2() {
    TestCase test = new TestCase(path + "number/input-int.ps", path + "number/expected-int.ps");
    Map<String, Object> result = runTest(test, "1.1");
    assertTrue(isSuccess(result));
  }

  @Test
  public void numberFloatV1() {
    TestCase test = new TestCase(path + "number/input-float.ps", path + "number/expected-float.ps");
    Map<String, Object> result = runTest(test, "1.0");
    assertTrue(isSuccess(result));
  }

  @Test
  public void numberFloatV2() {
    TestCase test = new TestCase(path + "number/input-float.ps", path + "number/expected-float.ps");
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
}
