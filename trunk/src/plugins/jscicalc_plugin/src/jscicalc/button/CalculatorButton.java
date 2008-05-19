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

package jscicalc.button;
import jscicalc.AngleType;
import jscicalc.OObject;
import jscicalc.CalculatorApplet;
import jscicalc.pobject.*;
import jscicalc.complex.Complex;

/**
 * Each button on the calculator is an instance of this class. Each button contains
 * two important pieces of information. It knows about a PObject object. This object
 * usually contains the information necessary for some calculator function such
 * as plus, log or even a Numeral that needs to be stored in an expression. There
 * are a few exceptions, such as the Mode and Shift buttons, where information
 * needs to be sent to the CalculatorApplet object, but does not form part of an
 * expression that the Parser object will evaluate. Generally, buttons that do
 * more than create part of an expression use a subclass of this. The second piece
 * of information that an object of this class needs is the CalculatorApplet
 * object. Buttons don&rsquo;t deal directly with the Parser object but rather request
 * that the CalculatorApplet object do this for them.
 *
 * Buttons are made available to the user through CalculatorPanel and its subclasses.
 *
 * @see CalculatorApplet
 * @see PObject
 * @see Parser
 * @see CalculatorPanel
 *
 * @author J.&nbsp;D.&nbsp;Lamb
 * @version $Revision: 1.7 $
 */
