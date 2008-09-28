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

/**
 *
 * @author 
 */
public class Divide extends BuiltinFunction
{

    public void eval(Environment aEnvironment, int aStackTop) throws Exception
    {
        BigNumber x = org.mathrider.piper.builtin.Functions.getNumber(aEnvironment, aStackTop, 1);
        BigNumber y = org.mathrider.piper.builtin.Functions.getNumber(aEnvironment, aStackTop, 2);
        BigNumber z = new BigNumber(aEnvironment.precision());
        // if both arguments are integers, then BigNumber::Divide would perform an integer divide, but we want a float divide here.
        if (x.IsInt() && y.IsInt())
        {
            // why can't we just say BigNumber temp; ?
            BigNumber tempx = new BigNumber(aEnvironment.precision());
            tempx.SetTo(x);
            tempx.BecomeFloat(aEnvironment.precision());  // coerce x to float

            BigNumber tempy = new BigNumber(aEnvironment.precision());
            tempy.SetTo(y);
            tempy.BecomeFloat(aEnvironment.precision());  // coerce x to float

            z.Divide(tempx, tempy, aEnvironment.precision());
        } else
        {
            z.Divide(x, y, aEnvironment.precision());
        }
        RESULT(aEnvironment, aStackTop).set(new org.mathrider.piper.lisp.Number(z));
        return;
    }
}
