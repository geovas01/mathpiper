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
import javax.swing.Box;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import org.mathpiper.builtin.BigNumber;
import org.mathpiper.builtin.BuiltinFunction;
import org.mathpiper.builtin.BuiltinFunctionEvaluator;
import org.mathpiper.lisp.Environment;
import org.mathpiper.lisp.LispError;
import org.mathpiper.lisp.Utility;
import org.mathpiper.lisp.cons.AtomCons;
import org.mathpiper.lisp.cons.Cons;
import org.mathpiper.lisp.cons.ConsPointer;
import org.mathpiper.lisp.cons.SublistCons;

import javax.swing.JLabel;

import org.mathpiper.builtin.JavaObject;
import org.mathpiper.lisp.cons.BuiltinObjectCons;
import org.mathpiper.ui.gui.worksheets.LatexRenderingController;
import org.mathpiper.ui.gui.worksheets.ScreenCapturePanel;
import org.scilab.forge.jlatexmath.TeXFormula;

/**
 *
 *
 */
public class ViewMath extends BuiltinFunction {

    public void plugIn(Environment aEnvironment)  throws Exception
    {
        aEnvironment.getBuiltinFunctions().setAssociation(
                new BuiltinFunctionEvaluator(this, 2, BuiltinFunctionEvaluator.Fixed | BuiltinFunctionEvaluator.Macro),
                "ViewMathInternal");

       String[] parameters = new String[] {"expression","size"};
       Utility.declareFunction("ViewMath", parameters, "ViewMathInternal(expression, size);", aEnvironment, LispError.TODO);


       parameters = new String[] {"expression"};
       Utility.declareFunction("ViewMath", parameters, "ViewMathInternal(expression, 2);", aEnvironment, LispError.TODO);


    }//end method.

    public void evaluate(Environment aEnvironment, int aStackTop) throws Exception {

        //Utility.lispEvaluate(aEnvironment, "TeXForm(x^2);");

        Cons head = SublistCons.getInstance(aEnvironment, AtomCons.getInstance(aEnvironment, aStackTop, "TeXForm"));

        ((ConsPointer) head.car()).cdr().setCons(getArgumentPointer(aEnvironment, aStackTop, 1).getCons());


        ConsPointer resultPointer = new ConsPointer();

        ConsPointer viewScalePointer = new ConsPointer();
        viewScalePointer.setCons(getArgumentPointer(aEnvironment, aStackTop, 2).getCons());
        aEnvironment.iLispExpressionEvaluator.evaluate(aEnvironment, aStackTop, resultPointer, viewScalePointer);
        BigNumber viewScale = (BigNumber) resultPointer.getCons().getNumber(aEnvironment.getPrecision(), aEnvironment);
        if(viewScale == null) LispError.checkArgument(aEnvironment, aStackTop, 1, "ViewMath");


        

        aEnvironment.iLispExpressionEvaluator.evaluate(aEnvironment, aStackTop, resultPointer, new ConsPointer(head));

        String texString = (String) resultPointer.car();
        texString = Utility.stripEndQuotesIfPresent(aEnvironment, aStackTop, texString);
        texString = texString.substring(1, texString.length());
        texString = texString.substring(0, texString.length() - 1);



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



        /*
        //MathPiper built-in math viewer.
        TexParser parser = new TexParser();
        SymbolBox sBoxExpression = parser.parse(texString);
        JTabbedPane tabbedPane = new JTabbedPane();
        //Math viewer.
        JPanel mathControllerPanel = new JPanel();
        mathControllerPanel.setLayout(new BorderLayout());
        MathPanel mathPanel = new MathPanel(sBoxExpression, viewScale.toDouble());
        MathPanelController mathPanelScaler = new MathPanelController(mathPanel, viewScale.toDouble());
        JScrollPane scrollPane = new JScrollPane(mathPanel,JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
        mathControllerPanel.add(scrollPane);
        mathControllerPanel.add(mathPanelScaler, BorderLayout.NORTH);
        tabbedPane.addTab("Math Form", null, mathControllerPanel, "Math expression viewer.");
        //Tree viewer.
        JPanel treeControllerPanel = new JPanel();
        treeControllerPanel.setLayout(new BorderLayout());
        TreePanel treePanel = new TreePanel(sBoxExpression,viewScale.toDouble());
        MathPanelController treePanelScaler = new MathPanelController(treePanel,viewScale.toDouble());
        JScrollPane treeScrollPane = new JScrollPane(treePanel,JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
        treeControllerPanel.add(treeScrollPane);
        treeControllerPanel.add(treePanelScaler, BorderLayout.NORTH);
        tabbedPane.addTab("Parse Tree", null, treeControllerPanel, "Parse tree viewer..");
        */





        Box box = Box.createVerticalBox();



        //JLatexMath
	TeXFormula formula = new TeXFormula(texString);
        JLabel latexLabel = new JLabel();
        JPanel latexPanelController = new LatexRenderingController(formula, latexLabel, 100);

        JPanel screenCapturePanel = new ScreenCapturePanel();

        screenCapturePanel.add(latexLabel);

        JScrollPane jMathTexScrollPane = new JScrollPane(screenCapturePanel,JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
        jMathTexScrollPane.getViewport().setBackground(Color.WHITE);
        box.add(jMathTexScrollPane);


        //box.add(tabbedPane); //MathPiper's built-in math viewer.
        

        contentPane.add(box);

        contentPane.add(latexPanelController, BorderLayout.NORTH);


        frame.setAlwaysOnTop(false);
        frame.setTitle("Math Viewer");
        frame.setSize(new Dimension(300, 200));
        frame.setResizable(true);
        frame.setLocationRelativeTo(null);

        frame.pack();
        frame.setVisible(true);


        //getTopOfStackPointer(aEnvironment, aStackTop).setCons(resultPointer.getCons()); //This use to print Latex code.

        JavaObject response = new JavaObject(frame);

        getTopOfStackPointer(aEnvironment, aStackTop).setCons(BuiltinObjectCons.getInstance(aEnvironment, aStackTop, response));

    }//end method.


}//end class.




/*
%mathpiper_docs,name="ViewMath",categories="User Functions;Built In;Visualization"
*CMD ViewMath --- display an expression in traditional form

*CALL
    ViewMath(expression)

*Params
{expression} -- an expression to view

*DESC
Display an expression in traditional form.

*E.G.
In> ViewMath(Expand((2*x)*(x+3)*(x+4)));

In> ViewMath(15*x^2 * Hold(Integrate(x,0,Infinity)Exp(-x^2)));


 
/%mathpiper

index := 1;

expressionsList := {};

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

