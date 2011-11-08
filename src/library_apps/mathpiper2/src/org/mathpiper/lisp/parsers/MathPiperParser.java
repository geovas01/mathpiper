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
package org.mathpiper.lisp.parsers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import org.mathpiper.lisp.printers.MathPiperPrinter;

import org.mathpiper.lisp.Utility;
import org.mathpiper.lisp.cons.ConsPointer;
import org.mathpiper.lisp.LispError;
import org.mathpiper.lisp.cons.AtomCons;
import org.mathpiper.lisp.tokenizers.MathPiperTokenizer;
import org.mathpiper.io.MathPiperInputStream;
import org.mathpiper.lisp.Environment;
import org.mathpiper.lisp.cons.SublistCons;
import org.mathpiper.lisp.Operator;
import org.mathpiper.lisp.collections.OperatorMap;
import org.mathpiper.lisp.cons.Cons;

public class MathPiperParser extends Parser
{

    public OperatorMap iPrefixOperators;
    public OperatorMap iInfixOperators;
    public OperatorMap iPostfixOperators;
    public OperatorMap iBodiedOperators;
    //private Environment iEnvironment;
    
    boolean iError;
    boolean iEndOfFile;
    String[] iLookAhead;
    public Cons parsedExpression;;
    private String locateFunctionOrOperatorName = null;
    private ArrayList<Map> functionOrOperatorLocationsList;

    public MathPiperParser(MathPiperTokenizer aTokenizer,
            MathPiperInputStream aInput,
            Environment aEnvironment,
            OperatorMap aPrefixOperators,
            OperatorMap aInfixOperators,
            OperatorMap aPostfixOperators,
            OperatorMap aBodiedOperators)
    {
        super(aTokenizer, aInput, aEnvironment);
        iPrefixOperators = aPrefixOperators;
        iInfixOperators = aInfixOperators;
        iPostfixOperators = aPostfixOperators;
        iBodiedOperators = aBodiedOperators;
        iEnvironment = aEnvironment;

        iError = false;
        iEndOfFile = false;
        iLookAhead = new String[4];
    }


    public ArrayList parseAndFind(int aStackTop, ConsPointer aResult, String functionOrOperatorName) throws Exception
    {

        locateFunctionOrOperatorName = functionOrOperatorName;

        functionOrOperatorLocationsList = new ArrayList();

        aResult.setCons(parse(aStackTop));

        return functionOrOperatorLocationsList;
    }


    public Cons parse(int aStackTop) throws Exception
    {
        readToken(aStackTop); //The character is placed into lookAhead.

        if (iEndOfFile)
        {
            parsedExpression = iEnvironment.iEndOfFileAtom.copy( iEnvironment, true);
            return parsedExpression;
        }

        readExpression(iEnvironment,aStackTop, MathPiperPrinter.KMaxPrecedence);  // least precedence

        if (iLookAhead[0] != iEnvironment.iEndStatementAtom.car())
        {
            fail(aStackTop);
        }


        //todo:tk:is this iError code needed?
        if (iError)
        {
            while (iLookAhead[0].length() > 0 && iLookAhead[0] != iEnvironment.iEndStatementAtom.car())
            {
                readToken(aStackTop);
            }
        }

        if (iError)
        {
            parsedExpression = null;
        }




        if(iError) LispError.throwError(iEnvironment, aStackTop, LispError.INVALID_EXPRESSION, "","INTERNAL");

        return parsedExpression;
    }

    void readToken(int aStackTop) throws Exception
    {
        // Get token.

        iLookAhead[0] = iTokenizer.nextToken(iEnvironment, aStackTop, iInput, iEnvironment.getTokenHash());

        /*
        if(iEnvironment.saveDebugInformation)
        {
             System.out.println(iLookAhead[0] + " " + iInput.iStatus.getLineNumber() + " " + (iInput.iStatus.getLineIndex() - iLookAhead[0].length()) + "," + iInput.iStatus.getLineIndex());
        }
        */
        

        iLookAhead[1] = iInput.iStatus.getLineNumber() + "";
        iLookAhead[3] = iInput.iStatus.getLineIndex() + "";
        iLookAhead[2] = (iInput.iStatus.getLineIndex() - iLookAhead[0].length()) + "";

        if (iLookAhead[0].length() == 0)
        {
            iEndOfFile = true;
        }
    }

