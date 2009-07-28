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
import java.util.Map;
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
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYBarRenderer;
import org.jfree.data.xy.IntervalXYDataset;
import org.mathpiper.lisp.cons.BuiltinObjectCons;


public class LineChart extends BuiltinFunction {

    private Map defaultOptions;


    public void plugIn(Environment aEnvironment) {
        aEnvironment.getBuiltinFunctions().setAssociation(
                new BuiltinFunctionEvaluator(this, 1, BuiltinFunctionEvaluator.Variable | BuiltinFunctionEvaluator.Function),
                "LineChart");

        defaultOptions = new HashMap();
        defaultOptions.put("title", null);
        defaultOptions.put("xAxisLabel", null);
        defaultOptions.put("yAxisLabel", null);
        defaultOptions.put("seriesTitle", "");
        defaultOptions.put("orientation", PlotOrientation.VERTICAL);
        defaultOptions.put("legend", true);
        defaultOptions.put("toolTips", true);

    }//end method.

    //private StandardFileOutputStream out = new StandardFileOutputStream(System.out);

    public void evaluate(Environment aEnvironment, int aStackTop) throws Exception {

        ConsPointer argumentsPointer = getArgumentPointer(aEnvironment, aStackTop, 1);

        LispError.check(Utility.isSublist(argumentsPointer), LispError.INVALID_ARGUMENT);

        argumentsPointer.goSub(); //Go to sub list.

        argumentsPointer.goNext(); //Strip List tag.

        LispError.check(Utility.isList(argumentsPointer), LispError.NOT_A_LIST);

        ConsPointer dataListPointer = (ConsPointer) argumentsPointer.car(); //Grab the first member of the list.

        ConsPointer optionsPointer = (ConsPointer) argumentsPointer.cdr();

        Map userOptions = Utility.optionsListToJavaMap(optionsPointer, defaultOptions);

        IntervalXYDataset dataSet = ChartUtility.listToIntervalXYDataset(dataListPointer, userOptions);


        JFreeChart chart = ChartFactory.createXYLineChart(
                (String) userOptions.get("title"), //title.
                (String) userOptions.get("xAxisLabel"), //x axis label.
                (String) userOptions.get("yAxisLabel"), //y axis label.
                dataSet, //
                (PlotOrientation) userOptions.get("orientation"), //orientation.
                ((Boolean) userOptions.get("legend")).booleanValue(), //legend.
                ((Boolean) userOptions.get("toolTips")).booleanValue(),//tool tips.
                false);//urls.

        XYPlot plot = (XYPlot) chart.getPlot();
        plot.setDomainCrosshairVisible(true);
        plot.setDomainCrosshairLockedOnData(true);
        plot.setRangeCrosshairVisible(true);
        plot.setRangeCrosshairLockedOnData(true);
        plot.setDomainZeroBaselineVisible(true);
        plot.setRangeZeroBaselineVisible(true);
        plot.setDomainPannable(true);
        plot.setRangePannable(true);
        NumberAxis domainAxis = (NumberAxis) plot.getDomainAxis();
        domainAxis.setAutoRangeIncludesZero(false);


        //Create and display a frame...  Import("org/mathpiper/builtin/functions/plugins/jfreechart/")
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

