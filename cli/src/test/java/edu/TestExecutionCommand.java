package edu;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import edu.ast.ProgramNode;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import org.junit.jupiter.api.Test;

public class TestExecutionCommand {

  private static String lineSeparator = System.lineSeparator();

  @Test
  public void testExecutionCommandWithSimpleInput() {
    // Configurar la captura de la salida estándar
    ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
    PrintStream originalOut = System.out;
    System.setOut(new PrintStream(outputStream));

    try {
      // Entrada simple: declaración de variable y uso en println
      String input = "let a: Number = 5; println(a);";
      String[] args = {"Execution", input, "1.0"};
      Cli cli = new Cli(args);

      // Ejecutar el CLI
      cli.run();

      // Verificar que el ProgramNode fue creado correctamente
      ProgramNode programNode = cli.getCommandContext().getProgramNode();
      assertNotNull(
          programNode, "El ProgramNode no debería ser nulo después de ejecutar ExecutionCommand.");
      assertTrue(
          programNode.getBody().size() > 0, "El ProgramNode debería tener al menos un statement.");

      // Verificar que el intérprete ejecutó el código correctamente
      String output = outputStream.toString();
      assertEquals(
          "5.0" + lineSeparator,
          output,
          "La salida del intérprete debería ser '5' seguido de un salto de línea.");

    } finally {
      // Restaurar la salida estándar original
      System.setOut(originalOut);
    }
  }

  @Test
  public void testExecutionCommandWithAddition() {
    ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
    PrintStream originalOut = System.out;
    System.setOut(new PrintStream(outputStream));

    try {
      String input = "let a: Number = 5 + 3; println(a);";
      String[] args = {"Execution", input, "1.0"};
      Cli cli = new Cli(args);
      cli.run();

      String output = outputStream.toString();
      assertEquals(
          "8.0" + lineSeparator,
          output,
          "La salida del intérprete debería ser '8.0' seguido de un salto de línea.");

    } finally {
      System.setOut(originalOut);
    }
  }

  @Test
  public void testExecutionCommandWithSubtraction() {
    ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
    PrintStream originalOut = System.out;
    System.setOut(new PrintStream(outputStream));

    try {
      String input = "let a: Number = 10 - 2; println(a);";
      String[] args = {"Execution", input, "1.0"};
      Cli cli = new Cli(args);
      cli.run();

      String output = outputStream.toString();
      assertEquals(
          "8.0" + lineSeparator,
          output,
          "La salida del intérprete debería ser '8.0' seguido de un salto de línea.");

    } finally {
      System.setOut(originalOut);
    }
  }

  @Test
  public void testExecutionCommandWithMultiplication() {
    ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
    PrintStream originalOut = System.out;
    System.setOut(new PrintStream(outputStream));

    try {
      String input = "let a: Number = 4 * 2; println(a);";
      String[] args = {"Execution", input, "1.0"};
      Cli cli = new Cli(args);
      cli.run();

      String output = outputStream.toString();
      assertEquals(
          "8.0" + lineSeparator,
          output,
          "La salida del intérprete debería ser '8.0' seguido de un salto de línea.");

    } finally {
      System.setOut(originalOut);
    }
  }

  @Test
  public void testExecutionCommandWithDivision() {
    ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
    PrintStream originalOut = System.out;
    System.setOut(new PrintStream(outputStream));

    try {
      String input = "let a: Number = 16 / 2; println(a);";
      String[] args = {"Execution", input, "1.0"};
      Cli cli = new Cli(args);
      cli.run();

      String output = outputStream.toString();
      assertEquals(
          "8.0" + lineSeparator,
          output,
          "La salida del intérprete debería ser '8.0' seguido de un salto de línea.");

    } finally {
      System.setOut(originalOut);
    }
  }

  @Test
  public void testExecutionCommandWithComplexExpression() {
    ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
    PrintStream originalOut = System.out;
    System.setOut(new PrintStream(outputStream));

    try {
      String input = "let a: Number = (5 + 3) * 2; println(a);";
      String[] args = {"Execution", input, "1.0"};
      Cli cli = new Cli(args);
      cli.run();

      String output = outputStream.toString();
      assertEquals(
          "16.0" + lineSeparator,
          output,
          "La salida del intérprete debería ser '16.0' seguido de un salto de línea.");

    } finally {
      System.setOut(originalOut);
    }
  }
}
