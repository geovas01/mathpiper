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
package org.mathpiper.builtin.functions;

import org.mathpiper.builtin.BuiltinFunction;
import org.mathpiper.lisp.Environment;
import org.mathpiper.lisp.LispError;
import org.mathpiper.lisp.ConsPointer;
import org.mathpiper.lisp.SubList;

/**
 *
 *  
 */
public class Listify extends BuiltinFunction
{

    public void eval(Environment aEnvironment, int aStackTop) throws Exception
    {
        LispError.checkArgumentCore(aEnvironment, aStackTop, argumentPointer(aEnvironment, aStackTop, 1).getCons().subList() != null, 1);
        ConsPointer head = new ConsPointer();
        head.setCons(aEnvironment.iListAtom.copy(false));
        head.getCons().cdr().setCons(argumentPointer(aEnvironment, aStackTop, 1).getCons().subList().getCons());
        result(aEnvironment, aStackTop).setCons(SubList.getInstance(head.getCons()));
    }
}
