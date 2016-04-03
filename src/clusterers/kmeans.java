

package clusterers;

import gui.AttributeSelection_Stats;
import java.net.URI;
import java.util.HashMap;

import weka.clusterers.ClusterEvaluation;

import weka.clusterers.SimpleKMeans;
import weka.core.Instances;
import weka.core.converters.ConverterUtils.DataSource;

public class kmeans {
	
	//int maxinter,int no_clusters,int seed

	public kmeans() {}
	
	//get default parameters for kmeans
	public static HashMap<String, Double> getDefaults() {
		
		HashMap<String, Double> hm = new HashMap<String, Double>(5);
		hm.put("maxiter", 500.0);
		hm.put("no_of_clusters", 3.0);
		hm.put("seed", 10.0);
		
		return hm;
	}
	
	//explicitly set kmeans parameters
	public HashMap<String, Double> setParameters(double clusters, double maxiter, double seed) {
    	HashMap<String, Double> hm = new HashMap<String, Double>(5);
    	hm.put("seed", seed);
    	hm.put("no_of_clusters", clusters);
    	hm.put("maxiter", maxiter);
    	return hm;
    }

	public void compute(String filePath, HashMap<String, Double> hm) throws Exception {
		   
		Instances dataa = DataSource.read(filePath); 
		
		SimpleKMeans kMeans;
		    // create the model 
		kMeans  = new SimpleKMeans();
		kMeans.setMaxIterations(hm.get("maxiter").intValue());
		kMeans.setSeed(hm.get("seed").intValue());
		kMeans.setNumClusters(hm.get("no_of_clusters").intValue());
		kMeans.buildClusterer(dataa); 
		ClusterEvaluation eval=new ClusterEvaluation();
		eval.setClusterer(kMeans);
		eval.evaluateClusterer(dataa);
		double p[]=eval.getClusterAssignments();
		
		new AttributeSelection_Stats(dataa, eval, "KMEANS", p);
		dataa=null;
		eval=null;
		kMeans=null;
		Runtime.getRuntime().gc();
		System.runFinalization ();

     
            
   }

}
