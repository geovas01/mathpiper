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
public class FastIsPrime extends BuiltinFunction
{

    public void eval(Environment aEnvironment, int aStackTop) throws Exception
    {
        //TODO fixme this routine should actually be called SlowIsPrime ;-)
        BigNumber x = org.mathrider.piper.builtin.Functions.getNumber(aEnvironment, aStackTop, 1);
        long n = x.Long();
        long result = 1;

        // We only want people to pass in small integers
        if (n > 65538)
        {
            result = 0;
        }
        int i = 2;
        int max = (int) (1 + Math.sqrt(n));
        //System.out.println("n = "+n+" max = "+max);
        while (i <= max && result == 1)
        {
            //System.out.println(""+n+"%"+i+" = "+(n%i));
            if ((n % i) == 0)
            {
                result = 0;
            }
            i++;
        }

        BigNumber z = new BigNumber(aEnvironment.precision());
        z.SetTo(result);
        RESULT(aEnvironment, aStackTop).set(new org.mathrider.piper.lisp.Number(z));
    }
}
