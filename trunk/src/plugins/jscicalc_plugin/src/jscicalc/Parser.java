/** @file
 * Copyright (C) 2003&ndash;5 John D Lamb (J.D.Lamb@btinternet.com)
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

package jscicalc;
import jscicalc.pobject.*;
import jscicalc.complex.Complex;

/**
 * This is the <em>model</em> class as opposed to CalculatorApplet, which is the
 * <em>controller</em> class and is the real power behind the calculator: it does
 * almost all of the actual calculations. The only calculations that CalculatorApplet
 * does are those involving memory and only then at the request of the Parser object
 *
 * Parser does not itself know how to calculate anything. Rather it keeps
 * an ordered list of PObject objects representing the expression to be evaluated.
 * It then uses the PObject class hierarchy to decide which PObject objects to
 * evaluate first, which next and so on. Thus it starts by taking apart parentheses
 * and converting digits to Complex numbers. Then it carries out various functions,
 * multiplication and division, addition and subtraction and finally boolean
 * operations to create a result, which it gives to the CalculatorApplet object
 * as a OObject.
 *
 * @see CalculatorApplet
 * @see PObject
 *
 * @author J.&nbsp;D.&nbsp;Lamb 
 * @version $Revision: 1.9 $
 */
public class Parser {

    public Parser(){
	list = new java.util.LinkedList<GObject>();
	base = Base.DECIMAL;
    } 

    
    /**
     * Convert exponents into a form that can be read directly. Exponents get
     * converted early in the process of evaluating an expression.
     * @param list A list of tokens to be evaluated.
     */
    private void convertExponentsToNumerals( java.util.List<GObject> list ){
	for( java.util.ListIterator<GObject> i = list.listIterator(); i.hasNext(); ){
	    GObject o = i.next();
	    if( o instanceof E ){
		i.set( new Numeral( 'e' ) );
		boolean negative = false;
		for( o = i.next(); o instanceof Add || o instanceof Subtract;
		     o = i.next() ){
		    // it ought to be Add or Subtract
		    if( o instanceof Subtract )
			negative = !negative;
		    i.remove();
		}
		i.previous(); // return to 'e'
		if( negative ){
		    i.previous();
		    i.next();  // position iterator correctly
		    i.add( new Numeral( '-' ) ); 
		}
	    }
	}
    }

    /**
     * Convert anything that is a Numeral into a Complex
     * Exponents are also converted implicitly.
     * @see Numeral
     * @param list A list of tokens to be evaluated.
     */
    private void convertNumerals( java.util.List<GObject> list ){
	convertExponentsToNumerals( list );
	for( java.util.ListIterator<GObject> i = list.listIterator(); i.hasNext(); ){
	    GObject o = i.next();
	    if( o instanceof Numeral ){
		i.remove();
		Numeral numeral = (Numeral)o;
		String number = numeral.name();
		boolean flag;
		while( (flag = i.hasNext()) && (o = i.next()) instanceof Numeral ){
		    i.remove();
		    numeral = (Numeral)o;
		    number = number.concat( numeral.name() );
		}
		if( flag )
		    i.previous();
		//i.add( Double.valueOf( number ) );
		i.add( ParseBase.parseString( number, base ) );
	    }
	}
    }

    /**
     * Convert anything that is a Container.
     * Numerals and exponents are also done before containers are handled.
     * @see Container
     * @param list A list of tokens to be evaluated.
     */
    private void convertContainers( java.util.List<GObject> list ){
	/* first we convert Numerals */
	convertNumerals( list );
	for( java.util.ListIterator<GObject> i = list.listIterator(); i.hasNext(); ){
	    GObject o = i.next();
	    if( o instanceof Container ){
		if( ((Container)o).error() )
		    throw new RuntimeException( "Stat Error" );
		i.set( ((Container)o).value() );
	    }
	}
    }

