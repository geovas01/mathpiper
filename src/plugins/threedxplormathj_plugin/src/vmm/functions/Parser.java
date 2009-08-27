/*  This file is part of the source code for 3D-XplorMath-J, Version 1.0 (January 2008).
 *  Copyright (c) 2008 The 3D-XplorMath Consortium (http://3d-xplormath.org).
 *  This source code is released under a BSD License, which allows redistribution   
 *  in source and binary form, with or without modification, provided copyright
 *  and license information are included, and with no warranty or guarantee of
 *  any kind.  For details, see http://3d-xplormath.org/j/source/BSDLicense.txt
 */
 
 package vmm.functions;

import java.text.MessageFormat;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import vmm.core.I18n;

/**
 * A Parser can parse a string that holds the definition of a 
 * real-valued or complex-valued function or expression. 
 * <p>An expression can use the operators +, -, *, /, and ^, where ^
 * represents expontiation.  Multiplication can also be represented
 * by juxtaposition.  The ternary conditional operator "?:"
 * can be used, as in "<code>x &lt; 0 ? -x : x</code>".  In the
 * condition, the relational operators =, !=, &lt;, &gt;, &lt;=, and &gt;=
 * and the logical operators and, or, and not can be used.  Not equal can
 * be expressed as &lt;&gt; as well as by !=, and the logical operators
 * can  be written as &amp; |, and ~. Parentheses (), braces {}, and brackets []
 * can be used for grouping.
 * <p>Names in expressions are not case-sensitive.  The constants pi, e, i can be 
 * used in expressions (where i is the complex number, square root of minus one.
 * Standard functions that are defined for both real and complex arguments are:
 * abs, sqrt, cubert, exp, log, log2, log10, sin, cos, tan, sec, csc, cot,
 * sinh, cosh, tanh, sech, scsh, coth, arcsin, arccos, arctan, arcsinh,
 * arccosh, arctanh.  Log can also be written as ln, and the inverse trig
 * functions can also be written as asin, acos, atan, asinh, acosh, and atahn.
 * Functions defined only for real arguments are: trunc, round, ceiling, floor,
 * and signum.  The signum function can also be written as sign or sgn.
 * The real- and imaginary-part functions, re and im, are defined for a complex 
 * argument and produce a real result; they will also accept real arguemnts.
 * The function complex(x,y) takes two real arguments and produces the complex
 * value x+i*y; complex(x,y) is just another way to write this complex value,
 * without using the constant i directly.
 * <p> The definition of an expression or function
 * can include references to parameters and functions that have
 * been added to the parser (see {@link #add(Variable)},
 * {@link #add(ComplexVariable)}, {@link #add(Function)},
 * and {@link #add(ComplexFunction)}.
 */
public class Parser {
	
	/**
	 * Create a parser with no parent. (See {@link #Parser(Parser)}.)
	 */
	public Parser() {
		this(null);
	}
	
	/**
	 * Create a new parser.  (This is mainly for internal use in this class.)
	 * @param parent if the parent is non-null, then this parser inherits 
	 * any symbols that have been added to the parent parser.  Symbols that
	 * are added to this parser do not affect the parent, but will hide symbols
	 * in the parent that have the same name making them unavailable for use
	 * by this parser.
	 */
	public Parser(Parser parent) {
		if (parent != null)
			symbolTable = new SymbolTable(parent.symbolTable);
		else {
			symbolTable = new SymbolTable();
			add(PI);
			add(E);
			add(I);
			StandardFunction[] functions = StandardFunction.getFunctions();
			for (StandardFunction f : functions)
				symbolTable.put(f.getName().toLowerCase(),f);
		}
	}
	
	/**
	 * Parse an expression.  This is just a synonym for {@link #parseExpression(String)}.
	 * @throws ParseError if a syntax error is found in the definition of the expression.
	 */
	public Expression parse(String str) {
		return parseExpression(str);
	}
	
	/**
	 * Parse a real-valued expression.
	 * @throws ParseError if a syntax error is found in the definition of the expression.
	 */
	public Expression parseExpression(String str) {
		Context context = new Context(str,symbolTable,false);
		context.bldr.start(0,Type.REAL);
		Type type = doParse(context);
		if (type == Type.COMPLEX)
			error(context,"vmm.parser.ExpectedRealFoundComplex");
		if (type == Type.BOOLEAN)
			error(context,"vmm.parser.ExpectedRealFoundBoolean");
		return new Expression(context.bldr.finish(Type.REAL));
	}
	
	/**
	 * Define a real-valued function of one real argument by parsing its definition.
	 * @param name the name of the function.  This can be null and really only has to be
	 * non-null if the function will be added to a Parser for use in other expressions.
	 * @param definition The string that will be parsed to define the function.
	 * @param argumentName The name of the argument to the function.  This must be non-null.
	 * It should be a legal identifier.  The argument name can be used in the definition
	 * of the function.
	 * @throws ParseError if a syntax error is found in the definition of the function.
	 */
	public Function1 parseFunction1(String name, String definition, String argumentName) {
		return (Function1)parseFunction(name,definition,argumentName);
	}
	
	/**
	 * Define a real-valued function of two real arguments by parsing its definition.
	 * @param name the name of the function.  This can be null and really only has to be
	 * non-null if the function will be added to a Parser for use in other expressions.
	 * @param definition The string that will be parsed to define the function.
	 * @param argumentName1 The name of the first argument to the function.  This must be non-null.
	 * It should be a legal identifier.
	 * @param argumentName2 The name of the second argument to the function.  This must be non-null.
	 * It should be a legal identifier.
	 * @throws ParseError if a syntax error is found in the definition of the function.
	 */
	public Function2 parseFunction2(String name, String definition, String argumentName1, String argumentName2) {
		return (Function2)parseFunction(name,definition,argumentName1,argumentName2);
	}
	
