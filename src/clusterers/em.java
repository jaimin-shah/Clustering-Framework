/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package clusterers;

import gui.AttributeSelection_Stats;
import java.io.File;
import java.util.HashMap;

import core.DataInstancesStore;
import weka.clusterers.*;
import weka.clusterers.EM;
import weka.core.Instance;
import weka.core.Instances;
import weka.core.converters.CSVLoader;
import weka.core.converters.ConverterUtils.DataSource;


public class em  {
	
	//int maxiter,int seed,int no_of_clusters,double minstddev    
    public em() {}
    
    //get default parameters for expectation maximization
    public static HashMap<String, Double> getDefaults() {
    	HashMap<String, Double> hm = new HashMap<String, Double>(5);
    	
    	hm.put("maxiter", 100.0);
    	hm.put("seed", 100.0);
    	hm.put("no_of_clusters", -1.0);
    	hm.put("minstddev", 0.000001);
    	
    	return hm;
    }
    
    //set parametrs for expectation maximization
    public static HashMap<String, Double> setParameters(double iters, double seed, double clusters, double minstddev) {
    	HashMap<String, Double> hm = new HashMap<String, Double>(5);
    	hm.put("maxiter", iters);
    	hm.put("seed", seed);
    	hm.put("no_of_clusters", clusters);
    	hm.put("minstddev", minstddev);
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
		compute(dataa, hm);
    }
    
    //run algo by providing data instances
    public void compute(Instances dataa, HashMap<String, Double> hm) throws Exception {
    	
       EM algo=new EM();
       

       algo.setMinStdDev(hm.get("minstddev"));
       algo.setMaxIterations(hm.get("maxiter").intValue());
       algo.setSeed(hm.get("seed").intValue());
       algo.setNumClusters(hm.get("no_of_clusters").intValue());
       algo.buildClusterer(dataa);
       
       ClusterEvaluation eval = new ClusterEvaluation();
       eval.setClusterer(algo);
       eval.evaluateClusterer(dataa);
       double[] p=eval.getClusterAssignments();
       new AttributeSelection_Stats(dataa, eval, "EM", p);
       dataa=null;
       eval=null;
       algo=null;
       Runtime.getRuntime().gc();
       System.runFinalization ();
       
       
    }

}
