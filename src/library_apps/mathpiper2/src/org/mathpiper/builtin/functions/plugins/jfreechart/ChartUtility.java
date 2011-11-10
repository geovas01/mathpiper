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


import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.statistics.HistogramDataset;
import org.jfree.data.xy.DefaultXYDataset;
import org.jfree.data.xy.IntervalXYDataset;
import org.jfree.data.xy.XYBarDataset;
import org.mathpiper.builtin.JavaObject;
import org.mathpiper.lisp.Environment;
import org.mathpiper.lisp.LispError;
import org.mathpiper.lisp.Utility;
import org.mathpiper.lisp.cons.Cons;



public class ChartUtility {


    public static Map optionsListToJavaMap(Environment aEnvironment, int aStackTop, Cons argumentsPointer, Map defaultOptions) throws Exception {

        Map userOptions = Utility.optionsListToJavaMap(aEnvironment, aStackTop, argumentsPointer, defaultOptions);


        if (userOptions.containsKey("orientation")) {
                    if (userOptions.get("orientation").equals("vertical")) {
                        userOptions.put("orientation", PlotOrientation.VERTICAL);
                    } else if (userOptions.get("orientation").equals("horizontal")) {
                        userOptions.put("orientation", PlotOrientation.HORIZONTAL);
                    } else {
                        userOptions.put("orientation", PlotOrientation.VERTICAL);
                    }//end if/else.
                }

        return userOptions;
    }//end method.



    public static HistogramDataset listToHistogramDataset(Environment aEnvironment, int aStackTop, Cons dataListPointer, Map userOptions) throws Exception {

        HistogramDataset dataSet = new HistogramDataset();

        if (Utility.isNestedList(aEnvironment, aStackTop, dataListPointer)) {

            List dataSeriesList = new ArrayList();
            dataListPointer = dataListPointer.cdr(); //Strip List tag.
            int seriesIndex = 1;
            int dataSize = 0;
            while (dataListPointer != null) {
                double[] dataValues = JavaObject.lispListToJavaDoubleArray(aEnvironment, aStackTop, (Cons) dataListPointer.car());
                if (dataValues.length > dataSize)
                    dataSize = dataValues.length;
                String seriesTitle = "";
                if (userOptions.containsKey("series" + seriesIndex + "Title")) {
                    seriesTitle = (String) userOptions.get("series" + seriesIndex + "Title");
                }
                dataSeriesList.add(seriesTitle);
                dataSeriesList.add(dataValues);
                seriesIndex++;
                dataListPointer =  dataListPointer.cdr();
            }//end while.

            int numberOfBins = Math.max((int) Math.sqrt(dataSize), 5);

            if (userOptions.get("numberOfBins") != null) {
                numberOfBins = (int) ((Double)userOptions.get("numberOfBins")).doubleValue();
            }

            Double binMinimum = (Double) userOptions.get("binMinimum");
            Double binMaximum = (Double) userOptions.get("binMaximum");

            int seriesIndex2 = 0;
            while (seriesIndex > 1) {
                String seriesTitle = (String) dataSeriesList.get(seriesIndex2++);
                double[] dataValues = (double[]) dataSeriesList.get(seriesIndex2++);
                if (binMinimum != null && binMaximum != null)
                    dataSet.addSeries(seriesTitle, dataValues, numberOfBins, binMinimum, binMaximum);
                else
                    dataSet.addSeries(seriesTitle, dataValues, numberOfBins);
                seriesIndex--;
            }//end while.

        } else {//Just a single series.
            double[] dataValues = JavaObject.lispListToJavaDoubleArray(aEnvironment, aStackTop, dataListPointer);

            int numberOfBins = Math.max((int) Math.sqrt(dataValues.length),5);
            Double numberOfBinsDouble = (Double) userOptions.get("numberOfBins");
            if (numberOfBinsDouble != null) {
                numberOfBins = (int) numberOfBinsDouble.doubleValue();
            }//end if.


            Double binMinimum = (Double) userOptions.get("binMinimum");
            Double binMaximum = (Double) userOptions.get("binMaximum");

            if (binMinimum != null && binMaximum != null) {
                dataSet.addSeries((String) userOptions.get("seriesTitle"), dataValues, numberOfBins, binMinimum, binMaximum);
            } else {
                dataSet.addSeries((String) userOptions.get("seriesTitle"), dataValues, numberOfBins);
            }
            //argumentsPointer.goNext();
        }//end if/else
        return dataSet;

    }//end method.