	/**
	 * Define a real-valued function of three real arguments by parsing its definition.
	 * @param name the name of the function.  This can be null and really only has to be
	 * non-null if the function will be added to a Parser for use in other expressions.
	 * @param definition The string that will be parsed to define the function.
	 * @param argumentName1 The name of the first argument to the function.  This must be non-null.
	 * It should be a legal identifier.
	 * @param argumentName2 The name of the second argument to the function.  This must be non-null.
	 * It should be a legal identifier.
	 * @param argumentName3 The name of the third argument to the function.  This must be non-null.
	 * It should be a legal identifier.
	 * @throws ParseError if a syntax error is found in the definition of the function.
	 */
	public Function3 parseFunction3(String name, String definition, String argumentName1, String argumentName2, String argumentName3) {
		return (Function3)parseFunction(name,definition,argumentName1, argumentName2, argumentName3);
	}
	
	/**
	 * Define a real-valued function of any number of real arguments by parsing its definition.
	 * @param name the name of the function.  This can be null and really only has to be
	 * non-null if the function will be added to a Parser for use in other expressions.
	 * @param definition The string that will be parsed to define the function.
	 * @param argumentName The names of the arguments to the function.  There can be any number
	 * of argumentNames.  They must be non-null and should be legal identifiers.
	 * @return the function.  If the number of arguments is one, two, or three, then the
	 * return value is actually of type {@link Function1}, {@link Function2}, or
	 * {@link Function3}, respectively.
	 * @throws ParseError if a syntax error is found in the definition of the function.
	 */
	public Function parseFunction(String name, String definition, String... argumentName) {
		Context context;
		int argCount;
		if (argumentName != null && argumentName.length > 0) {
			argCount = argumentName.length;
			SymbolTable tbl = new SymbolTable(symbolTable);
			for (int i = 0; i < argumentName.length; i++)
				tbl.put(argumentName[i].toLowerCase(), new Argument(i));
			context = new Context(definition,tbl,false);
		}
		else {
			argCount = 0;
			context = new Context(definition,symbolTable,false);
		}
		context.bldr.start(argCount,Type.REAL);
		Type type = doParse(context);
		if (type == Type.COMPLEX)
			error(context,"vmm.parser.ExpectedRealFoundComplex");
		if (type == Type.BOOLEAN)
			error(context,"vmm.parser.ExpectedRealFoundBoolean");
		ProgFunction func = context.bldr.finish(Type.REAL);
		if (argCount == 1)
			return new Function1(name,func);
		else if (argCount == 2)
			return new Function2(name,func);
		else if (argCount == 3)
			return new Function3(name,func);
		else
			return new Function(name,func);
	}
	
	/**
	 * Parse a complex-valued expression.
	 * @throws ParseError if a syntax error is found in the definition of the expression.
	 */
	public ComplexExpression parseComplexExpression(String str) {
		Context context = new Context(str,symbolTable,true);
		context.bldr.start(0,Type.COMPLEX);
		Type type = doParse(context);
		if (type == Type.BOOLEAN)
			error(context,"vmm.parser.ExpectedCompplexFoundBoolean");
		if (type == Type.REAL)
			context.bldr.addStackOp(StackOp.REAL_TO_COMPLEX);
		return new ComplexExpression(context.bldr.finish(Type.COMPLEX));
	}
	
	/**
	 * Define a complex-valued function of one real argumentsby parsing its definition.
	 * @param name the name of the function.  This can be null and really only has to be
	 * non-null if the function will be added to a Parser for use in other expressions.
	 * @param definition The string that will be parsed to define the function.
	 * @param argumentName The name of the argument to the function.  This must be non-null.
	 * It should be a legal identifier.
	 * @throws ParseError if a syntax error is found in the definition of the function.
	 */
	public ComplexFunction1 parseComplexFunction1(String name, String definition, String argumentName) {
		return (ComplexFunction1)parseComplexFunction(name,definition,argumentName);
	}
	
	/**
	 * Define a conplex-valued function of two complex arguments by parsing its definition.
	 * @param name the name of the function.  This can be null and really only has to be
	 * non-null if the function will be added to a Parser for use in other expressions.
	 * @param definition The string that will be parsed to define the function.
	 * @param argumentName1 The name of the first argument to the function.  This must be non-null.
	 * It should be a legal identifier.
	 * @param argumentName2 The name of the second argument to the function.  This must be non-null.
	 * It should be a legal identifier.
	 * @throws ParseError if a syntax error is found in the definition of the function.
	 */
	public ComplexFunction2 parseComplexFunction2(String name, String definition, String argumentName1, String argumentName2) {
		return (ComplexFunction2)parseComplexFunction(name,definition,argumentName1,argumentName2);
	}
	
