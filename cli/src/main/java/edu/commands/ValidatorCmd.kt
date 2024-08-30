package edu.commands

import com.github.ajalt.clikt.core.CliktCommand
import com.github.ajalt.clikt.parameters.options.option
import edu.FileReader.openFile
import edu.LexerFactory.createLexerV1
import edu.Parser
import edu.utils.CommandContext
import java.io.IOException

class ValidatorCmd() : CliktCommand(name = "validate", help = "Validate the source file") {
    val sourceFile by option("--sourceFile", envvar = "SOURCE_PATH")
    val commandContext = CommandContext()

    private fun execute() {
        try {
            println("Starting validation...")
            val fileReader = openFile(sourceFile)
            val lexer = createLexerV1(fileReader)
            lexer.tokenize()
            val tokens = lexer.tokens
            println("Tokenizing completed...")
            val parser = Parser()
            val programNode = parser.parse(tokens)
            println("Parsing completed...")
            commandContext.programNode = programNode
            println("Validation completed.")
        } catch (e: IOException) {
            println("Error executing validation command: ${e.message}")
        }
    }

    override fun run() {
        execute()
    }
}
