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
//	gTracePanel
//##################################################################################

package ubc.cs.JLog.Applet;

import java.lang.*;
import java.util.*;
import java.awt.*;
import java.awt.event.*;
import ubc.cs.JLog.Terms.*;
import ubc.cs.JLog.Foundation.*;

public class gTracePanel extends Panel
{
 public class gTraceItem implements iNameArity
 {
  protected iNameArity 		item;
  protected boolean 		traceon,breakon;
  
  public 		gTraceItem(iNameArity na,boolean t,boolean b)
  {
   item = na;
   traceon = t;
   breakon =b;
  };
  
  public void 		setTrace(boolean t)
  {
   traceon = t;
  };
  
  public void 		setBreak(boolean b)
  {
   breakon = b;
  };
  
  public boolean 	getTrace()
  {
   return traceon;
  };
  
  public boolean 	getBreak()
  {
   return breakon;
  };
  
  public String 	getName()
  {
   return item.getName();
  };
  
  public int 		getArity()
  {
   return item.getArity();
  };
  
  public String 	toString()
  {StringBuffer 	sb = new StringBuffer();
  
   sb.append("[" + (traceon ? "T" : " ") + (breakon ? "B" : " ") + "] ");
   sb.append(item.getName() + "/" + String.valueOf(item.getArity()));
   
   return sb.toString();
  };
 };

 protected final static String 		TRACELABEL = "Predicates";

 protected final static String 		TRACEUPDATE = " Update ";
 protected final static String 		HIDEBUTTON = "   Hide   ";
 protected final static String 		SHOWBUTTON = " Show All ";
 protected final static String 		SETTRACE =   "  Set Trace  ";
 protected final static String 		UNSETTRACE = " Unset Trace ";
 protected final static String 		SETBREAK =   " Set Break ";
 protected final static String 		UNSETBREAK = " Unset Break ";
 protected final static String 		CLEARTRACE = "Clear Traces";
 protected final static String 		CLEARBREAK = "Clear Breaks";

 protected java.awt.List 		predlist;
 protected Label 			predlabel;
 protected Button 			hideshow,update,settrace,setbreak,cleartrace,clearbreak;
 
 // required for debug functionality
 protected jPrologServices 		prolog; 
 protected Vector 				user_predicates,builtin_predicates;
 protected Hashtable 			traceitem_hash;
 
