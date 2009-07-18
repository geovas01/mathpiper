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
import java.util.List;
import java.util.Map;
import org.jfree.data.statistics.HistogramDataset;
import org.jfree.data.xy.DefaultXYDataset;
import org.jfree.data.xy.XYDataset;
import org.mathpiper.builtin.JavaObject;
import org.mathpiper.lisp.LispError;
import org.mathpiper.lisp.Utility;
import org.mathpiper.lisp.cons.ConsPointer;

public class ChartUtility {

    public static HistogramDataset listToHistogramDataset(ConsPointer dataListPointer, Map userOptions) throws Exception {

        HistogramDataset dataSet = new HistogramDataset();

        if (Utility.isNestedList(dataListPointer)) {

            List dataSeriesList = new ArrayList();
            List seriesTotalList = new ArrayList();
            dataListPointer.goNext(); //Strip List tag.
            int seriesIndex = 1;
            while (dataListPointer.getCons() != null) {
                double[] dataValues = JavaObject.LispListToJavaDoubleArray((ConsPointer) dataListPointer.car());
                String seriesTitle = "";
                if (userOptions.containsKey("series" + seriesIndex + "Title")) {
                    seriesTitle = (String) userOptions.get("series" + seriesIndex + "Title");
                }
                dataSeriesList.add(seriesTitle);
                dataSeriesList.add(dataValues);
                int index = 0;
                while (index < dataValues.length) {
                    seriesTotalList.add(dataValues[index++]);
                }//end while
                seriesIndex++;
                dataListPointer.goNext();
            }//end while.

            double min = Double.MAX_VALUE;
            double max = Double.MIN_VALUE;
            int index = 0;
            while (index < seriesTotalList.size()) {
                Double value = (Double) seriesTotalList.get(index);
                if (value < min) {
                    min = value;
                }
                if (value > max) {
                    max = value;
                }
                index++;
            }//end while
            min = Math.floor(min) - .5;
            max = Math.floor(max) + .5;


            int seriesIndex2 = 0;
            while (seriesIndex > 1) {
                String seriesTitle = (String) dataSeriesList.get(seriesIndex2++);
                double[] dataValues = (double[]) dataSeriesList.get(seriesIndex2++);
                dataSet.addSeries(seriesTitle, dataValues, 10, min, max);
                seriesIndex--;
            }//end while.

        } else {//Just a single series.
            int numberOfBins = 10;
            Double numberOfBinsDouble = (Double) userOptions.get("numberOfBins");
            if(numberOfBinsDouble != null)
            {
                numberOfBins = (int) numberOfBinsDouble.doubleValue();
            }//end if.

            Double binMinimum = (Double) userOptions.get("binMinimum");
            Double binMaximum = (Double) userOptions.get("binMaximum");

            double[] dataValues = JavaObject.LispListToJavaDoubleArray(dataListPointer);
            if(binMinimum != null && binMaximum != null)
            {
                dataSet.addSeries((String) userOptions.get("seriesTitle"), dataValues, numberOfBins, binMinimum, binMaximum);
            }
            else
            {
                dataSet.addSeries((String) userOptions.get("seriesTitle"), dataValues, numberOfBins);
            }
            //argumentsPointer.goNext();
        }//end if/else
        return dataSet;

    }//end method.



    public static XYDataset listToXYDataset(ConsPointer dataListPointer, Map userOptions) throws Exception {

        LispError.check(Utility.isNestedList(dataListPointer), LispError.INVALID_ARGUMENT);

        DefaultXYDataset dataSet = new DefaultXYDataset();

        dataListPointer.goNext(); //Strip List tag.
        int seriesIndex = 1;
        while (dataListPointer.getCons() != null) {
            double[] dataXValues = JavaObject.LispListToJavaDoubleArray((ConsPointer) dataListPointer.car());
            dataListPointer.goNext();
            double[] dataYValues = JavaObject.LispListToJavaDoubleArray((ConsPointer) dataListPointer.car());

            String seriesTitle = "";
            if (userOptions.containsKey("series" + seriesIndex + "Title")) {
                seriesTitle = (String) userOptions.get("series" + seriesIndex + "Title");
            }

            LispError.check(dataXValues.length == dataYValues.length, LispError.LIST_LENGTHS_MUST_BE_EQUAL);

            dataSet.addSeries(seriesTitle, new double[][]{dataXValues, dataYValues});

            seriesIndex++;
            dataListPointer.goNext();
        }//end while.




        return dataSet;

    }//end method.
}//end class

