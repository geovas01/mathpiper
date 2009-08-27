/*  This file is part of the source code for 3D-XplorMath-J, Version 1.0 (January 2008).
 *  Copyright (c) 2008 The 3D-XplorMath Consortium (http://3d-xplormath.org).
 *  This source code is released under a BSD License, which allows redistribution   
 *  in source and binary form, with or without modification, provided copyright
 *  and license information are included, and with no warranty or guarantee of
 *  any kind.  For details, see http://3d-xplormath.org/j/source/BSDLicense.txt
 */
 
 package vmm.functions;

import java.util.HashMap;

import vmm.core.Complex;

/**
 * An object of type EvalStack is used in the evaluation of an expression or function
 * (as defined, for example, in the classes {@link Expression}, {@link Function},
 * and {@link ComplexFunction}).  The <code>value()</code> methods in such expressions
 * and fuctions can, optionally, take a parameter of type EvalStack.  In fact, the
 * only thing that you can do with an EvalStack, outside of the package vmm.functions,
 * is construct it and pass it as a parameter to one of these <code>value()</code>
 * methods; the only reason to do this is to evaluate functions and expressions in
 * a thread-safe way.
 */
public final class EvalStack {

	private double[] stack;
	private int top;
	
	private int addressBase;  // The base of the "activation record" that holds the
	                          // arguments of a ProgFunction that is being evaluaed.
	                          // This value changes in startFunctionCall(), endRealValuedFunction()
	                          // and endComplexValuedFunction().
	                          
	private static HashMap<Thread, EvalStack> perThreadStacks = new HashMap<Thread, EvalStack>();
	
	/**
	 * Create an EvalStack with an initial size of 30.
	 */
	public EvalStack() {
		this(30);
	}
	
	/**
	 * Create an EvalStack with a specified initial size.  The size will grow, if necessary,
	 * so it is generally sufficient to use the default constructor.
	 * @param initialSize the initial size of the stack.  If a size less than 1 is specified,
	 * then the initial size will be 1.
	 */
	public EvalStack(int initialSize) {
		if (initialSize < 1)
			initialSize = 1;
		stack = new double[initialSize];
	}
	
	/**
	 * Returns an EvalStack that is unique to the current thread.  This
	 * method simply calls {@link #perThread(Thread)}, with <code>Thread.currentThread()</code>
	 * as its parameter.
	 */
	final public static EvalStack perThread() {
		return perThread(Thread.currentThread());
	}
	
	/**
	 * The first time this method is called by a given thread, it creates and returns
	 * a new EvalStack; subsequent calls with return the same EvalStack rather than create
	 * a new one.  This meant to support thread-safe evalution of functions; this capability
	 * is used in evaluation methods such as {@link Expression#value()}, {@link Function#value(double[])}
	 * and {@link ComplexFunction#value(Complex[])}.  The method {@link #perThreadRelease(Thread)}
	 * can be called to discard the EvalStack that has been created for a given thread.
	 * @param thread the Thread whose associated EvalStack is to be returned.  If thread is null,
	 *    the return value is also null.
	 */
	final public static EvalStack perThread(Thread thread) {
		EvalStack stack = perThreadStacks.get(thread);
		if (stack == null && thread != null) {
			stack = new EvalStack();
			perThreadStacks.put(thread,stack);
		}
		return stack;
	}
	
	/**
	 * Discard the EvalStack, if any, that has been created for the specified thread by
	 * {@link #perThread()} or {@link #perThread(Thread)}.
	 * @param thread The thread whose EvalStack is to be discarded.  If the value is
	 *    null or if there is not EvalStack for the specified stack, nothing is done;
	 *    this is not an error.
	 */
	public static void perThreadRelease(Thread thread) {
		perThreadStacks.remove(thread);
	}
	
	void reset() {
		top = addressBase = 0;
	}
	
	boolean isEmpty() {
		return top == 0;
	}
	
	void startFunctionCall(int sizeOfArgBlock) {  // Called before applying a ProgFunction
		push(addressBase);
		addressBase = top-1-sizeOfArgBlock;
	}
	
