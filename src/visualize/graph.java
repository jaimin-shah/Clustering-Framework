/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package visualize;


import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Shape;
import java.io.File;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.util.Iterator;
import javafx.scene.chart.ValueAxis;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.LegendItem;
import org.jfree.chart.axis.SymbolAxis;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYItemRenderer;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;
import org.jfree.ui.VerticalAlignment;
import org.jfree.util.ShapeUtilities;
import weka.clusterers.ClusterEvaluation;
import weka.core.Instances;

/**
 *
 * @author hp
 */
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
             series[(int)assign[i]].add(xdata[i], ydata[i]);
         }
         JFreeChart chart = ChartFactory.createScatterPlot("Cluster Visualization",data.attribute(x).name(),data.attribute(y).name(), dataset);
         
         
         
         
         ChartPanel chartPanel = new ChartPanel(chart);
         Shape cross = ShapeUtilities.createDiagonalCross((float)3,(float) 0.02);
         XYPlot plot = (XYPlot) chart.getPlot();
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
