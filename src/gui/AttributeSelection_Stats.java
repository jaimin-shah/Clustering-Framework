/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import visualize.graph;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextPane;
import weka.clusterers.ClusterEvaluation;
import weka.core.Instances;

/**
 *
 * @author hp
 */
public class AttributeSelection_Stats {

    private Instances data;
    private ClusterEvaluation eval;
    private JTextPane assign;
    private JFrame frame;
    private JPanel panel;
    private String heading;
    private double []clusterassign;
    public AttributeSelection_Stats(Instances datac,ClusterEvaluation evalc,String algo,double []clusterassignc) {
        frame=new JFrame(algo);
        frame.setSize(500, 500);
        frame.setVisible(true);
        data=datac;
        eval=evalc;
        heading=algo;
        clusterassign=clusterassignc;
        frame.setLayout(new GridLayout(2, 1));
        assign=new JTextPane();
        assign.setEditable(false);
        JScrollPane pane=new JScrollPane(assign);
        frame.add(pane);
        genrate_stats();
        attribute_selection();
    }
    
    void genrate_stats()
    {
      assign.setText("No of clusters"+eval.getNumClusters()+"\n"+"\n"+"\n"+"\n");
      for (int i = 0; i < data.numInstances(); i++) { 
          String s=null;
          if(clusterassign[i]==-1)
          {
              s="NOISE";
          }
          else
          {
              s=String.valueOf((int)clusterassign[i]);
          }
          
          assign.setText(assign.getText()+data.instance(i) + " is in cluster " +s+"\n"+"\n" );   
        
            
        }
      frame.repaint();
      frame.revalidate();
      
    }
    
    private void attribute_selection()
    {
       int n=data.numAttributes();
       String[] attnames = new String[n];
       for(int i=0;i<n;i++)
       {
           attnames[i]=data.attribute(i).name();
       }
        JComboBox x=new JComboBox(attnames);
        JComboBox y=new JComboBox(attnames);
        x.setSelectedIndex(0);
        y.setSelectedIndex(0);
        JButton plot=new JButton("PLOT");
        panel=new JPanel();
        panel.setLayout(new GridLayout(5, 2));
        panel.add(new JLabel("X-axis",JLabel.CENTER));
        panel.add(x);
        panel.add(new JLabel("Y-axis",JLabel.CENTER));
        panel.add(y);
        panel.add(plot);
        panel.add(new JLabel(""));
        panel.add(new JLabel(""));
        panel.add(new JLabel(""));
        panel.add(new JLabel(""));
        panel.add(new JLabel(""));
        frame.add(panel);
        frame.repaint();
        frame.revalidate();
        plot.addActionListener(new ActionListener() {

           @Override
           public void actionPerformed(ActionEvent ae) {
               int graph_x=x.getSelectedIndex();
               int graph_y=y.getSelectedIndex();
               try {
                   new graph(data, eval,graph_x, graph_y,heading);
               } catch (IOException ex) {
                   Logger.getLogger(AttributeSelection_Stats.class.getName()).log(Level.SEVERE, null, ex);
               }
           }
       });
       frame.addWindowListener(new WindowAdapter() {
 

           
           @Override
           public void windowClosing(WindowEvent we) {
               data=null;
               eval=null;
               Runtime.getRuntime().gc();
               
            }
       });
       
    }  
    
    
    
}
