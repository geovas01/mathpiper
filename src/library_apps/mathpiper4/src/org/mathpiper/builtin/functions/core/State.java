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
 *///}}}
// :indentSize=4:lineSeparator=\n:noTabs=false:tabSize=4:folding=explicit:collapseFolds=0:
package org.mathpiper.builtin.functions.core;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import org.mathpiper.builtin.BuiltinFunction;
import org.mathpiper.lisp.Environment;
import org.mathpiper.lisp.LispError;
import org.mathpiper.lisp.Utility;
import org.mathpiper.lisp.cons.Cons;
import org.mathpiper.lisp.cons.SublistCons;
import org.mathpiper.lisp.variables.GlobalVariable;

/**
 *
 *
 */
public class State extends BuiltinFunction {
    private Map defaultOptions;

    private State() {
    }

    public State(String functionName) {
	this.functionName = functionName;

	defaultOptions = new HashMap();
	defaultOptions.put("showHidden", false);
    }

    public void evaluate(Environment aEnvironment, int aStackTop)
	    throws Exception {

	Cons arguments = getArgument(aEnvironment, aStackTop, 1);

	if (!Utility.isSublist(arguments))
	    LispError.throwError(aEnvironment, aStackTop,
		    LispError.INVALID_ARGUMENT, "");

	Cons options = (Cons) arguments.car(); // Go to sub list.

	options = options.cdr(); // Strip List tag.

	Map userOptions = Utility.optionsListToJavaMap(aEnvironment, aStackTop,
		options, defaultOptions);

	Map<String, GlobalVariable> globalState = (Map<String, GlobalVariable>) aEnvironment
		.getGlobalState();

	java.util.Set<String> variablesSet = globalState.keySet();

	java.util.List<String> variablesList = null;

	variablesList = new ArrayList<String>();

	for (String key : variablesSet) {
	    if (userOptions.get("showPrivate").equals(true)) {
		variablesList.add(key + ":" + globalState.get(key));
	    } else if (!key.contains("$")
		    && !key.equals("I")
		    && !key.equals("%")
		    && ((GlobalVariable) globalState.get(key)).iConstant == false) {
		variablesList.add(key + ":" + globalState.get(key));
	    }

	}

	Collections.sort(variablesList, new NameComparator());

	Cons head = Utility.iterableToList(aEnvironment, aStackTop,
		variablesList);

	setTopOfStack(aEnvironment, aStackTop,
		SublistCons.getInstance(aEnvironment, head));

    }// end method.

    private class NameComparator implements Comparator<String> {

	public int compare(String s1, String s2) {
	    return s1.compareToIgnoreCase(s2);
	}// end method.
    }// end class.

}// end class.

/*
%mathpiper_docs,name="State",categories="User Functions;Variables"
*CMD State --- return a list which contains the names and values of the global variables

*CALL 
 
 State()
 State(showPrivate -> True)


*DESC 
Return a list which contains the values of all the global variables.

*E.G. 

In> a := 1; b := 2; c := 3;
Result> 3

In> State()
Result> [a:1,b:2,c:3]

%/mathpiper_docs
*/
