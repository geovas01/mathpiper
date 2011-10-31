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
//	gDebugPanel
//##################################################################################

package ubc.cs.JLog.Applet;

import java.lang.*;
import java.util.*;
import java.awt.*;
import java.awt.event.*;
import ubc.cs.JLog.Foundation.*;

public class gDebugPanel extends Panel
{
 protected final static String 		TRACE_TAB = "trace";
 protected final static String 		PROVED_TAB = "proved";
 protected final static String 		GOALS_TAB = "goals";

 protected final static String 		DEBUG = "Debugger On";
 protected final static String 		A_UNKNOWNPRED = "Abort on Unknown Predicate";
 protected final static String 		BREAKALL = "Break on Fail";
 protected final static String 		TRACEALL = "Trace All";
 
 protected final static String 		RUN = "Run";
 protected final static String 		STEPIN = "Step In";
 protected final static String 		STEPOVER = "Step Over";
 protected final static String 		BREAK = "Break";
 protected final static String 		CLEAR = "Clear";

 protected gCardPanel 		lists;
 protected gButtonTabMenu 	menu;
 protected TextArea 		info;
 protected Checkbox 		debug,unkpred,breakall,traceall;
 protected Button 			runpro,stepin,stepover,stop,clear;
 protected gButtonTab 		trace,proved,goals;
 protected gStackPanel		proved_stack,goals_stack;
 protected gTracePanel 		trace_list;
 
 // required for debug functionality
 protected jPrologServices 		prolog; 
 protected jDebugProver 		prover = null;

 protected int 				run_state;
 protected jGoal 			prover_currentnextgoal,prover_stopgoal;
 protected final static int 	STATE_STOP = 0;
 protected final static int 	STATE_RUN = 1;
 protected final static int 	STATE_STEPIN = 2;
 protected final static int 	STATE_STEPOVER = 3;