	/**
	 * Define a conplex-valued function of three complex arguments by parsing its definition.
	 * @param name the name of the function.  This can be null and really only has to be
	 * non-null if the function will be added to a Parser for use in other expressions.
	 * @param definition The string that will be parsed to define the function.
	 * @param argumentName1 The name of the first argument to the function.  This must be non-null.
	 * It should be a legal identifier.
	 * @param argumentName2 The name of the second argument to the function.  This must be non-null.
	 * It should be a legal identifier.
	 * @param argumentName3 The name of the third argument to the function.  This must be non-null.
	 * It should be a legal identifier.
	 * @throws ParseError if a syntax error is found in the definition of the function.
	 */
	public ComplexFunction3 parseComplexFunction3(String name, String definition, String argumentName1, String argumentName2, String argumentName3) {
		return (ComplexFunction3)parseComplexFunction(name,definition,argumentName1, argumentName2, argumentName3);
	}
	
	/**
	 * Define a complex-valued function of any number of complex arguments by parsing its definition.
	 * @param name the name of the function.  This can be null and really only has to be
	 * non-null if the function will be added to a Parser for use in other expressions.
	 * @param definition The string that will be parsed to define the function.
	 * @param argumentName The names of the arguments to the function.  There can be any number
	 * of argumentNames.  They must be non-null and should be legal identifiers.
	 * @return the function.  If the number of arguments is one, two, or three, then the
	 * return value is actually of type {@link ComplexFunction1}, {@link ComplexFunction2}, or
	 * {@link ComplexFunction3}, respectively.
	 * @throws ParseError if a syntax error is found in the definition of the function.
	 */
	public ComplexFunction parseComplexFunction(String name, String definition, String... argumentName) {
		Context context;
		int argCount;
		if (argumentName != null && argumentName.length > 0) {
			argCount = argumentName.length;
			SymbolTable tbl = new SymbolTable(symbolTable);
			for (int i = 0; i < argumentName.length; i++)
				tbl.put(argumentName[i].toLowerCase(), new ComplexArgument(i));
			context = new Context(definition,tbl,false);
		}
		else {
			argCount = 0;
			context = new Context(definition,symbolTable,true);
		}
		context.bldr.start(argCount,Type.COMPLEX);
		Type type = doParse(context);
		if (type == Type.BOOLEAN)
			error(context,"vmm.parser.ExpectedCompplexFoundBoolean");
		if (type == Type.REAL)
			context.bldr.addStackOp(StackOp.REAL_TO_COMPLEX);
		ProgFunction func = context.bldr.finish(Type.COMPLEX);
		if (argCount == 1)
			return new ComplexFunction1(name,func);
		else if (argCount == 2)
			return new ComplexFunction2(name,func);
		else if (argCount == 3)
			return new ComplexFunction3(name,func);
		else
			return new ComplexFunction(name,func);
	}
	
	/**
	 * Add a variable that can then be used as a parameter in expressions
	 * parsed by this parser.
	 * @param v the variable to be added to the parser.  This must be non-null
	 * and must have a non-null name.  Furthermore, the name should be
	 * a legal identifier that can be used in expressions; that is, it should
	 * start with a letter and contain only letters, digits, the underscore
	 * character, with one or more apostrophes allowed at the end of the name.
	 */
	public void add(Variable v) {
		symbolTable.put(v.getName().toLowerCase(), v);
	}
		
	/**
	 * Add a complex variable that can then be used as a parameter in expressions
	 * parsed by this parser.
	 * @param v the variable to be added to the parser.  This must be non-null
	 * and must have a non-null name.  Furthermore, the name should be
	 * a legal identifier that can be used in expressions; that is, it should
	 * start with a letter and contain only letters, digits,  the underscore
	 * character, with one or more apostrophes allowed at the end of the name.
	 */
	public void add(ComplexVariable v) {
		symbolTable.put(v.getName().toLowerCase(), v);
	}
		
	/**
	 * Add a real-valued function to the parser so that it can be used in
	 * expressions parsed by this parser.
	 * @param f the function to be added to the parser. (Note that the only way to get
	 * such a function is from a Parser.)  This must be non-null and must
	 * have a non-null name.  The name should be a legal identifier; that is, it should
	 * start with a letter and contain only letters, digits,  the underscore
	 * character, with one or more apostrophes allowed at the end of the name.
	 */
	public void add(Function f) {
		symbolTable.put(f.getName().toLowerCase(), f);
	}
		
	/**
	 * Add a complex-valued function to the parser so that it can be used in
	 * expressions parsed by this parser.
	 * @param f the function to be added to the parser. (Note that the only way to get
	 * such a function is from a Parser.)  This must be non-null and must
	 * have a non-null name.  The name should be a legal identifier; that is, it should
	 * start with a letter and contain only letters, digits,  the underscore
	 * character, with one or more apostrophes allowed at the end of the name.
	 */
	public void add(ComplexFunction f) {
		symbolTable.put(f.getName().toLowerCase(), f);
	}
	
	/**
	 * Gets the object from the parser's symbol table that has the specified name,
	 * or returns null if there is no such object.  The parent of the parser, if
	 * there is one, is also searched.  The name is not case sensitive.
	 * (This is mostly for internal use, and not likely to be used by ordinary
	 * programmers.)
	 */
	public Object get(String name) {
		return symbolTable.get(name.toLowerCase());
	}
	
	/**
	 * Removes the object from the parser's symbol table that has the specified name,
	 * if there is such an object.  (The object is only removed if it is found in
	 * this parser, not in the parent.)  The name is not case sensitive.
	 * (This is mostly for internal use, and not likely to be used by ordinary
	 * programmers.)
	 */
	public void remove(String objectName) {
		symbolTable.remove(objectName.toLowerCase());
	}
	
	//-------------------------------------------------------------------------------------------
	
