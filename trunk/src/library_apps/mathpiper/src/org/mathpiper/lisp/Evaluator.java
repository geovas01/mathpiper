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

// class EvalFuncBase defines the interface to 'something that can

import org.mathpiper.io.MathPiperOutputStream;
import org.mathpiper.io.StringOutputStream;
import org.mathpiper.lisp.cons.ConsPointer;
import org.mathpiper.lisp.Environment;
import org.mathpiper.lisp.printers.MathPiperPrinter;
import org.mathpiper.lisp.stacks.UserStackInformation;

// evaluate'
public abstract class Evaluator
{
    public static boolean DEBUG = false;
    public static boolean TRACE_TO_STANDARD_OUT = false;
    public static boolean VERBOSE_DEBUG = false;
    private static int evalDepth = 0;
    public static boolean iTraced = false;

    public static void showExpression(StringBuffer outString, Environment aEnvironment, ConsPointer aExpression) throws Exception {
        MathPiperPrinter infixprinter = new MathPiperPrinter(aEnvironment.iPrefixOperators, aEnvironment.iInfixOperators, aEnvironment.iPostfixOperators, aEnvironment.iBodiedOperators);
        // Print out the current expression
        //StringOutput stream(outString);
        MathPiperOutputStream stream = new StringOutputStream(outString);
        infixprinter.print(aExpression, stream, aEnvironment);
        // Escape quotes.
        for (int i = outString.length() - 1; i >= 0; --i) {
            char c = outString.charAt(i);
            if (c == '\"') {
                //outString.insert(i, '\\');
                outString.deleteCharAt(i);
            }
        }
    }

    public static void traceShowArg(Environment aEnvironment, ConsPointer aParam, ConsPointer aValue) throws Exception {
        for (int i = 0; i < evalDepth; i++) {
            //aEnvironment.iEvalDepth; i++) {
            if (TRACE_TO_STANDARD_OUT) {
                System.out.print("    ");
            } else {
                aEnvironment.write("    ");
            }
        }
        if (TRACE_TO_STANDARD_OUT) {
            System.out.print("Arg(");
        } else {
            aEnvironment.write("Arg(");
        }
        traceShowExpression(aEnvironment, aParam);
        if (TRACE_TO_STANDARD_OUT) {
            System.out.print(",");
        } else {
            aEnvironment.write(",");
        }
        traceShowExpression(aEnvironment, aValue);
        if (TRACE_TO_STANDARD_OUT) {
            System.out.print(");\n");
        } else {
            aEnvironment.write(");\n");
        }
    }

    public static void traceShowEnter(Environment aEnvironment, ConsPointer aExpression) throws Exception {
        for (int i = 0; i < evalDepth; i++) {
            // aEnvironment.iEvalDepth; i++) {
            if (TRACE_TO_STANDARD_OUT) {
                System.out.print("    ");
            } else {
                aEnvironment.write("    ");
            }
        }
        if (TRACE_TO_STANDARD_OUT) {
            System.out.print("Enter{(");
        } else {
            aEnvironment.write("Enter{(");
        }
        {
            String function = "";
            if (aExpression.getCons().getSublistPointer() != null) {
                ConsPointer sub = aExpression.getCons().getSublistPointer();
                if (sub.getCons().string() != null) {
                    function = sub.getCons().string();
                }
            }
            if (TRACE_TO_STANDARD_OUT) {
                System.out.print(function);
            } else {
                aEnvironment.write(function);
            }
        }
        if (TRACE_TO_STANDARD_OUT) {
            System.out.print(",");
        } else {
            aEnvironment.write(",");
        }
        traceShowExpression(aEnvironment, aExpression);
        if (TRACE_TO_STANDARD_OUT) {
            System.out.print(",");
        } else {
            aEnvironment.write(",");
        }
        if (DEBUG) {
            if (TRACE_TO_STANDARD_OUT) {
                System.out.print(",");
            } else {
                aEnvironment.write(",");
            }
        } else {
            if (TRACE_TO_STANDARD_OUT) {
                System.out.print("");
            } else {
                aEnvironment.write("");
            }
            if (TRACE_TO_STANDARD_OUT) {
                System.out.print(",");
            } else {
                aEnvironment.write(",");
            }
            if (TRACE_TO_STANDARD_OUT) {
                System.out.print("0");
            } else {
                aEnvironment.write("0");
            }
        }
        if (TRACE_TO_STANDARD_OUT) {
            System.out.print(");\n");
        } else {
            aEnvironment.write(");\n");
        }
        evalDepth++;
    }

    public static void traceShowExpression(Environment aEnvironment, ConsPointer aExpression) throws Exception {
        StringBuffer outString = new StringBuffer();
        showExpression(outString, aEnvironment, aExpression);
        if (TRACE_TO_STANDARD_OUT) {
            System.out.print(outString.toString());
        } else {
            aEnvironment.write(outString.toString());
        }
    }

    public static void traceShowLeave(Environment aEnvironment, ConsPointer aResult, ConsPointer aExpression) throws Exception {
        evalDepth--;
        for (int i = 0; i < evalDepth; i++) {
            // aEnvironment.iEvalDepth; i++) {
            if (TRACE_TO_STANDARD_OUT) {
                System.out.print("    ");
            } else {
                aEnvironment.write("    ");
            }
        }
        if (TRACE_TO_STANDARD_OUT) {
            System.out.print("Leave}(");
        } else {
            aEnvironment.write("Leave}(");
        }
        traceShowExpression(aEnvironment, aExpression);
        if (TRACE_TO_STANDARD_OUT) {
            System.out.print(",");
        } else {
            aEnvironment.write(",");
        }
        traceShowExpression(aEnvironment, aResult);
        if (TRACE_TO_STANDARD_OUT) {
            System.out.print(");\n");
        } else {
            aEnvironment.write(");\n");
        }
    }

    public static boolean isTraced() {
        return iTraced;
    }

    public static void traceOff() {
        iTraced = false;
    }

    public static void traceOn() {
        iTraced = true;
    }
    UserStackInformation iBasicInfo = new UserStackInformation();

	public abstract void evaluate(Environment aEnvironment,ConsPointer aResult, ConsPointer aArgumentsOrExpression) throws Exception;

        public void resetStack()
    {
    }

    public UserStackInformation stackInformation()
    {
        return iBasicInfo;
    }

    public void showStack(Environment aEnvironment, MathPiperOutputStream aOutput)
    {
    }
    
};
