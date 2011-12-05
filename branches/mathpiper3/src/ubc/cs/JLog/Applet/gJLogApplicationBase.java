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
//	JLogApplicationBase
//#########################################################################

package ubc.cs.JLog.Applet;

import java.awt.*;
import java.awt.event.*;
import java.net.*;
import java.io.*;
import java.util.*;
import ubc.cs.JLog.Foundation.*;
import ubc.cs.JLog.Parser.*;

/**
* This is the abstract Application class for the Prolog in Java environment. 
* It includes <code>jPrologServices</code>, but does not specify the user
* interface.  It is designed as a super class for any applications which needs
* the <code>jPrologServices</code>.  Derivative authors should inherit from
* this class to preserve the <code>getApplicationInfo()</code> credits.
* This class also supports the use of a single menu bar for all windows of
* the application.  This is not straightforward because Java uses a per
* window menu bar by default.  This class supports the construction of duplicate
* menu bars, so that each window can have the same menu bar (by appearance,
* but it is a copy), and also supports updating some common menu items so that
* they appear the same (e.g., same hilight, same check state) for all menu bar
* copies.
*
* @author       Glendon Holst
* @version      %I%, %G%
*/
abstract public class gJLogApplicationBase extends Frame 
                        implements iJLogApplBaseServices, iMenuServiceRequester
{ 
 public static final String 		MENU_CLOSE = "close";
 public static final String 		MENU_SAVE = "save";
 public static final String 		MENU_SAVEAS = "saveas";
 public static final String 		MENU_CUT = "cut";
 public static final String 		MENU_COPY = "copy";
 public static final String 		MENU_PASTE = "paste";
 public static final String 		MENU_SELECTALL = "selectall";
 public static final String 		MENU_FIND = "find";
 public static final String 		MENU_FINDNEXT = "findnext";
 public static final String 		MENU_GOTOLINE = "gotoline";
 public static final String 		MENU_CONSULTKB = "consultkb";
 public static final String 		MENU_CONSULTALLKB = "consultallkb";
 public static final String 		MENU_RESETKB = "resetkb";
 public static final String 		MENU_PAUSEQ = "pauseq";
 public static final String 		MENU_STOPQ = "stopq";
 public static final String 		MENU_DEBUG = "debug";
 public static final String 		MENU_UNKNOWNPRED = "unknownpredicate";
 public static final String 		MENU_TRACE = "trace";
 public static final String 		MENU_BREAK = "break";

 public static final String 		MENU_LABEL_PAUSEQ_PAUSE = "Pause Query";
 public static final String 		MENU_LABEL_PAUSEQ_CONT = "Continue Query";

 protected static gJLogBase 	base = null;
 protected static Vector		kb_windows = null;

 protected String  		current_directory = null;
 
 public gJLogApplicationBase()
 {
  if (base == null)
  {
   base = new gJLogBase(this);
   base.init();
  }
  
  if (kb_windows == null)
   kb_windows = new Vector();
 };

 /**
 * This function returns the credit assignment and copyright informaiton string.
 * It must be preserved by authors of derivative works. All applications should 
 * be derived from the <code>gJLogApplicationBase</code> class, and if they 
 * provide their own <code>getApplicationInfo()</code> function it should 
 * invoke this version first. 
 * For example: <code>return super.getApplicationInfo() + "derivative information";</code>
 *
 * @return 	The credit and information string.
 */ 
 public String 		getApplicationInfo()
 {
  return base.getRequiredCreditInfo();
 };

 public String 		getRequiredCreditInfo()
 {
  return base.getRequiredCreditInfo();
 };
 
 public String 		getSource() throws IOException
 {
  return base.getSource();
 }

 public URL				getURLFromFilename(String name) throws MalformedURLException, IOException
 {
  return jPrologFileServices.getURLFromFilename_S(name);
 };

 public InputStream 	getInputStreamFromFilename(String name) throws 
                                            MalformedURLException, IOException
 {
  return jPrologFileServices.getInputStreamFromFilename_S(name);
 };

 public URL				getResourceURLFromFilename(String name) throws MalformedURLException, IOException
 {
  return jPrologFileServices.getResourceURLFromFilename_S(name);
 };

 public InputStream 	getResourceInputStreamFromFilename(String name) throws 
                                            MalformedURLException, IOException
 {
  return jPrologFileServices.getResourceInputStreamFromFilename_S(name);
 };

 public String 		getTextFromInputStream(InputStream in_strm) throws IOException
 {
  return base.getTextFromInputStream(in_strm);
 };

 public String 		getParameter(String name)
 {
  return null;
 }; 
 
 public String 		convertStringLinebreaks(String param)
 {
  return base.convertStringLinebreaks(param);  
 }; 

 public Image 		getImage(String name)
 {
  return getToolkit().getImage(name);
 };

 /**
 * This function returns a new menu bar, customized for the given 
 * <code>iMenuServiceRequester</code>.  The menu bar will have the same layout
 * and items, so that all windows have the same menus, but those menus items 
 * not handled by the application will be passed to the iMenuServiceRequester
 * so that it can register a listener and the appropriate action. 
 *
 * @param s 	The <code>iMenuServiceRequester</code> that this menu bar is for.
 * @return 	A menu bar, customized for the given iMenuServiceRequester.
 */ 
 public MenuBar 	createMenuBar(iMenuServiceRequester s)
 {
  return null;
 };

 /**
 * Recreates the dynamic window menu for all registered windows. 
 */ 
 public void 		updateWindowMenu()
 {Enumeration 		e = kb_windows.elements();
  
  while (e.hasMoreElements())
  {
   updateWindowMenu(((gConsultWindow) e.nextElement()).getMenuBar());
  }
 };

 /**
 * Recreates the dynamic portion of the window menu for the given menu bar. 
 *
 * @param menubar 	The <code>MenuBar</code> to update.
 */ 
 protected void 	updateWindowMenu(MenuBar menubar)
 {Menu			win_menu = menubar.getMenu(getWindowMenuID());
  
  while (win_menu.getItemCount() > getWindowMenuDynamicStart())
   win_menu.remove(getWindowMenuDynamicStart());
   
  {Enumeration 		e = kb_windows.elements();
   
   while (e.hasMoreElements())
   {final gConsultWindow 	cw = ((gConsultWindow) e.nextElement());
    String 			s = cw.getTitle();
    MenuItem 		mi;

//    mi = new CheckboxMenuItem(s,false);
    mi = new MenuItem(s);
    win_menu.add(mi);
    mi.addActionListener(new ActionListener() 
        {
         public void actionPerformed (ActionEvent e) 
         {final gConsultWindow 	w = cw; 
          
          w.setVisible(true);
         }
        }
       );
//    mi.addItemListener(new ItemListener() 
//        {
//         public void itemStateChanged (ItemEvent e) 
//         {final gConsultWindow 	w = cw; 
//          
//          w.setVisible(true);
//         }
//        }
//       );
   }
  }
 };

 /**
 * This function updates the debug portion of the tool menu for all registered
 * windows.
 *
 * @param dbg 		true is the debugging is on.
 * @param unkpred 	true if prolog throws an error on unknown predicates. false if
 * 			if unknown predicates are false.
 * @param trce 		true is trace all goals.
 * @param brk 		true if break after each step.
 * @param in_query 	true if a query is presently underway.
 */ 
 public void 		updateToolMenuDebug(boolean dbg,
                                    boolean unkpred,boolean trce,boolean brk,
                                    boolean in_query)
 {Enumeration 		e = kb_windows.elements();
  
  while (e.hasMoreElements())
  {
   updateToolMenuDebug(((gConsultWindow) e.nextElement()).getMenuBar(),
                        dbg,unkpred,trce,brk,in_query);
  }
 };

 /**
 * This function updates the debug portion of the tool menu for all registered
 * windows.
 *
 * @param menubar 	The <code>iMenuServiceRequester</code> that this menu bar is for.
 * @param dbg 		true is the debugging is on.
 * @param unkpred 	true if prolog throws an error on unknown predicates. false if
 * 			if unknown predicates are false.
 * @param trce 		true is trace all goals.
 * @param brk 		true if break after each step.
 * @param in_query 	true if a query is presently underway.
 */ 
 protected void 	updateToolMenuDebug(MenuBar menubar,boolean dbg,
                                    boolean unkpred,boolean trce,boolean brk,
                                    boolean in_query)
 {Menu			tool_menu = menubar.getMenu(getToolMenuID());
  int 			i, max = tool_menu.getItemCount();
  
  for (i = 0; i < max; i++)
  {MenuItem 	mi = tool_menu.getItem(i);
  
   if (mi.getActionCommand() == MENU_DEBUG)
   {
    ((CheckboxMenuItem) mi).setState(dbg);
    mi.setEnabled(!in_query);
   }
   else if (mi.getActionCommand() == MENU_UNKNOWNPRED)
   {
    ((CheckboxMenuItem) mi).setState(unkpred);
    mi.setEnabled(!in_query);
   }
   else if (mi.getActionCommand() == MENU_TRACE)
   {
    ((CheckboxMenuItem) mi).setState(trce);
    mi.setEnabled(dbg);
   }
   else if (mi.getActionCommand() == MENU_BREAK)
   {
    ((CheckboxMenuItem) mi).setState(brk);
    mi.setEnabled(dbg);
   }
  }
 };

 public void 		updateToolMenuQuery(boolean in_query,boolean paused)
 {Enumeration 		e = kb_windows.elements();
  
  while (e.hasMoreElements())
  {
   updateToolMenuQuery(((gConsultWindow) e.nextElement()).getMenuBar(),
                        in_query,paused);
  }
 };

 protected void 	updateToolMenuQuery(MenuBar menubar,boolean in_query,
                                boolean paused)
 {Menu			tool_menu = menubar.getMenu(getToolMenuID());
  int 			i, max = tool_menu.getItemCount();
  
  for (i = 0; i < max; i++)
  {MenuItem 	mi = tool_menu.getItem(i);
  
   if (mi.getActionCommand() == MENU_PAUSEQ)
   {
    if (paused)
     mi.setLabel(MENU_LABEL_PAUSEQ_CONT);
    else
     mi.setLabel(MENU_LABEL_PAUSEQ_PAUSE);

    mi.setEnabled(in_query);
   }
   else if (mi.getActionCommand() == MENU_STOPQ)
   {
    mi.setEnabled(in_query);
   }
  }
 };

 public void 		updateToolMenuReset(MenuBar menubar,boolean state)
 {Menu			tool_menu = menubar.getMenu(getToolMenuID());
  int 			i, max = tool_menu.getItemCount();
  
  for (i = 0; i < max; i++)
  {MenuItem 	mi = tool_menu.getItem(i);
  
   if (mi.getActionCommand() == MENU_RESETKB)
   {
    mi.setEnabled(state);
   }
  }
 };

 public void 		updateToolMenuConsultAll(boolean state)
 {Enumeration 		e = kb_windows.elements();
  
  while (e.hasMoreElements())
  {
   updateToolMenuConsultAll(((gConsultWindow) e.nextElement()).getMenuBar(),
                        state);
  }
 };

 public void 		updateToolMenuConsultAll(MenuBar menubar,boolean state)
 {Menu			tool_menu = menubar.getMenu(getToolMenuID());
  int 			i, max = tool_menu.getItemCount();
  
  for (i = 0; i < max; i++)
  {MenuItem 	mi = tool_menu.getItem(i);
  
   if (mi.getActionCommand() == MENU_CONSULTALLKB)
   {
    mi.setEnabled(state);
   }
  }
 };

 public void 			addKBWindow(gConsultWindow w)
 {
  kb_windows.addElement(w);
  updateWindowMenu();
 };

 public void 			removeKBWindow(gConsultWindow w)
 {
  kb_windows.removeElement(w);
  updateWindowMenu();
 };

 public int 			getKBWindowCount()
 {
  return kb_windows.size();
 };

 /**
 * Returns the index of the tool menu in the menu bar.
 *
 * @return 	index number of tool menu in menu bar.
 */ 
 protected int 			getToolMenuID()
 {
  return 3;
 };
 
 /**
 * Returns the index of the window menu in the menu bar.
 *
 * @return 	index number of window menu in menu bar.
 */ 
 protected int 			getWindowMenuID()
 {
  return 4;
 };

 /**
 * Returns the index where the dynamic elements of the window menu start.
 *
 * @return 	index number of first dynamic element in window menu.
 */ 
 protected int 			getWindowMenuDynamicStart()
 {
  return 6;
 };

 public String 			getCurrentDirectory()
 {
  return current_directory;
 };

 public void 			setCurrentDirectory(String d)
 {
  current_directory = d;
 };

 public jPrologServices 	getPrologServices()
 {
  return base.getPrologServices();
 };
};
