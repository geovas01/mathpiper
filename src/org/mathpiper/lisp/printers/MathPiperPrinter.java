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
package org.mathpiper.lisp.printers;

import org.mathpiper.builtin.BuiltinContainer;
import org.mathpiper.io.MathPiperOutputStream;
import org.mathpiper.lisp.Utility;

import org.mathpiper.lisp.LispError;
import org.mathpiper.lisp.Environment;
import org.mathpiper.lisp.tokenizers.MathPiperTokenizer;
import org.mathpiper.lisp.Operator;
import org.mathpiper.lisp.collections.OperatorMap;
import org.mathpiper.lisp.cons.Cons;

public class MathPiperPrinter extends LispPrinter {

    StringBuilder spaces = new StringBuilder();
    public static int KMaxPrecedence = 60000;
    OperatorMap iPrefixOperators;
    OperatorMap iInfixOperators;
    OperatorMap iPostfixOperators;
    OperatorMap iBodiedOperators;
    char iPrevLastChar;
    Environment iCurrentEnvironment;

    //private List<Cons> visitedLists = new ArrayList<Cons>();
    public MathPiperPrinter(OperatorMap aPrefixOperators,
            OperatorMap aInfixOperators,
            OperatorMap aPostfixOperators,
            OperatorMap aBodiedOperators) {
        iPrefixOperators = aPrefixOperators;
        iInfixOperators = aInfixOperators;
        iPostfixOperators = aPostfixOperators;
        iBodiedOperators = aBodiedOperators;
        iPrevLastChar = 0;
    }

    @Override
    public void print(Cons aExpression,  MathPiperOutputStream aOutput, Environment aEnvironment) throws Exception {
        iCurrentEnvironment = aEnvironment;


        Print(aEnvironment, aExpression, aOutput, KMaxPrecedence);

        //visitedLists.clear();
    }

    @Override
    public void rememberLastChar(char aChar) {
        iPrevLastChar = aChar;
    }

