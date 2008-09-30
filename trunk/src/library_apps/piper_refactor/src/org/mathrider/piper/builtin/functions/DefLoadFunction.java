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
import org.mathrider.piper.lisp.DefFile;
import org.mathrider.piper.lisp.Environment;
import org.mathrider.piper.lisp.LispError;
import org.mathrider.piper.lisp.ConsPointer;
import org.mathrider.piper.lisp.UtilityFunctions;
import org.mathrider.piper.lisp.userfunctions.MultiUserFunction;

/**
 *
 *  
 */
public class DefLoadFunction extends BuiltinFunction
{

    public void eval(Environment aEnvironment, int aStackTop) throws Exception
    {
        ConsPointer name = new ConsPointer();
        name.set(argumentPointer(aEnvironment, aStackTop, 1).get());
        String orig = name.get().string();
        LispError.checkArgumentCore(aEnvironment, aStackTop, orig != null, 1);
        String oper = UtilityFunctions.internalUnstringify(orig);

        MultiUserFunction multiUserFunc =
                aEnvironment.multiUserFunction(aEnvironment.getTokenHash().lookUp(oper));
        if (multiUserFunc != null)
        {
            if (multiUserFunc.iFileToOpen != null)
            {
                DefFile def = multiUserFunc.iFileToOpen;
                if (!def.iIsLoaded)
                {
                    multiUserFunc.iFileToOpen = null;
                    UtilityFunctions.internalUse(aEnvironment, def.iFileName);
                }
            }
        }
        UtilityFunctions.internalTrue(aEnvironment, result(aEnvironment, aStackTop));
    }
}
