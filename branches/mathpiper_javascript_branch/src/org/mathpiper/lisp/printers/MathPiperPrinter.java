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
import org.mathpiper.lisp.cons.ConsPointer;
import org.mathpiper.lisp.LispError;
import org.mathpiper.lisp.cons.ConsTraverser;
import org.mathpiper.lisp.Environment;
import org.mathpiper.lisp.tokenizers.MathPiperTokenizer;
import org.mathpiper.lisp.Operator;
import org.mathpiper.lisp.collections.OperatorMap;

public class MathPiperPrinter extends LispPrinter {

    StringBuilder spaces = new StringBuilder();
    public static int KMaxPrecedence = 60000;
    OperatorMap iPrefixOperators;
    OperatorMap iInfixOperators;
    OperatorMap iPostfixOperators;
    OperatorMap iBodiedOperators;
    char iPrevLastChar;
    Environment iCurrentEnvironment;

    String infixSpaces;
    String bracketSpaces;

    //private List<Cons> visitedLists = new ArrayList<Cons>();

    public MathPiperPrinter(OperatorMap aPrefixOperators,
            OperatorMap aInfixOperators,
            OperatorMap aPostfixOperators,
            OperatorMap aBodiedOperators,
            boolean pretty) {

        super(pretty);
        
        iPrefixOperators = aPrefixOperators;
        iInfixOperators = aInfixOperators;
        iPostfixOperators = aPostfixOperators;
        iBodiedOperators = aBodiedOperators;
        iPrevLastChar = 0;



        if (pretty) {
            infixSpaces = "  ";
            bracketSpaces = "    ";
        } else {
            infixSpaces = " ";
            bracketSpaces = "";
        }

    }


    @Override
    public void print(int aStackTop, ConsPointer aExpression, MathPiperOutputStream aOutput, Environment aEnvironment) throws Exception {
        iCurrentEnvironment = aEnvironment;


        Print(aEnvironment, aStackTop, aExpression, aOutput, KMaxPrecedence);

        //visitedLists.clear();
    }


    @Override
    public void rememberLastChar(char aChar) {
        iPrevLastChar = aChar;
    }


