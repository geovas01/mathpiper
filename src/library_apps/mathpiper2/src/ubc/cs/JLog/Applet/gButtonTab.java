/*
    This file is part of JLog.

    Created by Glendon Holst for Alan Mackworth and the 
    "Computational Intelligence: A Logical Approach" text.
    
    Copyright 1998, 2000, 2002, 2008 by University of British Columbia, 
    Alan Mackworth and Glendon Holst.
    
    This notice must remain in all files which belong to, or are derived 
    from JLog.
    
    Check <http://jlogic.sourceforge.net/> or 
    <http://sourceforge.net/projects/jlogic> for further information
    about JLog, or to contact the authors.

    JLog is free software, dual-licensed under both the GPL and MPL 
    as follows:

    You can redistribute it and/or modify
    it under the terms of the GNU General Public License as published by
    the Free Software Foundation; either version 2 of the License, or
    (at your option) any later version.

    Or, you can redistribute it and/or modify
    it under the terms of the Mozilla Public License as published by
    the Mozilla Foundation; version 1.1 of the License.

    JLog is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU General Public License, or the Mozilla Public License for 
    more details.

    You should have received a copy of the GNU General Public License
    along with JLog, in the file GPL.txt; if not, write to the 
    Free Software Foundation, Inc., 
    59 Temple Place, Suite 330, Boston, MA  02111-1307  USA
    URLs: <http://www.fsf.org> or <http://www.gnu.org>

    You should have received a copy of the Mozilla Public License
    along with JLog, in the file MPL.txt; if not, contact:
    http://http://www.mozilla.org/MPL/MPL-1.1.html
    URLs: <http://www.mozilla.org/MPL/>
*/
//##################################################################################
//	gButtonTab
//##################################################################################

package ubc.cs.JLog.Applet;

import java.lang.*;
import java.util.*;
import java.awt.*;
import java.awt.event.*;

/**
* This represents a tab in a multi-tabbed user interface.  Each tab is associated
* with an panel.  When the tab is pressed it informs its owning 
* <code>gButtonTabMenu</code> that it was pressed and then notifies the enclosing 
* <code>iCardPanel</code> to display the associated panel.
*
* @author       Glendon Holst
* @version      %I%, %G%
*/
public class gButtonTab extends Panel
{
 protected final static String 		ACTIVE = "active";
 protected final static String 		INACTIVE = "inactive";
 
 protected String			cardname;
 protected iCardPanel 		card;
 protected gButtonTabMenu 	menu;
 protected Button 			button;
 protected Label 			label;
 
 public 	gButtonTab(String title,gButtonTabMenu m,iCardPanel cl,String cname)
 {
  card = cl;
  cardname = cname;
  
  menu = m;

  setLayout(new CardLayout());
  setFont(m.getFont());
  setBackground(Color.lightGray);
  setForeground(Color.black);

  add(button = new Button(title),INACTIVE);
  button.addActionListener(new ActionListener() 
                       {
                        public void 	actionPerformed(ActionEvent e)
                        {
                         menu.setActiveTab(gButtonTab.this);
                        }
                       }
                      );
  button.setBackground(Color.white);
  button.setForeground(Color.black);
 
  add(label = new Label(title,Label.CENTER),ACTIVE);
 };
 
 public void 	setState(boolean active)
 {
  if (active)
   card.setCard(cardname);
 
  ((CardLayout) getLayout()).show(this,(active ? ACTIVE : INACTIVE));
 };
 
 public void 	setEnabled(boolean b)
 {
  super.setEnabled(b);
  button.setEnabled(b);
  label.setEnabled(b);
 };
};