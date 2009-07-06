//Copyright (C) 2008 Ted Kosan (license information is at the end of this document.)

package org.mathrider.jfreechartplugin;

import javax.swing.JTextArea;

public class JFreeChartTextArea extends JTextArea {
	public JFreeChartTextArea() {
		super();
		setLineWrap(true);
		setWrapStyleWord(true);
		setTabSize(4);
	}
}//end class.

/* {{{ License.
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA  02111-1307, USA.
 */ //}}}
