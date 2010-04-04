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
import java.awt.Container;
import java.awt.Dimension;
import java.util.HashMap;
import java.util.Map;
import javax.swing.JFrame;
import org.mathpiper.builtin.BuiltinFunction;
import org.mathpiper.builtin.BuiltinFunctionEvaluator;
import org.mathpiper.lisp.Environment;
import org.mathpiper.lisp.LispError;
import org.mathpiper.lisp.Utility;
import org.mathpiper.lisp.cons.ConsPointer;

/**
 *
 *
 */
public class ControlChart extends BuiltinFunction {

    private Map defaultOptions;


    public void plugIn(Environment aEnvironment)  throws Exception
    {
        aEnvironment.getBuiltinFunctions().setAssociation(
                new BuiltinFunctionEvaluator(this, 1, BuiltinFunctionEvaluator.Variable | BuiltinFunctionEvaluator.Function),
                "ControlChartInternal");

        defaultOptions = new HashMap();
        defaultOptions.put("title", null);
        defaultOptions.put("xAxisLabel", null);
        defaultOptions.put("yAxisLabel", null);
        defaultOptions.put("seriesTitle", "");
        defaultOptions.put("legend", true);
        defaultOptions.put("toolTips", true);
        defaultOptions.put("domainCrosshair", true);
        defaultOptions.put("rangeCrosshair", true);

    }//end method.

    public void evaluate(Environment aEnvironment, int aStackTop) throws Exception {

        ConsPointer argumentsPointer = getArgumentPointer(aEnvironment, aStackTop, 1);

        LispError.check(aEnvironment, aStackTop, Utility.isSublist(argumentsPointer), LispError.INVALID_ARGUMENT, "ControlChart");

        argumentsPointer.goSub(aStackTop, aEnvironment); //Go to sub list.

        argumentsPointer.goNext(aStackTop, aEnvironment); //Strip List tag.

        Map userOptions = Utility.optionsListToJavaMap(aEnvironment, aStackTop, argumentsPointer, defaultOptions);

        JFrame frame = new JFrame();
        Container contentPane = frame.getContentPane();
        frame.setBackground(Color.WHITE);
        contentPane.setBackground(Color.WHITE);


        frame.setAlwaysOnTop(false);
        frame.setTitle("Control Chart");
        frame.setSize(new Dimension(300, 200));
        frame.setResizable(true);
        frame.setLocationRelativeTo(null);

        frame.pack();
        frame.setVisible(true);




        Utility.putTrueInPointer(aEnvironment, getTopOfStackPointer(aEnvironment, aStackTop));

    }//end method.


}//end class.



