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
package org.mathpiper.lisp.astprocessors;


import org.mathpiper.lisp.cons.AtomCons;
import org.mathpiper.lisp.Environment;
import org.mathpiper.lisp.cons.Cons;

/** ASTProcessor behaviour for changing the local variables to have unique
 * names.
 */
public class LocalSymbolSubstitute implements ASTProcessor {

    Environment iEnvironment;
    String[] iOriginalNames;
    String[] iNewNames;
    int iNumberOfNames;


    public LocalSymbolSubstitute(Environment aEnvironment,
            String[] aOriginalNames,
            String[] aNewNames, int aNrNames) {
        iEnvironment = aEnvironment;
        iOriginalNames = aOriginalNames;
        iNewNames = aNewNames;
        iNumberOfNames = aNrNames;
    }


    public Cons matches(Environment aEnvironment, int aStackTop, Cons aElement) throws Throwable {

        if (!(aElement.car() instanceof String)) {
            return null;
        }//end if.

        String name = (String) aElement.car();

        int i;
        for (i = 0; i < iNumberOfNames; i++) {
            if (name.equals(iOriginalNames[i])) {
                return AtomCons.getInstance(iEnvironment, aStackTop, iNewNames[i]);
            }
            
            if(name.contains("_"))
            {
	            if(name.startsWith("_") && name.substring(1, name.length()).equals(iOriginalNames[i]))
	            {
	            	String newName = name.replace(iOriginalNames[i], iNewNames[i]);
	            	
	            	return AtomCons.getInstance(iEnvironment, aStackTop, newName);
	            }
	            
	            String[] parts = name.split("_", 2);
	            
	            if(parts[0].equals(iOriginalNames[i]))
	            {
	            	String newName = iNewNames[i] + "_" + parts[1];
	            	
	            	return AtomCons.getInstance(iEnvironment, aStackTop, newName);
	            }
            }
            
            if(iOriginalNames[i].startsWith("_") && iOriginalNames[i].substring(1, iOriginalNames[i].length()).equals(name))
            {
        	return AtomCons.getInstance(iEnvironment, aStackTop, iNewNames[i].replace("_", ""));
            }
            
        }
        return null;
    }

};
