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
import org.mathpiper.io.StringInputStream;
import org.mathpiper.lisp.Environment;
import org.mathpiper.lisp.LispError;
import org.mathpiper.lisp.Utility;
import org.mathpiper.lisp.cons.AtomCons;
import org.mathpiper.lisp.cons.BuiltinObjectCons;
import org.mathpiper.lisp.cons.Cons;
import org.mathpiper.lisp.cons.SublistCons;
import org.mathpiper.lisp.parsers.MathPiperParser;
import org.mathpiper.lisp.parsers.Parser;
import org.mathpiper.lisp.parsers.LispParser;
import org.mathpiper.lisp.tokenizers.MathPiperTokenizer;
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
    // Show(TreeView( "a*(b+c) == a*b + a*c",  Resizable: True, IncludeExpression: True))
    // Show(TreeView( "(+ 1 2)", Prefix: True, Code: True, Resizable: True, IncludeExpression: True))
    // MetaSet(Car(Cdr(Car('(a+(b+c) == d)))),"op",True)
    // Show(StepsView(SolveEquation( '( ((- a^2) * b )/ c + d == e ), a), ShowTree: True))

    private Map defaultOptions;
    
    public void plugIn(Environment aEnvironment)  throws Throwable
    {
	this.functionName = "TreeView";
	
        aEnvironment.getBuiltinFunctions().put(
                "TreeView", new BuiltinFunctionEvaluator(this, 1, BuiltinFunctionEvaluator.VariableNumberOfArguments | BuiltinFunctionEvaluator.EvaluateArguments));

        defaultOptions = new HashMap();

        defaultOptions.put("Scale", 2.5);
        defaultOptions.put("Resizable", true);
        defaultOptions.put("IncludeExpression", true);
        defaultOptions.put("Lisp", false);
        defaultOptions.put("Code", false);




    }//end method.

    public void evaluate(Environment aEnvironment, int aStackTop) throws Throwable {
	

        Cons arguments = getArgument(aEnvironment, aStackTop, 1);
        
        Cons options = (Cons) Cons.cddar(arguments);

        Map userOptions = Utility.optionsListToJavaMap(aEnvironment, aStackTop, options, defaultOptions);
        
        

        Object argument = ((Cons)getArgument(aEnvironment, aStackTop, 1).car()).cdr().car();
        
        String texString = "";
        
        Cons expression = null;

        if ((argument instanceof String)) {
           
            
            String expressionString = (String) argument;

            expressionString = Utility.stripEndQuotesIfPresent(expressionString);
            
            Parser parser;
            
            if(((Boolean)userOptions.get("Lisp")) == true)
            {
        	texString = expressionString;
        	texString = texString.replace(" ", "\\ ");
            
                StringInputStream newInput = new StringInputStream(expressionString , aEnvironment.iInputStatus);

        	parser = new LispParser(aEnvironment.iCurrentTokenizer, newInput, aEnvironment);
            }
            else
            {
        	texString = expressionString;
        	
        	if (!expressionString.endsWith(";")) {
                    expressionString = expressionString + ";";
                }
                expressionString = expressionString.replaceAll(";;;", ";");
                expressionString = expressionString.replaceAll(";;", ";");
            
                StringInputStream newInput = new StringInputStream(expressionString , aEnvironment.iInputStatus);

        	parser = new MathPiperParser(new MathPiperTokenizer(), newInput, aEnvironment, aEnvironment.iPrefixOperators, aEnvironment.iInfixOperators, aEnvironment.iPostfixOperators, aEnvironment.iBodiedOperators);
            }
            
            expression = parser.parse(aStackTop);
           
        }
        else
        {

            if(! Utility.isSublist(arguments)) LispError.throwError(aEnvironment, aStackTop, LispError.INVALID_ARGUMENT, "ToDo");
    
            arguments = (Cons) arguments.car(); //Go to sub list.
    
            arguments = arguments.cdr(); //Strip List tag.
            
            expression = (Cons) arguments;
            //expression = (Cons) argument;
            
        }
        
        
        
        if(!((Boolean) userOptions.get("Lisp")))
        {
            if(!((Boolean) userOptions.get("Code")))
            {
                //Evaluate Hold function.
                Cons holdAtomCons = AtomCons.getInstance(aEnvironment, aStackTop, "Hold");
                holdAtomCons.setCdr(Cons.deepCopy(aEnvironment, aStackTop, expression));
                Cons holdSubListCons = SublistCons.getInstance(aEnvironment, holdAtomCons);
                Cons holdInputExpression = holdSubListCons;        
                
        	//Obtain LaTeX version of the expression.
        	Cons head = SublistCons.getInstance(aEnvironment, AtomCons.getInstance(aEnvironment, aStackTop, "UnparseLatex"));
                ((Cons) head.car()).setCdr(holdInputExpression);
                Cons result = aEnvironment.iLispExpressionEvaluator.evaluate(aEnvironment, aStackTop, head);
                texString = (String) result.car();
                texString = Utility.stripEndQuotesIfPresent(texString);
                texString = texString.substring(1, texString.length());
                texString = texString.substring(0, texString.length() - 1);
            }
            else
            {
                texString = Utility.printMathPiperExpression(aStackTop, expression, aEnvironment, -1); 
            }
        }




	TeXFormula formula = new TeXFormula(texString);
	JLabel latexLabel = new JLabel();
        JPanel latexPanelController = new LatexRenderingController(formula, latexLabel, 40);
        JPanel latexScreenCapturePanel = new ScreenCapturePanel();
        latexScreenCapturePanel.add(latexLabel);        
        

        
        double viewScale = ((Double)userOptions.get("Scale")).doubleValue();
        
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());
        

        
	panel.setBackground(Color.white);
	//box.setOpaque(true);


        TreePanelCons treePanel = new TreePanelCons(expression, viewScale, (Boolean)userOptions.get("Code"));
        
        JPanel treeScreenCapturePanel = new ScreenCapturePanel();
        
        treeScreenCapturePanel.add(treePanel);
        
        //JPanel screenCapturePanel = new ScreenCapturePanel();   
        //screenCapturePanel.add(treePanel);
	

	boolean includeSlider = (Boolean) userOptions.get("Resizable");
	boolean includeExpression = (Boolean) userOptions.get("IncludeExpression");

	
	if(includeSlider && includeExpression)
	{
	    MathPanelController treePanelScaler = new MathPanelController(treePanel, viewScale);
	    
	    JScrollPane treeScrollPane = new JScrollPane(treeScreenCapturePanel, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
	    treeScrollPane.getVerticalScrollBar().setUnitIncrement(16);
	    
            panel.add(latexScreenCapturePanel, BorderLayout.NORTH);
            panel.add(treeScrollPane, BorderLayout.CENTER);
            panel.add(treePanelScaler, BorderLayout.SOUTH);

	}
	else if(includeSlider)
	{
	    MathPanelController treePanelScaler = new MathPanelController(treePanel, viewScale);
	    
	    JScrollPane treeScrollPane = new JScrollPane(treeScreenCapturePanel, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
	    treeScrollPane.getVerticalScrollBar().setUnitIncrement(16);
	    
            panel.add(treeScrollPane, BorderLayout.CENTER);
            panel.add(treePanelScaler, BorderLayout.SOUTH);
	}
	else if(includeExpression)
	{
	    panel.add(latexScreenCapturePanel, BorderLayout.NORTH);
            panel.add(treeScreenCapturePanel, BorderLayout.CENTER);
	}
	else
	{
	    panel.add(treeScreenCapturePanel, BorderLayout.CENTER);
	}
 

        JavaObject response = new JavaObject(panel);

        setTopOfStack(aEnvironment, aStackTop, BuiltinObjectCons.getInstance(aEnvironment, aStackTop, response));


    }//end method.
    

}//end class.





/*
%mathpiper_docs,name="TreeView",categories="Mathematics Functions;Visualization"
*CMD TreeView --- display an expression tree

*CALL
    TreeView(expression, option, option, option...)

*PARMS
{expression} -- an expression (which may be in string form) to display as an expression tree

{Options:}

{Scale} -- a value that sets the initial size of the tree

{Resizable} -- if set to True, a resizing slider is displayed

{IncludeExpression} -- if set to True, the algebraic form of the expression is included above the tree

{Lisp} -- if set to True, the expression must be a string that is in Lisp form

{Code} -- if set to True, the expression is rendered using code symbols instead of mathematical symbols


*DESC
Returns a Java GUI component that contains an expression rendered as an
expression tree.

Options are entered using the : operator.
For example, here is how to disable {Resizable} option: {Resizable: False}.

Right click on the images that are displayed to save them.
 
*E.G.

In> Show(TreeView( '(a*(b+c) == a*b + a*c)))
Result: java.awt.Component

In> Show(TreeView( "a*(b+c) == a*b + a*c"))
Result: java.awt.Component

In> Show(TreeView( "(+ 1 (* 2 3))", Lisp: True))
Result: java.awt.Component


*SEE Show
%/mathpiper_docs
*/



