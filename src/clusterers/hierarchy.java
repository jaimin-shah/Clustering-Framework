/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clusterers;

import gui.AttributeSelection_Stats;
import weka.clusterers.ClusterEvaluation;
import weka.clusterers.HierarchicalClusterer;
import weka.core.Instances;
import weka.core.converters.ConverterUtils;


public class hierarchy {

	private String filePath;
        int no_of_clusters;
    public hierarchy(String s,int no_of_clusters) {
        this.no_of_clusters=no_of_clusters;
    	filePath = s;
    	try {
			compute();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
    private void compute() throws Exception {
        // TODO code application logic here
        Instances dataa = ConverterUtils.DataSource.read(filePath); 
      
        HierarchicalClusterer algo = new HierarchicalClusterer();
      
       algo.setNumClusters(no_of_clusters);
       algo.buildClusterer(dataa);
       ClusterEvaluation eval=new ClusterEvaluation();
       eval.setClusterer(algo);
       eval.evaluateClusterer(dataa);
       double[] p=eval.getClusterAssignments();
       new AttributeSelection_Stats(dataa, eval, "Hirtarchical Clustering", p);  
       dataa=null;
       eval=null;
       algo=null;
       Runtime.getRuntime().gc(); 
       System.runFinalization ();
    }
    
}
