/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.mathrider.piper.builtin.functions;

import org.mathrider.piper.builtin.BigNumber;
import org.mathrider.piper.builtin.BuiltinFunction;
import org.mathrider.piper.lisp.Environment;

/**
 *
 * @author 
 */
	public class Floor extends BuiltinFunction
	{
		public void eval(Environment aEnvironment,int aStackTop) throws Exception
		{
			BigNumber x = org.mathrider.piper.builtin.Functions.getNumber(aEnvironment, aStackTop, 1);
			BigNumber z = new BigNumber(aEnvironment.precision());
			z.Floor(x);
			RESULT(aEnvironment, aStackTop).set(new org.mathrider.piper.lisp.Number(z));
		}
	}
