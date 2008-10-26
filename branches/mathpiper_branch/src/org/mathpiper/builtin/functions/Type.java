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
import org.mathpiper.lisp.Atom;
import org.mathpiper.lisp.Cons;
import org.mathpiper.lisp.Environment;
import org.mathpiper.lisp.ConsPointer;

/**
 *
 *  
 */
public class Type extends BuiltinFunction
{

    public void eval(Environment aEnvironment, int aStackTop) throws Exception
    {
        ConsPointer evaluated = new ConsPointer();
        evaluated.setCons(argumentPointer(aEnvironment, aStackTop, 1).getCons());
        ConsPointer subList = evaluated.getCons().subList();
        Cons head = null;
        if (subList == null)
        {
            result(aEnvironment, aStackTop).setCons(Atom.getInstance(aEnvironment, "\"\""));
            return;
        }
        head = subList.getCons();
        if (head.string() == null)
        {
            result(aEnvironment, aStackTop).setCons(Atom.getInstance(aEnvironment, "\"\""));
            return;
        }
        result(aEnvironment, aStackTop).setCons(Atom.getInstance(aEnvironment, aEnvironment.getTokenHash().lookUpStringify(head.string())));
        return;
    }
}
