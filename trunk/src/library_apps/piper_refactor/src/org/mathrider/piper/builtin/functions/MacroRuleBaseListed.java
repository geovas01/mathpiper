/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.mathrider.piper.builtin.functions;

import org.mathrider.piper.builtin.BuiltinFunction;
import org.mathrider.piper.lisp.Environment;

/**
 *
 *  
 */
	public class MacroRuleBaseListed extends BuiltinFunction
	{
		public void eval(Environment aEnvironment,int aStackTop) throws Exception
		{
			org.mathrider.piper.builtin.Functions.internalRuleBase(aEnvironment, aStackTop, true);
		}
	}
