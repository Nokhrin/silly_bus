package com.nokhrin.interpreter.visitors;

import com.nokhrin.interpreter.*;
import com.nokhrin.interpreter.symbol_table.GlobalScope;
import com.nokhrin.interpreter.symbol_table.Symbol;
import com.nokhrin.interpreter.symbol_table.VariableSymbol;
import org.antlr.v4.runtime.tree.AbstractParseTreeVisitor;

public class CalcEvalVisitor extends AbstractParseTreeVisitor<ExprValue>
        implements CalcVisitor<ExprValue> {

    private final GlobalScope globalScope;

    public CalcEvalVisitor(GlobalScope globalScope) {
        this.globalScope = globalScope;
    }

    //region helpers
    private double toDouble(ExprValue val) {
        return switch (val) {
            case IntValue intValue -> intValue.value();
            case DoubleValue doubleValue -> doubleValue.value();
        };
    }

    private ExprValue wrap(double val) {
        if (val == Math.floor(val) && !Double.isInfinite(val)) {
            return new IntValue((int) val);
        }
        return new DoubleValue(val);
    }

    private ExprValue add(ExprValue left, ExprValue right) {
        if (left instanceof IntValue l && right instanceof IntValue r) {
            return new IntValue(l.value() + r.value());
        }
        return new DoubleValue(toDouble(left) + toDouble(right));
    }

    private ExprValue sub(ExprValue left, ExprValue right) {
        if (left instanceof IntValue l && right instanceof IntValue r) {
            return new IntValue(l.value() - r.value());
        }
        return new DoubleValue(toDouble(left) - toDouble(right));
    }

    private ExprValue mul(ExprValue left, ExprValue right) {
        if (left instanceof IntValue l && right instanceof IntValue r) {
            return new IntValue(l.value() * r.value());
        }
        return new DoubleValue(toDouble(left) * toDouble(right));
    }

    private ExprValue div(ExprValue left, ExprValue right) {
        double divisor = toDouble(right);
        if (divisor == 0) {
            throw new ArithmeticException("Division by zero");
        }
        double result = toDouble(left) / divisor;
        if (result == Math.floor(result) && !Double.isInfinite(result)) {
            return new IntValue((long) result);
        }
        return new DoubleValue(result);
    }

    private ExprValue pow(ExprValue left, ExprValue right) {
        double result = Math.pow(toDouble(left), toDouble(right));
        if (left instanceof IntValue
                && right instanceof IntValue
                && result == Math.floor(result)
                && !Double.isInfinite(result)) {
            return new IntValue((long) result);
        }
        return new DoubleValue((double) result);
    }
    //endregion helpers

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
        Symbol symbol = globalScope.resolve(name);
        VariableSymbol var;
        if (symbol instanceof VariableSymbol v) {
            var = v;
        } else {
            Symbol.Type inferredType = switch (value) {
                case IntValue _ -> Symbol.Type.INT;
                case DoubleValue _ -> Symbol.Type.FLOAT;
            };
            var = new VariableSymbol(name, inferredType, globalScope);
            globalScope.define(var);
        }
        var.setValue(value);
        return value;
    }

    @Override
    public ExprValue visitExpr(CalcParser.ExprContext ctx) {
        return visit(ctx.addSub());
    }

    @Override
    public ExprValue visitAddSub(CalcParser.AddSubContext ctx) {
        ExprValue result = visit(ctx.mulDiv(0));
        for (int i = 1; i < ctx.mulDiv().size(); i++) {
            String op = ctx.getChild(2 * i - 1).getText();
            ExprValue right = visit(ctx.mulDiv(i));
            result = switch (op) {
                case "+" -> add(result, right);
                case "-" -> sub(result, right);
                default -> result;
            };
        }
        return result;
    }

    @Override
    public ExprValue visitMulDiv(CalcParser.MulDivContext ctx) {
        ExprValue result = visit(ctx.pow(0));
        for (int i = 1; i < ctx.pow().size(); i++) {
            String op = ctx.getChild(2 * i - 1).getText();
            ExprValue right = visit(ctx.pow(i));
            result = switch (op) {
                case "*" -> mul(result, right);
                case "/" -> div(result, right);
                default -> result;
            };
        }
        return result;
    }

    @Override
    public ExprValue visitPow(CalcParser.PowContext ctx) {
        int n = ctx.unary().size();
        ExprValue result = visit(ctx.unary(n - 1));
        for (int i = n - 2; i >= 0; i--) {
            result = pow(visit(ctx.unary(i)), result);
        }
        return result;
    }

    @Override
    public ExprValue visitNeg(CalcParser.NegContext ctx) {
        return wrap(-toDouble(visit(ctx.unary())));
    }

    @Override
    public ExprValue visitPos(CalcParser.PosContext ctx) {
        return visit(ctx.unary());
    }

    @Override
    public ExprValue visitFact(CalcParser.FactContext ctx) {
        ExprValue val = visit(ctx.unary());
        return switch (val) {
            case IntValue intValue -> {
                long n = intValue.value();
                if (n < 0) throw new IllegalArgumentException("Factorial of negative number");
                long res = 1;
                for (long i = 2; i <= n; i++) res *= i;
                yield new IntValue(res);
            }
            case DoubleValue doubleValue -> {
                throw new IllegalArgumentException("Factorial requires integer operand");
            }
        };

    }

    @Override
    public ExprValue visitNumber(CalcParser.NumberContext ctx) {
        String text = ctx.NUM().getText();
        return text.contains(".")
                ? new DoubleValue(Double.parseDouble(text))
                : new IntValue(Integer.parseInt(text));
    }

    @Override
    public ExprValue visitId(CalcParser.IdContext ctx) {
        String name = ctx.ID().getText();
        Symbol sym = globalScope.resolve(name);
        if (sym instanceof VariableSymbol var) return var.getValue();
        throw new IllegalArgumentException("Undefined variable: " + name);
    }

    @Override
    public ExprValue visitAbs(CalcParser.AbsContext ctx) {
        ExprValue val = visit(ctx.expr());
        double absVal = Math.abs(toDouble(val));
        return wrap(absVal);
    }

    @Override
    public ExprValue visitParen(CalcParser.ParenContext ctx) {
        return visit(ctx.expr());
    }

    @Override
    public ExprValue visitPrime(CalcParser.PrimeContext ctx) {
        return visit(ctx.atom());
    }

}