 public 	gDebugPanel(jPrologServices ps,boolean show_buttons)
 {
  {
   prolog = ps;
   prolog.addBeginQueryListener(new jPrologServiceListener()
                       {
                        public void 	handleEvent(jPrologServiceEvent e)
                        {// during a query we want this to remain at the original state
                         debug.setEnabled(false);
                        }
                       }
                      );
   prolog.addRetryQueryListener(new jPrologServiceListener()
                       {
                        public void 	handleEvent(jPrologServiceEvent e)
                        {
                         if (e instanceof jUserQueryEvent)
                         {jUserQueryEvent 	uqe = (jUserQueryEvent) e;
                         
                          setRunState(STATE_STOP);
                
                          if (prolog.getDebugging() && uqe.getResult())
                           info.append("\nQuery Succeeded\n");
                         }
                        }
                       }
                      );
   prolog.addEndQueryListener(new jPrologServiceListener()
                       {
                        public void 	handleEvent(jPrologServiceEvent e)
                        {
                         queryTerminated();
                         if (prolog.getDebugging())
                          info.append("\nQuery Failed\n");
                        }
                       }
                      );
   prolog.addEndConsultListener(new jPrologServiceListener()
                       {
                        public void 	handleEvent(jPrologServiceEvent e)
                        {
                         if (prolog.getDebugging())
                          trace_list.updatePredicates();
                        }
                       }
                      );
   prolog.addThreadStoppedListener(new jPrologServiceListener()
                       {
                        public void 	handleEvent(jPrologServiceEvent e)
                        {
  						 queryTerminated();
                        }
                       }
                      );
   prolog.addDebugMessagesListener(new jPrologServiceListener()
                       {
                        public void 	handleEvent(jPrologServiceEvent e)
                        {
                         if (e instanceof jDebugProverStartedEvent)
                         {
                          debugProverStarted((jDebugProverStartedEvent) e);
                         }
                         else if (e instanceof jDebugTryGoalEvent)
 						 {
 						  debugTryGoal((jDebugTryGoalEvent) e);
 						 }
                         else if (e instanceof jDebugProveGoalEvent)
                         {
                          debugProveGoal((jDebugProveGoalEvent) e);
                         }
                         else if (e instanceof jDebugProvedGoalsEvent)
                         {
                          debugProvedGoals((jDebugProvedGoalsEvent) e);
                         }
                         else if (e instanceof jDebugRetryGoalEvent)
                         {
                          debugRetryGoal((jDebugRetryGoalEvent) e);
                         }
                         else if (e instanceof jDebugFailGoalEvent)
                         {
                          debugFailGoal((jDebugFailGoalEvent) e);
                         }
                        }
                       }
                      );
   prolog.addStateChangedListener(new jPrologServiceListener()
                       {
                        public void 	handleEvent(jPrologServiceEvent e)
                        {
                         syncCheckboxes();
                        }
                       }
                      );
  }
  {// create area for debugging ouput and status info
   info = new TextArea("", 0, 0, TextArea.SCROLLBARS_BOTH);
   info.setBackground(Color.white);
   info.setForeground(Color.black);
   info.setEditable(false);
   info.setFont(new Font("Monospaced",Font.PLAIN,10));
  }
  {// create card for tracing
   trace_list = new gTracePanel(prolog);
  }
  {// create card for gaols stack
   goals_stack = new gStackPanel("Goals",info,prolog,false);
  }
  {// create card for proved stack
   proved_stack = new gProverStackPanel("Proved",info,prolog,goals_stack);
  }
  {// create working area for trace
   lists = new gCardPanel();
   
   lists.add(trace_list,TRACE_TAB);
   lists.add(proved_stack,PROVED_TAB);
   lists.add(goals_stack,GOALS_TAB);
  }
  {// create menu and items for workingarea
   menu = new gButtonTabMenu(gButtonTabMenu.HORIZONTAL,new Font("SansSerif",Font.PLAIN,12));
   
   menu.add(trace = new gButtonTab("Trace",menu,lists,TRACE_TAB));
   menu.add(proved = new gButtonTab("Proved",menu,lists,PROVED_TAB));
   menu.add(goals = new gButtonTab("Goal",menu,lists,GOALS_TAB));
   
   proved.setEnabled(false);
   goals.setEnabled(false);
  }
  {// create run button
   runpro = new Button(RUN);
   runpro.addActionListener(new ActionListener() 
                       {
                        public void 	actionPerformed(ActionEvent e)
                        {
                         gDebugPanel.this.runprogram();
                        }
                       }
                      );
   runpro.setBackground(Color.white);
   runpro.setForeground(Color.black);
   runpro.setEnabled(false);
  }
  {// create stepin button
   stepin = new Button(STEPIN);
   stepin.addActionListener(new ActionListener() 
                       {
                        public void 	actionPerformed(ActionEvent e)
                        {
                         gDebugPanel.this.stepin();
                        }
                       }
                      );
   stepin.setBackground(Color.white);
   stepin.setForeground(Color.black);
   stepin.setEnabled(false);
  }
  {// create stepover button
   stepover = new Button(STEPOVER);
   stepover.addActionListener(new ActionListener() 
                       {
                        public void 	actionPerformed(ActionEvent e)
                        {
                         gDebugPanel.this.stepover();
                        }
                       }
                      );
   stepover.setBackground(Color.white);
   stepover.setForeground(Color.black);
   stepover.setEnabled(false);
  }
  {// create break button
   stop = new Button(BREAK);
   stop.addActionListener(new ActionListener() 
                       {
                        public void 	actionPerformed(ActionEvent e)
                        {
                         gDebugPanel.this.stop();
                        }
                       }
                      );
   stop.setBackground(Color.white);
   stop.setForeground(Color.black);
   stop.setEnabled(false);
  }
  {// create clear button
   clear = new Button(CLEAR);
   clear.addActionListener(new ActionListener() 
                       {
                        public void 	actionPerformed(ActionEvent e)
                        {
                         gDebugPanel.this.clear();
                        }
                       }
                      );
   clear.setBackground(Color.white);
   clear.setForeground(Color.black);
  }
  {// create debug checkbox
   debug = new Checkbox(DEBUG);
   debug.addItemListener(new ItemListener()
                       {
                        public void 	itemStateChanged(ItemEvent e)
                        {
                         gDebugPanel.this.debug(e.getStateChange() == ItemEvent.SELECTED);
                        }
                       }
                      );
  }
  {// create unknown predicate checkbox
   unkpred = new Checkbox(A_UNKNOWNPRED);
   unkpred.addItemListener(new ItemListener()
                       {
                        public void 	itemStateChanged(ItemEvent e)
                        {boolean 	unkp = e.getStateChange() == ItemEvent.SELECTED;
                         gDebugPanel.this.prolog.setFailUnknownPredicate(!unkp);
                        }
                       }
                      );
  }
  {// create break on all checkbox
   breakall = new Checkbox(BREAKALL);
   breakall.setEnabled(false);
  }
  {// create trace on all checkbox
   traceall = new Checkbox(TRACEALL);
   traceall.setEnabled(false);
  }
  
  {Panel 	checkbox_panel,control_buttons,left_panel,right_panel,inner_panel;
   Panel 	bottom_panel,control_panel,innerleft_panel;
   
   setLayout(new BorderLayout());
   setFont(new Font("SansSerif",Font.PLAIN,12));
   setBackground(Color.lightGray);
   setForeground(Color.black);
   
   add(new Panel(),BorderLayout.NORTH);
   add(new Panel(),BorderLayout.WEST);
   add(new Panel(),BorderLayout.EAST);
   add(new Panel(),BorderLayout.SOUTH);
 
   checkbox_panel = new Panel(new FlowLayout(FlowLayout.LEFT));
   control_buttons = new Panel(new GridLayout(1,0));
   control_panel = new Panel(new BorderLayout());
   bottom_panel = new Panel(new BorderLayout());
   left_panel = new Panel(new BorderLayout());
   right_panel = new Panel(new BorderLayout());
   innerleft_panel = new Panel(new BorderLayout());
   inner_panel = new Panel(new BorderLayout());
  
   checkbox_panel.add(debug);
   checkbox_panel.add(unkpred);
   checkbox_panel.add(traceall);
   checkbox_panel.add(breakall);
   
   control_buttons.add(runpro);
   control_buttons.add(stepin);
   control_buttons.add(stepover);
   control_buttons.add(stop);
   
   control_panel.add(control_buttons,BorderLayout.CENTER);
   control_panel.add(new Panel(),BorderLayout.SOUTH);

   bottom_panel.add(clear,BorderLayout.EAST);
   bottom_panel.add(new Panel(),BorderLayout.NORTH);

   right_panel.add(info,BorderLayout.CENTER);
   right_panel.add(control_panel,BorderLayout.NORTH);
   right_panel.add(bottom_panel,BorderLayout.SOUTH);
   
   innerleft_panel.add(menu,BorderLayout.NORTH);
   innerleft_panel.add(lists,BorderLayout.CENTER);
   
   left_panel.add(innerleft_panel,BorderLayout.CENTER);
   left_panel.add(new Panel(),BorderLayout.EAST);
   
   if (show_buttons)
    inner_panel.add(checkbox_panel,BorderLayout.NORTH);

   inner_panel.add(right_panel,BorderLayout.CENTER);
   inner_panel.add(left_panel,BorderLayout.WEST);
   inner_panel.add(new Panel(),BorderLayout.SOUTH);
   
   add(inner_panel,BorderLayout.CENTER);
   
   menu.setActiveTab(trace);
  }
  {// sync prolog services and debug panel
   debug.setState(prolog.getDebugging());
   debug(prolog.getDebugging());
 
   unkpred.setState(!prolog.getFailUnknownPredicate());
  }
 };

