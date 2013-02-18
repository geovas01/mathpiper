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

import java.util.HashMap;
import java.util.Map;

import org.mathpiper.lisp.tokenizers.MathPiperTokenizer;
import org.mathpiper.io.MathPiperInputStream;
import org.mathpiper.lisp.*;
import org.mathpiper.lisp.cons.Cons;

public abstract class Parser {

    public MathPiperTokenizer iTokenizer;
    public MathPiperInputStream iInput;
    public Environment iEnvironment;
    public boolean iListed;
    
    private static Map<String,Parser> supportedParsers = new HashMap<String,Parser>();


    public Parser(MathPiperTokenizer aTokenizer, MathPiperInputStream aInput,
            Environment aEnvironment) {
        iTokenizer = aTokenizer;
        iInput = aInput;
        iEnvironment = aEnvironment;
        iListed = false;
    }


    public abstract Cons parse(int aStackTop) throws Throwable;
    
    
    public abstract String processLineTermination(String code);
    
    public abstract String processCodeBlock(String code);
    
    
    public static boolean isSupportedParser(String parserName)
    {
	return supportedParsers.containsKey(parserName);
    }
    
    public static void addSupportedParser(String parserName, Parser parser)
    {
	if(!supportedParsers.containsKey(parserName))
	{
	    supportedParsers.put(parserName, parser);
	}
    }
    
    public static Parser getSupportedParser(String parserName)
    {
	return supportedParsers.get(parserName);
    }

}
