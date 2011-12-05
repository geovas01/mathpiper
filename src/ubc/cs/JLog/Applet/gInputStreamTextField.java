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
//#########################################################################
//	InputStreamTextField
//#########################################################################

package ubc.cs.JLog.Applet;

import java.lang.*;
import java.util.*;
import java.io.*;
import java.awt.TextField;
import java.awt.Button;
import java.awt.event.*;

/**
* This class represents an InputStream which gets input from a TextField.
*  
* @author       Glendon Holst
* @version      %I%, %G%
*/
public class gInputStreamTextField extends Reader
{
 protected TextField					input;
 protected Button						enter;
 protected StringReader					reader;

 public gInputStreamTextField(TextField i,Button e)
 {
  reader = null;
 
  input = i;
  enter = e;
  
  enter.addActionListener(new ActionListener() 
                       {
                        public void 	actionPerformed(ActionEvent e)
                        {
                         gInputStreamTextField.this.enter();
                        }
                       });
  input.addKeyListener(new KeyAdapter()
                       {
                        public void 	keyPressed(KeyEvent e)
                        {
                         if (e.getKeyCode() == KeyEvent.VK_ENTER && input.isEditable())
                          gInputStreamTextField.this.enter();
                        }
                       });
 };

 public void	close() throws IOException
 {
  if (input != null)
   input.setEditable(false);
  if (enter != null)
   enter.setEnabled(false);
  if (reader != null)
   reader.close();
   
  input = null;
  enter = null;
  reader = null;
 };

 public synchronized void	mark(int readlimit) throws IOException
 {
 };

 public boolean			markSupported()
 {
  return false;
 };

 public synchronized void	reset() throws IOException 
 {
 };
 
 public int		read() throws IOException
 {
  if (reader == null)
   readyInputAndEnter();

  while (reader == null)
  {
   Thread.currentThread().yield();
  }
  
  {int		result = reader.read();
  
   if (result < 0)
   {
    reader = null;
	return read();
   }

   if (!reader.ready())
    reader = null;

   return result;
  }
 };
 
 public int		read(char[] cbuff,int off,int len) throws IOException
 {
  if (reader == null)
   readyInputAndEnter();
   
  while (reader == null)
  {
   Thread.currentThread().yield();
  }
   
  {int		result = reader.read(cbuff,off,len);

   if (result < 0)
   {
    reader = null;
	return read(cbuff,off,len);
   }
  
   if (!reader.ready())
    reader = null;
	
   return result;
  }
 };
 
 public boolean		ready() throws IOException
 {
  return (reader != null && reader.ready());
 }
 
 public long	skip(long n) throws IOException
 {
  if (reader != null)
   return reader.skip(n);

  return 0; 
 };
 
 protected void		enter()
 {
  reader = new StringReader(input.getText() + "\n");
 
  input.setText("");
  input.setEditable(false);
  enter.setEnabled(false);
  
  Thread.currentThread().yield();
 };
 
 protected void		readyInputAndEnter()
 {
  input.setEditable(true);
  input.requestFocus();
  enter.setEnabled(true);
 };
};
