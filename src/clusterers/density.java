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
import weka.clusterers.MakeDensityBasedClusterer;
import weka.core.Instances;
import weka.core.converters.ConverterUtils;
import weka.core.converters.ConverterUtils.DataSource;


public class density {

    //double minstdev
    public density() {}
    
    //get default parameters for density clusterer
    public static HashMap<String, Double> getDefaults() {
    	HashMap<String, Double> hm = new HashMap<String, Double>();
    	
    	hm.put("minstdev", 10.0);
    	
    	return hm;
    }
    
    //set parameters for density clusterer
    public static HashMap<String, Double> setParameters(double minstddev) {
    	HashMap<String, Double> hm = new HashMap<String, Double>(5);
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
      
       MakeDensityBasedClusterer algo = new MakeDensityBasedClusterer();

       algo.setMinStdDev(hm.get("minstdev"));
       
       algo.buildClusterer(dataa);
       
       ClusterEvaluation eval=new ClusterEvaluation();
       eval.setClusterer(algo);
       eval.evaluateClusterer(dataa);
       double[] p=eval.getClusterAssignments();
       new AttributeSelection_Stats(dataa, eval,"Density Based Clustering" , p);  
       dataa=null;
       eval=null;
       algo=null;
       Runtime.getRuntime().gc();
       System.runFinalization ();
       
    }
    
}
