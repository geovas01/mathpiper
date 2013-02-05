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

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import org.mathpiper.builtin.BuiltinFunction;
import org.mathpiper.builtin.BuiltinFunctionEvaluator;
import org.mathpiper.builtin.JavaObject;
import org.mathpiper.lisp.Environment;
import org.mathpiper.lisp.LispError;
import org.mathpiper.lisp.Utility;
import org.mathpiper.lisp.cons.AtomCons;
import org.mathpiper.lisp.cons.BuiltinObjectCons;
import org.mathpiper.lisp.cons.Cons;
import org.mathpiper.lisp.cons.SublistCons;
import org.mathpiper.ui.gui.worksheets.LatexRenderingController;
import org.mathpiper.ui.gui.worksheets.MathPanelController;
import org.mathpiper.ui.gui.worksheets.ScreenCapturePanel;
import org.mathpiper.ui.gui.worksheets.TreePanelCons;
import org.scilab.forge.jlatexmath.TeXFormula;

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

        defaultOptions.put("Scale", 2.5);
        defaultOptions.put("Resizable", false);
        defaultOptions.put("IncludeExpression", false);




    }//end method.

    public void evaluate(Environment aEnvironment, int aStackTop) throws Throwable {
	

	
	
	
	
	
        Cons arguments = getArgument(aEnvironment, aStackTop, 1);

        if(! Utility.isSublist(arguments)) LispError.throwError(aEnvironment, aStackTop, LispError.INVALID_ARGUMENT, "ToDo");

        arguments = (Cons) arguments.car(); //Go to sub list.

        arguments = arguments.cdr(); //Strip List tag.


        Cons expression = (Cons) arguments;
        
        //if(! (latexStringObject instanceof String)) LispError.throwError(aEnvironment, aStackTop, LispError.INVALID_ARGUMENT, "ToDo");

        //Evaluate Hold function.
        Cons holdAtomCons = AtomCons.getInstance(aEnvironment, aStackTop, "Hold");
        holdAtomCons.setCdr(Cons.deepCopy(aEnvironment, aStackTop, expression));
        Cons holdSubListCons = SublistCons.getInstance(aEnvironment, holdAtomCons);
        Cons holdInputExpression = holdSubListCons;        
        
	//Obtain LaTeX version of the expression.
	Cons head = SublistCons.getInstance(aEnvironment, AtomCons.getInstance(aEnvironment, aStackTop, "TeXForm"));
        ((Cons) head.car()).setCdr(holdInputExpression);
        Cons result = aEnvironment.iLispExpressionEvaluator.evaluate(aEnvironment, aStackTop, head);
        String texString = (String) result.car();
        texString = Utility.stripEndQuotesIfPresent(aEnvironment, aStackTop, texString);
        texString = texString.substring(1, texString.length());
        texString = texString.substring(0, texString.length() - 1);

	TeXFormula formula = new TeXFormula(texString);
	JLabel latexLabel = new JLabel();
        JPanel latexPanelController = new LatexRenderingController(formula, latexLabel, 40);
        JPanel latexScreenCapturePanel = new ScreenCapturePanel();
        latexScreenCapturePanel.add(latexLabel);        
        
        

        
        
        Cons options = arguments.cdr();

        Map userOptions = Utility.optionsListToJavaMap(aEnvironment, aStackTop, options, defaultOptions);
        


        
        int viewScale = (int) ((Double)userOptions.get("Scale")).doubleValue();
        
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());
        

        
	panel.setBackground(Color.white);
	//box.setOpaque(true);


        TreePanelCons treePanel = new TreePanelCons(expression, viewScale);
        
        JPanel screenCapturePanel = new ScreenCapturePanel();
        
        screenCapturePanel.add(treePanel);
        
        //JPanel screenCapturePanel = new ScreenCapturePanel();   
        //screenCapturePanel.add(treePanel);
	

	boolean includeSlider = (Boolean) userOptions.get("Resizable");
	boolean includeExpression = (Boolean) userOptions.get("IncludeExpression");

	
	if(includeSlider && includeExpression)
	{
	    MathPanelController treePanelScaler = new MathPanelController(treePanel, viewScale);
	    
	    JScrollPane treeScrollPane = new JScrollPane(screenCapturePanel, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);

            panel.add(latexScreenCapturePanel, BorderLayout.NORTH);
            panel.add(treeScrollPane, BorderLayout.CENTER);
            panel.add(treePanelScaler, BorderLayout.SOUTH);

	}
	else if (includeExpression)
	{
	    panel.add(latexScreenCapturePanel, BorderLayout.NORTH);
	    JPanel jPanel = new JPanel();
	    jPanel.setOpaque(true);
	    jPanel.setBackground(Color.white);
	    jPanel.add(treePanel);
	    panel.add(jPanel, BorderLayout.CENTER);
	}
	else
	{
	    JPanel jPanel = new JPanel();
	    jPanel.setOpaque(true);
	    jPanel.setBackground(Color.white);
	    jPanel.add(treePanel);
	    panel.add(jPanel, BorderLayout.CENTER);
	}
 

        JavaObject response = new JavaObject(panel);

        setTopOfStack(aEnvironment, aStackTop, BuiltinObjectCons.getInstance(aEnvironment, aStackTop, response));


    }//end method.


}//end class.





/*
%mathpiper_docs,name="TreeView",categories="User Functions;Visualization"
*CMD TreeView --- display an expression tree

*CALL
    TreeView(expression, option, option, option...)

*PARMS
{expression} -- an expression to display as an expression tree

{Options:}

{Scale} -- a value that sets the initial size of the tree

{Resizable} -- if set to True, a resizing slider is displayed

{IncludeExpression} -- if set to True, the algebraic form of the expression is included above the tree


*DESC
Returns a Java GUI component that contains an expression rendered as an
expression tree.

Options are entered using the -> operator.
For example, here is how to set the {Resizable} option: {Resizable -> True}.

Right click on the image to save it.
 
*E.G.

In> Show(TreeView( '(a*(b+c) == a*b + a*c), Resizable -> True, IncludeExpression -> False))

Result: java.awt.Component



*SEE Show
%/mathpiper_docs
*/



