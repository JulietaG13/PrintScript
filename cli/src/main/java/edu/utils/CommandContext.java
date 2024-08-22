package edu.utils;

import edu.FormatterResult;
import edu.Report;
import edu.ast.ProgramNode;

public class CommandContext {
  private ProgramNode programNode;

  private Report linterReport;

  private FormatterResult formatterResult;

  public ProgramNode getProgramNode() {
    return programNode;
  }

  public void setProgramNode(ProgramNode programNode) {
    this.programNode = programNode;
  }

  public boolean hasProgramNode() {
    return programNode != null;
  }

  public Report getLinterReport() {
    return linterReport;
  }

  public void setLinterReport(Report linterReport) {
    this.linterReport = linterReport;
  }

  public FormatterResult getFormatterResult() {
    return formatterResult;
  }

  public void setFormatterResult(FormatterResult formatterResult) {
    this.formatterResult = formatterResult;
  }
}
