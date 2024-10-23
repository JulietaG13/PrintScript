package edu;

public class ConsolePrinter implements PrintEmitter {
  @Override
  public void print(String message) {
    System.out.println(message);
  }
}
