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
//	JLogApplication
//#########################################################################

package ubc.cs.JLog.Applet;

import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.*;
import java.lang.reflect.*;
import ubc.cs.JLog.Foundation.*;
import ubc.cs.JLog.Parser.*;

/**
 * This is the Applet for the full Prolog in Java environment, complete with
 * user interface.
 * 
 * @author Glendon Holst
 * @version %I%, %G%
 */
public class gJLogApplication extends gJLogApplicationBase {
    protected static gAboutDialog about_box = null;
    protected static Frame query_window = null;
    protected static gDebugWindow debug_window = null;
    protected static gWindowBase animation_window = null;
    protected static gWindowBase help_window = null;

    protected static gQueryPanel query;
    protected static boolean useBuiltinApplicationMenu;
    protected static boolean query_state = false;

    protected static Enumeration consult_enum = null;

    public static void main(String args[]) {
	new gJLogApplication();
    };

    public gJLogApplication() {
	super();

	setLayout(new GridLayout());

	// For MacOS X use the apple.eawt extensions to support standard menus
	// and behaviours
	// Sets useBuiltinApplicationMenu (so this code must appear early in the
	// constructor).
	try {
	    Class AppClass = Class.forName("com.apple.eawt.Application");
	    Class AppListenerClass = Class
		    .forName("com.apple.eawt.ApplicationListener");
	    Class AppAdapterClass = Class
		    .forName("com.apple.eawt.ApplicationAdapter");
	    Class InvocationClass = Class
		    .forName("java.lang.reflect.InvocationHandler");
	    Class ProxyClass = Class.forName("java.lang.reflect.Proxy");
	    Method AddMethod = AppClass.getMethod("addApplicationListener",
		    new Class[] { AppListenerClass });
	    Method GetAppMethod = AppClass.getMethod("getApplication",
		    new Class[] {});
	    Method NewProxyInstanceMethod = ProxyClass.getMethod(
		    "newProxyInstance", new Class[] { ClassLoader.class,
			    Class[].class, InvocationClass });
	    Object fApplication = GetAppMethod
		    .invoke(AppClass, new Object[] {});
	    Object fApplicationAdapter = NewProxyInstanceMethod.invoke(
		    ProxyClass,
		    new Object[] { AppAdapterClass.getClassLoader(),
			    AppAdapterClass.getInterfaces(),
			    new eawtApplicationAdapter(this) });

	    AddMethod
		    .invoke(fApplication, new Object[] { fApplicationAdapter });

	    useBuiltinApplicationMenu = true;
	} catch (Throwable e) {
	    useBuiltinApplicationMenu = false;
	}

	query = new gQueryPanel(getPrologServices(), null, false);
	add(query);

	if (query_window == null)
	    query_window = this;

	// these windows will use the createMenuBar method of this application,
	// so useBuiltinApplicationMenu must be set beforehand.
	if (about_box == null)
	    about_box = new gAboutDialog(this, false);
	if (debug_window == null)
	    debug_window = new gDebugWindow(this, false);
	if (animation_window == null)
	    animation_window = new gAnimationWindow(this, false);
	if (help_window == null)
	    help_window = new gHelpWindow(this, false);

	setMenuBar(createMenuBar(this));

	{
	    jPrologServices prolog = getPrologServices();

	    prolog.addStateChangedListener(new jPrologServiceListener() {
		public void handleEvent(jPrologServiceEvent e) {
		    updateToolMenuDebug();
		}
	    });
	    prolog.addBeginQueryListener(new jPrologServiceListener() {
		public void handleEvent(jPrologServiceEvent e) {
		    query_state = true;
		    updateToolMenuDebug();
		    updateToolMenuQuery();
		    updateToolMenuReset(false);
		    updateToolMenuConsultAll(false);
		}
	    });
	    prolog.addEndQueryListener(new jPrologServiceListener() {
		public void handleEvent(jPrologServiceEvent e) {
		    query_state = false;
		    updateToolMenuDebug();
		    updateToolMenuQuery();
		    updateToolMenuReset(true);
		    updateToolMenuConsultAll(true);
		}
	    });
	    prolog.addThreadStoppedListener(new jPrologServiceListener() {
		public void handleEvent(jPrologServiceEvent e) {
		    query_state = false;
		    updateToolMenuDebug();
		    updateToolMenuQuery();
		    updateToolMenuReset(true);
		    updateToolMenuConsultAll(true);
		}
	    });
	    prolog.addBeginConsultListener(new jPrologServiceListener() {
		public void handleEvent(jPrologServiceEvent e) {
		    updateToolMenuReset(false);
		    updateToolMenuConsultAll(false);
		}
	    });
	    prolog.addEndConsultListener(new jPrologServiceListener() {
		public void handleEvent(jPrologServiceEvent e) {
		    updateToolMenuReset(true);
		    updateToolMenuConsultAll(true);
		    consultNext();
		}
	    });
	}

	{
	    Point loc = getLocation();

	    pack();
	    setSize(640, 480);
	    loc.move(30, 30);
	    setLocation(loc);
	}

	addWindowListener(new WindowAdapter() {
	    public void windowClosing(WindowEvent evt) {
		close();
	    }
	});

	setTitle("JLog - Query");
	setVisible(true);
    };

