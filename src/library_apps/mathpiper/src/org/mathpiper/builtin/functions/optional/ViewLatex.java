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
import java.awt.Insets;
import javax.swing.Box;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import org.mathpiper.builtin.BigNumber;
import org.mathpiper.builtin.BuiltinFunction;
import org.mathpiper.builtin.BuiltinFunctionEvaluator;
import org.mathpiper.lisp.Environment;
import org.mathpiper.lisp.LispError;
import org.mathpiper.lisp.Utility;
import org.mathpiper.lisp.cons.ConsPointer;
import org.mathpiper.ui.gui.hoteqn.sHotEqn;
import org.mathpiper.ui.gui.jmathtex.TeXFormula;
import org.mathpiper.ui.gui.jmathtex.TeXIcon;
import org.mathpiper.ui.gui.worksheets.MathPanel;
import org.mathpiper.ui.gui.worksheets.MathPanelController;
import org.mathpiper.ui.gui.worksheets.latexparser.TexParser;
import org.mathpiper.ui.gui.worksheets.symbolboxes.SymbolBox;

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

        sHotEqn hotEqn = new sHotEqn();
        hotEqn.setFontsizes(18,18,18,18);
        hotEqn.setEquation(latexString);
        JScrollPane hotEqnScrollPane = new JScrollPane(hotEqn,JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);


        TeXFormula formula = new TeXFormula(latexString);
        TeXIcon icon = formula.createTeXIcon(0, 50);
        icon.setInsets(new Insets(1, 1, 1, 1));
        JLabel jMathTexLabel = new JLabel();
        jMathTexLabel.setPreferredSize(new Dimension(icon.getIconWidth(), icon.getIconHeight()));
        jMathTexLabel.setAlignmentY(icon.getBaseLine());
        jMathTexLabel.setIcon(icon);
        JScrollPane jMathTexScrollPane = new JScrollPane(jMathTexLabel,JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);

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

        box.add(mathPiperScrollPane);

        box.add(hotEqnScrollPane);

        box.add(jMathTexScrollPane);

        contentPane.add(box);

        contentPane.add(mathPanelScaler, BorderLayout.NORTH);

        frame.setAlwaysOnTop(false);
        frame.setTitle("MathPiper");
        frame.setSize(new Dimension(300, 200));
        frame.setResizable(true);
        frame.setLocationRelativeTo(null);

        frame.pack();
        frame.setVisible(true);


        Utility.putTrueInPointer(aEnvironment, getTopOfStackPointer(aEnvironment, aStackTop));


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
Result> True

*SEE ViewMath
%/mathpiper_docs
*/



