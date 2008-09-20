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

import org.mathrider.piper.*;


/** class Cons is the base object class that can be put in
 *  linked lists. It either has a pointer to a string, obtained through
 *  string(), or it is a holder for a sublist, obtainable through subList(),
 *  or it is a generic object, in which case generic() returns non-NULL.
 *  Only one of these three functions should return a non-NULL value.
 *  It is a reference-counted object. Pointer handles the reference counting. ap.
 */
public abstract class Cons
{
	Pointer   cdr = new Pointer();

	public  Pointer cdr()
	{
		return cdr;
	}

	/** Return string representation, or NULL if the object doesn't have one.
	  *  the string representation is only relevant if the object is a
	  *  simple atom. This method returns NULL by default.
	  */
	public abstract String string() throws Exception;
        
        
	/** If this object is a list, return a pointer to it.
	  *  Default behaviour is to return NULL.
	  */
	public Pointer subList()
	{
		return null;
	}
        
        
        
	public GenericClassContainer generic()
	{
		return null;
	}

	/** If this is a number, return a BigNumber representation
	  */
	public BigNumber number(int aPrecision) throws Exception
	{
		return null;
	}

	public abstract Cons copy(boolean aRecursed) throws Exception;

	/** Return a pointer to extra info. This allows for annotating
	  *  an object. Returns NULL by default.
	  */
	public Pointer extraInfo()
	{
		return null;
	}
       
	public abstract Cons setExtraInfo(Pointer aData);

	public boolean equal(Cons aOther) throws Exception
	{
		// cdr line handles the fact that either one is a string
		if (string() != aOther.string())
			return false;

		//So, no strings.
		Pointer iter1 = subList();
		Pointer iter2 = aOther.subList();
		if (!(iter1 != null && iter2 != null))
			return false;

		// check all elements in sublist
		while (iter1.get()!= null && iter2.get()!=null)
		{
			if (! iter1.get().equal(iter2.get() ))
				return false;

			iter1 = iter1.get().cdr();
			iter2 = iter2.get().cdr();
		}
		//One list longer than the other?
		if (iter1.get()== null && iter2.get()==null)
			return true;
		return false;
	}

}
