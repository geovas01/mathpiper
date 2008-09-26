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

package org.mathrider.piper.builtin;

import org.mathrider.piper.lisp.Pointer;
import org.mathrider.piper.lisp.LispError;
import org.mathrider.piper.lisp.Environment;
import org.mathrider.piper.lisp.PiperArgStack;


public abstract class BuiltinFunction
{
	public abstract void eval(Environment aEnvironment,int aStackTop) throws Exception;

	public static Pointer RESULT(Environment aEnvironment,int aStackTop) throws Exception
	{
		return aEnvironment.iStack.GetElement(aStackTop);
	}
	
	public static Pointer ARGUMENT(Environment aEnvironment,int aStackTop, int i)  throws Exception
	{
		return aEnvironment.iStack.GetElement(aStackTop+i);
	}

	public static Pointer argument(Pointer cur, int n) throws Exception
	{
		LispError.LISPASSERT(n>=0);

		Pointer loop = cur;
		while(n != 0)
		{
			n--;
			loop = loop.get().cdr();
		}
		return loop;
	}

}
