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
import org.mathrider.piper.lisp.LispError;
import org.mathrider.piper.lisp.ConsPointer;

/**
 *
 * @author 
 */
public class Fac extends BuiltinFunction
{

    public void eval(Environment aEnvironment, int aStackTop) throws Exception
    {
        LispError.CHK_ARG_CORE(aEnvironment, aStackTop, ARGUMENT(aEnvironment, aStackTop, 1).get().number(0) != null, 1);
        ConsPointer arg = ARGUMENT(aEnvironment, aStackTop, 1);

        //TODO fixme I am sure this can be optimized still
        int nr = (int) arg.get().number(0).Long();
        LispError.Check(nr >= 0, LispError.KLispErrInvalidArg);
        BigNumber fac = new BigNumber("1", 10, 10);
        int i;
        for (i = 2; i <= nr; i++)
        {
            BigNumber m = new BigNumber("" + i, 10, 10);
            m.Multiply(fac, m, 0);
            fac = m;
        }
        RESULT(aEnvironment, aStackTop).set(new org.mathrider.piper.lisp.Number(fac));
    }
}
