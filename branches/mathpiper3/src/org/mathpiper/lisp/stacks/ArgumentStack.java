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
package org.mathpiper.lisp.stacks;

import org.mathpiper.lisp.*;
import org.mathpiper.lisp.cons.ConsArray;

import org.mathpiper.lisp.cons.Cons;

/** 
 * Implements a stack of pointers to CONS that can be used to pass
 * arguments to functions, and receive results back.
 */
public class ArgumentStack {

    ConsArray iArgumentStack;
    int iStackTopIndex;

    //TODO appropriate constructor?
    public ArgumentStack(Environment aEnvironment, int aStackSize) {
        iArgumentStack = new ConsArray(aEnvironment, aStackSize);
        iStackTopIndex = 0;
        //printf("STACKSIZE %d\n",aStackSize);
    }

    public int getStackTopIndex() {
        return iStackTopIndex;
    }

    public void raiseStackOverflowError(Environment aEnvironment) throws Exception {
        LispError.raiseError("Argument stack reached maximum. Please extend argument stack with --stack argument on the command line.", aEnvironment);
    }

    public void pushArgumentOnStack(Cons aCons, Environment aEnvironment) throws Exception {
        if (iStackTopIndex >= iArgumentStack.size()) {
            raiseStackOverflowError(aEnvironment);
        }
        iArgumentStack.setElement(iStackTopIndex, aCons);
        iStackTopIndex++;
    }

    public void pushNulls(int aNr, Environment aEnvironment) throws Exception {
        if (iStackTopIndex + aNr > iArgumentStack.size()) {
            raiseStackOverflowError(aEnvironment);
        }
        iStackTopIndex += aNr;
    }

    public Cons getElement(int aPos, Environment aEnvironment) throws Exception {
        if(aPos < 0 || aPos >= iStackTopIndex) 
        {
            LispError.lispAssert(aEnvironment);
        }
        return iArgumentStack.getElement(aPos);
    }

    public void setElement(int aPos, Environment aEnvironment, Cons cons) throws Exception {
        if(aPos < 0 || aPos >= iStackTopIndex) LispError.lispAssert(aEnvironment);
        iArgumentStack.setElement(aPos, cons);
    }

    public void popTo(int aTop, Environment aEnvironment) throws Exception {
        if(aTop > iStackTopIndex) LispError.lispAssert(aEnvironment);
        while (iStackTopIndex > aTop) {
            iStackTopIndex--;
            iArgumentStack.setElement(iStackTopIndex, null);
        }
    }

    public void reset(Environment aEnvironment) throws Exception {
        this.popTo(0, aEnvironment);
    }//end method.

    public String dump(Environment aEnvironment) throws Exception {

        StringBuilder stringBuilder = new StringBuilder();

        int functionBaseIndex = 0;

        int functionPositionIndex = 0;

        int stackTop = aEnvironment.iArgumentStack.getStackTopIndex();


        while (functionBaseIndex <= stackTop) {

            if(functionBaseIndex == 0)
            {
                stringBuilder.append("\n\n========================================= Start Of Built In Function Stack Trace\n");
            }
            else
            {
                stringBuilder.append("-----------------------------------------\n");
            }

            Cons argumentCons = getElement(functionBaseIndex, aEnvironment);

            int argumentCount = Utility.listLength(aEnvironment, argumentCons);

            Cons  cons = argumentCons;

            stringBuilder.append(functionPositionIndex++ + ": ");
            stringBuilder.append(Utility.printMathPiperExpression(cons, aEnvironment, -1));
            stringBuilder.append("\n");

            cons = cons.cdr();

            while(cons != null)
            {
                stringBuilder.append("   " + functionPositionIndex++ + ": ");
                stringBuilder.append("-> " + Utility.printMathPiperExpression(cons, aEnvironment, -1));
                stringBuilder.append("\n");
                
                cons = cons.cdr();
            }


            functionBaseIndex = functionBaseIndex + argumentCount;

        }//end while.

        stringBuilder.append("========================================= End Of Built In Function Stack Trace\n\n");

        return stringBuilder.toString();
        
    }//end method.


}//end class.