	void endRealValuedFunction(int sizeOfArgBlock) { // Called after applying a ProgFunction
		double x = pop();
		addressBase = (int)pop();
		top -= sizeOfArgBlock;
		push(x);
	}
	
	void endComplexValuedFunction(int sizeOfArgBlock) { // Called afer applying a ProgFunction
		double a = pop();
		double b = pop();
		addressBase = (int)pop();
		top -= sizeOfArgBlock;
		push(b);
		push(a);
	}
	
	void push(double x) {
		if (stack.length == top) {
			double[] newStack = new double[top*2];
			System.arraycopy(stack, 0, newStack, 0, top);
			stack = newStack;
		}
		stack[top++] = x;
	}
	
	void push(double re, double im) {
		push(re);
		push(im);
	}
	
	void push(Complex c) {
		push(c.re);
		push(c.im);
	}
	
	double pop() {
		return stack[--top];
	}
	
	Complex popComplex() {
		double im = pop();
		double re = pop();
		return new Complex(re,im);
	}
	
	void popComplex(Complex c) {
		c.im = pop();
		c.re = pop();
	}
	
	void fetch(int address) {  // Used to copy a real-valued function argument from its position lower on the stack.
		push(stack[address+addressBase]);
	}
	
	void fetchComplex(int address) { // Used to copy a complex-valued function argument from its position lower on the stack.
		push(stack[address+addressBase]);
		push(stack[address+addressBase+1]);
	}
	
