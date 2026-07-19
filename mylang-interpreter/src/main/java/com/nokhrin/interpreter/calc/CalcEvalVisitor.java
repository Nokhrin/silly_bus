package com.nokhrin.interpreter.calc;

import com.nokhrin.interpreter.*;
import com.nokhrin.interpreter.common.*;
import com.nokhrin.interpreter.symbol_table.GlobalScope;
import com.nokhrin.interpreter.symbol_table.Symbol;
import com.nokhrin.interpreter.symbol_table.VariableSymbol;
import org.antlr.v4.runtime.tree.AbstractParseTreeVisitor;

import static com.nokhrin.interpreter.common.ArithmeticOperations.*;
import static com.nokhrin.interpreter.common.TypeConversion.toLong;

public class CalcEvalVisitor extends AbstractParseTreeVisitor<EvalResult>
        implements CalcVisitor<EvalResult> {

    private final GlobalScope globalScope;

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
        EvalResult value = visit(ctx.expr());
        Symbol symbol = globalScope.resolve(name);
        VariableSymbol var;
        if (symbol instanceof VariableSymbol v) {
            var = v;
        } else {
            Symbol.Type inferredType = switch (value) {
                case IntValue _ -> Symbol.Type.INT;
                case DoubleValue _ -> Symbol.Type.FLOAT;
                default -> throw new IllegalStateException("Unexpected value: " + value);
            };
            var = new VariableSymbol(name, inferredType, globalScope);
            globalScope.define(var);
        }
        var.setValue(value);
        return value;
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
        String varName = ctx.ID().getText();
        return resolveVar(varName).getValue();
    }

    public VariableSymbol resolveVar(String varName) {
        Symbol symbol = globalScope.resolve(varName);
        if (symbol instanceof VariableSymbol var) return var;
        throw new IllegalArgumentException("Undefined variable: " + varName);
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