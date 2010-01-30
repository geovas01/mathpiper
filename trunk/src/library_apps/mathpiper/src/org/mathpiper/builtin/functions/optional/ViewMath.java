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
import javax.swing.JFrame;
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
import org.mathpiper.ui.gui.worksheets.MathPanel;
import org.mathpiper.ui.gui.worksheets.MathPanelController;
import org.mathpiper.ui.gui.worksheets.TexParser;
import org.mathpiper.ui.gui.worksheets.symbolboxes.SBox;

/**
 *
 *
 */
public class ViewMath extends BuiltinFunction {

    public void plugIn(Environment aEnvironment) {
        aEnvironment.getBuiltinFunctions().setAssociation(
                new BuiltinFunctionEvaluator(this, 2, BuiltinFunctionEvaluator.Fixed | BuiltinFunctionEvaluator.Macro),
                "ViewMath");
    }//end method.

    public void evaluate(Environment aEnvironment, int aStackTop) throws Exception {

        //Utility.lispEvaluate(aEnvironment, "TeXForm(x^2);");

        Cons head = SublistCons.getInstance(aEnvironment, AtomCons.getInstance(aEnvironment, "TeXForm"));

        ((ConsPointer) head.car()).cdr().setCons(getArgumentPointer(aEnvironment, aStackTop, 1).getCons());


        ConsPointer viewScalePointer = new ConsPointer();
        viewScalePointer.setCons(getArgumentPointer(aEnvironment, aStackTop, 2).getCons());
        BigNumber viewScale = (BigNumber) viewScalePointer.getCons().getNumber(aEnvironment.getPrecision());
        LispError.checkArgument(aEnvironment, aStackTop, viewScale != null, 1, "ViewMath");


        ConsPointer resultPointer = new ConsPointer();

        aEnvironment.iLispExpressionEvaluator.evaluate(aEnvironment, resultPointer, new ConsPointer(head));

        String texString = (String) resultPointer.car();
        texString = Utility.stripEndQuotes(texString);
        texString = texString.substring(1, texString.length());
        texString = texString.substring(0, texString.length() - 1);



        TexParser parser = new TexParser();
        SBox sBoxExpression = parser.parse(texString);

        MathPanel mathPanel = new MathPanel(sBoxExpression, viewScale.toDouble());


        JFrame frame = new JFrame();
        Container contentPane = frame.getContentPane();
        frame.setBackground(Color.WHITE);
        contentPane.setBackground(Color.WHITE);


        MathPanelController mathPanelScaler = new MathPanelController(mathPanel, viewScale.toDouble());

        JScrollPane scrollPane = new JScrollPane(mathPanel,JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);

        /*
        DebugGraphics.setFlashCount(10);
        DebugGraphics.setFlashColor(Color.red);
        DebugGraphics.setFlashTime(1000);
        RepaintManager.currentManager(panel).setDoubleBufferingEnabled(false);
        panel.setDebugGraphicsOptions(DebugGraphics.FLASH_OPTION);
        panel.setDebugGraphicsOptions(DebugGraphics.LOG_OPTION);
         */

        contentPane.add(scrollPane);
        contentPane.add(mathPanelScaler, BorderLayout.NORTH);

        frame.setAlwaysOnTop(false);
        frame.setTitle("MathPiper");
        frame.setSize(new Dimension(300, 200));
        frame.setResizable(true);
        frame.setLocationRelativeTo(null);

        frame.pack();
        frame.setVisible(true);




        getTopOfStackPointer(aEnvironment, aStackTop).setCons(resultPointer.getCons());

    }//end method.


}//end class.



