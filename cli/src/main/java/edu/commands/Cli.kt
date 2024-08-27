package edu.commands

import com.github.ajalt.clikt.core.CliktCommand
import com.github.ajalt.clikt.core.subcommands

class Cli : CliktCommand(help = "Command Line Interface for File Operations") {
    override fun run() {
        echo("Welcome to the CLI!")
    }
}

fun main(args: Array<String>) {
    Cli()
        .subcommands(AnalyzerCmd(), FormattingCmd(), ValidatorCmd(), ExecutionCmd())
        .main(args)
}
