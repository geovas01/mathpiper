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
import org.mathrider.piper.lisp.UtilityFunctions;

/**
 *
 *  
 */
public class Subst extends BuiltinFunction
{

    public void eval(Environment aEnvironment, int aStackTop) throws Exception
    {
        ConsPointer from = new ConsPointer(), to = new ConsPointer(), body = new ConsPointer();
        from.set(argumentPointer(aEnvironment, aStackTop, 1).get());
        to.set(argumentPointer(aEnvironment, aStackTop, 2).get());
        body.set(argumentPointer(aEnvironment, aStackTop, 3).get());
        org.mathrider.piper.lisp.behaviours.Subst behaviour = new org.mathrider.piper.lisp.behaviours.Subst(aEnvironment, from, to);
        UtilityFunctions.internalSubstitute(result(aEnvironment, aStackTop), body, behaviour);
    }
}
