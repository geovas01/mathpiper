/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.mathpiper.builtin.functions;

import org.mathpiper.builtin.BuiltinFunction;
import org.mathpiper.lisp.Environment;

/**
 *
 *  
 */
	public class MacroRulebaseListed extends BuiltinFunction
	{
		public void evaluate(Environment aEnvironment,int aStackTop) throws Exception
		{
			org.mathpiper.lisp.UtilityFunctions.internalRuleDatabase(aEnvironment, aStackTop, true);
		}
	}
