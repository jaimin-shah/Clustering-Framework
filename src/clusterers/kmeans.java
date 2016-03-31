

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
	
	public static HashMap<String, Double> getDefaults() {
		
		HashMap<String, Double> hm = new HashMap<String, Double>();
		hm.put("maxiter", 10.0);
		hm.put("no_of_clusters", 5.0);
		hm.put("seed", 1.0);
		
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
