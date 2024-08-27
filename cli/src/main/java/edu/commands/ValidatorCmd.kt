package edu.commands

import com.github.ajalt.clikt.core.CliktCommand
import com.github.ajalt.clikt.parameters.options.option
import edu.Lexer
import edu.Parser
import edu.utils.CommandContext
import edu.utils.FileReader
import java.io.IOException

class ValidatorCmd() : CliktCommand(name = "validate", help = "Validate the source file") {
    val sourceFile by option("--sourceFile", envvar = "SOURCE_PATH")
    val commandContext = CommandContext()

    private fun execute() {
        try {
            val text = getText()
            val lexer = Lexer(text)
            lexer.tokenize()

            val tokens = lexer.tokens

            val parser = Parser()
            val programNode = parser.parse(tokens)
            commandContext.programNode = programNode
        } catch (e: IOException) {
            println("Error executing validation command: ${e.message}")
        }
    }

    private fun getText(): String {
        return try {
            FileReader.readFileToString(sourceFile)
        } catch (e: IOException) {
            println("Error reading source file: ${e.message}")
            throw e
        }
    }

    override fun run() {
        execute()
    }
}
