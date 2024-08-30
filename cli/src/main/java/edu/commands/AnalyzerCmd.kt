package edu.commands

import com.github.ajalt.clikt.core.CliktCommand
import com.github.ajalt.clikt.parameters.options.option
import edu.LexerFactory.createLexerV1
import edu.Linter
import edu.LinterFactory.createLinterV1
import edu.Parser
import edu.ast.ProgramNode
import edu.utils.CommandContext
import edu.utils.JsonConfigLoader
import edu.utils.ProgramNodeUtils
import java.io.IOException

class AnalyzerCmd : CliktCommand(name = "analyze", help = "Analyze a source code file") {
    val sourceFile by option("--sourceFile", envvar = "SOURCE_PATH")
    val configFile by option("--configFile", envvar = "CONFIG_PATH")
    val commandContext = CommandContext()
    private fun execute() {
        try {
            println("Starting analysis...")
            println("Reading file...")
            val programNode: ProgramNode = ProgramNodeUtils.getProgramNode(sourceFile)
            println("Parsing completed...")
            val linter = getLinter()
            println("Analyzing program...")
            val report = linter.analyze(programNode)
            println("Analysis completed.")
            commandContext.linterReport = report
            println("Report: ${report.printReport()}")
        } catch (e: IOException) {
            println("Error: " + e.message)
        }
    }
    private fun createProgramNode(sourceFile: String): ProgramNode {
        val lexer = createLexerV1(sourceFile)
        lexer.tokenize()
        val parser = Parser()
        return parser.parse(lexer.tokens)
    }

    @Throws(IOException::class)
    private fun getLinter(): Linter {
        val config = JsonConfigLoader.loadFromFile(configFile)
        return createLinterV1(config)
    }

    override fun run() {
        execute()
    }
}
