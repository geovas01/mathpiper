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

import org.mathpiper.lisp.printers.MathPiperPrinter;

import org.mathpiper.lisp.UtilityFunctions;
import org.mathpiper.lisp.cons.ConsPointer;
import org.mathpiper.lisp.LispError;
import org.mathpiper.lisp.cons.ConsTraverser;
import org.mathpiper.lisp.cons.AtomCons;
import org.mathpiper.lisp.tokenizers.MathPiperTokenizer;
import org.mathpiper.io.MathPiperInputStream;
import org.mathpiper.lisp.Environment;
import org.mathpiper.lisp.cons.SubListCons;
import org.mathpiper.lisp.InfixOperator;
import org.mathpiper.lisp.collections.OperatorMap;

public class MathPiperParser extends Parser
{

    public OperatorMap iPrefixOperators;
    public OperatorMap iInfixOperators;
    public OperatorMap iPostfixOperators;
    public OperatorMap iBodiedOperators;
    
    boolean iError;
    boolean iEndOfFile;
    String iLookAhead;
    public ConsPointer iSExpressionResult = new ConsPointer();

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

        iError = false;
        iEndOfFile = false;
        iLookAhead = null;
    }

    public void parse(ConsPointer aResult) throws Exception
    {
        parse();
        aResult.setCons(iSExpressionResult.getCons());
    }

    public void parse() throws Exception
    {
        readToken();
        if (iEndOfFile)
        {
            iSExpressionResult.setCons(iEnvironment.iEndOfFileAtom.copy(true));
            return;
        }

        readExpression(MathPiperPrinter.KMaxPrecedence);  // least precedence

        if (iLookAhead != iEnvironment.iEndStatementAtom.first())
        {
            fail();
        }
        if (iError)
        {
            while (iLookAhead.length() > 0 && iLookAhead != iEnvironment.iEndStatementAtom.first())
            {
                readToken();
            }
        }

        if (iError)
        {
            iSExpressionResult.setCons(null);
        }
        LispError.check(!iError, LispError.KLispErrInvalidExpression);
    }

    void readToken() throws Exception
    {
        // Get token.
        iLookAhead = iTokenizer.nextToken(iInput,
                iEnvironment.getTokenHash());
        if (iLookAhead.length() == 0)
        {
            iEndOfFile = true;
        }
    }

    void matchToken(String aToken) throws Exception
    {
        if (aToken != iLookAhead)
        {
            fail();
        }
        readToken();
    }

    void readExpression(int depth) throws Exception
    {
        readAtom();

        for (;;)
        {
            //Handle special case: a[b]. a is matched with lowest precedence!!
            if (iLookAhead == iEnvironment.iProgOpenAtom.first())
            {
                // Match opening bracket
                matchToken(iLookAhead);
                // Read "index" argument
                readExpression(MathPiperPrinter.KMaxPrecedence);
                // Match closing bracket
                if (iLookAhead != iEnvironment.iProgCloseAtom.first())
                {
                    LispError.raiseError("Expecting a ] close bracket for program block, but got " + iLookAhead + " instead.",iEnvironment);
                    return;
                }
                matchToken(iLookAhead);
                // Build into Ntn(...)
                String theOperator = (String) iEnvironment.iNthAtom.first();
                insertAtom(theOperator);
                combine(2);
            } else
            {
                InfixOperator op = (InfixOperator) iInfixOperators.lookUp(iLookAhead);
                if (op == null)
                {
                    //printf("op [%s]\n",iLookAhead.String());
                    if(iLookAhead.equals(""))
                    {

                       LispError.raiseError("Expression must end with a semi-colon (;)",iEnvironment);
                        return;
                    }
                    if (MathPiperTokenizer.isSymbolic(iLookAhead.charAt(0)))
                    {
                        int origlen = iLookAhead.length();
                        int len = origlen;
                        //printf("IsSymbolic, len=%d\n",len);

                        while (len > 1)
                        {
                            len--;
                            String lookUp =
                                    (String) iEnvironment.getTokenHash().lookUp(iLookAhead.substring(0, len));

                            //printf("trunc %s\n",lookUp.String());
                            op = (InfixOperator) iInfixOperators.lookUp(lookUp);
                            //if (op) printf("FOUND\n");
                            if (op != null)
                            {
                                String toLookUp = iLookAhead.substring(len, origlen);
                                String lookUpRight =
                                       (String) iEnvironment.getTokenHash().lookUp(toLookUp);

                                //printf("right: %s (%d)\n",lookUpRight.String(),origlen-len);

                                if (iPrefixOperators.lookUp(lookUpRight) != null)
                                {
                                    //printf("ACCEPT %s\n",lookUp.String());
                                    iLookAhead = lookUp;
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




                //              return;
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
                getOtherSide(2, upper);
            }
        }
    }

    void readAtom() throws Exception
    {
        InfixOperator op;
        // parse prefix operators
        op = (InfixOperator) iPrefixOperators.lookUp(iLookAhead);
        if (op != null)
        {
            String theOperator = iLookAhead;
            matchToken(iLookAhead);
            {
                readExpression(op.iPrecedence);
                insertAtom(theOperator);
                combine(1);
            }
        } // Else parse brackets
        else if (iLookAhead == iEnvironment.iBracketOpenAtom.first())
        {
            matchToken(iLookAhead);
            readExpression(MathPiperPrinter.KMaxPrecedence);  // least precedence
            matchToken( (String) iEnvironment.iBracketCloseAtom.first());
        } //parse lists
        else if (iLookAhead == iEnvironment.iListOpenAtom.first())
        {
            int nrargs = 0;
            matchToken(iLookAhead);
            while (iLookAhead != iEnvironment.iListCloseAtom.first())
            {
                readExpression(MathPiperPrinter.KMaxPrecedence);  // least precedence
                nrargs++;

                if (iLookAhead == iEnvironment.iCommaAtom.first())
                {
                    matchToken(iLookAhead);
                } else if (iLookAhead != iEnvironment.iListCloseAtom.first())
                {
                    LispError.raiseError("Expecting a } close bracket for a list, but got " + iLookAhead + " instead.",iEnvironment);
                    return;
                }
            }
            matchToken(iLookAhead);
            String theOperator = (String) iEnvironment.iListAtom.first();
            insertAtom(theOperator);
            combine(nrargs);

        } // parse prog bodies
        else if (iLookAhead == iEnvironment.iProgOpenAtom.first())
        {
            int nrargs = 0;

            matchToken(iLookAhead);
            while (iLookAhead != iEnvironment.iProgCloseAtom.first())
            {
                readExpression(MathPiperPrinter.KMaxPrecedence);  // least precedence
                nrargs++;

                if (iLookAhead == iEnvironment.iEndStatementAtom.first())
                {
                    matchToken(iLookAhead);
                } else
                {
                    LispError.raiseError("Expecting ; end of statement in program block, but got " + iLookAhead + " instead.",iEnvironment);
                    return;
                }
            }
            matchToken(iLookAhead);
            String theOperator = (String) iEnvironment.iProgAtom.first();
            insertAtom(theOperator);

            combine(nrargs);
        } // Else we have an atom.
        else
        {
            String theOperator = iLookAhead;
            matchToken(iLookAhead);

            int nrargs = -1;
            if (iLookAhead == iEnvironment.iBracketOpenAtom.first())
            {
                nrargs = 0;
                matchToken(iLookAhead);
                while (iLookAhead != iEnvironment.iBracketCloseAtom.first())
                {
                    readExpression(MathPiperPrinter.KMaxPrecedence);  // least precedence
                    nrargs++;

                    if (iLookAhead == iEnvironment.iCommaAtom.first())
                    {
                        matchToken(iLookAhead);
                    } else if (iLookAhead != iEnvironment.iBracketCloseAtom.first())
                    {
                        LispError.raiseError("Expecting ) closing bracket for sub-expression, but got " + iLookAhead + " instead.",iEnvironment);
                        return;
                    }
                }
                matchToken(iLookAhead);

                op = (InfixOperator) iBodiedOperators.lookUp(theOperator);
                if (op != null)
                {
                    readExpression(op.iPrecedence); // MathPiperPrinter.KMaxPrecedence
                    nrargs++;
                }
            }
            insertAtom(theOperator);
            if (nrargs >= 0)
            {
                combine(nrargs);
            }
        }

        // parse postfix operators

        while ((op = (InfixOperator) iPostfixOperators.lookUp(iLookAhead)) != null)
        {
            insertAtom(iLookAhead);
            matchToken(iLookAhead);
            combine(1);
        }
    }

    void getOtherSide(int aNrArgsToCombine, int depth) throws Exception
    {
        String theOperator = iLookAhead;
        matchToken(iLookAhead);
        readExpression(depth);
        insertAtom(theOperator);
        combine(aNrArgsToCombine);
    }

    void combine(int aNrArgsToCombine) throws Exception
    {
        ConsPointer subList = new ConsPointer();
        subList.setCons(SubListCons.getInstance(iSExpressionResult.getCons()));
        ConsTraverser consTraverser = new ConsTraverser(iSExpressionResult);
        int i;
        for (i = 0; i < aNrArgsToCombine; i++)
        {
            if (consTraverser.getCons() == null)
            {
                fail();
                return;
            }
            consTraverser.goNext();
        }
        if (consTraverser.getCons() == null)
        {
            fail();
            return;
        }
        subList.getCons().getRestPointer().setCons(consTraverser.getCons().getRestPointer().getCons());
        consTraverser.getCons().getRestPointer().setCons(null);

        UtilityFunctions.internalReverseList(((ConsPointer) subList.getCons().first()).getCons().getRestPointer(),
                ((ConsPointer) subList.getCons().first()).getCons().getRestPointer());
        iSExpressionResult.setCons(subList.getCons());
    }

    void insertAtom(String aString) throws Exception
    {
        ConsPointer ptr = new ConsPointer();
        ptr.setCons(AtomCons.getInstance(iEnvironment, aString));
        ptr.getCons().getRestPointer().setCons(iSExpressionResult.getCons());
        iSExpressionResult.setCons(ptr.getCons());
    }

    void fail() throws Exception // called when parsing fails, raising an exception
    {
        iError = true;
        if (iLookAhead != null)
        {
            LispError.raiseError("Error parsing expression, near token " + iLookAhead + ".", iEnvironment);
        }
        LispError.raiseError("Error parsing expression.",iEnvironment);
    }
};
