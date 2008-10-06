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
package org.mathrider.piper.lisp.parsers;

import org.mathrider.piper.*;
import org.mathrider.piper.lisp.ConsPointer;
import org.mathrider.piper.lisp.Environment;
import org.mathrider.piper.lisp.parsers.Parser;
import org.mathrider.piper.lisp.tokenizers.Tokenizer;
import org.mathrider.piper.io.InputStream;
import org.mathrider.piper.lisp.Operators;

public class InfixParser extends Parser
{

    public Operators iPrefixOperators;
    public Operators iInfixOperators;
    public Operators iPostfixOperators;
    public Operators iBodiedOperators;

    public InfixParser(Tokenizer aTokenizer,
            InputStream aInput,
            Environment aEnvironment,
            Operators aPrefixOperators,
            Operators aInfixOperators,
            Operators aPostfixOperators,
            Operators aBodiedOperators)
    {
        super(aTokenizer, aInput, aEnvironment);
        iPrefixOperators = aPrefixOperators;
        iInfixOperators = aInfixOperators;
        iPostfixOperators = aPostfixOperators;
        iBodiedOperators = aBodiedOperators;
    }

    public void parse(ConsPointer aResult) throws Exception
    {
        ParsedInfixExpression object = new ParsedInfixExpression(this);
        object.parse();
        aResult.set(object.iResult.get());
    }
}


