package edu.commands

import com.github.ajalt.clikt.core.CliktCommand
import com.github.ajalt.clikt.parameters.options.option
import edu.Interpreter
import edu.ast.ProgramNode
import edu.utils.CommandContext
import edu.utils.ProgramNodeUtils

class ExecutionCmd() : CliktCommand(name = "execute", help = "Execute the source file") {
    val sourceFile by option("--sourceFile", envvar = "SOURCE_PATH")
    val commandContext = CommandContext()

    private fun execute() {
        val programNode: ProgramNode = ProgramNodeUtils.getProgramNode(sourceFile)
        val interpreter = Interpreter()
        interpreter.interpret(programNode)
        commandContext.programNode = programNode
    }

    override fun run() {
        execute()
    }
}
