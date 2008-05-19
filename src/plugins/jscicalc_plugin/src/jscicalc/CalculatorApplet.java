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
import jscicalc.pobject.PObject;
import jscicalc.pobject.Numeral;
import jscicalc.pobject.Mode;
import jscicalc.pobject.Mean;
import jscicalc.pobject.StDev;
import jscicalc.pobject.PopStDev;
import jscicalc.button.CalculatorButton;
import jscicalc.complex.Complex;

/**
 * This class is effectively the <em>controller</em> class for the calculator.
 * It creates either a frame or an applet with the calculator in it, stores
 * the results of calculations, a history of calculations done and the state of
 * the calculator. Parser <em>model</em> class, in the sense that it stores and
 * evaluates the current expression. The <em>view</em> of the current expression
 * is handled by DisplayPanel and the classes it contains.
 *
 * If you want to use the calculator as a convenient dialog for calculating values
 * for some JTextComponent, then attach the component with setTextComponent() and 
 * detach it if necessary with setTextComponent( null ).
 *
 * @author J.&nbsp;D.&nbsp;Lamb 
 * @version $Revision: 1.16 $
 */
 public class CalculatorApplet extends javax.swing.JPanel //Note:tk:changed to a non-frame component javax.swing.JApplet
    implements java.awt.event.KeyListener, ReadOnlyCalculatorApplet {

    /**
     * Constructor. This is the constructor we use for the applet.
     */
    public CalculatorApplet() {
	applet = this;
	addMouseListener( new java.awt.event.MouseAdapter() {
		public void mouseClicked( java.awt.event.MouseEvent mouseEvent ){
		    boolean showAbout = mouseEvent.getButton()
			!= java.awt.event.MouseEvent.BUTTON1;
		    if( !showAbout ){
			return;
		    }
		    javax.swing.JOptionPane
			.showMessageDialog( applet,
				"<html>Copyright &#169; 2004&#8211;5, 2007&#8211;5"
                                + ", John D Lamb"
				+ " &#60;J.D.Lamb@btinternet.com&#62;<br>"
				+ "This is free software; see the"
				+ " source for copying "
				+ "conditions.  There is NO "
				+ "warranty;<br>not even for"
				+ " MERCHANTABILITY or FITNESS"
				+ " FOR A PARTICULAR PURPOSE.</html>",
					    "About",
					    javax.swing.JOptionPane
					    .INFORMATION_MESSAGE );
		}
	    } );
    }

    /**
     * Used to construct the applet. Calls setup to do most of the work.
     * @see #setup()
     */
    public void init(){
	// set up GUI
	applet = this;
	frame( null );
	try {
	    javax.swing.SwingUtilities
		.invokeAndWait( new Runnable() {
			public void run(){ setup(); }
		    } );
	} catch( Exception e ){
	    System.err.println( "InterruptedException" );
	}
    }

    /**
     * Use this to construct a calculator. At the moment the args parameter is
     * completely ignored, though there are some useful possibilities. It would be
     * nice if we could specify the size (minSize) by passing some argument.
     * The MODE button currently allows this and a default is hardcoded.
     * @param args Currently ignored
     */
    public static void main( String args[] ){
	javax.swing.SwingUtilities
	    .invokeLater( new Runnable() { public void run() { showFrame(); } } );
    }

    /**
     * Shows the frame. This is used by main to make sure the calculator JFrame
     * is created on the event-dispatching thread if we don&rsquo;t run it as an applet.
     * I haven&rsquo;t checked that this all works as it should.
     */
    private static void showFrame(){
	javax.swing.JFrame frame = createFrame();
	frame.setDefaultCloseOperation( javax.swing.JFrame.EXIT_ON_CLOSE );
	frame.setVisible( true );
    }

    /**
     * Actually create a JFrame so that forby using this as an applet, we can
     * use it as a standalone application. Like init(), this uses setup() to do
     * most of the real work. Additionally it prints out a boilerplate copyright
     * message.
     * @return A JFrame with the calculator in it
     * @see #init()
     * @see #main( String[] )
     * @see #showFrame()
     */
    public static javax.swing.JFrame createFrame(){
	CalculatorApplet main = new CalculatorApplet();
	System.out.println( "Copyright (C) 2004–5, 2007–2008, John D Lamb"
			    + " <J.D.Lamb@btinternet.com>" );
	System.out.println( "This is free software; see the source for copying "
			    + "conditions.  There is NO" );
	System.out.println( "warranty; not even for MERCHANTABILITY"
                            + " or FITNESS FOR A PARTICULAR PURPOSE." );
	javax.swing.JFrame frame
	    = new javax.swing.JFrame( "Scientific Calculator" );
	main.frame( frame );
	main.setup();

	frame.setContentPane( main );
	frame.setResizable( false );
	frame.setUndecorated( false );
	frame.pack();
	return frame;
    }

    /**
     * This sets up the calculator for either the applet of the standalone
     * application. I&rsquo;m not sure why the function is public because it probably
     * should be private.
     */
    public void setup() {
	/* intitially no attached component */
	setTextComponent( null );

	/* set initial value */
	setValue( new Complex() );

	/* set initial sizes */
	setSizes();
	
	/* set initial state */
	parser = new Parser();
	shift = false;
	angleType = AngleType.DEGREES;
	mode = 0;
	stat = false;
	notation = new Notation(); // set default of standard, rectangular, non-complex
	memory = new Complex();
	statMemory = new java.util.Vector<Complex>();
	statMemoryNeg = new java.util.Vector<Complex>();

	//listen for key presses
	addKeyListener( this );
	setFocusable( true );

        removeAll(); //Note:tk:getContentPane().removeAll();
	
	/* set up initial objects */
	displayPanel = new DisplayPanel( this );
	displayPanel
	    .setBorder( new javax.swing.border
			.BevelBorder( javax.swing.border.BevelBorder.LOWERED ) );
	setPanels();

	/* create a DataTransfer so we can copy to system clipboard */
	/* this code does not actually work -- I don't know why */
	dataTransfer = new DataTransfer( this );
	calculatorPanel.getInputMap()
	    .put( javax.swing.KeyStroke
		  .getKeyStroke( java.awt.event.KeyEvent.VK_COPY, 0), "copy" );
	calculatorPanel.getInputMap()
	    .put( javax.swing.KeyStroke
		  .getKeyStroke( java.awt.event.KeyEvent.VK_C,
				 java.awt.event.ActionEvent.CTRL_MASK |
				 java.awt.event.ActionEvent.SHIFT_MASK, false  ),
		  "copy" );
	calculatorPanel.getActionMap().put( "copy", dataTransfer );
	dataTransfer.setEnabled( true );
	
	//set up history
	
	setBase( Base.DECIMAL );
	history = new java.util.Vector<HistoryItem>();
	tempParserList
	    = new HistoryItem( null, getAngleType(), getBase(), getNotation() );
	historyPosition = -1;
	shiftDown = false;
	setOn( true );
	setValue( new Complex( 0 ) );
	updateDisplay( true, true );
	requestFocusInWindow();
    }

    /**
     * The calculator buttons are all set on objects of class CalculatorPanel. I
     * tried first changing the buttons according to context, but redoing 40 buttons
     * or so every time you press shift slows the calculator down visibly. So this
     * is the alternative. The panels also take some time to generate and so they
     * are created on separate threads. This allows us to see and start using the
     * calculator while some of the panels are still being constructed.
     * @see CalculatorPanel
     */
    private void setPanels() {
	displayPanel.setUp();
	calculatorPanels
	    = new java.util.HashMap<SpecialButtonType,CalculatorPanel>();
	for( SpecialButtonType sbt : SpecialButtonType.values() ){
	    CalculatorPanel calculatorPanel
		= CalculatorPanel
		.createPanel( this, sbt, panelColour );
	    calculatorPanels.put( sbt, calculatorPanel );
	}

	setCalculatorPanel( SpecialButtonType.NONE );
    }
    
    /**
     * Choose a CalculatorPanel. You would do this in response to a button
     * press. The most obvious one is the <em>shift</em> button, but we also
     * change panels for <em>Stat</em> mode and when changing base. 
     * @param sbt An enumerated type representing the panel we want
     */
    private void setCalculatorPanel( SpecialButtonType sbt ){
	if( calculatorPanel != null )
		remove( calculatorPanel ); //Note:th:getContentPane().remove( calculatorPanel );
	calculatorPanel = calculatorPanels.get( sbt );
	add( calculatorPanel );//Note:tk:getContentPane().add( calculatorPanel );
	calculatorPanel.setDisplayPanel();
	repaint();
    }

    /**
     * As you might expect this clears the calculator history. The only time we use
     * this is when we switch the calculator off. Otherwise we keep a record of the
     * last few expressions together with information about the calculator state
     * (base, degrees or radians, etc.).
     */
    public void clearHistory(){
	history = new java.util.Vector<HistoryItem>();
	historyPosition = -1;
    }

    /**
     * Moves up the history list (if possible). CalculatorApplet maintains
     * a list of previously evaluated expressions and this function allows
     * you to move backward through them. It&rsquo;s called by UpButton and is
     * particularly useful if you want to reedit some expression.
     * @see #downHistory()
     * @see UpButton
     */
    public void upHistory(){
	if( displayPanel.displayLabelHasCaret() ){
	    setCaretToEntry();
	    //return;
	}
	if( historyPosition >= history.size() - 1 ) return;
	// save old buffer
	if( historyPosition == -1 ){
	    // if error shown clear it.
	    if( getValue() instanceof jscicalc.Error )
		setValue( new Complex() );
	    // copy to temporary buffer
	    tempParserList.list = parser.getList();
	    tempParserList.angleType = getAngleType();
	    tempParserList.base = getBase();
	    tempParserList.notation = getNotation();
	} else {
	    // copy to history buffer
	    history.elementAt( historyPosition ).list = parser.getList();
	    history.elementAt( historyPosition ).angleType = getAngleType();
	    history.elementAt( historyPosition ).base = getBase();
	    history.elementAt( historyPosition ).notation = getNotation();
	}
	// move up history list
	++historyPosition;
	// copy history buffer to parser
	parser.setList( history.elementAt( historyPosition ).list );
	setAngleType( history.elementAt( historyPosition ).angleType  );
	setBase( history.elementAt( historyPosition ).base  );
	setNotation( history.elementAt( historyPosition ).notation  );
	// reset display panel
	displayPanel.setExpression( parser );
    }
    
    /**
     * Moves down the history list (if possible).
     * @see #upHistory()
     * @see DownButton
     * @return <em>true</em> if any action is performed, <em>false</em> otherwise.
     */
    public boolean downHistory(){
	if( historyPosition < 0 ){
	    if( displayPanel.displayLabelScrollable() ){
		setCaretToDisplay();
		return true;
	    }
	    return false;
	}
	// copy to history buffer
	history.elementAt( historyPosition ).list = parser.getList();
	history.elementAt( historyPosition ).angleType = getAngleType();
	history.elementAt( historyPosition ).base = getBase();
	history.elementAt( historyPosition ).notation = getNotation();
	// move down history list
	--historyPosition;
	if( historyPosition == -1 ) {
	    parser.setList( tempParserList.list );
	    setAngleType( tempParserList.angleType );
	    setBase( tempParserList.base );
	    setNotation( tempParserList.notation );
	} else {
	    parser.setList( history.elementAt( historyPosition ).list );
	    setAngleType( history.elementAt( historyPosition ).angleType );
	    setBase( history.elementAt( historyPosition ).base );
	    setNotation( history.elementAt( historyPosition ).notation );
	}
	// reset display panel
	displayPanel.setExpression( parser );
	return true;
    }

    /**
     * This is called by EqualsButton and its subclasses and stores an expression
     * in the history list before evaluating it. This allows the possibility of
     * going back and editing a mistyped expression.
     * @see EqualsButton
     */
    public void pushHistory(){
	java.util.LinkedList<PObject> list = parser.getList();
	if( list.size() == 0 ) return;
	history.add( 0,
		     new HistoryItem( list, getAngleType(), getBase(), getNotation() ) );
	while( history.size() > HISTORY_SIZE )
	    history.removeElementAt( HISTORY_SIZE );
	// Once we have added an item, we want to return to end of history list
	historyPosition = -1;
    }
    
    /**
     * Moves right through the current expression. We need this so that expressions
     * are editable.
     * @see RightButton
     */
    public void right(){
	displayPanel.right();
    }

    /**
     * Moves left through the current expression. We need this so that expressions
     * are editable.
     * @see LeftButton
     */
    public void left(){
	displayPanel.left();
    }

    /**
     * This was an experimental feature that I couldn&rsquo;t get to work. probably it
     * could be removed.
     * @return An Action
     */
    public javax.swing.Action backward(){
	return displayPanel.backward();
    }

    /**
     * When we evaluate an expression, we need to create a new one. This function
     * delegates the job to the DisplayPanel.
     * @see DisplayPanel
     */
    public void newExpression(){
	displayPanel.newExpression();
    }

    public DisplayPanel displayPanel(){
	return displayPanel;
    }

    /**
     * We use this when we want to clear the expresion in the LCD window. As
     * usual we delegate this to the <em>view</em> class. The expression doesn&rsquo;t
     * get cleared until we start typing a new one, which is why we separate this
     * from newexpression().
     * @see DisplayPanel
     * @see #newExpression()
     */
    public void clear(){
	displayPanel.clear( getParser() );
	displayPanel.setExpression( parser );
    }

    /**
     * Delete at the current caret position. Delegated to DisplayPanel.
     * @see DisplayPanel
     */
    public void delete(){
	displayPanel.delete( getParser() );
    }

    /**
     * Copy from displayPanel to system clipboard.
     */
    public void copy(){
	dataTransfer.copy();
    }
    
    /**
     * Insert at the current caret position. Delegated to DisplayPanel.
     * @param p The PObject representing whatever we&rsquo;re trying to insert
     * @see DisplayPanel
     */
    public void insert( PObject p ){
	displayPanel.insert( p, getParser() );
    }
    
    /**
     * Used to tell the <em>view</em> that we want the display changed, for example,
     * in response to pressing the <em>shift</em> button.
     * We sometimes want to update the display but not the expression in the
     * EntryLabel object.
     *
     * Additionally this function will send a copy of the calculated value as a
     * string to any JTextComponent you happen to have attached to the calculator.
     *
     * @param entry Set to <em>true</em> or <em>false</em> according as we want the
     * entry label updated or not
     * @param extra Designed so that we can specify whether the ExtraPanel object
     * should be updated, but not currently used
     * @see DisplayPanel
     * @see EntryLabel
     */
    public void updateDisplay( boolean entry, boolean extra ){
	if( displayPanel != null )
	    displayPanel.update( entry, extra );
	//if( textComponent != null && getValue() instanceof Complex )
	//textComponent.setText( ((Complex)(getValue())).toString() );
	if( textComponent != null )
	    textComponent.setText( getValue().toString() );
	// FIXME
    }
    
    public int displayHeight( int minSize ){
	return dHeight * minSize;
    }

    public int buttonHeight( int minSize ){
	return bHeight * minSize;
    }

    public int buttonWidth( int minSize ){
	return bWidth * minSize;
    }

    public int strutSize( int minSize ){
	return sSize * minSize;
    }

    public int displayHeight(){
	return displayHeight( minSize() );
    }

    public int buttonHeight(){
	return buttonHeight( minSize() );
    }

    public int buttonWidth(){
	return buttonWidth( minSize() );
    }

    public int strutSize(){
	return strutSize( minSize() );
    }

    /**
     * An important little function. This effectively defines the size of the
     * calculator. Values in the range 3-6 seem to work well. The
     * CalculatorApplet object stores this value and uses it to calculate everything
     * else. That way it stays in control of sizes but also allows you to scale
     * the calculator.
     * @param mSize A small integer determining the smallest unit used in drawing
     * the calculator
     */
    public void setMinSize( int mSize ){
	this.mSize = mSize;
	if( frame() == null )
	    return;
	setPanels();
	frame().pack();
    }

    public int minSize(){
	return mSize;
    }

    public float buttonTextSize(){
	return mSize * bTextSize;
    }

    public float entryTextSize(){
	return mSize * eTextSize;
    }

    public float displayTextSize(){
	return mSize * dTextSize;
    }

    public float extraTextSize(){
	return mSize * sTextSize;
    }

    /**
     * Set the <em>shift</em> button to pressed or unpressed. Obviously, it will be
     * called by ShiftButton, but it&rsquo;s also called when a shifted key is pressed and
     * on some other occasions.
     * @param value <em>true</em> or <em>false</em> according as the shift button is
     * pressed or not.
     * @see ShiftButton
     */
    public void setShift( boolean value ){
	shift = value;
	if( shift )
	    if( stat ) 
		setCalculatorPanel( SpecialButtonType.SHIFT_STAT );
	    else	
		switch( getBase() ){
		case DECIMAL:
		    setCalculatorPanel( SpecialButtonType.SHIFT );
		    break;
		default:
		    setCalculatorPanel( SpecialButtonType.SHIFT_HEX );
		}
	else
	    if( stat )
		setCalculatorPanel( SpecialButtonType.STAT );
	    else
		switch( getBase() ){
		case DECIMAL:
		    setCalculatorPanel( SpecialButtonType.NONE );
		    break;
		default:
		    setCalculatorPanel( SpecialButtonType.HEX );
		}
    }

    /**
     * Called when we set the calculator on or off.
     * @param value <em>true</em> for on, <em>false</em> for off.
     * @see OnButton
     * @see OffButton
     */
    public void setOn( boolean value ){
	if( displayPanel != null ){
	    displayPanel.setOn( value );
	    displayPanel.repaint();
	} else
	    System.out.println( "no display panel" );
    }

    public boolean getOn(){
	if( displayPanel != null )
	    return displayPanel.getOn();
	else
	    return false;
    }

    public boolean getShift(){
	return shift;
    }

    public Parser getParser(){
	return parser;
    }

    public OObject getValue(){
	return value;
    }

    /**
     * when an EqualsButton calculates a value it has to transfer it from the Parser
     * object to this and setValue() is the function we use. The object should be
     * a Complex, but we can&rsquo;t represent errors as Complex numbers and it's
     * convenient just to extract what we can as an object and use instanceof to
     * check if needed.
     *
     * You could modify this function to put a value directly into some other
     * Component. One of the design objectives of the calculator is that you should
     * can use it as part of some applet as a pop up calculator. A better approach
     * might be to use EqualsButton to send the value where you want or to tell the
     * Component to set its value using getValue().
     *
     * Have a look at CalcButtonApplet if you want to see how you can make the
     * calculator pop up on demand.
     *
     * @param o The value (should be gotten from the Parser Object)
     * @see Parser
     * @see EqualsButton
     * @see getValue()
     * @see CalcButtonApplet
     */
    public void setValue( OObject o ){
	value = o;
	if( value != null && displayPanel != null )
	    displayPanel.setValue();
    }

    /**
     * The calculator can store one value and this is how we do it. Notice that
     * it allows memory addition and subtraction when used
     * in conjunction with getMemory().
     * @param o The value to put into memory
     * @see getMemory()
     */
    public void setMemory( OObject o ){
	memory = o;
    }

    /**
     * @return value from memory.
     */
    public OObject getMemory(){
	return memory;
    }
    
    /**
     * We can work with degrees or radians and this is how we set them. Does anyone
     * use grads? If so you can ay change AngleType etc, to handle. these.
     * @param angleType AngleType.DEGREES or AngleType.RADIANS
     * @see AngleType
     */
    public void setAngleType( AngleType angleType ){
	this.angleType = angleType;
    }
    
    /**
     * @return Angletype: degrees or radians.
     */
    public AngleType getAngleType(){
	return angleType;
    }
    
    /**
     * @return <em>true</em> or <em>false</em> according as we are in statistics
     * mode or not.
     */
    public boolean getStat(){
	return stat;
    }
    
    /**
     * Set <em>stat</em> mode on or off.
     * @param stat A value: <em>true</em> for <em>stat</em> mode.
     * @see StatPanel
     * @see #getStat()
     */
    public void setStat( boolean stat ){
	this.stat = stat;
	if( stat == true )
	    setBase( Base.DECIMAL );
	setShift( getShift() );
    }

    public int getMode(){
	return mode;
    }

    /**
     * Sets the mode. Allows us to change from degrees to radians, change calculator
     * size, set STAT mode on or off.
     * @param i The new mode: it&rsquo;s forced into the right range even if you give an
     * illegal value.
     */
    public void setMode( int i ){
	mode = i % ( (frame() != null && sizes.size() > 1) ? 4 : 3 );
    }

    /**
     * Sets the mode. Allows us to change from degrees to radians, change calculator
     * size, set STAT mode on or off.
     * CalculatorButton objects generate PObjects rather than integers; so we pass
     * these and act accordingly. This is not a particularly good piece of code, but
     * there&rsquo;s not much that can go seriously wrong with it; so I'm not planning
     * to fix it.
     * @param p A PObject, generated by a Calculator button during change of mode.
     */
    public void setMode( PObject p ){
	//System.out.print( "Mode: " );
	//System.out.println( getMode() );
	if( p instanceof Numeral ){
	    Numeral numeral = (Numeral)p;
	    //System.out.print( "numeral: " );
	    //System.out.println( numeral.name() );
	    if( mode == 3 ){
		int size = 0;
		switch( numeral.get() ){
		case '1': size = 1; break;
		case '2': size = 2; break;
		case '3': size = 3; break;
		case '4': size = 4; break;
		case '5': size = 5; break;
		case '6': size = 6; break;
		case '7': size = 7; break;
		case '8': size = 8; break;
		case '9': size = 9; break;
		default: size = 0;
		}
		if( size < sizes.size() ){
		    size = sizes.elementAt( size );
		    if( minSize() != size ) setMinSize( size );
		    setMode( 0 );
		    updateDisplay( true, true );
		}
	    } else if( numeral.name().equals( "1" ) ){
		//System.out.println( "***** 1 *****" );
		if( mode == 2 )
		    setAngleType( AngleType.DEGREES );
		else if( mode == 1 )
		    setStat( false );
		setMode( 0 );
		updateDisplay( true, true );
	    } else if( numeral.name().equals( "2" ) ){
		//System.out.println( "***** 2 *****" );
		if( mode == 2 )
		    setAngleType( AngleType.RADIANS );
		else if( mode == 1 )
		    setStat( true );
		setMode( 0 );
		updateDisplay( true, true );
	    } else if( numeral.name().equals( "3" ) ){
		setMode( 0 );
		updateDisplay( true, true );
	    }
	} else if( p instanceof Mode ){
	    setMode( getMode() + 1 );
	}
    }

    /**
     * Clear statistical memory.
     * @see SclButton
     */
    public void clearStatMemory(){
	statMemory.clear();
	statMemoryNeg.clear();
    }

    /**
     * Add a Complex to statistical memory. Statistical memory holds a list of these
     * numbers so that mean, standard deviation and the like can be calcualted at
     * will.
     * The function also returns the number of objects in statistical memory as
     * an Object. An object is used because it conveniently fits what we typically
     * want to do with the return result: display it in the calculator where the
     * expression was evaluated.
     * @param d The Complex to put into memory
     * @return The number of objects in statistical memory.
     */
    public Complex statAdd( Complex d ){
	statMemory.add( d );
	return new Complex( statSize() );
    }

    /**
     * Removes a Complex from statistical memory.
     * @param d The Complex to put into memory
     * @return The number of objects in statistcal memory.
     * @see #statAdd( Complex )
     */
    public Complex statSub( Complex d ){
	statMemoryNeg.add( d );
	return new Complex( statSize() );
    }

    /**
     * Used internally in calculations of mean and standard deviation so that
     * we can get these right even when we&rsquo;ve removed a number.
     * @return The number to use in statistical calculations
     */
    private double statSize(){
	return statMemory.size() - statMemoryNeg.size();
    }

    public Mean statMean(){
	Mean mean = new Mean();
	Complex d = new Complex();
	for( Complex o : statMemory ){
	    d = d.add( o );
	}
	for( Complex o : statMemoryNeg ){
	    d = d.subtract( o );
	}
	if( statSize() > 0 ){
	   mean.setValue( d.divide( new Complex( statSize(), 0 ) ) );
	} else {
	    mean.setError( true );
 	}
	return mean;
    }
    
    public Complex statSumSquares(){
	Mean m = statMean();
	if( m.error() || !(m.value() instanceof Complex) )
	    throw new RuntimeException( "Stat Error" );
	Complex e = (Complex)(m.value());
	Complex d = new Complex();
	for( Complex o : statMemory ){
	    Complex c = o.subtract( e );
	    d = d.add( c.square() );
	}
	for( Complex o : statMemoryNeg ){
	    Complex c = o.subtract( e );
	    d = d.subtract( c.square() );
	}
	return d;
    }

    public StDev statSampleStDev(){
	StDev stDev = new StDev();
	try {
	    Complex d = statSumSquares();
	    //if( d < 0 || statSize() < 2 ){
	    if( statSize() < 2 ){
		stDev.setError( true );
		return stDev;
	    } else {
		stDev.setValue( d.divide( new Complex( statSize() - 1, 0 ) ).sqrt() );
		return stDev;
	    }
	} catch( Exception e ){
	    stDev.setError( true );
	    return stDev;
	}
    }

    public PopStDev statPopulationStDev(){
	PopStDev stDev = new PopStDev();
	try {
	    Complex d = statSumSquares();
	    //if( d < 0 || statSize() < 1 ){
	    if( statSize() < 1 ){
		stDev.setError( true );
		return stDev;
	    } else {
		stDev.setValue( d.divide( new Complex( statSize(), 0 ) ).sqrt() );
		return stDev;
	    }
	} catch( Exception e ){
	    stDev.setError( true );
	    return stDev;
	}
    }

    /**
     * Used to set value of jframe. I can&rsquo;t remember why we need this, possibly
     * for CalcButtonApplet.
     * @param jframe A JFrame
     */
    public void frame( javax.swing.JFrame jframe ){
	this.jframe = jframe;
    }

    public javax.swing.JFrame frame(){
	return jframe;
    }
    
    /**
     * Some keys don&rsquo;t generate keyTyped Events. This function handles 
     * the arrow keys.
     * @param keyEvent The event of key pressed
     */
    public void keyPressed( java.awt.event.KeyEvent keyEvent ){
	if( keyEvent.getKeyCode() == java.awt.event.KeyEvent.VK_LEFT )
	    calculatorPanel.buttons().elementAt( 40 ).actionPerformed( null );
	else if( keyEvent.getKeyCode() == java.awt.event.KeyEvent.VK_RIGHT )
	    calculatorPanel.buttons().elementAt( 41 ).actionPerformed( null );
	else if( keyEvent.getKeyCode() == java.awt.event.KeyEvent.VK_UP )
	    calculatorPanel.buttons().elementAt( 43 ).actionPerformed( null );
	else if( keyEvent.getKeyCode() == java.awt.event.KeyEvent.VK_DOWN )
	    calculatorPanel.buttons().elementAt( 44 ).actionPerformed( null );
	else if( keyEvent.getKeyCode() == java.awt.event.KeyEvent.VK_ALT_GRAPH ){
	    if( !shiftDown ){
		calculatorPanel.buttons().elementAt( 10 ).actionPerformed( null );
		shiftDown = true;
	    }
	}
	else if( keyEvent.getKeyCode() == java.awt.event.KeyEvent.VK_COPY ){
	    if( !shiftDown ){
		copy();
	    } else {
		// also does copy but changes switch state too
		calculatorPanel.buttons().elementAt( 15 ).actionPerformed( null );
	    }
	}
    }
    
    /**
     * Some keys don&rsquo;t generate keyTyped Events. This function handles 
     * the release of the AltGraph key associated with the <em>shift</em> button. As
     * far as I can tell Java 2 doesn&rsquo;t actually generate an event with this
     * key; so this part of the function is probably redundant. I&rsquo;ve also
     * implemented
     * a crude hack here to recognise when CTRL-C is pressed and generate a
     * copy-to-clipboard event.
     * @param keyEvent The event of key pressed
     */
    public void keyReleased( java.awt.event.KeyEvent keyEvent ){
	if( keyEvent.getKeyCode() == java.awt.event.KeyEvent.VK_ALT_GRAPH ){
	    shiftDown = false;
	} else {
	    char c = keyEvent.getKeyChar();
	    //System.out.println( c );
	    //System.out.println( keyEvent.getKeyCode() );
	    //System.out.println( keyEvent.paramString() );
	    if( (keyEvent.getModifiersEx()
		 & java.awt.event.InputEvent.CTRL_DOWN_MASK) != 0
		&& ( keyEvent.getKeyCode() == 67 ) ){ // CTRL-C
		dataTransfer.copy();
	    }
	}
    }
    
    /**
     * Handle typed keys. This is how we associate keys to buttons. There are too
     * many buttons to ask each to handle its own (and too much risk of confusion);
     * so we handle them together from a list of buttons generated by
     * CalculatorPanel.
     *
     * Generally this function just finds which key you typed and uses a map
     * to activate the appropriate button.
     *
     * @param keyEvent The event of key typed
     * @see CalculatorPanel#keyMap()
     */
    public void keyTyped( java.awt.event.KeyEvent keyEvent ){
	if( (keyEvent.getModifiersEx()
	     & java.awt.event.InputEvent.CTRL_DOWN_MASK) == 0 ){ 
	    char c = keyEvent.getKeyChar();
	    if( c == java.awt.event.KeyEvent.VK_ENTER )
		c = '=';
	    CalculatorButton b = calculatorPanel.keyMap().get( c );
	    if( b != null )
		b.actionPerformed( null );
	    else if( c == java.awt.event.KeyEvent.VK_BACK_SPACE && !shift )
		calculatorPanel.buttons().elementAt( 30 ).actionPerformed( null );
	}
    }
    
    /**
     * javax.swing.Spring doesn&rsquo;t have a scale function. Although we only use this
     * in CalculatorPanel and its derived classes, we define a scale function here.
     * Why? Originally we used this class to generate Springs. It could probably be
     * moved to CalculatorPanel.
     * @param spring A spring
     * @param s how much we want to scale the spring: a positive integer -
     * negatives and zero are ignored
     * @see CalculatorPanel
     */
    private static javax.swing.Spring scale( javax.swing.Spring spring, int s ){
	if( s < 2 )
	    return spring;
	javax.swing.Spring result = javax.swing.Spring.sum( spring, spring );
	for( int i = 2; i < s; ++i ){
	    result = javax.swing.Spring.sum( result, spring );
	}
	return result;
    }

    public Base getBase(){
	return parser.base();
    }

    /**
     * This is where we set the base. Used by
     * EqualsButton and its derived classed.
     * @param b The base
     * @see EqualsButton
     * @see HexButton
     * @see BinButton
     * @see DecButton
     * @see OctButton
     */
    public void setBase( Base b ){
	if( getBase() == b ) return;
	//System.out.println( b );
	parser.base( b );
	//displayPanel.setBase( b );
	
	switch( b ){
	case DECIMAL:
	    setCalculatorPanel( SpecialButtonType.NONE );
	    break;
	default:
	    setCalculatorPanel( SpecialButtonType.HEX );
	    break;
	}
    }

    /**
     * This function is useful if you want to attach a JTextComponent such as 
     * JTextField to the calculator so that when you calculate a value, it gets
     * transferred to the JTextComponent.
     * @param textComponent The component you want to attach
     */
    public void setTextComponent( javax.swing.text.JTextComponent textComponent ){
	this.textComponent = textComponent;
    }

    /**
     * Get the JTextComponent associated with this oject. This feature is only useful
     * if you want to attach the calculator to a component, for example to
     * calculate some number for you.
     * @return The component attached
     */
    public final javax.swing.text.JTextComponent getTextComponent(){
	return textComponent;
    }
    /**
     * Get notation (scientific, polar, complex)
     * @return the notation.
     */
    public Notation getNotation(){
	return notation;
    }
    /**
     * Set notation (scientific, polar, complex)
     * @param notation The notation to set
     */
    public void setNotation( Notation notation ){
	this.notation = notation;
    }

    /**
     * Get the frame Insets
     * @return frame Insets
     */
    public final java.awt.Insets getFrameInsets(){
	if( jframe == null ) return new java.awt.Insets( 29, 4, 4, 4 ); // just guess
	return jframe.getInsets();
    }

    /**
     * Estimate width of panel at given size.
     * @param mSize The size at which to estimate wdith
     * @return An estimate (usually correct) of current frame width at given size
     */
    private int getFrameWidth( int mSize ){
	int x = getFrameInsets().left + getFrameInsets().right;
	return 4 * strutSize( mSize ) + 5 * mSize + 8 * buttonWidth( mSize ) + x;
    }

    /**
     * Estimate height of panel at given size.
     * @param mSize The size at which to estimate wdith
     * @return An estimate (usually correct) of current frame height at given size
     */
    private int getFrameHeight( int mSize ){
	int y = getFrameInsets().left + getFrameInsets().right;
	return 3 * strutSize( mSize ) + 4 * mSize + 5 * buttonHeight( mSize )
	    + displayHeight( mSize ) + y;
    }

    /**
     * Set the allowable range of sizes for the calulator. These are the values
     * that mSize may take.
     */
    private void setSizes(){
	mSize = 4; // A default value;
	
	// Do nothing if no displayPanel
	if( jframe == null ) return;
	
	// get the screen width and height
	int width = java.awt.Toolkit.getDefaultToolkit().getScreenSize().width;
	int height = java.awt.Toolkit.getDefaultToolkit().getScreenSize().height;

	int max = minimumSize;
	for( int i = 0; i < 11; ++i ){ // 11 stops infinite loop, redundant, detectable
	    if( getFrameWidth( minimumSize + i ) > width 
		|| getFrameHeight( minimumSize + i ) > height ){
		max = i;
		break;
	    } else if( i == 10 )
		max = i;
	}

	sizes = new java.util.Vector<Integer>( max );
	for( int i = minimumSize; i < minimumSize + max; ++i )
	    sizes.add( i );
	
	if( max < 4 )
	    mSize = minimumSize;
    }

    public int getSizesSize(){
	return sizes.size();
    }

    public int getMinSize(){
	return minimumSize;
    }

    /** 
     * Set Caret to entry
     */
    private void setCaretToEntry(){
	displayPanel.setCaretToEntry();
    }

    /** 
     * Set Caret to display
     */
    private void setCaretToDisplay(){
	displayPanel.setCaretToDisplay();
    }

    /**
     * The value of the last expression evaluated. Usually a Double unless an error
     * occurred.
     */
    private OObject value;
    /**
     * DEGREES or RADIANS
     */
    private AngleType angleType;
    /**
     * The MODE
     */
    private int mode;
    /**
     * Statistics mode or not
     */
    private boolean stat;

    /**
     * The calculator&rsquo;s memory.
     */
    private OObject memory;
    /**
     * Statistics memory needs to hold multiple values.
     */
    private java.util.Vector<Complex> statMemory;
    /**
     * Statistics memory needs to know if you&rsquo;ve removed values.
     */
    private java.util.Vector<Complex> statMemoryNeg;

    /**
     * The frame height - I think this is redundant because the frame calculates
     * its own height using pack().
     */
    private int frameHeight;

    /**
     * If we create a frame, we create an applet, otherwise, this.
     */
    private CalculatorApplet applet;
    /**
     * The <em>view</em> object
     */
    private DisplayPanel displayPanel;
    /**
     * The object that contains all the buttons: the input object.
     */
    private CalculatorPanel calculatorPanel;
    /**
     * More than one input panel so keep track of all of them with an map and
     * enumerated type.
     */
    private java.util.HashMap<SpecialButtonType,CalculatorPanel> calculatorPanels;

    /**
     * The parameter that determines how much everything is scaled.
     */
    private int mSize;

    /**
     * The <em>model</em> object. It parses expressions and is actually the real
     * power behind the calculator. Nearly everything else is just interface.
     */
    private Parser parser;
    /**
     * Is shift button pressed?
     */
    private boolean shift;

    /**
     * Button width.
     */
    private static final int bWidth = 23;
    /**
     * Button width.
     */
    private static final int bHeight = 10;
    /**
     * Strut size.
     */
    private static final int sSize = 3;
    /**
     * Height of DisplayPanel.
     */
    private static final int dHeight = 30;
    /**
     * Button text size.
     */
    private static final float bTextSize = 4;
    /**
     * EntryPanel text size.
     */
    //private static final float eTextSize = (float)6.8;
    private static final float eTextSize = (float)6.4;
    /**
     * DisplayLabel text size.
     */
    //private static final float dTextSize = (float)9.5;
    private static final float dTextSize = (float)9.2;
    /**
     * Small text size.
     */
    private static final float sTextSize = (float)2.5;

    /**
     * The history.
     */
    private java.util.Vector<HistoryItem> history;
    /**
     * How many events should we store? Change this to suit yourself.
     */
    private static final int HISTORY_SIZE = 24;
    /**
     * If we roll up the history with a half completed expression, we can change
     * our minds and go back to it.
     */
    HistoryItem tempParserList;
    /**
     * where are we in the history list.
     */
    int historyPosition;

    /**
     * This is where you change the DisplayPanel colour. I haven&rsquo;t checked that
     * it actually works, but in principle you could have (say) a red or blue
     * calculator if you want.
     */
    private static final java.awt.Color panelColour = java.awt.Color.LIGHT_GRAY;

    /**
     * Used by CalcButtonApplet and DisplayPanel
     */
    protected javax.swing.JFrame jframe;
    /**
     * Used to tell if a modifier key is being held down for shift&mdash;I
     * don&rsquo;t think
     * it actually does anything at the moment.
     */
    private boolean shiftDown;
    /**
     * Notation
     */
    private Notation notation;
    /**
     * Can be used to attach a text component to the object.
     */
    private javax.swing.text.JTextComponent textComponent;
    /**
     * Used to Paste from DisplayPanel to system clipboard.
     */
    private DataTransfer dataTransfer;
    /**
     * The minimum value of msize
     */
    private final int minimumSize = 3;
    /**
     * The range of sizes allowed
     */
    private java.util.Vector<Integer> sizes;
    
    private static final long serialVersionUID = 1L;   
}
