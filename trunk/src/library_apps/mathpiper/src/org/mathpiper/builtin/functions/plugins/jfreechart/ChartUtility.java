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
import java.util.Collections;
import java.util.List;
import java.util.Map;
import org.jfree.data.statistics.HistogramDataset;
import org.mathpiper.builtin.JavaObject;
import org.mathpiper.lisp.Utility;
import org.mathpiper.lisp.cons.ConsPointer;

public class ChartUtility {
    
    public static HistogramDataset listToHistogramDataset(ConsPointer dataListPointer, Map userOptions) throws Exception{

        HistogramDataset dataSet = new HistogramDataset();

        if (Utility.isNestedList(dataListPointer)) {

           List dataSeriesList = new ArrayList();
           List seriesTotalList = new ArrayList();
           dataListPointer.goNext(); //Strip List tag.
           int seriesIndex = 1;
            while(dataListPointer.getCons() != null)
            {
                 double[] dataValues = JavaObject.LispListToJavaDoubleArray((ConsPointer) dataListPointer.car());
                 String seriesTitle = "";
                 if(userOptions.containsKey("series" + seriesIndex + "Title"))
                 {
                     seriesTitle =(String) userOptions.get("series" + seriesIndex + "Title");
                 }
                  dataSeriesList.add(seriesTitle);
                  dataSeriesList.add(dataValues);
                  int index = 0;
                  while(index < dataValues.length)
                  {
                        seriesTotalList.add(dataValues[index++]);
                  }//end while
                 seriesIndex++;
                 dataListPointer.goNext();
            }//end while.


            double min = Double.MAX_VALUE;
            double max = Double.MIN_VALUE;
            int index = 0;
            while(index < seriesTotalList.size())
            {
                Double value = (Double) seriesTotalList.get(index);
                if(value < min) min = value;
                if(value > max) max = value;
                index++;
            }//end while
            min = Math.floor(min);
            max = Math.floor(max);


           int seriesIndex2 = 0;
           while(seriesIndex  > 1)
           {
               String seriesTitle = (String) dataSeriesList.get(seriesIndex2++);
               double[] dataValues = (double[]) dataSeriesList.get(seriesIndex2++);
               dataSet.addSeries(seriesTitle, dataValues, 10, min, max);
               seriesIndex--;
           }//end while.

        } else {//Just a single series.
            double[] dataValues = JavaObject.LispListToJavaDoubleArray(dataListPointer);
            dataSet.addSeries((String) userOptions.get("seriesTitle"), dataValues, 10);
            //argumentsPointer.goNext();
        }//end if/else
        return dataSet;

    }//end method.

}//end class