    void matchToken(int aStackTop, String aToken) throws Exception
    {
        if (!aToken.equals(iLookAhead[0]))
        {
            fail(aStackTop);
        }
        readToken(aStackTop);
    }

    void readExpression(Environment aEnvironment,int aStackTop, int depth) throws Exception
    {
        readAtom(aEnvironment, aStackTop);

        //This code is used by AnalyzeScripts to locate where a given function or operator us located in the scripts.
        if(locateFunctionOrOperatorName != null && locateFunctionOrOperatorName.equals(iLookAhead[0]))
        {
            Map locationInformation = new HashMap();

            locationInformation.put("operatorOrFunctionName", iLookAhead[0]);
            locationInformation.put("lineNumber", (iInput.iStatus.getLineNumber()));
            locationInformation.put("lineIndex", (iInput.iStatus.getLineIndex()));
            functionOrOperatorLocationsList.add(locationInformation);
        }


        for (;;)
        {
            //Handle special case: a[b]. a is matched with lowest precedence!!
            if (iLookAhead[0] == iEnvironment.iProgOpenAtom.car())
            {
                // Match opening bracket
                matchToken(aStackTop, iLookAhead[0]);
                // Read "index" argument
                readExpression(aEnvironment, aStackTop, MathPiperPrinter.KMaxPrecedence);
                // Match closing bracket
                if (iLookAhead[0] != iEnvironment.iProgCloseAtom.car())
                {
                    LispError.raiseError("Expected a ***( ] )*** close bracket token for program block but found ***( " + iLookAhead[0] + " )*** instead.", "[INTERNAL]", Integer.parseInt(iLookAhead[1]), Integer.parseInt(iLookAhead[2]), Integer.parseInt(iLookAhead[3]), aStackTop, aEnvironment);
                    return;
                }
                matchToken(aStackTop, iLookAhead[0]);
                // Build into Ntn(...)
                String theOperator = (String) iEnvironment.iNthAtom.car();
                insertAtom(aEnvironment, aStackTop, theOperator);
                combine(aEnvironment,aStackTop, 2);
            } else
            {
                Operator op = (Operator) iInfixOperators.lookUp(iLookAhead[0]);
                if (op == null)
                {
                    //printf("op [%s]\n",iLookAhead[0].String());
                    if(iLookAhead[0].equals(""))
                    {

                       LispError.raiseError("Expression must end with a semi-colon ***( ; )*** ", "[INTERNAL]", Integer.parseInt(iLookAhead[1]), Integer.parseInt(iLookAhead[2]), Integer.parseInt(iLookAhead[3]), aStackTop, aEnvironment);
                        return;
                    }
                    if (MathPiperTokenizer.isSymbolic(iLookAhead[0].charAt(0)))
                    {
                        int origlen = iLookAhead[0].length();
                        int len = origlen;
                        //printf("IsSymbolic, len=%d\n",len);

                        while (len > 1)
                        {
                            len--;
                            String lookUp =
                                    (String) iEnvironment.getTokenHash().lookUp(iLookAhead[0].substring(0, len));

                            //printf("trunc %s\n",lookUp.String());
                            op = (Operator) iInfixOperators.lookUp(lookUp);
                            //if (op) printf("FOUND\n");
                            if (op != null)
                            {
                                String toLookUp = iLookAhead[0].substring(len, origlen);
                                String lookUpRight =
                                       (String) iEnvironment.getTokenHash().lookUp(toLookUp);

                                //printf("right: %s (%d)\n",lookUpRight.String(),origlen-len);

                                if (iPrefixOperators.lookUp(lookUpRight) != null)
                                {
                                    //printf("ACCEPT %s\n",lookUp.String());
                                    iLookAhead[0] = lookUp;
                                    MathPiperInputStream input = iInput;
                                    int newPos = input.position() - (origlen - len);
                                    input.setPosition(newPos);
                                    //printf("Pushhback %s\n",&input.startPtr()[input.position()]);
                                    break;
                                } else
                                {
                                    op = null;
                                }
                            }
                        }
                        if (op == null)
                        {
                            return;
                        }
                    } else
                    {
                        return;
                    }

                }
                if (depth < op.iPrecedence)
                {
                    return;
                }
                int upper = op.iPrecedence;
                if (op.iRightAssociative == 0)
                {
                    upper--;
                }
                getOtherSide(aEnvironment,aStackTop, 2, upper);
            }
        }
    }

