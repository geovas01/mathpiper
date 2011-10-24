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

import java.util.ArrayList;
import java.util.List;
import org.mathpiper.lisp.Environment;

/** 
 * Similar to ConsPointer, but implements an array of pointers to CONS.
 *  
 */
public class ConsPointerArray {

    int iSize;
    Cons[] iArray;


    public ConsPointerArray(Environment aEnvironment, int aSize) {
       iSize = aSize;
       iArray = new Cons[50000];
    }


    public int size() {
        return iSize;
    }


    public Cons getElement(int aItem) {
        return iArray[aItem];
    }



    public void setElement(int aItem, Cons aCons) {
        iArray[aItem] = aCons;
    }

}