public class CalculatorButton extends javax.swing.JButton 
    implements java.awt.event.ActionListener {

    /**
     * A constructor with no arguments lets us derive subclasses without having
     * to undo everything done here.
     */
    protected CalculatorButton(){
    }

    /**
     * A setup function. It might be better to make it protected and call
     * it from the subclass so that we can add ActionListener objects without
     * reimplementing this code.
     * @param applet The <em>controller</em> object
     * @param pobject The PObject object that tells the button how to look and
     * behave
     */
    private void setup( CalculatorApplet applet, PObject pobject ){
	this.applet = applet;
	setPObject( pobject );
	setText();
	setTextSize();
	addActionListener( this );
    }

    /**
     * If we didn&rsquo;t set a PObject initially, we do so now.
     * @param p The PObject object that tells the button how to look and
     * behave
     */
    public void setPObject( PObject p ){
	if( p == null )
	    p = NullPObject.instance();
	pobject = p;
	setText( p );
	tooltip = p.tooltip();
	shortcut = p.shortcut();
	setToolTipText();
    }

    /**
     * Set the text for the tooltip. This is extracted from the PObject byt taking
     * the PObject.tooltip() function, adding the PObject.shortcut key in square
     * brackets and wrapping everything in HTML.
     * @see PObject
     */
    public void setToolTipText(){
	if( tooltip.length() > 0 )
	    if( shortcut == ' ' ) 
		setToolTipText( "<html>" + tooltip + " [space]</html>" );
	    else if( shortcut != 0 ) 
		setToolTipText( "<html>" + tooltip + " [" + shortcut + "]</html>" );
	    else
		setToolTipText( "<html>" + tooltip + "</html>" );
	else
	    if( shortcut == ' ' )
		setToolTipText( "<html>[space]</html>" );
	    else if( shortcut != 0 ) 
		setToolTipText( "<html>[" + shortcut + "]</html>" );
	    else
		return;
    }

    /**
     * Another constructor. This one is useful for subclasses and so could probably
     * be protected. It&rsquo;s also used to create empty buttons, though
     * CalculatorPanel could be rewritten just to pass null as the PObject and call
     * the standard constructor.
     * @param applet The <em>controller</em> object
     */
    public CalculatorButton( CalculatorApplet applet ){
	setup( applet, NullPObject.instance() );
    }

    /**
     * This is the constructor that we normally use. The remaining constructors
     * should probably all be protected since they are designed to be used by
     * subclasses.
     * @param applet The <em>controller</em> object
     * @param pobject The PObject object that tells the button how to look and
     * behave
     */
    public CalculatorButton( CalculatorApplet applet, PObject pobject ){
	setup( applet, pobject );
    }

    /**
     * Set the text. Again we use the PObject.
     */
    public void setText(){
	setText( pobject );
    }

    /**
     * Set the text from a PObject. This allows us to override the PObject in this
     * class, though I don&rsquo;t think we ever do. It uses PObject.shortName() to
     * create some text that appears on the button and then wraps in in HTML tags.
     * This is useful so that, for example, the squared button has the 2 in the
     * position.
     * @param p A PObject: usually this.pobject.
     */
    protected void setText( PObject p ){
	StringBuilder s = new StringBuilder();
	s.append( "<html>" );
	if( p != null )
	    s.append( p.shortName() );
	s.append( "</html>" );
	setText( s.toString() );
    }

    /**
     * Convenient for getting the <em>controller</em> object.
     */
    protected CalculatorApplet getApplet(){
	return applet;
    }

    /**
     * When a button is constructed it needs a fontsize consisten with all other
     * buttons. This method gets the correct font from the <em>controller</em>
     * CalculatorApplet object.
     */
    public void setTextSize(){
	setFont( getFont().deriveFont( getApplet().buttonTextSize() ) );
    }

    /**
     * The PObject objects of class Trig need to know the AngleType currently in use
     * by the CalculatorApplet. This method allows us to set the value. Probably it
     * should not have a parameter but ask the CalculatorApplet for a value.
     * @param angleType AngleType.DEGREES or AngleType.RADIANS
     */
    public void setAngleType( AngleType angleType ){
	if( pobject instanceof Trig )
	    ((Trig)pobject).setScale( angleType );
    }

    /**
     * This is the default actionPerformed. It checks that the calculator is switched
     * on and then carries out the action associated with this button depending
     * on the state of the CalculatorApplet object. Typically it just asks the
     * CalculatorApplet object to add its PObject to the Parser object at the 
     * current insert position and then update the display.
     * @param actionEvent The event that generated this method call: usually a button
     * press or called when CalculatorApplet responded to the key associated with
     * this button
     */
    public void actionPerformed( java.awt.event.ActionEvent actionEvent ){
	synchronized( applet ){
	    if( getApplet().getMode() != 0 ){
		getApplet().setMode( pobject );
		getApplet().requestFocusInWindow();
		return;
	    }
	    if( pobject instanceof Numeral ){
		char c = pobject.shortcut();
		switch( getApplet().getBase() ){
		case BINARY:
		    if( c == '2' || c == '3' || c == '4' || c == '5'
			|| c == '6' || c == '7' ) return;
		    break;
		case OCTAL:
		    if( c == '8' || c == '9' ) return;
		    break;
		case DECIMAL:
		    if( c == 'A' || c == 'B' || c == 'C' || c == 'D'
			|| c == 'E' || c == 'F' )
			return;
		    break;
		case HEXADECIMAL:
		default:
		    break;
		}
	    }
	    if( pobject instanceof NullPObject || pobject == null )
		return;
	    else if( pobject instanceof Mean )
		add( getApplet().statMean() );
	    else if( pobject instanceof StDev )
		add( getApplet().statSampleStDev() );
	    else if( pobject instanceof PopStDev )
		add( getApplet().statPopulationStDev() );
	    else {
		setAngleType( applet.getAngleType() );
		add( pobject );
	    }
	    getApplet().updateDisplay( true, true );
	    if( getApplet().getShift() )
		getApplet().setShift( false );
	    getApplet().requestFocusInWindow();
	}
    }

    /**
     * Used internally when asking the applet to insert a PObject. In some cases
     * we want to add an extra Ans object and this method handles this case.
     * @param p The PObject: since it should always be this.pobject the parameter
     * should probably be removed
     */
    protected void add( PObject p ){
	// FIXME: this is probably not quite right
	if( !(getApplet().getValue() instanceof OObject) )
	    return;
	OObject value = getApplet().getValue();
	if( getApplet().getParser().isEmpty()
	    && !(p instanceof Numeral) 
	    && !(p instanceof Variable) 
	    && !(p instanceof AFunction) 
	    && !(p instanceof RFunction) 
	    && !(p instanceof LParen) 
	    && !(p instanceof RParen) 
	    && !(p instanceof I) 
	    && !(p instanceof Container) ){
	    Ans ans = new Ans();
	    ans.setValue( value );
	    getApplet().insert( ans );
	    getApplet().updateDisplay( true, true );
	    getApplet().insert( p );
	} else {
	    getApplet().insert( p );
	}
    }
    /**
     * A shortcut key character.
     * @return a character representing a shortcut key
     */ 
    public char shortcut(){
	return shortcut;
    }

    /**
     * Get a copy of the tooltip. I can&rsquo;t remember if we use this.
     * @return A copy of the tooltip
     */
    public String tooltip(){
	return new String( tooltip );
    }

    /**
     * The <em>controller</em> object
     */
    protected CalculatorApplet applet;
    /**
     * The PObject that defines the behaviour of this object
     */
    protected PObject pobject;
    /**
     * The shortcut key (0 means no shortcut key)
     */
    protected char shortcut;
    /**
     * The tooltip string
     */
    protected String tooltip;
    private static final long serialVersionUID = 1L;   
}
