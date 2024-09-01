package edu.commands

import com.github.ajalt.clikt.core.CliktCommand
import com.github.ajalt.clikt.parameters.options.option
import com.github.ajalt.clikt.parameters.options.required
import edu.Runner
import java.io.IOException

class ValidateCommand : CliktCommand(help = "Validate the source file") {
    private val sourceFile by option("--sourceFile", envvar = "SOURCE_PATH").required()
    private val version by option("--version", envvar = "VERSION").required()

    override fun run() {
        try {
            val runner = Runner(version)
            runner.validate(sourceFile)
        } catch (e: IOException) {
            println("Error during validation: ${e.message}")
        }
    }
}
