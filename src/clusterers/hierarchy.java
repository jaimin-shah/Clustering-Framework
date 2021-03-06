/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clusterers;

import java.util.HashMap;

import core.DataInstancesStore;
import gui.AttributeSelection_Stats;
import weka.clusterers.ClusterEvaluation;
import weka.clusterers.HierarchicalClusterer;
import weka.core.Instances;
import weka.core.converters.ConverterUtils;
import weka.core.converters.ConverterUtils.DataSource;


public class hierarchy {
	
	//int no_of_clusters
    public hierarchy() {}
    
    //get default parameters for hierarchical clusterer
    public static HashMap<String, Double> getDefaults() {
    	HashMap<String, Double> hm = new HashMap<String, Double>(5);
    	
    	hm.put("no_of_clusters", 2.0);
    	
    	return hm;

    }
    
    //set parameters for hierarchical clusterer
    public static HashMap<String, Double> setParameters(double clusters) {
    	HashMap<String, Double> hm = new HashMap<String, Double>(5);
    	hm.put("no_of_clusters", clusters);
    	return hm;
    }
    
  //run algo by providing file path
    public void compute(String filePath, HashMap<String, Double> hm) throws Exception {
        // TODO code application logic here
    	Instances dataa = null;
		if(DataInstancesStore.hasDataInstance(filePath)) {
			dataa = DataInstancesStore.getDataInstanceOf(filePath);
		}
		else {
			dataa = DataInstancesStore.computeDataInstance(filePath);
		}
		compute(dataa, hm,filePath);
    }
    
    //run algo by providing data instances
    public void compute(Instances dataa, HashMap<String, Double> hm,String filePath) throws Exception {
    	
       HierarchicalClusterer algo = new HierarchicalClusterer();
      
       algo.setNumClusters(hm.get("no_of_clusters").intValue());

       algo.buildClusterer(dataa);
       ClusterEvaluation eval=new ClusterEvaluation();
       eval.setClusterer(algo);
       eval.evaluateClusterer(dataa);
       double[] p=eval.getClusterAssignments();
       new AttributeSelection_Stats(dataa, eval, "Hirtarchical Clustering", p);  
       DataInstancesStore.remove(filePath);
       dataa=null;
       eval=null;
       algo=null;
       Runtime.getRuntime().gc(); 
       System.runFinalization ();
    }
    
}
