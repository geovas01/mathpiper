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

import org.mathpiper.lisp.cons.SublistCons;
import org.mathpiper.lisp.cons.AtomCons;
import org.mathpiper.lisp.tokenizers.MathPiperTokenizer;
import org.mathpiper.io.MathPiperInputStream;
import org.mathpiper.lisp.*;
import org.mathpiper.lisp.cons.Cons;

public class Parser {

    public MathPiperTokenizer iTokenizer;
    public MathPiperInputStream iInput;
    public Environment iEnvironment;
    public boolean iListed;


    public Parser(MathPiperTokenizer aTokenizer, MathPiperInputStream aInput,
            Environment aEnvironment) {
        iTokenizer = aTokenizer;
        iInput = aInput;
        iEnvironment = aEnvironment;
        iListed = false;
    }


    public Cons parse(int aStackBase) throws Exception {
        Cons aResult;

        String token;
        // Get token.
        token = iTokenizer.nextToken(iEnvironment, aStackBase, iInput);
        if (token.length() == 0) //TODO FIXME either token == null or token.length() == 0?
        {
            aResult = AtomCons.getInstance(iEnvironment, aStackBase, "EndOfFile");
            return aResult;
        }
        aResult = parseAtom(iEnvironment, aStackBase, token);
        return aResult;
    }


    Cons parseList(Environment aEnvironment, int aStackBase) throws Exception {
        String token;

        Cons aResult = null;
        Cons iter;
        if (iListed) {
            aResult = AtomCons.getInstance(iEnvironment, aStackBase, "List");
            iter = (aResult.cdr()); //TODO FIXME
        }
        for (;;) {
            //Get token.
            token = iTokenizer.nextToken(iEnvironment, aStackBase, iInput);
            // if token is empty string, error!
            if(token.length() <= 0) LispError.throwError(iEnvironment, aStackBase, LispError.INVALID_TOKEN, "Token empty."); //TODO FIXME
            // if token is ")" return result.
            if (token.equals(")")) {
                return aResult;
            }
            // else parse simple atom with parse, and append it to the
            // results list.

            iter = parseAtom(aEnvironment, aStackBase, token);
            iter = (iter.cdr()); //TODO FIXME
        }
    }


    Cons parseAtom(Environment aEnvironment, int aStackBase, String aToken) throws Exception {
        // if token is empty string, return null pointer (no expression)
        if (aToken.length() == 0) //TODO FIXME either token == null or token.length() == 0?
        {
            return null;
        }
        // else if token is "(" read in a whole array of objects until ")",
        //   and make a sublist
        if (aToken.equals("(")) {
            Cons subList = parseList(aEnvironment, aStackBase);
            Cons aResult = SublistCons.getInstance(aEnvironment, subList);
            return aResult;
        }
        // else make a simple atom, and return it.
        Cons aResult = AtomCons.getInstance(iEnvironment, aStackBase, aToken);

        return aResult;
    }

}
