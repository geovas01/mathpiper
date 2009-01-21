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

package org.mathpiper.builtin;

import org.mathpiper.lisp.ArgumentList;


public class JavaObject extends BuiltinContainer
{
    private Object javaObject;
    public JavaObject(Object javaObject)
    {
        this.javaObject = javaObject;
    }

	public String send(ArgumentList aArgList)
	{
		return null;
	}
	public String typeName()
	{
		return "Not implemented yet.";
	}

    public Object getJavaObject() {
        return javaObject;
    }

    


}