    /**
     * Deal with all instances of I (square root of negative one).
     * Implicitly calls convertContainers.
     * @param list A list of tokens to be evaluated.
     */
    private void convertIs( java.util.List<GObject> list ){
	/* first we must convert Containers and hence Numerals */
	convertContainers( list );
	/* Now deal carefully with I.
	 * If we find an I we have to check whether it is followed by a Complex or I.
	 * If it is, we signal to multiply the Complex or I by i.
	 * Otherwise, we check if I is preceded by a Complex. If it is, we signal to
	 * multiply the Complex by i.
	 */
	for( java.util.ListIterator<GObject> i = list.listIterator( 0 ); i.hasNext(); ){
	    Object o = i.next();
	    if( o instanceof I ){
		i.set( Complex.I );
		/* check whether it is followed by a Complex or I
		 * If it is, we multiply the Complex or I by i. */
		if( i.hasNext() ){
		    o = i.next();
		    if( (o instanceof I) || (o instanceof Complex) ){
			i.previous();
			i.add( new Multiply() );
			i.previous();
		    } else {
			i.previous(); // return to scanning
		    }
		}
 		/* Now we check if I is preceded by a Complex.
 		 * If it is, we multiply the Complex by i. */
 		i.previous(); // go back before current position
 		if( i.hasPrevious() ){
 		    o = i.previous();
		    if( o instanceof Complex ){
			i.next();
			i.add( new Multiply() );
			i.next(); // next object scanned is Complex
		    }
		}
	    }
	}
    }

    /**
     * Convert variables into output form.
     * @param list A list of tokens to be evaluated. Nothing
     * else is done implicitly by this method.
     */
    private void convertVariables( java.util.List<GObject> list ){
	/* first we must convert Is (square root of negative one) */
	convertIs( list );
	/* Now deal carefully with variables.
	 * If we find a variable we have to check whether it is followed by a Complex.
	 * If it is, we multiply the Complex by variable and continue.
	 * Otherwise, we check if it is preceded by a Complex. If it is, we multiply
	 * the Complex by the variable and continue.
	 * If not, we check if it is preceded by another variable. If it is, we replace
	 * both by their product and continue.
	 * Otherwise we replace input variable with output variable.
	 */
	for( java.util.ListIterator<GObject> i = list.listIterator( 0 ); i.hasNext(); ){
	    Object o = i.next();
	    if( o instanceof jscicalc.pobject.Variable ){
		i.set( new jscicalc.expression.Variable( (jscicalc.pobject.Variable)o ) );
		/* check whether it is followed by a Complex or Variable
		 * If it is, we multiply the Complex or Variable by i. */
		if( i.hasNext() ){
		    o = i.next();
		    if( (o instanceof Complex) ||
			(o instanceof jscicalc.pobject.Variable) ){
			i.previous();
			i.add( new Multiply() );
			i.previous();
		    } else {
			i.previous(); // return to scanning
		    }
		}
 		/* Now we check if it is preceded by a Complex.
 		 * If it is, we multiply the Complex by the variable. */
 		i.previous(); // go back before current position
 		if( i.hasPrevious() ){
 		    o = i.previous();
		    if( o instanceof Complex ){
			i.next();
			i.add( new Multiply() );
			i.next(); // next object scanned is Complex
		    }
		}
	    }
	}
    }

    /**
     * This is where unary plus/minus is handled. Implicitly calls
     * convertIs.
     * @param list A list of tokens to be evaluated.
     */
    private void convertARFunctions( java.util.List<GObject> list ){
	/* first we must convert Variables */
	convertVariables( list );
	for( java.util.ListIterator<GObject> i = list.listIterator( list.size() );
	     i.hasPrevious(); ){
	    GObject o = i.previous();
	    if( o instanceof AFunction ){
		AFunction a = (AFunction)o;
		boolean flag;
		if( (flag = !i.hasPrevious()) ||
		    !((o = i.previous()) instanceof OObject
		      || o instanceof LFunction ) ){
		    if( !flag )
			i.next(); // advance to the AFunction;
		    i.next();
		    if( a instanceof Add )
			i.set( new Uplus() );
		    else if( a instanceof Subtract )
			i.set( new Uminus() );
		    else
			throw new RuntimeException( "+/- Error" );
		}
	    }
	}
    }

