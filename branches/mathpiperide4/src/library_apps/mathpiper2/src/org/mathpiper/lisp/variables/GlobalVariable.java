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
package org.mathpiper.lisp.variables;

import org.mathpiper.lisp.cons.Cons;


/**
 * Value of a Lisp global variable.
 * The only special feature of this class is the attribute
 * <b>iEvalBeforeReturn</b>, which defaults to <b>LispFalse</b>. If this
 * attribute is set to <b>LispTrue</b>, the value in <b>iValue</b> needs to be
 * evaluated to get the value of the Lisp variable.
 * See: LispEnvironment::GetVariable()
 */
public class GlobalVariable {

    public Cons iValue;
    public boolean iEvalBeforeReturn;


    public GlobalVariable(GlobalVariable aOther) {
        iValue = aOther.iValue;
        iEvalBeforeReturn = aOther.iEvalBeforeReturn;
    }


    public GlobalVariable(Cons aValue) {
        iValue = aValue;
        iEvalBeforeReturn = false;
    }


    public void setEvalBeforeReturn(boolean aEval) {
        iEvalBeforeReturn = aEval;
    }


    @Override
    public String toString() {
        return (String) iValue.toString();
    }


    public boolean isIEvalBeforeReturn() {
        return iEvalBeforeReturn;
    }


    public Cons getValue() {
        return iValue;
    }

}