 public void 		syncCheckboxes()
 {
  if (debug.getState() != prolog.getDebugging())
  {
   debug.setState(prolog.getDebugging());
   debug(prolog.getDebugging());
  }
  
  if (unkpred.getState() == prolog.getFailUnknownPredicate())
  {
   unkpred.setState(!prolog.getFailUnknownPredicate());
  }
 };
  
 public void 		runprogram()
 {
  if (prover != null)
  {
//   info.append("running\n");
   setRunState(STATE_RUN);
  }
 };
 
 public void 		stepin()
 {
  if (prover != null)
  {
//   info.append("stepping into:\n");
   setRunState(STATE_STEPIN);
  }
 };
 
 public void 		stepover()
 {
  if (prover != null)
  {
//   info.append("steping over\n");
   prover_stopgoal = prover_currentnextgoal;
   setRunState(STATE_STEPOVER);
  }
 };
 
 public void 		stop()
 {
  if (prover != null)
  {
//   info.append("stopping at predicate:\n");
   setRunState(STATE_STOP);
  }
 };
 
 public void 		clear()
 {
  info.setText("");
 };

 public void 		setBreakState(boolean state)
 {
  breakall.setState(state);
 };

 public void 		setTraceState(boolean state)
 {
  traceall.setState(state);
 };

 public boolean 	getBreakState()
 {
  return breakall.getState();
 };

 public boolean 	getTraceState()
 {
  return traceall.getState();
 };

 public void 		setRunState(int state)
 {boolean 	running;
 
  run_state = state;
  running = run_state != STATE_STOP;
  
  proved_stack.enablePanel(!running);
  goals_stack.enablePanel(!running);
 
  if (!running)
  {
   proved_stack.update();
   goals_stack.update();
  };
  
  runpro.setEnabled(!running);
  stepin.setEnabled(!running);
  stepover.setEnabled(!running);
  stop.setEnabled(running);

  if (running)   
   prover.step();
 };
 