    /**
     * Deal with Square and like left functions.
     * Calls convertARFunction (through convertExponents) first.
     * @param list A list of tokens to be evaluated.
     */
    private void convertLFunctions( java.util.List<GObject> list ){
	/* first we must convert ARFunctions */
	convertARFunctions( list );
	for( java.util.ListIterator<GObject> i = list.listIterator(); i.hasNext(); ){
	    GObject o = i.next();
	    if( o instanceof LFunction ){
		i.remove();
		LFunction l = (LFunction)o;
		//double x = (Double)i.previous();
		//Complex x = (Complex)i.previous();
		OObject x = (OObject)i.previous();
		i.set( l.function( x ) );
	    }
	}
    }

    /**
     * Deal with functions like Power that have both left and right arguments.
     * Calls convertLFunctions first.
     * @param list A list of tokens to be evaluated.
     */
    private void convertDFunctions( java.util.List<GObject> list ){
	/* first we must convert LFunctions */
	convertLFunctions( list );
	for( java.util.ListIterator<GObject> i = list.listIterator(); i.hasNext(); ){
	    GObject o = i.next();
	    if( o instanceof DFunction ){
		i.remove();
		DFunction d = (DFunction)o;
		o = i.next();
		boolean sign = true; // +
		// Deal with Uplus/Uminus
		while( o instanceof Uplus || o instanceof Uminus ){
		    sign = sign ^ (o instanceof Uminus);
		    i.remove();
		    o = i.next();
		}
		//double y = (Double)i.next();
		//Complex y = (Complex)o;
		OObject y = (OObject)o;
		i.remove();
		//double x = (Double)i.previous();
		OObject x = (OObject)i.previous();
		i.set( sign ? d.function( x, y ) : d.function( x, y.negate() ) );
	    }
	}
    }

    /**
     * Deal with functions like SquareRoot that have right argument only.
     * Calls convertDFunctions first.
     * @param list A list of tokens to be evaluated.
     */
    private void convertRFunctions( java.util.List<GObject> list ){
	/* first we must convert DFunctions */
	convertDFunctions( list );
	for( java.util.ListIterator<GObject> i = list.listIterator( list.size() );
	     i.hasPrevious(); ){
	    GObject o = i.previous();
	    if( o instanceof RFunction ){
		i.remove();
		RFunction r = (RFunction)o;
		//double x = ((Double)(i.next())).doubleValue();
		//Complex x = (Complex)(i.next());
		OObject x = (OObject)(i.next());
		//i.set( new Double( r.function( x ) ) );
		i.set( r.function( x ) );
	    }
	}
    }

    /**
     * Deal with multiplication and division.
     * Calls convertRFunctions first.
     * @param list A list of tokens to be evaluated.
     */
    private void convertMFunctions( java.util.List<GObject> list ){
	/* first we must convert RFunctions */
	convertRFunctions( list );
	for( java.util.ListIterator<GObject> i = list.listIterator(); i.hasNext(); ){
	    GObject o = i.next();
	    if( o instanceof MFunction ){
		i.remove();
		MFunction m = (MFunction)o;
		//double y = (Double)i.next();
		OObject y = (OObject)i.next();
		i.remove();
		//double x = (Double)i.previous();
		OObject x = (OObject)i.previous();
		i.set( m.function( x, y ) );
	    }
	}
    }

    /**
     * Deal with addition and subtraction.
     * Calls convertMFunctions first.
     * @param list A list of tokens to be evaluated.
     */
    private void convertAFunctions( java.util.List<GObject> list ){
	/* first we must convert MFunctions */
	convertMFunctions( list );
	for( java.util.ListIterator<GObject> i = list.listIterator(); i.hasNext(); ){
	    GObject o = i.next();
	    if( o instanceof AFunction ){
		i.remove();
		AFunction a = (AFunction)o;
		OObject y = (OObject)i.next();
		i.remove();
		OObject x = (OObject)i.previous();
		OObject q = a.function( x, y );
		i.set( q );
	    }
	}
    }
    
