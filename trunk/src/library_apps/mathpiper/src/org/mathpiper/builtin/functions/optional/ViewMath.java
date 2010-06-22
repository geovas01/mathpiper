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
import org.mathpiper.ui.gui.jmathtex.exceptions.ParseException;
import javax.swing.Box;
import javax.swing.JFrame;
import javax.swing.JLabel;
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
import org.mathpiper.lisp.cons.ConsPointer;
import org.mathpiper.lisp.cons.SublistCons;
import org.mathpiper.ui.gui.jmathtex.TeXFormula;
import org.mathpiper.ui.gui.jmathtex.TeXIcon;
import org.mathpiper.ui.gui.worksheets.MathPanel;
import org.mathpiper.ui.gui.worksheets.MathPanelController;
import org.mathpiper.ui.gui.worksheets.TreePanel;
import org.mathpiper.ui.gui.worksheets.latexparser.TexParser;
import org.mathpiper.ui.gui.worksheets.symbolboxes.SymbolBox;

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
        LispError.checkArgument(aEnvironment, aStackTop, viewScale != null, 1, "ViewMath");


        

        aEnvironment.iLispExpressionEvaluator.evaluate(aEnvironment, aStackTop, resultPointer, new ConsPointer(head));

        String texString = (String) resultPointer.car();
        texString = Utility.stripEndQuotes(texString);
        texString = texString.substring(1, texString.length());
        texString = texString.substring(0, texString.length() - 1);



        TexParser parser = new TexParser();
        SymbolBox sBoxExpression = parser.parse(texString);

        //ViewTree viewTree = new ViewTree();
        //viewTree.walkTree(sBoxExpression);



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


        Box box = Box.createVerticalBox();

        box.add(tabbedPane);

        try
        {
            TeXFormula formula = new TeXFormula(texString);
            TeXIcon icon = formula.createTeXIcon(0, 50);
            icon.setInsets(new Insets(1, 1, 1, 1));
            JLabel jMathTexLabel = new JLabel();
            jMathTexLabel.setPreferredSize(new Dimension(icon.getIconWidth(), icon.getIconHeight()));
            jMathTexLabel.setAlignmentY(icon.getBaseLine());
            jMathTexLabel.setIcon(icon);
            JScrollPane jMathTexScrollPane = new JScrollPane(jMathTexLabel,JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
            jMathTexScrollPane.getViewport().setBackground(Color.WHITE);

            box.add(jMathTexScrollPane);
        }
        catch(ParseException pe)
        {
            box.add(new JLabel(pe.getMessage()));
        }

        contentPane.add(box);


        frame.setAlwaysOnTop(false);
        frame.setTitle("Math Viewer");
        frame.setSize(new Dimension(300, 200));
        frame.setResizable(true);
        frame.setLocationRelativeTo(null);

        frame.pack();
        frame.setVisible(true);




        getTopOfStackPointer(aEnvironment, aStackTop).setCons(resultPointer.getCons());

    }//end method.


}//end class.




/*
%mathpiper_docs,name="ViewMath",categories="User Functions;Visualization"
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

*SEE ViewList, ViewLatex
%/mathpiper_docs
*/