	private static Variable PI = new Variable("pi",Math.PI);
	private static Variable E = new Variable("e",Math.E);
	private static ComplexVariable I = new ComplexVariable("i",0,1);
	private static EnumSet<Token> relationalOps = EnumSet.of(Token.EQUAL, Token.NOT_EQUAL, 
			Token.GREATER, Token.GREATER_EQUAL, Token.LESS, Token.LESS_EQUAL);
	private static EnumSet<Token> canStartFactor = EnumSet.of(Token.NUMBER, Token.VARIABLE, Token.COMPLEX_VARIABLE,
			Token.ARGUMENT, Token.COMPLEX_ARGUMENT, Token.FUNCTION, Token.COMPLEX_FUNCTION, Token.STANDARD_FUNCTION,
			Token.FUNCTION_COMPLEX_TO_REAL, Token.LEFT_BRACE, Token.LEFT_PAREN, Token.LEFT_BRACKET,
			Token.TIMES, Token.DIVIDE);
	private static EnumSet<StackOp> needRealToComplex = EnumSet.of(StackOp.SQRT, StackOp.CUBERT, 
			StackOp.LOG, StackOp.LOG2, StackOp.LOG10, StackOp.ARCSIN, StackOp.ARCCOS, StackOp.ARCTAN,
			StackOp.ARCSINH, StackOp.ARCCOSH, StackOp.ARCTANH);
	
	private SymbolTable symbolTable;
	
	private void error(Context context, String errorMessage, Object... arg) {
		String err = I18n.tr(errorMessage);
		if (arg != null && arg.length > 0)
			err = MessageFormat.format(err,arg);
		context.bldr.reset();
		throw new ParseError(err, context.pos, context.str);
	}
	
	private Type doParse(Context cntx) {
		if (cntx.peek() == Token.EOS)
			error(cntx,"vmm.parser.EmpytDefinition");
		Type type = parseBExpr(cntx);
		if (cntx.peek() != Token.EOS)
			error(cntx,"vmm.parser.ExtraStuff");
		return type;
	}
	
	private Type parseBExpr(Context cntx) {
		Type type = parseBTerm(cntx);
		while (cntx.peek() == Token.OR) {
			if (type != Type.BOOLEAN)
				error(cntx,"vmm.parser.OperatorRequriesBoolean", "OR");
			cntx.next();
			Type nextType = parseBTerm(cntx);
			if (nextType != Type.BOOLEAN)
				error(cntx,"vmm.parser.OperatorRequriesBoolean", "OR");
			cntx.bldr.addStackOp(StackOp.OR);
		}
		if (cntx.peek() == Token.QUESTION) {
			if (type != Type.BOOLEAN)
				error(cntx,"vmm.parser.ConditionalRequiresBoolean");
			cntx.next();
			int firstExpr = cntx.bldr.startSubProg();
			type = parseNumericalExpr(cntx);
			if (type == Type.BOOLEAN)
				error(cntx,"vmm.parser.ConditionalExpressionsMustBeNumerical");
			cntx.bldr.finishSubProg();
			int secondExpr = cntx.bldr.startSubProg();
			Type nextType;
			if (cntx.peek() != Token.COLON) {
				nextType = type;
				if (type == Type.REAL)
					cntx.bldr.addRealConstant(Double.NaN);
				else
					cntx.bldr.addComplexConstant(Double.NaN, Double.NaN);
			}
			else {
				cntx.next();
				nextType = parseBExpr(cntx);
				if (nextType == Type.BOOLEAN)
					error(cntx,"vmm.parser.ConditionalExpressionsMustBeNumerical");
				if (type == Type.COMPLEX && nextType == Type.REAL)
					cntx.bldr.addStackOp(StackOp.REAL_TO_COMPLEX);
			}
			cntx.bldr.finishSubProg();
			if (type == Type.REAL && nextType == Type.COMPLEX) {
				cntx.bldr.addConditional(firstExpr, secondExpr, true);
				type = Type.COMPLEX;
			}
			else
				cntx.bldr.addConditional(firstExpr,secondExpr,false);
		}
		return type;
	}
	
	private Type parseBTerm(Context cntx) {
		Type type = parseBFactor(cntx);
		while (cntx.peek() == Token.AND) {
			if (type != Type.BOOLEAN)
				error(cntx,"vmm.parser.OperatorRequriesBoolean", "AND");
			cntx.next();
			Type nextType = parseBFactor(cntx);
			if (nextType != Type.BOOLEAN)
				error(cntx,"vmm.parser.OperatorRequriesBoolean", "AND");
			cntx.bldr.addStackOp(StackOp.AND);
		}
		return type;
	}
	
	private Type parseBFactor(Context cntx) {
		int notCount = 0;
		while (cntx.peek() == Token.NOT) {
			notCount++;
			cntx.next();
		}
		Type type = parseRelation(cntx);
		if (notCount > 0 && type != Type.BOOLEAN)
			error(cntx,"vmm.parser.OperatorRequriesBoolean", "NOT");
		if (notCount % 2 == 1)
			cntx.bldr.addStackOp(StackOp.NOT);
		return type;
	}
	
