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
import weka.clusterers.FarthestFirst;
import weka.core.Instances;
import weka.core.converters.ConverterUtils;
import weka.core.converters.ConverterUtils.DataSource;


public class farthest {
	
	//int seed,int no_of_clusters
    
    public farthest() {}
    
    //get default parameters for farthest first
    public static HashMap<String, Double> getDefaults() {
    	
    	HashMap<String, Double> hm = new HashMap<String, Double>(5);
    	
    	hm.put("seed", 1.0);
    	hm.put("no_of_clusters", 3.0);
    	
    	return hm;

    }
    
    //set parameters for farthest first
    public static HashMap<String, Double> setParameters(double seed, double clusters) {
    	HashMap<String, Double> hm = new HashMap<String, Double>(5);
    	hm.put("seed", seed);
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
    	
       FarthestFirst algo=  new FarthestFirst();

       algo.setNumClusters(hm.get("no_of_clusters").intValue());
       algo.setSeed(hm.get("seed").intValue());

       
       algo.buildClusterer(dataa);
       ClusterEvaluation eval=new ClusterEvaluation();
       eval.setClusterer(algo);
       eval.evaluateClusterer(dataa);
       double[] p=eval.getClusterAssignments();
       new AttributeSelection_Stats(dataa, eval, "Farthest First", p);  
       DataInstancesStore.remove(filePath);
       dataa=null;
       eval=null;
       algo=null;
       Runtime.getRuntime().gc();
       System.runFinalization ();
       
    }
    
}
