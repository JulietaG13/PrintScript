
import com.github.ajalt.clikt.testing.test
import edu.commands.ValidatorCmd
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test

class ValidatorCliktCommandTest {

    @Test
    fun testValidatorCommandWithValidInput() {
        val testFilePath = "src/test/java/resources/test.txt"
        // Instantiate and execute the ValidatorCmd with the arguments
        val cmd = ValidatorCmd()
        cmd.test("", envvars = mapOf("SOURCE_PATH" to testFilePath))
        // Retrieve the command context to access the ProgramNode

        // Assert that the ProgramNode is not null, meaning the source file was successfully parsed
        val commandContext = cmd.commandContext
        val programNode = commandContext.programNode
        assertNotNull(programNode, "The ProgramNode should not be null after executing ValidatorCommand.")

        // Assert that the ProgramNode contains at least one statement, meaning the parsing was successful
        assertTrue(programNode.body.isNotEmpty(), "The ProgramNode should have at least one statement.")
    }
}