    /**
     * Deal with boolean functions.
     * Calls convertMFunctions first.
     * @param list A list of tokens to be evaluated.
     */
    private void convertBoolFunctions( java.util.List<GObject> list ){
	/* first we must convert AFunctions */
	convertAFunctions( list );
	for( java.util.ListIterator<GObject> i = list.listIterator(); i.hasNext(); ){
	    GObject o = i.next();
	    if( o instanceof BoolFunction ){
		i.remove();
		BoolFunction b = (BoolFunction)o;
		OObject y = (OObject)i.next();
		i.remove();
		OObject x = (OObject)i.previous();
		i.set( b.function( x, y ) );
	    }
	}
    }

    /**
     * Takes final expression and multiplies all Complex numbers together.
     * Calls convertBoolFunctions first (and cascades)
     * @param list A list of tokens to be evaluated.
     */
    private void convertToProduct( java.util.List<GObject> list ){
	convertBoolFunctions( list );
	while( list.size() > 1 ){
	    OObject z = (OObject)(list.get( 0 ));
	    OObject c = (OObject)(list.remove( 1 ));
	    list.set( 0, c.multiply( (OObject)(list.get( 0 )) ) );
	}
    }

    /**
     * Tries to strip a pair of parentheses. If it succeeds, the expression in
     * parentheses is passed to convertBoolFunctions and so on up the chain
     * so that it gets converted to a Complex.
     * Used convertToProduct() to do detailed conversion.
     * @return true if we managed to strip a pair of parentheses.
     */
    private boolean stripParenthesis( java.util.List<GObject> list ){
	int lparen = -1;
	int rparen = -1;
	for( int i = 0; i < list.size(); ++i ){
	    GObject o = list.get( i );
	    if( o instanceof LParen )
		lparen = i;
	    else if( o instanceof RParen ){
		rparen = i;
		break;
	    }
	}
	if( lparen == -1 && rparen == -1 )
	    return false;
	if( lparen == -1 || rparen == - 1 )
	    throw new RuntimeException( "Parenthesis Error" );
	list.remove( rparen );
	list.remove( lparen );
	convertToProduct( list.subList( lparen, rparen - 1 ) );
	return true;
    }

    /**
     * This is the main evaluation function. It works by a finding suitable
     * subexpressions and calling a cascade of methods to evaluate the expression
     * in the correct sequence. Thus the parser works largely by recursion on
     * what can be thought of as a tree of PObjects defined by the sequence of
     * Parser methods and the PObject hierarchy.
     *
     * There may be some inconsistency here&mdash;either Parser should store AngleType
     * or it doesn&rsquo;t need to store Base.
     * It won&rsquo;t cause any errors because we&rsquo;ll
     * always get evaluated what was displayed.
     *
     * @param angleType Whether to use radians or degrees
     * @return A Complex or an error if the expression was nonsensical.
     */
    public OObject evaluate( AngleType angleType ){
	/* set AngleType for all trig functions */
	for( GObject o : list )
	    if( o instanceof Trig )
		((Trig)o).setScale( angleType );
	try {
	    while( stripParenthesis( list ) );
	    convertToProduct( list );
	} catch( Exception e ){
	    System.out.println( e.getMessage() );
	    return new Error( "Error" );
	}
	if( list.size() != 1 ){
 	    System.out.println( list.size() );
 	    return new Error( "Error" );
 	}
	try {
	    //Double d = (Double)(list.remove( 0 ));
	    //return d;
	    OObject c = (OObject)(list.remove( 0 ));
	    return c.auto_simplify();
	} catch( Exception e ){
	    System.out.println( e.getMessage() );
	    return new Error( "Error" );
	}
    }

