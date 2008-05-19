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
import jscicalc.complex.Complex;

/**
 * This is the little panel that tells you about the state of the calculator.
 * Currently it tells you the base (Base.OCTAL, Base.DECIMAL, Base.HEXADECIMAL or
 * Base.BINARY), whether we&rsquo;re using scientific notation (sci),
 * whether we&rsquo;re in
 * statistics mode (Stat) whether angles are measured in Degrees or Radians
 * (AngleType) and whether (mem) we&rsquo;ve a number stored in memory.
 *
 * @author J.&nbsp;D.&nbsp;Lamb 
 * @version $Revision: 1.6 $
 */
public class ExtraPanel extends javax.swing.JPanel {
    
    /**
     * The constructor
     * @param panel An interface to the DisplayPanel object
     */
    public ExtraPanel( ReadOnlyDisplayPanel panel ){
	this.panel = panel;
    }
    
    /**
     * This does all the work of updating the component, reading all values
     * directly from DisplayPanel or indirectly from CalculatorApplet. Probably they
     * should all be read indirectly.
     * @param graphics The graphics context required.
     */
    public void paintComponent( java.awt.Graphics graphics ){
	java.awt.Graphics2D g2 = (java.awt.Graphics2D)graphics;
	g2.setRenderingHint( java.awt.RenderingHints.KEY_ANTIALIASING,
			     java.awt.RenderingHints.VALUE_ANTIALIAS_ON );
	graphics.clearRect( 0, 0, getWidth(), getHeight() );
	graphics.setFont( getFont().deriveFont( getPanel().getApplet()
						.extraTextSize() ) );
	int fontHeight
	    = (int)(Math.ceil( getPanel().getApplet().extraTextSize() ));
	int x = 0; //(int)(fontHeight * 0.5);
	int xx = getWidth() - graphics.getFontMetrics().stringWidth( "Hex " ); 
	int y = getHeight() - (int)(fontHeight * 0.5);
	if( getPanel().getOn() ){
	    if( getPanel().getApplet().getNotation().nonComplex() )
		graphics.drawString( getPanel().getApplet().getAngleType().toString(),
				     x, y );
	    else {
		if( getPanel().getApplet().getAngleType() == AngleType.DEGREES )
		    graphics.drawString( "Deg", x, y );
		else
		    graphics.drawString( "Rad", x, y );
		graphics.drawString( "Cplx", xx, y );
	    }
	    y = getHeight() - (int)(fontHeight * 2.0);
	    if( getPanel().getApplet().getNotation().scientific() )
		graphics.drawString( "Sci", xx, y );
	    if( getPanel().getApplet().getStat() )
		graphics.drawString( "Stat", x, y );
	    y = getHeight() - (int)(fontHeight * 3.5);
	    if( !getPanel().getApplet().getMemory().isZero() )
		graphics.drawString( "Mem", x, y );
	    switch( getPanel().getApplet().getBase() ){
	    case HEXADECIMAL:
		graphics.drawString( "Hex", xx, y );
		break;
	    case OCTAL:
		graphics.drawString( "Oct", xx, y );
		break;
	    case BINARY:
		graphics.drawString( "Bin", xx, y );
		break;
	    }
	    y = getHeight() - (int)(fontHeight * 5.0);
	    if( getPanel().getApplet().getShift() )
		graphics.drawString( "Shift", x, y );
	    if( getPanel().getApplet().getNotation().polar() )
		graphics.drawString( "Pol", xx, y );
	}
    }
    
    /**
     * @return The DisplayPanel interface.
     */
    public ReadOnlyDisplayPanel getPanel(){
	return panel;
    }

    /**
     * The DisplayPanel interface.
     */
    private ReadOnlyDisplayPanel panel;
    private static final long serialVersionUID = 1L;   
}
	