 public 	gTracePanel(jPrologServices ps)
 {
  {
   user_predicates = new Vector(0);
   builtin_predicates = new Vector(0);
   traceitem_hash = new Hashtable(1);
  }
  {
   prolog = ps;
  }
  {// create predicate list for trace
   predlist = new java.awt.List();
   predlist.setBackground(Color.white);
   predlist.setForeground(Color.black);
   predlist.setMultipleMode(false);
   predlist.addItemListener(new ItemListener()
                       {
                        public void 	itemStateChanged(ItemEvent e)
                        {
                         settrace.setEnabled(e.getStateChange() == ItemEvent.SELECTED);
                         setbreak.setEnabled(e.getStateChange() == ItemEvent.SELECTED);
                         
                         if (e.getStateChange() == ItemEvent.SELECTED)
						  gTracePanel.this.updateSelectedState();
                        }
                       }
                      );
   predlist.setEnabled(false);
   predlist.setFont(new Font("Monospaced",Font.PLAIN,10));
   
   predlabel = new Label(TRACELABEL,Label.LEFT);
  }
  {// create button for updating predicates
   update = new Button(TRACEUPDATE);
   update.addActionListener(new ActionListener() 
                       {
                        public void 	actionPerformed(ActionEvent e)
                        {
                         gTracePanel.this.updatePredicates();
                        }
                       }
                      );
   update.setBackground(Color.white);
   update.setForeground(Color.black);
   
   update.setEnabled(false);
  }
  {// create button for hiding / showing builtin predicates
   hideshow = new Button(SHOWBUTTON);
   hideshow.addActionListener(new ActionListener() 
                       {
                        public void 	actionPerformed(ActionEvent e)
                        {
                         gTracePanel.this.toggleHideShow();
                        }
                       }
                      );
   hideshow.setBackground(Color.white);
   hideshow.setForeground(Color.black);
   
   hideshow.setEnabled(false);
  }
  {// create button for setting traces
   settrace = new Button(SETTRACE);
   settrace.addActionListener(new ActionListener() 
                       {
                        public void 	actionPerformed(ActionEvent e)
                        {
                         gTracePanel.this.setTrace();
                        }
                       }
                      );
   settrace.setBackground(Color.white);
   settrace.setForeground(Color.black);
   
   settrace.setEnabled(false);
  }
  {// create button for setting breakpoints
   setbreak = new Button(SETBREAK);
   setbreak.addActionListener(new ActionListener() 
                       {
                        public void 	actionPerformed(ActionEvent e)
                        {
                         gTracePanel.this.setBreakpoint();
                        }
                       }
                      );
   setbreak.setBackground(Color.white);
   setbreak.setForeground(Color.black);
   
   setbreak.setEnabled(false);
  }
  {// create button for clearing traces
   cleartrace = new Button(CLEARTRACE);
   cleartrace.addActionListener(new ActionListener() 
                       {
                        public void 	actionPerformed(ActionEvent e)
                        {
                         gTracePanel.this.clearTrace();
                        }
                       }
                      );
   cleartrace.setBackground(Color.white);
   cleartrace.setForeground(Color.black);
   cleartrace.setEnabled(false);
  }
  {// create button for clearing breakpoints
   clearbreak = new Button(CLEARBREAK);
   clearbreak.addActionListener(new ActionListener() 
                       {
                        public void 	actionPerformed(ActionEvent e)
                        {
                         gTracePanel.this.clearBreakpoint();
                        }
                       }
                      );
   clearbreak.setBackground(Color.white);
   clearbreak.setForeground(Color.black);
   clearbreak.setEnabled(false);
  }
  
  {Panel 	button_panel,clear_buttons,trace_buttons,top_panel,inner_panel;
   
   setLayout(new BorderLayout());
   setFont(new Font("SansSerif",Font.PLAIN,12));
   setBackground(Color.lightGray);
   setForeground(Color.black);
   
   add(new Panel(),BorderLayout.NORTH);
 
   button_panel = new Panel(new BorderLayout());
   top_panel = new Panel(new BorderLayout());
   inner_panel = new Panel(new BorderLayout());
   trace_buttons = new Panel(new GridLayout(4,0));
   clear_buttons = new Panel(new GridLayout(4,0));
   
   trace_buttons.add(update);
   trace_buttons.add(new Panel());
   trace_buttons.add(settrace);
   trace_buttons.add(setbreak);

   clear_buttons.add(hideshow);
   clear_buttons.add(new Panel());
   clear_buttons.add(cleartrace);
   clear_buttons.add(clearbreak);
      
   button_panel.add(clear_buttons,BorderLayout.WEST);
   button_panel.add(trace_buttons,BorderLayout.EAST);
   button_panel.add(new Panel(),BorderLayout.CENTER);
   button_panel.add(new Panel(),BorderLayout.NORTH);
   
   top_panel.add(predlist,BorderLayout.CENTER);
   top_panel.add(predlabel,BorderLayout.NORTH);
   
   inner_panel.add(top_panel,BorderLayout.CENTER);
   inner_panel.add(button_panel,BorderLayout.SOUTH);
   
   add(inner_panel,BorderLayout.CENTER);    
  }
 };
 
 public gTraceItem 	getTraceInfo(iNameArity na)
 {
  return (gTraceItem) traceitem_hash.get(internal_getNameArityString(na));
 };
 
