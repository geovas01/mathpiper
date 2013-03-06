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

import org.mathpiper.builtin.BuiltinFunction;
import org.mathpiper.lisp.Environment;
import org.mathpiper.lisp.LispError;
import org.mathpiper.lisp.Utility;
import org.mathpiper.lisp.cons.Cons;
import org.mathpiper.lisp.cons.SublistCons;

/**
 *
 *  
 */
public class Cdr extends BuiltinFunction
{

    private Cdr()
    {
    }

    public Cdr(String functionName)
    {
        this.functionName = functionName;
    }


    public void evaluate(Environment aEnvironment, int aStackTop) throws Throwable
    {
	Cons argumentCons = getArgument(aEnvironment, aStackTop, 1);

	Object object = argumentCons.cdr();
	
	if(object == null)
	{
	    Cons head = aEnvironment.iListAtom.copy(false);

	    setTopOfStack(aEnvironment, aStackTop, SublistCons.getInstance(aEnvironment, head));
	}
	else
	{
	    setTopOfStack(aEnvironment, aStackTop, ((Cons)object));
	}
        
    }
}



/*
%mathpiper_docs,name="Cdr",categories="Programming Functions;Miscellaneous;Built In"
*CMD Cdr --- the rest of a Lisp list
*CORE
*CALL
	Cdr(lispList)

*PARMS

{lispList} -- a lisp list

*DESC

This function returns the rest of a Lisp list.

*E.G.

In> Cdr(Car([a,b,c]))
Result: a

*SEE Car
%/mathpiper_docs
*/