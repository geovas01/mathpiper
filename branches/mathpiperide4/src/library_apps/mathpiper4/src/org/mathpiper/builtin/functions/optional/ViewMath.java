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
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Toolkit;

import javax.swing.Box;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;

import org.mathpiper.builtin.BigNumber;
import org.mathpiper.builtin.BuiltinFunction;
import org.mathpiper.builtin.BuiltinFunctionEvaluator;
import org.mathpiper.lisp.Environment;
import org.mathpiper.lisp.LispError;
import org.mathpiper.lisp.Utility;
import org.mathpiper.lisp.cons.AtomCons;
import org.mathpiper.lisp.cons.Cons;
import org.mathpiper.lisp.cons.SublistCons;

import javax.swing.JLabel;

import org.mathpiper.builtin.JavaObject;
import org.mathpiper.lisp.cons.BuiltinObjectCons;
import org.mathpiper.ui.gui.worksheets.LatexRenderingController;
import org.mathpiper.ui.gui.worksheets.MathPanel;
import org.mathpiper.ui.gui.worksheets.MathPanelController;
import org.mathpiper.ui.gui.worksheets.ScreenCapturePanel;
import org.mathpiper.ui.gui.worksheets.TreePanel;
import org.mathpiper.ui.gui.worksheets.TreePanelCons;
import org.mathpiper.ui.gui.worksheets.latexparser.TexParser;
import org.mathpiper.ui.gui.worksheets.symbolboxes.SymbolBox;
import org.scilab.forge.jlatexmath.TeXFormula;

/**
 *
 *
 */
public class ViewMath extends BuiltinFunction {

    public void plugIn(Environment aEnvironment)  throws Throwable
    {
        this.functionName = "ViewMathInternal";
        aEnvironment.getBuiltinFunctions().put(
                this.functionName, new BuiltinFunctionEvaluator(this, 2, BuiltinFunctionEvaluator.FixedNumberOfArguments | BuiltinFunctionEvaluator.HoldArguments) );

       String[] parameters = new String[] {"_expression","_size"};
       Utility.declareFunction("ViewMath", parameters, "ViewMathInternal(expression, size);", aEnvironment, LispError.TODO);


       parameters = new String[] {"_expression"};
       Utility.declareFunction("ViewMath", parameters, "ViewMathInternal(expression, 2);", aEnvironment, LispError.TODO);


    }//end method.