    public MenuBar createMenuBar(iMenuServiceRequester s) {
	MenuBar menubar;
	MenuItem mi;

	menubar = new MenuBar();

	// JLog
	if (!useBuiltinApplicationMenu) {
	    Menu jlog_menu = menubar.add(new Menu("JLog"));

	    mi = jlog_menu.add(new MenuItem("About JLog..."));
	    mi.addActionListener(new ActionListener() {
		public void actionPerformed(ActionEvent e) {
		    showAboutBox();
		}
	    });

	    jlog_menu.addSeparator();

	    mi = jlog_menu
		    .add(new MenuItem("Quit JLog", new MenuShortcut('Q')));
	    mi.addActionListener(new ActionListener() {
		public void actionPerformed(ActionEvent e) {
		    quit();
		}
	    });
	}
	// File
	{
	    Menu file_menu = menubar.add(new Menu("File"));

	    mi = file_menu.add(new MenuItem("New KB", new MenuShortcut('N')));
	    mi.addActionListener(new ActionListener() {
		public void actionPerformed(ActionEvent e) {
		    new gConsultWindow(gJLogApplication.this, null);
		}
	    });

	    mi = file_menu
		    .add(new MenuItem("Open KB...", new MenuShortcut('O')));
	    mi.addActionListener(new ActionListener() {
		public void actionPerformed(ActionEvent e) {
		    open(null);
		}
	    });

	    file_menu.addSeparator();

	    mi = file_menu.add(new MenuItem("Close", new MenuShortcut('W')));
	    mi.setActionCommand(MENU_CLOSE);
	    if (!s.useMenuItem(mi))
		mi.setEnabled(false);

	    mi = file_menu.add(new MenuItem("Save", new MenuShortcut('S')));
	    mi.setActionCommand(MENU_SAVE);
	    if (!s.useMenuItem(mi))
		mi.setEnabled(false);

	    mi = file_menu.add(new MenuItem("Save As...", new MenuShortcut('S',
		    true)));
	    mi.setActionCommand(MENU_SAVEAS);
	    if (!s.useMenuItem(mi))
		mi.setEnabled(false);
	}
	// Edit
	{
	    Menu edit_menu = menubar.add(new Menu("Edit"));

	    mi = edit_menu.add(new MenuItem("Cut", new MenuShortcut('X')));
	    mi.setActionCommand(MENU_CUT);
	    if (!s.useMenuItem(mi))
		mi.setEnabled(false);

	    mi = edit_menu.add(new MenuItem("Copy", new MenuShortcut('C')));
	    mi.setActionCommand(MENU_COPY);
	    if (!s.useMenuItem(mi))
		mi.setEnabled(false);

	    mi = edit_menu.add(new MenuItem("Paste", new MenuShortcut('V')));
	    mi.setActionCommand(MENU_PASTE);
	    if (!s.useMenuItem(mi))
		mi.setEnabled(false);

	    mi = edit_menu
		    .add(new MenuItem("Select All", new MenuShortcut('A')));
	    mi.setActionCommand(MENU_SELECTALL);
	    if (!s.useMenuItem(mi))
		mi.setEnabled(false);

	    edit_menu.addSeparator();

	    mi = edit_menu.add(new MenuItem("Find...", new MenuShortcut('F')));
	    mi.setActionCommand(MENU_FIND);
	    if (!s.useMenuItem(mi))
		mi.setEnabled(false);

	    mi = edit_menu
		    .add(new MenuItem("Find Next", new MenuShortcut('G')));
	    mi.setActionCommand(MENU_FINDNEXT);
	    if (!s.useMenuItem(mi))
		mi.setEnabled(false);

	    edit_menu.addSeparator();

	    mi = edit_menu.add(new MenuItem("Goto Line...", new MenuShortcut(
		    'L')));
	    mi.setActionCommand(MENU_GOTOLINE);
	    if (!s.useMenuItem(mi))
		mi.setEnabled(false);
	}
	// Tools
	{
	    Menu tool_menu = menubar.add(new Menu("Tool"));

	    mi = tool_menu
		    .add(new MenuItem("Consult KB", new MenuShortcut('K')));
	    mi.setActionCommand(MENU_CONSULTKB);
	    if (!s.useMenuItem(mi))
		mi.setEnabled(false);

	    mi = tool_menu.add(new MenuItem("Consult All", new MenuShortcut(
		    'K', true)));
	    mi.setActionCommand(MENU_CONSULTALLKB);
	    mi.setEnabled(getPrologServices().isAvailable());
	    mi.addActionListener(new ActionListener() {
		public void actionPerformed(ActionEvent e) {
		    consultAll();
		}
	    });

	    mi = tool_menu.add(new MenuItem("Reset KB", new MenuShortcut('R')));
	    mi.setActionCommand(MENU_RESETKB);
	    if (!s.useMenuItem(mi)) {
		mi.setEnabled(getPrologServices().isAvailable());
		mi.addActionListener(new ActionListener() {
		    public void actionPerformed(ActionEvent e) {
			resetdb();
		    }
		});
	    }

	    tool_menu.addSeparator();

	    if (query.isPaused())
		mi = tool_menu.add(new MenuItem(MENU_LABEL_PAUSEQ_CONT, null));
	    else
		mi = tool_menu.add(new MenuItem(MENU_LABEL_PAUSEQ_PAUSE, null));

	    mi.setEnabled(query_state);
	    mi.setActionCommand(MENU_PAUSEQ);
	    mi.addActionListener(new ActionListener() {
		public void actionPerformed(ActionEvent e) {
		    query.pause();
		    updateToolMenuQuery();
		}
	    });

	    mi = tool_menu
		    .add(new MenuItem("Stop Query", new MenuShortcut('.')));
	    mi.setEnabled(query_state);
	    mi.setActionCommand(MENU_STOPQ);
	    mi.addActionListener(new ActionListener() {
		public void actionPerformed(ActionEvent e) {
		    query.stop();
		    updateToolMenuQuery();
		}
	    });

	    tool_menu.addSeparator();

	    mi = tool_menu.add(new CheckboxMenuItem("Debugger On", false));
	    ((CheckboxMenuItem) mi)
		    .setState(getPrologServices().getDebugging());
	    mi.setEnabled(!query_state);
	    mi.setActionCommand(MENU_DEBUG);
	    ((CheckboxMenuItem) mi).addItemListener(new ItemListener() {
		public void itemStateChanged(ItemEvent e) {
		    boolean dbg = getPrologServices().getDebugging();

		    getPrologServices().setDebugging(!dbg);
		    
		    if(!dbg)
		    {
			showDebugWindow();
		    }
		    
		}
	    });
	    // mi.addActionListener(new ActionListener()
	    // {
	    // public void actionPerformed (ActionEvent e)
	    // {boolean dbg = getPrologServices().getDebugging();
	    //
	    // getPrologServices().setDebugging(!dbg);
	    // // AWT insists on swapping the states, so we must reverse them.
	    // ((CheckboxMenuItem) e.getSource()).setState(!dbg);
	    // }
	    // }
	    // );

	    mi = tool_menu.add(new CheckboxMenuItem(
		    "Abort on Unknown Predicates", false));
	    ((CheckboxMenuItem) mi).setState(!getPrologServices()
		    .getFailUnknownPredicate());
	    mi.setEnabled(!query_state);
	    mi.setActionCommand(MENU_UNKNOWNPRED);
	    ((CheckboxMenuItem) mi).addItemListener(new ItemListener() {
		public void itemStateChanged(ItemEvent e) {
		    boolean unkp = getPrologServices()
			    .getFailUnknownPredicate();

		    getPrologServices().setFailUnknownPredicate(!unkp);
		}
	    });
	    // mi.addActionListener(new ActionListener()
	    // {
	    // public void actionPerformed (ActionEvent e)
	    // {boolean unkp = getPrologServices().getFailUnknownPredicate();
	    //
	    // getPrologServices().setFailUnknownPredicate(!unkp);
	    // // AWT insists on swapping the states, so we must reverse them.
	    // ((CheckboxMenuItem) e.getSource()).setState(!unkp);
	    // }
	    // }
	    // );

	    mi = tool_menu.add(new CheckboxMenuItem("Trace All", false));
	    ((CheckboxMenuItem) mi).setState(false);
	    mi.setEnabled(getPrologServices().getDebugging());
	    mi.setActionCommand(MENU_TRACE);
	    ((CheckboxMenuItem) mi).addItemListener(new ItemListener() {
		public void itemStateChanged(ItemEvent e) {
		    boolean ta = debug_window.getTraceState();

		    debug_window.setTraceState(!ta);
		    updateToolMenuDebug();
		}
	    });
	    // mi.addActionListener(new ActionListener()
	    // {
	    // public void actionPerformed (ActionEvent e)
	    // {boolean ta = debug_window.getTraceState();
	    //
	    // debug_window.setTraceState(!ta);
	    // updateToolMenuDebug();
	    // // AWT insists on swapping the states, so we must reverse them.
	    // ((CheckboxMenuItem) e.getSource()).setState(ta);
	    // }
	    // }
	    // );

	    mi = tool_menu.add(new CheckboxMenuItem("Break on Failure", false));
	    ((CheckboxMenuItem) mi).setState(false);
	    mi.setEnabled(getPrologServices().getDebugging());
	    mi.setActionCommand(MENU_BREAK);
	    ((CheckboxMenuItem) mi).addItemListener(new ItemListener() {
		public void itemStateChanged(ItemEvent e) {
		    boolean ba = debug_window.getBreakState();

		    debug_window.setBreakState(!ba);
		    updateToolMenuDebug();
		}
	    });
	    // mi.addActionListener(new ActionListener()
	    // {
	    // public void actionPerformed (ActionEvent e)
	    // {boolean ba = debug_window.getBreakState();
	    //
	    // debug_window.setBreakState(!ba);
	    // updateToolMenuDebug();
	    // // AWT insists on swapping the states, so we must reverse them.
	    // ((CheckboxMenuItem) e.getSource()).setState(ba);
	    // }
	    // }
	    // );
	}
	// Window
	{
	    Menu win_menu = menubar.add(new Menu("Window"));

	    mi = win_menu.add(new MenuItem("Close", new MenuShortcut('W')));
	    mi.setActionCommand(MENU_CLOSE);
	    if (!s.useMenuItem(mi))
		mi.setEnabled(false);

	    win_menu.addSeparator();

	    mi = win_menu.add(new MenuItem("Query", null));
	    mi.addActionListener(new ActionListener() {
		public void actionPerformed(ActionEvent e) {
		    showQueryWindow();
		}
	    });

	    mi = win_menu.add(new MenuItem("Debug", null));
	    mi.addActionListener(new ActionListener() {
		public void actionPerformed(ActionEvent e) {
		    showDebugWindow();
		}
	    });

	    mi = win_menu.add(new MenuItem("Animation", null));
	    mi.addActionListener(new ActionListener() {
		public void actionPerformed(ActionEvent e) {
		    showAnimationWindow();
		}
	    });

	    win_menu.addSeparator();
	}
	// Help
	{
	    Menu help_menu = new Menu("Help");

	    menubar.setHelpMenu(help_menu);

	    mi = help_menu
		    .add(new MenuItem("JLog Help", new MenuShortcut('?')));
	    mi.addActionListener(new ActionListener() {
		public void actionPerformed(ActionEvent e) {
		    showHelpWindow();
		}
	    });
	}
	return menubar;
    };

