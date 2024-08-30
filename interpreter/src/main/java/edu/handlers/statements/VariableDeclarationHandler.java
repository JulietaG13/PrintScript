package edu.handlers.statements;

import edu.ast.interfaces.StatementNode;
import edu.ast.statements.Kind;
import edu.ast.statements.VariableDeclarationNode;
import edu.handlers.StatementHandler;
import edu.reader.InterpreterReader;
import edu.reader.ReaderResult;

public class VariableDeclarationHandler implements StatementHandler {
  @Override
  public InterpreterReader handle(StatementNode node, InterpreterReader interpreterReader) {
    VariableDeclarationNode varNode = (VariableDeclarationNode) node;
    // get the identifier
    ReaderResult resultId = interpreterReader.getIdentifier();
    InterpreterReader newInterpreterReader = resultId.getReader();
    interpreterReader = newInterpreterReader;
    String varName = resultId.getValue().toString();
    // get the value
    ReaderResult resultValue = interpreterReader.getLiteral();
    InterpreterReader newInterpreterReader1 = resultValue.getReader();
    interpreterReader = newInterpreterReader1;
    Object value = resultValue.getValue();

    InterpreterReader newInterpreterReader2 = interpreterReader.write(varName, value);
    interpreterReader = newInterpreterReader2;
    if (varNode.kind() == Kind.CONST) {
      InterpreterReader newInterpreterReader3 = interpreterReader.writeConst(varName);
      interpreterReader = newInterpreterReader3;
    }
    return interpreterReader;
  }
}
