/** @file
 * Copyright (C) 2004&ndash;5 John D Lamb (J.D.Lamb@btinternet.com)
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
 * This is the component that shows the entry we are typing. For various reasons
 * it takes quite a lot of setting up. We want it to be editable and show HTML
 * output (so, for example, that we can get square root or exponent signs). So we
 * use a JTextPane. But we also want it to be a single line and scrollable; so we
 * encapsulate it in a JViewPort. Finally we want to make sure we only scroll to
 * valid positions (between PObject strings) so we add a NavigationFilter called
 * Navigator. We also need a JPanel just to hold everything together.
 *
 * @author J.&nbsp;D.&nbsp;Lamb 
 * @version $Revision: 1.10 $
 */
public class EntryLabel extends ScrollableLabel {
    
    /**
     * The constructor defines an interface to the DisplayPanel object.
     * @param panel The display panel (we read values from CalculatorApplet through
     * it)
     */
    public EntryLabel( ReadOnlyDisplayPanel panel ){
	super( panel, new EntryNavigator() );
    }

    /**
     * Update the display. The parameter is probably redundant since we can
     * get the value of on indirectly from the CalculatorApplet object.
     * @param on if <em>false</em> the calculator is switched off and so we don&rsquo;t
     * display anything; otherwise redisplay expression.
     */
    public void update( boolean on ){
	//System.out.println( "entrylabel:update" );
	//setFont( getFont().deriveFont( panel.getApplet().entryTextSize() ) );
	StringBuilder text = new StringBuilder();
	text.append( "<html><p style=\"font-size:" );
	text.append( Float
		     .toString( panel.getApplet().entryTextSize() ) );
	text.append( "pt\">" );//font-family=\"Lucida\">" );
	if( on ){
	    if( panel.getApplet().getMode() == 0 ){
		setCaretVisible( true );
 		text.append( expression );
 		text.append( endhtml );
 		textPane.setText( text.toString() );
		//System.out.print( "dot position is " );
		//System.out.println( dotPosition );
 		textPane.getCaret().setDot( dotPosition ); 
	    } else if( panel.getApplet().getMode() == 2 ){
		setCaretVisible( false );
		text.append( "Degrees: 1&nbsp;&nbsp;Radians: 2" );
		text.append( endhtml );
		textPane.setText( text.toString() );
 		textPane.getCaret().setDot( 1 ); 
	    } else if( panel.getApplet().getMode() == 1 ){
		setCaretVisible( false );
		text.append( "Comp: 1&nbsp;&nbsp;Stat: 2" );
		text.append( endhtml );
		textPane.setText( text.toString() );
 		textPane.getCaret().setDot( 1 ); 
	    } else if( panel.getApplet().getMode() == 3 ){
		setCaretVisible( false );
		text.append( "Set size: 0&ndash;" );
		text.append( panel.getApplet().getSizesSize() - 1 );
		text.append( " (current value: " );
		text.append( panel.getApplet().minSize()
			     - panel.getApplet().getMinSize() );
		text.append( ")" );
		text.append( endhtml );
		textPane.setText( text.toString() );
 		textPane.getCaret().setDot( 1 ); 
	    }
	} else {
	    setCaretVisible( false );
	    text.append( endhtml );
	    textPane.setText( text.toString() );
	}
    }
    
    /**
     * Not used. I tried allowing the CalculatorButton to fire an Action to move
     * the Caret left, but it didn&rsquo;t work
     * @return The Action (mmove left)
     */
    public javax.swing.Action backward(){
	return backward;
    }
    
    /**
     * Delete element to left of Caret (if possible).
     * @param parser If we delete we have to change the Parser too
     * @see DelButton
     */
    public synchronized void delete( Parser parser ){
	/* clear selection if any */
	textPane.getCaret().setDot( textPane.getCaretPosition() );
	int position = getDotPosition();
	//System.err.println( textPane.getCaretPosition() );
	//System.err.print( "position = " );
	//System.err.println( position );
	/* put text in textPane */
	PObject p = parser.del( position );
	if( p == null ){
	    dotPosition = navigator.dots().get( position );
	    return;
	}
	//System.out.println( p.name() );
	int length = 0;
	length = p.name_array().length;
	expression = parser.getExpression();
	/* change dots to match */
	int currentDot = navigator.dots().get( position );
	navigator.dots().remove( position );
	for( int i = position; i < navigator.dots().size(); ++i ){
	    int q = navigator.dots().elementAt( i );
	    q = q - length;
	    navigator.dots().setElementAt( q, i );
	}
	/* move dot to required position */
	dotPosition = currentDot - length;
    }
    
    /**
     * Set a new expression from the Parser. Useful if we&rsquo;ve just loaded a new
     * expression from history.
     * @param parser The Parser
     */
    public synchronized void setExpression( Parser parser ){
	expression = parser.getExpression();
	newExpression = false;
	navigator.dots().clear();
	int i = 1;
	navigator.dots().add( i );
	for( PObject pobject : parser.getList() ){
	    i += pobject.name_array().length;
	    navigator.dots().add( i );
	}
	dotPosition = i;
    }

    /**
     * Clear expression. Useful in response to OnButton, for example.
     * @param parser The Parser: we must also clear expression in the Parser
     */
    public synchronized void clear( Parser parser ){
	//System.out.println( "entrylabel:clear" );
	parser.clearExpression();
	expression = "";
	dotPosition = 0;
    }
    
    /**
     * Insert element to right of Caret (if possible).
     * @param p The PObject, usually goten from a CalculatorButton
     * @param parser If we delete we have to change the Parser too
     */
    public synchronized void insert( PObject p, Parser parser ){
	/* clear selection if any */
	textPane.getCaret().setDot( textPane.getCaretPosition() );
	int position = getDotPosition();
	//System.out.println( textPane.getCaretPosition() );
	//System.out.println( p.name() );
	int length = p.name_array().length;
	/* put text in textPane */
	//textPane.replaceSelection( p.name() );
	/* make sure recorded expression matches */
	//System.err.print( "******** " );
	//System.out.println( position );
	if( newExpression ){
	    newExpression = false;
	    navigator.dots().clear();
	    navigator.dots().add( 1 );
	    position = 0;
	}
	parser.add( position, p );
	expression = parser.getExpression();
	/* change dots to match */
	int currentDot = navigator.dots().get( position );
	for( int i = position; i < navigator.dots().size(); ++i ){
	    int q = navigator.dots().elementAt( i );
	    q = q + length;
	    navigator.dots().setElementAt( q, i );
	}
	navigator.dots().insertElementAt( currentDot, position );
	/* move dot to required position */
	dotPosition = currentDot + length;
    }

    /**
     * This pads out the expression and makes it a bit taller if necessary so that
     * new expressions are left-adjusted. Otherwise the display would start in the
     * middle of the JViewPort. Navigator makes sure we can never try to edit this
     * invisible region.
     */
    private final static String endhtml
	= "<sup> </sup><sub>&nbsp;<sub>&nbsp;&nbsp;&nbsp;"
	+ "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;"
	+ "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;"
	+ "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;"
	+ "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;"
	+ "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;"
	+ "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;"
	+ "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;"
	+ "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;"
	+ "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;"
	+ "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;"
	+ "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;"
	+ "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;"
	+ "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;"
	+ "&nbsp;&nbsp;&nbsp;&nbsp;<sup> </sup><sub>&nbsp;</sub></p></html>";
    private static final long serialVersionUID = 1L;   
}
	
