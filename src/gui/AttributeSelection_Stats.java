/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import java.awt.GridLayout;
import javax.swing.JFrame;
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
    private double []clusterassign;
    public AttributeSelection_Stats(Instances datac,ClusterEvaluation evalc,String algo,double []clusterassignc) {
        frame=new JFrame(algo);
        frame.setSize(500, 500);
        frame.setVisible(true);
        data=datac;
        eval=evalc;
        clusterassign=clusterassignc;
        frame.setLayout(new GridLayout(2, 1));
        assign=new JTextPane();
        assign.setEditable(false);
        JScrollPane pane=new JScrollPane(assign);
        frame.add(pane);
        genrate_stats();
        
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
      
    
    
    
}
