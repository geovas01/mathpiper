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

package org.mathpiper.builtin.functions.optional;

import org.mathpiper.builtin.BuiltinFunction;
import org.mathpiper.builtin.BuiltinFunctionEvaluator;
import org.mathpiper.lisp.Environment;
import org.mathpiper.lisp.Utility;
import org.mathpiper.ui.gui.help.FunctionTreePanel;

/**
 *
 *
 */
public class Help extends BuiltinFunction
{

    public void plugIn(Environment aEnvironment)
    {
        aEnvironment.getBuiltinFunctions().setAssociation(
                new BuiltinFunctionEvaluator(this, 0, BuiltinFunctionEvaluator.Fixed | BuiltinFunctionEvaluator.Function),
                "Help");
    }//end method.

    public void evaluate(Environment aEnvironment, int aStackTop) throws Exception
    {
         FunctionTreePanel.main(null);

         Utility.putTrueInPointer(aEnvironment, getTopOfStackPointer(aEnvironment, aStackTop));
         
    }//end method.

}//end class.




/*
%mathpiper_docs,name="Help",categories="User Functions;Built In"
*CMD Help --- show the function help window
*CORE
*CALL

    Break()

*DESC

Shows the function help window.


%/mathpiper_docs
*/