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



import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.data.xy.IntervalXYDataset;
import org.mathpiper.lisp.cons.BuiltinObjectCons;
import org.mathpiper.lisp.cons.Cons;

/**
 *
 *
 */
public class CumulativePlot extends BuiltinFunction {

    private Map defaultOptions;

    public void plugIn(Environment aEnvironment)  throws Throwable
    {
        aEnvironment.getBuiltinFunctions().put(
                "CumulativePlot", new BuiltinFunctionEvaluator(this, 1, BuiltinFunctionEvaluator.Variable | BuiltinFunctionEvaluator.Function));

        defaultOptions = new HashMap();
        defaultOptions.put("title", null);
        defaultOptions.put("xAxisLabel", null);
        defaultOptions.put("yAxisLabel", "Cumulative Frequency");
        defaultOptions.put("seriesTitle", "");
        defaultOptions.put("orientation", PlotOrientation.VERTICAL);
        defaultOptions.put("legend", true);
        defaultOptions.put("toolTips", true);
        defaultOptions.put("binMinumum", null);
        defaultOptions.put("binMaximum", null);
        defaultOptions.put("numberOfBins", null);

    }//end method.

    //private StandardFileOutputStream out = new StandardFileOutputStream(System.out);
    public void evaluate(Environment aEnvironment, int aStackTop) throws Throwable {

        Cons arguments = getArgument(aEnvironment, aStackTop, 1);

        if(! Utility.isSublist(arguments)) LispError.throwError(aEnvironment, aStackTop, LispError.INVALID_ARGUMENT, "");

        arguments = (Cons) arguments.car(); //Go to sub list.

        arguments = arguments.cdr(); //Strip List tag.

        if(! Utility.isList(arguments)) LispError.throwError(aEnvironment, aStackTop, LispError.NOT_A_LIST, "");

        Cons dataList = (Cons) arguments.car(); //Grab the first member of the list.

        Cons options = arguments.cdr();
        
        Map userOptions = ChartUtility.optionsListToJavaMap(aEnvironment, aStackTop, options, defaultOptions);


        IntervalXYDataset dataSet = ChartUtility.listToCumulativeDataset(aEnvironment, aStackTop, dataList, userOptions);

        //createXYBarChart(java.lang.String title, java.lang.String xAxisLabel, boolean dateAxis, java.lang.String yAxisLabel, IntervalXYDataset dataset, PlotOrientation orientation, boolean legend, boolean tooltips, boolean urls)

        JFreeChart chart = ChartFactory.createXYBarChart(
                (String) userOptions.get("title"), //title.
                (String) userOptions.get("xAxisLabel"), //x axis label.
                false,
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


//create and display a frame...  Import("org/mathpiper/builtin/functions/plugins/jfreechart/")
//ChartFrame frame = new ChartFrame(null, chart);frame.pack();frame.setVisible(true);


        if (chart == null) {
            setTopOfStack(aEnvironment, aStackTop, Utility.getFalseAtom(aEnvironment));
            return;
        } else {
            setTopOfStack(aEnvironment, aStackTop, BuiltinObjectCons.getInstance(aEnvironment, aStackTop, new JavaObject(new ChartPanel(chart))));
            return;
        }//end if/else.


    }//end method.
    
}//end class.







/*
%mathpiper_docs,name="CumulativePlot",categories="User Functions;Visualization"
*CMD CumulativePlot --- displays a graphic cumulative plot
*CORE
*CALL
	CumulativePlot(list, option, option, option...)

*PARMS

{list} -- a list which contains the values

{numberOfBins} -- the number of bins in the histogram

{title} -- the title of the histogram

{xAxisLabel} -- the label for the x axis

{yAxisLabel} -- the label for the y axis

{seriesTitle} -- the title for a single data series


*DESC

Creates a cumulative plot. Options are entered using the : operator.
For example, here is how to set the {title} option: {title: "Example Title"}.

*E.G.
/%mathpiper,title=""

samples := [
438,413,444,468,445,472,474,454,455,449,
450,450,450,459,466,470,457,441,450,445,
487,430,446,450,456,433,455,459,423,455,
451,437,444,453,434,454,448,435,432,441,
452,465,466,473,471,464,478,446,459,464,
441,444,458,454,437,443,465,435,444,457,
444,471,471,458,459,449,462,460,445,437,
461,453,452,438,445,435,454,428,454,434,
432,431,455,447,454,435,425,449,449,452,
471,458,445,463,423,451,440,442,441,439
];

CumulativePlot(samples,numberOfBins: 10, title: "Cumulative Plot", xAxisLabel: "X Axis", yAxisLabel: "Y Axis", seriesTitle: "Series Title");

/%/mathpiper


%/mathpiper_docs
*/

