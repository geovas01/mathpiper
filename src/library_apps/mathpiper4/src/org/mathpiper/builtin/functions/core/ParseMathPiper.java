package org.mathpiper.builtin.functions.core;

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


import org.mathpiper.builtin.BuiltinFunction;
import org.mathpiper.lisp.tokenizers.MathPiperTokenizer;
import org.mathpiper.lisp.Environment;
import org.mathpiper.lisp.parsers.MathPiperParser;
import org.mathpiper.lisp.parsers.Parser;
import org.mathpiper.lisp.parsers.LispParser;

/**
 *
 *  
 */
public class ParseMathPiper extends BuiltinFunction
{

    private ParseMathPiper()
    {
    }

    public ParseMathPiper(String functionName)
    {
        this.functionName = functionName;
    }
    


    public void evaluate(Environment aEnvironment, int aStackTop) throws Throwable
    {
        Parser parser = new MathPiperParser(aEnvironment.iCurrentTokenizer, aEnvironment.getCurrentInput(), aEnvironment, aEnvironment.iPrefixOperators, aEnvironment.iInfixOperators, aEnvironment.iPostfixOperators, aEnvironment.iBodiedOperators);
        
        // Parse expression
        setTopOfStack(aEnvironment, aStackTop, parser.parse(aStackTop));
    }
}



/*
%mathpiper_docs,name="ParseMathPiper",categories="Programming Functions;Input/Output;Built In"
*CMD ParseMathPiper --- parse expressions in MathPiper syntax from the current input
*CORE
*CALL
	ParseMathPiper()

*DESC

The function {ParseMathPiper} parses an expression in the MathPiper syntax from the current input, and returns
it unevaluated. When the end of an input file is encountered, the
special token atom {EndOfFile} is returned.


*E.G. notest

In> PipeFromString("2+5;") ParseMathPiper();
Result: 2+5;

In> PipeFromString("") ParseMathPiper();
Result: EndOfFile;

*SEE PipeFromFile, PipeFromString, ParseMathPiperToken, UnparseLisp, ParseLispListed
%/mathpiper_docs







%mathpiper,name="ParseMathPiper",subtype="automatic_test"

Verify(PipeFromString("_a+_b;") ParseMathPiper(),_a+_b);

%/mathpiper
*/
