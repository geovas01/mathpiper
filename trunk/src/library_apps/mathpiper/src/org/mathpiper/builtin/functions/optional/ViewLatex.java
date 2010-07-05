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
import org.mathpiper.ui.gui.hoteqn.sHotEqn;
import org.mathpiper.ui.gui.worksheets.MathPanel;
import org.mathpiper.ui.gui.worksheets.MathPanelController;
import org.mathpiper.ui.gui.worksheets.latexparser.TexParser;
import org.mathpiper.ui.gui.worksheets.symbolboxes.SymbolBox;

import java.awt.Insets;
import java.awt.Color;

import javax.swing.JLabel;

import org.mathpiper.builtin.JavaObject;
import org.mathpiper.lisp.cons.BuiltinObjectCons;
import org.scilab.forge.jlatexmath.TeXConstants;
import org.scilab.forge.jlatexmath.TeXFormula;
import org.scilab.forge.jlatexmath.TeXIcon;
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


    }//end method.

    public void evaluate(Environment aEnvironment, int aStackTop) throws Exception {

        String latexString = null;

        ConsPointer consPointer = null;

        Object expressionPointer = getArgumentPointer(aEnvironment, aStackTop, 1).car();

        if (expressionPointer instanceof String)
        {
            latexString = (String) expressionPointer;

            latexString = Utility.stripEndQuotes(latexString);

            latexString = Utility.stripEndDollarSigns(latexString);
        }
        else
        {
            LispError.raiseError("The first argument must be a string which contains Latex code.", "ViewLatex", aStackTop, aEnvironment);
        }//end else.



        ConsPointer resultPointer = new ConsPointer();

        ConsPointer viewScalePointer = new ConsPointer();
        viewScalePointer.setCons(getArgumentPointer(aEnvironment, aStackTop, 2).getCons());
        aEnvironment.iLispExpressionEvaluator.evaluate(aEnvironment, aStackTop, resultPointer, viewScalePointer);
        BigNumber viewScale = (BigNumber) resultPointer.getCons().getNumber(aEnvironment.getPrecision(), aEnvironment);
        LispError.checkArgument(aEnvironment, aStackTop, viewScale != null, 1, "ViewLatex");

        /*sHotEqn hotEqn = new sHotEqn();
        hotEqn.setFontsizes(18,18,18,18);
        hotEqn.setEquation(latexString);
        JScrollPane hotEqnScrollPane = new JScrollPane(hotEqn,JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
        */


        /*
        JEditorPane htmlPane = new JEditorPane();
        htmlPane.setContentType("text/html");
        htmlPane.setEditable(false); // very important, as the default is true (sorry about that!)
        try {
        htmlPane.setPage(ViewLatex.class.getResource("/org/mathpiper/test/test.html"));
        } catch (Exception e) {
        e.printStackTrace();
        }*/

        TexParser parser = new TexParser();
        SymbolBox sBoxExpression = parser.parse(latexString);
        MathPanel mathPanel = new MathPanel(sBoxExpression, viewScale.toDouble());


        JFrame frame = new JFrame();
        Container contentPane = frame.getContentPane();
        frame.setBackground(Color.WHITE);
        contentPane.setBackground(Color.WHITE);


        MathPanelController mathPanelScaler = new MathPanelController(mathPanel, viewScale.toDouble());

        JScrollPane mathPiperScrollPane = new JScrollPane(mathPanel,JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);

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
        DefaultTeXFont.registerAlphabet(new CyrillicRegistration());
	DefaultTeXFont.registerAlphabet(new GreekRegistration());
	TeXFormula formula = new TeXFormula(latexString);
	TeXIcon icon = formula.createTeXIcon(TeXConstants.STYLE_DISPLAY, 50);
	icon.setInsets(new Insets(5, 5, 5, 5));
        JLabel jLatexMathLabel = new JLabel();
        jLatexMathLabel.setPreferredSize(new Dimension(icon.getIconWidth(), icon.getIconHeight()));
        jLatexMathLabel.setAlignmentY(icon.getBaseLine());
        jLatexMathLabel.setIcon(icon);
        JScrollPane jMathTexScrollPane = new JScrollPane(jLatexMathLabel,JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
        jMathTexScrollPane.getViewport().setBackground(Color.WHITE);
        box.add(jMathTexScrollPane);

        
        contentPane.add(box);
        contentPane.add(mathPanelScaler, BorderLayout.NORTH);
        box.add(mathPiperScrollPane);

        frame.setAlwaysOnTop(false);
        frame.setTitle("MathPiper");
        frame.setSize(new Dimension(300, 200));
        frame.setResizable(true);
        frame.setLocationRelativeTo(null);

        frame.pack();
        frame.setVisible(true);


        JavaObject response = new JavaObject(frame);

        getTopOfStackPointer(aEnvironment, aStackTop).setCons(BuiltinObjectCons.getInstance(aEnvironment, aStackTop, response));


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