    void Print(Environment aEnvironment, Cons aExpression, MathPiperOutputStream aOutput, int iPrecedence) throws Exception {

        if(aExpression == null) 
        {
            LispError.lispAssert(aEnvironment);
        }

        String functionOrOperatorName;
        if (aExpression.car() instanceof String) {
            functionOrOperatorName = (String) aExpression.car();
            boolean bracket = false;
            if (iPrecedence < KMaxPrecedence &&
                    functionOrOperatorName.charAt(0) == '-' &&
                    (MathPiperTokenizer.isDigit(functionOrOperatorName.charAt(1)) || functionOrOperatorName.charAt(1) == '.')) {
                //Code for (-1)/2 .
                bracket = true;
            }
            if (bracket) {
                WriteToken(aOutput, "(");
            }
            WriteToken(aOutput, functionOrOperatorName);
            if (bracket) {
                WriteToken(aOutput, ")");
            }
            return;
        }

        if (aExpression.car() instanceof BuiltinContainer) {
            //TODO display genericclass
            WriteToken(aOutput, ((BuiltinContainer) aExpression.car()).getObject().getClass().toString());
            return;
        }

        Cons subList = (Cons) aExpression.car();

        if(subList == null) LispError.throwError(aEnvironment, "");

        if (subList == null) {
            WriteToken(aOutput, "( )");
        } else {
            int length = Utility.listLength(aEnvironment, subList);
            functionOrOperatorName = (String) subList.car();
            Operator prefix = (Operator) iPrefixOperators.lookUp(functionOrOperatorName);
            Operator infix = (Operator) iInfixOperators.lookUp(functionOrOperatorName);
            Operator postfix = (Operator) iPostfixOperators.lookUp(functionOrOperatorName);
            Operator bodied = (Operator) iBodiedOperators.lookUp(functionOrOperatorName);
            Operator operator = null;

            if (length != 2) {
                prefix = null;
                postfix = null;
            }
            if (length != 3) {
                infix = null;
            }
            if (prefix != null) {
                operator = prefix;
            }
            if (postfix != null) {
                operator = postfix;
            }
            if (infix != null) {
                operator = infix;
            }

            if (operator != null) {
                Cons left = null;
                Cons right = null;

                if (prefix != null) {
                    right = subList.cdr();
                } else if (infix != null) {
                    left = subList.cdr();
                    right = subList.cdr().cdr();
                } else if (postfix != null) {
                    left = subList.cdr();
                }

                if (iPrecedence < operator.iPrecedence) {
                    WriteToken(aOutput, "(");
                } else {
                    //Vladimir?    aOutput.write(" ");
                }

                if (left != null) {

                    if (functionOrOperatorName.equals("/") && Utility.functionType(left).equals("/")) {
                        //Code for In> Hold((3/2)/(1/2)) Result> (3/2)/(1/2) .
                        WriteToken(aOutput, "(");
                    }//end if.

                    Print(aEnvironment, left, aOutput, operator.iLeftPrecedence);

                    if (functionOrOperatorName.equals("/") && Utility.functionType(left).equals("/")) {
                        //Code for In> Hold((3/2)/(1/2)) Result> (3/2)/(1/2) .
                        WriteToken(aOutput, ")");
                    }//end if.
                }

                boolean addSpaceAroundInfixOperator = false; //Todo:tk:perhaps a more general way should be found to place a space around operators which are words.
                if(functionOrOperatorName.equals("And?") || functionOrOperatorName.equals("Or?"))
                {
                    addSpaceAroundInfixOperator = true;
                }

                if (addSpaceAroundInfixOperator == true) {
                    WriteToken(aOutput, " ");
                }//end if.

                WriteToken(aOutput, functionOrOperatorName);

                if (addSpaceAroundInfixOperator == true) {
                    WriteToken(aOutput, " ");
                }//end if.

                if (right != null) {

                    if (functionOrOperatorName.equals("/") && Utility.functionType(right).equals("/")) {
                        //Code for In> Hold((3/2)/(1/2)) Result> (3/2)/(1/2) .
                        WriteToken(aOutput, "(");
                    }//end if.

                    if (functionOrOperatorName.equals("Not?")) {//Todo:tk:perhaps a more general way should be found to place a space after a prefix operator.
                        WriteToken(aOutput, " ");
                    }//end if.

                    Print(aEnvironment, right, aOutput, operator.iRightPrecedence);

                    if (functionOrOperatorName.equals("/") && Utility.functionType(right).equals("/")) {
                        //Code for In> Hold((3/2)/(1/2)) Result> (3/2)/(1/2) .
                        WriteToken(aOutput, ")");
                    }//end if.
                }

                if (iPrecedence < operator.iPrecedence) {
                    WriteToken(aOutput, ")");
                }

            } else {

                Cons  consTraverser =  subList.cdr();

               /*
                   Removing complex number output notation formatting until the problem with Solve(x^3 - 2*x - 7 == 0,x) is resolved.

                   if (functionOrOperatorName == iCurrentEnvironment.iComplexAtom.car()) {

                    Print(consTraverser.getPointer(), aOutput, KMaxPrecedence);

                    consTraverser.goNext(); //Point to second argument.

                    if (!consTraverser.car().toString().startsWith("-")) {
                        WriteToken(aOutput, "+");
                    }

                    Print(consTraverser.getPointer(), aOutput, KMaxPrecedence);

                    WriteToken(aOutput, "*I");

                } else */
                if (functionOrOperatorName.equals(iCurrentEnvironment.iListAtom.car())) {

                    /*
                    Cons atomCons = (Cons) subList.getCons();
                    if (visitedLists.contains(atomCons)) {
                    WriteToken(aOutput, "{CYCLE_LIST}");
                    return;

                    } else {

                    visitedLists.add(atomCons);*/

                    WriteToken(aOutput, "{");

                    while (consTraverser != null) {
                        Print(aEnvironment, consTraverser, aOutput, KMaxPrecedence);
                        consTraverser = consTraverser.cdr();
                        if (consTraverser != null) {
                            WriteToken(aOutput, ",");
                        }
                    }//end while.

                    WriteToken(aOutput, "}");

                    // }//end else.
                } else if (functionOrOperatorName.equals(iCurrentEnvironment.iProgAtom)) // Program block brackets.
                {
                    aOutput.write("\n" + spaces.toString());

                    WriteToken(aOutput, "[");
                    aOutput.write("\n");
                    spaces.append("    ");

                    while (consTraverser != null) {
                        aOutput.write(spaces.toString());
                        Print(aEnvironment, consTraverser, aOutput, KMaxPrecedence);
                        consTraverser = consTraverser.cdr();
                        WriteToken(aOutput, ";");
                        aOutput.write("\n");
                    }

                    spaces.delete(0, 4);
                    aOutput.write(spaces.toString());
                    WriteToken(aOutput, "]");
                    //aOutput.write("\n");

                } else if (functionOrOperatorName.equals(iCurrentEnvironment.iNthAtom)) {
                    Print(aEnvironment, consTraverser, aOutput, 0);
                    consTraverser = consTraverser.cdr();
                    WriteToken(aOutput, "[");
                    Print(aEnvironment, consTraverser,  aOutput, KMaxPrecedence);
                    WriteToken(aOutput, "]");
                } else {
                    boolean bracket = false;
                    if (bodied != null) {
                        //printf("%d > %d\n",iPrecedence, bodied.iPrecedence);
                        if (iPrecedence < bodied.iPrecedence) {
                            bracket = true;
                        }
                    }
                    if (bracket) {
                        WriteToken(aOutput, "(");
                    }
                    if (functionOrOperatorName != null) {
                        WriteToken(aOutput, functionOrOperatorName); //Print function name.
                    } else {
                        Print(aEnvironment, subList, aOutput, 0);
                    }
                    WriteToken(aOutput, "("); //Print the opening parenthese of the function argument list.

                    Cons counter = consTraverser;
                    int nr = 0;

                    while (counter != null) { //Count arguments.
                        counter = counter.cdr();
                        nr++;
                    }

                    if (bodied != null) {
                        nr--;
                    }
                    while (nr-- != 0) {
                        Print(aEnvironment, consTraverser, aOutput, KMaxPrecedence); //Print argument.

                        consTraverser = consTraverser.cdr();

                        if (nr != 0) {
                            WriteToken(aOutput, ","); //Print the comma which is between arguments.
                        }
                    }//end while.

                    WriteToken(aOutput, ")");

                    if (consTraverser != null) {
                        Print(aEnvironment, consTraverser, aOutput, bodied.iPrecedence);
                    }

                    if (bracket) {
                        WriteToken(aOutput, ")"); //Print the closing parenthese of the function argument list.
                    }
                }
            }
        }//end sublist if.
    }

    void WriteToken(MathPiperOutputStream aOutput, String aString) throws Exception {
        /*if (MathPiperTokenizer.isAlNum(iPrevLastChar) && (MathPiperTokenizer.isAlNum(aString.charAt(0)) || aString.charAt(0)=='_'))
        {
        aOutput.write(" ");
        }
        else if (MathPiperTokenizer.isSymbolic(iPrevLastChar) && MathPiperTokenizer.isSymbolic(aString.charAt(0)))
        {
        aOutput.write(" ");
        }*/
        aOutput.write(aString);
        rememberLastChar(aString.charAt(aString.length() - 1));
    }
}