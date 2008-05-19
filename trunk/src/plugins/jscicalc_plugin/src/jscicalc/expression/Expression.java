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
 * This class represents an output token that can be handled by Parser and is not an
 * Error or Complex.
 *
 * @see OObject
 *
 * @author J.&nbsp;D.&nbsp;Lamb
 * @version $Revision: 1.3 $
 */
public abstract class Expression extends OObject implements Comparable<Expression> {

    /**
     * The comparison operator.
     * @param expression The Expression to be compared.
     * @return integer indicating how expression is compared to this.
     */
    public int compareTo( Expression expression ){
	if( this instanceof SumOrProduct ){
	    // Deal with SumOrProduct (1)
	    if( expression instanceof SumOrProduct ){
		if( ((this instanceof Sum) && (expression instanceof Product)) ||
		    ((this instanceof Product) && (expression instanceof Sum)) ){
		    System.out.println( "Warning: Sum and Product being compared." );
		} else if( this instanceof Sum ){
		    return ((Sum)this).compareTo( (Sum)expression );
		} else if( this instanceof Product ){
		    return ((Product)this).compareTo( (Product)expression );
		} else
		    return +1;
	    } else {
		return -1;
	    }
	} else if( expression instanceof SumOrProduct ){
	    // Deal with SumOrProduct (1)
	    if( expression instanceof Sum ){
		return (new Sum( this ) ).compareTo( (Sum)expression );
	    } else if( expression instanceof Product ){
		return (new Product( this, false )).compareTo( (Product)expression );
	    } else
		return +1; // should never be reached provided SumOrProduct is Sum or
	    // Product
	} else if( this instanceof Power ){
	    // Deal with Power (1)
	    if( expression instanceof Power ){
		return ((Power)this).compareTo( (Power)expression );
	    } else {
		return -1;
	    }
	} else if( expression instanceof Power ){
	    // Deal with Power (2)
	    return +1;
	} else if( this instanceof Variable ){
	    // Deal with Variable (1)
	    if( expression instanceof Variable ){
		return ((Variable)this).compareTo( (Variable)expression );
	    } else {
		return -1;
	    }
	} else if( expression instanceof Variable ){
	    // Deal with Variable (2)
	    return +1;
	} else if( this instanceof Exp ){
	    // Deal with Exp (1)
	    if( expression instanceof Exp ){
		return 0;
	    } else {
		return -1;
	    }
	} else if( expression instanceof Exp ){
	    // Deal with Exp (2)
	    return +1;
	} else if( this instanceof Sin ){
	    // Deal with Sin (1)
	    if( expression instanceof Sin ){
		return 0;
	    } else {
		return -1;
	    }
	} else if( expression instanceof Sin ){
	    // Deal with Sin (2)
	    return +1;
	} else if( this instanceof Cos ){
	    // Deal with Cos (1)
	    if( expression instanceof Cos ){
		return 0;
	    } else {
		return -1;
	    }
	} else if( expression instanceof Cos ){
	    // Deal with Cos (2)
	    return +1;
	} else if( this instanceof Tan ){
	    // Deal with Tan (1)
	    if( expression instanceof Tan ){
		return 0;
	    } else {
		return -1;
	    }
	} else if( expression instanceof Tan ){
	    // Deal with Tan (2)
	    return +1;
	} else if( this instanceof ASin ){
	    // Deal with ASin (1)
	    if( expression instanceof ASin ){
		return 0;
	    } else {
		return -1;
	    }
	} else if( expression instanceof ASin ){
	    // Deal with ASin (2)
	    return +1;
	} else if( this instanceof ACos ){
	    // Deal with ACos (1)
	    if( expression instanceof ACos ){
		return 0;
	    } else {
		return -1;
	    }
	} else if( expression instanceof ACos ){
	    // Deal with ACos (2)
	    return +1;
	} else if( this instanceof ATan ){
	    // Deal with ATan (1)
	    if( expression instanceof ATan ){
		return 0;
	    } else {
		return -1;
	    }
	} else if( expression instanceof ATan ){
	    // Deal with ATan (2)
	    return +1;
	} else if( this instanceof Ln ){
	    // Deal with Ln (1)
	    if( expression instanceof Ln ){
		return 0;
	    } else {
		return -1;
	    }
	} else if( expression instanceof Ln ){
	    // Deal with Ln (2)
	    return +1;
	} else if( this instanceof Log ){
	    // Deal with Log (1)
	    if( expression instanceof Log ){
		return 0;
	    } else {
		return -1;
	    }
	} else if( expression instanceof Log ){
	    // Deal with Log (2)
	    return +1;
	} else if( this instanceof Factorial ){
	    // Deal with Factorial (1)
	    if( expression instanceof Factorial ){
		return 0;
	    } else {
		return -1;
	    }
	} else if( expression instanceof Factorial ){
	    // Deal with Factorial (2)
	    return +1;
	} else if( this instanceof Permutation ){
	    // Deal with Permutation (1)
	    if( expression instanceof Permutation ){
		return 0;
	    } else {
		return -1;
	    }
	} else if( expression instanceof Permutation ){
	    // Deal with Permutation (2)
	    return +1;
	} else if( this instanceof Combination ){
	    // Deal with Combination (1)
	    if( expression instanceof Combination ){
		return 0;
	    } else {
		return -1;
	    }
	} else if( expression instanceof Combination ){
	    // Deal with Combination (2)
	    return +1;
	} else if( this instanceof Conjugate ){
	    // Deal with Conjugate (1)
	    if( expression instanceof Conjugate ){
		return 0;
	    } else {
		return -1;
	    }
	} else if( expression instanceof Conjugate ){
	    // Deal with Conjugate (2)
	    return +1;
	} else if( this instanceof And ){
	    // Deal with And (1)
	    if( expression instanceof And ){
		return 0;
	    } else {
		return -1;
	    }
	} else if( expression instanceof And ){
	    // Deal with And (2)
	    return +1;
	} else if( this instanceof Or ){
	    // Deal with Or (1)
	    if( expression instanceof Or ){
		return 0;
	    } else {
		return -1;
	    }
	} else if( expression instanceof Or ){
	    // Deal with Or (2)
	    return +1;
	} else if( this instanceof Xor ){
	    // Deal with Xor (1)
	    if( expression instanceof Xor ){
		return 0;
	    } else {
		return -1;
	    }
	} else if( expression instanceof Xor ){
	    // Deal with Xor (2)
	    return +1;
	} else {
	    System.out.println( "Warning: unknown function" );
	}
	return 0;
    }
    
    /**
     * Return the negative of this.
     * @return The negative of this
     */
    public abstract Expression negate();
}
