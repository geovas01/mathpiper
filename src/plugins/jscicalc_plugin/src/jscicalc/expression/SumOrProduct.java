/** @file
 * Copyright (C) 2007, 2008 John D Lamb (J.D.Lamb@btinternet.com)
 *
 * This program is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 2 of the License, or (at
 * your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful, but
 * WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 675 Mass Ave, Cambridge, MA 02139, USA.
 */

package jscicalc.expression;
import jscicalc.OObject;
import jscicalc.complex.Complex;

/**
 * This class represents a sum or product of Expression objects.
 * expression.
 *
 * @see OObject
 *
 * @author J.&nbsp;D.&nbsp;Lamb
 * @version $Revision: 1.3 $
 */
public abstract class SumOrProduct extends Expression {
    /**
     * Construct
     */
    protected SumOrProduct(){
    }

    /**
     * Get the Complex.
     * @return The Complex
     */
    public final Complex getComplex(){
	return complex;
    }
    
    /**
     * Get the Expression list.
     * @return The Expression list
     */
    public final java.util.LinkedList<Expression> getExpressionList(){
	return expressionList;
    }

    public boolean isZero(){
	if( !complex.isZero() ) return false;
	if( expressionList.isEmpty() ) 
	    return true;
	else {
	    for( java.util.ListIterator<Expression> i = expressionList.listIterator();
		 i.hasNext(); ){
		if( !i.next().isZero() ) return false;
	    }
	}
	return true;
    }

    /**
     * Sort the class.
     */
    public void sort(){
	for( Expression e : expressionList )
	    e.sort();
	java.util.Collections.sort( expressionList );
    }

    /**
     * Automatic simplifications.
     * @return this
     */
    public OObject auto_simplify(){
	return this;
    }

    /**
     * Used for comparing lists. Checks lists for equivalence in much the same way you
     * would check strings for equivalence.
     * @param expressionList1 The first list
     * @param expressionList2 The second list
     * @return &#8722;1 if expressionList1 is considered less than expressionList2,
     * 1 if expressionList1 is considered greater than expressionList2 and 0 if they
     * are identical
     */
    public static int compare( java.util.LinkedList<Expression> expressionList1,
			       java.util.LinkedList<Expression> expressionList2 ){
	java.util.ListIterator<Expression> i = expressionList1.listIterator();
	java.util.ListIterator<Expression> j = expressionList2.listIterator();
	while( i.hasNext() && j.hasNext() ){
	    Expression e = i.next();
	    Expression f = j.next();
	    int r = e.compareTo( f );
	    if( r != 0 ) return r;
	}
	// No more comparison possible
	if( i.hasNext() )
	    return -1;
	else if( j.hasNext() )
	    return +1;
	else
	    return 0;
    }
    
    /**
     * The SumOrProduct should always contain precisely one Complex
     * (default value of zero).
     */
    protected Complex complex;
    /**
     * The Expression objects.
     */
    protected java.util.LinkedList<Expression> expressionList;
}
