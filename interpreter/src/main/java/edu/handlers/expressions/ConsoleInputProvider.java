package edu.handlers.expressions;

import java.util.Scanner;

public class ConsoleInputProvider implements InputProvider {
  private final Scanner scanner = new Scanner(System.in);

  @Override
  public String input(String name) {
    System.out.print(name);
    String input = scanner.nextLine();
    return input;
  }
}
