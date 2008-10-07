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

package org.mathpiper.lisp;

/** 
 * Implements a stack of pointers to CONS that can be used to pass
 * arguments to functions, and receive results back.
 */
public class ArgumentStack
{

    ConsPointerArray iArgumentStack;
    int iStackTop;

    //TODO appropriate constructor?
    public ArgumentStack(int aStackSize)
    {
        iArgumentStack = new ConsPointerArray(aStackSize, null);
        iStackTop = 0;
    //printf("STACKSIZE %d\n",aStackSize);
    }

    public int getStackTop()
    {
        return iStackTop;
    }

    public void raiseStackOverflowError() throws Exception
    {
        LispError.raiseError("Argument stack reached maximum. Please extend argument stack with --stack argument on the command line.");
    }

    public void pushArgumentOnStack(Cons aCons) throws Exception
    {
        if (iStackTop >= iArgumentStack.size())
        {
            raiseStackOverflowError();
        }
        iArgumentStack.setElement(iStackTop, aCons);
        iStackTop++;
    }

    public void pushNulls(int aNr) throws Exception
    {
        if (iStackTop + aNr > iArgumentStack.size())
        {
            raiseStackOverflowError();
        }
        iStackTop += aNr;
    }

    public ConsPointer getElement(int aPos) throws Exception
    {
        LispError.lispAssert(aPos >= 0 && aPos < iStackTop);
        return iArgumentStack.getElement(aPos);
    }

    public void popTo(int aTop) throws Exception
    {
        LispError.lispAssert(aTop <= iStackTop);
        while (iStackTop > aTop)
        {
            iStackTop--;
            iArgumentStack.setElement(iStackTop, null);
        }
    }
};