    void readAtom(Environment aEnvironment, int aStackTop) throws Exception
    {
        Operator op;
        // parse prefix operators
        op = (Operator) iPrefixOperators.lookUp(iLookAhead[0]);
        if (op != null)
        {
            String[] theOperator = new String[4];

            System.arraycopy(iLookAhead, 0, theOperator, 0, iLookAhead.length);

            matchToken(aStackTop, iLookAhead[0]);
            {
                readExpression(aEnvironment,aStackTop, op.iPrecedence);
                insertAtom(aEnvironment, aStackTop, theOperator);
                combine(aEnvironment,aStackTop, 1);
            }
        } // Else parse brackets.
        else if (iLookAhead[0] == iEnvironment.iBracketOpenAtom.car())
        {
            matchToken(aStackTop, iLookAhead[0]);
            readExpression(aEnvironment,aStackTop, MathPiperPrinter.KMaxPrecedence);  // least precedence
            matchToken( aStackTop, (String) iEnvironment.iBracketCloseAtom.car());
        } //parse lists
        else if (iLookAhead[0] == iEnvironment.iListOpenAtom.car())
        {
            int nrargs = 0;
            matchToken(aStackTop, iLookAhead[0]);
            while (iLookAhead[0] != iEnvironment.iListCloseAtom.car())
            {
                readExpression(aEnvironment,aStackTop, MathPiperPrinter.KMaxPrecedence);  // least precedence
                nrargs++;

                if (iLookAhead[0] == iEnvironment.iCommaAtom.car())
                {
                    matchToken(aStackTop, iLookAhead[0]);
                } else if (iLookAhead[0] != iEnvironment.iListCloseAtom.car())
                {
                    LispError.raiseError("Expected a ***( } )*** close bracket token for a list but found ***( " + iLookAhead[0] + " )*** instead.", "[INTERNAL]", Integer.parseInt(iLookAhead[1]), Integer.parseInt(iLookAhead[2]), Integer.parseInt(iLookAhead[3]), aStackTop, aEnvironment);
                    return;
                }
            }
            matchToken(aStackTop, iLookAhead[0]);
            String theOperator = (String) iEnvironment.iListAtom.car();
            insertAtom(aEnvironment, aStackTop, theOperator);
            combine(aEnvironment, aStackTop, nrargs);

        } // parse prog bodies
        else if (iLookAhead[0] == iEnvironment.iProgOpenAtom.car())
        {
            int nrargs = 0;

            matchToken(aStackTop, iLookAhead[0]);
            while (iLookAhead[0] != iEnvironment.iProgCloseAtom.car())
            {
                readExpression(aEnvironment,aStackTop, MathPiperPrinter.KMaxPrecedence);  // least precedence
                nrargs++;

                if (iLookAhead[0] == iEnvironment.iEndStatementAtom.car())
                {
                    matchToken(aStackTop, iLookAhead[0]);
                } else
                {
                    LispError.raiseError("Expected a ***( ; )*** end of statement token in program block but found ***( " + iLookAhead[0] + " )*** instead.", "[INTERNAL]", Integer.parseInt(iLookAhead[1]), Integer.parseInt(iLookAhead[2]), Integer.parseInt(iLookAhead[3]), aStackTop, aEnvironment);
                    return;
                }
            }
            matchToken(aStackTop, iLookAhead[0]);
            String theOperator = (String) iEnvironment.iProgAtom.car();
            insertAtom(aEnvironment, aStackTop, theOperator);

            combine(aEnvironment, aStackTop, nrargs);
        } // Else we have an atom.
        else
        {
            String[] theOperator = new String[4];

            System.arraycopy(iLookAhead, 0, theOperator, 0, iLookAhead.length);

            matchToken(aStackTop, iLookAhead[0]);

            int nrargs = -1;
            if (iLookAhead[0] == iEnvironment.iBracketOpenAtom.car())
            {
                nrargs = 0;
                matchToken(aStackTop, iLookAhead[0]);
                while (iLookAhead[0] != iEnvironment.iBracketCloseAtom.car())
                {
                    readExpression(aEnvironment,aStackTop, MathPiperPrinter.KMaxPrecedence);  // least precedence
                    nrargs++;

                    if (iLookAhead[0] == iEnvironment.iCommaAtom.car())
                    {
                        matchToken(aStackTop, iLookAhead[0]);
                    } else if (iLookAhead[0] != iEnvironment.iBracketCloseAtom.car())
                    {
                        LispError.raiseError("Expected a ***( ) )*** close parentheses token for sub-expression but found ***( " + iLookAhead[0] + " )*** instead. ", "[INTERNAL]", Integer.parseInt(iLookAhead[1]), Integer.parseInt(iLookAhead[2]), Integer.parseInt(iLookAhead[3]), aStackTop, aEnvironment);
                        return;
                    }
                }
                matchToken(aStackTop, iLookAhead[0]);

                op = (Operator) iBodiedOperators.lookUp(theOperator[0]);
                if (op != null)
                {
                    readExpression(aEnvironment,aStackTop, op.iPrecedence); // MathPiperPrinter.KMaxPrecedence
                    nrargs++;
                }
            }//end if.

            insertAtom(aEnvironment, aStackTop, theOperator);

            if (nrargs >= 0)
            {
                combine(aEnvironment, aStackTop, nrargs);
            }
        }

        // parse postfix operators

        while ((op = (Operator) iPostfixOperators.lookUp(iLookAhead[0])) != null)
        {
            insertAtom(aEnvironment, aStackTop, iLookAhead[0]);
            matchToken(aStackTop, iLookAhead[0]);
            combine(aEnvironment,aStackTop, 1);
        }
    }

