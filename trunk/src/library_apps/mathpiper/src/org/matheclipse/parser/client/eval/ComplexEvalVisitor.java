package org.matheclipse.parser.client.eval;

import java.util.HashMap;
import java.util.Map;

import org.matheclipse.parser.client.ast.ASTNode;
import org.matheclipse.parser.client.ast.FloatNode;
import org.matheclipse.parser.client.ast.FractionNode;
import org.matheclipse.parser.client.ast.FunctionNode;
import org.matheclipse.parser.client.ast.IntegerNode;
import org.matheclipse.parser.client.ast.NumberNode;
import org.matheclipse.parser.client.ast.PatternNode;
import org.matheclipse.parser.client.ast.StringNode;
import org.matheclipse.parser.client.ast.SymbolNode;
import org.matheclipse.parser.client.eval.api.AbstractASTVisitor;
import org.matheclipse.parser.client.eval.api.IEvaluator;
import org.matheclipse.parser.client.math.Complex;
import org.matheclipse.parser.client.math.MathException;

/**
 * Abstract AST visitor with empty default method implementation.
 * 
 * @param <T>
 */
public class ComplexEvalVisitor extends AbstractASTVisitor<Complex, ComplexVariable, Complex> {

	public final static boolean DEBUG = false;

//	private static double EPSILON = 1.0e-15;

	private static Map<String, Complex> SYMBOL_MAP;

	private static Map<String, Boolean> SYMBOL_BOOLEAN_MAP;

	private static Map<String, Object> FUNCTION_MAP;

	private static Map<String, Object> FUNCTION_BOOLEAN_MAP;

	static class ArcTanFunction implements IComplex1Function {
		public Complex evaluate(Complex arg1) {
			return arg1.atan();// ComplexUtils.atan(arg1);
		}

	}

	static class LogFunction implements IComplex1Function, IComplex2Function {
		public Complex evaluate(Complex arg1) {
			return arg1.log();// ComplexUtils.log(arg1);
		}

		public Complex evaluate(Complex base, Complex z) {
			return z.log().divide(base.log());// ComplexUtils.log(z).divide(ComplexUtils.log(base));
		}
	}

	static class CompoundExpressionFunction implements IComplexFunction {
		public Complex evaluate(IEvaluator<Complex, ComplexVariable> engine, FunctionNode function) {
			Complex result = null;
			int end = function.size();
			for (int i = 1; i < end; i++) {
				result = engine.evaluateNode((ASTNode) function.getNode(i));
				if (DEBUG) {
					System.out.println(result);
				}
			}
			return result;
		}
	}

	static class SetFunction implements IComplexFunction {
		public Complex evaluate(IEvaluator<Complex, ComplexVariable>  engine, FunctionNode function) {
			if (function.size() != 3) {
				throw new ArithmeticException("SetFunction#evaluate(DoubleEvaluator,FunctionNode) needs 2 arguments: "
						+ function.toString());
			}
			if (!(function.getNode(1) instanceof SymbolNode)) {
				throw new ArithmeticException("SetFunction#evaluate(DoubleEvaluator,FunctionNode) symbol required on the left hand side: "
						+ function.toString());
			}
			String variableName = ((SymbolNode) function.getNode(1)).getString();
			Complex result = engine.evaluateNode((ASTNode) function.getNode(2));
			ComplexVariable dv = engine.getVariable(variableName);
			if (dv == null) {
				dv = new ComplexVariable(result);
			} else {
				dv.setValue(result);
			}
			engine.defineVariable(variableName, dv);
			return result;
		}
	}

	static class PlusFunction implements IComplexFunction, IComplex2Function {
		public Complex evaluate(Complex arg1, Complex arg2) {
			return arg1.add(arg2);
		}

		public Complex evaluate(IEvaluator<Complex, ComplexVariable>  engine, FunctionNode function) {
			Complex result = new Complex(0.0, 0.0);
			for (int i = 1; i < function.size(); i++) {
				result = result.add(engine.evaluateNode((ASTNode) function.getNode(i)));
			}
			return result;
		}
	}

	static class TimesFunction implements IComplexFunction, IComplex2Function {
		public Complex evaluate(Complex arg1, Complex arg2) {
			return arg1.multiply(arg2);
		}

		public Complex evaluate(IEvaluator<Complex, ComplexVariable>  engine, FunctionNode function) {
			Complex result = new Complex(1.0, 0.0);
			for (int i = 1; i < function.size(); i++) {
				result = result.multiply(engine.evaluateNode((ASTNode) function.getNode(i)));
			}
			return result;
		}
	}

