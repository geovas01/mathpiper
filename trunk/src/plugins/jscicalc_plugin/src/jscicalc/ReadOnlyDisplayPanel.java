/** @file
 * Copyright (C) 2003&ndash;5, 2008 John D Lamb (J.D.Lamb@btinternet.com)
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
 * An interface for DisplayPanel so that we can read the state in classes that
 * don&rsquo;t need to change the state.
 *
 * @author J.&nbsp;D.&nbsp;Lamb
 * @version $Revision: 1.5 $
 */
public interface ReadOnlyDisplayPanel {
    
    /**
     * This function tells us if the display is switched on or not. Probably it
     * is redundant because the state should always match CalculatorPanel.
     * @return <em>true</em> whenever the display is switched on
     */
    public boolean getOn();
    
    /**
     * Get a read-only interface for the CalculatorApplet. Useful because
     * <em>view</em> classes should never change the CalculatorApplet state
     * @return A read-only interface to the <em>controller</em>
     */
    public ReadOnlyCalculatorApplet getApplet();
    
    /** 
     * Find if scrollableLabel has the Caret. This allows us to query the interface and
     * find whether EntryPanel or DisplayPanel has permission to display a Caret
     * @param scrollableLabel The Scrollable label we wish to know about
     * @return <em>true</em> or <em>false</em> according as scrollableLabel has or has
     * not permission to show a Caret
     */
    boolean hasCaret( final ScrollableLabel scrollableLabel );
}
	
