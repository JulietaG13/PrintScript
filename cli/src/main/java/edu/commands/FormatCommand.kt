package edu.commands

import com.github.ajalt.clikt.core.CliktCommand
import com.github.ajalt.clikt.parameters.options.option
import com.github.ajalt.clikt.parameters.options.required
import com.google.gson.JsonObject
import edu.FileReader.openFile
import edu.Runner
import edu.utils.JsonConfigLoader
import java.io.IOException

class FormatCommand : CliktCommand(name = "format", help = "Format the source file") {
    private val sourceFile by option("--sourceFile", envvar = "SOURCE_PATH").required()
    private val configFile by option("--configFile", envvar = "CONFIG_PATH").required()
    private val version by option("--version", envvar = "VERSION").required()

    override fun run() {
        val runner = Runner(version)
        try {
            runner.format(openFile(sourceFile), getRules(configFile))
        } catch (e: IOException) {
            println("Error during formatting: ${e.message}")
        }
    }

    @Throws(IOException::class)
    private fun getRules(configFile: String): JsonObject {
        val config = JsonConfigLoader.loadFromFile(configFile)
        return config
    }
}
