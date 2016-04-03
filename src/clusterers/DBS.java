

package clusterers;

import java.util.HashMap;

import gui.AttributeSelection_Stats;
import visualize.*;

import weka.clusterers.ClusterEvaluation;
import weka.clusterers.DBSCAN;
import weka.clusterers.SimpleKMeans;
import weka.core.Instances;
import weka.core.converters.ConverterUtils.DataSource;



public class DBS {

	public DBS() {}
	
	//double epsilon, int minpoints 
	
	//get default parameters for DbScan
	public static HashMap<String, Double> getDefaults() {
		
		HashMap<String, Double> hm = new HashMap<String, Double>(5);
		hm.put("epsilon", 0.9);
		hm.put("minpoints", 6.0);
		
		return hm;
	}
	
	//set parameters for DbScan
	public HashMap<String, Double> setParameters(double epsilon, double minpoints) {
    	HashMap<String, Double> hm = new HashMap<String, Double>(5);
    	hm.put("epsilon", epsilon);
    	hm.put("minpoints", minpoints);
    	return hm;
    }
	
	public void compute(String filePath, HashMap<String, Double> hm) throws Exception {
		Instances dataa = DataSource.read(filePath); 

		DBSCAN DBS;
		// create the model 
		DBS  = new DBSCAN();
		
		DBS.setEpsilon(hm.get("epsilon"));
        DBS.setMinPoints(hm.get("minpoints").intValue());
		ClusterEvaluation eval = new ClusterEvaluation();
		DBS.buildClusterer(dataa); 
		        
		// print out the cluster centroids
		eval.setClusterer(DBS);
		
		eval.evaluateClusterer(dataa);
		double[] p=eval.getClusterAssignments();
		new AttributeSelection_Stats(dataa, eval, "DBS SCAN", p);
		
		dataa=null;
		eval=null;
		DBS=null;
		Runtime.getRuntime().gc();
		System.runFinalization ();

   }

}