    /**
     * How many objects (usually PObject objects) in the expression?
     * @return size of expression stored.
     */
    public int size(){
	return list.size();
    }

    /**
     * Put a PObject object on the end of the expression.
     * @param o The PObject to add
     */
    public void add( GObject o ){
	list.add( o );
    }

    /**
     * Put a GObject object at a specified position. We need this if the expression
     * is editable. Otherwise we could not add elements at an arbitrary position.
     * @param index The position at which to insert the object
     * @param o The GObject to add
     */
    public void add( int index, GObject o ){
	if( index < list.size() )
	    list.add( index, o );
	else if( index == list.size() )
	    list.add( o );
	else
	    throw new RuntimeException( "Parser.add(): index out of range" );
    }

    /**
     * Delete GObject at position index. Used for editing expressions.
     * @param index The position at which to delete
     * @return The deleted object, which would normally just be discarded
     */
    public PObject del( int index ){
	if( index > 0 && index < list.size() ){
	    GObject o = list.remove( index - 1 );
	    if( o instanceof PObject )
		return (PObject)o;
	    else
		return null;
	} else if( index == list.size() ){
	    if( list.isEmpty() )
		return null;
	    GObject o = list.remove( index - 1 );
	    if( o instanceof PObject )
		return (PObject)o;
	    else
		return null;
	} else if( index == 0 )
	    return null;
	else
	    throw new RuntimeException( "Parser.del(): index out of range" );
    }

    /**
     * Find an array of String objects representing the first q GObject objects.
     * The strange format is so that we could
     * know how many characters (including HTML) were in the string. The Navigator
     * class uses this kind of information.
     * 
     * @param q An integer
     * @return GObject instances that can be placed in a string.
     */
    private java.util.Vector<String> getStringVector( int q ){
	/* first create an array of HTML characters */
	java.util.Vector<String> c
	    = new java.util.Vector<String>();
	int i = 0;
	for( GObject o : list ){
	    if( o instanceof PObject ){
		PObject p = (PObject)o;
		for( String s : p.name_array() )
		    c.add( s );
	    }
	    ++i;
	    if( i >= Math.min( q, list.size() ) ) break;
	}
	return c;
    }

    // pure debugging function
    private java.util.Vector<String> getListAsStringVector(){
	/* first create an array of HTML characters */
	java.util.Vector<String> c
	    = new java.util.Vector<String>();
	for( Object o : list ){
	    if( o instanceof PObject ){
		PObject p = (PObject)o;
		for( String s : p.name_array() )
		    c.add( s );
	    } else if( o instanceof Complex ){
		Complex z = (Complex)o;
		String s = Double.toString( z.real() )
		    + "+i" + Double.toString( z.imaginary() );
		c.add( s );
	    } else if(  o instanceof Double ){
		Double d = (Double)o;
		c.add( d.toString() );
	    }
	}
	return c;
    }

    /**
     * Convert expression to a string. Useful for debugging and displaying.
     * @return Expresion as a string.
     */
    public String getExpression(){
	/* first create an array of HTML characters */
	java.util.Vector<String> c = getStringVector( list.size() );
	StringBuilder s = new StringBuilder();
	for( int i = 0; i < c.size(); ++i )
	    s.append( c.elementAt( i ) );
	return s.toString();
    }

    /**
     * Convert expression to a string. Useful for debugging and displaying. Probably
     * obsolete.
     * param n The number of objects to try to convert.
     * @return Expresion as a string.
     */
    public String getExpression( int n ){
	/* first create an array of HTML characters */
	java.util.Vector<String> c = getStringVector( list.size() );
	StringBuilder s = new StringBuilder();
	//s.append( "<html><pre " );
	//s.append( ">" );
	int start = Math.max( c.size() - n, 0 );
	for( int i = start; i < start + Math.min( c.size(), n ); ++i )
	    s.append( c.elementAt( i ) );
	//s.append( "</pre></html>" );
	return s.toString();
    }
    
