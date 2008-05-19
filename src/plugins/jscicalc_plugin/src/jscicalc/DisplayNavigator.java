/** @file
 * Copyright (C) 2008 John D Lamb (J.D.Lamb@btinternet.com)
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
 * Used by EntryPanel to allow the caret to move and expressions to be edited.
 *
 * @author J.&nbsp;D.&nbsp;Lamb 
 * @version $Revision: 1.1 $
 * @see Navigator
 */
class DisplayNavigator extends Navigator {
    
    /**
     * Create a new navigator and set dots to contain only the position one.
     */
    public DisplayNavigator(){
	super();
    }
}
