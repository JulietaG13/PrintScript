package edu.cases;

import static edu.testrunner.TestRunner.runTest;
import static org.junit.jupiter.api.Assertions.assertThrows;

import edu.check.VariableNotDeclaredException;
import edu.exceptions.InvalidStatementException;
import edu.testrunner.TestCase;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class GeneralExceptionsTests {
  private static final String path = "src/test/java/resources/exceptions/";

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

  /* --- INVALID STATEMENT --- */

  @Test
  public void invalidStatement1() {
    TestCase test =
        new TestCase(path + "exc-invalid-statement1.ps", path + "exc-invalid-statement1.ps");
    assertThrows(InvalidStatementException.class, () -> runTest(test, "1.1"));
  }

  @Test
  public void invalidStatement2() {
    TestCase test =
        new TestCase(path + "exc-invalid-statement2.ps", path + "exc-invalid-statement2.ps");
    assertThrows(InvalidStatementException.class, () -> runTest(test, "1.1"));
  }

  @Test
  public void invalidStatement3() {
    TestCase test =
        new TestCase(path + "exc-invalid-statement3.ps", path + "exc-invalid-statement3.ps");
    assertThrows(InvalidStatementException.class, () -> runTest(test, "1.1"));
  }

  @Test
  public void invalidStatement4() {
    TestCase test =
        new TestCase(path + "exc-invalid-statement4.ps", path + "exc-invalid-statement4.ps");
    assertThrows(InvalidStatementException.class, () -> runTest(test, "1.1"));
  }

  @Test
  public void invalidStatement5() {
    TestCase test =
        new TestCase(path + "exc-invalid-statement5.ps", path + "exc-invalid-statement5.ps");
    assertThrows(InvalidStatementException.class, () -> runTest(test, "1.1"));
  }

  /* --- VARIABLE NOT DECLARED --- */

  @Test
  public void undeclaredAssign1() {
    TestCase test =
        new TestCase(path + "exc-undeclared-assign1.ps", path + "exc-undeclared-assign1.ps");
    assertThrows(VariableNotDeclaredException.class, () -> runTest(test, "1.1"));
  }

  @Test
  public void undeclaredAssign2() {
    TestCase test =
        new TestCase(path + "exc-undeclared-assign2.ps", path + "exc-undeclared-assign2.ps");
    assertThrows(VariableNotDeclaredException.class, () -> runTest(test, "1.1"));
  }

  @Test
  public void undeclaredAssign3() {
    TestCase test =
        new TestCase(path + "exc-undeclared-assign3.ps", path + "exc-undeclared-assign3.ps");
    assertThrows(VariableNotDeclaredException.class, () -> runTest(test, "1.1"));
  }

  @Test
  public void undeclaredIf1() {
    TestCase test = new TestCase(path + "exc-undeclared-if1.ps", path + "exc-undeclared-if1.ps");
    assertThrows(VariableNotDeclaredException.class, () -> runTest(test, "1.1"));
  }

  @Test
  public void undeclaredIf2() {
    TestCase test = new TestCase(path + "exc-undeclared-if2.ps", path + "exc-undeclared-if2.ps");
    assertThrows(VariableNotDeclaredException.class, () -> runTest(test, "1.1"));
  }

  @Test
  public void undeclaredIf3() {
    TestCase test = new TestCase(path + "exc-undeclared-if3.ps", path + "exc-undeclared-if3.ps");
    assertThrows(VariableNotDeclaredException.class, () -> runTest(test, "1.1"));
  }

  @Test
  public void undeclaredIf4() {
    TestCase test = new TestCase(path + "exc-undeclared-if4.ps", path + "exc-undeclared-if4.ps");
    assertThrows(VariableNotDeclaredException.class, () -> runTest(test, "1.1"));
  }
}
