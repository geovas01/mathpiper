/*  This file is part of the source code for 3D-XplorMath-J, Version 1.0 (January 2008).
 *  Copyright (c) 2008 The 3D-XplorMath Consortium (http://3d-xplormath.org).
 *  This source code is released under a BSD License, which allows redistribution   
 *  in source and binary form, with or without modification, provided copyright
 *  and license information are included, and with no warranty or guarantee of
 *  any kind.  For details, see http://3d-xplormath.org/j/source/BSDLicense.txt
 */
 
 package vmm.functions;

/**
 * A package-private class that defines objects that represent standard functions
 * such as sin(x) and abs(z).  This is for use by {@link Parser}; objects belonging
 * to this class are stored in the Parser's symbol table.
 */
class StandardFunction {

	private String name;
	private StackOp realArgOp;
	private Type returnTypeForRealArg;
	private StackOp complexArgOp;
	private Type returnTypeForComplexArg;
	
	private static StandardFunction[] functions;

	private StandardFunction(String name, StackOp realArgOp, Type returnTypeForRealArg, StackOp complexArgOp, Type returnTypeForComplexArg) {
		super();
		this.name = name;
		this.realArgOp = realArgOp;
		this.returnTypeForRealArg = returnTypeForRealArg;
		this.complexArgOp = complexArgOp;
		this.returnTypeForComplexArg = returnTypeForComplexArg;
	}

	StackOp getComplexArgOp() {
		return complexArgOp;
	}

	String getName() {
		return name;
	}

	StackOp getRealArgOp() {
		return realArgOp;
	}

	Type getReturnTypeForComplexArg() {
		return returnTypeForComplexArg;
	}

	Type getReturnTypeForRealArg() {
		return returnTypeForRealArg;
	}

	static StandardFunction[] getFunctions() {
		if (functions == null)
			makeStandardFunctions();
		return functions;
	}

	private static void makeStandardFunctions() {
		functions = new StandardFunction[] {
			new StandardFunction("ABS",StackOp.ABS,Type.REAL,StackOp.C_ABS,Type.COMPLEX),
			new StandardFunction("SQRT",StackOp.SQRT,Type.REAL,StackOp.C_SQRT,Type.COMPLEX),
			new StandardFunction("CUBERT",StackOp.CUBERT,Type.REAL,StackOp.C_CUBERT,Type.COMPLEX),
			new StandardFunction("EXP",StackOp.EXP,Type.REAL,StackOp.C_EXP,Type.COMPLEX),
			new StandardFunction("LN",StackOp.LOG,Type.REAL,StackOp.C_LOG,Type.COMPLEX),
			new StandardFunction("LOG",StackOp.LOG,Type.REAL,StackOp.C_LOG,Type.COMPLEX),
			new StandardFunction("LOG2",StackOp.LOG2,Type.REAL,StackOp.C_LOG2,Type.COMPLEX),
			new StandardFunction("LOG10",StackOp.LOG10,Type.REAL,StackOp.C_LOG10,Type.COMPLEX),
			new StandardFunction("SIN",StackOp.SIN,Type.REAL,StackOp.C_SIN,Type.COMPLEX),
			new StandardFunction("COS",StackOp.COS,Type.REAL,StackOp.C_COS,Type.COMPLEX),
			new StandardFunction("TAN",StackOp.TAN,Type.REAL,StackOp.C_TAN,Type.COMPLEX),
			new StandardFunction("SEC",StackOp.SEC,Type.REAL,StackOp.C_SEC,Type.COMPLEX),
			new StandardFunction("COT",StackOp.COT,Type.REAL,StackOp.C_COT,Type.COMPLEX),
			new StandardFunction("CSC",StackOp.CSC,Type.REAL,StackOp.C_CSC,Type.COMPLEX),
			new StandardFunction("SINH",StackOp.SINH,Type.REAL,StackOp.C_SINH,Type.COMPLEX),
			new StandardFunction("COSH",StackOp.COSH,Type.REAL,StackOp.C_COSH,Type.COMPLEX),
			new StandardFunction("TANH",StackOp.TANH,Type.REAL,StackOp.C_TANH,Type.COMPLEX),
			new StandardFunction("SECH",StackOp.SECH,Type.REAL,StackOp.C_SECH,Type.COMPLEX),
			new StandardFunction("COTH",StackOp.COTH,Type.REAL,StackOp.C_COTH,Type.COMPLEX),
			new StandardFunction("CSCH",StackOp.CSCH,Type.REAL,StackOp.C_CSCH,Type.COMPLEX),
			new StandardFunction("ARCSIN",StackOp.ARCSIN,Type.REAL,StackOp.C_ARCSIN,Type.COMPLEX),
			new StandardFunction("ARCCOS",StackOp.ARCCOS,Type.REAL,StackOp.C_ARCCOS,Type.COMPLEX),
			new StandardFunction("ARCTAN",StackOp.ARCTAN,Type.REAL,StackOp.C_ARCTAN,Type.COMPLEX),
			new StandardFunction("ARCSINH",StackOp.ARCSINH,Type.REAL,StackOp.C_ARCSINH,Type.COMPLEX),
			new StandardFunction("ARCCOSH",StackOp.ARCCOSH,Type.REAL,StackOp.C_ARCCOSH,Type.COMPLEX),
			new StandardFunction("ARCTANH",StackOp.ARCTANH,Type.REAL,StackOp.C_ARCTANH,Type.COMPLEX),
			new StandardFunction("ASIN",StackOp.ARCSIN,Type.REAL,StackOp.C_ARCSIN,Type.COMPLEX),
			new StandardFunction("ACOS",StackOp.ARCCOS,Type.REAL,StackOp.C_ARCCOS,Type.COMPLEX),
			new StandardFunction("ATAN",StackOp.ARCTAN,Type.REAL,StackOp.C_ARCTAN,Type.COMPLEX),
			new StandardFunction("ASINH",StackOp.ARCSINH,Type.REAL,StackOp.C_ARCSINH,Type.COMPLEX),
			new StandardFunction("ACOSH",StackOp.ARCCOSH,Type.REAL,StackOp.C_ARCCOSH,Type.COMPLEX),
			new StandardFunction("ATANH",StackOp.ARCTANH,Type.REAL,StackOp.C_ARCTANH,Type.COMPLEX),
			new StandardFunction("TRUNC",StackOp.TRUNC,Type.REAL,null,null),
			new StandardFunction("ROUND",StackOp.ROUND,Type.REAL,null,null),
			new StandardFunction("CEILING",StackOp.CEILING,Type.REAL,null,null),
			new StandardFunction("FLOOR",StackOp.FLOOR,Type.REAL,null,null),
			new StandardFunction("SIGN",StackOp.SIGNUM,Type.REAL,null,null),
			new StandardFunction("SGN",StackOp.SIGNUM,Type.REAL,null,null),
			new StandardFunction("SIGNUM",StackOp.SIGNUM,Type.REAL,null,null),
			new StandardFunction("RE",null,null,StackOp.COMPLEX_TO_REAL,Type.REAL),
			new StandardFunction("IM",null,null,StackOp.IMAGINARY_PART,Type.REAL),
			new StandardFunction("CONJ",null,null,StackOp.CONJ,Type.COMPLEX)
		};
	}


}