	void apply(StackOp op) {
		double x, y;
		double xr,xi, yr,yi;
		switch(op) {
		case COMPLEX_TO_REAL:
			pop();
			break;
		case REAL_TO_COMPLEX:
			push(0);
			break;
		case FIRST_OP_TO_COMPLEX:
			x = pop();
			y = pop();
			push(0);
			push(y);
			push(x);
			break;
		case IMAGINARY_PART:
			xi = pop();
			pop();
			push(xi);
			break;
		case PLUS:
			push(pop()+pop());
			break;
		case MINUS:
			y = pop();
			push(pop() - y);
			break;
		case TIMES:
			push(pop()*pop());
			break;
		case DIVIDE:
			y = pop();
			push(pop() / y);
			break;
		case POWER:
			y = pop();
			push(Math.pow(pop(), y));
			break;
		case C_PLUS:
			yi = pop();
			yr = pop();
			xi = pop();
			xr = pop();
			push(xr+yr, xi+yi);
			break;
		case C_MINUS:
			yi = pop();
			yr = pop();
			xi = pop();
			xr = pop();
			push(xr-yr, xi-yi);
			break;
		case C_TIMES:
			yi = pop();
			yr = pop();
			xi = pop();
			xr = pop();
			push(xr*yr - xi*yi, xr*yi + yr*xi);
			break;
		case C_DIVIDE: {
				yi = pop();
				yr = pop();
				xi = pop();
				xr = pop();
				double denom = yr*yr + yi*yi;
				push( (xr*yr+xi*yi)/denom, (xi*yr-xr*yi)/denom );
			}
			break;
		case C_POWER: {
				yi = pop();
				yr = pop();
				xi = pop();
				xr = pop();
				double modulus = Math.sqrt(xr*xr+xi*xi);
				double modulus_log = Math.log(modulus);
				double arg = Math.atan2(xi,xr);
				double modulus_ans = Math.exp(yr*modulus_log - yi*arg);
				double arg_ans = yi*modulus_log + yr*arg;
				push(modulus_ans*Math.cos(arg_ans), modulus_ans*Math.sin(arg_ans));
			}
			break;
		case C_REAL_POWER: {
				x = pop();
				xi = pop();
				xr = pop();
				double modulus = Math.sqrt(xi*xi + xr*xr);
				double arg = Math.atan2(xi,xr);
				double log_re = Math.log(modulus);
				double log_im = arg;
				double x_log_re = x * log_re;
				double x_log_im = x * log_im;
				double modulus_ans = Math.exp(x_log_re);
				push(modulus_ans*Math.cos(x_log_im), modulus_ans*Math.sin(x_log_im));
			}
			break;
		case EQ:
			push( (pop() == pop())? 1 : 0 );
			break;
		case NE:
			push( (pop() != pop())? 1 : 0 );
			break;
		case GE:
			push( (pop() <= pop())? 1 : 0 );
			break;
		case GT:
			push( (pop() < pop())? 1 : 0 );
			break;
		case LE:
			push( (pop() >= pop())? 1 : 0 );
			break;
		case LT:
			push( (pop() > pop())? 1 : 0 );
			break;
		case C_EQ:
			yi = pop();
			yr = pop();
			xi = pop();
			xr = pop();
			push ( (xr==yr && xi==yi)? 1 : 0 );
			break;
		case C_NE:
			yi = pop();
			yr = pop();
			xi = pop();
			xr = pop();
			push ( (xr==yr && xi==yi)? 0 : 1 );
			break;
		case AND:
			y = pop();
			x = pop();
			push( (x != 0 && y != 0)? 1 : 0);
			break;
		case OR:
			y = pop();
			x = pop();
			push( (x != 0 || y != 0)? 1 : 0);
			break;
		case NOT:
			push( (pop() != 0)? 0 : 1);
			break;
		case UNARY_MINUS:
			push(-pop());
			break;
		case C_UNARY_MINUS:
			xi = pop();
			xr = pop();
			push(-xr,-xi);
			break;
		case ABS:
			push(Math.abs(pop()));
			break;
		case SQRT:
			push(Math.sqrt(pop()));
			break;
		case CUBERT:
			push(Math.cbrt(pop()));
			break;
		case EXP:
			push(Math.exp(pop()));
			break;
		case LOG:
			push(Math.log(pop()));
			break;
		case LOG2:
			push(Math.log(pop())/Math.log(2));
			break;
		case LOG10:
			push(Math.log10(pop()));
			break;
		case C_ABS:
			xi = pop();
			xr = pop();
			push(Math.sqrt(xr*xr+xi*xi));
			break;
		case C_SQRT:
			push(0.5);
			apply(StackOp.C_REAL_POWER);
			break;
		case C_CUBERT:
			push(1.0/3.0);
			apply(StackOp.C_REAL_POWER);
			break;
		case C_EXP: {
				xi = pop();
				xr = pop();
				double e = Math.exp(xr);
				push( e*Math.cos(xi), e*Math.sin(xi) );
			}
			break;
		case C_LOG:
			xi = pop();
			xr = pop();
			push( 0.5*Math.log(xr*xr+xi*xi), Math.atan2(xi,xr) );
			break;
		case C_LOG2:
			apply(StackOp.C_LOG);
			push(Math.log(2),0);
			apply(StackOp.C_DIVIDE);
			break;
		case C_LOG10:
			apply(StackOp.C_LOG);
			push(Math.log(10),0);
			apply(StackOp.C_DIVIDE);
			break;
		case ARG:
			xi = pop();
			xr = pop();
			push(Math.atan2(xi,xr));
			break;
		case TRUNC:
			push( (int)pop() );
			break;
		case ROUND:
			push( (int)Math.round(pop()) );
			break;
		case CEILING:
			push( Math.ceil(pop()) );
			break;
		case FLOOR:
			push( Math.floor(pop()) );
			break;
		case SIGNUM:
			push( Math.signum(pop()) );
			break;
		case SIN:
			push( Math.sin(pop()) );
			break;
		case COS:
			push( Math.cos(pop()) );
			break;
		case TAN:
			push( Math.tan(pop()) );
			break;
		case SEC:
			push( 1.0 / Math.cos(pop()) );
			break;
		case COT:
			x = pop();
			push( Math.cos(x) / Math.sin(x) );
			break;
		case CSC:
			push( 1.0 / Math.sin(pop()) );
			break;
		case SINH:
			push( Math.sinh(pop()) );
			break;
		case COSH:
			push( Math.cosh(pop()) );
			break;
		case TANH:
			push( Math.tanh(pop()) );
			break;
		case SECH:
			push( 1.0 / Math.cosh(pop()) );
			break;
		case COTH:
			x = pop();
			push( Math.cosh(x) / Math.sinh(x) );
			break;
		case CSCH:
			push( 1.0 / Math.sinh(pop()) );
			break;
		case ARCSIN:
			push( Math.asin(pop()) );
			break;
		case ARCCOS:
			push( Math.acos(pop()) );
			break;
		case ARCTAN:
			push( Math.atan(pop()) );
			break;
		case ARCSINH:
			x = pop();
			push( Math.log(Math.sqrt(x*x+1) + x));
			break;
		case ARCCOSH:
			x = pop();
			push( x < 1 ? Double.NaN : Math.log(Math.sqrt(x*x - 1) + x));
			break;
		case ARCTANH:
			x = pop();
			push( Math.abs(x) >= 1 ? Double.NaN : Math.log( (1+x)/(1-x) )/2 );
			break;
		case C_SIN: {
				xi = pop();
				xr = pop();
				double e2 = Math.exp(xi);
				double e1 = 1.0 / e2;
				push( (e1+e2)*Math.sin(xr)/2, (e2-e1)*Math.cos(xr)/2 );
			}
			break;
		case C_COS: {
				xi = pop();
				xr = pop();
				double e2 = Math.exp(xi);
				double e1 = 1.0 / e2;
				push( (e1+e2)*Math.cos(xr)/2, (e1-e2)*Math.sin(xr)/2 );
			}
			break;
		case C_TAN: {
				xi = pop();
				xr = pop();
				double e2 = Math.exp(xi);
				double e1 = 1.0 / e2;
				double a = (e1+e2)*Math.sin(xr)/2;  // sin
				double b = (e2-e1)*Math.cos(xr)/2;
				double c = (e1+e2)*Math.cos(xr)/2;  // cos
				double d = (e1-e2)*Math.sin(xr)/2;
				double denom = c*c + d*d;
				push( (a*c+b*d)/denom, (b*c-a*d)/denom );
			}
			break;
		case C_SEC: {
				xi = pop();
				xr = pop();
				double e2 = Math.exp(xi);
				double e1 = 1.0 / e2;
				double a = (e1+e2)*Math.cos(xr)/2;  // cos
				double b = (e1-e2)*Math.sin(xr)/2;
				double denom = a*a + b*b;
				push( a/denom, -b/denom );
			}
			break;
		case C_COT: {
				xi = pop();
				xr = pop();
				double e2 = Math.exp(xi);
				double e1 = 1.0 / e2;
				double a = (e1+e2)*Math.cos(xr)/2;  // cos
				double b = (e1-e2)*Math.sin(xr)/2;
				double c = (e1+e2)*Math.sin(xr)/2;  // sin
				double d = (e2-e1)*Math.cos(xr)/2;
				double denom = c*c + d*d;
				push( (a*c+b*d)/denom, (b*c-a*d)/denom );
			}
			break;
		case C_CSC: {
				xi = pop();
				xr = pop();
				double e2 = Math.exp(xi);
				double e1 = 1.0 / e2;
				double a = (e1+e2)*Math.sin(xr)/2;  // sin
				double b = (e2-e1)*Math.cos(xr)/2;
				double denom = a*a + b*b;
				push( a/denom, -b/denom );
			}
			break;
		case C_SINH: {
				xi = pop();
				xr = pop();
				double e2 = Math.exp(xr);
				double e1 = 1.0 / e2;
				push( (e2-e1)*Math.cos(xi)/2, (e1+e2)*Math.sin(xi)/2 );
			}
			break;
		case C_COSH: {
				xi = pop();
				xr = pop();
				double e2 = Math.exp(xr);
				double e1 = 1.0 / e2;
				push( (e1+e2)*Math.cos(xi)/2, (e2-e1)*Math.sin(xi)/2 );
			}
			break;
		case C_TANH: {
				xi = pop();
				xr = pop();
				double e2 = Math.exp(xr);
				double e1 = 1.0 / e2;
				double a = (e2-e1)*Math.cos(xi)/2; // sinh
				double b = (e1+e2)*Math.sin(xi)/2;
				double c = (e1+e2)*Math.cos(xi)/2; // cosh
				double d = (e2-e1)*Math.sin(xi)/2;
				double denom = c*c + d*d;
				push( (a*c+b*d)/denom, (b*c-a*d)/denom );
			}
			break;
		case C_SECH: {
				xi = pop();
				xr = pop();
				double e2 = Math.exp(xr);
				double e1 = 1.0 / e2;
				double a = (e1+e2)*Math.cos(xi)/2; // cosh
				double b = (e2-e1)*Math.sin(xi)/2;
				double denom = a*a + b*b;
				push( a/denom, -b/denom );
			}			
			break;
		case C_COTH: {
				xi = pop();
				xr = pop();
				double e2 = Math.exp(xr);
				double e1 = 1.0 / e2;
				double a = (e1+e2)*Math.cos(xi)/2; // cosh
				double b = (e2-e1)*Math.sin(xi)/2;
				double c = (e2-e1)*Math.cos(xi)/2; // sinh
				double d = (e1+e2)*Math.sin(xi)/2;
				double denom = c*c + d*d;
				push( (a*c+b*d)/denom, (b*c-a*d)/denom );
			}			
			break;
		case C_CSCH: {
				xi = pop();
				xr = pop();
				double e2 = Math.exp(xr);
				double e1 = 1.0 / e2;
				double a = (e2-e1)*Math.cos(xi)/2; // sinh
				double b = (e1+e2)*Math.sin(xi)/2;
				double denom = a*a + b*b;
				push( a/denom, -b/denom );
			}			
			break;
		case C_ARCSIN:  // -i log[iz + (1-z^2)^0.5]
			xi = pop();
			xr = pop();
			push(-xi,xr);  // iz
			push(1,0);
			push(xr,xi);
			push(2);
			apply(StackOp.C_REAL_POWER);
			apply(StackOp.C_MINUS);
			push(0.5);
			apply(StackOp.C_REAL_POWER);
			apply(StackOp.C_PLUS);
			apply(StackOp.C_LOG);
			xi = pop();
			xr = pop();
			push(xi,-xr);  // *(-i)
			break; 
		case C_ARCCOS:  // -i log[z+i(1-z^2)^0.5]
			xi = pop();
			xr = pop();
			push(xr,xi);  // z
			push(1,0);
			push(xr,xi);
			push(2);
			apply(StackOp.C_REAL_POWER);
			apply(StackOp.C_MINUS);
			push(0.5);
			apply(StackOp.C_REAL_POWER);
			xi = pop();
			xr = pop();
			push(-xi,xr); // *i
			apply(StackOp.C_PLUS);
			apply(StackOp.C_LOG);
			xi = pop();
			xr = pop();
			push(xi,-xr);  // *(-i)
			break; 
		case  C_ARCTAN:  // i/2 * log[ (i+z)/(i-z) ]
			xi = pop();
			xr = pop();
			push(xr,xi+1);  // i+z
			push(-xr,1-xi); // i-z
			apply(StackOp.C_DIVIDE);
			apply(StackOp.C_LOG);
			xi = pop();
			xr = pop();
			push(-xi/2,xr/2);
			break; 
		case C_ARCSINH:  // log(z+(z^2+1)^0.5)
			xi = pop();
			xr = pop();
			push(xr,xi);
			push(xr,xi);
			push(2);
			apply(StackOp.C_REAL_POWER);
			push(1,0);
			apply(StackOp.C_PLUS);
			push(0.5);
			apply(StackOp.C_REAL_POWER);
			apply(StackOp.C_PLUS);
			apply(StackOp.C_LOG);
			break; 
		case C_ARCCOSH: // log(a+(x^2-1)^0.5)
			xi = pop();
			xr = pop();
			push(xr,xi);
			push(xr,xi);
			push(2);
			apply(StackOp.C_REAL_POWER);
			push(1,0);
			apply(StackOp.C_MINUS);
			push(0.5);
			apply(StackOp.C_REAL_POWER);
			apply(StackOp.C_PLUS);
			apply(StackOp.C_LOG);
			break; 
		case C_ARCTANH:  // 1/2 log[ (1+z)/(1-z) ]
			xi = pop();
			xr = pop();
			push(xr+1,xi);
			push(1-xr,-xi);
			apply(StackOp.C_DIVIDE);
			apply(StackOp.C_LOG);
			xi = pop();
			xr = pop();
			push(xr/2,xi/2);
			break; 
		case CONJ:
			xi = pop();
			push(-xi);
			break;
		}
	}
		
}
