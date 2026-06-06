package com.nokhrin.expr;

import org.antlr.v4.runtime.tree.TerminalNode;

import static com.nokhrin.expr.CalculatorLexer.*;

/**
 * Visitor. Семантический анализ и интерпретация: - вывод типов - вычисление значений - проверка
 * определенности переменных
 */
public class ASTBuilder extends CalculatorBaseVisitor<ExprValue> {
    private final SymbolTable symbolTable;

    public static double toDouble(ExprValue num) {
        return switch (num) {
            case ExprValue.IntValue intValue -> intValue.value();
            case ExprValue.DoubleValue doubleValue -> doubleValue.value();
        };
    }

    public ExprValue applyNegate(ExprValue num) {
        return switch (num) {
            case ExprValue.IntValue intValue -> new ExprValue.IntValue(-intValue.value());
            case ExprValue.DoubleValue doubleValue -> new ExprValue.DoubleValue(-doubleValue.value());
        };
    }

    public ExprValue applyFactorial(ExprValue num) {
        if (!(num instanceof ExprValue.IntValue(int n))) {
            throw new ArithmeticException("Факториал определен только для целых чисел");
        }
        if (n < 0) {
            throw new ArithmeticException("Факториал определен только для неотрицательных чисел");
        }
        long result = 1;
        for (int i = 1; i <= n; i++) {
            result *= i;
        }
        return new ExprValue.IntValue((int) result);
    }

    private ExprValue applyPower(ExprValue base, ExprValue exponent) {
        double b = toDouble(base);
        double e =toDouble(exponent);
        double result = Math.pow(b,e);

        if (base instanceof ExprValue.IntValue
                && exponent instanceof ExprValue.IntValue
                && result == Math.floor(result)
                && Double.isFinite(result)
                && result >= Integer.MIN_VALUE
                && result <= Integer.MAX_VALUE) {
            return new ExprValue.IntValue((int) result);
        }

        return new ExprValue.DoubleValue(result);
    }

    private ExprValue applyAbs(ExprValue num) {
        return switch (num) {
            case ExprValue.IntValue intValue -> new ExprValue.IntValue(Math.abs(intValue.value()));
            case ExprValue.DoubleValue doubleValue -> new ExprValue.DoubleValue(Math.abs(doubleValue.value()));
        };
    }

    public ExprValue applyBinOp(ExprValue left, ExprValue right, TerminalNode op) {
        double leftNum = toDouble(left);
        double rightNum = toDouble(right);

        double result =
                switch (op.getSymbol().getType()) {
                    case PLUS -> leftNum + rightNum;
                    case MINUS -> leftNum - rightNum;
                    case MUL -> leftNum * rightNum;
                    case DIV -> {
                        if (rightNum == 0) {
                            throw new ArithmeticException("Деление на ноль");
                        }
                        yield leftNum / rightNum;
                    }
                    default -> throw new IllegalStateException("Неизвестный оператор: " + op.getSymbol().getType());
                };

        if (left instanceof ExprValue.IntValue
                && right instanceof ExprValue.IntValue
                && result == Math.floor(result)
                && Double.isFinite(result)
                && result >= Integer.MIN_VALUE
                && result <= Integer.MAX_VALUE) {
            return new ExprValue.IntValue((int) result);
        }

        return new ExprValue.DoubleValue(result);
    }

    public ASTBuilder(SymbolTable symbolTable) {
        this.symbolTable = symbolTable;
    }

    // Синтаксическое правило
    // program   ::= statement {statement}
    // Семантическое правило
    @Override
    public ExprValue visitProgram(CalculatorParser.ProgramContext programContext) {
        ExprValue result = new ExprValue.IntValue(0);
        for (var statement : programContext.statement()) {
            result = visit(statement);
        }
        return result;
    }

    // Синтаксическое правило
    // statement ::= assignment | expression
    // Семантическое правило
    @Override
    public ExprValue visitStatement(CalculatorParser.StatementContext statementContext) {
        if (statementContext.assignment() != null) {
            return visit(statementContext.assignment());
        }
        return visit(statementContext.expression());
    }

