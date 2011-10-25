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
import java.awt.Container;
import java.awt.Dimension;
import javax.swing.Box;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import org.mathpiper.builtin.BigNumber;
import org.mathpiper.builtin.BuiltinFunction;
import org.mathpiper.builtin.BuiltinFunctionEvaluator;
import org.mathpiper.lisp.Environment;
import org.mathpiper.lisp.LispError;
import org.mathpiper.lisp.Utility;
import org.mathpiper.lisp.cons.ConsPointer;

import java.awt.Color;

import javax.swing.JLabel;

import javax.swing.JPanel;
import org.mathpiper.builtin.JavaObject;
import org.mathpiper.lisp.cons.BuiltinObjectCons;
import org.mathpiper.lisp.cons.Cons;
import org.mathpiper.ui.gui.worksheets.LatexRenderingController;
import org.mathpiper.ui.gui.worksheets.ScreenCapturePanel;
import org.scilab.forge.jlatexmath.TeXFormula;
import org.scilab.forge.jlatexmath.DefaultTeXFont;

import org.scilab.forge.jlatexmath.cyrillic.CyrillicRegistration;
import org.scilab.forge.jlatexmath.greek.GreekRegistration;

/**
 *
 *
 */
public class ViewLatex extends BuiltinFunction {

    public void plugIn(Environment aEnvironment)  throws Exception
    {
        aEnvironment.getBuiltinFunctions().setAssociation(
                new BuiltinFunctionEvaluator(this, 2, BuiltinFunctionEvaluator.Fixed | BuiltinFunctionEvaluator.Function),
                "ViewLatexInternal");

       String[] parameters = new String[] {"expression","size"};
       Utility.declareFunction("ViewLatex", parameters, "ViewLatexInternal(expression, size);", aEnvironment, LispError.TODO);


       parameters = new String[] {"expression"};
       Utility.declareFunction("ViewLatex", parameters, "ViewLatexInternal(expression, 2);", aEnvironment, LispError.TODO);


        DefaultTeXFont.registerAlphabet(new CyrillicRegistration());
	DefaultTeXFont.registerAlphabet(new GreekRegistration());


    }//end method.

    public void evaluate(Environment aEnvironment, int aStackTop) throws Exception {

        String latexString = null;

        Object expressionPointer = getArgumentPointer(aEnvironment, aStackTop, 1).car();

        if (expressionPointer instanceof String)
        {
            latexString = (String) expressionPointer;

            latexString = Utility.stripEndQuotesIfPresent(aEnvironment, aStackTop, latexString);

            latexString = Utility.stripEndDollarSigns(latexString);
        }
        else
        {
            LispError.raiseError("The first argument must be a string which contains Latex code.", "ViewLatex", aStackTop, aEnvironment);
        }//end else.


        Cons viewScalePointer = getArgumentPointer(aEnvironment, aStackTop, 2);
        Cons result = aEnvironment.iLispExpressionEvaluator.evaluate(aEnvironment, aStackTop, viewScalePointer);
        BigNumber viewScale = (BigNumber) result.getNumber(aEnvironment.getPrecision(), aEnvironment);
        if(viewScale == null) LispError.checkArgument(aEnvironment, aStackTop, 1, "ViewLatex");

        /*sHotEqn hotEqn = new sHotEqn();
        hotEqn.setFontsizes(18,18,18,18);
        hotEqn.setEquation(latexString);
        JScrollPane hotEqnScrollPane = new JScrollPane(hotEqn,JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
        */




        //MathPiper built-in math viewer.
        /*TexParser parser = new TexParser();
        SymbolBox sBoxExpression = parser.parse(latexString);
        MathPanel mathPanel = new MathPanel(sBoxExpression, viewScale.toDouble());
        MathPanelController mathPanelScaler = new MathPanelController(mathPanel, viewScale.toDouble());
        JScrollPane mathPiperScrollPane = new JScrollPane(mathPanel,JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
         */

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

        Box box = Box.createVerticalBox();


        //JLateXMath
	TeXFormula formula = new TeXFormula(latexString);
        JLabel latexLabel = new JLabel();
        JPanel latexPanelController = new LatexRenderingController(formula, latexLabel, 100);

        JPanel screenCapturePanel = new ScreenCapturePanel();

        screenCapturePanel.add(latexLabel);

        JScrollPane jMathTexScrollPane = new JScrollPane(screenCapturePanel, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
        jMathTexScrollPane.getViewport().setBackground(Color.WHITE);
        box.add(jMathTexScrollPane);

        contentPane.add(box);

        contentPane.add(latexPanelController, BorderLayout.NORTH);

        //box.add(mathPiperScrollPane);

        frame.setAlwaysOnTop(false);
        frame.setTitle("MathPiper");
        frame.setSize(new Dimension(300, 200));
        frame.setResizable(true);
        frame.setLocationRelativeTo(null);

        frame.pack();
        frame.setVisible(true);


        JavaObject response = new JavaObject(frame);

        setTopOfStackPointer(aEnvironment, aStackTop, BuiltinObjectCons.getInstance(aEnvironment, aStackTop, response));


    }//end method.


}//end class.





/*
%mathpiper_docs,name="ViewLatex",categories="User Functions;Visualization"
*CMD ViewLatex --- display rendered Latex code

*CALL
    ViewLatex(string)

*Params
{string} -- a string which contains Latex code

*DESC
Display rendered Latex code.  Note: backslashes must be escaped
with a backslash.
 
*E.G.
In> ViewLatex("2\\sum_{i=1}^n a_i")
Result: javax.swing.JFrame



The ViewXXX functions all return a reference to the Java JFrame windows which they are displayed in.
This JFrame instance can be used to hide, show, and dispose of the window.

In> frame := ViewLatex("2\\sum_{i=1}^n a_i")
Result: javax.swing.JFrame

In> JavaCall(frame, "hide")
Result: True

In> JavaCall(frame, "show")
Result: True

In> JavaCall(frame, "dispose")
Result: True

*SEE ViewMath
%/mathpiper_docs
*/



