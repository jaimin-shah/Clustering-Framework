

package clusterers;

import java.net.URI;

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

    // print out the cluster centroids
    Instances centroids = kMeans.getClusterCentroids(); 
    for (int i = 0; i < centroids.numInstances(); i++) { 
      System.out.println( "Centroid " + i+1 + ": " + centroids.instance(i)); 
    } 

    // get cluster membership for each instance 
    for (int i = 0; i < dataa.numInstances(); i++) { 
      System.out.println( dataa.instance(i) + " is in cluster " + kMeans.clusterInstance(dataa.instance(i)) + 1); 

    } 
   }

}
