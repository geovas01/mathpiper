/** @file
 * Copyright (C) 2004&ndash;5 John D Lamb (J.D.Lamb@btinternet.com)
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
import jscicalc.OObject;

/**
 * This is the part of the DisplayPanel that shows the output of calculations.
 *
 * @author J.&nbsp;D.&nbsp;Lamb 
 * @version $Revision: 1.10 $
 */
public class DisplayLabel extends ScrollableLabel {
    
    /**
     * Constructor. The object constructed should can read but not modify
     * the DisplayPanel.
     * @param panel The panel containing this
     */
    public DisplayLabel( ReadOnlyDisplayPanel panel ){
	super( panel, new DisplayNavigator() );
	scrollData = new ScrollData( this );
	displayCaret = new DisplayCaret( scrollData );
	setNewExpression();
	textPane.setCaret( displayCaret );
	clearDisplay = false;
    }

    /**
     * Set a new expression in the label.
     */
    public void setNewExpression(){
	int m = getDigits();
	int precision = 10;
	final Base b = getBase();
	final Notation n = panel.getApplet().getNotation();
	double factor = 1;
	if( panel.getApplet().getAngleType() == AngleType.DEGREES )
	    factor = 1;//180 / StrictMath.PI;
	OObject o = panel.getApplet().getValue();
	if( o != null )
	    setExpression( o, m, precision, b, n, factor );
	synchronized( displayCaret ){
	    displayCaret.updateFlag = true;
	}
    }

    /** 
     * Change whether or not the caret should be shown, assuming permission is given.
     * This overrides base function so that caret is always invisible.
     * @param b <em>true</em> if caret should be shown; <em>false</em> otherwise
     */
    protected void setCaretVisible( boolean b ){
	caretVisible = false;
	updateCaretVisibility();
    }
    /**
     * Update the display. The state should depend on whether the calculator
     * is switched on and we ough to read this indirectly rather than pass
     * the state as a parameter.
     * @param on <em>true</em> if calculator switched on.
     */
    public void update( boolean on ){
	if( on ){
	    if( panel.getApplet().getMode() == 0 ){
		synchronized( displayCaret ){
		    if( clearDisplay ) setNewExpression();
		    textPane.setText( expression );
		    textPane.getCaret().setDot( dotPosition ); 
		    //displayCaret.updateFlag = true;
		    // 	    try {
		    // 		displayCaret.wait();
		    // 	    } catch( InterruptedException e ){
		    // 		System.out.println( e );
		    // 	    }
		}
	    } else {
		clear();
		textPane.setText( expression );
	    }
	} else {
	    clear();
	    textPane.setText( expression );
	}
    }
    /**
     * Set a new expression from an OObject.
     * @param o The OObject to be displayed
     * @param m The number of digits (used by Complex)
     * @param precision The precision (used by Complex)
     * @param b The Base for numbers
     * @param n The Notation for numbers
     * @param factor Not currently used
     */
    public synchronized void setExpression( OObject o, int m, int precision, Base b,
					    Notation n, double factor ){
	StringBuilder sb = new StringBuilder();
	sb.append( "<html><p style=\"font-size:" );
	sb.append( Float.toString( panel.getApplet().displayTextSize() ) );
	sb.append( "pt;font-family:" );
	sb.append( javax.swing.UIManager.getFont( "Label.font" ).getName() );
	sb.append( "\">" );
	sb.append( start );
	navigator.dots().clear();
	int i = 53;
	navigator.dots().add( i );
	StringArray stringArray = o.toHTMLStringVector( m, precision, b, n, factor );
	// fix superscripts
	stringArray.removeDoubleSuperscripts();
	for( java.util.Vector<String> v : stringArray ){
	    for( String s : v ){
		sb.append( s );
	    }
	    i += v.size();
	    navigator.dots().add( i );
	}
	sb.append( "</p></html>" );	
	expression = sb.toString();
	dotPosition = i;
    }

    /**
     * Clear expression. Useful in response to OnButton, for example.
     */
    public synchronized void clear(){
	expression = start;
	dotPosition = 54;
	clearDisplay = true;
    }

    /**
     * The DisplayFormat object has a Base, which ought to be the same as the
     * CalculatorApplet Base and we use this to get it.
     * @return The Base of the DisplayFormat object.
     */
    public Base getBase(){
	return panel.getApplet().getBase();
    }

    /**
     * Get number of digits used. I think this is a default number
     * so we don&rsquo;t overrun the display width.
     * @return A number representing the number of digits in the display
     */
    public int getDigits(){
	return DIGITS;
    }
    /**
     * Scroll left if possible.
     * @see LeftButton
     * @see Navigator
     */
    public void left(){ 
	scrollData.left();
    }