	private Type parseRelation(Context cntx) {
		Type type = parseNumericalExpr(cntx);
		if (relationalOps.contains(cntx.peek())) {
			Token op = cntx.next();
			String opName = cntx.tokstr;
			if (type == Type.BOOLEAN)
				error(cntx,"vmm.parser.OperatorRequiresNumerical",opName);
			if (type == Type.COMPLEX && op != Token.EQUAL && op != Token.NOT_EQUAL)
				error(cntx,"vmm.parser.RelationNotDefinedForComplex",opName);
			Type nextType = parseNumericalExpr(cntx);
			if (nextType == Type.BOOLEAN)
				error(cntx,"vmm.parser.OperatorRequiresNumerical",opName);
			if (nextType == Type.COMPLEX && op != Token.EQUAL && op != Token.NOT_EQUAL)
				error(cntx,"vmm.parser.RelationNotDefinedForComplex",opName);
			if (type == Type.REAL && nextType == Type.COMPLEX) {
				cntx.bldr.addStackOp(StackOp.FIRST_OP_TO_COMPLEX);
				type = Type.COMPLEX;
			}
			switch (op) {
			case EQUAL:
				cntx.bldr.addStackOp( type == Type.COMPLEX ? StackOp.C_EQ : StackOp.EQ );
				break;
			case NOT_EQUAL:
				cntx.bldr.addStackOp( type == Type.COMPLEX ? StackOp.C_NE : StackOp.NE );
				break;
			case GREATER:
				cntx.bldr.addStackOp(StackOp.GT);
				break;
			case LESS:
				cntx.bldr.addStackOp(StackOp.LT);
				break;
			case GREATER_EQUAL:
				cntx.bldr.addStackOp(StackOp.GE);
				break;
			case LESS_EQUAL:
				cntx.bldr.addStackOp(StackOp.LE);
				break;
			}
			if (relationalOps.contains(cntx.peek()))
				error(cntx,"vmm.parser.CantStringRelations");
			return Type.BOOLEAN;
		}
		return type;
	}
	
	private Type parseNumericalExpr(Context cntx) {
		Token leadingSign = null;
		if (cntx.peek() == Token.PLUS || cntx.peek() == Token.MINUS)
			leadingSign = cntx.next();
		Type type = parseNumericalTerm(cntx);
		if (leadingSign != null) {
			if (type == Type.BOOLEAN)
				error(cntx,"vmm.parser.OperatorRequiresNumerical",leadingSign == Token.PLUS ? "+" : "-");
			if (leadingSign == Token.MINUS)
				cntx.bldr.addStackOp( type == Type.REAL ? StackOp.UNARY_MINUS : StackOp.C_UNARY_MINUS);
		}
		while (cntx.peek() == Token.PLUS || cntx.peek() == Token.MINUS) {
			Token op = cntx.next();
			String opName = cntx.tokstr;
			if (type == Type.BOOLEAN)
				error(cntx,"vmm.parser.OperatorRequiresNumerical",opName);
			Type nextType = parseNumericalTerm(cntx);
			if (nextType == Type.BOOLEAN)
				error(cntx,"vmm.parser.OperatorRequiresNumerical",opName);
			if (type == Type.REAL && nextType == Type.COMPLEX) {
				cntx.bldr.addStackOp(StackOp.FIRST_OP_TO_COMPLEX);
				type = Type.COMPLEX;
			}
			else if (type == Type.COMPLEX && nextType == Type.REAL)
				cntx.bldr.addStackOp(StackOp.REAL_TO_COMPLEX);
			if (type == Type.REAL)
				cntx.bldr.addStackOp( op == Token.PLUS ? StackOp.PLUS : StackOp.MINUS );
			else
				cntx.bldr.addStackOp( op == Token.PLUS ? StackOp.C_PLUS : StackOp.C_MINUS);
		}
		return type;
	}
	
	private Type parseNumericalTerm(Context cntx) {
		Type type = parseNumericalFactor(cntx);
		while (canStartFactor.contains(cntx.peek())) {
			Token op;
			String opName;
			if (cntx.peek() == Token.TIMES || cntx.peek() == Token.DIVIDE) {
				op = cntx.next();
				opName = cntx.tokstr;
			}
			else {
				op = Token.TIMES;
				opName = "*";
			}
			if (type == Type.BOOLEAN)
				error(cntx,"vmm.parser.OperatorRequiresNumerical",opName);
			Type nextType = parseNumericalFactor(cntx);
			if (nextType == Type.BOOLEAN)
				error(cntx,"vmm.parser.OperatorRequiresNumerical",opName);
			if (type == Type.REAL && nextType == Type.COMPLEX) {
				cntx.bldr.addStackOp(StackOp.FIRST_OP_TO_COMPLEX);
				type = Type.COMPLEX;
			}
			else if (type == Type.COMPLEX && nextType == Type.REAL)
				cntx.bldr.addStackOp(StackOp.REAL_TO_COMPLEX);
			if (type == Type.REAL)
				cntx.bldr.addStackOp( op == Token.TIMES ? StackOp.TIMES : StackOp.DIVIDE );
			else
				cntx.bldr.addStackOp( op == Token.TIMES ? StackOp.C_TIMES : StackOp.C_DIVIDE);
		}
		return type;
	}
	
	private Type parseNumericalFactor(Context cntx) {
		Type type = parseNumericalPrimary(cntx);
		while (cntx.peek() == Token.POWER) {
			cntx.next();
			String opName = cntx.tokstr;
			if (type == Type.BOOLEAN)
				error(cntx,"vmm.parser.OperatorRequiresNumerical",opName);
			Type nextType = parseNumericalPrimary(cntx);
			if (nextType == Type.BOOLEAN)
				error(cntx,"vmm.parser.OperatorRequiresNumerical",opName);
			if (type == Type.REAL) {
				if (nextType == Type.COMPLEX) {
					cntx.bldr.addStackOp(StackOp.FIRST_OP_TO_COMPLEX);
					type = Type.COMPLEX;
				}
				else if (cntx.complexOnly) {
					cntx.bldr.addStackOp(StackOp.REAL_TO_COMPLEX);
					cntx.bldr.addStackOp(StackOp.FIRST_OP_TO_COMPLEX);
					type = nextType = Type.COMPLEX;
				}
			}
			if (type == Type.COMPLEX && nextType == Type.REAL)
				cntx.bldr.addStackOp(StackOp.C_REAL_POWER);
			else if (type == Type.REAL)
				cntx.bldr.addStackOp(StackOp.POWER);
			else
				cntx.bldr.addStackOp(StackOp.C_POWER);
		}
		return type;
	}
	
