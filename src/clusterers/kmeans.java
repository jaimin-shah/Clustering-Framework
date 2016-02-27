

package clusterers;

import gui.AttributeSelection_Stats;
import java.net.URI;
import weka.clusterers.ClusterEvaluation;

import weka.clusterers.SimpleKMeans;
import weka.core.Instances;
import weka.core.converters.ConverterUtils.DataSource;



public class kmeans {
	
	private static String filePath;
	public kmeans(String f) {
		filePath = f;
		try {
			compute();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

   private void compute() throws Exception {
	   //System.out.println("Hello " + filePath.toString());
Instances dataa = DataSource.read(filePath); 

SimpleKMeans kMeans;
    // create the model 
    kMeans  = new SimpleKMeans();
    kMeans.setNumClusters(3);
    kMeans.buildClusterer(dataa); 
    ClusterEvaluation eval=new ClusterEvaluation();
    eval.setClusterer(kMeans);
    eval.evaluateClusterer(dataa);
    double p[]=eval.getClusterAssignments();
    
    new AttributeSelection_Stats(dataa, eval, "KMEANS", p);
    // print out the cluster centroids
//    Instances centroids = kMeans.getClusterCentroids(); 
//    for (int i = 0; i < centroids.numInstances(); i++) { 
//      System.out.println( "Centroid " + i+1 + ": " + centroids.instance(i)); 
//    } 

     
            
   }

}
