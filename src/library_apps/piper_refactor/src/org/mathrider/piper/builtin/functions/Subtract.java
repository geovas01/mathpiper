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

import org.mathrider.piper.builtin.BigNumber;
import org.mathrider.piper.builtin.BuiltinFunction;
import org.mathrider.piper.lisp.Environment;
import org.mathrider.piper.lisp.Standard;

/**
 *
 * @author 
 */
public class Subtract extends BuiltinFunction
{

    public void eval(Environment aEnvironment, int aStackTop) throws Exception
    {
        int length = Standard.internalListLength(argument(aEnvironment, aStackTop, 0));
        if (length == 2)
        {
            BigNumber x = org.mathrider.piper.builtin.Functions.getNumber(aEnvironment, aStackTop, 1);
            BigNumber z = new BigNumber(x);
            z.Negate(x);
            result(aEnvironment, aStackTop).set(new org.mathrider.piper.lisp.Number(z));
            return;
        } else
        {
            BigNumber x = org.mathrider.piper.builtin.Functions.getNumber(aEnvironment, aStackTop, 1);
            BigNumber y = org.mathrider.piper.builtin.Functions.getNumber(aEnvironment, aStackTop, 2);
            BigNumber yneg = new BigNumber(y);
            yneg.Negate(y);
            BigNumber z = new BigNumber(aEnvironment.precision());
            z.Add(x, yneg, aEnvironment.precision());
            result(aEnvironment, aStackTop).set(new org.mathrider.piper.lisp.Number(z));
            return;
        }
    }
}
