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

import java.util.Map;
import org.mathpiper.io.StringOutput;
import org.mathpiper.lisp.Environment;
import org.mathpiper.lisp.printers.LispPrinter;


/**
 * Class Cons is the base object class that can be put in
 *  linked lists. It either has a pointer to a string
 * or it is a holder for a sublist, ,
 *  or it is a Java object.  All of these values are obtainable
 * using car();
 *  It is a reference-counted object. ConsPointer handles the reference counting. ap.
 */
public abstract class Cons //Note:tk:was MathPiperObject.
{

    protected Map metadataMap;

    public Cons() throws Exception
    {
        metadataMap = null; //aEnvironment.iEmptyAtom;
    }//end constructor.


    public abstract Object car() throws Exception;

    public abstract void setCar(Object object) throws Exception;

    public abstract Cons cdr();

    public abstract void setCdr(Cons aCons);

    public abstract int type();



    /**
     * If this is a number, return a BigNumber representation of it.
     */
    public Object getNumber(int aPrecision, Environment aEnvironment) throws Exception {
        return null;
    }

    public abstract Cons copy(boolean aRecursed) throws Exception;




    /**
     *  Return a pointer to extra info. This allows for annotating
     *  an object. Returns NULL by default.
     */
    public Map getMetadataMap()
    {
        return metadataMap;
    }//end method.



    public void setMetadataMap(Map metaDataMap)
    {
        this.metadataMap = metaDataMap;
    }//end method.



