package edu

import com.github.ajalt.clikt.testing.test
import edu.ast.ProgramNode
import edu.commands.ExecutionCmd
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import java.io.ByteArrayOutputStream
import java.io.PrintStream

class TestExecutionCommand {
    @Test
    fun testExecutionCommandWithValidInput() {
        val outputStream = ByteArrayOutputStream()
        val originalOut = System.out
        System.setOut(PrintStream(outputStream))

        try {
            val testFilePath = "src/test/java/resources/test.txt"
            val cmd = ExecutionCmd()
            cmd.test("", envvars = mapOf("SOURCE_PATH" to testFilePath))

            val programNode: ProgramNode? = cmd.commandContext.programNode
            Assertions.assertNotNull(
                programNode,

                "El ProgramNode no debería ser nulo después de ejecutar ExecutionCommand.",
            )
            Assertions.assertTrue(
                programNode?.body?.isNotEmpty() == true,
                "El ProgramNode debería tener al menos un statement.",
            )
            // Verificar que el intérprete ejecutó el código correctamente
            val output = outputStream.toString()
            Assertions.assertEquals(
                "5.0" + System.lineSeparator(),
                output,
                "La salida del intérprete debería ser '5' seguido de un salto de línea.",
            )
        } finally {
            // Restaurar la salida estándar original
            System.setOut(originalOut)
        }
    }
}