    public void evaluate(Environment aEnvironment, int aStackTop) throws Throwable {

        //Utility.lispEvaluate(aEnvironment, "TeXForm(x^2);");

        Cons head = SublistCons.getInstance(aEnvironment, AtomCons.getInstance(aEnvironment, aStackTop, "UnparseLatex"));

        ((Cons) head.car()).setCdr(getArgument(aEnvironment, aStackTop, 1));
        



        Cons viewScaleCons = getArgument(aEnvironment, aStackTop, 2);
        Cons result = aEnvironment.iLispExpressionEvaluator.evaluate(aEnvironment, aStackTop, viewScaleCons);
        BigNumber viewScale = (BigNumber) result.getNumber(aEnvironment.getPrecision(), aEnvironment);
        if(viewScale == null) LispError.checkArgument(aEnvironment, aStackTop, 1);


        
        //Evaluate parameter.
        result = aEnvironment.iLispExpressionEvaluator.evaluate(aEnvironment, aStackTop, head);

        String texString = (String) result.car();
        texString = Utility.stripEndQuotesIfPresent(texString);
        texString = texString.substring(1, texString.length());
        texString = texString.substring(0, texString.length() - 1);
        
        
        
        Cons expression1 = getArgument(aEnvironment, aStackTop, 1);
        Cons expression2 = aEnvironment.iLispExpressionEvaluator.evaluate(aEnvironment, aStackTop, expression1);


        JFrame frame = new JFrame();
        Container contentPane = frame.getContentPane();
        frame.setBackground(Color.WHITE);
        contentPane.setBackground(Color.WHITE);




        /*
        DebugGraphics.setFlashCount(10);
        DebugGraphics.setFlashColor(Color.red);
        DebugGraphics.setFlashTime(1000);
        RepaintManager.currentManager(panel).setDoubleBufferingEnabled(false);
        panel.setDebugGraphicsOptions(DebugGraphics.FLASH_OPTION);
        panel.setDebugGraphicsOptions(DebugGraphics.LOG_OPTION);
         */



        //
        JTabbedPane tabbedPane = new JTabbedPane();
        //Tree viewer.
        JPanel treeControllerPanel = new JPanel();
        treeControllerPanel.setLayout(new BorderLayout());
        //
        //TreePanel treePanel = new TreePanel(sBoxExpression,viewScale.toDouble());
        TreePanelCons treePanel = new TreePanelCons(expression2, viewScale.toDouble(), false);
        //
        MathPanelController treePanelScaler = new MathPanelController(treePanel,viewScale.toDouble());
        JScrollPane treeScrollPane = new JScrollPane(treePanel,JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
        treeScrollPane.getVerticalScrollBar().setUnitIncrement(16);
        treeControllerPanel.add(treeScrollPane);
        treeControllerPanel.add(treePanelScaler, BorderLayout.NORTH);
        tabbedPane.addTab("Expression Tree", null, treeControllerPanel, "Expression tree viewer..");
        //MathPiper built-in math viewer.
        TexParser parser = new TexParser();
        SymbolBox sBoxExpression = parser.parse(texString);
        //
        //Math viewer.
        JPanel mathControllerPanel = new JPanel();
        mathControllerPanel.setLayout(new BorderLayout());
        MathPanel mathPanel = new MathPanel(sBoxExpression, viewScale.toDouble());
        MathPanelController mathPanelScaler = new MathPanelController(mathPanel, viewScale.toDouble());
        JScrollPane scrollPane = new JScrollPane(mathPanel,JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);
        mathControllerPanel.add(scrollPane);
        mathControllerPanel.add(mathPanelScaler, BorderLayout.NORTH);
        tabbedPane.addTab("Math Form", null, mathControllerPanel, "Math expression viewer.");
        





        Box box = Box.createVerticalBox();



        //JLatexMath
	TeXFormula formula = new TeXFormula(texString);
        JLabel latexLabel = new JLabel();
        JPanel latexPanelController = new LatexRenderingController(formula, latexLabel, 100);

        JPanel screenCapturePanel = new ScreenCapturePanel();

        screenCapturePanel.add(latexLabel);

        JScrollPane jMathTexScrollPane = new JScrollPane(screenCapturePanel,JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);
        jMathTexScrollPane.getViewport().setBackground(Color.WHITE);
        box.add(jMathTexScrollPane);


        box.add(tabbedPane); //Uncomment to show MathPiper's built-in math viewer.
        

        contentPane.add(box);

        contentPane.add(latexPanelController, BorderLayout.NORTH);


        frame.setAlwaysOnTop(false);
        frame.setTitle("Math Viewer");
        frame.setResizable(true);
        frame.pack();
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int height = screenSize.height;
        int width = screenSize.width;
        frame.setSize(width/2, height/2);
        frame.setLocationRelativeTo(null);

        frame.setVisible(true);


        //getTopOfStackPointer(aEnvironment, aStackTop).setCons(resultPointer.getCons()); //This use to print Latex code.

        JavaObject response = new JavaObject(frame);

        setTopOfStack(aEnvironment, aStackTop, BuiltinObjectCons.getInstance(aEnvironment, aStackTop, response));

    }//end method.


}//end class.




/*
%mathpiper_docs,name="ViewMath",categories="Mathematics Functions;Built In;Visualization"
*CMD ViewMath --- display an expression in traditional form

*CALL
    ViewMath(expression)

*PARMS
{expression} -- an expression to view

*DESC
Display an expression in traditional form.

*E.G.
In> ViewMath(Expand((2*x)*(x+3)*(x+4)));

In> ViewMath(15*x^2 * Hold(Integrate(x,0,Infinity)Exp(-x^2)));


 
/%mathpiper

index := 1;

expressionsList := [];

While(index <= 9)
[
   expressionsList := Append(expressionsList, RandomPoly(x,3,1,10));

   index++;
];

matrix := Partition(expressionsList,3);

ViewMath(matrix);

/%/mathpiper
 
 
 
The ViewXXX functions all return a reference to the Java JFrame windows which they are displayed in.
This JFrame instance can be used to hide, show, and dispose of the window.
 

In> frame := ViewMath(x^2)
Result: javax.swing.JFrame

In> JavaCall(frame, "hide")
Result: True

In> JavaCall(frame, "show")
Result: True

In> JavaCall(frame, "dispose")
Result: True

*SEE ViewList, ViewLatex
%/mathpiper_docs
*/

