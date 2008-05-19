/** @file
 * Copyright (C) 2008 John D Lamb (J.D.Lamb@btinternet.com)
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

/**
 * This class is used to hold an array of strings. It is really just a Vector of Vectors
 * of String objects, with a few additional functions. Its main purpose is to hold
 * HTML in a way that allows us to efficiently find the positions at which a Caret makes
 * sense.
 *
 * @author J.&nbsp;D.&nbsp;Lamb 
 * @version $Revision: 1.3 $
 */
public class StringArray extends java.util.Vector<java.util.Vector<String>> {
    
    /**
     * Create a new empty string array.
     */
    public StringArray(){
	super();
    }
    
    /**
     * Add a String to this. Wraps the string as a Vector<String> first.
     * @param string The String to be added
     */
    public void add( String string ){
	java.util.Vector<String> q = new java.util.Vector<String>();
	q.add( string );
	add( q );
    }
    
    /**
     * Add a character to this. Wraps the character as a Vector<String> first.
     * @param character The character to be added
     */
    public void add( char character ){
	add( Character.toString( character ) );
    }

    /**
     * Add a string array to this. Converts the string array to a Vector<String> first.
     * @param string The string array to be added
     */
    public void add( String[] string ){
	java.util.Vector<String> t = new java.util.Vector<String>();
	t.addAll( java.util.Arrays.asList( string ) );
	add( t );
    }

    /**
     * Test whether this matches one
     * @return <em>true</em> or <em>false</em> according as this matches 1 or not
     */
    public boolean isOne(){
	if( size() != 1 ) return false;
	if( firstElement().size() != 1 ) return false;
	if( !firstElement().elementAt( 0 ).equals( "1" ) ) return false;
	return true;
    }

    /**
     * Test whether this matches zero
     * @return <em>true</em> or <em>false</em> according as this matches zero or not
     */
    public boolean isZero(){
	if( size() != 1 ) return false;
	if( firstElement().size() != 1 ) return false;
	if( !firstElement().elementAt( 0 ).equals( "0" ) ) return false;
	return true;
    }   

    /**
     * Test whether this matches minus 1
     * @return <em>true</em> or <em>false</em> according as this matches minus 1 or
     * not
     */
    public boolean isMinusOne(){
	if( size() != 1 ) return false;
	if( firstElement().size() != 2 ) return false;
	if( !firstElement().elementAt( 0 )
	    .equals( jscicalc.complex.DoubleFormat.minus.elementAt( 0 ) ) )
	    return false;
	if( !firstElement().elementAt( 1 ).equals( "1" ) ) return false;
	return true;
    }
    /**
     * Remove double superscripts.
     */
    public void removeDoubleSuperscripts(){
	int superscriptLevel = 0;
	for( java.util.Vector<String> i : this ){
	    for( java.util.ListIterator<String> j = i.listIterator(); j.hasNext(); ){
		String s = j.next();
		// check if we have found a <sup>
		if( s.startsWith( "<sup>" )){
		    if( superscriptLevel > 0 ){
			//replace: FIXME -- correct but crude
			s = s.substring( 5 );
			j.set( "^" );
			j.add( "(" );
			j.previous();
			j.next();
			j.add( s );
			j.previous();
			j.next();
		    }
		    // now increment superscriptLevel
		    ++superscriptLevel;
		}
		// check if we have found a </sup>
		if( s.endsWith( "</sup>" )){
		    // decrement superscriptLevel
		    --superscriptLevel;
		    if( superscriptLevel > 0 ){
			//replace: FIXME -- correct but crude
			s = s.substring( 0, s.length() - 6 );
			j.set( s );
			j.add( ")" );
		    }
		}
	    }
	}
    }
    private static final long serialVersionUID = 1L;   
}
