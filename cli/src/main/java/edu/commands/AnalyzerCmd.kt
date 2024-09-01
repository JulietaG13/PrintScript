package edu.commands

import com.github.ajalt.clikt.core.CliktCommand
import com.github.ajalt.clikt.parameters.options.option
import edu.FileReader.openFile
import edu.LexerFactory.createLexerV1
import edu.Linter
import edu.LinterFactory.createLinterV1
import edu.Parser
import edu.ParserFactory.createParserV1
import edu.utils.CommandContext
import edu.utils.JsonConfigLoader
import java.io.IOException

class AnalyzerCmd : CliktCommand(name = "analyze", help = "Analyze a source code file") {
    val sourceFile by option("--sourceFile", envvar = "SOURCE_PATH")
    val configFile by option("--configFile", envvar = "CONFIG_PATH")
    val commandContext = CommandContext()
    private fun execute() {
        try {
            println("Starting analysis...")
            println("Reading file...")
            val lexer = createLexerV1(openFile(sourceFile))
            val parser = createParserV1(lexer)
            println("Parsing completed...")
            val linter = getLinter(parser)
            println("Analyzing program...")
            val report = linter.analyze()
            println("Analysis completed.")
            commandContext.linterReport = report
            println("Report: ${report.printReport()}")
        } catch (e: IOException) {
            println("Error: " + e.message)
        }
    }

    @Throws(IOException::class)
    private fun getLinter(parser: Parser): Linter {
        val config = JsonConfigLoader.loadFromFile(configFile)
        return createLinterV1(config, parser)
    }

    override fun run() {
        execute()
    }
}
