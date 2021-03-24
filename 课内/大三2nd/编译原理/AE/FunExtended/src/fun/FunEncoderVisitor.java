//////////////////////////////////////////////////////////////
//
// A visitor for code generation for Fun.
//
// Based on a previous version developed by
// David Watt and Simon Gay (University of Glasgow).
//
//	Name: Yao Du
//	Date: 23/03/21
/////////////////////////////////////////////////////////////////
//	For-command('for' ident = expr1(int) 'to' expr2(int): seq-com .)
//	Code Template:
//  1. code to initialize the control variable
//  (retrieve control variable in address table, get the local var address)
//	2. code to evaluate expr1
//  4. code to store expr1 to control var addr
//  5. code to evaluate expr2
//	6. code to load control var and test control variable and expr2 (COMPGT control var & expr2)
//	7. JUMPT (conditional jumps, if control var > expr2 true, jumps to line 11<this addr should be patched later>)
//	8. code to evaluate seq-com (sequential commands)
//	9. code to increment control variable (
//		retreive value stored in control var addr,
//		code to increment the value
//		store results into control var addr	)
// 	10. JUMP (unconditional jumps backwards, jump to line 6 to test the updated control var again)
//  11. exit
////////////////////////////////////////////////////////////////
//	Switch-command('switch' expr(int) : (case guard:seq-com)* default:seq-com .)
//
//  Code Template for **NON-RANGE guard** case:
// 1. code to evaluate expr for each case-com
// 2. code to load the guard for each case-com (
//		if the guard is not RANGE, i.e. NUM|FALSE|TRUE, directly load corresponding constant )
// 3. code to test whether expr == guard (CMPEQ) for each case-com
// 4. JUMPF (conditional jumps, if expr == guard false, jumps to line 7 the start point of next (default) case <this addr should be patched later>)
// 5. code to evaluate seq-com for each case-com
// 6. JUMP (unconditional jumps, jumps to the end of switch body, i.e. addr after default case <this addr should be patched later>)
// 7. code to evaluate default case seq-com or just another case starts points (reuse this template)
// 8. exit (only if line  7 is default case)
//
//  Code Template for **RANGE guard** case: (if the guard is RANGE, i.e. NUM..NUM, load corresponding 2 constant, lowerBound & upperBound))
// 1. code to evaluate expr for each case-com
// 2. code to load the lowerBound guard for each case-com
// 3. code to test whether expr < lowerBound (CMPLT) for each case-com
// 4. JUMPT (conditional jumps, if expr < lowerBound True, jumps to line 7 the start point of next (default)case <this addr should be patched later>)
// 5. code to evaluate expr for each case-com again (assume last expr value is consumed, so reloaded)
// 6. code to load the upperBound guard for each case-com
// 7. code to test whether expr > upperBound (CMPGT) for each case-com
// 8. JUMPT (conditional jumps, if expr > upperBound True, jumps to line 7 the start point of next (default)case <this addr should be patched later>)
// 5. code to evaluate seq-com for each case-com
// 6. JUMP (unconditional jumps, jumps to the end of switch body, i.e. addr after default case <this addr should be patched later>)
// 7. code to evaluate default case seq-com or just another case starts points (reuse this template)
// 8. exit (only if line  7 is default case)
//
//////////////////////////////////////////////////////////////

package fun;

import org.antlr.v4.runtime.tree.*;

import java.util.ArrayList;
import java.util.List;

import ast.*;

public class FunEncoderVisitor extends AbstractParseTreeVisitor<Void> implements FunVisitor<Void> {

	private SVM obj = new SVM();

	private int globalvaraddr = 0;
	private int localvaraddr = 0;
	private int currentLocale = Address.GLOBAL;

	private SymbolTable<Address> addrTable =
	   new SymbolTable<Address>();

	private void predefine () {
	// Add predefined procedures to the address table.
		addrTable.put("read",
		   new Address(SVM.READOFFSET, Address.CODE));
		addrTable.put("write",
		   new Address(SVM.WRITEOFFSET, Address.CODE));
	}

	public SVM getSVM() {
	    return obj;
	}

