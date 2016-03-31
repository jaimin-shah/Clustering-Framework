/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clusterers;

import java.util.HashMap;

import gui.AttributeSelection_Stats;
import weka.clusterers.ClusterEvaluation;
import weka.clusterers.HierarchicalClusterer;
import weka.core.Instances;
import weka.core.converters.ConverterUtils;


public class hierarchy {

	
	//int no_of_clusters
    public hierarchy() {}
    
    public static HashMap<String, Double> getDefaults() {
    	HashMap<String, Double> hm = new HashMap<String, Double>();
    	
    	hm.put("no_of_clusters", 5.0);
    	
    	return hm;
    }
    
    public void compute(String filePath, HashMap<String, Double> hm) throws Exception {
        // TODO code application logic here
        Instances dataa = ConverterUtils.DataSource.read(filePath); 
      
        HierarchicalClusterer algo = new HierarchicalClusterer();
      
       algo.setNumClusters(hm.get("no_of_clusters").intValue());
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
