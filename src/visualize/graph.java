/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package visualize;


import java.awt.Color;
import java.awt.GridLayout;
import java.awt.Shape;
import java.io.IOException;
import java.util.Iterator;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.LegendItem;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYItemRenderer;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;
import org.jfree.util.ShapeUtilities;

import weka.clusterers.ClusterEvaluation;
import weka.core.Attribute;
import weka.core.Instances;

public class graph {
     public graph(Instances data,ClusterEvaluation eval,int x,int y) throws IOException
    {
        JScrollPane pane = null;
        JFrame frame=new JFrame();
        XYSeriesCollection dataset = new XYSeriesCollection();
        int num=eval.getNumClusters();
        XYSeries series[] = new XYSeries[num];
        for(int i=0;i<num;i++)
        {
            series[i]=new XYSeries(i+1);
            dataset.addSeries(series[i]);
        }
         double[] xdata=data.attributeToDoubleArray(x);
         double[] ydata=data.attributeToDoubleArray(y);
         double[] assign=eval.getClusterAssignments();
         for(int i=0;i<xdata.length;i++)
         {
             try{
                series[(int)assign[i]].add(xdata[i], ydata[i]);
             }
             catch(Exception e)
             {
                 
             }
             
         }
         JFreeChart chart = ChartFactory.createScatterPlot("Cluster Visualization",data.attribute(x).name(),data.attribute(y).name(), dataset);
         
         
         
         
         ChartPanel chartPanel = new ChartPanel(chart);
         Shape cross = ShapeUtilities.createDiagonalCross((float)3,(float) 0.02);
         XYPlot plot = (XYPlot) chart.getPlot();
         if(data.attribute(x).isNominal())
         {
             Attribute att=data.attribute(x);
             int length=(data.attribute(x).numValues());
             String[] s=new String[length];
             for(int i=0;i<s.length;i++)
             {
                 s[i]=att.value(i);
             }
             SymbolAxis sa = new SymbolAxis(att.name(),s);
             plot.setDomainAxis(sa);
         }
         if(data.attribute(y).isNominal())
         {
             Attribute att=data.attribute(y);
             int length=(data.attribute(y).numValues());
             String[] s=new String[length];
             for(int i=0;i<s.length;i++)
             {
                 s[i]=att.value(i);
             }
             SymbolAxis sa = new SymbolAxis(att.name(),s);
             plot.setRangeAxis(sa);
         }
         
         //SymbolAxis sa = new SymbolAxis("AxisLabel",new String[]{"Category1","Category2","Category3"});
         //plot.setDomainAxis(sa);
         XYItemRenderer renderer = plot.getRenderer();
         if(num>100)
         {
            frame.setLayout(new GridLayout(2,1));
            JPanel panel = new JPanel(new GridLayout(0, 15));
            Iterator iterator = plot.getLegendItems().iterator();
            chart.removeLegend();
            while (iterator.hasNext()) {
            LegendItem item = (LegendItem) iterator.next();
            JLabel label = new JLabel(item.getLabel());
            label.setIcon(new ColorIcon( 8,8, (Color) item.getFillPaint()));
            panel.add(label);
            }
         pane=new JScrollPane(panel);
         
        }
         
        
          
         for(int i=0;i<num;i++)
        {
           renderer.setSeriesShape(i, cross);
        }
         
         org.jfree.chart.axis.ValueAxis yAxis = plot.getRangeAxis();
        
         double min=yAxis.getLowerBound();
         double range=yAxis.getUpperBound()-yAxis.getLowerBound();
         if(min==0)
         {
            //System.out.println(range);
            yAxis.setLowerBound(-range*0.1);
         }
         else if(min<0)
         {
            yAxis.setLowerBound(min+range*0.1);
         }
         else
         {
            yAxis.setLowerBound(min-range*0.1);
         }
        
         
         //chartPanel.setPreferredSize(new Dimension(400, 400));
         //chartPanel.setBounds(100, 100, 400, 400);
         chartPanel.setVisible(true);
         frame.setSize(500,500);
         frame.setVisible(true);
          
         if(num>100)
         {
             frame.add(chartPanel);
             frame.add(pane);
         }
         else
         {
             frame.add(chartPanel);
         }
         
    }
}
