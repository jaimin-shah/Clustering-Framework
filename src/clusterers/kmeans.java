

package clusterers;

import gui.AttributeSelection_Stats;
import java.net.URI;
import weka.clusterers.ClusterEvaluation;

import weka.clusterers.SimpleKMeans;
import weka.core.Instances;
import weka.core.converters.ConverterUtils.DataSource;

public class kmeans {
	
	private static String filePath;
        int maxinter;
        int no_clusters;
        int seed;
	public kmeans(String f,int maxinter,int no_clusters,int seed) {
                this.maxinter=maxinter;
                this.no_clusters=no_clusters;
                this.seed=seed;
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
    kMeans.setMaxIterations(maxinter);
    kMeans.setSeed(seed);
    kMeans.setNumClusters(no_clusters);
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
    // print out the cluster centroids
//    Instances centroids = kMeans.getClusterCentroids(); 
//    for (int i = 0; i < centroids.numInstances(); i++) { 
//      System.out.println( "Centroid " + i+1 + ": " + centroids.instance(i)); 
//    } 

     
            
   }

}
