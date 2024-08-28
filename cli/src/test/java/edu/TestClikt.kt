package edu

import edu.commands.Cli
import org.junit.jupiter.api.Test

class TestClikt {
    @Test
    fun testClikt() {
        val cli: Cli = Cli()
        cli.parse(arrayOf())
        cli.run()
    }
}
