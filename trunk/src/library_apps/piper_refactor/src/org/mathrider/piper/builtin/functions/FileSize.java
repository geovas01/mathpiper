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
import org.mathrider.piper.lisp.Atom;
import org.mathrider.piper.lisp.Environment;
import org.mathrider.piper.lisp.Input;
import org.mathrider.piper.lisp.LispError;
import org.mathrider.piper.lisp.ConsPointer;
import org.mathrider.piper.lisp.UtilityFunctions;

/**
 *
 *  
 */
public class FileSize extends BuiltinFunction
{

    public void eval(Environment aEnvironment, int aStackTop) throws Exception
    {
        ConsPointer fnameObject = new ConsPointer();
        fnameObject.set(argumentPointer(aEnvironment, aStackTop, 1).get());
        LispError.checkIsStringCore(aEnvironment, aStackTop, fnameObject, 1);
        String fname = UtilityFunctions.internalUnstringify(fnameObject.get().string());
        String hashedname = aEnvironment.getTokenHash().lookUp(fname);

        long fileSize = 0;
        InputStatus oldstatus = new InputStatus(aEnvironment.iInputStatus);
        aEnvironment.iInputStatus.setTo(hashedname);
        try
        {
            // Open file
            Input newInput = // new StdFileInput(hashedname, aEnvironment.iInputStatus);
                    UtilityFunctions.openInputFile(aEnvironment, aEnvironment.iInputDirectories, hashedname, aEnvironment.iInputStatus);

            LispError.check(newInput != null, LispError.KLispErrFileNotFound);
            fileSize = newInput.startPtr().length();
        } catch (Exception e)
        {
            throw e;
        } finally
        {
            aEnvironment.iInputStatus.restoreFrom(oldstatus);
        }
        result(aEnvironment, aStackTop).set(Atom.getInstance(aEnvironment, "" + fileSize));
    }
}