    public boolean useMenuItem(MenuItem mi) {
	if (mi.getActionCommand() == MENU_CLOSE) {
	    mi.addActionListener(new ActionListener() {
		public void actionPerformed(ActionEvent e) {
		    close();
		}
	    });
	    return true;
	}
	return false;
    };

    protected static void showAboutBox() {
	if (about_box != null)
	    about_box.setVisible(true);
    };

    protected void showQueryWindow() {
	if (query_window != null)
	    query_window.setVisible(true);
    };

    protected void showDebugWindow() {
	if (debug_window != null)
	    debug_window.setVisible(true);
    };

    protected void showAnimationWindow() {
	if (animation_window != null)
	    animation_window.setVisible(true);
    };

    protected void showHelpWindow() {
	if (help_window != null)
	    help_window.setVisible(true);
    };

    public void updateWindowMenu() {
	super.updateWindowMenu();

	updateWindowMenu(query_window.getMenuBar());
	updateWindowMenu(debug_window.getMenuBar());
	updateWindowMenu(animation_window.getMenuBar());
	updateWindowMenu(help_window.getMenuBar());
    };

    protected void updateToolMenuDebug() {
	boolean dbg = getPrologServices().getDebugging();
	boolean unkp = getPrologServices().getFailUnknownPredicate();
	boolean ta = debug_window.getTraceState();
	boolean ba = debug_window.getBreakState();

	updateToolMenuDebug(dbg, !unkp, ta, ba, query_state);
    };

