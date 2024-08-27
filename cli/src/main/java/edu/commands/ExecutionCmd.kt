package edu.commands

import com.github.ajalt.clikt.core.CliktCommand
import com.github.ajalt.clikt.parameters.options.option
import edu.Interpreter
import edu.Lexer
import edu.Parser
import edu.ast.ProgramNode
import edu.utils.CommandContext
import edu.utils.FileReader

class ExecutionCmd() : CliktCommand(name = "execute", help = "Execute the source file") {
    val sourceFile by option("--sourceFile", envvar = "SOURCE_PATH")
    val commandContext = CommandContext()

    private fun execute() {
        echo("Starting analysis...")
        val text: String = FileReader.readFileToString(sourceFile)
        echo("Reading file...")
        val programNode: ProgramNode = createProgramNode(text)
        echo("Parsing completed...")
        val interpreter = Interpreter()
        echo("Interpreting program...")
        interpreter.interpret(programNode)
        commandContext.programNode = programNode
    }

    private fun createProgramNode(sourceFile: String): ProgramNode {
        val lexer = Lexer(sourceFile)
        lexer.tokenize()
        val parser = Parser()
        return parser.parse(lexer.tokens)
    }

    override fun run() {
        execute()
    }
}
