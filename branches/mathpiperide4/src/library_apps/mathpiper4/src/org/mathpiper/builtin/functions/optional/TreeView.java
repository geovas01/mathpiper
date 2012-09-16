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
import java.util.HashMap;
import java.util.Map;

import javax.swing.Box;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import org.mathpiper.builtin.BuiltinFunction;
import org.mathpiper.builtin.BuiltinFunctionEvaluator;
import org.mathpiper.builtin.JavaObject;
import org.mathpiper.lisp.Environment;
import org.mathpiper.lisp.LispError;
import org.mathpiper.lisp.Utility;
import org.mathpiper.lisp.cons.BuiltinObjectCons;
import org.mathpiper.lisp.cons.Cons;
import org.mathpiper.ui.gui.worksheets.MathPanelController;
import org.mathpiper.ui.gui.worksheets.ScreenCapturePanel;
import org.mathpiper.ui.gui.worksheets.TreePanelCons;

/**
 *
 *
 */
public class TreeView extends BuiltinFunction {

    private Map defaultOptions;
    
    public void plugIn(Environment aEnvironment)  throws Throwable
    {
	this.functionName = "TreeView";
	
        aEnvironment.getBuiltinFunctions().put(
                "TreeView", new BuiltinFunctionEvaluator(this, 1, BuiltinFunctionEvaluator.Variable | BuiltinFunctionEvaluator.Function));

        defaultOptions = new HashMap();
        defaultOptions.put("scale", 3.0);
        defaultOptions.put("slider", false);



    }//end method.

    public void evaluate(Environment aEnvironment, int aStackTop) throws Throwable {
	
        Cons arguments = getArgument(aEnvironment, aStackTop, 1);

        if(! Utility.isSublist(arguments)) LispError.throwError(aEnvironment, aStackTop, LispError.INVALID_ARGUMENT, "ToDo");

        arguments = (Cons) arguments.car(); //Go to sub list.

        arguments = arguments.cdr(); //Strip List tag.


        Cons expression = (Cons) arguments;
        
        //if(! (latexStringObject instanceof String)) LispError.throwError(aEnvironment, aStackTop, LispError.INVALID_ARGUMENT, "ToDo");


        
        
        Cons options = arguments.cdr();

        Map userOptions = Utility.optionsListToJavaMap(aEnvironment, aStackTop, options, defaultOptions);
        


        
        int viewScale = (int) ((Double)userOptions.get("scale")).doubleValue();
        

        Box box = Box.createVerticalBox();
        
	box.setBackground(Color.white);
	//box.setOpaque(true);


        TreePanelCons treePanel = new TreePanelCons(expression, viewScale);
        
        //JPanel screenCapturePanel = new ScreenCapturePanel();   
        //screenCapturePanel.add(treePanel);
	
	boolean includeSlider = (Boolean) userOptions.get("slider");
	
	if(includeSlider)
	{
	    MathPanelController treePanelScaler = new MathPanelController(treePanel, viewScale);
	    JScrollPane treeScrollPane = new JScrollPane(treePanel, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);

            box.add(treeScrollPane);
            box.add(treePanelScaler);
	}
	else
	{
	    JPanel jPanel = new JPanel();
	    jPanel.setOpaque(true);
	    jPanel.setBackground(Color.white);
	    jPanel.add(treePanel);
	    box.add(new JPanel().add(jPanel));
	}
 

        JavaObject response = new JavaObject(box);

        setTopOfStack(aEnvironment, aStackTop, BuiltinObjectCons.getInstance(aEnvironment, aStackTop, response));


    }//end method.


}//end class.





/*
%mathpiper_docs,name="TreeView",categories="User Functions;Visualization"
*CMD TreeView --- display rendered Latex code

*CALL
    TreeView(string)

*Params
{string} -- a string which contains Latex code

*DESC
Display rendered Latex code.  Note: backslashes must be escaped
with a backslash.
 
*E.G.
In> LatexView("2\\sum_{i=1}^n a_i")
Result: javax.swing.JFrame



The XXXView functions all return a reference to the Java Component which they are displayed in.
The Show function can be used to display these components.

In> component := ViewLatex("2\\sum_{i=1}^n a_i")
Result: java.awt.Component



*SEE Show
%/mathpiper_docs
*/