    public static XYBarDataset listToCumulativeDataset(Environment aEnvironment, int aStackTop, Cons dataListPointer, Map userOptions) throws Exception {

        if(Utility.isNestedList(aEnvironment, aStackTop, dataListPointer)) LispError.throwError(aEnvironment, aStackTop, LispError.INVALID_ARGUMENT, "");


        int numberOfBins = 15;

        Double numberOfBinsDouble = (Double) userOptions.get("numberOfBins");
        if (numberOfBinsDouble != null) {
            numberOfBins = (int) numberOfBinsDouble.doubleValue();
        }//end if.


        double[] dataValues = JavaObject.lispListToJavaDoubleArray(aEnvironment, aStackTop, dataListPointer);


        Arrays.sort(dataValues);

        double minimumValue = Math.floor(dataValues[0]);
        double maximumValue = Math.floor(dataValues[dataValues.length - 1]);


        double[] cumulativeValues = new double[numberOfBins];
        double[] binLabels = new double[numberOfBins];

        double step = (maximumValue - minimumValue) / numberOfBins;

        int binIndex = 0;

        double binStartValue = minimumValue;

        int valuesInBinCount = 0;

        int index = 0;

        double binEndValue = 0;



        for (binEndValue = minimumValue + step; Math.floor(binEndValue) <= maximumValue; binEndValue = binEndValue + step, binStartValue = binStartValue + step ){


            while ( index != dataValues.length && (Math.floor(dataValues[index]) <= Math.floor(binEndValue))) {
                valuesInBinCount++;
                index++;
            }//end for.

            double binAverageValue = (binEndValue - binStartValue) / 2;

            double binLabelValue = binStartValue + binAverageValue;

            //System.out.println("bin start: " + binStartValue + "   bin end: " + binEndValue + "   bin label: " + binLabelValue);

            binLabels[binIndex] = binLabelValue;

            cumulativeValues[binIndex] = valuesInBinCount;


            binIndex++;

        }//end for.

        double[][] combinedValues = new double[][]{binLabels, cumulativeValues};


        //Double binMinimum = (Double) userOptions.get("binMinimum");
        //Double binMaximum = (Double) userOptions.get("binMaximum");
        String seriesTitle = (String) userOptions.get("seriesTitle");


        DefaultXYDataset dataSet = new DefaultXYDataset();
        dataSet.addSeries(seriesTitle, combinedValues);

        //argumentsPointer.goNext();

        return new XYBarDataset(dataSet, step);

    }//end method.



    public static DefaultXYDataset listToXYDataset(Environment aEnvironment, int aStackTop, Cons dataListPointer, Map userOptions) throws Exception {

        if(!Utility.isNestedList(aEnvironment, aStackTop, dataListPointer)) LispError.throwError(aEnvironment, aStackTop, LispError.INVALID_ARGUMENT, "");

        DefaultXYDataset dataSet = new DefaultXYDataset();

        dataListPointer = dataListPointer.cdr(); //Strip List tag.
        int seriesIndex = 1;
        while (dataListPointer != null) {
            double[] dataXValues = JavaObject.lispListToJavaDoubleArray(aEnvironment, aStackTop, (Cons) dataListPointer.car());
            dataListPointer = dataListPointer.cdr();
            double[] dataYValues = JavaObject.lispListToJavaDoubleArray(aEnvironment, aStackTop, (Cons) dataListPointer.car());

            String seriesTitle = "series" + seriesIndex;
            if (userOptions.containsKey("series" + seriesIndex + "Title")) {
                seriesTitle = (String) userOptions.get("series" + seriesIndex + "Title");
            }

            if(dataXValues.length != dataYValues.length) LispError.throwError(aEnvironment, aStackTop, LispError.LIST_LENGTHS_MUST_BE_EQUAL, "");

            dataSet.addSeries(seriesTitle, new double[][]{dataXValues, dataYValues});

            seriesIndex++;
            dataListPointer = dataListPointer.cdr();
        }//end while.


        return dataSet;

    }//end method.



    public static IntervalXYDataset listToIntervalXYDataset(Environment aEnvironment, int aStackTop, Cons dataListPointer, Map userOptions) throws Exception {

        DefaultXYDataset xYDataset = listToXYDataset(aEnvironment, aStackTop, dataListPointer, userOptions);

        int seriesCount = xYDataset.getSeriesCount();
        if(seriesCount == 0) LispError.throwError(aEnvironment, aStackTop, LispError.INVALID_ARGUMENT, "");

        //int seriesItemCount =  xYDataset.getItemCount(0);

        //double lowXValue = xYDataset.getXValue(0, 0);

        //double highXValue = xYDataset.getXValue(0, seriesItemCount-1);

        double barWidth = xYDataset.getXValue(0, 1) - xYDataset.getXValue(0, 0);

        return new XYBarDataset(xYDataset, barWidth);
    }//end method.


}//end class

