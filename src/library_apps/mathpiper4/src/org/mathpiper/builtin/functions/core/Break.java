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
import org.mathpiper.exceptions.BreakException;
import org.mathpiper.lisp.Environment;

/**
 *
 *
 */
public class Break extends BuiltinFunction
{

    private Break()
    {
    }

    public Break(String functionName)
    {
        this.functionName = functionName;
    }


    public void evaluate(Environment aEnvironment, int aStackTop) throws Throwable
    {
         throw new BreakException();
    }
    
}//end class.



/*
%mathpiper_docs,name="Break",categories="Programming Functions;Control Flow;Built In"
*CMD Break --- break out of a loop
*CORE
*CALL

    Break()

*DESC

If Break is executed inside of a While, Until, For, or ForEach loop, it will
cause the loop to be exited.

*E.G.

/%/mathpiper

    %output,sequence="1",timestamp="2013-12-05 09:27:53.559",preserve="false"
      Result: class javax.swing.JFrame
      
      Side Effects:
      [["criticalFScoreBlock",3.325834530],["fScoreBlock",0.08045614890],["criticalFScore",4.102821015],["fScore",3.078377024],["meanSquareBlock",0.1418888884],["meanSquareWithin",1.763555556],["meanSquareBetween",5.428888905],["sumOfSquaresTotal",29.20277781],["sumOfSquaresBlock",0.7094444419],["sumOfSquaresBetween",10.85777781],["sumOfSquaresWithin",17.63555556],["html",class java.lang.String]] 
      
      F-Score of the block: 0.08045614890 
      
.   %/output

/%mathpiper

x := 1;

While(x <=? 10)
{
    Echo(x);

    If(x =? 5,) Break();

    x++;
};

/%/mathpiper

    /%output,sequence="5",timestamp="2013-12-09 12:41:41.334",preserve="false"
      Result: True
      
      Side Effects:
      1 
      2 
      3 
      4 
      5 
      
.   /%/output

*SEE While, Until, For, ForEach, Continue
%/mathpiper_docs
*/