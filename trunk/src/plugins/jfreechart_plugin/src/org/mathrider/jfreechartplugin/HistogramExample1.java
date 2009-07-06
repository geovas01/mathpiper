
package org.mathrider.jfreechartplugin;

import java.io.IOException;
import java.util.Random;

import javax.swing.JPanel;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.StandardXYBarPainter;
import org.jfree.chart.renderer.xy.XYBarRenderer;
import org.jfree.data.statistics.HistogramDataset;
import org.jfree.data.xy.IntervalXYDataset;

public class HistogramExample1 {




	private static IntervalXYDataset createDataset() {
		HistogramDataset dataset = new HistogramDataset();
		String samplesString = "16.375,16.375,17.125,16,14.375,17.25,16.625,16,17,17.25,17,15.875,16.625,16.125,17.125,16.875,16.375,16.375,16.875,17.125,17,16.75,17.25,17.125,15.375";
		String[] samples = samplesString.split(",");
		double[] values = new double[samples.length];
		int i = 0;
		for (String sample:samples) {
			values[i] = Float.parseFloat(sample);
			i++;
		}
		dataset.addSeries("Pile E", values, 20, 14.0, 20.0);



		samplesString = "18.25,19.25,18.25,15.625,17.625,17.5,17.125,17.125,17.5,14.5,17.375,16.875,17.75,18.875,14.875,19.25,18.125,16.25,16.125,16.75,17.25,17.375,17.125,17.5,16.625";
		samples = samplesString.split(",");
		values = new double[samples.length];
		i = 0;
		for (String sample:samples) {
			values[i] = Float.parseFloat(sample);
			i++;
		}
		dataset.addSeries("Pile D", values, 20, 14.0, 20.0);

		return dataset;
	}


	private static JFreeChart createChart(IntervalXYDataset dataset) {
		JFreeChart chart = ChartFactory.createHistogram(
		                           "Wood Pile Samples",
		                           null,
		                           null,
		                           dataset,
		                           PlotOrientation.VERTICAL,
		                           true,
		                           true,
		                           false);
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
		return chart;
	}


	public static JPanel createDemoPanel() {
		JFreeChart chart = createChart(createDataset());
		ChartPanel panel = new ChartPanel(chart);
		return panel;
	}



}//end class.