	static {
		SYMBOL_MAP = new HashMap<String, Complex>();
		SYMBOL_MAP.put("Degree", new Complex(Math.PI / 180, 0.0));
		SYMBOL_MAP.put("E", new Complex(Math.E, 0.0));
		SYMBOL_MAP.put("I", new Complex(0.0, 1.0));
		SYMBOL_MAP.put("Pi", new Complex(Math.PI, 0.0));

		SYMBOL_BOOLEAN_MAP = new HashMap<String, Boolean>();
		SYMBOL_BOOLEAN_MAP.put("False", Boolean.FALSE);
		SYMBOL_BOOLEAN_MAP.put("True", Boolean.TRUE);

		FUNCTION_BOOLEAN_MAP = new HashMap<String, Object>();

		FUNCTION_BOOLEAN_MAP.put("And", new IBooleanBoolean2Function() {
			public boolean evaluate(boolean arg1, boolean arg2) {
				return arg1 && arg2;
			}
		});
		FUNCTION_BOOLEAN_MAP.put("Not", new IBooleanBoolean1Function() {
			public boolean evaluate(boolean arg1) {
				return !arg1;
			}
		});
		FUNCTION_BOOLEAN_MAP.put("Or", new IBooleanBoolean2Function() {
			public boolean evaluate(boolean arg1, boolean arg2) {
				return arg1 || arg2;
			}
		});

		FUNCTION_BOOLEAN_MAP.put("Equal", new IBooleanComplex2Function() {
			public boolean evaluate(Complex arg1, Complex arg2) {
				return arg1.equals(arg2);
			}
		});

		FUNCTION_BOOLEAN_MAP.put("Unequal", new IBooleanComplex2Function() {
			public boolean evaluate(Complex arg1, Complex arg2) {
				return !arg1.equals(arg2);
			}
		});

		FUNCTION_MAP = new HashMap<String, Object>();
		FUNCTION_MAP.put("ArcTan", new ArcTanFunction());
		FUNCTION_MAP.put("Log", new LogFunction());
		FUNCTION_MAP.put("CompoundExpression", new CompoundExpressionFunction());
		FUNCTION_MAP.put("Set", new SetFunction());
		FUNCTION_MAP.put("Plus", new PlusFunction());
		FUNCTION_MAP.put("Times", new TimesFunction());
		//
		// Functions with 0 argument
		//
		FUNCTION_MAP.put("Random", new IComplex0Function() {
			public Complex evaluate() {
				return new Complex(Math.random(), Math.random());
			}
		});
		//
		// Functions with 1 argument
		//
		FUNCTION_MAP.put("ArcCos", new IComplex1Function() {
			public Complex evaluate(Complex arg1) {
				return arg1.acos();// acos(arg1);
			}
		});
		FUNCTION_MAP.put("ArcSin", new IComplex1Function() {
			public Complex evaluate(Complex arg1) {
				return arg1.asin();// asin(arg1);
			}
		});

		FUNCTION_MAP.put("Cos", new IComplex1Function() {
			public Complex evaluate(Complex arg1) {
				return arg1.cos();// cos(arg1);
			}
		});
		FUNCTION_MAP.put("Cosh", new IComplex1Function() {
			public Complex evaluate(Complex arg1) {
				return arg1.cosh();// cosh(arg1);
			}
		});
		FUNCTION_MAP.put("Exp", new IComplex1Function() {
			public Complex evaluate(Complex arg1) {
				return arg1.exp();// exp(arg1);
			}
		});
		FUNCTION_MAP.put("Sin", new IComplex1Function() {
			public Complex evaluate(Complex arg1) {
				return arg1.sin();// sin(arg1);
			}
		});
		FUNCTION_MAP.put("Sinh", new IComplex1Function() {
			public Complex evaluate(Complex arg1) {
				return arg1.sinh();// sinh(arg1);
			}
		});
		FUNCTION_MAP.put("Sqrt", new IComplex1Function() {
			public Complex evaluate(Complex arg1) {
				return arg1.sqrt();// sqrt(arg1);
			}
		});
		FUNCTION_MAP.put("Tan", new IComplex1Function() {
			public Complex evaluate(Complex arg1) {
				return arg1.tan();// tan(arg1);
			}
		});
		FUNCTION_MAP.put("Tanh", new IComplex1Function() {
			public Complex evaluate(Complex arg1) {
				return arg1.tanh();// tanh(arg1);
			}
		});

		//
		// Functions with 2 arguments
		//
		FUNCTION_MAP.put("Power", new IComplex2Function() {
			public Complex evaluate(Complex arg1, Complex arg2) {
				return arg1.pow(arg2);// pow(arg1, arg2);
			}
		});
	}

