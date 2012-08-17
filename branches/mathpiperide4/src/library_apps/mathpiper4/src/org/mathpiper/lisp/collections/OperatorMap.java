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

package org.mathpiper.lisp.collections;

import java.util.Map;

import org.mathpiper.lisp.*;


public class OperatorMap // <Operator>
{
    public Map map = new java.util.HashMap();
    
	Environment iEnvironment;

        public OperatorMap(Environment aEnvironment)
        {
            iEnvironment = aEnvironment;
        }

	public void setOperator(int aPrecedence,String aString)
	{
		Operator op = new Operator(aPrecedence);
		map.put(aString, op);
	}
	
	public void setRightAssociative(int aStackTop, String aString) throws Exception
	{
		Operator op = (Operator)map.get(aString);
		if(op == null) LispError.throwError(iEnvironment, aStackTop, LispError.NOT_AN_INFIX_OPERATOR, aString);
		op.setRightAssociative();
	}
	
	public void setLeftPrecedence(int aStackTop, String aString,int aPrecedence) throws Exception
	{
		Operator op = (Operator)map.get(aString);
		if(op == null) LispError.throwError(iEnvironment, aStackTop, LispError.NOT_AN_INFIX_OPERATOR, aString);
		op.setLeftPrecedence(aPrecedence);
	}
	
	public void setRightPrecedence(int aStackTop, String aString, int aPrecedence) throws Exception
	{
		Operator op = (Operator)map.get(aString);
		if(op == null) LispError.throwError(iEnvironment, aStackTop, LispError.NOT_AN_INFIX_OPERATOR, aString);
		op.setRightPrecedence(aPrecedence);
	}
	
}
