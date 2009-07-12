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
package org.mathpiper.builtin.functions.plugins.jfreechart;

import java.util.HashMap;
import org.mathpiper.builtin.BuiltinFunction;
import org.mathpiper.builtin.BuiltinFunctionEvaluator;
import org.mathpiper.builtin.JavaObject;
import org.mathpiper.lisp.Environment;
import org.mathpiper.lisp.LispError;
import org.mathpiper.lisp.Utility;
import org.mathpiper.lisp.cons.ConsPointer;


import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartFrame;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.statistics.HistogramDataset;
import org.mathpiper.lisp.cons.BuiltinObjectCons;

/**
 *
 *
 */
public class JFreeChartHistogram extends BuiltinFunction {

    private HashMap defaultOptions;

    public void plugIn(Environment aEnvironment) {
        aEnvironment.getBuiltinFunctions().setAssociation(
                new BuiltinFunctionEvaluator(this, 1, BuiltinFunctionEvaluator.Variable | BuiltinFunctionEvaluator.Function),
                "Histogram");

        defaultOptions = new HashMap();
        defaultOptions.put("title",null);
        defaultOptions.put("xAxisLabel",null);
        defaultOptions.put("yAxisLabel",null);
        defaultOptions.put("seriesTitle","Data");
        defaultOptions.put("plotOrientation",PlotOrientation.VERTICAL);
        defaultOptions.put("legend",true);
        defaultOptions.put("toolTips",true);

    }//end method.

    //private StandardFileOutputStream out = new StandardFileOutputStream(System.out);
    public void evaluate(Environment aEnvironment, int aStackTop) throws Exception {

            ConsPointer argumentsPointer = getArgumentPointer(aEnvironment, aStackTop, 1);

            LispError.check(Utility.isSublist(argumentsPointer), LispError.INVALID_ARGUMENT);

            argumentsPointer.goSub();
            argumentsPointer.goNext();

            LispError.check(Utility.isList(argumentsPointer), LispError.NOT_A_LIST);

            ConsPointer dataListPointer = (ConsPointer) argumentsPointer.car();

            double[] dataValues = JavaObject.LispListToJavaDoubleArray(dataListPointer);

            argumentsPointer.goNext();

            HashMap userOptions = (HashMap) defaultOptions.clone();

            while (argumentsPointer.getCons() != null) {
                //Obtain -> operator.
                ConsPointer optionPointer = (ConsPointer) argumentsPointer.car();
                LispError.check(optionPointer.type() == Utility.ATOM, LispError.INVALID_ARGUMENT);
                String operator = (String) optionPointer.car();
                LispError.check(operator.equals("->"), LispError.INVALID_ARGUMENT);

                //Obtain key.
                optionPointer.goNext();
                LispError.check(optionPointer.type() == Utility.ATOM, LispError.INVALID_ARGUMENT);
                String key = (String) optionPointer.car();

                //Obtain value.
                optionPointer.goNext();
                LispError.check(optionPointer.type() == Utility.ATOM, LispError.INVALID_ARGUMENT);
                String value = (String) optionPointer.car();

                userOptions.put(key, value);

                argumentsPointer.goNext();

            }//end while 



             HistogramDataset dataSet = new HistogramDataset();
             dataSet.addSeries((String) userOptions.get("seriesTitle"), dataValues, 10);

         JFreeChart chart = ChartFactory.createHistogram(
                    (String) userOptions.get("seriesTitle"), //title.
                    (String) userOptions.get("xAxisLabel"), //x axis label.
                    (String) userOptions.get("yAxisLabel"), //y axis label.
                    dataSet, //
                    (PlotOrientation) userOptions.get("plotOrientation"), //orientation.
                    ((Boolean) userOptions.get("legend")).booleanValue(), //legend.
                    ((Boolean) userOptions.get("toolTips")).booleanValue(),//tool tips.
                    false);//urls.
	 
// create and display a frame...  Import("org/mathpiper/builtin/functions/plugins/jfreechart/")
//ChartFrame frame = new ChartFrame(null, chart);frame.pack();frame.setVisible(true);


                if (chart == null) {
                    Utility.putFalseInPointer(aEnvironment, getTopOfStackPointer(aEnvironment, aStackTop));
                    return;
                } else {
                    getTopOfStackPointer(aEnvironment, aStackTop).setCons(BuiltinObjectCons.getInstance(new JavaObject(new ChartPanel(chart))));
                    return;
                }//end if/else.


    }//end method.


}//end class.
