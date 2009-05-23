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
package org.mathpiper.builtin.functions.optional;

import java.util.ArrayList;
import org.mathpiper.builtin.BuiltinContainer;
import org.mathpiper.builtin.BuiltinFunction;
import org.mathpiper.lisp.Environment;
import org.mathpiper.lisp.UtilityFunctions;
import org.mathpiper.lisp.cons.AtomCons;
import org.mathpiper.lisp.cons.Cons;
import org.mathpiper.lisp.cons.ConsPointer;
import org.mathpiper.lisp.cons.ConsTraverser;

/**
 *
 *
 */
public class JavaCall extends BuiltinFunction {

	//private StandardFileOutputStream out = new StandardFileOutputStream(System.out);
	public void evaluate(Environment aEnvironment, int aStackTop) throws Exception {
		ConsPointer subList = getArgumentPointer(aEnvironment, aStackTop, 1).getCons().getSublistPointer();
		if (subList != null) {
			ConsTraverser consTraverser = new ConsTraverser(subList);

			//Skip past List type.
			consTraverser.goNext();

			Cons argumentCons;

			//Obtain the name of the variable that the desired Java object is bound to.
			argumentCons = consTraverser.getPointer().getCons();
			ConsPointer result = new ConsPointer();

			String argumentConsString = argumentCons.string().replace("\"", "");
			// argumentConsString = argumentConsString.replace("\"", "");
			aEnvironment.getGlobalVariable(argumentConsString, result);

			BuiltinContainer builtinContainer;

			if (result.getCons() != null) {

				builtinContainer = result.getCons().getGeneric();

				if (builtinContainer != null) {

					//System.out.println(argumentCons);
					consTraverser.goNext();

					ArrayList argumentArrayList = new ArrayList();
					while (consTraverser.getCons() != null) {
						argumentCons = consTraverser.getPointer().getCons();

						String argumentString = argumentCons.string();

						argumentString = argumentString.replace("\"", "");

						argumentArrayList.add(argumentString);

						consTraverser.goNext();

					}//end while.


					String response = builtinContainer.execute((String[]) argumentArrayList.toArray(new String[0]));
					//System.out.println("XXXXXXXXXXX: " + response);

					if(response == null)
					{
						response = "null";
					}
					getResult(aEnvironment, aStackTop).setCons(AtomCons.getInstance(aEnvironment, response));

					return;

				}//end if.

			}//end if.

		}//end if.

		UtilityFunctions.internalFalse(aEnvironment, getResult(aEnvironment, aStackTop));

	}//end method.
}
