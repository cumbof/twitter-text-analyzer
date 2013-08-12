/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package it.uniroma3.dia.tta.utility;

import it.uniroma3.dia.tta.TwitterTextAnalyzer;
import java.awt.Color;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYItemRenderer;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.data.xy.IntervalXYDataset;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

/**
 *
 * @author Fabio Cumbo, Alessandro Giacomini
 * @application TwitterTextAnalyzer
 * @version 1.0
 * @organization Università degli studi Roma Tre - Dipartimento di Informatica e Automazione
 * 
 */
public class PlotUtils {
    
    private final int _WORD_FREQUENCIES_DISTRIBUTION_CODE_ = 0;
    private final int _TAGS_COMPARISON_DISTRIBUTION_CODE_ = 1;
    
    public PlotUtils() {}
    
    public void plotDistribution(String _TITLE_, String _X_, String _Y_, int _PANEL_CODE_, Object data) {
        if (_PANEL_CODE_ == _WORD_FREQUENCIES_DISTRIBUTION_CODE_) {
            final XYDataset dataset = createDataset_WF(data);
            final JFreeChart chart = createChart_WF(dataset, _X_, _Y_, _TITLE_);
            final ChartPanel chartPanel = new ChartPanel(chart);
            XYItemRenderer renderer = chartPanel.getChart().getXYPlot().getRenderer();
            renderer.setBaseSeriesVisibleInLegend(false);
            chartPanel.setPreferredSize(new java.awt.Dimension(350, 190));
            TwitterTextAnalyzer.getApp().getWordFrequenciesDistribution_frame().setContentPane(chartPanel); 
        }
        else if (_PANEL_CODE_ == _TAGS_COMPARISON_DISTRIBUTION_CODE_) {
            IntervalXYDataset dataset = createDataset_TC(data);
            JFreeChart chart = createChart_TC(dataset, _X_, _Y_, _TITLE_);
            ChartPanel chartPanel = new ChartPanel(chart);
            chartPanel.setPreferredSize(new java.awt.Dimension(860, 220));
            chartPanel.setMouseZoomable(true, false);
            TwitterTextAnalyzer.getApp().getTagsComparisonDistribution_frame().setContentPane(chartPanel); 
        }
    }
    
    private XYDataset createDataset_WF(Object data) {
	ArrayList<Double> castingData = null;
        try {
            castingData = (ArrayList<Double>)data;
        } catch (ClassCastException e) {
            throw new UnsupportedOperationException("Error during casting Object to HashMap...");
        }
        
        XYSeries series = new XYSeries("xySeries");
        
        int count = 1;
        for (double value: castingData) {
            series.add(count, value);
            count++;
        }
        
        XYSeriesCollection dataset = new XYSeriesCollection();
        dataset.addSeries(series);      
        return dataset;
    }

    private JFreeChart createChart_WF(XYDataset dataset, String xTitle, String yTitle, String title) {
        JFreeChart chart = ChartFactory.createXYLineChart(
            title,      // chart title
	    xTitle,                      // x axis label
	    yTitle,                      // y axis label
	    dataset,                  // data
	    PlotOrientation.VERTICAL,
	    true,                     // include legend
	    true,                     // tooltips
	    false                     // urls
	);
	
        // NOW DO SOME OPTIONAL CUSTOMISATION OF THE CHART...
	chart.setBackgroundPaint(Color.white);
	
	//    final StandardLegend legend = (StandardLegend) chart.getLegend();
	//      legend.setDisplaySeriesShapes(true);
	    
	// get a reference to the plot for further customisation...
	XYPlot plot = chart.getXYPlot();
	plot.setBackgroundPaint(Color.lightGray);
	//    plot.setAxisOffset(new Spacer(Spacer.ABSOLUTE, 5.0, 5.0, 5.0, 5.0));
	plot.setDomainGridlinePaint(Color.white);
	plot.setRangeGridlinePaint(Color.white);
	    
	XYLineAndShapeRenderer renderer = new XYLineAndShapeRenderer();
	renderer.setSeriesLinesVisible(0, true);
	renderer.setSeriesShapesVisible(1, false);
	plot.setRenderer(renderer);
	
	// change the auto tick unit selection to integer units only...
	NumberAxis rangeAxis = (NumberAxis) plot.getRangeAxis();
	rangeAxis.setStandardTickUnits(NumberAxis.createIntegerTickUnits());
	// OPTIONAL CUSTOMISATION COMPLETED.
	            
	return chart;
    }
    

    private IntervalXYDataset createDataset_TC(Object data) {
        Object[] castingData;
        HashMap<String, Double> positiveTwittPOSTag = null;
        HashMap<String, Double> negativeTwittPOSTag = null;
        try {
            castingData = (Object[])data;
            positiveTwittPOSTag = (HashMap<String, Double>)castingData[0];
            negativeTwittPOSTag = (HashMap<String, Double>)castingData[1];
        } catch (ClassCastException e) {
            throw new UnsupportedOperationException("Error during casting Object to HashMap...");
        }
        
        final XYSeries positiveSeries = new XYSeries("positive");
        final XYSeries negativeSeries = new XYSeries("negative");
        double[] positiveData = getData(positiveTwittPOSTag, true);
        double[] negativeData = getData(negativeTwittPOSTag, false);
        int countPositive = 1;
        int countNegative = -1;
        for (double val: positiveData) {
            positiveSeries.add(countPositive, val);
            countPositive++;
        }
        for (double val: negativeData) {
            negativeSeries.add(countNegative, val);
            countNegative--;
        }
        
        final XYSeriesCollection dataset = new XYSeriesCollection(positiveSeries);
        dataset.addSeries(negativeSeries);
        return dataset;
    }

    private JFreeChart createChart_TC(IntervalXYDataset dataset, String _X_, String _Y_, String _TITLE_) {
        JFreeChart chart = ChartFactory.createHistogram(
           _TITLE_, 
           _X_, 
           _Y_, 
           dataset, 
           PlotOrientation.VERTICAL, 
           true, 
           false, 
           false
        );
        chart.getXYPlot().setForegroundAlpha(0.75f);
        return chart;
    }
    
    private double[] getData(HashMap<String, Double> data, boolean positive) {
        ArrayList<Double> values = new ArrayList<>(data.values());
        Collections.sort(values);
        if (!positive)
            Collections.reverse(values);
        double[] result = new double[values.size()];
        int index = 0;
        for (Double val: values) {
            result[index] = val;
            index++;
        }
        return result;
    }
    
}
