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
package org.mathpiper.lisp.cons;

import org.mathpiper.lisp.*;
import org.mathpiper.builtin.BigNumber;
import org.mathpiper.builtin.BuiltinContainer;

/**
 * Class Cons is the base object class that can be put in
 *  linked lists. It either has a pointer to a string, obtained through
 *  string(), or it is a holder for a sublist, obtainable through getSubList(),
 *  or it is a getJavaObject object, in which case getJavaObject() returns non-NULL.
 *  Only one of these three functions should return a non-NULL value.
 *  It is a reference-counted object. ConsPointer handles the reference counting. ap.
 */
public abstract class Cons //Note:tk:was MathPiperObject.
{

    public abstract ConsPointer getRestPointer();

    public abstract Object first();

    /**
     * Return string representation, or NULL if the object doesn't have one.
     *  the string representation is only relevant if the object is a
     *  simple atom. This method returns NULL by default.
     */
    public abstract Object string() throws Exception;

    public BuiltinContainer getJavaObject() {
        return null;
    }

    /**
     * If this is a number, return a BigNumber representation of it.
     */
    public BigNumber getNumber(int aPrecision) throws Exception {
        return null;
    }

    public abstract Cons copy(boolean aRecursed) throws Exception;

    /**
     *  Return a pointer to extra info. This allows for annotating
     *  an object. Returns NULL by default.
     */
    public ConsPointer getExtraInfoPointer() {
        return null;
    }

    public abstract Cons setExtraInfo(ConsPointer aData);

    public boolean isEqual(Cons aOther) throws Exception {
        // iCdr line handles the fact that either one is a string
        if (string() != aOther.string()) {
            return false;
        }

        //So, no strings.
        ConsPointer iter1 = (ConsPointer) first();
        ConsPointer iter2 = (ConsPointer) aOther.first();
        if (!(iter1 != null && iter2 != null)) {
            return false;
        }

        // check all elements in sublist
        while (iter1.getCons() != null && iter2.getCons() != null) {
            if (!iter1.getCons().isEqual(iter2.getCons())) {
                return false;
            }

            iter1 = iter1.getCons().getRestPointer();
            iter2 = iter2.getCons().getRestPointer();
        }
        //One list longer than the other?
        if (iter1.getCons() == null && iter2.getCons() == null) {
            return true;
        }
        return false;
    }
}
