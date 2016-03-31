/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clusterers;

import java.util.HashMap;

import gui.AttributeSelection_Stats;
import weka.clusterers.ClusterEvaluation;
import weka.clusterers.FarthestFirst;
import weka.core.Instances;
import weka.core.converters.ConverterUtils;


public class farthest {

	
	//int seed,int no_of_clusters
    
    public farthest() {}
    
    public static HashMap<String, Double> getDefaults() {
    	
    	HashMap<String, Double> hm = new HashMap<String, Double>();
    	
    	hm.put("seed", 10.0);
    	hm.put("no_of_clusters", 3.0);
    	
    	return hm;
    }
    
    public void compute(String filePath, HashMap<String, Double> hm) throws Exception {
        // TODO code application logic here
        Instances dataa = ConverterUtils.DataSource.read(filePath); 
      
        FarthestFirst algo=new FarthestFirst();
      
       algo.setNumClusters(hm.get("no_of_clusters").intValue());
       algo.setSeed(hm.get("seed").intValue());
       
       algo.buildClusterer(dataa);
       ClusterEvaluation eval=new ClusterEvaluation();
       eval.setClusterer(algo);
       eval.evaluateClusterer(dataa);
       double[] p=eval.getClusterAssignments();
       new AttributeSelection_Stats(dataa, eval, "Farthest First", p);  
       dataa=null;
       eval=null;
       algo=null;
       Runtime.getRuntime().gc();
       System.runFinalization ();
       
    }
    
}
