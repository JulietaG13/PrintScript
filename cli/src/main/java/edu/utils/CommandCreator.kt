package edu.commands

import com.github.ajalt.clikt.core.CliktCommand

object CommandCreator {
    fun createCommands(): List<CliktCommand> {
        return listOf(
            ValidateCommand(),
            ExecuteCommand(),
            FormatCommand(),
            AnalyzeCommand(),
        )
    }
}
