

package clusterers;

import gui.AttributeSelection_Stats;
import visualize.*;

import weka.clusterers.ClusterEvaluation;
import weka.clusterers.DBSCAN;
import weka.clusterers.SimpleKMeans;
import weka.core.Instances;
import weka.core.converters.ConverterUtils.DataSource;



public class DBS {

	private String filePath;
	
	public DBS(String f) {
		filePath = f;
		try {
			compute();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
   private void compute() throws Exception {
		Instances dataa = DataSource.read(filePath); 

		DBSCAN DBS;
		// create the model 
		DBS  = new DBSCAN();


		ClusterEvaluation eval = new ClusterEvaluation();
		DBS.buildClusterer(dataa); 

		// print out the cluster centroids
		eval.setClusterer(DBS);

		eval.evaluateClusterer(dataa);
		double[] p=eval.getClusterAssignments();
		new AttributeSelection_Stats(dataa, eval, "DBS SCAN", p);

   }

}
