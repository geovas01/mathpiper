/*  This file is part of the source code for 3D-XplorMath-J, Version 1.0 (January 2008).
 *  Copyright (c) 2008 The 3D-XplorMath Consortium (http://3d-xplormath.org).
 *  This source code is released under a BSD License, which allows redistribution   
 *  in source and binary form, with or without modification, provided copyright
 *  and license information are included, and with no warranty or guarantee of
 *  any kind.  For details, see http://3d-xplormath.org/j/source/BSDLicense.txt
 */
 
 package vmm.functions;

import java.util.ArrayList;

/**
 * Package-private class that represents the compiled version of an expression
 * or function that has been parsed by a {@link Parser}.  A ProgFuntion is essentially
 * a list of commands that are carried out to evaluate an expression or function.
 * A ProgFunction can only be created by an object belonging to the static
 * nested class {@link ProgFunction.Builder}.  (Note that an "expression" is taken
 * to be a function of zero arguments and so is not given any special treatment.)
 * <p>A ProgFunction is evaluated using an {@link EvalStack} which contains all
 * the intermediate results used in the computation.  It also contains a copy of
 * the arguments to the function.
 */
final class ProgFunction {

	private ProgCommand[] commands;
	private int argCount;
	private Type argType;
	private Type type;
	
	private ProgFunction() {
	}
	
	/**
	 * Returns the number of arguemnts of this ProgFunction.  When a ProgFunction is applied
	 * to a stack, it expects this many arguments to have been placed on the stack.  This
	 * will be zero for an expression that has no arguments.
	 */
	int getArgCount() {
		return argCount;
	}

	/**
	 * Returns the type of this function, which will be either Type.REAL or Type.COMPLEX.
	 * The arguements of this function are of this type.  There is no support for
	 * having different arguments be of different type.
	 */
	Type getArgType() {
		return argType;
	}
	
	/**
	 * Gets the return type of this function, which will be of type Type.REAL or Type.COMPLEX.
	 * After the function has been applied to a stack, the arguemnts of the function (if any)
	 * will be replaced by a value of this type that represents the value of the function for
	 * those arguemnts.
	 */
	Type getType() {
		return type;
	}
			
	/**
	 * Apply this function to a stack.  The arguments, if any, should already have been pushed
	 * onto the stack.  After the evaluation, the arguements have been removed from the stack
	 * and have been replaced by the value of the function.
	 */
	void apply(EvalStack stack) {
		if (argCount == 0) {
			for (ProgCommand command : commands)
				command.apply(stack);
		}
		else {
			int sizeOfArgBlock = argType == Type.COMPLEX ? 2*argCount : argCount;
			stack.startFunctionCall(sizeOfArgBlock);
			for (ProgCommand command : commands)
				command.apply(stack);
			if (type == Type.COMPLEX)
				stack.endComplexValuedFunction(sizeOfArgBlock);
			else
				stack.endRealValuedFunction(sizeOfArgBlock);
		}
	}
	
