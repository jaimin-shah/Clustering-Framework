

package clusterers;

import visualize.graph;
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
     System.out.println("# of clusters: " + eval.getNumClusters()); 
    
    // get cluster membership for each instance 
    for (int i = 0; i < dataa.numInstances(); i++) { 
        try
        {
          System.out.println( dataa.instance(i) + " is in cluster " + DBS.clusterInstance(dataa.instance(i)) );   
        }
        catch(Exception e)
        {
            
        }
      
    } 
       new graph(dataa, eval, 1, 0);
   }

}
