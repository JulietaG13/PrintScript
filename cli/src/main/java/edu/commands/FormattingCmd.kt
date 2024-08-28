package edu.commands

import com.github.ajalt.clikt.core.CliktCommand
import com.github.ajalt.clikt.parameters.options.option
import edu.Formatter
import edu.FormatterResult
import edu.ast.ProgramNode
import edu.rules.FormatterRuleParser
import edu.rules.FormatterRuleProvider
import edu.utils.CommandContext
import edu.utils.JsonConfigLoader
import edu.utils.ProgramNodeUtils
import java.io.IOException

class FormattingCmd() : CliktCommand(name = "format", help = "Format the source file") {
    val sourceFile by option("--sourceFile", envvar = "SOURCE_PATH")
    val configFile by option("--configFile", envvar = "CONFIG_PATH")
    val commandContext = CommandContext()

    private fun execute() {
        try {
            println("Starting formatting...")
            println("Reading file...")
            val programNode: ProgramNode = ProgramNodeUtils.getProgramNode(sourceFile)
            println("Parsing completed...")
            val formatter = getFormatter()
            println("Formatting program...")
            val result: FormatterResult = formatter.format(programNode)
            println("Formatting completed.")
            commandContext.formatterResult = result
            println("Formatted code:")
            echo(result.result)
        } catch (e: IOException) {
            println("Error executing formatting command: ${e.message}")
        }
    }

    private fun getFormatter(): Formatter {
        return Formatter(getRules())
    }

    private fun getRules(): FormatterRuleProvider {
        try {
            val config = JsonConfigLoader.loadFromFile(configFile)
            val ruleProvider = FormatterRuleParser.parseRules(config)
            return ruleProvider
        } catch (e: IOException) {
            println("Error loading config file: ${e.message}")
            throw e
        }
    }

    override fun run() {
        execute()
    }
}
