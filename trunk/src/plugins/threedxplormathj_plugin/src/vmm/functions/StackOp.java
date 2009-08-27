/*  This file is part of the source code for 3D-XplorMath-J, Version 1.0 (January 2008).
 *  Copyright (c) 2008 The 3D-XplorMath Consortium (http://3d-xplormath.org).
 *  This source code is released under a BSD License, which allows redistribution   
 *  in source and binary form, with or without modification, provided copyright
 *  and license information are included, and with no warranty or guarantee of
 *  any kind.  For details, see http://3d-xplormath.org/j/source/BSDLicense.txt
 */
 
 package vmm.functions;

/**
 * A package-private enum whose values represent operations that can be applied to
 * an {@link EvalStack}.
 */
enum StackOp  {
	
	COMPLEX_TO_REAL, REAL_TO_COMPLEX, FIRST_OP_TO_COMPLEX, IMAGINARY_PART,
	
	PLUS, MINUS, TIMES, DIVIDE, POWER,
	C_PLUS, C_MINUS, C_TIMES, C_DIVIDE, C_POWER, 
	C_REAL_POWER,
	
	EQ, NE, GE, GT, LE, LT, C_EQ, C_NE,
	
	AND, OR, NOT,
	
	UNARY_MINUS, C_UNARY_MINUS,
	
	ABS, SQRT, CUBERT, EXP, LOG, LOG2, LOG10,
	C_ABS, C_SQRT, C_CUBERT, C_EXP, C_LOG, C_LOG2, C_LOG10, ARG,
	
	TRUNC, ROUND, CEILING, FLOOR, SIGNUM,
	
	SIN, COS, TAN, SEC, COT, CSC, SINH, COSH, TANH, SECH, COTH, CSCH, ARCSIN, ARCCOS, ARCTAN, ARCSINH, ARCCOSH, ARCTANH,
	C_SIN, C_COS, C_TAN, C_SEC, C_COT, C_CSC, C_SINH, C_COSH, C_TANH, C_SECH, C_COTH, C_CSCH,
	      C_ARCSIN, C_ARCCOS, C_ARCTAN, C_ARCSINH, C_ARCCOSH, C_ARCTANH,
	
	CONJ
	
}