    void Print(Environment aEnvironment, int aStackTop, ConsPointer aExpression, MathPiperOutputStream aOutput, int iPrecedence) throws Exception {

        LispError.lispAssert(aExpression.getCons() != null, aEnvironment, aStackTop);

        String atom;
        if (aExpression.car() instanceof String) {
            atom = (String) aExpression.car();
            boolean bracket = false;
            if (iPrecedence < KMaxPrecedence
                    && atom.charAt(0) == '-'
                    && (MathPiperTokenizer.isDigit(atom.charAt(1)) || atom.charAt(1) == '.')) {
                //Code for (-1)/2 .
                bracket = true;
            }
            if (bracket) {
                writeToken(aOutput, "(");
            }

            if (atom.startsWith("\"")) //String atom.
            {
                if (!pretty) {
                    if (atom.contains("\n")) {
                        int xx = 1;
                    }
                    atom = atom.replace("\\", "\\\\");
                    atom = atom.replace("\"", "\\\"");
                    atom = atom.replace("\n", "\\n");
                }
            }

            writeToken(aOutput, atom);


            if (bracket) {
                writeToken(aOutput, ")");
            }
            return;
        }

        if (aExpression.car() instanceof BuiltinContainer) {
            //TODO display genericclass
            writeToken(aOutput, ((BuiltinContainer) aExpression.car()).getObject().getClass().toString());
            return;
        }

        ConsPointer subList = (ConsPointer) aExpression.car();

        LispError.check(aEnvironment, aStackTop, subList != null, LispError.UNPRINTABLE_TOKEN, "INTERNAL");

        if (subList.getCons() == null) {
            writeToken(aOutput, "(" + spaceCharacter + ")");
        } else {
            int length = Utility.listLength(aEnvironment, aStackTop, subList);
            atom = (String) subList.car();
            Operator prefix = (Operator) iPrefixOperators.lookUp(atom);
            Operator infix = (Operator) iInfixOperators.lookUp(atom);
            Operator postfix = (Operator) iPostfixOperators.lookUp(atom);
            Operator bodied = (Operator) iBodiedOperators.lookUp(atom);
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
                ConsPointer left = null;
                ConsPointer right = null;

                if (prefix != null) {
                    right = subList.cdr();
                } else if (infix != null) {
                    left = subList.cdr();
                    right = subList.cdr().cdr();
                } else if (postfix != null) {
                    left = subList.cdr();
                }

                if (iPrecedence < operator.iPrecedence) {
                    writeToken(aOutput, "(");
                } else {
                    //Vladimir?    aOutput.write(" ");
                }

                if (left != null) {

                    writeToken(aOutput, "(");

                    Print(aEnvironment, aStackTop, left, aOutput, operator.iLeftPrecedence);

                    writeToken(aOutput, ")");
                    
                }

                boolean addSpaceAroundInfixOperator =
                        left != null && right != null && // is this really an infix operator?
                        atom.length() > 1;// no spaces around +,-,*,/ etc

                if (addSpaceAroundInfixOperator) {
                    writeToken(aOutput, infixSpaces);
                }

                writeToken(aOutput, atom);

                if (addSpaceAroundInfixOperator) {
                    writeToken(aOutput, infixSpaces);
                }

                if (right != null) {

                    if (atom.equals("/") && Utility.functionType(right).equals("/")) {
                        //Code for In> Hold((3/2)/(1/2)) Result> (3/2)/(1/2) .
                        writeToken(aOutput, "(");
                    }//end if.

                    if (atom.equals("Not")) {//Todo:tk:perhaps a more general way should be found to place a space after a prefix operator.
                        writeToken(aOutput, " ");
                    }//end if.

                    if (atom.equals("-")) {//Todo:tk:perhaps a more general way should be found to do this.
                        writeToken(aOutput, " ");
                    }//end if.

                    if (atom.equals("`")) {
                        writeToken(aOutput, "(");
                    }

                    Print(aEnvironment, aStackTop, right, aOutput, operator.iRightPrecedence);

                    if (atom.equals("/") && Utility.functionType(right).equals("/")) {
                        //Code for In> Hold((3/2)/(1/2)) Result> (3/2)/(1/2) .
                        writeToken(aOutput, ")");
                    }//end if.

                    if (atom.equals("`")) {
                        writeToken(aOutput, ")");
                    }
                }//end if.

                if (iPrecedence < operator.iPrecedence) {
                    writeToken(aOutput, ")");
                }

            } else {

                ConsTraverser consTraverser = new ConsTraverser(aEnvironment, subList.cdr());

                /*
                Removing complex number output notation formatting until the problem with Solve(x^3 - 2*x - 7 == 0,x) is resolved.
                
                if (functionOrOperatorName == iCurrentEnvironment.iComplexAtom.car()) {

                Print(consTraverser.getPointer(), aOutput, KMaxPrecedence);

                consTraverser.goNext(); //Point to second argument.

                if (!consTraverser.car().toString().startsWith("-")) {
                writeToken(aOutput, "+");
                }

                Print(consTraverser.getPointer(), aOutput, KMaxPrecedence);

                writeToken(aOutput, "*I");

                } else */
                if (atom == iCurrentEnvironment.iListAtom.car()) {

                    /*
                    Cons atomCons = (Cons) subList.getCons();
                    if (visitedLists.contains(atomCons)) {
                    writeToken(aOutput, "{CYCLE_LIST}");
                    return;

                    } else {

                    visitedLists.add(atomCons);*/

                    writeToken(aOutput, "{");

                    while (consTraverser.getCons() != null) {
                        Print(aEnvironment, aStackTop, consTraverser.getPointer(), aOutput, KMaxPrecedence);
                        consTraverser.goNext(aStackTop);
                        if (consTraverser.getCons() != null) {
                            writeToken(aOutput, ",");
                        }
                    }//end while.

                    writeToken(aOutput, "}");

                    // }//end else.
                } else if (atom == iCurrentEnvironment.iProgAtom.car()) // Program block brackets.
                {
                    writeToken(aOutput, "[");
                    aOutput.write(newLineCharacter);
                    spaces.append(bracketSpaces);

                    while (consTraverser.getCons() != null) {
                        aOutput.write(spaces.toString());
                        Print(aEnvironment, aStackTop, consTraverser.getPointer(), aOutput, KMaxPrecedence);
                        consTraverser.goNext(aStackTop);
                        writeToken(aOutput, ";");
                        aOutput.write(newLineCharacter);
                    }

                    writeToken(aOutput, "]");
                    aOutput.write(newLineCharacter);
                    spaces.delete(0, spaces.length());
                } else if (atom == iCurrentEnvironment.iNthAtom.car()) {
                    Print(aEnvironment, aStackTop, consTraverser.getPointer(), aOutput, 0);
                    consTraverser.goNext(aStackTop);
                    writeToken(aOutput, "[");
                    Print(aEnvironment, aStackTop, consTraverser.getPointer(), aOutput, KMaxPrecedence);
                    writeToken(aOutput, "]");
                } else {
                    boolean bracket = false;
                    if (bodied != null) {
                        //printf("%d > %d\n",iPrecedence, bodied.iPrecedence);
                        if (iPrecedence < bodied.iPrecedence) {
                            bracket = true;
                        }
                    }
                    if (bracket) {
                        writeToken(aOutput, "(");
                    }
                    if (atom != null) {
                        writeToken(aOutput, atom); //Print function name.
                    } else {
                        Print(aEnvironment, aStackTop, subList, aOutput, 0);
                    }
                    writeToken(aOutput, "("); //Print the opening parenthese of the function argument list.

                    ConsTraverser counter = new ConsTraverser(aEnvironment, consTraverser.getPointer());
                    int nr = 0;

                    while (counter.getCons() != null) { //Count arguments.
                        counter.goNext(aStackTop);
                        nr++;
                    }

                    if (bodied != null) {
                        nr--;
                    }
                    while (nr-- != 0) {
                        Print(aEnvironment, aStackTop, consTraverser.getPointer(), aOutput, KMaxPrecedence); //Print argument.

                        consTraverser.goNext(aStackTop);

                        if (nr != 0) {
                            writeToken(aOutput, ","); //Print the comma which is between arguments.
                        }
                    }//end while.

                    writeToken(aOutput, ")");

                    if (consTraverser.getCons() != null) {
                        Print(aEnvironment, aStackTop, consTraverser.getPointer(), aOutput, bodied.iPrecedence);
                    }

                    if (bracket) {
                        writeToken(aOutput, ")"); //Print the closing parenthese of the function argument list.
                    }
                }
            }
        }//end sublist if.
    }


    void writeToken(MathPiperOutputStream aOutput, String aString) throws Exception {
        /*if (MathPiperTokenizer.isAlNum(iPrevLastChar) && (MathPiperTokenizer.isAlNum(aString.charAt(0)) || aString.charAt(0)=='_'))
        {
        aOutput.write(" ");
        }
        else if (MathPiperTokenizer.isSymbolic(iPrevLastChar) && MathPiperTokenizer.isSymbolic(aString.charAt(0)))
        {
        aOutput.write(" ");
        }*/
        aOutput.write(aString);

        if (aString.length() != 0) {
            rememberLastChar(aString.charAt(aString.length() - 1));
        }
    }

}
