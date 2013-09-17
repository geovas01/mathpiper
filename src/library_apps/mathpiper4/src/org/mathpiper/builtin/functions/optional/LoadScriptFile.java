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
package org.mathpiper.builtin.functions.optional;

import java.io.ByteArrayInputStream;
import java.util.Map;

import org.mathpiper.builtin.BuiltinFunction;
import org.mathpiper.builtin.BuiltinFunctionEvaluator;
import org.mathpiper.builtin.functions.optional.support.FileInputStream;
import org.mathpiper.interpreters.EvaluationResponse;
import org.mathpiper.io.InputStatus;
import org.mathpiper.io.StringInputStream;
import org.mathpiper.lisp.Environment;
import org.mathpiper.lisp.LispError;
import org.mathpiper.lisp.Utility;
import org.mathpiper.lisp.cons.Cons;
import org.mathpiper.test.Fold;

public class LoadScriptFile extends BuiltinFunction {

    public void plugIn(Environment aEnvironment) throws Throwable {
	this.functionName = "LoadScriptFile";
	aEnvironment.getBuiltinFunctions().put(this.functionName, new BuiltinFunctionEvaluator(this, 1, BuiltinFunctionEvaluator.FixedNumberOfArguments | BuiltinFunctionEvaluator.EvaluateArguments));
    }// end method.

    public void evaluate(Environment aEnvironment, int aStackTop)
	    throws Throwable {

	if (aEnvironment.iSecure != false)
	    LispError.throwError(aEnvironment, aStackTop, LispError.SECURITY_BREACH);

	Cons evaluated = getArgument(aEnvironment, aStackTop, 1);

	if (evaluated == null)
	    LispError.checkArgument(aEnvironment, aStackTop, 1);

	String fileName = (String) evaluated.car();

	if (fileName == null)
	    LispError.checkArgument(aEnvironment, aStackTop, 1);

	fileName = Utility.stripEndQuotesIfPresent(fileName);

	InputStatus inputStatus = new InputStatus("USER_File: " + fileName);

	

	Environment.saveDebugInformation = true;

	if (fileName.toLowerCase().endsWith(".mpw")) {
	    
	    java.io.FileInputStream javaFileInputStream = new java.io.FileInputStream(fileName);
	    Map<String, Fold> foldsMap = org.mathpiper.test.MPWSFile.getFoldsMap(fileName, javaFileInputStream);

	    for (Map.Entry<String, Fold> entry : foldsMap.entrySet()) {
		Fold fold = entry.getValue();

		String foldType = fold.getType();

		if (foldType.equalsIgnoreCase("%mathpiper")) {

		    if (fold.getAttributes().containsKey("def")) {
			String codeText = fold.getContents();

			StringInputStream stringInputStream = new StringInputStream(codeText, inputStatus);
			
			Utility.doInternalLoad(aEnvironment, aStackTop, stringInputStream);
		    }
		}

	    }
	} else {
	    FileInputStream mathpiperFileInputStream = new FileInputStream(fileName, inputStatus); // aEnvironment.iCurrentInput.iStatus);
	    Utility.doInternalLoad(aEnvironment, aStackTop, mathpiperFileInputStream);
	}

	Environment.saveDebugInformation = false;

	setTopOfStack(aEnvironment, aStackTop, Utility.getTrueAtom(aEnvironment));

    }// end method.

}// end class.

/*
%mathpiper_docs,name="LoadScriptFile",categories="Programming Functions;Input/Output;Built In"
*CMD LoadScriptFile --- evaluate MathPiper code that is in a file

*CALL LoadScriptFile(fileName)

*PARMS

{fileName} -- a string that contains the path and name of a file that
contains MathPiper code

*DESC

If the file is a .mpw file, all the code in %mathpiper folds that have a name attribute
and a def attribute is evaluated. For other file types, all MathPiper code in the file
is evaluated.
{LoadScriptFile} always returns {True}.

*SEE LoadScript 
%/mathpiper_docs
*/
