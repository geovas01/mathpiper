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
import org.mathrider.piper.lisp.Environment;
import org.mathrider.piper.lisp.Iterator;
import org.mathrider.piper.lisp.LispError;

/**
 *
 * @author 
 */
public class ConcatenateStrings extends BuiltinFunction
{

    void ConcatenateStrings(StringBuffer aStringBuffer, Environment aEnvironment, int aStackTop) throws Exception
    {
        aStringBuffer.append('\"');
        int arg = 1;

        Iterator iter = new Iterator(ARGUMENT(aEnvironment, aStackTop, 1).get().subList());
        iter.GoNext();
        while (iter.GetObject() != null)
        {
            LispError.CHK_ISSTRING_CORE(aEnvironment, aStackTop, iter.Ptr(), arg);
            String thisString = iter.GetObject().string();
            String toAppend = thisString.substring(1, thisString.length() - 1);
            aStringBuffer.append(toAppend);
            iter.GoNext();
            arg++;
        }
        aStringBuffer.append('\"');
    }

    public void eval(Environment aEnvironment, int aStackTop) throws Exception
    {
        StringBuffer strBuffer = new StringBuffer("");
        ConcatenateStrings(strBuffer, aEnvironment, aStackTop);
        RESULT(aEnvironment, aStackTop).set(Atom.getInstance(aEnvironment, strBuffer.toString()));
    }
}
