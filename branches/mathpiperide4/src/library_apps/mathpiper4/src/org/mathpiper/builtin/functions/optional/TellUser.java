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
import org.mathpiper.lisp.Environment;
import org.mathpiper.lisp.LispError;
import org.mathpiper.lisp.Utility;

/**
 *
 *
 */
public class TellUser extends BuiltinFunction {

    public void plugIn(Environment aEnvironment) throws Throwable {
        this.functionName = "TellUser";
        aEnvironment.getBuiltinFunctions().put(
                this.functionName, new BuiltinFunctionEvaluator(this, 1, BuiltinFunctionEvaluator.FixedNumberOfArguments | BuiltinFunctionEvaluator.EvaluateArguments));
    }//end method.

    public void evaluate(Environment aEnvironment, int aStackTop) throws Throwable {
        if (getArgument(aEnvironment, aStackTop, 1) == null) {
            LispError.checkArgument(aEnvironment, aStackTop, 1);
        }

        Object argument = getArgument(aEnvironment, aStackTop, 1).car();

        if (! (argument instanceof String)) {
            LispError.raiseError("The argument to TellUser must be a string.", aStackTop, aEnvironment);
        }

        String messageString = (String) argument;

        if (messageString == null) {
            LispError.checkArgument(aEnvironment, aStackTop, 1);
        }

        messageString = Utility.stripEndQuotesIfPresent(messageString);
        
        final String messageStringFinal = messageString;
        
        final AtomicReference<String> userInput = new AtomicReference<String>();
        
        userInput.set("CONFIRMATION_NOT_RECEIVED");
        
        SwingUtilities.invokeLater(new Runnable(){
            @Override
            public void run() {
            	JOptionPane.showMessageDialog(null, messageStringFinal, "Message from MathPiper", JOptionPane.INFORMATION_MESSAGE);
            	
            	userInput.set("CONFIRMATION_RECEIVED");
            }
        });
        
        
        while(userInput.get() != null && ((String)userInput.get()).equals("CONFIRMATION_NOT_RECEIVED"))
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

        setTopOfStack(aEnvironment, aStackTop, Utility.getTrueAtom(aEnvironment));
    }//end method.
}//end class.



/*
%mathpiper_docs,name="TellUser",categories="Programming Functions;Input/Output;Built In"
*CMD AskUser --- displays a message to the user in a dialog.
*CORE
*CALL
	TellUser(message)

*PARMS

{message} -- a message to display to the user

*DESC

This function allows a message to be displayed to the user.  The message will be
displayed in a GUI dialog box.

*SEE AskUser
%/mathpiper_docs
*/
