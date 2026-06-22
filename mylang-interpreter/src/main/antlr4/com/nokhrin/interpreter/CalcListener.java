// Generated from src/main/antlr4/com/nokhrin/interpreter/Calc.g4 by ANTLR 4.13.2
import org.antlr.v4.runtime.tree.ParseTreeListener;

/**
 * This interface defines a complete listener for a parse tree produced by
 * {@link CalcParser}.
 */
public interface CalcListener extends ParseTreeListener {
	/**
	 * Enter a parse tree produced by {@link CalcParser#prog}.
	 * @param ctx the parse tree
	 */
	void enterProg(CalcParser.ProgContext ctx);
	/**
	 * Exit a parse tree produced by {@link CalcParser#prog}.
	 * @param ctx the parse tree
	 */
	void exitProg(CalcParser.ProgContext ctx);
	/**
	 * Enter a parse tree produced by {@link CalcParser#stat}.
	 * @param ctx the parse tree
	 */
	void enterStat(CalcParser.StatContext ctx);
	/**
	 * Exit a parse tree produced by {@link CalcParser#stat}.
	 * @param ctx the parse tree
	 */
	void exitStat(CalcParser.StatContext ctx);
	/**
	 * Enter a parse tree produced by {@link CalcParser#assign}.
	 * @param ctx the parse tree
	 */
	void enterAssign(CalcParser.AssignContext ctx);
	/**
	 * Exit a parse tree produced by {@link CalcParser#assign}.
	 * @param ctx the parse tree
	 */
	void exitAssign(CalcParser.AssignContext ctx);
	/**
	 * Enter a parse tree produced by {@link CalcParser#expr}.
	 * @param ctx the parse tree
	 */
	void enterExpr(CalcParser.ExprContext ctx);
	/**
	 * Exit a parse tree produced by {@link CalcParser#expr}.
	 * @param ctx the parse tree
	 */
	void exitExpr(CalcParser.ExprContext ctx);
	/**
	 * Enter a parse tree produced by {@link CalcParser#mul}.
	 * @param ctx the parse tree
	 */
	void enterMul(CalcParser.MulContext ctx);
	/**
	 * Exit a parse tree produced by {@link CalcParser#mul}.
	 * @param ctx the parse tree
	 */
	void exitMul(CalcParser.MulContext ctx);
	/**
	 * Enter a parse tree produced by {@link CalcParser#unary}.
	 * @param ctx the parse tree
	 */
	void enterUnary(CalcParser.UnaryContext ctx);
	/**
	 * Exit a parse tree produced by {@link CalcParser#unary}.
	 * @param ctx the parse tree
	 */
	void exitUnary(CalcParser.UnaryContext ctx);
	/**
	 * Enter a parse tree produced by {@link CalcParser#prime}.
	 * @param ctx the parse tree
	 */
	void enterPrime(CalcParser.PrimeContext ctx);
	/**
	 * Exit a parse tree produced by {@link CalcParser#prime}.
	 * @param ctx the parse tree
	 */
	void exitPrime(CalcParser.PrimeContext ctx);
}