/** @file
 * Copyright (C) 2006 John D Lamb (J.D.Lamb@btinternet.com)
 * Copyright (C) 2007  John D Lamb (J.D.Lamb@btinternet.com)
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
import jscicalc.complex.Complex;

/**
 * This class handles pasting to the system clipboard.
 *
 * @author J.&nbsp;D.&nbsp;Lamb 
 * @version $Revision: 1.8 $
 */
public class DataTransfer extends javax.swing.AbstractAction
    implements java.awt.datatransfer.ClipboardOwner {
    /**
     * The constructor needs a display label so that it knows what to copy.
     * @param calculatorApplet The CalculatorApplet
     */
    public DataTransfer( CalculatorApplet calculatorApplet ){
	this.calculatorApplet = calculatorApplet;
    }
    
    /**
     * Do nothing. Jscicalc doesn&rsquo;t worry about losing clipboard
     */
    public void lostOwnership( java.awt.datatransfer.Clipboard c,
			       java.awt.datatransfer.Transferable t ){}   

    /**
     * Paste output.
     */
    public void copy(){
	Object value = calculatorApplet.getValue();
	if( !(value instanceof Complex ) )
	    return;
	Complex d = (Complex)value;
	/* get values from CalculatorApplet */
	Base base = calculatorApplet.getBase();
	Notation notation = new Notation();
	double factor = 1;
	if( calculatorApplet.getAngleType() == AngleType.DEGREES )
	    factor = 1;//180 / StrictMath.PI;
	String string = d.toHTMLString( maxLength, sigDigits, base, notation, factor );
	/* String now needs formatting */
	java.util.regex.Matcher matcher = html.matcher( string );
	string = matcher.replaceAll( "" );
	matcher = htmlend.matcher( string );
	string = matcher.replaceAll( "" );
	matcher = minus.matcher( string );
	string = matcher.replaceAll( "-" );
	System.out.println( string );
	java.awt.datatransfer.StringSelection stringSelection
	    = new java.awt.datatransfer.StringSelection( string );
	java.awt.datatransfer.Clipboard clipboard
	    = java.awt.Toolkit.getDefaultToolkit().getSystemClipboard();
	clipboard.setContents( stringSelection, this );
    }

    /**
     * Make this class an ActionListener. This allows copy and (poteniatly) paste
     * actions.
     * @param actionEvent The ActionEvent.
     */
    public void actionPerformed( java.awt.event.ActionEvent actionEvent ){
	System.out.println( actionEvent.getActionCommand() );
    }

    /**
     * The CalculatorApplet
     */
    private CalculatorApplet calculatorApplet;
    
    /**
     * A constant used to determine length.
     */
    static final int maxLength = 120;
    /**
     * Constant for significant digits
     */
    static final int sigDigits = 32;
    /**
     * Matches &lt;html&gt;
     */
    private static final java.util.regex.Pattern html
    = java.util.regex.Pattern.compile( "<html>" );
    /**
     * Matches &lt;/html&gt;
     */
    private static final java.util.regex.Pattern htmlend
    = java.util.regex.Pattern.compile( "</html>" );
    /**
     * Matches minus 
     */
    private static final java.util.regex.Pattern minus
    = java.util.regex.Pattern.compile( "&#8722;" );
    private static final long serialVersionUID = 1L;   
}
