/** @file
 * Copyright (C) 2004&ndash;5, 2008 John D Lamb (J.D.Lamb@btinternet.com)
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
import jscicalc.pobject.PObject;

/**
 * This class forms a base class for scrollable labels. The original scrollable label
 * was the EntryLabel, but we also want the DisplayLabel to be scrollable. Hence,
 * a base class can provide common functionality.
 *
 * @author J.&nbsp;D.&nbsp;Lamb 
 * @version $Revision: 1.2 $
 */
public abstract class ScrollableLabel extends javax.swing.JViewport {
    
    /**
     * The constructor defines an interface to the DisplayPanel object.
     * @param panel The display panel (we read values from CalculatorApplet through
     * it)
     */
    public ScrollableLabel( ReadOnlyDisplayPanel panel, Navigator navigator ){
	setBackground( java.awt.Color.white );
	jPanel = new WhitePanel();
	textPane = new javax.swing.JTextPane();
	textPane.setContentType( "text/html" );
	textPane.setEditable( false );
	this.navigator = navigator;
	textPane.setNavigationFilter( navigator );
 	jPanel.add( textPane );
 	setView( jPanel );
	expression = "";
	dotPosition = 0;
	newExpression = true;
	this.panel = panel;
	update( false );
	backward = textPane.getActionMap().get( "caret-backward" );
	forward = textPane.getActionMap().get( "caret-forward" );
    }

    /**
     * Update the display. The parameter is probably redundant since we can
     * get the value of on indirectly from the CalculatorApplet object.
     * @param on if <em>false</em> the calculator is switched off and so we don&rsquo;t
     * display anything; otherwise redisplay expression.
     */
    public abstract void update( boolean on );

    /**
     * Move the Caret left if possible.
     * @see LeftButton
     * @see Navigator
     */
    public void left(){ 
	//System.out.println( "###left" );
	int pos = textPane.getCaret().getDot();
	java.util.ListIterator<Integer> i;
	for( i = navigator.dots().listIterator(); i.hasNext(); ){
	    int p = i.next();
	    if( p >= pos ){
		i.previous();
		break;
	    }
	}
	dotPosition = i.hasPrevious() ? i.previous() : 0;
    }

    /**
     * Move the Caret right if possible.
     * @see RightButton
     * @see Navigator
     */
    public void right(){ 
	//System.out.println( "###right" );
	int pos = textPane.getCaret().getDot();
	java.util.ListIterator<Integer> i;
	for( i = navigator.dots().listIterator( navigator.dots().size() );
	     i.hasPrevious(); ){
	    int p = i.previous();
	    if( p <= pos ){
		i.next();
		break;
	    }
	}
	dotPosition = i.hasNext() ? i.next() : navigator.dots().lastElement();
    }

    /**
     * Get the Caret position. I&rsquo;m not quite sure how Caret positions work because
     * Sun&rsquo;s documentation isn&rsquo;t crytal clear on this,
     * but we can move Carets without
     * any problems.
     * @return Caret position
     */
    protected int getDotPosition(){
	if( navigator == null )
	    System.out.println( "ScrollableLabel.getDotPosition(): navigator == null" );
	int p = navigator.dots().indexOf( textPane.getCaret().getDot() );
	return p == -1 ? 0 : p;
    }

    /**
     * Note that we have a new expression (in case we&rsquo;re asked to update the
     * panel). This is useful if we&rsquo;ve just evaluated an expression but not cleared
     * the panel. Then the Parser and this object will not match and we must
     * be aware of this to avoid problems.
     */
    public void newExpression(){
	newExpression = true;
	setCaretVisible( false );
    }

    /** 
     * Use to update whether or not caret is shown.
     */
    public void updateCaretVisibility(){
	if( caretVisible && panel.hasCaret( this ) ){
	    textPane.getCaret().setVisible( true );
	} else {
	    textPane.getCaret().setVisible( false );
	}
	textPane.getCaret().setDot( dotPosition ); 
    }

    /** 
     * Change whether or not the caret should be shown, assuming permission is given.
     * @param b <em>true</em> if caret should be shown; <em>false</em> otherwise
     */
    protected void setCaretVisible( boolean b ){
	caretVisible = b;
	updateCaretVisibility();
    }

    @Override public void paintComponent( java.awt.Graphics g ){
	java.awt.Graphics2D g2 = (java.awt.Graphics2D)g;
	g2.setRenderingHint( java.awt.RenderingHints.KEY_ANTIALIASING,
			     java.awt.RenderingHints.VALUE_ANTIALIAS_ON );
	super.paintComponent( g );
    }

    /**
     * Use to show if caret should be visible in this object, assuming object has
     * permission to show it.
     */
    protected boolean caretVisible;
    /**
     * The NavigationFilter we use to make sure Caret is always sensibly placed.
     */
    protected Navigator navigator;
    /**
     * The expression (an HTML string).
     */
    protected String expression;
    /**
     * The current Caret position (probably only meaningful internally).
     */
    protected int dotPosition;
    /**
     * A JPanel we use as part of this component.
     */
    protected javax.swing.JPanel jPanel;
    /**
     * Allows us to scroll through the expression and add some simple editing
     * methods.
     */
    protected javax.swing.JTextPane textPane;
    /**
     * Interface to main display panel
     * (good for accessing other parts of the calculator).
     */
    protected ReadOnlyDisplayPanel panel;
    /**
     * Have we just evaluated an expression so that Parser is cleared but
     * we haven&rsquo;t
     * yet cleared the expression in this display?
     */
    protected boolean newExpression;
    /**
     * Not used.
     */
    protected javax.swing.Action backward;
    /**
     * Not used.
     */
    protected javax.swing.Action forward;
    private static final long serialVersionUID = 1L;   
}
	
