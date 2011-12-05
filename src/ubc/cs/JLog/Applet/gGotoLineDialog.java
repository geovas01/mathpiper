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
//	gGotoLineDialog
//##################################################################################

package ubc.cs.JLog.Applet;

import java.lang.*;
import java.util.*;
import java.awt.*;
import java.awt.event.*;
import java.applet.Applet;

public class gGotoLineDialog extends Dialog
{
 protected TextField		line_field;
 protected int				initial_line;
 protected int				goto_line = -1;
 
 public gGotoLineDialog(gWindowBase w,int line)
 {
  super(w,"Goto Line",true);
  
  initial_line = line;
  
  setLayout(new BorderLayout());
  
  {Panel	fields = new Panel();
   Font		font = new Font("Dialog",Font.BOLD,12);
   Label 	info;
   
   fields.setLayout(new BorderLayout());
   
   info = new Label("Line: ",Label.LEFT);
   info.setFont(font);

   fields.add(info,BorderLayout.WEST);

   line_field = new TextField(Integer.toString(initial_line));
   line_field.setFont(font);

   line_field.addKeyListener(new KeyAdapter()
                       {
                        public void 	keyPressed(KeyEvent e)
                        {
                         if (e.getKeyCode() == KeyEvent.VK_ENTER)
                         { 
						  gGotoLineDialog.this.doGoto();
						 } 
                        }
                       });

   fields.add(line_field,BorderLayout.CENTER);
   fields.add(new Panel(),BorderLayout.SOUTH);
   add(fields,BorderLayout.CENTER);
  }
   
  add(new Panel(),BorderLayout.WEST);
  add(new Panel(),BorderLayout.EAST);
  add(new Panel(),BorderLayout.NORTH);
  
  {Panel 	buttons = new Panel();
   Panel	go = new Panel();
   Button 	b;

   buttons.setLayout(new BorderLayout());
   go.setLayout(new BorderLayout());
   
   b = new Button("Goto");
   b.addActionListener(new ActionListener()
                {
                 public void 	actionPerformed(ActionEvent e)
                 {
				  gGotoLineDialog.this.doGoto();
                 }
                }); 
   b.setBackground(Color.white);
   b.setForeground(Color.black);
   
   go.add(b,BorderLayout.EAST);
   go.add(new Panel(),BorderLayout.CENTER);
   go.add(new Panel(),BorderLayout.WEST);
   buttons.add(go,BorderLayout.CENTER);
   buttons.add(new Panel(),BorderLayout.EAST);
   buttons.add(new Panel(),BorderLayout.WEST);
   buttons.add(new Panel(),BorderLayout.SOUTH);
   add(buttons,BorderLayout.SOUTH);
  }
    
  pack();

  addWindowListener(new WindowAdapter() 
                {
				 public void windowClosing(WindowEvent evt) 
                 {
				  close();
		         }
		        });

  setSize(400, 100);
  {Point	ploc = w.getLocation();
  
   setLocation(ploc.x+16,ploc.y+32);
  }
  setVisible(true);
 };
 
 public boolean 		wasAccepted()
 {
  return (goto_line >= 0);
 };
 
 public int 		getGotoLine()
 {
  return goto_line;
 };
 
 protected void		doGoto()
 {
  try
  {
   goto_line = Integer.parseInt(line_field.getText());
   close();
  }
  catch (NumberFormatException ex)
  {
   goto_line = -1;
   line_field.setText(Integer.toString(initial_line));
   line_field.selectAll();
   Toolkit.getDefaultToolkit().beep();
  } 
 };

 protected void 	close()
 {
  dispose();
 };
};