 public void 		queryTerminated()
 {
  // after a query, permit this to toggle
  debug.setEnabled(true);

  setRunState(STATE_STOP);

  // only used during query, so disable
  runpro.setEnabled(false);
  stepin.setEnabled(false);
  stepover.setEnabled(false);
  stop.setEnabled(false);
 
  prover = null;
 };
 
 public void 		debug(boolean state)
 {
  prolog.setDebugging(state);

  traceall.setEnabled(state);
  breakall.setEnabled(state);
  
  if (!state)
  {
   menu.setActiveTab(trace);
   trace_list.disableDebugging();
   prover = null;
  }
  else
   trace_list.enableDebugging();
  
  proved.setEnabled(state);
  goals.setEnabled(state);
 };
 
 public void 		debugProverStarted(jDebugProverStartedEvent de)
 {                         
  prover = de.getDebugProver();
  
  setRunState(STATE_STOP);
  prover_stopgoal = null;
  prover_currentnextgoal = null;
 };
 
 public void 		debugTryGoal(jDebugTryGoalEvent de)
 {gTracePanel.gTraceItem 	ti = trace_list.getTraceInfo(de.getGoal());
  
  prover_currentnextgoal = de.getNextGoal();

  if ((run_state == STATE_STEPOVER && prover_stopgoal == de.getGoal()) ||
      (run_state == STATE_STEPIN) || (ti != null && ti.getBreak()))
   setRunState(STATE_STOP);

  if (traceGoal(ti))
  {
   info.append("trying: " + de.getGoal().toString());
   info.append("\n");
  }
  switch (run_state)
  {
   case STATE_STEPOVER:
   case STATE_RUN:
    prover.step();
  }                         
 };
 
 public void 		debugProveGoal(jDebugProveGoalEvent de)
 {
  
  if (traceGoal(de.getGoal()))
  {
   info.append("proving: " + de.getGoal().toString());
   info.append("\n");
   
   if (de.getSubGoals().size() > 0)
   {Enumeration 	e = de.getSubGoals().elements();
   
    info.append("sub goals:\n");
    while (e.hasMoreElements())
    {
     info.append(e.nextElement().toString());
     info.append("\n");
    }
   }
   info.append("\n");
  }
 };
 
 public void 		debugProvedGoals(jDebugProvedGoalsEvent de)
 {Vector 	v = traceableDebugItems(de.getProvedGoals());
 
  if (v.size() > 0)
  {Enumeration 		e = v.elements();

   info.append("proved:\n");
   while (e.hasMoreElements())
   {jDebugProvedGoalStack.jDebugGoalItem 	dgi;
   
    dgi = (jDebugProvedGoalStack.jDebugGoalItem) e.nextElement();
  
    info.append(dgi.getGoal().toString());
    info.append("\n");
   }
   info.append("\n");
  }
 };
 
 public void 		debugRetryGoal(jDebugRetryGoalEvent de)
 {
  if (traceGoal(de.getGoal()))
  {
   info.append("retry: " + de.getGoal().toString());
   info.append("\n");
  }
 };
 
 public void 		debugFailGoal(jDebugFailGoalEvent de)
 {
  if (traceGoal(de.getGoal()))
  {
   if (breakall.getState())
    setRunState(STATE_STOP);
    
   info.append("failed: " + de.getGoal().toString());
   info.append("\n\n");
  }
 };
 
 protected boolean 	traceGoal(gTracePanel.gTraceItem ti)
 {
  if (traceall.getState() || run_state == STATE_STOP || run_state == STATE_STEPIN)
   return true;
      
  if (ti != null)
   return (ti.getTrace());
   
  return false;
 };
 
 protected boolean 	traceGoal(jGoal g)
 {gTracePanel.gTraceItem 	ti = trace_list.getTraceInfo(g);

  return traceGoal(ti);
 };
 
 protected Vector 	traceableDebugItems(Vector v)
 {Vector 		tv = new Vector(v.size());
  Enumeration 	e = v.elements();
  
  while (e.hasMoreElements())
  {jDebugProvedGoalStack.jDebugGoalItem 	dgi;
   
   dgi = (jDebugProvedGoalStack.jDebugGoalItem) e.nextElement();

   if (traceGoal(dgi.getGoal()))
    tv.addElement(dgi);
  }
  return tv;
 };
};