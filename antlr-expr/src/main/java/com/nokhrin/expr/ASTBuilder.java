package com.nokhrin.expr;

import org.antlr.v4.runtime.tree.TerminalNode;

public class ASTBuilder extends CalculatorBaseVisitor<Integer> {
    @Override
    public Integer visitNum(CalculatorParser.NumContext numContext) {
        return Integer.parseInt(numContext.getText());
    }

    @Override
    public Integer visitSum(CalculatorParser.SumContext sumContext) {
        int result = visit(sumContext.mul(0));
        for (int i = 0; i < sumContext.mul().size() - 1; i++) {

            int value = visit(sumContext.mul(i + 1));
            TerminalNode op = (TerminalNode) sumContext.children.get(i * 2 + 1);

            if (op.getSymbol().getType() == CalculatorParser.PLUS) {
                result += value;
            } else if (op.getSymbol().getType() == CalculatorParser.MINUS) {
                result -= value;
            }
        }
        return result;
    }

    @Override
    public Integer visitMul(CalculatorParser.MulContext mulContext) {
        int result = visit(mulContext.prime(0));
        for (int i = 0; i < mulContext.prime().size() - 1; i++) {

            int value = visit(mulContext.prime(i + 1));
            TerminalNode op = (TerminalNode) mulContext.children.get(i * 2 + 1);

            if (op.getSymbol().getType() == CalculatorParser.MUL) {
                result *= value;
            } else if (op.getSymbol().getType() == CalculatorParser.DIV) {
                if (value == 0) throw new ArithmeticException("Деление на ноль");
                result /= value;
            }
        }
        return result;
    }

    @Override
    public Integer visitPrime(CalculatorParser.PrimeContext primeContext) {
        if (primeContext.num() != null) {
            return visit(primeContext.num());
        } else {
            return visit(primeContext.sum());
        }
    }

}
