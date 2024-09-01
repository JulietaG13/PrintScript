package edu.commands

import com.github.ajalt.clikt.core.CliktCommand
import com.github.ajalt.clikt.parameters.options.option
import com.github.ajalt.clikt.parameters.options.required
import edu.Runner
import java.io.IOException

class AnalyzeCommand : CliktCommand(name = "analyze", help = "Analyze the source file") {
    private val sourceFile by option("--sourceFile", envvar = "SOURCE_PATH").required()
    private val configFile by option("--configFile", envvar = "CONFIG_PATH").required()
    private val version by option("--version", envvar = "VERSION").required()

    override fun run() {
        val runner = Runner(version)
        try {
            runner.analyze(sourceFile, configFile)
        } catch (e: IOException) {
            println("Error during analysis: ${e.message}")
        }
    }
}