    public void updateToolMenuDebug(boolean dbg, boolean unkpred, boolean trce,
	    boolean brk, boolean in_query) {
	super.updateToolMenuDebug(dbg, unkpred, trce, brk, in_query);

	updateToolMenuDebug(query_window.getMenuBar(), dbg, unkpred, trce, brk,
		in_query);
	updateToolMenuDebug(debug_window.getMenuBar(), dbg, unkpred, trce, brk,
		in_query);
	updateToolMenuDebug(animation_window.getMenuBar(), dbg, unkpred, trce,
		brk, in_query);
	updateToolMenuDebug(help_window.getMenuBar(), dbg, unkpred, trce, brk,
		in_query);
    };

    protected void updateToolMenuQuery() {
	updateToolMenuQuery(query_state, query.isPaused());
    };

    public void updateToolMenuQuery(boolean in_query, boolean paused) {
	super.updateToolMenuQuery(in_query, paused);

	updateToolMenuQuery(query_window.getMenuBar(), in_query, paused);
	updateToolMenuQuery(debug_window.getMenuBar(), in_query, paused);
	updateToolMenuQuery(animation_window.getMenuBar(), in_query, paused);
	updateToolMenuQuery(help_window.getMenuBar(), in_query, paused);
    };

    public void updateToolMenuReset(boolean state) {
	updateToolMenuReset(query_window.getMenuBar(), state);
	updateToolMenuReset(debug_window.getMenuBar(), state);
	updateToolMenuReset(animation_window.getMenuBar(), state);
	updateToolMenuReset(help_window.getMenuBar(), state);
    };