	private Map<String, ComplexVariable> fVariableMap;

	private Map<String, BooleanVariable> fBooleanVariables;
	
	public ComplexEvalVisitor() {
		fVariableMap = new HashMap<String, ComplexVariable>();
		fBooleanVariables = new HashMap<String, BooleanVariable>();
	}
	
	public Complex getResult() {
		return null;
	}

	public void setUp(Complex data) {
	}

	public void tearDown() {
	}

	public Complex visit(ComplexNode node) {
		return ((ComplexNode) node).complexValue();
	}

	public Complex visit(DoubleNode node) {
		return new Complex(node.doubleValue(), 0.0);
	}

	public Complex visit(FloatNode node) {
		return new Complex(node.doubleValue(), 0.0);
	}

	public Complex visit(FractionNode node) {
		return new Complex(node.doubleValue(), 0.0);
	}

	public Complex visit(FunctionNode functionNode) {
		if (functionNode.size() > 0 && functionNode.getNode(0) instanceof SymbolNode) {
			String symbol = functionNode.getNode(0).toString();
			if (symbol.equals("If")) {
				if (functionNode.size() == 3) {
					if (evaluateNodeLogical(functionNode.getNode(1))) {
						return evaluateNode(functionNode.getNode(2));
					}
				} else if (functionNode.size() == 4) {
					if (evaluateNodeLogical(functionNode.getNode(1))) {
						return evaluateNode(functionNode.getNode(2));
					} else {
						return evaluateNode(functionNode.getNode(3));
					}
				}
			} else {
				Object obj = FUNCTION_MAP.get(symbol);
				if (obj instanceof IComplexFunction) {
					return ((IComplexFunction) obj).evaluate(this, functionNode);
				}
				if (functionNode.size() == 1) {
					if (obj instanceof IComplex0Function) {
						return ((IComplex0Function) obj).evaluate();
					}
				} else if (functionNode.size() == 2) {
					if (obj instanceof IComplex1Function) {
						return ((IComplex1Function) obj).evaluate(evaluateNode((ASTNode) functionNode.getNode(1)));
					}
				} else if (functionNode.size() == 3) {
					if (obj instanceof IComplex2Function) {
						return ((IComplex2Function) obj).evaluate(evaluateNode((ASTNode) functionNode.getNode(1)),
								evaluateNode((ASTNode) functionNode.getNode(2)));
					}
				} 
			}
		}
		throw new MathException("EvalDouble#evaluateFunction(FunctionNode) not possible for: " + functionNode.toString());

	}

	public Complex visit(IntegerNode node) {
		return new Complex(node.doubleValue(), 0.0);
	}

	public Complex visit(PatternNode node) {
		return null;
	}

	public Complex visit(StringNode node) {
		return null;
	}

	public Complex visit(SymbolNode node) {
		ComplexVariable v = (ComplexVariable) fVariableMap.get(node.toString());
		if (v != null) {
			return v.getValue();
		}
		Complex c = (Complex) SYMBOL_MAP.get(node.toString());
		if (c != null) {
			return c;
		}
		throw new MathException("ComplexEvalVisitor#visit(SymbolNode) not possible for: " + node.toString());
	}

	public boolean evaluateNodeLogical(final ASTNode node) {
		if (node instanceof FunctionNode) {
			return evaluateFunctionLogical((FunctionNode) node);
		}
		if (node instanceof SymbolNode) {
			BooleanVariable v = fBooleanVariables.get(node.toString());
			if (v != null) {
				return v.getValue();
			}
			Boolean boole = (Boolean) SYMBOL_BOOLEAN_MAP.get(node.toString());
			if (boole != null) {
				return boole.booleanValue();
			}
		}

		throw new ArithmeticException("EvalDouble#evaluateNodeLogical(ASTNode) not possible for: " + node.toString());
	}

