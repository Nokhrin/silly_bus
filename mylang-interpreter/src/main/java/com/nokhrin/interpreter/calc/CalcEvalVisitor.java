package com.nokhrin.interpreter.calc;

import com.nokhrin.interpreter.*;
import com.nokhrin.interpreter.common.compiletime.Symbol;
import com.nokhrin.interpreter.common.compiletime.VariableSymbol;
import com.nokhrin.interpreter.common.runtime.BaseScope;
import com.nokhrin.interpreter.common.runtime.GlobalScope;
import com.nokhrin.interpreter.common.values.DoubleValue;
import com.nokhrin.interpreter.common.values.EvalResult;
import com.nokhrin.interpreter.common.values.IntValue;
import org.antlr.v4.runtime.tree.AbstractParseTreeVisitor;

import static com.nokhrin.interpreter.common.operations.ArithmeticOperations.*;
import static com.nokhrin.interpreter.common.operations.TypeConversion.toLong;

public class CalcEvalVisitor extends AbstractParseTreeVisitor<EvalResult>
        implements CalcVisitor<EvalResult> {

    private final BaseScope globalScope;

    public CalcEvalVisitor(GlobalScope globalScope) {
        this.globalScope = globalScope;
    }

    @Override
    public EvalResult visitProg(CalcParser.ProgContext ctx) {
        EvalResult result = null;
        for (var stat : ctx.stat()) {
            result = visit(stat);
        }
        return result;
    }

    @Override
    public EvalResult visitStat(CalcParser.StatContext ctx) {
        if (ctx.assign() != null) {
            return visit(ctx.assign());
        }
        return visit(ctx.expr());
    }

    @Override
    public EvalResult visitAssign(CalcParser.AssignContext ctx) {
        String name = ctx.ID().getText();
        EvalResult variableValue = visit(ctx.expr());
        Symbol symbol = globalScope.resolve(name);
        VariableSymbol variableSymbol;
        if (symbol instanceof VariableSymbol v) {
            variableSymbol = v;
        } else {
            Symbol.Type inferredType = switch (variableValue) {
                case IntValue _ -> Symbol.Type.INTEGER;
                case DoubleValue _ -> Symbol.Type.FLOAT;
                default -> throw new IllegalStateException("Unexpected value: " + variableValue);
            };
            variableSymbol = new VariableSymbol(name, inferredType, globalScope);
            globalScope.define(variableSymbol);
        }
        globalScope.setValue(variableSymbol, variableValue);
        return variableValue;
    }

    @Override
    public EvalResult visitExpr(CalcParser.ExprContext ctx) {
        return visit(ctx.addSub());
    }

    @Override
    public EvalResult visitAddSub(CalcParser.AddSubContext ctx) {
        EvalResult result = visit(ctx.mulDiv(0));
        for (int i = 1; i < ctx.mulDiv().size(); i++) {
            String op = ctx.getChild(2 * i - 1).getText();
            EvalResult right = visit(ctx.mulDiv(i));
            result = switch (op) {
                case "+" -> add(result, right);
                case "-" -> sub(result, right);
                default -> result;
            };
        }
        return result;
    }

    @Override
    public EvalResult visitMulDiv(CalcParser.MulDivContext ctx) {
        EvalResult result = visit(ctx.pow(0));
        for (int i = 1; i < ctx.pow().size(); i++) {
            String op = ctx.getChild(2 * i - 1).getText();
            EvalResult right = visit(ctx.pow(i));
            result = switch (op) {
                case "*" -> mul(result, right);
                case "/" -> div(result, right);
                default -> result;
            };
        }
        return result;
    }

    @Override
    public EvalResult visitPow(CalcParser.PowContext ctx) {
        int n = ctx.unary().size();
        EvalResult result = visit(ctx.unary(n - 1));
        for (int i = n - 2; i >= 0; i--) {
            result = pow(visit(ctx.unary(i)), result);
        }
        return result;
    }

    @Override
    public EvalResult visitNeg(CalcParser.NegContext ctx) {
        return neg(visit(ctx.unary()));
    }

    @Override
    public EvalResult visitPos(CalcParser.PosContext ctx) {
        return visit(ctx.unary());
    }

    @Override
    public EvalResult visitFact(CalcParser.FactContext ctx) {
        EvalResult value = visit(ctx.unary());
        long n = toLong(value);
        if (n < 0) throw new IllegalArgumentException("Factorial of negative");
        long factValue = 1;
        for (long i = 1; i <= n; i++) {
            factValue *= i;
        }
        return new IntValue(factValue);
    }

    @Override
    public EvalResult visitNumber(CalcParser.NumberContext ctx) {
        String text = ctx.NUM().getText();
        return text.contains(".")
                ? new DoubleValue(Double.parseDouble(text))
                : new IntValue(Integer.parseInt(text));
    }

    @Override
    public EvalResult visitVarValue(CalcParser.VarValueContext ctx) {
        String name = ctx.ID().getText();
        VariableSymbol symbol = (VariableSymbol) globalScope.resolve(name);
        return globalScope.getValue(symbol);
    }

    @Override
    public EvalResult visitAbs(CalcParser.AbsContext ctx) {
        return abs(visit(ctx.expr()));
    }

    @Override
    public EvalResult visitParen(CalcParser.ParenContext ctx) {
        return visit(ctx.expr());
    }

    @Override
    public EvalResult visitPrime(CalcParser.PrimeContext ctx) {
        return visit(ctx.atom());
    }

}