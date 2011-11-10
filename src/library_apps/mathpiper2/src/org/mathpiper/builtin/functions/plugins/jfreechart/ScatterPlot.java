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
import org.jfree.data.xy.XYDataset;
import org.mathpiper.lisp.cons.BuiltinObjectCons;
import org.mathpiper.lisp.cons.Cons;


public class ScatterPlot extends BuiltinFunction {

    private Map defaultOptions;

    public void plugIn(Environment aEnvironment)  throws Exception
    {
        aEnvironment.getBuiltinFunctions().setAssociation(
                new BuiltinFunctionEvaluator(this, 1, BuiltinFunctionEvaluator.Variable | BuiltinFunctionEvaluator.Function),
                "ScatterPlot");

        defaultOptions = new HashMap();
        defaultOptions.put("title", null);
        defaultOptions.put("xAxisLabel", null);
        defaultOptions.put("yAxisLabel", null);
        defaultOptions.put("seriesTitle", "");
        defaultOptions.put("orientation", PlotOrientation.VERTICAL);
        defaultOptions.put("legend", true);
        defaultOptions.put("toolTips", true);
        defaultOptions.put("domainCrosshair", true);
        defaultOptions.put("rangeCrosshair", true);

    }//end method.

    //private StandardFileOutputStream out = new StandardFileOutputStream(System.out);
    public void evaluate(Environment aEnvironment, int aStackTop) throws Exception {

        Cons argumentsPointer = getArgumentPointer(aEnvironment, aStackTop, 1);

        if(! Utility.isSublist(argumentsPointer)) LispError.throwError(aEnvironment, aStackTop, LispError.INVALID_ARGUMENT, "");

        argumentsPointer = (Cons) argumentsPointer.car(); //Go to sub list.

        argumentsPointer = argumentsPointer.cdr(); //Strip List tag.

        if(! Utility.isList(argumentsPointer)) LispError.throwError(aEnvironment, aStackTop, LispError.NOT_A_LIST, "");

        Cons dataListPointer = (Cons) argumentsPointer.car(); //Grab the first member of the list.

        Cons optionsPointer = argumentsPointer.cdr();

        Map userOptions = ChartUtility.optionsListToJavaMap(aEnvironment, aStackTop, optionsPointer, defaultOptions);

        XYDataset dataSet = ChartUtility.listToXYDataset(aEnvironment, aStackTop, dataListPointer, userOptions);

        JFreeChart chart = ChartFactory.createScatterPlot(
                (String) userOptions.get("title"), //title.
                (String) userOptions.get("xAxisLabel"), //x axis label.
                (String) userOptions.get("yAxisLabel"), //y axis label.
                dataSet, //
                (PlotOrientation) userOptions.get("orientation"), //orientation.
                ((Boolean) userOptions.get("legend")).booleanValue(), //legend.
                ((Boolean) userOptions.get("toolTips")).booleanValue(),//tool tips.
                false);//urls.

        XYPlot plot = (XYPlot) chart.getPlot();
        plot.setDomainCrosshairVisible(((Boolean) userOptions.get("domainCrosshair")).booleanValue());
        plot.setDomainCrosshairLockedOnData(true);
        plot.setRangeCrosshairVisible(((Boolean) userOptions.get("rangeCrosshair")).booleanValue());
        plot.setRangeCrosshairLockedOnData(true);
        plot.setDomainZeroBaselineVisible(true);
        plot.setRangeZeroBaselineVisible(true);
        plot.setDomainPannable(true);
        plot.setRangePannable(true);
        NumberAxis domainAxis = (NumberAxis) plot.getDomainAxis();
        domainAxis.setAutoRangeIncludesZero(false);

// create and display a frame...  Import("org/mathpiper/builtin/functions/plugins/jfreechart/")
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
%mathpiper_docs,name="ScatterPlot",categories="User Functions;Visualization"
*CMD ScatterPlot --- displays a graphic scatter plot
*CORE
*CALL
	ScatterPlot({domain_list, range_list}, option, option, option...)
    ScatterPlot({domain_list_1, range_list_1, domain_list_2, range_list_2,...}, option, option, option...)

*PARMS

{domain_list} -- a list which contains the domain values

{range_list} -- a list which contains the range values that go with the domain_list values

{title} -- the title of the scatter plot

{xAxisLabel} -- the label for the x axis

{yAxisLabel} -- the label for the y axis

{seriesTitle} -- the title for a single data series

{series<x>Title} -- the title for more than one series. <x> can be 1, 2, 3, etc.

*DESC

Creates either a single scatter plot or multiple scatter plots on the same plot. Options are entered using the -> operator.
For example, here is how to set the {title} option: {title -> "Example Title"}.

*E.G.
/%mathpiper,title=""

claim := 1 .. 40;
days := {48,41,35,36,37,26,36,46,35,47,35,34,36,42,43,36,56,32,46,30,37,43,17,26,28,27,45,33,22,27,16,22,33,30,24,23,22,30,31,17};
ScatterPlot({claim, days}, title -> "Scatter Plot", series1Title -> "Series 1", xAxisLabel -> "Claim", yAxisLabel -> "Days");

/%/mathpiper


/%mathpiper,title=""

claim := 1 .. 40;
days1 := {48,41,35,36,37,26,36,46,35,47,35,34,36,42,43,36,56,32,46,30,37,43,17,26,28,27,45,33,22,27,16,22,33,30,24,23,22,30,31,17};
days2 := RandomIntegerVector(Length(claim), 20, 50);
ScatterPlot({claim, days1, claim, days2}, title -> "Scatter Plot", series1Title -> "Series 1", series2Title -> "Series 2", xAxisLabel -> "Claim", yAxisLabel -> "Days");

/%/mathpiper


%/mathpiper_docs
*/