	/**
	 * An object of this type is used (by a {@link Parser}) to create a ProgFunction.
	 * This is the only way to create a ProgFunction.  The builder provides methods
	 * for adding various types of commands to the ProgFunction that it is building.
	 */
	static class Builder {
		private ProgFunction func;
		private ArrayList<ProgCommand> commands;
		private ArrayList<ProgCommand> currentSubProg;
		private ArrayList<ArrayList<ProgCommand>> pendingSubProgs;
		private ArrayList<ArrayList<ProgCommand>> subProgs;
		private ArrayList<ConstRef> constants;
		private void add(ProgCommand command) {
			assert func != null;
			assert command != null;
			if (currentSubProg != null)
				currentSubProg.add(command);
			else
				commands.add(command);
//			System.out.println("added " + command);
		}
		void start(int argCount, Type argType) {  // Begin creating a function with specified number and type of arguemts.
			assert func == null;
			func = new ProgFunction();
			func.argType = argType;
			func.argCount = argCount;
			commands = new ArrayList<ProgCommand>();
		}
		void addStackOp(StackOp op) {  // Add a command to the function that represents applying a specified StackOp
			add(func.new StackOpRef(op));
		}
		void addArgumentReference(int argumentNumber) {  // Add a command that represents one of the arguments to the function
			add(func.new ArgRef(argumentNumber));
		}
		void addConditional(int trueCaseProgNum, int falseCaseProgNum, boolean trueCaseNeedsRealToComplex) {
			    // Add a command representing a conditional expression.  The trueCaseProgNum and
			    // falseCaseProgNum were returned by the startSubProg() method.  If
			    // trueCaseNeedsRealToComplex than a REAL_TO_COMPLEX StackOp command is added to the
			    // end of the trueCase ProgFunction.
			if (trueCaseNeedsRealToComplex)
				subProgs.get(trueCaseProgNum).add(func.new StackOpRef(StackOp.REAL_TO_COMPLEX));
			add(func.new Conditional(subProgs.get(trueCaseProgNum),subProgs.get(falseCaseProgNum)));
		}
		void addFunctionRef(ProgFunction func) {  // Add a command representing calling a ProgFunction
			add(func.new FunctionRef(func));
		}
		void addRealConstant(double x) {  //  Add a command representing a real constant.
			if (constants == null)
				constants = new ArrayList<ConstRef>();
			for (ConstRef c : constants) {
				if ( !(c instanceof ComplexConstRef) && c.re == x ) {
					add(c);
					return;
				}
			}
			ConstRef c = func.new ConstRef(x);
			constants.add(c);
			add(c);
		}
		void addComplexConstant(double re, double im) {  //  Add a command representing a Complex constant.
			if (constants == null)
				constants = new ArrayList<ConstRef>();
			for (ConstRef c : constants) {
				if ( (c instanceof ComplexConstRef) && c.re == re && ((ComplexConstRef)c).im == im ) {
					add(c);
					return;
				}
			}
			ComplexConstRef c = func.new ComplexConstRef(re,im);
			constants.add(c);
			add(c);
		}
		void addVariableRef(Variable v) {   //  Add a command representing a real variable.
			add(func.new VarRef(v));
		}
		void addComplexVariableRef(ComplexVariable v) {   //  Add a command representing a Complex variable.
			add(func.new ComplexVarRef(v));
		}
		int startSubProg() {  // Start parsing the true case or false case in a conditional; the return value identifies the sub-program.
			assert(func != null);
			if (currentSubProg != null) {
				pendingSubProgs = new ArrayList<ArrayList<ProgCommand>>();
				pendingSubProgs.add(currentSubProg);
			}
			currentSubProg = new ArrayList<ProgCommand>();
			if (subProgs == null)
				subProgs = new ArrayList<ArrayList<ProgCommand>>();
			subProgs.add(currentSubProg);
			return subProgs.size() - 1;
		}
		void finishSubProg() {  // Finish parsing the sub-expression that was started in startSubProg().
			assert(currentSubProg != null);
			if (pendingSubProgs != null && pendingSubProgs.size() > 0)
				currentSubProg = pendingSubProgs.remove(pendingSubProgs.size()-1);
			else 
				currentSubProg = null;
		}
		ProgFunction finish(Type valueType) {  // Finish parsing the function and specify the type of value that it computes.
			assert func != null;
			assert currentSubProg == null;
			func.type = valueType;
			func.commands = new ProgCommand[commands.size()];
			commands.toArray(func.commands);
			ProgFunction f = func;
			reset();
			return f;
		}
		void reset() {  // Throws away the function that is being built, so that Builder can start on a new function.
			func = null;
			commands = null;
			subProgs = null;
			currentSubProg = null;
			pendingSubProgs = null;
			constants = null;
		}
	}

	abstract private class ProgCommand {  // The command in the ProgFunction belong to this class
		abstract void apply(EvalStack stack);
	}

	private class ArgRef extends ProgCommand {  // A ProgCommand representing a reference to an argument of a function
		int argNum;
		ArgRef(int argNum) {
			this.argNum = argNum;
		}
		void apply(EvalStack stack) {
			if (argType == Type.COMPLEX)
				stack.fetchComplex(argNum*2);
			else
				stack.fetch(argNum);
		}
	}
	
	private class StackOpRef extends ProgCommand { // A ProgCommand representing a StackOp
		StackOp op;
		StackOpRef(StackOp op) {
			this.op = op;
		}
		void apply(EvalStack stack) {
			stack.apply(op);
		}
	}
	
	private class Conditional extends ProgCommand { // A ProgCommand representing the ternary conditional operator ?:
		ProgCommand[] trueCase, falseCase;
		Conditional(ArrayList<ProgCommand> trueCase, ArrayList<ProgCommand> falseCase) {
			this.trueCase = new ProgCommand[trueCase.size()];
			trueCase.toArray(this.trueCase);
			this.falseCase = new ProgCommand[falseCase.size()];
			falseCase.toArray(this.falseCase);
		}
		void apply(EvalStack stack) {
			if (stack.pop() == 0) {
				for (ProgCommand c : falseCase)
					c.apply(stack);
			}
			else {
				for (ProgCommand c : trueCase)
					c.apply(stack);
			}
		}
	}
	
	private class ConstRef extends ProgCommand { // A ProgCommand representing a real constant.
		double re;
		ConstRef(double x) {
			re = x;
		}
		void apply(EvalStack stack) {
			stack.push(re);
		}
	}
	
	private class ComplexConstRef extends ConstRef {  // A ProgCommand representing a complex constant.
		double im;
		ComplexConstRef(double re, double im) {
			super(re);
			this.im = im;
		}
		void apply(EvalStack stack) {
			stack.push(re,im);
		}
	}
	
	private class VarRef extends ProgCommand {   // A ProgCommand representing a reference to a real Variable
		Variable variable;
		VarRef(Variable v) {
			variable = v;
		}
		void apply(EvalStack stack) {
			stack.push(variable.getVal());
		}
	}
	
	private class ComplexVarRef extends ProgCommand { // A ProgCommand representing a reference to a ComplexVariable
		ComplexVariable variable;
		ComplexVarRef(ComplexVariable v) {
			variable = v;
		}
		void apply(EvalStack stack) {
			stack.push(variable.getRe(),variable.getIm());
		}
	}
	
	private class FunctionRef extends ProgCommand { // A ProgCommand representing a ProgFunction
		ProgFunction func;
		FunctionRef(ProgFunction f) {
			func = f;
		}
		void apply(EvalStack stack) {
			func.apply(stack);
		}
	}
			

}