    /**
     * Scroll right if possible.
     * @see RightButton
     * @see Navigator
     */
    public void right(){ 
	scrollData.right();
    }

    /**
     * Find the directions in which it is possible to scroll.
     * @return The directions in which it is possible to scroll
     */
    final LeftOrRight getScrollDirections(){
	return scrollData.getScrollDirections();
    }

    /**
     * This is a local class that implements a default Caret. It&rsquo;s purpose
     * is purely to get access to modelToView at a point when the textpane and caret
     * cannot move and so calculate distances for scrolling
     */
    private class DisplayCaret extends javax.swing.text.DefaultCaret {
	/**
	 * The constructor gets a ScrollData object so that when the Caret gets
	 * repainted the ScrollData data can be calculated.
	 * @param scrollData The ScrollData object
	 */
	public DisplayCaret( ScrollData scrollData ){
	    super();
	    this.scrollData = scrollData;
	    updateFlag = false;
	}
	/**
	 * Standard paint method with one addition: if updateFlag has been set to true
	 * then ScrollData data is calculated immediately after the paint method of the
	 * superclass is called. This allows us to get distances without risk of
	 * values changing because of events in another thread. In fact, it doesn&rsquo;t
	 * fully account for concurrency problems: I haven&rsquo;t checked that there are
	 * no possible circumstances under which the data in ScrollData might be required
	 * before this function call
	 * @param graphics Standard parameter
	 */
	public void paint( java.awt.Graphics graphics ){
	    synchronized( this ){
		super.paint( graphics );
		if( updateFlag ) scrollData.update();
		updateFlag = false;
		notify();
	    }
	}
	/**
	 * Keep a reference to the ScrollData object
	 */
	private ScrollData scrollData;
	/**
	 * Set this flag to true to force an update of calculated distances used for
	 * scrolling DisplayLabel
	 */
	public boolean updateFlag;
	private static final long serialVersionUID = 1L;   
    }
    
    /**
     * Keep a reference to the only Caret
     */
    private DisplayCaret displayCaret;

    /**
     * This class is used to hold the information required for scrolling left and right.
     */
    private class ScrollData {
	/**
	 * The constructor takes information from a DisplayLabel
	 * @param displayLabel The object used for construction
	 */
	public ScrollData( DisplayLabel displayLabel ){
	    // references
	    dots = displayLabel.navigator.dots();
	    this.displayLabel = displayLabel;
	    // set up
	    distances = new java.util.Vector<Integer>();
	    atRight = true; // intially always.
	}

	/**
	 * This function calculates distances used by left() and right() but should never
	 * be called directly. Instead, set the updateFlag of displayCaret and the values
	 * get calculated when the Caret is repainted.
	 */
	public void update(){
	    distances = new java.util.Vector<Integer>();
	    if( dots.isEmpty() ) return;
	    java.util.ListIterator<Integer> i = dots.listIterator();
	    java.awt.Rectangle r = null;
	    try { r = textPane.modelToView( i.next() ); }
	    catch( javax.swing.text.BadLocationException e ){ return; }
	    if( r == null ) return;
	    int p = r.x;
	    while( i.hasNext() ){
		try { r = textPane.modelToView( i.next() ); }
		catch( javax.swing.text.BadLocationException e ){ return; }
		if( r == null ) return;
		int q = r.x; 
		distances.add( q - p );
		p = q;
	    }
	    // test
// 	    System.out.print( "dots = " );
// 	    for( i = dots.listIterator(); i.hasNext(); ){
// 		System.out.print( i.next() );
// 		System.out.print( " " );
// 	    }
// 	    System.out.println();
// 	    System.out.print( "distances = " );
// 	    for( i = distances.listIterator(); i.hasNext(); ){
// 		System.out.print( i.next() );
// 		System.out.print( " " );
// 	    }
// 	    System.out.println();
	}

	/**
	 * Find the directions in which it is possible to scroll.
	 * @return The directions in which it is possible to scroll
	 */
	final LeftOrRight getScrollDirections(){
	    if( dots.isEmpty() || distances.size() != dots.size() - 1 )
		return LeftOrRight.NEITHER; 
	    boolean left = false;
	    boolean right = false;
	    if( atRight ){
		right = dots.lastElement() > displayLabel.dotPosition;
		double distance = 0;
		java.util.ListIterator<Integer> i = dots.listIterator();
		java.util.ListIterator<Integer> j = distances.listIterator();
		while( j.hasNext() ){
		    if( i.next() >= displayLabel.dotPosition ) break;
		    distance += j.next();
		}
		left = distance > displayLabel.getWidth();
	    } else {
		left = dots.firstElement() < displayLabel.dotPosition;
		double distance = 0;
		java.util.ListIterator<Integer> i = dots.listIterator( dots.size() );
		java.util.ListIterator<Integer> j
		    = distances.listIterator( distances.size() );
		while( j.hasPrevious() ){
		    if( i.previous() <= displayLabel.dotPosition ) break;
		    distance += j.previous();
		}
		right = distance > displayLabel.getWidth();
	    }
	    if( left && right )
		return LeftOrRight.BOTH;
	    else if( left )
		return LeftOrRight.LEFT;
	    else if( right )
		return LeftOrRight.RIGHT;
	    else
		return LeftOrRight.NEITHER;
	}