	/**
	 * Visit a parse tree produced by the {@code prog}
	 * labeled alternative in {@link FunParser#program}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	public Void visitProg(FunParser.ProgContext ctx) {
	    predefine();
	    List<FunParser.Var_declContext> var_decl = ctx.var_decl();
	    for (FunParser.Var_declContext vd : var_decl)
		visit(vd);
	    int calladdr = obj.currentOffset();
	    obj.emit12(SVM.CALL, 0);
	    obj.emit1(SVM.HALT);
	    List<FunParser.Proc_declContext> proc_decl = ctx.proc_decl();
	    for (FunParser.Proc_declContext pd : proc_decl)
		visit(pd);
	    int mainaddr = addrTable.get("main").offset;
	    obj.patch12(calladdr, mainaddr);
	    return null;
	}

	/**
	 * Visit a parse tree produced by the {@code proc}
	 * labeled alternative in {@link FunParser#proc_decl}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	public Void visitProc(FunParser.ProcContext ctx) {
	    String id = ctx.ID().getText();
	    Address procaddr = new Address(obj.currentOffset(), Address.CODE);
	    addrTable.put(id, procaddr);
	    addrTable.enterLocalScope();
	    currentLocale = Address.LOCAL;
	    localvaraddr = 2;
	    // ... allows 2 words for link data
	    FunParser.Formal_decl_seqContext fd = ctx.formal_decl_seq();
	    if (fd != null)
		visit(fd);
	    List<FunParser.Var_declContext> var_decl = ctx.var_decl();
	    for (FunParser.Var_declContext vd : var_decl)
		visit(vd);
	    visit(ctx.seq_com());
	    obj.emit11(SVM.RETURN, 0);
	    addrTable.exitLocalScope();
	    currentLocale = Address.GLOBAL;
	    return null;
	}

	/**
	 * Visit a parse tree produced by the {@code func}
	 * labeled alternative in {@link FunParser#proc_decl}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	public Void visitFunc(FunParser.FuncContext ctx) {
	    String id = ctx.ID().getText();
	    Address procaddr = new Address(obj.currentOffset(), Address.CODE);
	    addrTable.put(id, procaddr);
	    addrTable.enterLocalScope();
	    currentLocale = Address.LOCAL;
	    localvaraddr = 2;
	    // ... allows 2 words for link data
		FunParser.Formal_decl_seqContext fd = ctx.formal_decl_seq();
		if (fd != null)
		visit(fd);
	    List<FunParser.Var_declContext> var_decl = ctx.var_decl();
	    for (FunParser.Var_declContext vd : var_decl)
		visit(vd);
	    visit(ctx.seq_com());
            visit(ctx.expr());
	    obj.emit11(SVM.RETURN, 1);
	    addrTable.exitLocalScope();
	    currentLocale = Address.GLOBAL;
	    return null;
	}

	@Override
	public Void visitFormalseq(FunParser.FormalseqContext ctx) {
		for (FunParser.Formal_declContext each :
				ctx.formal_decl()) {
			visit(each);
		}
		return null;
	}

	/**
	 * Visit a parse tree produced by the {@code formal}
	 * labeled alternative in {@link FunParser#formal_decl}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	public Void visitFormal(FunParser.FormalContext ctx) {
	    FunParser.TypeContext tc = ctx.type();
		String id = ctx.ID().getText();
		addrTable.put(id, new Address(localvaraddr++, Address.LOCAL));
		obj.emit11(SVM.COPYARG, 1); 
	    return null;
	}


	/**
	 * Visit a parse tree produced by the {@code var}
	 * labeled alternative in {@link FunParser#var_decl}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	public Void visitVar(FunParser.VarContext ctx) {
	    visit(ctx.expr());
	    String id = ctx.ID().getText();
	    switch (currentLocale) {
	    case Address.LOCAL:
		addrTable.put(id, new Address(
					      localvaraddr++, Address.LOCAL));
		break;
	    case Address.GLOBAL:
		addrTable.put(id, new Address(
					      globalvaraddr++, Address.GLOBAL));
	    }
	    return null;
	}

	/**
	 * Visit a parse tree produced by the {@code bool}
	 * labeled alternative in {@link FunParser#type}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	public Void visitBool(FunParser.BoolContext ctx) {
	    return null;
	}

	/**
	 * Visit a parse tree produced by the {@code int}
	 * labeled alternative in {@link FunParser#type}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	public Void visitInt(FunParser.IntContext ctx) {
	    return null;
	}

	/**
	 * Visit a parse tree produced by the {@code assn}
	 * labeled alternative in {@link FunParser#com}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	public Void visitAssn(FunParser.AssnContext ctx) {
	    visit(ctx.expr());
	    String id = ctx.ID().getText();
	    Address varaddr = addrTable.get(id);
	    switch (varaddr.locale) {
	    case Address.GLOBAL:
		obj.emit12(SVM.STOREG,varaddr.offset);
		break;
	    case Address.LOCAL:
		obj.emit12(SVM.STOREL,varaddr.offset);
	    }
	    return null;
	}

	/**
	 * Visit a parse tree produced by the {@code proccall}
	 * labeled alternative in {@link FunParser#com}.
	 *
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	public Void visitProccall(FunParser.ProccallContext ctx) {
		//whether has actual params
		if (ctx.actual_seq() != null) {
			visit(ctx.actual_seq());
		}
		String id = ctx.ID().getText();
		Address procaddr = addrTable.get(id);
		// Assume procaddr.locale == CODE.
		obj.emit12(SVM.CALL, procaddr.offset);
		return null;
	}

	/**
	 * Visit a parse tree produced by the {@code if}
	 * labeled alternative in {@link FunParser#com}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	public Void visitIf(FunParser.IfContext ctx) {
	    visit(ctx.expr());
	    int condaddr = obj.currentOffset();
	    obj.emit12(SVM.JUMPF, 0);
	    if (ctx.c2 == null) { // IF without ELSE
		visit(ctx.c1);
		int exitaddr = obj.currentOffset();
		obj.patch12(condaddr, exitaddr);
	    }
	    else {                // IF ... ELSE
		visit(ctx.c1);
		int jumpaddr = obj.currentOffset();
		obj.emit12(SVM.JUMP, 0);
		int elseaddr = obj.currentOffset();
		obj.patch12(condaddr, elseaddr);
		visit(ctx.c2);
		int exitaddr = obj.currentOffset();
		obj.patch12(jumpaddr, exitaddr);
	    }
	    return null;
	}

	/**
	 * Visit a parse tree produced by the {@code while}
	 * labeled alternative in {@link FunParser#com}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	public Void visitWhile(FunParser.WhileContext ctx) {
	    int startaddr = obj.currentOffset();
	    visit(ctx.expr());
	    int condaddr = obj.currentOffset();
	    obj.emit12(SVM.JUMPF, 0);
	    visit(ctx.seq_com());
	    obj.emit12(SVM.JUMP, startaddr);
	    int exitaddr = obj.currentOffset();
	    obj.patch12(condaddr, exitaddr);
	    return null;
	}

	///////////////////////////Extension - For command Starts
	/**
	 * Visit a parse tree produced by the {@code for}
	 * labeled alternative in {@link FunParser#com}.
	 *
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	@Override
	public Void visitFor(FunParser.ForContext ctx) {
		// retrive addr of local control var
		Address controlVar = addrTable.getLocal(ctx.ID().getText());
		// evaluate expr1
		visit(ctx.expr1);
		// store expr1 results into control var addr
		obj.emit12(SVM.STOREL, controlVar.offset);

		// Addr: update control var , used for unconditional jump
		int startaddr = obj.currentOffset();
		// load value from control var addr
		obj.emit12(SVM.LOADL, controlVar.offset);
		//	evaluate expr2
		visit(ctx.expr2);
		// test control var > expr2
		obj.emit1(SVM.CMPGT);

		// Addr: starts point of conditional jump would be patched to exit point later
		int conaddr = obj.currentOffset();
		//	JUMPT if control var greater than expr2, jumps to the end
		obj.emit12(SVM.JUMPT, 0);

		// evaluate seq-com
		visit(ctx.seq_com());

		// load value from control var addr
		obj.emit12(SVM.LOADL, controlVar.offset);
		// incremtent
		obj.emit1(SVM.INC);
		// store value to control var addr
		obj.emit12(SVM.STOREL, controlVar.offset);

		// Unconditional JUMP (backward) to where start load control var and test
		obj.emit12(SVM.JUMP, startaddr);

		//exit, patch addr of conditional jump to correct addr
		obj.patch12(conaddr, obj.currentOffset());
		return null;
	}
	/////////////////////////Extension - for ends
	/////////////////////////Extension - switch starts
	/**
	 * Visit a parse tree produced by the {@code switch}
	 * labeled alternative in {@link FunParser#com}.
	 *
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	@Override
	public Void visitSwitch(FunParser.SwitchContext ctx) {
		// store all the initial end points of each case com, need to be patched here later
		ArrayList<Integer> caseEndsAddrs = new ArrayList<>();

		// evaluate each case com
		for (FunParser.Case_comContext each :
				ctx.case_com()) {
			// evaluate switch expr
			visit(ctx.expr());
			// inside would only cover case test, any JUMPF, JUMP won't be covered inside
			visit(each);

			FunParser.CasecomContext eachCase = (FunParser.CasecomContext) each;
			// not a RANGE guard
			if (eachCase.g.RANGE() == null) {
				// Addr: JUMPF starts point, should be patched later to next case
				int moveNextAddr = obj.currentOffset();
				obj.emit12(SVM.JUMPF, 0);

				// evaluate seq-com
				visit(eachCase.seq_com());

				// unconditional JUMP(forward), to the end of switch
				// unable to process/patch endAddr inside, as addr behind Default case needed
				// so some tricks here, add to list defined outside then process outside
				int endAddr = obj.currentOffset();
				caseEndsAddrs.add(endAddr);
				obj.emit12(SVM.JUMP, 0);

				// Addr: next case test starts points
				int nextCaseAddr = obj.currentOffset();
				obj.patch12(moveNextAddr, nextCaseAddr);
			} else {
				// DO other things to process range guard
				// load 2 NUM - lowerBound & upperBound
				TerminalNode lowerBound = eachCase.g.NUM(0);
				TerminalNode upperBound = eachCase.g.NUM(1);

				// consider we have 1 expr value loaded outside,
				// load lowerBound first (manually, visit doesn't work)
				obj.emit12(SVM.LOADC, Integer.parseInt(lowerBound.getText()));
				// test whether expr < lowerBound, if true jump to next case/default start point
				obj.emit1(SVM.CMPLT);

				// Addr JUMPT starts point, should be patched later to next case start point
				int moveNextAddr1 = obj.currentOffset();
				obj.emit12(SVM.JUMPT, 0);

				// load expr value again
				visit(ctx.expr());
				// load upperBound then (manually, visit doesn't work)
				obj.emit12(SVM.LOADC, Integer.parseInt(upperBound.getText()));
				// test whether expr > upperBound, if true jump to next case/default start point
				obj.emit1(SVM.CMPGT);

				// Addr JUMPF starts point, should be patched later to next case start point
				int moveNextAddr2 = obj.currentOffset();
				obj.emit12(SVM.JUMPT, 0);

				// evaluate seq-com (case-com-body)
				visit(eachCase.seq_com());


				// unconditional JUMP(forward), to the end of switch
				// unable to process/patch endAddr inside, as addr behind Default case needed
				// so some tricks here, add to list defined outside then process outside
				int endAddr = obj.currentOffset();
				caseEndsAddrs.add(endAddr);
				obj.emit12(SVM.JUMP, 0);

				// Addr: next case test starts points
				int nextCaseAddr = obj.currentOffset();
				obj.patch12(moveNextAddr1, nextCaseAddr);
				obj.patch12(moveNextAddr2, nextCaseAddr);
			}
		}
		// evaluate default case
		visit(ctx.de_case());
		// end addr of switch body
		int endSwitchAddr = obj.currentOffset();

		for (Integer each :
				caseEndsAddrs) {
			// patch all the case ends point JUMP to right addr
			obj.patch12(each, endSwitchAddr);
		}

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
	public Void visitCasecom(FunParser.CasecomContext ctx) {
		// evaluate, load guard value, if RANGE is null(i.e. not a range guard)
		if (ctx.g.RANGE() == null) {
			visit(ctx.g);
			// since single guard, directly test whether expr == guard (or falls in guard)
			obj.emit1(SVM.CMPEQ);
		}
		return null;
	}

	/**
	 * Visit a parse tree produced by the {@code defaultcase}
	 * labeled alternative in {@link FunParser#de_case}.
	 *
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	@Override
	public Void visitDefaultcase(FunParser.DefaultcaseContext ctx) {
		// evaluate seq-com
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
	public Void visitGuard(FunParser.GuardContext ctx) {
		// visit the node doesn't work here(won't emit, LOAD guard so choose to emit manually)
		if (ctx.TRUE() != null) {
			obj.emit12(SVM.LOADC,1);
		} else if (ctx.FALSE() != null) {
			obj.emit12(SVM.LOADC,0);
		} else if (ctx.RANGE() == null) {
			//not range, directly visit single NUM() node
			obj.emit12(SVM.LOADC, Integer.parseInt(ctx.NUM(0).getText()));
		}
		return null;
	}
	/////////////////////////////////Extension swtich Ends

	/**
	 * Visit a parse tree produced by the {@code seq}
	 * labeled alternative in {@link FunParser#seq_com}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	public Void visitSeq(FunParser.SeqContext ctx) {
	    visitChildren(ctx);
	    return null;
	}

	/**
	 * Visit a parse tree produced by {@link FunParser#expr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	public Void visitExpr(FunParser.ExprContext ctx) {
	    visit(ctx.e1);
	    if (ctx.e2 != null) {
		visit(ctx.e2);
		switch (ctx.op.getType()) {
		case FunParser.EQ:
		    obj.emit1(SVM.CMPEQ);
		    break;
		case FunParser.LT:
		    obj.emit1(SVM.CMPLT);
		    break;
		case FunParser.GT:
		    obj.emit1(SVM.CMPGT);
		    break;
		}
	    }
	    return null;
	}

	/**
	 * Visit a parse tree produced by {@link FunParser#sec_expr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	public Void visitSec_expr(FunParser.Sec_exprContext ctx) {
	    visit(ctx.e1);
	    if (ctx.e2 != null) {
		visit(ctx.e2);
		switch (ctx.op.getType()) {
		case FunParser.PLUS:
		    obj.emit1(SVM.ADD);
		    break;
		case FunParser.MINUS:
		    obj.emit1(SVM.SUB);
		    break;
		case FunParser.TIMES:
		    obj.emit1(SVM.MUL);
		    break;
		case FunParser.DIV:
		    obj.emit1(SVM.DIV);
		    break;
		}
	    }
	    return null;
	}

	/**
	 * Visit a parse tree produced by the {@code false}
	 * labeled alternative in {@link FunParser#prim_expr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	public Void visitFalse(FunParser.FalseContext ctx) {
	    obj.emit12(SVM.LOADC, 0);
	    return null;
	}

	/**
	 * Visit a parse tree produced by the {@code true}
	 * labeled alternative in {@link FunParser#prim_expr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	public Void visitTrue(FunParser.TrueContext ctx) {
	    obj.emit12(SVM.LOADC, 1);
	    return null;
	}

	/**
	 * Visit a parse tree produced by the {@code num}
	 * labeled alternative in {@link FunParser#prim_expr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	public Void visitNum(FunParser.NumContext ctx) {
	    int value = Integer.parseInt(ctx.NUM().getText());
	    obj.emit12(SVM.LOADC, value);
	    return null;
	}

	/**
	 * Visit a parse tree produced by the {@code id}
	 * labeled alternative in {@link FunParser#prim_expr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	public Void visitId(FunParser.IdContext ctx) {
	    String id = ctx.ID().getText();
	    Address varaddr = addrTable.get(id);
	    switch (varaddr.locale) {
	    case Address.GLOBAL:
		obj.emit12(SVM.LOADG,varaddr.offset);
		break;
	    case Address.LOCAL:
		obj.emit12(SVM.LOADL,varaddr.offset);
	    }
	    return null;
	}

	/**
	 * Visit a parse tree produced by the {@code funccall}
	 * labeled alternative in {@link FunParser#prim_expr}.
	 *
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	public Void visitFunccall(FunParser.FunccallContext ctx) {
		//whether func has actual params
		if (ctx.actual_seq() != null) {
			visit(ctx.actual_seq());
		}
		String id = ctx.ID().getText();
		Address funcaddr = addrTable.get(id);
		// Assume that funcaddr.locale == CODE.
		obj.emit12(SVM.CALL, funcaddr.offset);
		return null;
	}

	/**
	 * Visit a parse tree produced by the {@code not}
	 * labeled alternative in {@link FunParser#prim_expr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	public Void visitNot(FunParser.NotContext ctx) {
	    visit(ctx.prim_expr());
	    obj.emit1(SVM.INV); 
	    return null;
	}

	/**
	 * Visit a parse tree produced by the {@code parens}
	 * labeled alternative in {@link FunParser#prim_expr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	public Void visitParens(FunParser.ParensContext ctx) {
	    visit(ctx.expr());
	    return null;
	}


	/**
	 * Visit a parse tree produced by {@link FunParser#actual}.
	 *
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	@Override
	public Void visitActualseq(FunParser.ActualseqContext ctx) {
		// actual params sequences
		List<FunParser.ExprContext> exprContextList = ctx.expr();
		for (FunParser.ExprContext each :
				exprContextList) {
			visit(each);
		}
		return null;
	}
}
