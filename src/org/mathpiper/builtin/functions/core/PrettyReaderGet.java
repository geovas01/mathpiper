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
import org.mathpiper.lisp.cons.AtomCons;
import org.mathpiper.lisp.Environment;

/**
 *
 *  
 */
public class PrettyReaderGet extends BuiltinFunction
{

    private PrettyReaderGet()
    {
    }

    public PrettyReaderGet(String functionName)
    {
        this.functionName = functionName;
    }


    public void evaluate(Environment aEnvironment, int aStackTop) throws Exception
    {
        if (aEnvironment.iPrettyReaderName == null)
        {
            setTopOfStack(aEnvironment, aStackTop, AtomCons.getInstance(aEnvironment, "\"\""));
        } else
        {
            setTopOfStack(aEnvironment, aStackTop, AtomCons.getInstance(aEnvironment, aEnvironment.iPrettyReaderName));
        }
    }
}



/*
%mathpiper_docs,name="PrettyReaderGet",categories="User Functions;Built In"
*CMD PrettyReaderGet --- get routine that is currently used as pretty-reader

*CORE

*CALL
	PrettyReaderGet()

*DESC

{PrettyReaderGet()} returns the current reader, or it returns
an empty string if the default pretty printer is used.



*E.G.

In> PrettyReaderGet()
Result: ""

*SEE Read, LispRead, OMRead, PrettyPrinterSet, PrettyPrinterGet, PrettyReaderSet
%/mathpiper_docs
*/