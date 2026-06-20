package com.nokhrin.interpreter;

import org.antlr.v4.runtime.tree.AbstractParseTreeVisitor;

public class CalcEvalVisitor extends AbstractParseTreeVisitor<ExprValue>
    implements CalcVisitor<ExprValue> {

  private final SymbolTable symbolTable;

  // region helpers
  private double toDouble(ExprValue val) {
    return switch (val) {
      case ExprValue.IntValue iv -> iv.value();
      case ExprValue.DoubleValue dv -> dv.value();
    };
  }

  private ExprValue wrap(double val) {
    if (val == Math.floor(val) && !Double.isInfinite(val)) {
      return new ExprValue.IntValue((int) val);
    }
    return new ExprValue.DoubleValue(val);
  }

  private ExprValue add(ExprValue left, ExprValue right) {
    double res = toDouble(left) + toDouble(right);
    return wrap(res);
  }

  private ExprValue sub(ExprValue left, ExprValue right) {
    double res = toDouble(left) - toDouble(right);
    return wrap(res);
  }

  private ExprValue mul(ExprValue left, ExprValue right) {
    double res = toDouble(left) * toDouble(right);
    return wrap(res);
  }

  private ExprValue div(ExprValue left, ExprValue right) {
    double res = toDouble(left) / toDouble(right);
    return wrap(res);
  }

  private ExprValue negate(ExprValue val) {
    return wrap(-toDouble(val));
  }

  // endregion helpers

  public CalcEvalVisitor(SymbolTable symbolTable) {
    this.symbolTable = symbolTable;
  }

  @Override
  public ExprValue visitProg(CalcParser.ProgContext ctx) {
    ExprValue result = null;
    for (var stat : ctx.stat()) {
      result = visit(stat);
    }
    return result;
  }

  @Override
  public ExprValue visitStat(CalcParser.StatContext ctx) {
    if (ctx.assign() != null) {
      return visit(ctx.assign());
    }
    return visit(ctx.expr());
  }

  @Override
  public ExprValue visitAssign(CalcParser.AssignContext ctx) {
    String name = ctx.ID().getText();
    ExprValue value = visit(ctx.expr());
    symbolTable.insert(name, value);
    return value;
  }

  @Override
  public ExprValue visitExpr(CalcParser.ExprContext ctx) {
    ExprValue result = visit(ctx.mul(0));
    for (int i = 1; i < ctx.mul().size(); i++) {
      if (ctx.ADD(i - 1) != null) {
        result = add(result, visit(ctx.mul(i)));
      } else {
        result = sub(result, visit(ctx.mul(i)));
      }
    }
    return result;
  }

  @Override
  public ExprValue visitMul(CalcParser.MulContext ctx) {
    ExprValue result = visit(ctx.unary(0));
    for (int i = 1; i < ctx.unary().size(); i++) {
      if (ctx.MUL(i - 1) != null) {
        result = mul(result, visit(ctx.unary(i)));
      } else {
        ExprValue div = visit(ctx.unary(i));
        if (toDouble(div) == 0) {
          throw new ArithmeticException("Деление на 0");
        }
        result = div(result, visit(ctx.unary(i)));
      }
    }
    return result;
  }

  @Override
  public ExprValue visitUnary(CalcParser.UnaryContext ctx) {
    if (ctx.ADD() != null || ctx.SUB() != null) {
      ExprValue value = visit(ctx.unary());
      if (ctx.SUB() != null) {
        return negate(value);
      }
      return value;
    }
    return visit(ctx.primary());
  }

  @Override
  public ExprValue visitPrimary(CalcParser.PrimaryContext ctx) {
    if (ctx.NUM() != null) {
      String primText = ctx.NUM().getText();

      // TODO: 2026-06-19 add sci floats , like 1.0e+2
      if (primText.contains(".")) {
        return new ExprValue.DoubleValue(Double.parseDouble(primText));
      }
      return new ExprValue.IntValue(Integer.parseInt(primText));
    }
    if (ctx.ID() != null) {
      String name = ctx.ID().getText();
      return symbolTable.lookup(name);
    }
    return visit(ctx.expr());
  }
}