 public void 		setTrace()
 {gTraceItem 	ti;
 
  try
  {
   ti = internal_getSelectedTraceItem();
   ti.setTrace(!ti.getTrace());
   internal_updatePredicateListItem(ti);
  }
  catch (InvalidTraceItemException e)
  {
  }
  updateSelectedState();
 };
 
 public void 		setBreakpoint()
 {gTraceItem 	ti;
 
  try
  {
   ti = internal_getSelectedTraceItem();
   ti.setBreak(!ti.getBreak());
   internal_updatePredicateListItem(ti);
  }
  catch (InvalidTraceItemException e)
  {
  } 
  updateSelectedState();
 };

 public void 		clearTrace()
 {
  {Enumeration 		e = user_predicates.elements();
  
   while (e.hasMoreElements())
   {gTraceItem 	ti = (gTraceItem) e.nextElement();
   
    if (ti.getTrace())
    {
     ti.setTrace(false);
     internal_updatePredicateListItem(ti);
    }
   }  
  }
  {Enumeration 		e = builtin_predicates.elements();
  
   while (e.hasMoreElements())
   {gTraceItem 	ti = (gTraceItem) e.nextElement();
   
    if (ti.getTrace())
    {
     ti.setTrace(false);
     internal_updatePredicateListItem(ti);
    }
   }  
  }
  settrace.setLabel(SETTRACE);
  updateSelectedState();
 };
 
 public void 		clearBreakpoint()
 {
  {Enumeration 		e = user_predicates.elements();
  
   while (e.hasMoreElements())
   {gTraceItem 	ti = (gTraceItem) e.nextElement();
   
    if (ti.getBreak())
    {
     ti.setBreak(false);
     internal_updatePredicateListItem(ti);
    }
   }  
  }
  {Enumeration 		e = builtin_predicates.elements();
  
   while (e.hasMoreElements())
   {gTraceItem 	ti = (gTraceItem) e.nextElement();
   
    if (ti.getBreak())
    {
     ti.setBreak(false);
     internal_updatePredicateListItem(ti);
    }
   }  
  }
  setbreak.setLabel(SETBREAK);
  updateSelectedState();
 };
 
 public void 		toggleHideShow()
 {iNameArity 	na;
 
  hideshow.setLabel((hideshow.getLabel() == HIDEBUTTON ? SHOWBUTTON : HIDEBUTTON));
  try
  {
   na = internal_getSelectedTraceItem();
  }
  catch (InvalidTraceItemException e)
  {
   na = null;
  }
  
  internal_updatePredicateList(na);
 };
 
 public void 		updateSelectedState()
 {
  try
  {gTraceItem 		ti = internal_getSelectedTraceItem();

   settrace.setLabel((ti.getTrace() ? UNSETTRACE : SETTRACE));
   setbreak.setLabel((ti.getBreak() ? UNSETBREAK : SETBREAK));
  }
  catch (InvalidTraceItemException e)
  {
   settrace.setLabel(SETTRACE);
   setbreak.setLabel(SETBREAK);
   settrace.setEnabled(false);
   setbreak.setEnabled(false);
  }
 };
 
 public void 		enableDebugging()
 {
  updatePredicates();

  predlist.setEnabled(true);
  hideshow.setEnabled(true);
  update.setEnabled(true);
 
  settrace.setEnabled(predlist.getSelectedIndex() >= 0);
  setbreak.setEnabled(predlist.getSelectedIndex() >= 0);
  updateSelectedState();
 
  cleartrace.setEnabled(true);
  clearbreak.setEnabled(true);
 };
 
 public void 		disableDebugging()
 {
  predlist.setEnabled(false);
  hideshow.setEnabled(false);
  update.setEnabled(false);
  settrace.setEnabled(false);
  setbreak.setEnabled(false);
  cleartrace.setEnabled(false);
  clearbreak.setEnabled(false);
  
  user_predicates = new Vector(0);
  builtin_predicates = new Vector(0);
  traceitem_hash = new Hashtable(1);
  internal_updatePredicateList(null);
 };
 
