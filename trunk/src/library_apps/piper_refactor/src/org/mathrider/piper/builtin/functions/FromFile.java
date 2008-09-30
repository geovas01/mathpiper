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
package org.mathrider.piper.builtin.functions;

import org.mathrider.piper.builtin.BuiltinFunction;
import org.mathrider.piper.io.InputStatus;
import org.mathrider.piper.lisp.Environment;
import org.mathrider.piper.lisp.Input;
import org.mathrider.piper.lisp.LispError;
import org.mathrider.piper.lisp.ConsPointer;
import org.mathrider.piper.lisp.UtilityFunctions;

/**
 *
 * 
 */
public class FromFile extends BuiltinFunction
{

    public void eval(Environment aEnvironment, int aStackTop) throws Exception
    {
        LispError.checkCore(aEnvironment, aStackTop, aEnvironment.iSecure == false, LispError.KLispErrSecurityBreach);
        ConsPointer evaluated = new ConsPointer();
        aEnvironment.iEvaluator.evaluate(aEnvironment, evaluated, argumentPointer(aEnvironment, aStackTop, 1));

        // Get file name
        LispError.checkArgumentCore(aEnvironment, aStackTop, evaluated.get() != null, 1);
        String orig = evaluated.get().string();
        LispError.checkArgumentCore(aEnvironment, aStackTop, orig != null, 1);

        String hashedname = aEnvironment.getTokenHash().lookUpUnStringify(orig);

        InputStatus oldstatus = aEnvironment.iInputStatus;
        Input previous = aEnvironment.iCurrentInput;
        try
        {
            aEnvironment.iInputStatus.setTo(hashedname);
            Input input = // new StdFileInput(hashedname, aEnvironment.iInputStatus);
                    UtilityFunctions.openInputFile(aEnvironment, aEnvironment.iInputDirectories, hashedname, aEnvironment.iInputStatus);
            aEnvironment.iCurrentInput = input;
            // Open file
            LispError.checkCore(aEnvironment, aStackTop, input != null, LispError.KLispErrFileNotFound);

            // Evaluate the body
            aEnvironment.iEvaluator.evaluate(aEnvironment, result(aEnvironment, aStackTop), argumentPointer(aEnvironment, aStackTop, 2));
        } catch (Exception e)
        {
            throw e;
        } finally
        {
            aEnvironment.iCurrentInput = previous;
            aEnvironment.iInputStatus.restoreFrom(oldstatus);
        }
    //Return the result
    }
}
