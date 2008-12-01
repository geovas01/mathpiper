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
 */

//}}}
// :indentSize=4:lineSeparator=\n:noTabs=false:tabSize=4:folding=explicit:collapseFolds=0:

package org.mathpiper.interpreters;

/**
 *
 */
public class Interpreters {
    
private Interpreters()
{}

public static Interpreter newSynchronousInterpreter()
{
    return SynchronousInterpreter.newInstance();
}

public static Interpreter getSynchronousInterpreter()
{
    return SynchronousInterpreter.getInstance();
}

public static Interpreter newAsynchronousInterpreter()
{
    return AsynchronousInterpreter.newInstance();
}

public static Interpreter getAsynchronousInterpreter()
{
    return AsynchronousInterpreter.getInstance();
}

}
