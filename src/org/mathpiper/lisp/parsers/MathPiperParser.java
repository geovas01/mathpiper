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


    public Object[] parseAndFind(int aStackTop, String functionOrOperatorName) throws Exception
    {

        locateFunctionOrOperatorName = functionOrOperatorName;

        functionOrOperatorLocationsList = new ArrayList();

        Object[] result = new Object[2];

        result[0] = parse();

        result[1] = functionOrOperatorLocationsList;
        
        return result;
    }


    public Cons parse() throws Exception
    {
        readToken(); //The character is placed into lookAhead.

        if (iEndOfFile)
        {
            parsedExpression = iEnvironment.iEndOfFileAtom.copy(true);
            return parsedExpression;
        }

        readExpression(iEnvironment, MathPiperPrinter.KMaxPrecedence);  // least precedence

        if (!iLookAhead[0].equals(iEnvironment.iEndStatementAtom))
        {
            fail();
        }


        //todo:tk:is this iError code needed?
        if (iError)
        {
            while (iLookAhead[0].length() > 0 && !iLookAhead[0].equals(iEnvironment.iEndStatementAtom))
            {
                readToken();
            }
        }

        if (iError)
        {
            parsedExpression = null;
        }




        if(iError) LispError.throwError(iEnvironment, "");

        return parsedExpression;
    }

    void readToken() throws Exception
    {
        // Get token.

        iLookAhead[0] = iTokenizer.nextToken(iEnvironment, iInput);


   //if(iEnvironment.saveDebugInformation )System.out.println(iLookAhead[0] + "XX");
        
        if(iEnvironment.saveDebugInformation)
        {        
            iLookAhead[1] = iInput.iStatus.getLineNumber() + "";
            iLookAhead[3] = iInput.iStatus.getLineIndex() + "";
            iLookAhead[2] = (iInput.iStatus.getLineIndex() - iLookAhead[0].length()) + "";
        }
        

        if (iLookAhead[0].length() == 0)
        {
            iEndOfFile = true;
        }
    }

    void matchToken(String aToken) throws Exception
    {
        if (!aToken.equals(iLookAhead[0]))
        {
            fail();
        }
        readToken();
    }

    void readExpression(Environment aEnvironment, int depth) throws Exception
    {
        readAtom(aEnvironment);

        for (;;)
        {
            //Handle special case: a[b]. a is matched with lowest precedence!!
            if (iLookAhead[0].equals(iEnvironment.iProgOpenAtom))
            {
                // Match opening bracket
                matchToken(iLookAhead[0]);
                // Read "index" argument
                readExpression(aEnvironment, MathPiperPrinter.KMaxPrecedence);
                // Match closing bracket
                if (!iLookAhead[0].equals(iEnvironment.iProgCloseAtom))
                {
                    LispError.raiseError("Expected a ***( ] )*** close bracket token for program block but found ***( " + iLookAhead[0] + " )*** instead.", Integer.parseInt(iLookAhead[1]), Integer.parseInt(iLookAhead[2]), Integer.parseInt(iLookAhead[3]), aEnvironment);
                    return;
                }
                matchToken(iLookAhead[0]);
                // Build into Ntn(...)
                String theOperator = (String) iEnvironment.iNthAtom;
                insertAtom(aEnvironment, theOperator);
                combine(aEnvironment, 2);
            } else
            {
                Operator op = (Operator) iInfixOperators.lookUp(iLookAhead[0]);
                if (op == null)
                {
                    //printf("op [%s]\n",iLookAhead[0].String());
                    if(iLookAhead[0].equals(""))
                    {

                       LispError.raiseError("Expression must end with a semi-colon ***( ; )*** ", Integer.parseInt(iLookAhead[1]), Integer.parseInt(iLookAhead[2]), Integer.parseInt(iLookAhead[3]), aEnvironment);
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
                            String lookUp = iLookAhead[0].substring(0, len);

                            //printf("trunc %s\n",lookUp.String());
                            op = (Operator) iInfixOperators.lookUp(lookUp);
                            //if (op) printf("FOUND\n");
                            if (op != null)
                            {
                                String toLookUp = iLookAhead[0].substring(len, origlen);
                                String lookUpRight = toLookUp;

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
                getOtherSide(aEnvironment, 2, upper);
            }
        }
    }

    void readAtom(Environment aEnvironment) throws Exception
    {
        Operator op;
        // parse prefix operators
        op = (Operator) iPrefixOperators.lookUp(iLookAhead[0]);
        if (op != null)
        {
            String[] theOperator = new String[4];

            System.arraycopy(iLookAhead, 0, theOperator, 0, iLookAhead.length);

            matchToken(iLookAhead[0]);
            {
                readExpression(aEnvironment, op.iPrecedence);
                insertAtom(aEnvironment, theOperator);
                combine(aEnvironment, 1);
            }
        } // Else parse brackets.
        else if (iLookAhead[0].equals(iEnvironment.iBracketOpenAtom))
        {
            matchToken(iLookAhead[0]);
            readExpression(aEnvironment, MathPiperPrinter.KMaxPrecedence);  // least precedence
            matchToken( (String) iEnvironment.iBracketCloseAtom);
        } //parse lists
        else if (iLookAhead[0].equals(iEnvironment.iListOpenAtom))
        {
            int nrargs = 0;
            matchToken(iLookAhead[0]);
            while (!iLookAhead[0].equals(iEnvironment.iListCloseAtom))
            {
                readExpression(aEnvironment, MathPiperPrinter.KMaxPrecedence);  // least precedence
                nrargs++;

                if (iLookAhead[0].equals(iEnvironment.iCommaAtom))
                {
                    matchToken(iLookAhead[0]);
                } else if (!iLookAhead[0].equals(iEnvironment.iListCloseAtom))
                {
                    LispError.raiseError("Expected a ***( } )*** close bracket token for a list but found ***( " + iLookAhead[0] + " )*** instead.", Integer.parseInt(iLookAhead[1]), Integer.parseInt(iLookAhead[2]), Integer.parseInt(iLookAhead[3]), aEnvironment);
                    return;
                }
            }
            matchToken(iLookAhead[0]);
            String theOperator = (String) iEnvironment.iListAtom.car();
            insertAtom(aEnvironment, theOperator);
            combine(aEnvironment, nrargs);

        } // parse prog bodies
        else if (iLookAhead[0].equals(iEnvironment.iProgOpenAtom))
        {
            int nrargs = 0;

            matchToken(iLookAhead[0]);
            while (!iLookAhead[0].equals(iEnvironment.iProgCloseAtom))
            {
                readExpression(aEnvironment, MathPiperPrinter.KMaxPrecedence);  // least precedence
                nrargs++;

                if (iLookAhead[0].equals(iEnvironment.iEndStatementAtom))
                {
                    matchToken(iLookAhead[0]);
                } else
                {
                    LispError.raiseError("Expected a ***( ; )*** end of statement token in program block but found ***( " + iLookAhead[0] + " )*** instead.", Integer.parseInt(iLookAhead[1]), Integer.parseInt(iLookAhead[2]), Integer.parseInt(iLookAhead[3]), aEnvironment);
                    return;
                }
            }
            matchToken(iLookAhead[0]);
            String theOperator = (String) iEnvironment.iProgAtom;
            insertAtom(aEnvironment, theOperator);

            combine(aEnvironment, nrargs);
        } // Else we have an atom.
        else
        {
            String[] theOperator = new String[4];

            System.arraycopy(iLookAhead, 0, theOperator, 0, iLookAhead.length);


            //System.out.println(iLookAhead[0] + " " + iInput.iStatus.getLineNumber() + " " + (iInput.iStatus.getLineIndex() - iLookAhead[0].length()) + "," + iInput.iStatus.getLineIndex());

            //This code is used by AnalyzeScripts to locate where a given function or operator us located in the scripts.
            if(locateFunctionOrOperatorName != null && locateFunctionOrOperatorName.equals(iLookAhead[0]))
            {
                Map locationInformation = new HashMap();

                locationInformation.put("operatorOrFunctionName", iLookAhead[0]);
                locationInformation.put("lineNumber", (iInput.iStatus.getLineNumber()));
                locationInformation.put("lineIndex", (iInput.iStatus.getLineIndex()));
                functionOrOperatorLocationsList.add(locationInformation);
            }

            matchToken(iLookAhead[0]);

            int nrargs = -1;
            if (iLookAhead[0].equals(iEnvironment.iBracketOpenAtom))
            {
                nrargs = 0;
                matchToken(iLookAhead[0]);
                while (!iLookAhead[0].equals(iEnvironment.iBracketCloseAtom))
                {
                    readExpression(aEnvironment, MathPiperPrinter.KMaxPrecedence);  // least precedence
                    nrargs++;

                    if (iLookAhead[0].equals(iEnvironment.iCommaAtom))
                    {
                        matchToken(iLookAhead[0]);
                    } else if (!iLookAhead[0].equals(iEnvironment.iBracketCloseAtom))
                    {
                        LispError.raiseError("Expected a ***( ) )*** close parentheses token for sub-expression but found ***( " + iLookAhead[0] + " )*** instead. ", Integer.parseInt(iLookAhead[1]), Integer.parseInt(iLookAhead[2]), Integer.parseInt(iLookAhead[3]), aEnvironment);
                        return;
                    }
                }
                matchToken(iLookAhead[0]);

                op = (Operator) iBodiedOperators.lookUp(theOperator[0]);
                if (op != null)
                {
                    readExpression(aEnvironment, op.iPrecedence); // MathPiperPrinter.KMaxPrecedence
                    nrargs++;
                }
            }//end if.

            insertAtom(aEnvironment, theOperator);

            if (nrargs >= 0)
            {
                combine(aEnvironment, nrargs);
            }
        }//end else.

        
        //This code is used by AnalyzeScripts to locate where a given function or operator us located in the scripts.
        if(locateFunctionOrOperatorName != null && locateFunctionOrOperatorName.equals(iLookAhead[0]))
        {
            Map locationInformation = new HashMap();

            locationInformation.put("operatorOrFunctionName", iLookAhead[0]);
            locationInformation.put("lineNumber", (iInput.iStatus.getLineNumber()));
            locationInformation.put("lineIndex", (iInput.iStatus.getLineIndex()));
            functionOrOperatorLocationsList.add(locationInformation);
        }

        // parse postfix operators
        while ((op = (Operator) iPostfixOperators.lookUp(iLookAhead[0])) != null)
        {
            insertAtom(aEnvironment, iLookAhead[0]);
            matchToken(iLookAhead[0]);
            combine(aEnvironment, 1);
        }
    }

    void getOtherSide(Environment aEnvironment, int aNrArgsToCombine, int depth) throws Exception
    {
        String theOperator = iLookAhead[0];
        matchToken(iLookAhead[0]);
        readExpression(aEnvironment, depth);
        insertAtom(aEnvironment, theOperator);
        combine(aEnvironment, aNrArgsToCombine);
    }

    void combine(Environment aEnvironment, int aNrArgsToCombine) throws Exception
    {
        Cons subList = SublistCons.getInstance(aEnvironment,parsedExpression);
        Cons consTraverser =  parsedExpression;
        int i;
        for (i = 0; i < aNrArgsToCombine; i++)
        {
            if (consTraverser == null)
            {
                fail();
                return;
            }
            consTraverser = consTraverser.cdr();
        }
        if (consTraverser == null)
        {
            fail();
            return;
        }
        subList.setCdr(consTraverser.cdr());
        consTraverser.setCdr(null);

       

        Cons result = Utility.reverseList(aEnvironment,
                ((Cons) subList.car()).cdr());
        ((Cons) subList.car()).setCdr(result);

        parsedExpression = subList;
    }

    void insertAtom(Environment aEnvironment, String aString) throws Exception
    {
        String[] string = new String[4];
        string[0] = aString;
        insertAtom(aEnvironment, string);
    }

    void insertAtom(Environment aEnvironment, String[] aString) throws Exception
    {

        Cons newCons = AtomCons.getInstance(iEnvironment, aString[0]);

        if(aEnvironment.saveDebugInformation == true && aString[1] != null)
        {
            Map metaDataMap = new HashMap();
            metaDataMap.put("lineNumber", Integer.parseInt(aString[1]));
            metaDataMap.put("startIndex", Integer.parseInt(aString[2]));
            metaDataMap.put("endIndex", Integer.parseInt(aString[3]));
            newCons.setMetadataMap(metaDataMap);
        }

        newCons.setCdr(parsedExpression);
        parsedExpression = newCons;
    }

    void fail() throws Exception // called when parsing fails, raising an exception
    {
        iError = true;
        if (iLookAhead[0] != null)
        {
            LispError.raiseError("Error parsing expression near token ***( " + iLookAhead[0] + " )***.", Integer.parseInt(iLookAhead[1]), Integer.parseInt(iLookAhead[2]), Integer.parseInt(iLookAhead[3]), iEnvironment);
        }
        LispError.raiseError("Error parsing expression.", Integer.parseInt(iLookAhead[1]), Integer.parseInt(iLookAhead[2]), Integer.parseInt(iLookAhead[3]), iEnvironment);
    }
};