	public boolean evaluateFunctionLogical(final FunctionNode functionNode) {
		if (functionNode.size() > 0 && functionNode.getNode(0) instanceof SymbolNode) {
			String symbol = functionNode.getNode(0).toString();
			if (functionNode.size() == 2) {
				Object obj = FUNCTION_BOOLEAN_MAP.get(symbol);
				if (obj instanceof IBooleanBoolean1Function) {
					return ((IBooleanBoolean1Function) obj).evaluate(evaluateNodeLogical((ASTNode) functionNode.getNode(1)));
				}
			} else if (functionNode.size() == 3) {
				Object obj = FUNCTION_BOOLEAN_MAP.get(symbol);
				if (obj instanceof IBooleanComplex2Function) {
					return ((IBooleanComplex2Function) obj).evaluate(evaluateNode((ASTNode) functionNode.getNode(1)),
							evaluateNode((ASTNode) functionNode.getNode(2)));
				} else if (obj instanceof IBooleanBoolean2Function) {
					return ((IBooleanBoolean2Function) obj).evaluate(evaluateNodeLogical((ASTNode) functionNode.getNode(1)),
							evaluateNodeLogical((ASTNode) functionNode.getNode(2)));
				}
				// } else {
				// Object obj = FUNCTION_BOOLEAN_MAP.get(symbol);
				// if (obj instanceof IBooleanDoubleFunction) {
				// return ((IBooleanDoubleFunction) obj).evaluate(this, functionNode);
				// }
			}
		}
		throw new ArithmeticException("EvalDouble#evaluateFunctionLogical(FunctionNode) not possible for: " + functionNode.toString());

	}
	
	/**
	 * Returns a <code>String</code> representation of the given
	 * <code>Complex</code> number.
	 * 
	 * @param c
	 * @return
	 * 
	 */
//	public static String toString(Complex c) {
//		double real = c.getReal();
//		double imag = c.getImaginary();
//		if (imag == 0.0) {
//			return Double.valueOf(real).toString();
//		} else {
//			if (imag >= 0.0) {
//				return Double.valueOf(real).toString() + "+I*" + Double.valueOf(imag).toString();
//			} else {
//				return Double.valueOf(real).toString() + "+I*(" + Double.valueOf(imag).toString() + ")";
//			}
//		}
//	}

	/**
	 * Define a value for a given variable name.
	 * 
	 * @param variableName
	 * @param value
	 */
	public void defineVariable(String variableName, ComplexVariable value) {
		fVariableMap.put(variableName, value);
	}

	/**
	 * Returns the Complex variable value to which the specified variableName is
	 * mapped, or {@code null} if this map contains no mapping for the
	 * variableName.
	 * 
	 * @param variableName
	 * @return
	 */
	public ComplexVariable getVariable(String variableName) {
		return fVariableMap.get(variableName);
	}

	/**
	 * Define a boolean value for a given variable name.
	 * 
	 * @param variableName
	 * @param value
	 */
	public void defineVariable(String variableName, BooleanVariable value) {
		fBooleanVariables.put(variableName, value);
	}

	/**
	 * Clear all defined variables for this evaluator.
	 */
	public void clearVariables() {
		fVariableMap.clear();
	}
	
	/**
	 * Optimize an already parsed in <code>functionNode</code> into an
	 * <code>ASTNode</code>.
	 * 
	 * @param functionNode
	 * @return
	 * 
	 */
	public ASTNode optimizeFunction(final FunctionNode functionNode) {
		if (functionNode.size() > 0) {
			boolean complexOnly = true;
			ASTNode node;
			for (int i = 1; i < functionNode.size(); i++) {
				node = functionNode.getNode(i);
				if (node instanceof NumberNode) {
					functionNode.set(i, new ComplexNode(((NumberNode) functionNode.getNode(i)).doubleValue()));
				} else if (functionNode.getNode(i) instanceof FunctionNode) {
					ASTNode optNode = optimizeFunction((FunctionNode) functionNode.getNode(i));
					if (!(optNode instanceof ComplexNode)) {
						complexOnly = false;
					}
					functionNode.set(i, optNode);
				} else if (node instanceof SymbolNode) {
					Complex c = (Complex) SYMBOL_MAP.get(node.toString());
					if (c != null) {
						functionNode.set(i, new ComplexNode(c));
					} else {
						complexOnly = false;
					}
				} else {
					complexOnly = false;
				}
			}
			if (complexOnly) {
				try {
					return new ComplexNode(visit(functionNode));
				} catch (Exception e) {

				}
			}
		}
		return functionNode;
	}
}
