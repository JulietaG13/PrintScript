import com.github.ajalt.clikt.testing.test
import edu.FormatterResult
import edu.commands.FormattingCmd
import edu.utils.CommandContext
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.Test

class TestFormattingCommand {
    private val lineSeparator: String = System.lineSeparator()

    @Test
    fun testFormattingCommandWithFile() {
        // Configurar el archivo de prueba
        val filePath = "src/test/java/resources/input.txt"
        val rulesPath = "src/test/java/resources/rules.txt"
        val expectedOutput = "let my_cool_variable : string = \"ciclon\";" + lineSeparator

        val args = arrayOf("Formatting", filePath, "1.0", "--config-file=$rulesPath")

        // Instanciar y ejecutar el comando
        val cmd = FormattingCmd()
        cmd.test("", "", envvars = mapOf("SOURCE_PATH" to filePath, "CONFIG_PATH" to rulesPath))
        val commandContext: CommandContext = cmd.commandContext

        // Verificar que el FormatterResult fue creado correctamente
        val formatterResult: FormatterResult? = commandContext.formatterResult
        assertNotNull(formatterResult, "El FormatterResult no debería ser nulo después de ejecutar FormattingCmd.")
        assertEquals(expectedOutput, formatterResult?.result, "La salida del formatter debería coincidir con el resultado esperado.")
    }
}
