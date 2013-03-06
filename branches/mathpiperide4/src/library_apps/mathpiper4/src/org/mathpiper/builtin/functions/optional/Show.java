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
// :indentSize=4:lineSeparator=\n:noTabs=false:tabSize=4:folding=explicit:collapseFolds=0:
package org.mathpiper.builtin.functions.optional;


import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.MenuBar;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Map;

import javax.swing.Box;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JScrollPane;

import org.mathpiper.builtin.BuiltinFunction;
import org.mathpiper.builtin.BuiltinFunctionEvaluator;
import org.mathpiper.builtin.JavaObject;
import org.mathpiper.lisp.Environment;
import org.mathpiper.lisp.LispError;
import org.mathpiper.lisp.Utility;

import org.mathpiper.lisp.cons.BuiltinObjectCons;
import org.mathpiper.lisp.cons.Cons;



public class Show extends BuiltinFunction {

    private Map defaultOptions;


    public void plugIn(Environment aEnvironment)  throws Throwable
    {
	this.functionName = "Show";
	
        aEnvironment.getBuiltinFunctions().put(
                "Show", new BuiltinFunctionEvaluator(this, 1, BuiltinFunctionEvaluator.Variable | BuiltinFunctionEvaluator.Function));

        defaultOptions = new HashMap();
        defaultOptions.put("Title", null);


    }//end method.



    public void evaluate(Environment aEnvironment, int aStackTop) throws Throwable {

        Cons arguments = getArgument(aEnvironment, aStackTop, 1);

        if(! Utility.isSublist(arguments)) LispError.throwError(aEnvironment, aStackTop, LispError.INVALID_ARGUMENT, "ToDo");

        arguments = (Cons) arguments.car(); //Go to sub list.

        arguments = arguments.cdr(); //Strip List tag.
        
        Cons dataList = null;

        if(Utility.isSublist(arguments))
        {
            dataList = (Cons) arguments.car(); //Grab the first member of the list arguments list.
            
            dataList = dataList.cdr(); //Strip List tag.
        }
        else
        {
            dataList = arguments.copy(false);
        }



        Cons options = arguments.cdr();

        Map userOptions = Utility.optionsListToJavaMap(aEnvironment, aStackTop, options, defaultOptions);

        final Box box = Box.createVerticalBox();
        
        
        JFrame frame = new JFrame();
        Container contentPane = frame.getContentPane();
        frame.setBackground(Color.WHITE);
        contentPane.setBackground(Color.WHITE);

        frame.setAlwaysOnTop(false);
        frame.setTitle((String) userOptions.get("title"));
        frame.setResizable(true);
        frame.pack();
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int height = screenSize.height;
        int width = screenSize.width;
        frame.setSize(width/2, height/2);
        frame.setLocationRelativeTo(null);        
        
        while(dataList != null)
        {
            Object object = dataList.car();
            
            if(! (object instanceof JavaObject)) LispError.throwError(aEnvironment, aStackTop, LispError.INVALID_ARGUMENT, "ToDo");
            
            JavaObject javaObject = (JavaObject) object;
            
            object = javaObject.getObject();
            
            if(! (object instanceof Component)) LispError.throwError(aEnvironment, aStackTop, LispError.INVALID_ARGUMENT, "ToDo");
            
            Component component = (Component) object;
            
            box.add(component);
            
            dataList = dataList.cdr();
        }
        
        JScrollPane scrollPane = new JScrollPane(box,JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);
        
        contentPane.add(scrollPane);
        
        
        JMenu fileMenu = new JMenu("File");
        
	JMenuItem saveAsImageAction = new JMenuItem();
	saveAsImageAction.setText("Save As Image");
	saveAsImageAction.addActionListener(new ActionListener() {
	    public void actionPerformed(ActionEvent ae) {
		org.mathpiper.ui.gui.Utility.saveImageOfComponent(box);
	    }
	    });
	fileMenu.add(saveAsImageAction);
	
	JMenuBar menuBar = new JMenuBar();
	
	menuBar.add(fileMenu);
	
	frame.setJMenuBar(menuBar);

        frame.setVisible(true);
        

        

        JavaObject response = new JavaObject(frame);

        setTopOfStack(aEnvironment, aStackTop, BuiltinObjectCons.getInstance(aEnvironment, aStackTop, response));


    }//end method.
    
}//end class.







/*
%mathpiper_docs,name="Show",categories="Mathematics Functions;Visualization"
*CMD Show --- displays GUI components
*CORE
*CALL
	Show(component, option, option, option...)

*PARMS

{component} -- a Java GUI component

{Options:}

{Title} -- the title of the window


*DESC

Displays a GUI window that contains a Java GUI component. These GUI components 
are usually returned from XXXView functions and charting functions.



*E.G.
In> Show(TreeView( '(a*(b+c) == a*b + a*c)))
Result: class javax.swing.JFrame

*SEE TreeView, LatexView

%/mathpiper_docs
*/

