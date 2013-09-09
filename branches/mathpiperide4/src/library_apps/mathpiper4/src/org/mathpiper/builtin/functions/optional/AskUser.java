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

import java.util.concurrent.atomic.AtomicReference;

import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;

import org.mathpiper.builtin.BuiltinFunction;
import org.mathpiper.builtin.BuiltinFunctionEvaluator;
import org.mathpiper.builtin.JavaObject;
import org.mathpiper.exceptions.BreakException;
import org.mathpiper.lisp.Environment;
import org.mathpiper.lisp.LispError;
import org.mathpiper.lisp.Utility;
import org.mathpiper.lisp.cons.AtomCons;

/**
 *
 *  
 */
public class AskUser extends BuiltinFunction {

    public void plugIn(Environment aEnvironment) throws Throwable {
        this.functionName = "AskUser";
        aEnvironment.getBuiltinFunctions().put(
		        this.functionName, new BuiltinFunctionEvaluator(this, 1, BuiltinFunctionEvaluator.FixedNumberOfArguments | BuiltinFunctionEvaluator.EvaluateArguments));
    }//end method.

    public void evaluate(Environment aEnvironment, int aStackTop) throws Throwable {
        if (getArgument(aEnvironment, aStackTop, 1) == null) {
            LispError.checkArgument(aEnvironment, aStackTop, 1);
        }


        Object argument = getArgument(aEnvironment, aStackTop, 1).car();


        if (argument == null) {
            LispError.checkArgument(aEnvironment, aStackTop, 1);
        }
        

        if(argument instanceof String)
        {
            final AtomicReference<String> userInput = new AtomicReference<String>();
            
            userInput.set("NO_INPUT_RECEIVED");
            
        	final String messageStringFinal = Utility.stripEndQuotesIfPresent((String) argument);
        
            SwingUtilities.invokeLater(new Runnable(){
                @Override
                public void run() {
                 userInput.set(JOptionPane.showInputDialog(null, messageStringFinal, "Message from MathPiper", JOptionPane.INFORMATION_MESSAGE));
                }
            });
            
            while(userInput.get() != null && ((String)userInput.get()).equals("NO_INPUT_RECEIVED"))
            {
            	try
            	{
            		Thread.sleep(100);
            	}
            	catch(InterruptedException e)
            	{
            		//Eat exception.
            	}
            }
            
            setTopOfStack(aEnvironment, aStackTop, AtomCons.getInstance(aEnvironment, aStackTop, "\"" + userInput.get() + "\""));
        }
        else if(argument instanceof JavaObject)
        {
        	
        	final Object componentFinal = ((JavaObject)argument).getObject();
        	
            if (!(componentFinal instanceof javax.swing.JComponent)) {
                LispError.checkArgument(aEnvironment, aStackTop, 1);
            }
            
            final AtomicReference<Integer> userInput = new AtomicReference<Integer>();
            
            userInput.set(-1);
            
        	
            
            SwingUtilities.invokeLater(new Runnable(){
                @Override
                public void run() {
                 userInput.set(JOptionPane.showConfirmDialog(null, componentFinal, "AskUser", JOptionPane.OK_CANCEL_OPTION));
                }
            });
            
            while(userInput.get() != null && userInput.get() != JOptionPane.CANCEL_OPTION && userInput.get() != JOptionPane.OK_OPTION)
            {
            	try
            	{
            		Thread.sleep(100);
            	}
            	catch(InterruptedException e)
            	{
            		//Eat exception.
            	}
            }
            
            setTopOfStack(aEnvironment, aStackTop, AtomCons.getInstance(aEnvironment, aStackTop, userInput.get().toString()));
        }
        
        
    }//end method.
}//end class.



/*
%mathpiper_docs,name="AskUser",categories="Programming Functions;Input/Output;Built In"
*CMD AskUser --- displays an input dialog to the user
*CORE
*CALL
	AskUser(message)

*PARMS

{message} -- a message (either a string or a javax.swing.JCompnent)) that indicates what kind of input to enter

*DESC

This function allows information to be obtained from the user in the
form of a string.  A GUI dialog box will be displayed which the user
can use to enter their input.  If the user selects the cancel button,
the Break() function will be executed.

*SEE TellUser
%/mathpiper_docs
*/