 public void 		updatePredicates()
 {Vector 		uv,bv;
  iNameArity 	na;
  
  try
  {
   na = internal_getSelectedTraceItem();
  }
  catch (InvalidTraceItemException e)
  {
   na = null;
  }

  {Enumeration 		e = prolog.getPredicateRegistry().enumPredicates();
 
   bv = new Vector();
  
   while (e.hasMoreElements())
   {iNameArity 		na2 = (iNameArity) e.nextElement();
    
    try
    {gTraceItem 		ti;

     ti = internal_getNameArityTraceItem(na2);
     internal_sortInsertNameArity(bv,new gTraceItem(na2,ti.getTrace(),ti.getBreak()));
    }
    catch (InvalidTraceItemException e1)
    {
     internal_sortInsertNameArity(bv,new gTraceItem(na2,false,false));
    } 
   }
   
   e = prolog.getKnowledgeBase().enumDefinitions();
  
   while (e.hasMoreElements())
   {jRuleDefinitions 	rd = (jRuleDefinitions) e.nextElement();
   
    if (rd.isBuiltin())
    {
     try
     {gTraceItem 		ti;

      ti = internal_getNameArityTraceItem(rd);
      internal_sortInsertNameArity(bv,new gTraceItem(rd,ti.getTrace(),ti.getBreak()));
     }
     catch (InvalidTraceItemException e2)
     {
      internal_sortInsertNameArity(bv,new gTraceItem(rd,false,false));
     } 
    }
   }
  }
  {Enumeration 		e = prolog.getKnowledgeBase().enumDefinitions();
  
   uv = new Vector();
   
   while (e.hasMoreElements())
   {jRuleDefinitions 	rd = (jRuleDefinitions) e.nextElement();
   
    if (!rd.isBuiltin())
    {
     try
     {gTraceItem 		ti;

      ti = internal_getNameArityTraceItem(rd);
      internal_sortInsertNameArity(uv,new gTraceItem(rd,ti.getTrace(),ti.getBreak()));
     }
     catch (InvalidTraceItemException e3)
     {
      internal_sortInsertNameArity(uv,new gTraceItem(rd,false,false));
     } 
    }
   }
  }
  
  // the design of this method and the methods it calls, requires that the instance 
  // predicate vectors are updated at the end, after the old versions are used to lookup
  // the trace items.
  user_predicates = uv;
  builtin_predicates = bv;
  
  // create hash for quick lookup
  traceitem_hash = new Hashtable(user_predicates.size()+builtin_predicates.size()+1);
  {Enumeration 		e;
  
   e = user_predicates.elements();
   while (e.hasMoreElements())
   {gTraceItem 		ti = (gTraceItem) e.nextElement();
   
    traceitem_hash.put(internal_getNameArityString(ti),ti);
   }
   
   e = builtin_predicates.elements();
   while (e.hasMoreElements())
   {gTraceItem 		ti = (gTraceItem) e.nextElement();
   
    traceitem_hash.put(internal_getNameArityString(ti),ti);
   }
  }
  
  internal_updatePredicateList(na);
 };
 
 protected synchronized void 		internal_updatePredicateList(iNameArity keepselected)
 {
  predlist.removeAll();

  {Enumeration 		e = user_predicates.elements();
  
   while (e.hasMoreElements())
    predlist.add(((gTraceItem) e.nextElement()).toString());  
  }

  if (hideshow.getLabel() == HIDEBUTTON)
  {Enumeration 		e = builtin_predicates.elements();
  
   while (e.hasMoreElements())
    predlist.add(((gTraceItem) e.nextElement()).toString());  
  }

  if (keepselected != null)
  {
   try
   {
    predlist.select(internal_getNameArityIndex(keepselected));
   }
   catch (InvalidTraceItemException e)
   {
   }
  }
  updateSelectedState();
 };