	private Type parseNumericalPrimary(Context cntx) {
		Type type;
		Token tok = cntx.next();
		Token openingParen;
		switch (tok) {
		case NUMBER:
			cntx.bldr.addRealConstant(cntx.number);
			return Type.REAL;
		case VARIABLE:
			cntx.bldr.addVariableRef((Variable)cntx.symbol);
			return Type.REAL;
		case ARGUMENT:
			cntx.bldr.addArgumentReference(((Argument)cntx.symbol).argnum);
			return Type.REAL;
		case COMPLEX_VARIABLE:
			cntx.bldr.addComplexVariableRef((ComplexVariable)cntx.symbol);
			return Type.COMPLEX;
		case COMPLEX_ARGUMENT:
			cntx.bldr.addArgumentReference(((ComplexArgument)cntx.symbol).argnum);
			return Type.COMPLEX;
		case FUNCTION:
		case COMPLEX_FUNCTION:
		case STANDARD_FUNCTION:
		case FUNCTION_COMPLEX_TO_REAL:
			Token funcTok = tok;
			Object symbol = cntx.symbol;
			String funcName = cntx.tokstr;
			openingParen = cntx.next();
			if (openingParen != Token.LEFT_PAREN && openingParen != Token.LEFT_BRACE && openingParen != Token.LEFT_BRACKET)
				error(cntx,"vmm.parser.FunctionRequiresParen",funcName);
			int argCount = 0;
			int expectedArgs = funcTok == Token.FUNCTION ? ((Function)symbol).getArity() :
	              funcTok == Token.COMPLEX_FUNCTION ? ((ComplexFunction)symbol).getArity() : 
	            	  funcTok == Token.STANDARD_FUNCTION ? 1 : 2;
	        Type argType = null;
			while (true) {
				tok = cntx.peek();
				if (tok == Token.RIGHT_PAREN || tok == Token.RIGHT_BRACE || tok == Token.RIGHT_BRACKET)
					break;
				argCount++;
				if (argCount > expectedArgs)
					error(cntx,"vmm.parser.TooManyArguments",funcName);
				argType = parseBExpr(cntx);
				if (funcTok == Token.FUNCTION || funcTok == Token.FUNCTION_COMPLEX_TO_REAL) {
					if (argType != Type.REAL)
						error(cntx,"vmm.parser.NeedRealArgument",funcName);
				}
				else if (funcTok == Token.COMPLEX_FUNCTION) {
					if (argType == Type.BOOLEAN)
						error(cntx,"vmm.parser.NeedComplexArgument",funcName);
					if (argType == Type.REAL)
						cntx.bldr.addStackOp(StackOp.REAL_TO_COMPLEX);
				}
				else if (funcTok == Token.STANDARD_FUNCTION) {
					StandardFunction f = (StandardFunction)symbol;
					if (argType == Type.COMPLEX) {
						if (f.getComplexArgOp() == null)
							error(cntx,"vmm.parser.NeedRealArgument",funcName);
					}
					else if (argType == Type.REAL) {
						if (f.getRealArgOp() == null || (cntx.complexOnly && needRealToComplex.contains(f.getRealArgOp()))) {
							cntx.bldr.addStackOp(StackOp.REAL_TO_COMPLEX);
							argType = Type.COMPLEX;
						}
					}
					else { // argType is boolean
						if (f.getRealArgOp() == null || (cntx.complexOnly && needRealToComplex.contains(f.getRealArgOp())))
							error(cntx,"vmm.parser.NeedComplexArgument",funcName);
						else
							error(cntx,"vmm.parser.NeedRealArgument",funcName);
					}
				}
				tok = cntx.peek();
				if (tok != Token.COMMA)
					break;
				cntx.next();
			}
			tok = cntx.next();
			if (argCount < expectedArgs)
				error(cntx,"vmm.parser.NotEnoughArguments",funcName);
			if (openingParen == Token.LEFT_PAREN && tok != Token.RIGHT_PAREN)
				error(cntx,"vmm.parser.MissingCloseOfArgumentList",")");
			if (openingParen == Token.LEFT_BRACE && tok != Token.RIGHT_BRACE)
				error(cntx,"vmm.parser.MissingCloseOfArgumentList","}");
			if (openingParen == Token.LEFT_BRACKET && tok != Token.RIGHT_BRACKET)
				error(cntx,"vmm.parser.MissingCloseOfArgumentList","]");
	        if (funcTok == Token.FUNCTION) {
	        	cntx.bldr.addFunctionRef(((Function)symbol).getProgFunction());
	        	return Type.REAL;
	        }
	        else if (funcTok == Token.COMPLEX_FUNCTION) {
	        	cntx.bldr.addFunctionRef(((ComplexFunction)symbol).getProgFunction());
	        	return Type.COMPLEX;
	        }
	        else if (funcTok == Token.FUNCTION_COMPLEX_TO_REAL)
	        	return Type.COMPLEX;
	        else {
	        	StandardFunction f = (StandardFunction)symbol;
	        	cntx.bldr.addStackOp( argType == Type.COMPLEX ? f.getComplexArgOp() : f.getRealArgOp() );
	        	return argType == Type.COMPLEX ? f.getReturnTypeForComplexArg() : f.getReturnTypeForRealArg();
	        }
		case LEFT_PAREN:
			type = parseBExpr(cntx);
			tok = cntx.next();
			if (tok == Token.EOS)
				error(cntx,"vmm.parser.MissingRightGroupThingAtEOS",")","(");
			if (tok != Token.RIGHT_PAREN)
				error(cntx,"vmm.parser.MissingRightGroupThing",")","(",cntx.tokstr);
			return type;
		case LEFT_BRACE:
			type = parseBExpr(cntx);
			tok = cntx.next();
			if (tok == Token.EOS)
				error(cntx,"vmm.parser.MissingRightGroupThingAtEOS","]","[");
			if (tok != Token.RIGHT_BRACE)
				error(cntx,"vmm.parser.MissingRightGroupThing","]","[",cntx.tokstr);
			return type;
		case LEFT_BRACKET:
			type = parseBExpr(cntx);
			tok = cntx.next();
			if (tok == Token.EOS)
				error(cntx,"vmm.parser.MissingRightGroupThingAtEOS","}","{");
			if (tok != Token.RIGHT_PAREN)
				error(cntx,"vmm.parser.MissingRightGroupThing","}","{",cntx.tokstr);
			return type;
		case RIGHT_PAREN:
			error(cntx,"vmm.parser.ExtraRightGroupThing",")","(");
			return null;
		case RIGHT_BRACE:
			error(cntx,"vmm.parser.ExtraRightGroupThing","}","{");
			return null;
		case RIGHT_BRACKET:
			error(cntx,"vmm.parser.ExtraRightGroupThing","]","[");
			return null;
		case UNKNOWN_CHAR:
			error(cntx,"vmm.parser.UnknownChar",cntx.tokstr);
			return null;
		case UNKNOWN_WORD:
			error(cntx,"vmm.parser.UndefinedWord",cntx.tokstr);
			return null;
		case ILLEGAL_NUMBER:
			error(cntx,"vmm.parser.IllegalNumber",cntx.tokstr);
			return null;
		case EOS:
			error(cntx,"vmm.parser.IncompleteExpression");
		default:
			error(cntx,"vmm.parser.UnexcpectedToken",cntx.tokstr);
			return null;
		}
	}
		