    void getOtherSide(Environment aEnvironment, int aStackTop, int aNrArgsToCombine, int depth) throws Exception
    {
        String theOperator = iLookAhead[0];
        matchToken(aStackTop, iLookAhead[0]);
        readExpression(aEnvironment, aStackTop,  depth);
        insertAtom(aEnvironment, aStackTop, theOperator);
        combine(aEnvironment, aStackTop, aNrArgsToCombine);
    }

    void combine(Environment aEnvironment, int aStackTop, int aNrArgsToCombine) throws Exception
    {
        ConsPointer subList = new ConsPointer();
        subList.setCons(SublistCons.getInstance(aEnvironment,parsedExpression));
        Cons consTraverser =  parsedExpression;
        int i;
        for (i = 0; i < aNrArgsToCombine; i++)
        {
            if (consTraverser == null)
            {
                fail(aStackTop);
                return;
            }
            consTraverser = consTraverser.cdr();
        }
        if (consTraverser == null)
        {
            fail(aStackTop);
            return;
        }
        subList.getCons().setCdr(consTraverser.cdr());
        consTraverser.setCdr(null);

       

        Cons result = Utility.reverseList(aEnvironment,
                new ConsPointer(((Cons) subList.car()).cdr()));
        ((Cons) subList.car()).setCdr(result);

        parsedExpression = subList.getCons();
    }

    void insertAtom(Environment aEnvironment, int aStackTop, String aString) throws Exception
    {
        String[] string = new String[4];
        string[0] = aString;
        insertAtom(aEnvironment, aStackTop, string);
    }

    void insertAtom(Environment aEnvironment, int aStackTop, String[] aString) throws Exception
    {

        ConsPointer ptr = new ConsPointer();
        Cons newCons = AtomCons.getInstance(iEnvironment, aStackTop, aString[0]);

        if(aEnvironment.saveDebugInformation == true && aString[1] != null)
        {
            Map metaDataMap = new HashMap();
            metaDataMap.put("lineNumber", Integer.parseInt(aString[1]));
            metaDataMap.put("startIndex", Integer.parseInt(aString[2]));
            metaDataMap.put("endIndex", Integer.parseInt(aString[3]));
            newCons.setMetadataMap(metaDataMap);
        }

        ptr.setCons(newCons);
        ptr.getCons().setCdr(parsedExpression);
        parsedExpression = ptr.getCons();
    }

    void fail(int aStackTop) throws Exception // called when parsing fails, raising an exception
    {
        iError = true;
        if (iLookAhead[0] != null)
        {
            LispError.raiseError("Error parsing expression near token ***( " + iLookAhead[0] + " )***.", "[INTERNAL]", Integer.parseInt(iLookAhead[1]), Integer.parseInt(iLookAhead[2]), Integer.parseInt(iLookAhead[3]), aStackTop, iEnvironment);
        }
        LispError.raiseError("Error parsing expression.", "[INTERNAL]", Integer.parseInt(iLookAhead[1]), Integer.parseInt(iLookAhead[2]), Integer.parseInt(iLookAhead[3]), aStackTop, iEnvironment);
    }
};