 protected synchronized void 		internal_updatePredicateListItem(gTraceItem ti)
 {
  try
  {int 		i = internal_getTraceIndex(ti), s = predlist.getSelectedIndex();
  
   predlist.replaceItem(ti.toString(),i);
   predlist.select(s);
  }
  catch (InvalidTraceItemException e)
  {
  }  
 };
 
 protected boolean 	internal_sortCompareGreaterThan(iNameArity na1,iNameArity na2)
 {String 	s1 = na1.getName().toLowerCase(),s2 = na2.getName().toLowerCase();
  int 		result;
  
  result = s1.compareTo(s2);
  if (result == 0)
   return na1.getArity() > na2.getArity();
  
  return (result > 0);
 };
 
 protected void 	internal_sortInsertNameArity(Vector v,iNameArity na)
 {int 	i,max,result;
 
  for (i = 0, max = v.size(); i < max; i++)
  {
   if (internal_sortCompareGreaterThan((iNameArity) v.elementAt(i),na))
   {
    v.insertElementAt(na,i);
    return;
   }    
  }
  v.insertElementAt(na,v.size());
 };
 
 protected synchronized gTraceItem 	internal_getSelectedTraceItem()
 {int 	i;
 
  return internal_getTraceItem(predlist.getSelectedIndex());
 };

 protected synchronized gTraceItem 	internal_getTraceItem(int i)
 {
  if (i < 0)
   throw new InvalidTraceItemException();
  
  if (i < user_predicates.size())
   return (gTraceItem) user_predicates.elementAt(i);
   
  i -= user_predicates.size(); 
  if (i < builtin_predicates.size())
   return (gTraceItem) builtin_predicates.elementAt(i);
   
  throw new InvalidTraceItemException();
 };

 protected synchronized int 	internal_getTraceIndex(gTraceItem ti)
 {int 	i;
 
  if ((i = user_predicates.indexOf(ti)) >= 0)
   return i;
  
  if (hideshow.getLabel() != HIDEBUTTON)
   throw new InvalidTraceItemException();

  if ((i = builtin_predicates.indexOf(ti)) >= 0)
   return i + user_predicates.size();
   
  throw new InvalidTraceItemException();
 };

 protected synchronized gTraceItem 	internal_getNameArityTraceItem(iNameArity na)
 {
 
  {Enumeration 		e = user_predicates.elements();
  
   while (e.hasMoreElements())
   {gTraceItem 		ti = (gTraceItem) e.nextElement();
   
    if (na.getName().equals(ti.getName()) && na.getArity() == ti.getArity())
     return ti;
   }  
  }

  {Enumeration 		e = builtin_predicates.elements();
  
   while (e.hasMoreElements())
   {gTraceItem 		ti = (gTraceItem) e.nextElement();
   
    if (na.getName().equals(ti.getName()) && na.getArity() == ti.getArity())
     return ti;
   }  
  }

  throw new InvalidTraceItemException();
 };

 protected synchronized int 	internal_getNameArityIndex(iNameArity na)
 {int 	i,max;
 
  for (i = 0,max = user_predicates.size();i < max; i++)
  {gTraceItem 		ti = (gTraceItem) user_predicates.elementAt(i);
   
   if (na.getName().equals(ti.getName()) && na.getArity() == ti.getArity())
    return i;
  }

  if (hideshow.getLabel() != HIDEBUTTON)
   throw new InvalidTraceItemException();

  for (i = 0,max = builtin_predicates.size();i < max; i++)
  {gTraceItem 		ti = (gTraceItem) builtin_predicates.elementAt(i);
   
   if (na.getName().equals(ti.getName()) && na.getArity() == ti.getArity())
    return i;
  }

  throw new InvalidTraceItemException();
 };
 
 protected String 		internal_getNameArityString(iNameArity na)
 {
  return new String(na.getName()+"/"+String.valueOf(na.getArity()));
 };
};