    public boolean isEqual(Cons aOther) throws Exception {
        // iCdr line handles the fact that either one is a string
        if(car() instanceof String && aOther.car() instanceof String){
            if (! (((String)car()).equals(((String)aOther.car())))) {
                return false;
            }
            else
            {
                return true;
            }
        }

        //So, no strings.
        Cons iter1 = (Cons) car();
        Cons iter2 = (Cons) aOther.car();
        if (!(iter1 != null && iter2 != null)) {
            return false;
        }

        // check all elements in sublist
        while (iter1 != null && iter2 != null) {
            if (!iter1.isEqual(iter2)) {
                return false;
            }

            iter1 = iter1.cdr();
            iter2 = iter2.cdr();
        }
        //One list longer than the other?
        if (iter1 == null && iter2 == null) {
            return true;
        }
        return false;
    }//end method.


    
    public String toString() {
        StringOutput out = new StringOutput();
        LispPrinter printer = new LispPrinter();
        try {
            printer.print(this, out, null);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return out.toString();
    }//end method.





    public static Object car(Object cons) throws Exception
    {
        return ((Cons)cons).car();
    }




    public static Object cdr(Object cons) throws Exception
    {
        return ((Cons)cons).cdr();
    }



    public static Object cadar(Object cons) throws Exception
    {
        cons = ((Cons)cons).car();
        cons = ((Cons)cons).cdr();
        return ((Cons)cons).car();
    }


    
    public static Object caaaar(Object cons) throws Exception
    {
        cons = ((Cons)cons).car();
        cons = ((Cons)cons).car();
        cons = ((Cons)cons).car();
        return ((Cons)cons).car();
    }



    public static Object caaadr(Object cons) throws Exception
    {
        cons = ((Cons)cons).cdr();
        cons = ((Cons)cons).car();
        cons = ((Cons)cons).car();
        return ((Cons)cons).car();
    }



    public static Object caaar(Object cons) throws Exception
    {
        cons = ((Cons)cons).car();
        cons = ((Cons)cons).car();
        return ((Cons)cons).car();
    }



    public static Object caadar(Object cons) throws Exception
    {
        cons = ((Cons)cons).car();
        cons = ((Cons)cons).cdr();
        cons = ((Cons)cons).car();
        return ((Cons)cons).car();
    }



    public static Object caaddr(Object cons) throws Exception
    {
        cons = ((Cons)cons).cdr();
        cons = ((Cons)cons).cdr();
        cons = ((Cons)cons).car();
        return ((Cons)cons).car();

    }



    public static Object caadr(Object cons) throws Exception
    {
        cons = ((Cons)cons).cdr();
        cons = ((Cons)cons).car();
        return ((Cons)cons).car();
    }



    public static Object caar(Object cons) throws Exception
    {
        cons = ((Cons)cons).car();
        return ((Cons)cons).car();
    }



    public static Object cadaar(Object cons) throws Exception
    {
        cons = ((Cons)cons).car();
        cons = ((Cons)cons).car();
        cons = ((Cons)cons).cdr();
        return ((Cons)cons).car();
    }



    public static Object cadadr(Object cons) throws Exception
    {
        cons = ((Cons)cons).cdr();
        cons = ((Cons)cons).car();
        cons = ((Cons)cons).cdr();
        return ((Cons)cons).car();
    }



    public static Object adar(Object cons) throws Exception
    {
        cons = ((Cons)cons).car();
        cons = ((Cons)cons).cdr();
        return ((Cons)cons).car();
    }



    public static Object caddar(Object cons) throws Exception
    {
        cons = ((Cons)cons).car();
        cons = ((Cons)cons).cdr();
        cons = ((Cons)cons).cdr();
        return ((Cons)cons).car();

    }



    public static Object cadddr(Object cons) throws Exception
    {
        cons = ((Cons)cons).cdr();
        cons = ((Cons)cons).cdr();
        cons = ((Cons)cons).cdr();
        return ((Cons)cons).car();
    }



    public static Object caddr(Object cons) throws Exception
    {
        cons = ((Cons)cons).cdr();
        cons = ((Cons)cons).cdr();
        return ((Cons)cons).car();
    }



    public static Object cadr(Object cons) throws Exception
    {
        cons = ((Cons)cons).cdr();
        return ((Cons)cons).car();
    }



    public static Object cdaaar(Object cons) throws Exception
    {
        cons = ((Cons)cons).car();
        cons = ((Cons)cons).car();
        cons = ((Cons)cons).car();
        return ((Cons)cons).cdr();
    }



    public static Object cdaadr(Object cons) throws Exception
    {
        cons = ((Cons)cons).cdr();
        cons = ((Cons)cons).car();
        cons = ((Cons)cons).car();
        return ((Cons)cons).cdr();
    }



    public static Object cdaar(Object cons) throws Exception
    {
        cons = ((Cons)cons).car();
        cons = ((Cons)cons).car();
        return ((Cons)cons).cdr();
    }



    public static Object cdadar(Object cons) throws Exception
    {
        cons = ((Cons)cons).car();
        cons = ((Cons)cons).cdr();
        cons = ((Cons)cons).car();
        return ((Cons)cons).cdr();
    }



    public static Object cdaddr(Object cons) throws Exception
    {
        cons = ((Cons)cons).cdr();
        cons = ((Cons)cons).cdr();
        cons = ((Cons)cons).car();
        return ((Cons)cons).cdr();
    }



    public static Object cdadr(Object cons) throws Exception
    {
        cons = ((Cons)cons).cdr();
        cons = ((Cons)cons).car();
        return ((Cons)cons).cdr();
    }



    public static Object cdar(Object cons) throws Exception
    {
        cons = ((Cons)cons).car();
        return ((Cons)cons).cdr();
    }



    public static Object cddaar(Object cons) throws Exception
    {
        cons = ((Cons)cons).car();
        cons = ((Cons)cons).car();
        cons = ((Cons)cons).cdr();
        return ((Cons)cons).cdr();
    }



    public static Object cddadr(Object cons) throws Exception
    {
        cons = ((Cons)cons).cdr();
        cons = ((Cons)cons).car();
        cons = ((Cons)cons).cdr();
        return ((Cons)cons).cdr();
    }



    public static Object cddar(Object cons) throws Exception
    {
        cons = ((Cons)cons).car();
        cons = ((Cons)cons).cdr();
        return ((Cons)cons).cdr();
    }



    public static Object cdddar(Object cons) throws Exception
    {
        cons = ((Cons)cons).car();
        cons = ((Cons)cons).cdr();
        cons = ((Cons)cons).cdr();
        return ((Cons)cons).cdr();
    }



    public static Object cddddr(Object cons) throws Exception
    {
        cons = ((Cons)cons).cdr();
        cons = ((Cons)cons).cdr();
        cons = ((Cons)cons).cdr();
        return ((Cons)cons).cdr();
    }



    public static Object cdddr(Object cons) throws Exception
    {
        cons = ((Cons)cons).cdr();
        cons = ((Cons)cons).cdr();
        return ((Cons)cons).cdr();
    }



    public static Object cddr(Object cons) throws Exception
    {
        cons = ((Cons)cons).cdr();
        return ((Cons)cons).cdr();
    }

    
}//end class.
