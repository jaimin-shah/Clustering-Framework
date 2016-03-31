/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package clusterers;

import gui.AttributeSelection_Stats;
import java.io.File;
import weka.clusterers.*;
import weka.clusterers.EM;
import weka.core.Instance;
import weka.core.Instances;
import weka.core.converters.CSVLoader;
import weka.core.converters.ConverterUtils.DataSource;


public class em  {
    int maxiter,seed,no_of_clusters;
    double minstddev;
    private String filePath;
    public em(String f,int maxiter,int seed,int no_of_clusters,double minstddev) {
        this.maxiter=maxiter;
        this.seed=seed;
        this.no_of_clusters=no_of_clusters;
        this.minstddev=minstddev;
    	filePath = f;
    	try {
			compute();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
    private void compute() throws Exception {
        // TODO code application logic here
        Instances dataa = DataSource.read(filePath); 
      

       EM algo=new EM();
       
       algo.setMinStdDev(minstddev);
       algo.setMaxIterations(maxiter);
       algo.setSeed(seed);
       algo.setNumClusters(no_of_clusters);
       algo.buildClusterer(dataa);
       ClusterEvaluation eval=new ClusterEvaluation();
       eval.setClusterer(algo);
       eval.evaluateClusterer(dataa);
       double[] p=eval.getClusterAssignments();
       new AttributeSelection_Stats(dataa, eval, "EM", p);
       dataa=null;
       eval=null;
       algo=null;
       Runtime.getRuntime().gc();
       System.runFinalization ();
       
       
    }

}
