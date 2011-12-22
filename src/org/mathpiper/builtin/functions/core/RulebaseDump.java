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
package org.mathpiper.builtin.functions.core;

import java.util.Iterator;
import org.mathpiper.builtin.BuiltinFunction;
import org.mathpiper.lisp.Environment;
import org.mathpiper.lisp.LispError;
import org.mathpiper.lisp.Utility;
import org.mathpiper.lisp.rulebases.MultipleArityRulebase;
import org.mathpiper.lisp.rulebases.SingleArityRulebase;

public class RulebaseDump extends BuiltinFunction
{

    private RulebaseDump()
    {
    }

    public RulebaseDump(String functionName)
    {
        this.functionName = functionName;
    }


    public void evaluate(Environment aEnvironment, int aStackBase) throws Exception {
        if(getArgument(aEnvironment, aStackBase, 1) == null) LispError.checkArgument(aEnvironment, aStackBase, 1);
        String rulebaseName = (String) getArgument(aEnvironment, aStackBase, 1).car();
        if(rulebaseName == null) LispError.checkArgument(aEnvironment, aStackBase, 1);
        if(rulebaseName.charAt(0) != '\"') LispError.checkArgument(aEnvironment, aStackBase, 1);
        if( rulebaseName.charAt(rulebaseName.length() - 1) != '\"') LispError.checkArgument(aEnvironment, aStackBase, 1);

        rulebaseName = Utility.stripEndQuotesIfPresent(aEnvironment, aStackBase, rulebaseName);

        MultipleArityRulebase rulebase = aEnvironment.getMultipleArityRulebase(aStackBase, rulebaseName, false);

        if (rulebase != null) {

            aEnvironment.write("Rulebase: " + rulebaseName + "\n\n");
            
            Iterator multipleArityUserFunctionIterator = rulebase.getFunctions();

            while (multipleArityUserFunctionIterator.hasNext()) {
                SingleArityRulebase userFunction = (SingleArityRulebase) multipleArityUserFunctionIterator.next();
                Iterator rulesIterator = userFunction.getRules();

                while (rulesIterator.hasNext()) {

                    org.mathpiper.lisp.rulebases.Rule branchRulebase = (org.mathpiper.lisp.rulebases.Rule) rulesIterator.next();

                    String ruleDump = org.mathpiper.lisp.Utility.dumpRule(-1, branchRulebase, aEnvironment, userFunction);

                    aEnvironment.write(ruleDump + "\n");

                }//end while.

            }//end while.
        } else {
            aEnvironment.write("Rule not defined");
        }

        setTopOfStack(aEnvironment, aStackBase, Utility.getTrueAtom(aEnvironment));

    }//end method.
}//end class.




/*
%mathpiper_docs,name="RulebaseDump",categories="Programmer Functions;Programming;Built In"
*CMD RulebaseDump --- prints the rules in a rulebase if the rulebase exists
*CALL
    RulebaseDump("string")

 *PARMS
 {"string"} -- a string which contains the name of the rulebase

*DESC
This function prints information about each rule in a rulebase if the rulebase exists.

%/mathpiper_docs
*/
