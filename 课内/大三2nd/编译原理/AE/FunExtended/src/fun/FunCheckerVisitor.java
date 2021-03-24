//////////////////////////////////////////////////////////////
//
// A visitor for contextual analysis of Fun.
//
// Based on a previous version developed by
// David Watt and Simon Gay (University of Glasgow).
//
//  Name: Yao Du
//  Date: 22/03/21
//
//////////////////////////////////////////////////////////////

package fun;

import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.tree.*;
import org.antlr.v4.runtime.misc.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import ast.*;

public class FunCheckerVisitor extends AbstractParseTreeVisitor<Type> implements FunVisitor<Type> {

	// Contextual errors

	private int errorCount = 0;

	private CommonTokenStream tokens;

	// Constructor

	public FunCheckerVisitor(CommonTokenStream toks) {
	    tokens = toks;
	}

	private void reportError (String message,
	                          ParserRuleContext ctx) {
	// Print an error message relating to the given 
	// part of the AST.
	    Interval interval = ctx.getSourceInterval();
	    Token start = tokens.get(interval.a);
	    Token finish = tokens.get(interval.b);
	    int startLine = start.getLine();
	    int startCol = start.getCharPositionInLine();
	    int finishLine = finish.getLine();
	    int finishCol = finish.getCharPositionInLine();
	    System.err.println(startLine + ":" + startCol + "-" +
                               finishLine + ":" + finishCol
		   + " " + message);
		errorCount++;
	}

	public int getNumberOfContextualErrors () {
	// Return the total number of errors so far detected.
		return errorCount;
	}


	// Scope checking
	// symbol table = type table
	private SymbolTable<Type> typeTable =
	   new SymbolTable<Type>();

	private void predefine () {
	// Add predefined procedures to the type table.
		// built-in proce
		typeTable.put("read",
		   new Type.Mapping(Type.EMPTY, Type.INT));

		ArrayList<Type> types = new ArrayList<Type>();
		types.add(Type.INT);
		Type.Sequence sequence = new Type.Sequence(types);
		typeTable.put("write",
		   new Type.Mapping(sequence, Type.VOID));
	}

	private void define (String id, Type type,
	                     ParserRuleContext decl) {
	// Add id with its type to the type table, checking 
	// that id is not already declared in the same scope.
		boolean ok = typeTable.put(id, type);
		if (!ok)
			reportError(id + " is redeclared", decl);
	}

	private Type retrieve (String id, ParserRuleContext occ) {
	// Retrieve id's type from the type table.
		Type type = typeTable.get(id);
		if (type == null) {
			reportError(id + " is undeclared", occ);
			return Type.ERROR;
		} else
			return type;
	}

	// Type checking

	private static final Type.Mapping
	   NOTTYPE = new Type.Mapping(Type.BOOL, Type.BOOL),
	   COMPTYPE = new Type.Mapping(
	      new Type.Pair(Type.INT, Type.INT), Type.BOOL),
	   ARITHTYPE = new Type.Mapping(
	      new Type.Pair(Type.INT, Type.INT), Type.INT),
	   MAINTYPE = new Type.Mapping(Type.EMPTY, Type.VOID);

	private void checkType (Type typeExpected,
	                        Type typeActual,
	                        ParserRuleContext construct) {
	// Check that a construct's actual type matches 
	// the expected type.
		if (! typeActual.equiv(typeExpected))
			reportError("type is " + typeActual
			   + ", should be " + typeExpected,
			   construct);
	}

	private Type checkCall (String id, Type typeArg,
	                        ParserRuleContext call) {
	// Check that a procedure call identifies a procedure 
	// and that its argument type matches the proecure's 
	// type. Return the type of the procedure call.
		Type typeProc = retrieve(id, call);
		if (! (typeProc instanceof Type.Mapping)) {
			reportError(id + " is not a procedure", call);
			return Type.ERROR;
		} else {
			Type.Mapping mapping = (Type.Mapping)typeProc;
			checkType(mapping.domain, typeArg, call);
			return mapping.range;
		}
	}

	private Type checkUnary (Type.Mapping typeOp,
	                         Type typeArg,
	                         ParserRuleContext op) {
	// Check that a unary operator's operand type matches 
	// the operator's type. Return the type of the operator 
	// application.
		if (! (typeOp.domain instanceof Type.Primitive))
			reportError(
			   "unary operator should have 1 operand",
			   op);
		else
			checkType(typeOp.domain, typeArg, op);
		return typeOp.range;
	}

