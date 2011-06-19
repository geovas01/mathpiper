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

public class RulebaseDump extends BuiltinFunction {

    public void evaluate(Environment aEnvironment, int aStackTop) throws Exception {
        LispError.checkArgument(aEnvironment, aStackTop, getArgumentPointer(aEnvironment, aStackTop, 1).getCons() != null, 1, "RulebaseDump");
        String rulebaseName = (String) getArgumentPointer(aEnvironment, aStackTop, 1).car();
        LispError.checkArgument(aEnvironment, aStackTop, rulebaseName != null, 1, "RulebaseDump");
        LispError.checkArgument(aEnvironment, aStackTop, rulebaseName.charAt(0) == '\"', 1, "StringToUnicode");
        LispError.checkArgument(aEnvironment, aStackTop, rulebaseName.charAt(rulebaseName.length() - 1) == '\"', 1, "StringToUnicode");

        rulebaseName = Utility.stripEndQuotesIfPresent(aEnvironment, aStackTop, rulebaseName);

        MultipleArityRulebase rulebase = aEnvironment.getMultipleArityRulebase(aStackTop, rulebaseName, false);

        if (rulebase != null) {
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

        Utility.putTrueInPointer(aEnvironment, getTopOfStackPointer(aEnvironment, aStackTop));

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
