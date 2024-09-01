package edu

import com.github.ajalt.clikt.testing.test
import edu.commands.AnalyzeCommand
import edu.commands.ExecuteCommand
import edu.commands.FormatCommand
import edu.commands.ValidateCommand
import org.junit.jupiter.api.Test

class TestCommands {
    @Test
    fun testValidate() {
        val command = ValidateCommand()
        val result = command.test("--sourceFile test/java/resources/test.txt --version 1.0")
        result.stdout.contains("Error during validation: Source file not found")
    }

    @Test
    fun testExecute() {
        val command = ExecuteCommand()
        val result = command.test("--sourceFile test/java/resources/test.txt --version 1.1")
        result.stdout.contains("Error during execution: Source file not found")
    }

    @Test
    fun testAnalyze() {
        val command = AnalyzeCommand()
        val result =
            command.test(" --sourceFile test/java/resources/analysis_input.txt --version 1.0 --configFile src/test/java/resources/analysis_rules.txt")
        result.stdout.contains("Error during analysis: Source file not found")
    }

    @Test
    fun testFormat() {
        val command = FormatCommand()
        val result =
            command.test(" --sourceFile test/java/resources/analysis_input.txt --version 1.0 --configFile src/test/java/resources/analysis_rules.txt")
        result.stdout.contains("Error during formatting: Source file not found")
    }
}
