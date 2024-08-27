package edu

import com.github.ajalt.clikt.testing.test
import edu.commands.AnalyzerCmd
import edu.utils.CommandContext
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

class TestAnalyzerCommand {
    @Test
    fun tesAnalyzeCommandWithFile() {
        val rulesPath = "src/test/java/resources/analysis_rules.txt"
        val filePath = "src/test/java/resources/analysis_input.txt"

        val expectedOutput = """Error in println function: The println function only accepts identifiers or literals as arguments.
Argument 1 is invalid:
 - Type: BinaryExpressionNode
 - Position: LexicalRange(offset=8, line=0, column=8)
 - Content: "Hello" + "World""""

        val args = arrayOf("Analyzing", filePath, "1.0", "--config-file=$rulesPath")

        val cmd = AnalyzerCmd()
        cmd.test("", envvars = mapOf("SOURCE_PATH" to filePath, "CONFIG_PATH" to rulesPath))
        val commandContext: CommandContext = cmd.commandContext

        // Verificar que el FormatterResult fue creado correctamente
        val report: Report? = commandContext.linterReport
        Assertions.assertEquals(1, report!!.messages.size)
        Assertions.assertEquals(expectedOutput, report.messages[0])
    }
}
