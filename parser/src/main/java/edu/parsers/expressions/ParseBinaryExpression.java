package edu.parsers.expressions;

import static edu.utils.ParserUtil.isCloseParen;
import static edu.utils.ParserUtil.isIdentifier;
import static edu.utils.ParserUtil.isKeyword;
import static edu.utils.ParserUtil.isLiteral;
import static edu.utils.ParserUtil.isOpenParen;
import static edu.utils.ParserUtil.isOperator;
import static edu.utils.ParserUtil.isSyntax;

import edu.Token;
import edu.ast.expressions.BinaryExpressionNode;
import edu.ast.interfaces.ExpressionNode;
import edu.common.OperatorProvider;
import edu.parsers.ExpressionParser;
import edu.parsers.ParseExpression;
import edu.utils.LexicalRange;
import java.util.ArrayList;
import java.util.List;

public class ParseBinaryExpression implements ExpressionParser {

  @Override
  public ExpressionNode parse(List<Token> tokens) {
    LexicalRange start = tokens.getFirst().getStart();
    LexicalRange end = tokens.getLast().getEnd();

    while (isSurroundedByParens(tokens)) {
      tokens = tokens.subList(1, tokens.size() - 1);
    }

    if (!isXexpression(tokens)) {
      throw new RuntimeException(); // TODO
    }

    List<Integer> operators = getOperators(tokens);
    int operatorIndex = getFirstMinOperator(tokens, operators);

    List<Token> left = tokens.subList(0, operatorIndex);
    List<Token> right = tokens.subList(operatorIndex + 1, tokens.size());

    return new BinaryExpressionNode(
        start,
        end,
        tokens.get(operatorIndex).getContent(),
        ParseExpression.parse(left),
        ParseExpression.parse(right));
  }

  @Override
  public boolean isXexpression(List<Token> tokens) {
    if (tokens.size() < 3) {
      return false;
    }
    if (isSurroundedByParens(tokens)) { // call again without surrounding parenthesis
      return isXexpression(tokens.subList(1, tokens.size() - 1));
    }

    List<Token> extraRemoved;

    try {
      extraRemoved = removeExtra(tokens); // remove args and parenthesis
    } catch (Exception e) {
      return false;
    }

    if (extraRemoved.size() < 3 || !containsOperator(extraRemoved)) {
      return false;
    }

    for (int i = 0; i < extraRemoved.size(); i++) { // check if it is alternated
      if (i % 2 == 0) {
        if (isLiteral(extraRemoved, i) || isIdentifier(extraRemoved, i)) {
          continue;
        }
        return false;
      }
      if (!isOperator(extraRemoved, i)) {
        return false;
      }
    }

    return true;
  }

  private static boolean isSurroundedByParens(List<Token> tokens) {
    if (isOpenParen(tokens, 0) && isCloseParen(tokens, tokens.size() - 1)) {
      int balance = 1;
      for (int i = 1; i < tokens.size() - 1; i++) {
        if (isOpenParen(tokens, i)) {
          balance++;
        }
        if (isCloseParen(tokens, i)) {
          balance--;
        }
        if (balance <= 0) {
          return false;
        }
      }
      return balance == 1;
    }
    return false;
  }

  private static List<Token> removeExtra(List<Token> tokens) throws Exception {
    List<Token> result = new ArrayList<>();
    int totalBalance = 0;

    for (int i = 0; i < tokens.size(); i++) {

      if (isKeyword(tokens, i) || totalBalance < 0) {
        throw new Exception("Not a Binary Expression!");
      }

      if (isOpenParen(tokens, i)) {
        totalBalance++;
        if (i == 0) { // skip first opening parenthesis
          continue;
        }
        if (isIdentifier(tokens, i - 1)) { // found a function
          int balance = 1;
          while (balance > 0) { // skip args
            i++;
            if (isOpenParen(tokens, i)) {
              balance++;
            }
            if (isCloseParen(tokens, i)) {
              balance--;
            }
          }
          continue; // skip closing parenthesis after args
        } else { // skip normal opening parenthesis
          continue;
        }
      }
      if (isCloseParen(tokens, i)) { // skip normal closing parenthesis
        totalBalance--;
        continue;
      }
      if (isSyntax(tokens, i)) {
        throw new Exception("Not a Binary Expression!");
      }
      result.add(tokens.get(i));
    }

    return result;
  }

  private static List<Integer> getOperators(List<Token> tokens) { // only first level
    List<Integer> operators = new ArrayList<>();

    int balance = 0;
    for (int i = 0; i < tokens.size(); i++) {
      if (isOperator(tokens, i) && balance == 0) {
        operators.add(i);
      }
      if (isOpenParen(tokens, i)) {
        balance++;
      }
      if (isCloseParen(tokens, i)) {
        balance--;
      }
    }
    return operators;
  }

  private static int getFirstMinOperator(List<Token> tokens, List<Integer> operators) {
    if (operators.isEmpty()) {
      throw new RuntimeException(); // TODO
    }
    int minIndex = operators.getFirst();
    Token firstOp = tokens.get(operators.getFirst());
    int min = OperatorProvider.getOperator(firstOp.getContent()).priority;

    for (Integer operator : operators) {
      Token op = tokens.get(operator);
      int priority = OperatorProvider.getOperator(op.getContent()).priority;
      if (priority < min) {
        min = priority;
        minIndex = operator;
      }
    }
    return minIndex;
  }

  private static boolean containsOperator(List<Token> tokens) {
    int balance = 0;
    for (int i = 0; i < tokens.size(); i++) {
      if (isOperator(tokens, i) && balance == 0) {
        return true;
      }
      if (isOpenParen(tokens, i)) {
        balance++;
      }
      if (isCloseParen(tokens, i)) {
        balance--;
      }
    }
    return false;
  }
}
