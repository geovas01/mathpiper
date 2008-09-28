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
import org.mathrider.piper.lisp.Atom;
import org.mathrider.piper.lisp.DefFile;
import org.mathrider.piper.lisp.Environment;
import org.mathrider.piper.lisp.LispError;
import org.mathrider.piper.lisp.Pointer;
import org.mathrider.piper.lisp.Standard;
import org.mathrider.piper.lisp.userfunctions.MultiUserFunction;

/**
 *
 * @author 
 */
public class FindFunction extends BuiltinFunction
{

    public void eval(Environment aEnvironment, int aStackTop) throws Exception
    {
        LispError.CHK_CORE(aEnvironment, aStackTop, aEnvironment.iSecure == false, LispError.KLispErrSecurityBreach);

        Pointer evaluated = new Pointer();
        evaluated.set(ARGUMENT(aEnvironment, aStackTop, 1).get());

        // Get file name
        LispError.CHK_ARG_CORE(aEnvironment, aStackTop, evaluated.get() != null, 1);
        String orig = evaluated.get().string();
        LispError.CHK_ARG_CORE(aEnvironment, aStackTop, orig != null, 1);
        String oper = Standard.internalUnstringify(orig);

        MultiUserFunction multiUserFunc =
                aEnvironment.multiUserFunction(aEnvironment.hashTable().lookUp(oper));
        if (multiUserFunc != null)
        {
            DefFile def = multiUserFunc.iFileToOpen;
            if (def != null)
            {
                RESULT(aEnvironment, aStackTop).set(Atom.getInstance(aEnvironment, def.iFileName));
                return;
            }
        }
        RESULT(aEnvironment, aStackTop).set(Atom.getInstance(aEnvironment, "\"\""));
    }
}