	//-------------------------------------------------------------------------------------------
	
	private enum Token {
		NUMBER, ILLEGAL_NUMBER, 
		FUNCTION, COMPLEX_FUNCTION, STANDARD_FUNCTION, FUNCTION_COMPLEX_TO_REAL,
		VARIABLE, COMPLEX_VARIABLE, ARGUMENT, COMPLEX_ARGUMENT, 
		UNKNOWN_WORD, 
		LEFT_PAREN, RIGHT_PAREN, LEFT_BRACE, RIGHT_BRACE, LEFT_BRACKET, RIGHT_BRACKET,
		PLUS, MINUS, TIMES, DIVIDE, POWER, AND, OR, NOT,
		EQUAL, NOT_EQUAL, GREATER, LESS, GREATER_EQUAL, LESS_EQUAL,
		QUESTION, COLON, COMMA,
		UNKNOWN_CHAR, EOS
	}
	
	private static class Context {
		private final static Pattern numberRegex = Pattern.compile("(\\+|-)?(([0-9]+(\\.[0-9]*)?)|(\\.[0-9]+))((e|E)(\\+|-)?[0-9]+)?");
		private Matcher numberMatcher;   // Used for reading floating point numbers; created from the floatRegex Pattern.
		private SymbolTable symbolTable;
		private Token currentToken;
		ProgFunction.Builder bldr;
		boolean complexOnly;
		Object symbol;
		String str;
		int pos;
		double number;      // set for token type NUMBER only
		String tokstr;      // set for all tokens
		Context(String str, SymbolTable table, boolean complexOnly) {
			this.str = str;
			this.pos = 0;
			this.symbolTable = table;
			this.complexOnly = complexOnly;
			this.bldr = new ProgFunction.Builder();
		}
		Token peek() {
			if (currentToken == null)
				currentToken = readToken();
			return currentToken;
		}
		Token next() {
			Token t = peek();
			currentToken = null;
			return t;
		}
		private Token readToken() {
			symbol = null;
			if (str == null)
				return Token.EOS;
			while (pos < str.length() && Character.isWhitespace(str.charAt(pos)))
				pos++;
			if (pos >= str.length())
				return Token.EOS;
			char ch = str.charAt(pos);
			if (Character.isLetter(ch) || ch == '_') {
				tokstr = "";
				while ( pos < str.length() && (Character.isLetterOrDigit(str.charAt(pos)) || str.charAt(pos) == '_') ) {
					tokstr += str.charAt(pos);
					pos++;
				}
				while ( pos < str.length() && str.charAt(pos) == '\'') {
					tokstr += '\'';
					pos++;
				}
				String lower = tokstr.toLowerCase();
				symbol = symbolTable.get(lower);
				if (symbol != null) {
					if (symbol instanceof Variable)
						return Token.VARIABLE;
					else if (symbol instanceof ComplexVariable)
						return Token.COMPLEX_VARIABLE;
					else if (symbol instanceof Function)
						return Token.FUNCTION;
					else if (symbol instanceof ComplexFunction)
						return Token.COMPLEX_FUNCTION;
					else if (symbol instanceof Argument)
						return Token.ARGUMENT;
					else if (symbol instanceof ComplexArgument)
						return Token.COMPLEX_ARGUMENT;
					else if (symbol instanceof StandardFunction)
						return Token.STANDARD_FUNCTION;
					else
						throw new IllegalStateException("internal error: unknown object type in symbol table");
				}
				else if (lower.equals("complex"))
					return Token.FUNCTION_COMPLEX_TO_REAL;
				else if (lower.equals("and"))
					return Token.AND;
				else if (lower.equals("or"))
					return Token.OR;
				else if (lower.equals("not"))
					return Token.NOT;
				else
					return Token.UNKNOWN_WORD;
			}
			else if (Character.isDigit(ch) || ch == '.') {
				if (numberMatcher == null)
					numberMatcher = numberRegex.matcher(str);
				numberMatcher.region(pos,str.length());
				if (numberMatcher.lookingAt()) {
					tokstr = numberMatcher.group();
					pos = numberMatcher.end();
					try {
						number = Double.parseDouble(tokstr);
						if (Double.isInfinite(number) || Double.isNaN(number))
							throw new NumberFormatException();
					}
					catch (NumberFormatException e) {
						return Token.ILLEGAL_NUMBER;
					}
					return Token.NUMBER;
				}
				else {
					pos++;
					tokstr = ".";
					return Token.ILLEGAL_NUMBER;
				}
			}
			else if (ch == '=') {
				tokstr = "=";
				pos++;
				if (pos == str.length())
					return Token.EQUAL;
				else if (str.charAt(pos) == '=') {
					tokstr += '=';
					pos++;
					return Token.EQUAL;
				}
				else if (str.charAt(pos) == '>') {
					tokstr += '>';
					pos++;
					return Token.GREATER_EQUAL;
				}
				else if (str.charAt(pos) == '<') {
					tokstr += '<';
					pos++;
					return Token.LESS_EQUAL;
				}
				else
					return Token.EQUAL;
			}
			else if (ch == '<') {
				tokstr = "<";
				pos++;
				if (pos == str.length())
					return Token.LESS;
				else if (str.charAt(pos) == '=') {
					pos++;
					tokstr += '=';
					return Token.LESS_EQUAL;
				}
				else if (str.charAt(pos) == '>') {
					pos++;
					tokstr += '>';
					return Token.NOT_EQUAL;
				}
				else
					return Token.LESS;
			}
			else if (ch == '>') {
				tokstr = ">";
				pos++;
				if (pos == str.length())
					return Token.GREATER;
				else if (str.charAt(pos) == '=') {
					tokstr += '=';
					pos++;
					return Token.GREATER_EQUAL;
				}
				else if (str.charAt(pos) == '>') {
					tokstr += '>';
					pos++;
					return Token.NOT_EQUAL;
				}
				else
					return Token.GREATER;
			}
			else if (ch == '!') {
				tokstr = "!";
				pos++;
				if (pos < str.length() && str.charAt(pos) == '=') {
					tokstr += '=';
					pos++;
					return Token.NOT_EQUAL;
				}
				else
					return Token.UNKNOWN_CHAR;
			}
			else if (ch == '*') {
				tokstr = "*";
				pos++;
				if (pos < str.length() && str.charAt(pos) == '*') {
					tokstr += '*';
					pos++;
					return Token.POWER;
				}
				else
					return Token.TIMES;
			}
			else if (ch == '&') {
				tokstr = "&";
				pos++;
				if (pos < str.length() && str.charAt(pos) == '&') {
					tokstr += '&';
					pos++;
				}
				return Token.AND;
			}
			else if (ch == '|') {
				tokstr = "|";
				pos++;
				if (pos < str.length() && str.charAt(pos) == '|') {
					tokstr += '|';
					pos++;
				}
				return Token.OR;
			}
			else  {
				tokstr = "" + ch;
				pos++;
				switch(ch) {
				case '(': return Token.LEFT_PAREN;
				case ')': return Token.RIGHT_PAREN;
				case '{': return Token.LEFT_BRACE;
				case '}': return Token.RIGHT_BRACE;
				case '[': return Token.LEFT_BRACKET;
				case ']': return Token.RIGHT_BRACKET;
				case '?': return Token.QUESTION;
				case ':': return Token.COLON;
				case ',': return Token.COMMA;
				case '+': return Token.PLUS;
				case '-': return Token.MINUS;
				case '/': return Token.DIVIDE;
				case '^': return Token.POWER;
				case '~': return Token.NOT;
				default:  return Token.UNKNOWN_CHAR;
				}
			}
		}
	}
	
	private static class SymbolTable {
		private SymbolTable parent;
		private HashMap<String, Object> table = new HashMap<String,Object>();
		SymbolTable() {
		}
		SymbolTable(SymbolTable parent) {
			this.parent = parent;
		}
		void put(String name, Object val) {
			table.put(name, val);
		}
		void remove(String name) {
			if (name != null)
				table.remove(name.toLowerCase());
		}
		Object get(String name) {
			Object val = table.get(name);
			if (val == null && parent != null)
				return parent.get(name);
			else
				return val;
		}
 	}
	
	private static class Argument {
		int argnum;
		Argument(int n) {
			argnum = n;
		}
	}
	
	private static class ComplexArgument {
		int argnum;
		ComplexArgument(int n) {
			argnum = n;
		}
	}
	
}