    public void updateToolMenuConsultAll(boolean state) {
	super.updateToolMenuConsultAll(state);

	updateToolMenuConsultAll(query_window.getMenuBar(), state);
	updateToolMenuConsultAll(debug_window.getMenuBar(), state);
	updateToolMenuConsultAll(animation_window.getMenuBar(), state);
	updateToolMenuConsultAll(help_window.getMenuBar(), state);
    };

    protected int getToolMenuID() {
	int id = super.getToolMenuID();

	return (useBuiltinApplicationMenu ? id - 1 : id);
    };

    protected int getWindowMenuID() {
	int id = super.getWindowMenuID();

	return (useBuiltinApplicationMenu ? id - 1 : id);
    };

    protected void open(String fileName) {
	File input;

	if (fileName == null) {
	    FileDialog fd;

	    fd = new FileDialog(this, "Open File", FileDialog.LOAD);

	    if (getCurrentDirectory() == null)
		fd.setDirectory(System.getProperty("user.dir"));
	    else
		fd.setDirectory(getCurrentDirectory());

	    fd.setVisible(true);

	    if (fd.getFile() == null) {
		return;
	    }

	    setCurrentDirectory(fd.getDirectory());
	    input = new File(fd.getDirectory(), fd.getFile());
	} else {
	    input = new File(fileName);
	}

	{
	    String file_path = input.getAbsolutePath();
	    gConsultWindow cw = getConsultWindow(file_path);

	    if (cw == null) {
		cw = new gConsultWindow(this, null);
		cw.open(input);
	    } else
		cw.setVisible(true);
	}
    };