	private Type checkBinary (Type.Mapping typeOp,
	                          Type typeArg1, Type typeArg2,
	                          ParserRuleContext op) {
	// Check that a binary operator's operand types match 
	// the operator's type. Return the type of the operator 
	// application.
		if (! (typeOp.domain instanceof Type.Pair))
			reportError(
			   "binary operator should have 2 operands",
			   op);
		else {
			Type.Pair pair =
			   (Type.Pair)typeOp.domain;
			checkType(pair.first, typeArg1, op);
			checkType(pair.second, typeArg2, op);
		}
		return typeOp.range;
	}

	/**
	 * Visit a parse tree produced by the {@code prog}
	 * labeled alternative in {@link FunParser#program}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	public Type visitProg(FunParser.ProgContext ctx) {
	    predefine();
	    visitChildren(ctx);
	    Type tmain = retrieve("main", ctx);
	    checkType(MAINTYPE, tmain, ctx);
	    return null;
	}

	/**
	 * Visit a parse tree produced by the {@code proc}
	 * labeled alternative in {@link FunParser#proc_decl}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	public Type visitProc(FunParser.ProcContext ctx) {
	    typeTable.enterLocalScope();
		Type t;
		// formal list
	    FunParser.Formal_decl_seqContext fd = ctx.formal_decl_seq();
	    // if args != null
	    if (fd != null)
		t = visit(fd);
	    else // empty args
		t = Type.EMPTY;

	    Type proctype = new Type.Mapping(t, Type.VOID); //proc mapping
	    define(ctx.ID().getText(), proctype, ctx);
	    List<FunParser.Var_declContext> var_decl = ctx.var_decl();
	    for (FunParser.Var_declContext vd : var_decl)
		visit(vd);
	    visit(ctx.seq_com());
	    typeTable.exitLocalScope();
	    define(ctx.ID().getText(), proctype, ctx);
	    return null;
	}

	/**
	 * Visit a parse tree produced by the {@code func}
	 * labeled alternative in {@link FunParser#proc_decl}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	public Type visitFunc(FunParser.FuncContext ctx) {
	    typeTable.enterLocalScope();
	    Type t1 = visit(ctx.type());
	    Type t2;
	    FunParser.Formal_decl_seqContext fd = ctx.formal_decl_seq();
	    if (fd != null)
		t2 = visit(fd);
	    else
		t2 = Type.EMPTY;

	    Type functype = new Type.Mapping(t2, t1);
	    define(ctx.ID().getText(), functype, ctx);
	    List<FunParser.Var_declContext> var_decl = ctx.var_decl();
	    for (FunParser.Var_declContext vd : var_decl)
		visit(vd);
	    visit(ctx.seq_com());
	    Type returntype = visit(ctx.expr());
	    checkType(t1, returntype, ctx);
	    typeTable.exitLocalScope();
	    define(ctx.ID().getText(), functype, ctx);
	    return null;
	}


	public Type.Sequence visitFormalseq(FunParser.FormalseqContext ctx) {
		// may multiple formals
		//List<Formal_declContext> formal_decl()
		List<FunParser.Formal_declContext> formal_declContexts = ctx.formal_decl();
		ArrayList<Type> results = new ArrayList<>();
		for (FunParser.Formal_declContext each: formal_declContexts
			 ) {
			// visit each formal declaration in the context list
			// return each types, need to be collected into ArrayList
			// to construct the Type.Sequence(ArrayList<Type>)
			results.add(visit(each));
		}
		return new Type.Sequence(results);
	}


	/**
	 * Visit a parse tree produced by the {@code formal}
	 * labeled alternative in {@link FunParser#formal_decl}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	public Type visitFormal(FunParser.FormalContext ctx) {
	    FunParser.TypeContext tc = ctx.type();
		Type t;
		// type never be null, optional now in formal_seq
		t = visit(tc);
		define(ctx.ID().getText(), t, ctx);
	    return t;
	}

	/**
	 * Visit a parse tree produced by the {@code var}
	 * labeled alternative in {@link FunParser#var_decl}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	public Type visitVar(FunParser.VarContext ctx) {
	    Type t1 = visit(ctx.type());
	    Type t2 = visit(ctx.expr());
	    define(ctx.ID().getText(), t1, ctx);
	    checkType(t1, t2, ctx);
	    return null;
	}

	/**
	 * Visit a parse tree produced by the {@code bool}
	 * labeled alternative in {@link FunParser#type}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	public Type visitBool(FunParser.BoolContext ctx) {
	    return Type.BOOL;
	}

	/**
	 * Visit a parse tree produced by the {@code int}
	 * labeled alternative in {@link FunParser#type}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	public Type visitInt(FunParser.IntContext ctx) {
	    return Type.INT;
	}

	/**
	 * Visit a parse tree produced by the {@code assn}
	 * labeled alternative in {@link FunParser#com}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	public Type visitAssn(FunParser.AssnContext ctx) {
	    Type tvar = retrieve(ctx.ID().getText(), ctx);
	    Type t = visit(ctx.expr());
	    checkType(tvar, t, ctx);
	    return null;
	}

	/**
	 * Visit a parse tree produced by the {@code proccall}
	 * labeled alternative in {@link FunParser#com}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	public Type visitProccall(FunParser.ProccallContext ctx) {
		// actual_seq params might be null,
		Type t;
		if (ctx.actual_seq() != null) {
			t = visit(ctx.actual_seq());
		}else{
			t = new Type.Sequence(new ArrayList<Type>());
		}
		Type tres = checkCall(ctx.ID().getText(), t, ctx);
	    if (! tres.equiv(Type.VOID))
		reportError("procedure should be void", ctx);
	    return null;
	}

	/**
	 * Visit a parse tree produced by the {@code if}
	 * labeled alternative in {@link FunParser#com}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	public Type visitIf(FunParser.IfContext ctx) {
	    Type t = visit(ctx.expr());
	    visit(ctx.c1);
	    if (ctx.c2 != null)
		visit(ctx.c2);
	    checkType(Type.BOOL, t, ctx);
	    return null;
	}

	/**
	 * Visit a parse tree produced by the {@code while}
	 * labeled alternative in {@link FunParser#com}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	public Type visitWhile(FunParser.WhileContext ctx) {
	    Type t = visit(ctx.expr());
	    visit(ctx.seq_com());
	    checkType(Type.BOOL, t, ctx);
	    return null;
	}

	/////////////// Extension - for command node
	@Override
	public Type visitFor(FunParser.ForContext ctx) {
		visit(ctx.seq_com());
		// check expr1, expr2 type whether are INT & matched
		// checkType (Type typeExpected, Type typeActual)
		Type t1 = visit(ctx.expr1);
		checkType(Type.INT, t1, ctx.expr1);
		Type t2 = visit(ctx.expr2);
		checkType(Type.INT, t2, ctx.expr2);
		// check control variable is pre-defined or not
		// retrive local var ID in type table
		checkType(Type.INT, retrieve(ctx.ID().getText(), ctx), ctx);
		return null;
	}

	////////////Extension - Switch command
	/**
	 * Visit a parse tree produced by the {@code switch}
	 * labeled alternative in {@link FunParser#com}.
	 *
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	@Override
	public Type visitSwitch(FunParser.SwitchContext ctx) {
		//Set to process guard check
		Set<String> guards = new HashSet<String>();
		//expr be INT|BOOL
		// expr type must be consistent with case-guard
		Type expr_type = visit(ctx.expr());
		// list of case ctx
		List<FunParser.Case_comContext> case_comContexts = ctx.case_com();
		for (FunParser.Case_comContext each :
				case_comContexts) {
			Type case_type = visit(each);
			FunParser.CasecomContext casecomContext = (FunParser.CasecomContext) each;
			checkType(expr_type, case_type, casecomContext.g);
			//	guard CANNOT overlap, duplicates NOT ALLOWED
			//	guard: INT|BOOL(TRUE,FALSE)|INT..INT

			// guard is a range of numbers
			// NUM RANGE NUM = 1 .. 4
			if (casecomContext.guard().RANGE() != null) {
				int lowerBound = Integer.parseInt(casecomContext.guard().NUM(0).getText());
				int upperBound = Integer.parseInt(casecomContext.guard().NUM(1).getText());
				if (lowerBound >= upperBound) {
					reportError("Invalid guard range " + lowerBound + ".." + upperBound, casecomContext.g);
				}
				for (int i = lowerBound; i <= upperBound; i++) {
					String guard = String.valueOf(i);
					if (guards.contains(guard)) {
						reportError("Guard " + casecomContext.guard().getText() + " confilts with other guards before", casecomContext.g);
						break;
					} else {
						guards.add(guard);
					}
				}
			}else{
				String guard = casecomContext.g.getText();
				if (guards.contains(guard)) {
					reportError("Duplicate guard " + guard + " already exists", casecomContext.g);
				} else {
					guards.add(guard);
				}
			}
		}
		//	visit default cases
		visit(ctx.de_case());
		return null;
	}

	/**
	 * Visit a parse tree produced by the {@code casecom}
	 * labeled alternative in {@link FunParser#case_com}.
	 *
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	@Override
	public Type visitCasecom(FunParser.CasecomContext ctx) {
		//CASE g=guard COLON
		//		seq_com
		visit(ctx.seq_com());
		return visit(ctx.guard());
	}

	/**
	 * Visit a parse tree produced by the {@code defaultcase}
	 * labeled alternative in {@link FunParser#de_case}.
	 *
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	@Override
	public Type visitDefaultcase(FunParser.DefaultcaseContext ctx) {
		//de_case
		//: DEFAULT COLON
		//seq_com
		visit(ctx.seq_com());
		return null;
	}

	/**
	 * Visit a parse tree produced by {@link FunParser#guard}.
	 *
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	@Override
	public Type visitGuard(FunParser.GuardContext ctx) {
		if (ctx.FALSE() != null || ctx.TRUE() != null) {
			return Type.BOOL;
		}
		return Type.INT;
	}
	/////////////////////Extension End

	/**
	 * Visit a parse tree produced by the {@code seq}
	 * labeled alternative in {@link FunParser#seq_com}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	public Type visitSeq(FunParser.SeqContext ctx) {
	    visitChildren(ctx);
	    return null;
	}

	/**
	 * Visit a parse tree produced by {@link FunParser#expr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	public Type visitExpr(FunParser.ExprContext ctx) {
	    Type t1 = visit(ctx.e1);
	    if (ctx.e2 != null) {
		Type t2 = visit(ctx.e2);
		return checkBinary(COMPTYPE, t1, t2, ctx);
		// COMPTYPE is INT x INT -> BOOL
		// checkBinary checks that t1 and t2 are INT and returns BOOL
		// If necessary it produces an error message.
	    }
	    else {
		return t1;
	    }
	}

	/**
	 * Visit a parse tree produced by {@link FunParser#sec_expr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	public Type visitSec_expr(FunParser.Sec_exprContext ctx) {
	    Type t1 = visit(ctx.e1);
	    if (ctx.e2 != null) {
		Type t2 = visit(ctx.e2);
		return checkBinary(ARITHTYPE, t1, t2, ctx);
	    }
	    else {
		return t1;
	    }
	}

	/**
	 * Visit a parse tree produced by the {@code false}
	 * labeled alternative in {@link FunParser#prim_expr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	public Type visitFalse(FunParser.FalseContext ctx) {
	    return Type.BOOL;
	}

	/**
	 * Visit a parse tree produced by the {@code true}
	 * labeled alternative in {@link FunParser#prim_expr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	public Type visitTrue(FunParser.TrueContext ctx) {
	    return Type.BOOL;
	}

	/**
	 * Visit a parse tree produced by the {@code num}
	 * labeled alternative in {@link FunParser#prim_expr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	public Type visitNum(FunParser.NumContext ctx) {
	    return Type.INT;
	}

	/**
	 * Visit a parse tree produced by the {@code id}
	 * labeled alternative in {@link FunParser#prim_expr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	public Type visitId(FunParser.IdContext ctx) {
	    return retrieve(ctx.ID().getText(), ctx);
	}

	/**
	 * Visit a parse tree produced by the {@code funccall}
	 * labeled alternative in {@link FunParser#prim_expr}.
	 *
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	public Type visitFunccall(FunParser.FunccallContext ctx) {
		Type t;
		if (ctx.actual_seq() != null) {
			t = visit(ctx.actual_seq());
		} else {
			t = new Type.Sequence(new ArrayList<Type>());
		}
		// return the type of procedure call results
		Type tres = checkCall(ctx.ID().getText(), t, ctx);
		if (tres.equiv(Type.VOID)) {
			reportError("procedure(func result) should be non-void", ctx);
		}
		return tres;
	}

	/**
	 * Visit a parse tree produced by the {@code not}
	 * labeled alternative in {@link FunParser#prim_expr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	public Type visitNot(FunParser.NotContext ctx) {
	    Type t = visit(ctx.prim_expr());
	    return checkUnary(NOTTYPE, t, ctx);
	}

	/**
	 * Visit a parse tree produced by the {@code parens}
	 * labeled alternative in {@link FunParser#prim_expr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	public Type visitParens(FunParser.ParensContext ctx) {
	    return visit(ctx.expr());
	}

	/**
	 * Visit a parse tree produced by {@link FunParser#actual}.
	 *
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	public Type.Sequence visitActualseq(FunParser.ActualseqContext ctx) {
		// public List<FunParser.ExprContext> expr()
		// must not be null
		ArrayList<Type> types = new ArrayList<>();
		List<FunParser.ExprContext> exprContextList = ctx.expr();
		for (FunParser.ExprContext each: exprContextList
			 ) {
			Type type = visitExpr(each);
			types.add(type);
		}
		return new Type.Sequence(types);
	}

}
