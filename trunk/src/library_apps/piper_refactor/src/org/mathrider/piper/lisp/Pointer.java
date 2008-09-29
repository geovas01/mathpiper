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
package org.mathrider.piper.lisp;

/** 
 * Provides a smart pointer type to CONS
 *  that can be inserted into linked lists. They do the actual
 *  reference counting, and consequent destruction of the object if
 *  nothing points to it. Pointer is used in Cons as a pointer
 *  to the next object, and in diverse parts of the built-in internal
 *  functions to hold temporary values.
 */
public class Pointer
{

    Cons iCons;

    public Pointer()
    {
        iCons = null;
    }

    public Pointer(Pointer aOther)
    {
        iCons = aOther.iCons;
    }

    public Pointer(Cons aOther)
    {
        iCons = aOther;
    }

    public void set(Cons aNext)
    {
        iCons = aNext;
    }

    public Cons get()
    {
        return iCons;
    }

    public void goNext()
    {
        iCons = iCons.iCdr.iCons;
    }

    void doSet(Cons aNext)
    {
        iCons = aNext;
    }
}
