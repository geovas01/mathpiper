/* {{{ License.
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA  02111-1307, USA.
 */ //}}}

// :indentSize=4:lineSeparator=\n:noTabs=false:tabSize=4:folding=explicit:collapseFolds=0:

package org.mathpiper.builtin.functions.core;

import javax.swing.JOptionPane;
import org.mathpiper.builtin.BuiltinFunction;
import org.mathpiper.lisp.Environment;
import org.mathpiper.lisp.LispError;
import org.mathpiper.lisp.Utility;
import org.mathpiper.lisp.cons.AtomCons;

/**
 *
 *  
 */
public class AskUser extends BuiltinFunction
{

    public void evaluate(Environment aEnvironment, int aStackTop) throws Exception
    {
        LispError.checkArgument(aEnvironment, aStackTop, getArgumentPointer(aEnvironment, aStackTop, 1).getCons() != null, 1);
        String messageString = (String) getArgumentPointer(aEnvironment, aStackTop, 1).car();
        LispError.checkArgument(aEnvironment, aStackTop, messageString != null, 1);


        messageString = Utility.stripEndQuotes(messageString);

        String userInputString = JOptionPane.showInputDialog(messageString);

        getTopOfStackPointer(aEnvironment, aStackTop).setCons(AtomCons.getInstance(aEnvironment, "\"" + userInputString + "\""));
    }//end method.

}//end class.



/*
%mathpiper_docs,name="AskUser",categories="User Functions;Input/Output;Built In"
*CMD AskUser --- read an expression from command line and return in string
*CORE
*CALL
	AskUser(prompt)

*PARMS

{prompt} -- string representing the prompt shown on screen

*DESC

This function allows for interactive input similar to the command line.
When using this function, the history from the command line is also available.

The result is returned in a string, so it still needs to be parsed.

This function will typically be used in situations where one wants a custom
read-eval-print loop.


*E.G. notest

The following defines a function that when invoked keeps asking
for an expression (the <i>read</i> step), and then takes
the derivative of it (the <i>eval</i> step) and then
uses PrettyForm to display the result (the <i>print</i> step).

	In> ReEvPr() := \
	In>   While(True) [ \
	In>     PrettyForm(Deriv(x) \
	In>      FromString(AskUser("Deriv> "):";")Read()); \
	In> ];
	Out> True;

Then one can invoke the command, from which the following interaction
might follow:

	In> ReEvPr()
	Deriv> Sin(a^2*x/b)

	   /  2     \
	   | a  * x |    2
	Cos| ------ | * a  * b
	   \   b    /
	----------------------
	           2
	          b

	Deriv> Sin(x)

	Cos( x )

	Deriv>

*SEE Read, LispRead, LispReadListed
%/mathpiper_docs
*/