    protected boolean close() {
	if (query_window != null)
	    query_window.setVisible(false);

	return true;
    };

    protected static boolean closeAll() {
	Vector kb = (Vector) kb_windows.clone();
	Enumeration e = kb.elements();

	while (e.hasMoreElements()) {
	    gConsultWindow cw = ((gConsultWindow) e.nextElement());

	    if (!cw.close())
		return false;
	}

	return true;
    };

    protected static boolean quit() {
	if (closeAll()) {
	    System.exit(0);
	    return true;
	} else
	    return false;
    };

    protected gConsultWindow getConsultWindow(String fp) {
	Enumeration e = kb_windows.elements();

	while (e.hasMoreElements()) {
	    gConsultWindow cw = ((gConsultWindow) e.nextElement());
	    String fn = cw.getFilePath();

	    if (fn != null && fn.equals(fp))
		return cw;
	}

	return null;
    };

    protected static void consultAll() {
	consult_enum = ((Vector) kb_windows.clone()).elements();
	consultNext();
    };

    protected static void consultNext() {
	if (consult_enum == null)
	    return;

	if (consult_enum.hasMoreElements()) {
	    gConsultWindow cw = ((gConsultWindow) consult_enum.nextElement());

	    cw.consult();
	} else
	    consult_enum = null;
    };

    public static void resetdb() {
	jPrologServices prolog = base.getPrologServices();
	PrintWriter errors = query.getOutputStream();

	if (!prolog.start(new jResetDatabaseThread(prolog, errors))) {
	    errors.println("reset failed. other events pending.");
	    errors.flush();
	}
    };

    protected class eawtApplicationAdapter implements InvocationHandler {
	protected gJLogApplication application = null;
	protected Method SetHandledMethod = null;

	public eawtApplicationAdapter(gJLogApplication app) throws Throwable {
	    Class AppEventClass = Class
		    .forName("com.apple.eawt.ApplicationEvent");

	    SetHandledMethod = AppEventClass.getMethod("setHandled",
		    new Class[] { boolean.class });
	    application = app;
	}

	public Object invoke(Object proxy, Method method, Object[] args)
		throws Throwable {
	    String methodName = method.getName();

	    if (methodName.equals("handleAbout")) {
		handleAbout(args[0]);
	    } else if (methodName.equals("handleOpenApplication")) {
		handleOpenApplication(args[0]);
	    } else if (methodName.equals("handleOpenFile")) {
		handleOpenFile(args[0]);
	    } else if (methodName.equals("handlePreferences")) {
		handlePreferences(args[0]);
	    } else if (methodName.equals("handlePrintFile")) {
		handlePrintFile(args[0]);
	    } else if (methodName.equals("handleQuit")) {
		handleQuit(args[0]);
	    }
	    return null;
	}

	public void handleAbout(Object e) throws Throwable {
	    application.showAboutBox();
	    SetHandledMethod.invoke(e, new Object[] { Boolean.TRUE });
	}

	public void handleOpenApplication(Object e) throws Throwable {
	}

	public void handleOpenFile(Object e) throws Throwable {
	}

	public void handlePreferences(Object e) throws Throwable {
	}

	public void handlePrintFile(Object e) throws Throwable {
	}

	public void handleQuit(Object e) throws Throwable {
	    boolean result;

	    result = application.quit();

	    SetHandledMethod.invoke(e, new Object[] { (result ? Boolean.TRUE
		    : Boolean.FALSE) });
	}
    };

};
