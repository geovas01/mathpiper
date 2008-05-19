/** @file
 * Copyright (C) 2003&ndash;4 John D Lamb (J.D.Lamb@btinternet.com)
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
import jscicalc.pobject.PObject;

/**
 * This class is responsible for the LCD-like display in the calculator. When
 * a particular CalculatorPanel is made current by CalculatorApplet.setDisplayPanel()
 * through CalculatorPanel.setDisplayPanel() the instance of this class is attached
 * to the the CalculatorPanel. I&rsquo;m not sure if this is handled entirely correctly
 * yet because the DisplayPanel has actually been added to more than one
 * CalculatorPanel, which I think swing doesn&rsquo;t permit. However, it works provided
 * we reattach it.
 *
 * 
 *
 * @author J.&nbsp;D.&nbsp;Lamb 
 * @version $Revision: 1.8 $
 */
public class DisplayPanel extends javax.swing.JPanel
    implements ReadOnlyDisplayPanel {
    
    /**
     * The constructor takes a ReadOnlyCalculatorApplet so that it has
     * an interface to the CalculatorApplet object. It doesn&rsquo;t need to can modify
     * the CalculatorApplet object and so it doesn&rsquo;t need access to all members.
     * Forby, despite its general moralistic tendencies, Java doesn&rsquo;t let you
     * specify that an object is const.
     * @param applet The CalculatorApplet object (accessed through a read-only
     * interface)
     */
    public DisplayPanel( final ReadOnlyCalculatorApplet applet ){
	this.applet = applet;
	entryLabel = new EntryLabel( this );
	displayLabel = new DisplayLabel( this );
	extraPanel = new ExtraPanel( this );
	leftPanel = new MiniPanel( true );
	rightPanel = new MiniPanel( false );
 	add( entryLabel );
   	add( displayLabel );
  	setCaretToEntry(); // initialise Caret
    	add( extraPanel );
    	add( leftPanel );
    	add( rightPanel );
    }


    /**
     * Set up the panel. Everything is kept in place by a SpringLayout.
     * @see CalculatorApplet.getFrameWidth()
     * @see CalculatorApplet.getFrameHeight()
     */
    public void setUp(){
	/* set up basic layout */
	java.awt.Insets insets = getInsets();
	int ib = insets.bottom;
	int il = insets.left;
	int ir = insets.right;
	int it = insets.top;

	//int a = (int)(0.5 * getApplet().extraTextSize());
	int b = getApplet().strutSize() + 4 * getApplet().minSize() +
	    6 * getApplet().buttonWidth();
	int c = getApplet().displayHeight(); 
	//int d = (int)(0.15 * b + 0.5);
	int d = (int)(0.09 * b + 0.5);

	int v = (int)(0.125 * getApplet().extraTextSize() + 0.5);
	int h = 2 * v;
	int dh = (int)( 0.58 * (c - 3 * v) + 0.5);
	int m = (int)(0.1 * dh + 0.5);

	// vertical spacers
	javax.swing.Spring vSpring = javax.swing.Spring.constant( v, v, v );
	// horizontal spacers
	javax.swing.Spring hSpring = javax.swing.Spring.sum( vSpring, vSpring );
	// extra panel width
	javax.swing.Spring eSpring = javax.swing.Spring.constant( d );
	// display panel height
	javax.swing.Spring dSpring = javax.swing.Spring.constant( dh );
	// mini panel width
	javax.swing.Spring mSpring = javax.swing.Spring.constant( m );
	// space to right of entry
	javax.swing.Spring rSpring = javax.swing.Spring.sum( mSpring, hSpring );
	// minimum (zero?) space
	javax.swing.Spring zSpring = javax.swing.Spring.constant( 0, 0, 2 );

 	javax.swing.SpringLayout layout = new javax.swing.SpringLayout();
 	setLayout( layout );
 	javax.swing.SpringLayout.Constraints constraints = null;

	// entryLabel
 	constraints = layout.getConstraints( entryLabel );
  	constraints.setWidth( javax.swing.Spring.constant( b - 2 * h - m ) );
	constraints.setHeight( javax.swing.Spring.constant( c - 2 * v - dh -ib - it ) );
 	layout.putConstraint( javax.swing.SpringLayout.NORTH, entryLabel,
 			      vSpring, javax.swing.SpringLayout.NORTH, this );
 	layout.putConstraint( javax.swing.SpringLayout.WEST, entryLabel,
 			      hSpring, javax.swing.SpringLayout.WEST, this );
 	layout.putConstraint( javax.swing.SpringLayout.EAST, entryLabel,
 			      javax.swing.Spring.minus( rSpring ),
			      javax.swing.SpringLayout.EAST, this );
	// extraPanel
 	constraints = layout.getConstraints( extraPanel );
  	constraints.setWidth( eSpring );
	constraints.setHeight( dSpring );
 	layout.putConstraint( javax.swing.SpringLayout.NORTH, extraPanel,
 			      zSpring, javax.swing.SpringLayout.SOUTH, entryLabel );
   	layout.putConstraint( javax.swing.SpringLayout.SOUTH, extraPanel,
   			      javax.swing.Spring.minus( vSpring ),
			      javax.swing.SpringLayout.SOUTH, this );
  	layout.putConstraint( javax.swing.SpringLayout.WEST, extraPanel,
  			      hSpring, javax.swing.SpringLayout.WEST, this );
 	// leftPanel
 	constraints = layout.getConstraints( leftPanel );
  	constraints.setWidth( mSpring );
	constraints.setHeight( dSpring );
  	layout.putConstraint( javax.swing.SpringLayout.NORTH, leftPanel,
  			      zSpring, javax.swing.SpringLayout.SOUTH, entryLabel );
  	layout.putConstraint( javax.swing.SpringLayout.SOUTH, leftPanel,
  			      javax.swing.Spring.minus( vSpring ),
			      javax.swing.SpringLayout.SOUTH, this );
  	layout.putConstraint( javax.swing.SpringLayout.WEST, leftPanel,
  			      zSpring, javax.swing.SpringLayout.EAST, extraPanel );
  	// displayLabel
 	constraints = layout.getConstraints( displayLabel );
	constraints.setHeight( dSpring );
	constraints.setWidth( javax.swing.Spring
			      .constant( b - d - 2 * m - 2 * h -il -ir  ) );
  	layout.putConstraint( javax.swing.SpringLayout.NORTH, displayLabel,
  			      zSpring, javax.swing.SpringLayout.SOUTH, entryLabel );
  	layout.putConstraint( javax.swing.SpringLayout.SOUTH, displayLabel,
  			      javax.swing.Spring.minus( vSpring ),
			      javax.swing.SpringLayout.SOUTH, this );
  	layout.putConstraint( javax.swing.SpringLayout.WEST, displayLabel,
  			      zSpring, javax.swing.SpringLayout.EAST, leftPanel );
 	constraints = layout.getConstraints( displayLabel );
  	//constraints.setWidth( zSpring );
	constraints.setHeight( dSpring );
  	// rightPanel
 	constraints = layout.getConstraints( rightPanel );
  	constraints.setWidth( mSpring );
	constraints.setHeight( dSpring );
  	layout.putConstraint( javax.swing.SpringLayout.NORTH, rightPanel,
  			      zSpring, javax.swing.SpringLayout.SOUTH, entryLabel );
  	layout.putConstraint( javax.swing.SpringLayout.SOUTH, rightPanel,
  			      javax.swing.Spring.minus( vSpring ),
			      javax.swing.SpringLayout.SOUTH, this );
  	layout.putConstraint( javax.swing.SpringLayout.WEST, rightPanel,
  			      zSpring, javax.swing.SpringLayout.EAST, displayLabel );
  	layout.putConstraint( javax.swing.SpringLayout.EAST, rightPanel,
  			      javax.swing.Spring.minus( hSpring ),
			      javax.swing.SpringLayout.EAST, this );
     }

    /**
     * This probably does nothing.
     * I&rsquo;m not quite sure I've got this right. I'm trying to make sure the
     * background is always white: probably even that should be replaced by a
     * specified colour stored in CalculatorApplet.
     * @param graphics The graphics context required.
     */
     public void paintComponent( java.awt.Graphics graphics ){
	 graphics.setColor( java.awt.Color.WHITE );
	 graphics.fillRect( 0, 0, getWidth(), getHeight() );
     }
    
    /**
     * Used by CalculatorApplet in response to a LeftButton Action to ask
     * the EntryLabel to move the caret on place left. It&rsquo;s important
     * CalculatorApplet controls this as it&rsquo;s the only thing that can read the
     * Parser and so know whether the caret can move left.
     */
    public void left(){
	labelWithCaret.left();
    }
    
    /**
     * Used by CalculatorApplet in response to a RightButton Action to ask
     * the EntryLabel to move the caret on place right. It&rsquo;s important
     * CalculatorApplet controls this as it&rsquo;s the only thing that can read the
     * Parser and so know whether the caret can move right.
     */
    public void right(){
	labelWithCaret.right();
    }
    
    /**
     * Updates the panel. The second parameter is not used but in principle could
     * let us update only the EntryPanel and not the ExtraPanel. We sometimes don&rsquo;t
     * want to update the EntryPanel immediately. For example, when we evaluate an
     * expression, we clear the expression in the parser but wait until we start
     * typing a new expression before updating the EntryPanel.
     * @param entry Should we update EntryPanel?
     * @param extra Should we update ExtraPanel? (ignored anyway)
     */
    public void update( boolean entry, boolean extra ){
	if( entry ){
	    //setCaretToEntry();
	    entryLabel.update( on );
	}
	displayLabel.update( on );
	extraPanel.repaint();
	LeftOrRight leftOrRight = LeftOrRight.NEITHER;
	if( displayLabelHasCaret() ){
	    leftOrRight = displayLabel.getScrollDirections();
	}
	switch( leftOrRight ){
	case LEFT:
	    leftPanel.setIlluminated( true );
	    rightPanel.setIlluminated( false );
	    break;
	case RIGHT:
	    leftPanel.setIlluminated( false );
	    rightPanel.setIlluminated( true );
	    break;
	case BOTH:
	    leftPanel.setIlluminated( true );
	    rightPanel.setIlluminated( true );
	    break;
	case NEITHER:
	    leftPanel.setIlluminated( false );
	    rightPanel.setIlluminated( false );
	    break;
	default:
	    leftPanel.setIlluminated( false );
	    rightPanel.setIlluminated( false );
	    break;
	}
	leftPanel.repaint();
	rightPanel.repaint();
    }

    /**
     * Find if it is possible to scroll within the display label
     * @return <em>true</em> or <em>false</em> according as we can or cannot
     * scroll the display label
     */
    final boolean displayLabelScrollable(){
	return displayLabel.getScrollDirections() != LeftOrRight.NEITHER;
    }

    /**
     * This was designed to collect an Action so we could move the Caret from 
     * within the CalculatorApplet, but I couldn&rsquo;t get it to work so used left()
     * instead.
     * @return An Action: move Caret left
     */
    public javax.swing.Action backward(){
	return entryLabel.backward();
    }

    /**
     * Set the current expression. We pass a Parser because this class shouldn&rsquo;t
     * access the Parser object directly.
     * @param parser The Parser, which contains the expression
     */
    public void setExpression( Parser parser ){
	entryLabel.setExpression( parser );
    }
    
    /**
     * Sets a new expression in EntryLabel. This doesn&rsquo;t immediately clear the old
     * expression but does disable Caret movements.
     */
    public void newExpression(){
	entryLabel.newExpression();
    }

    /**
     * Clears the current expression and the Parser. Probably it should only clear
     * the expression because CalculatorApplet should be the only object changing
     * the state of the parser.
     * @param parser The Parser, which contains the expression
     */
    public void clear( Parser parser ){
	entryLabel.clear( parser );
    }

    /**
     * Deletes the PObject at the current Caret position. Probably it should only
     * change the expression because CalculatorApplet should be the only object
     * changing the state of the parser.
     * @param parser The Parser, which contains the expression
     */
    public void delete( Parser parser ){
	entryLabel.delete( parser );
    }
	
    /**
     * Inserts a PObject at the current Caret position. Probably it should only
     * change the expression because CalculatorApplet should be the only object
     * changing the state of the parser.
     * @param p The PObject: typically generated by a CalculatorButton
     * @param parser The Parser, which contains the expression
     */
    public void insert( PObject p, Parser parser ){
	entryLabel.insert( p, parser );
    }
	
    /**
     * Set state (on/off). Probably should always get this state from
     * CalculatorApplet because the states should match.
     * @param value <em>true</em> for on, <em>false</em> for off.
     */
    public void setOn( boolean value ){
	on = value;
    }

    /**
     * Get state (on/off). Probably should always get this state from
     * CalculatorApplet because the states should match.
     * @return <em>true</em> for on, <em>false</em> for off.
     */
    public boolean getOn(){
	return on;
    }

    /**
     * Set the diplayLabel value to match the OObject in the applet
     */
    public void setValue(){
	displayLabel.setNewExpression();
    }

    /**
     * Get an interface for the CalculatorApplet object.
     * @return an interface for the CalculatorApplet object.
     */
    public ReadOnlyCalculatorApplet getApplet(){
	return applet;
    }
    /**
     * Udes by CalculatorApplet to get DisplayLabel.
     */
    public final DisplayLabel getDisplayLabel(){
	return displayLabel;
    }

    /** 
     * Set Caret to entry
     */
    public void setCaretToEntry(){
	labelWithCaret = entryLabel;
	displayLabel.updateCaretVisibility();
	entryLabel.updateCaretVisibility();
    }

    /** 
     * Set Caret to display
     */
    public void setCaretToDisplay(){
	labelWithCaret = displayLabel;
	entryLabel.updateCaretVisibility();
	displayLabel.updateCaretVisibility();
    }

    /** 
     * Find if scrollableLabel has the Caret. This allows us to query the interface and
     * find whether EntryPanel or DisplayPanel has permission to display a Caret
     * @param scrollableLabel The Scrollable label we wish to know about
     * @return <em>true</em> or <em>false</em> according as scrollableLabel has or has
     * not permission to show a Caret
     */
    public boolean hasCaret( final ScrollableLabel scrollableLabel ){
	return labelWithCaret == scrollableLabel;
    }

    /** 
     * Find if displayLabel has the Caret. This allows us to query the interface and
     * find whether EntryPanel or DisplayPanel has permission to display a Caret
     * @return <em>true</em> or <em>false</em> according as displayLabel has or has
     * not permission to show a Caret
     */
    public boolean displayLabelHasCaret(){
	return hasCaret( displayLabel );
    }

    private ScrollableLabel labelWithCaret;

    /**
     * The entry label: shows what you&rsquo;ve typed.
     */
    private EntryLabel entryLabel;
    /**
     * The display label: shows results of calculations.
     */
    private DisplayLabel displayLabel;
    /**
     * The panel that shows calculator state: shift, Stat mode, degrees or radians,
     * Base and the like.
     */
    private ExtraPanel extraPanel;
    /**
     * Panel to show left movement of display is possible
     */
    private MiniPanel leftPanel;
    /**
     * Panel to show right movement of display is possible
     */
    private MiniPanel rightPanel;
    /**
     * Interface to the CalculatorApplet object that allows us to read values but
     * not to change their state.
     */
    private final ReadOnlyCalculatorApplet applet;
    /**
     * State of calculator on or off. This should be removed because only the
     * CalculatorApplet should have this information.
     */
    private boolean on;
    private static final long serialVersionUID = 1L;   
}
	
