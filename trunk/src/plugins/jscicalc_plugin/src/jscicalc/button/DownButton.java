/** @file
 * Copyright (C) 2005 John D Lamb (J.D.Lamb@btinternet.com)
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
import jscicalc.CalculatorApplet;
import jscicalc.pobject.Down;

/**
 * Button to move down history list.
 *
 * @author J.&nbsp;D.&nbsp;Lamb
 * @version $Revision: 1.8 $
 */
public class DownButton extends CalculatorButton {

    /**
     * Constructor. Sets PObject to DOwn.
     * @param applet The <em>controller</em> object.
     */
    public DownButton( CalculatorApplet applet ){
	this.applet = applet;
	pobject = new Down();
 	//setText();
 	//setTextSize();
 	tooltip = pobject.tooltip();
 	setToolTipText();
	int size = applet.minSize();
	if( size < 3 ) size = 3;
	if( size > 9 ) size = 9;
	StringBuilder s = new StringBuilder( "jscicalc/icons/" ); //Note:tk:removed leading /.
	s.append( Integer.toString( size ) );
	s.append( "downarrow.png" );
	java.io.InputStream inputStream = org.gjt.sp.jedit.jEdit.getPlugin("org.mathrider.jscicalcplugin.JSciCalcPlugin").getPluginJAR().getClassLoader().getResourceAsStream( s.toString() );
	//Note:tk:getClass().getResourceAsStream( s.toString() );
	java.io.BufferedInputStream bufferedInputStream
	    = new java.io.BufferedInputStream( inputStream );
	byte[] buffer = new byte[4096];
	try {
	    bufferedInputStream.read( buffer, 0, 4096 );
	} catch( Exception e ){
	    System.err.println( "Failed to read image." );
	}
	java.awt.Image image
	    = java.awt.Toolkit.getDefaultToolkit().createImage( buffer );
	setIcon( new javax.swing.ImageIcon( image ) );
	addActionListener( this );
    }

    /**
     * Moves down the history list unless we are at the last expression in the list,
     * in which case does nothing.
     * @param actionEvent The event that generated this method call: usually a button
     * press or called when CalculatorApplet responded to the key associated with
     * this button
     */
    public void actionPerformed( java.awt.event.ActionEvent actionEvent ){
	if( getApplet().getMode() != 0 )
	    return;
	if( getApplet().downHistory() )
	    getApplet().updateDisplay( true, true );
	getApplet().requestFocusInWindow();
    }
    
    private static final long serialVersionUID = 1L;   
}