    // Синтаксическое правило
    // assignment ::= id "=" expression
    // Семантическое правило
    @Override
    public ExprValue visitAssignment(CalculatorParser.AssignmentContext assignmentContext) {
        String name = assignmentContext.id().ID().getText();
        ExprValue value = visit(assignmentContext.expression());
        symbolTable.assign(name, value);
        return value;
    }


    // Синтаксическое правило
    // expression ::= sum
    // Семантическое правило
    @Override
    public ExprValue visitExpression(CalculatorParser.ExpressionContext expressionContext) {
        return visit(expressionContext.sum());
    }

    // Синтаксическое правило
    // sum        ::= mul { ("+" | "-") mul }
    // Семантическое правило
    @Override
    public ExprValue visitSum(CalculatorParser.SumContext sumContext) {
        // mul
        ExprValue result = visit(sumContext.mul(0));

        // { ("+" | "-") mul }
        for (int i = 1; i < sumContext.mul().size(); i++) {
            ExprValue right = visit(sumContext.mul(i));
            TerminalNode op = (TerminalNode) sumContext.children.get(i * 2 - 1);
            result = applyBinOp(result, right, op);
        }
        return result;
    }

    // Синтаксическое правило
    // mul        ::= pow { ("*" | "/") pow }
    // Семантическое правило
    @Override
    public ExprValue visitMul(CalculatorParser.MulContext mulContext) {
        // pow
        ExprValue result = visit(mulContext.pow(0));

        // { ("*" | "/") pow }
        for (int i = 1; i < mulContext.pow().size(); i++) {
            ExprValue right = visit(mulContext.pow(i));
            TerminalNode op = (TerminalNode) mulContext.children.get(i * 2 - 1);
            result = applyBinOp(result, right, op);
        }
        return result;
    }

    // Синтаксическое правило
    // pow        ::= un [ "^" pow ]
    // Семантическое правило
    @Override
    public ExprValue visitPow(CalculatorParser.PowContext powContext) {
        ExprValue base = visit(powContext.unary());
        if (powContext.POW() != null) {
            ExprValue exponent = visit(powContext.pow());
            return applyPower(base, exponent);
        }
        return base;
    }

    // Синтаксическое правило
    // un         ::= ["+" | "-"] fact
    // Семантическое правило
    @Override
    public ExprValue visitUnary(CalculatorParser.UnaryContext unaryContext) {
        if (unaryContext.factorial() != null) {
            return visitFactorial(unaryContext.factorial());
        }
        ExprValue value = visit(unaryContext.unary());
        if (unaryContext.MINUS() != null) {
            return applyNegate(value);
        }
        return value;
    }

    // Синтаксическое правило
    // fact       ::= prim [ "!" ]
    // Семантическое правило
    @Override
    public ExprValue visitFactorial(CalculatorParser.FactorialContext factorialContext) {
        if (factorialContext.EXCL() != null) {
            return applyFactorial(visit(factorialContext.prime()));
        }
        return visit(factorialContext.prime());
    }

    // Синтаксическое правило
    // prim       ::= num | id | "|" expression "|" | "(" expression ")"
    // Семантическое правило
    @Override
    public ExprValue visitPrime(CalculatorParser.PrimeContext primeContext) {
        if (primeContext.num() != null) return visit(primeContext.num());
        if (primeContext.id() != null) return visit(primeContext.id());
        if (primeContext.expression() != null) {
            ExprValue value = visit(primeContext.expression());
            if (primeContext.MOD() != null) {
                return applyAbs(value);
            }
            return value;
        }
        throw new IllegalArgumentException("Неизвестная грамматика");
    }

    // Синтаксическое правило
    // id         ::= letter { letter | digit }
    // Семантическое правило
    @Override
    public ExprValue visitId(CalculatorParser.IdContext idContext) {
        return symbolTable.get(idContext.ID().getText());
    }

    // Синтаксическое правило
    // num        ::= digit {digit} [ "." {digit} ] | "." digit {digit}
    // Семантическое правило
    @Override
    public ExprValue visitNum(CalculatorParser.NumContext numContext) {
        String numString = numContext.NUM().getText();

        if (numString.contains(".")) {
            return new ExprValue.DoubleValue(Double.parseDouble(numString));
        }
        return new ExprValue.IntValue(Integer.parseInt(numString));
    }
}
