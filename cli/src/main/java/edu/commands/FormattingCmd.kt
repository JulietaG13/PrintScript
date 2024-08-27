package edu.commands

import com.github.ajalt.clikt.core.CliktCommand
import com.github.ajalt.clikt.parameters.options.option
import edu.Formatter
import edu.FormatterResult
import edu.Lexer
import edu.Parser
import edu.ast.ProgramNode
import edu.rules.FormatterRuleParser
import edu.rules.FormatterRuleProvider
import edu.utils.CommandContext
import edu.utils.FileReader
import edu.utils.JsonConfigLoader
import java.io.IOException

class FormattingCmd() : CliktCommand(name = "format", help = "Format the source file") {
    val sourceFile by option("--sourceFile", envvar = "SOURCE_PATH")
    val configFile by option("--configFile", envvar = "CONFIG_PATH")
    val commandContext = CommandContext()

    private fun execute() {
        try {
            val text: String = FileReader.readFileToString(sourceFile)
            val programNode: ProgramNode = createProgramNode(text)
            val formatter = getFormatter()
            val result: FormatterResult = formatter.format(programNode)
            commandContext.formatterResult = result
            echo(result.result)
        } catch (e: IOException) {
            println("Error executing formatting command: ${e.message}")
        }
    }
    private fun createProgramNode(sourceFile: String): ProgramNode {
        val lexer = Lexer(sourceFile)
        lexer.tokenize()
        val parser = Parser()
        return parser.parse(lexer.tokens)
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
