/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.mathpiper.builtin.functions;

import org.mathpiper.builtin.BigNumber;
import org.mathpiper.builtin.BuiltinFunction;
import org.mathpiper.lisp.Environment;

/**
 *
 *  
 */
	public class Floor extends BuiltinFunction
	{
		public void evaluate(Environment aEnvironment,int aStackTop) throws Exception
		{
			BigNumber x = org.mathpiper.lisp.UtilityFunctions.getNumber(aEnvironment, aStackTop, 1);
			BigNumber z = new BigNumber(aEnvironment.getPrecision());
			z.floor(x);
			getResult(aEnvironment, aStackTop).setCons(new org.mathpiper.lisp.cons.NumberCons(z));
		}
	}



