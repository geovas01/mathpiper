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
import org.mathrider.piper.lisp.Cons;
import org.mathrider.piper.lisp.Environment;
import org.mathrider.piper.lisp.LispError;
import org.mathrider.piper.lisp.Utility;

/**
 *
 *  
 */
public class UnList extends BuiltinFunction
{

    public void eval(Environment aEnvironment, int aStackTop) throws Exception
    {
        LispError.checkArgumentCore(aEnvironment, aStackTop, argumentPointer(aEnvironment, aStackTop, 1).get() != null, 1);
        LispError.checkArgumentCore(aEnvironment, aStackTop, argumentPointer(aEnvironment, aStackTop, 1).get().subList() != null, 1);
        Cons subList = argumentPointer(aEnvironment, aStackTop, 1).get().subList().get();
        LispError.checkArgumentCore(aEnvironment, aStackTop, subList != null, 1);
        LispError.checkArgumentCore(aEnvironment, aStackTop, subList.string() == aEnvironment.iList.string(), 1);
        Utility.internalTail(result(aEnvironment, aStackTop), argumentPointer(aEnvironment, aStackTop, 1));
    }
}
