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
import org.jfree.chart.renderer.xy.StandardXYBarPainter;
import org.jfree.chart.renderer.xy.XYBarRenderer;
import org.jfree.data.statistics.HistogramDataset;
import org.mathpiper.lisp.cons.BuiltinObjectCons;

/**
 *
 *
 */
public class Histogram extends BuiltinFunction {

    private Map defaultOptions;

    public void plugIn(Environment aEnvironment)  throws Exception
    {
        aEnvironment.getBuiltinFunctions().setAssociation(
                new BuiltinFunctionEvaluator(this, 1, BuiltinFunctionEvaluator.Variable | BuiltinFunctionEvaluator.Function),
                "Histogram");

        defaultOptions = new HashMap();
        defaultOptions.put("title", null);
        defaultOptions.put("xAxisLabel", null);
        defaultOptions.put("yAxisLabel", null);
        defaultOptions.put("seriesTitle", "");
        defaultOptions.put("orientation", PlotOrientation.VERTICAL);
        defaultOptions.put("legend", true);
        defaultOptions.put("toolTips", true);
        defaultOptions.put("binMinumum", null);
        defaultOptions.put("binMaximum", null);
        defaultOptions.put("numberOfBins", null);

    }//end method.

    //private StandardFileOutputStream out = new StandardFileOutputStream(System.out);
    public void evaluate(Environment aEnvironment, int aStackTop) throws Exception {

        ConsPointer argumentsPointer = new ConsPointer(getArgumentPointer(aEnvironment, aStackTop, 1));

        if(! Utility.isSublist(argumentsPointer)) LispError.throwError(aEnvironment, aStackTop, LispError.INVALID_ARGUMENT, "", "Histogram");

        argumentsPointer.goSub(aStackTop, aEnvironment); //Go to sub list.

        argumentsPointer.goNext(aStackTop, aEnvironment); //Strip List tag.

        if(! Utility.isList(argumentsPointer)) LispError.throwError(aEnvironment, aStackTop, LispError.NOT_A_LIST, "", "Histogram");

        ConsPointer dataListPointer = (ConsPointer) argumentsPointer.car(); //Grab the first member of the list.

        ConsPointer optionsPointer = new ConsPointer(argumentsPointer.cdr());

        Map userOptions = ChartUtility.optionsListToJavaMap(aEnvironment, aStackTop, optionsPointer, defaultOptions);



        HistogramDataset dataSet = ChartUtility.listToHistogramDataset(aEnvironment, aStackTop, dataListPointer, userOptions);

        JFreeChart chart = ChartFactory.createHistogram(
                (String) userOptions.get("title"), //title.
                (String) userOptions.get("xAxisLabel"), //x axis label.
                (String) userOptions.get("yAxisLabel"), //y axis label.
                dataSet, //
                (PlotOrientation) userOptions.get("orientation"), //orientation.
                ((Boolean) userOptions.get("legend")).booleanValue(), //legend.
                ((Boolean) userOptions.get("toolTips")).booleanValue(),//tool tips.
                false);//urls.

        XYPlot plot = (XYPlot) chart.getPlot();
        plot.setDomainPannable(true);
        plot.setRangePannable(true);
        plot.setForegroundAlpha(0.85f);
        NumberAxis yAxis = (NumberAxis) plot.getRangeAxis();
        yAxis.setStandardTickUnits(NumberAxis.createIntegerTickUnits());
        XYBarRenderer renderer = (XYBarRenderer) plot.getRenderer();
        renderer.setDrawBarOutline(true);
        renderer.setBarPainter(new StandardXYBarPainter());
        renderer.setShadowVisible(false);

//create and display a frame...  Import("org/mathpiper/builtin/functions/plugins/jfreechart/")
//ChartFrame frame = new ChartFrame(null, chart);frame.pack();frame.setVisible(true);


        if (chart == null) {
            setTopOfStackPointer(aEnvironment, aStackTop, Utility.putFalseInPointer(aEnvironment));
            return;
        } else {
            setTopOfStackPointer(aEnvironment, aStackTop, BuiltinObjectCons.getInstance(aEnvironment, aStackTop, new JavaObject(new ChartPanel(chart))));
            return;
        }//end if/else.


    }//end method.
    
}//end class.





/*
%mathpiper_docs,name="Histogram",categories="User Functions;Visualization"
*CMD Histogram --- displays a graphic histogram
*CORE
*CALL
	Histogram(list, option, option, option...)
    Histogram({list1, list2, list3...}, option, option, option...)

*PARMS

{list} -- a list which contains the values

{list1, list2, list3...} -- the data for multiple histograms is passed in as a list of lists

{binMinimum} -- the minimum bin value

{binMaximum} -- the maximum bin value

{numberOfBins} -- the number of bins in the histogram

{title} -- the title of the histogram

{xAxisLabel} -- the label for the x axis

{yAxisLabel} -- the label for the y axis

{seriesTitle} -- the title for a single data series

{series<x>Title} -- the title for more than one series. <x> can be 1, 2, 3, etc.

*DESC

Creates either a single histogram or multiple histograms on the same plot. Options are entered using the -> operator.
For example, here is how to set the {title} option: {title -> "Example Title"}.

*E.G.
/%mathpiper

Histogram({1.0, 1.1, 1.1, 1.2, 1.7, 2.2, 2.5, 4.0});

/%/mathpiper


/%mathpiper

Histogram({1.0, 1.1, 1.1, 1.2, 1.7, 2.2, 2.5, 4.0, 4.2}, seriesTitle -> "Options Example", xAxisLabel -> "X Axis", yAxisLabel -> "Y Axis");

/%/mathpiper


/%mathpiper

Histogram({1.0, 1.1, 1.1, 1.2, 1.7, 2.2, 2.5, 4.0, 4.2}, orientation -> "horizontal");

/%/mathpiper


/%mathpiper,title=""

pileESamples := {16.375,16.375,17.125,16,14.375,17.25,16.625,16,17,17.25,17,15.875,16.625,16.125,17.125,16.875,16.375,16.375,16.875,17.125,17,16.75,17.25,17.125,15.375};
pileDSamples := {18.25,19.25,18.25,15.625,17.625,17.5,17.125,17.125,17.5,14.5,17.375,16.875,17.75,18.875,14.875,19.25,18.125,16.25,16.125,16.75,17.25,17.375,17.125,17.5,16.625};

Histogram({pileDSamples, pileESamples}, title -> "Wood Piles", series1Title -> "Pile D", series2Title -> "Pile E");

/%/mathpiper


/%mathpiper,title=""

numberOfRoles := 1000;

dieRolesList := RandomIntegerVector(numberOfRoles,1,6);

Histogram(dieRolesList, binMinimum -> .5, binMaximum -> 6.5, numberOfBins -> 6, title -> "Single Die Rolls", xAxisLabel -> "Number Rolled", yAxisLabel -> "Frequency", seriesTitle -> String(numberOfRoles) : " Roles");

/%/mathpiper


%/mathpiper_docs
*/

