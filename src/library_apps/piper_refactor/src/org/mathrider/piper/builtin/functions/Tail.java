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
import org.mathrider.piper.lisp.Environment;
import org.mathrider.piper.lisp.ConsPointer;
import org.mathrider.piper.lisp.Standard;

/**
 *
 * @author 
 */
public class Tail extends BuiltinFunction
{

    public void eval(Environment aEnvironment, int aStackTop) throws Exception
    {
        ConsPointer first = new ConsPointer();
        Standard.internalTail(first, argument(aEnvironment, aStackTop, 1));
        Standard.internalTail(result(aEnvironment, aStackTop), first);
        ConsPointer head = new ConsPointer();
        head.set(aEnvironment.iList.copy(false));
        head.get().cdr().set(result(aEnvironment, aStackTop).get().subList().get());
        result(aEnvironment, aStackTop).get().subList().set(head.get());
    }
}
