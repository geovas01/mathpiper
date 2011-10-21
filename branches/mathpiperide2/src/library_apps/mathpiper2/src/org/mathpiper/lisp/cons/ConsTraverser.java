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

/**
 * Works almost like ConsPointer, but doesn't enforce
 * reference counting, so it should be slightly faster. This one
 * should be used instead of ConsPointer if you are going to traverse
 * a lisp expression in a non-destructive way.
 */
public class ConsTraverser {

    Cons iCurrentPointer;
    Cons iPreviousPointer;
    Cons iHeadPointer;

    private Environment iEnvironment;

    public ConsTraverser(Environment aEnvironment, ConsPointer aPtr) {
        iEnvironment = aEnvironment;
        iCurrentPointer = aPtr.getCons();
        iHeadPointer = iCurrentPointer;
        
    }

    public Object car() throws Exception {
        return iCurrentPointer.car();
    }

    public Cons cdr() {
        return iCurrentPointer.cdr();
    }

    public Cons getCons() {
        return iCurrentPointer;
    }

    public void setCons(Cons aCons) {
        iCurrentPointer = aCons;
        
        iPreviousPointer.setCdr(iCurrentPointer);
    }

    public ConsPointer getPointer() {
        return new ConsPointer(iCurrentPointer);
    }

    public ConsPointer getHeadPointer()
    {
        return new ConsPointer(iHeadPointer);
    }

    public void goNext(int aStackTop) throws Exception {
        if(iCurrentPointer == null) LispError.throwError(iEnvironment, aStackTop, LispError.NOT_LONG_ENOUGH, "","INTERNAL");
        iPreviousPointer = iCurrentPointer;
        iCurrentPointer = iCurrentPointer.cdr();
    }

    public void goSub(int aStackTop) throws Exception {
        if(iCurrentPointer == null) LispError.throwError(iEnvironment, aStackTop, LispError.INVALID_ARGUMENT, "","INTERNAL");
        if(! (iCurrentPointer.car() instanceof ConsPointer)) LispError.throwError(iEnvironment, aStackTop, LispError.NOT_A_LIST, iCurrentPointer,"INTERNAL");
        iCurrentPointer = ((ConsPointer)iCurrentPointer.car()).getCons();
    }
};