	/**
	 * Scroll left if possible.
	 * @see LeftButton
	 * @see Navigator
	 */
	public void left(){ 
	    int width = displayLabel.getWidth();
	    if( atRight ){
		// first, find distance to leftmost position
		int distance = 0;
		java.util.ListIterator<Integer> i = dots.listIterator();
		java.util.ListIterator<Integer> j = distances.listIterator();
		while( j.hasNext() ){
		    if( i.next() >= displayLabel.dotPosition ) break;
		    distance += j.next();
		}
		if( width >= distance ) return; // nothing to do
		// now move back along until we find a position that is not hidden
		i = dots.listIterator();
		j = distances.listIterator();
		int position = i.next(); // position of leftmost element
		while( j.hasNext() ){ // should always be true in this loop
		    int d = j.next(); 
		    if( distance - d < width ) break;
		    distance -= d; // shorten
		    position = i.next(); // get dot position
		}
		displayLabel.dotPosition = position;
		atRight = false; // we're now at left
	    } else {
		java.util.ListIterator<Integer> i = dots.listIterator();
		while( i.hasNext() ){
		    if( i.next() >= displayLabel.dotPosition ) break;
		}
		i.previous();
		if( !i.hasPrevious() ) return; // at leftmost position already
		displayLabel.dotPosition = i.previous();
	    }
	    // set new dot position
	    displayLabel.textPane.getCaret().setDot( displayLabel.dotPosition ); 
	}

	/**
	 * Scroll right if possible.
	 * @see RightButton
	 * @see Navigator
	 */
	public void right(){ 
	    int width = displayLabel.getWidth();
	    if( !atRight ){
		// first, find distance to rightmost position
		int distance = 0;
		java.util.ListIterator<Integer> i = dots.listIterator( dots.size() );
		java.util.ListIterator<Integer>
		    j = distances.listIterator( distances.size() );
		while( j.hasPrevious() ){
		    if( i.previous() <= displayLabel.dotPosition ) break;
		    distance += j.previous();
		}
		if( width >= distance ) return; // nothing to do
		// now move back along until we find a position that is not hidden
		i = dots.listIterator( dots.size() );
		j = distances.listIterator( distances.size() );
		int position = i.previous(); // position of leftmost element
		while( j.hasPrevious() ){ // should always be true in this loop
		    int d = j.previous(); 
		    if( distance - d < width ) break;
		    distance -= d; // shorten
		    position = i.previous(); // get dot position
		}
		displayLabel.dotPosition = position;
		atRight = true; // we're now at right
	    } else {
		java.util.ListIterator<Integer> i = dots.listIterator( dots.size() );
		while( i.hasPrevious() ){
		    if( i.previous() <= displayLabel.dotPosition ) break;
		}
		i.next();
		if( !i.hasNext() ) return; // at leftmost position already
		displayLabel.dotPosition = i.next();
	    }
	    // set new dot position
	    displayLabel.textPane.getCaret().setDot( displayLabel.dotPosition ); 
	}
	
	/**
	 * Keep a reference to owning object
	 */
	private DisplayLabel displayLabel;
	/**
	 * Keep a reference to dots
	 */
	private final java.util.Vector<Integer> dots;
	/**
	 * Distances between dot posistion
	 */
	private java.util.Vector<Integer> distances;
	/**
	 * Keep a record of whether dot postion is or is not at right of display.
	 */
	boolean atRight;
    }

    /**
     * Holds data about where the expression is within the Jviewport
     */
    private ScrollData scrollData;

    /**
     * private use for updating after cleared data
     */
    private boolean clearDisplay;
    /**
     * Standard constant for IEEE 754 doubles
     */
    public static final long BIAS = 1023;
    /**
     * Standard constant for IEEE 754 doubles
     */
    public static final long E_MAX = 1023;
    /**
     * Standard constant for IEEE 754 doubles
     */
    public static final long E_MIN = -1022;

    /**
     * Used to set number of digits in display.
     */
    private static final int DIGITS = 20;
    private final static String start
 	= "<sub>&nbsp;</sub>&nbsp;<sup>&nbsp;</sup>&nbsp;&nbsp;&nbsp;" 
	+ "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;"
 	+ "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;"
 	+ "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;"
 	+ "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;";
    private static final long serialVersionUID = 1L;   
}
	