    /**
     * When you press AC you need a new expression.
     */
    public void clearExpression(){
	list.clear();
    }

    /**
     * Are there any objects in the current expression.
     * @return <em>true</em> if current expression is empty.
     */
    public boolean isEmpty(){
	return list.isEmpty();
    }

    /**
     * Get the last object. This is used by EqualsButton to find out whether
     * or not it should add an Ans object before evaluating an expression.
     * @return last object in the expression
     */
    public final PObject getLast(){
	if( list.isEmpty() )
	    return null;
	GObject o = list.getLast();
	if( o instanceof PObject )
	    return (PObject)o;
	else
	    return null;
    }
    
    /**
     * Delete GObject at position index. Used for editing expressions.
     * This version is probably obsolete.
     * @return The deleted object, which would normally just be discarded
     */
    public boolean del(){
	if( list.isEmpty() )
	    return false;
	java.util.ListIterator i = list.listIterator( list.size() );
	i.previous();
	i.remove();
	return true;
    }
    
    /**
     * We need to can get a copy of the expression if we move up the history list.
     * @return A copy of the current expression
     */
    public java.util.LinkedList<PObject> getList(){
	java.util.LinkedList<PObject> result = new java.util.LinkedList<PObject>();
	for( GObject object : list )
	    if( object instanceof PObject )
		result.add( (PObject)object );
	return result;
    }

    /**
     * If we move up or down the history list we change the current expression using
     * this function
     * @see #getList()
     * @param newlist A list of PObject objects to be copied into this Parser
     */
    public void setList( java.util.LinkedList<PObject> newlist ){
	list.clear();
	for( PObject object : newlist )
	    list.add( object );
    }

    /**
     * Parser keeps a copy of the Base. We do this because the Parser object
     * does not know about the CalculatorApplet object: the <em>controller</em> is
     * hidden from the <em>model</em>.
     * @return base
     */
    public final Base base(){
	return base;
    }

    /**
     * Parser keeps a copy of the Base. We do this because the Parser object
     * does not know about the CalculatorApplet object: the <em>controller</em> is
     * hidden from the <em>model</em>.
     * Value gets set by CalculatorApplet.
     * @param base The new base
     */
    public void base( Base base ){
	this.base = base;
    }

    /**
     * The expression: a list of objects. Actually PObject objects most of the time,
     * but it&rsquo;s convenient to keep a list of objects because during expression
     * evaluation, Complex objects get put in the list.
     */
    private java.util.LinkedList<GObject> list;
    /**
     * The base. Base.DECIMAL, Base.Octal, Base.HEXADECIMAL or Base.Binary.
     * @see #base()
     * @see Base
     */
    private Base base;

    /**
     * May be obsolete: used to witch font size within HTML. If it&rsquo;s not obsolete,
     * it probably belongs in a <em>view</em> class
     * @param fontSize A font size
     * @return The CSS to change font size.
     */
    public static String font_size( float fontSize ){
	String s = "style=\"font-size: "
	    + Float.toString( fontSize )
	    + "\"";
	return s;
    }

    /**
     * Test code.
     */
    public static void main( String args[] ){
	Parser parser = new Parser();
	parser.add( new Numeral( '3' ) );
	parser.add( new Numeral( '.' ) );
	parser.add( new Numeral( '1' ) );
	parser.add( new Numeral( '4' ) );
	parser.add( new Multiply() );
	parser.add( new LParen() );
	parser.add( new Numeral( '4' ) );
	parser.add( new Add() );
	parser.add( new Numeral( '5' ) );
	parser.add( new Cube() );
	parser.add( new Subtract() );
	parser.add( new Numeral( '5' ) );
	parser.add( new Combination() );
	parser.add( new Numeral( '2' ) );
	parser.add( new RParen() );
	javax.swing.JOptionPane.showMessageDialog( null, parser.getExpression() );
	System.out.println( parser.evaluate( AngleType.DEGREES ) );
    }

}
