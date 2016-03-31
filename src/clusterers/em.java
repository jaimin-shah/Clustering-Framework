/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package clusterers;

import gui.AttributeSelection_Stats;
import java.io.File;
import java.util.HashMap;

import weka.clusterers.*;
import weka.clusterers.EM;
import weka.core.Instance;
import weka.core.Instances;
import weka.core.converters.CSVLoader;
import weka.core.converters.ConverterUtils.DataSource;


public class em  {
	
	//int maxiter,int seed,int no_of_clusters,double minstddev    
    public em() {}
    
    public static HashMap<String, Double> getDefaults() {
    	HashMap<String, Double> hm = new HashMap<String, Double>();
    	
    	hm.put("maxiter", 10.0);
    	hm.put("seed", 10.0);
    	hm.put("no_of_clusters", 5.0);
    	hm.put("minstddev", 1.0);
    	
    	return hm;
    }
    
    public void compute(String filePath, HashMap<String, Double> hm) throws Exception {
        // TODO code application logic here
        Instances dataa = DataSource.read(filePath); 
      

       EM algo=new EM();
       
       algo.setMinStdDev(hm.get("minstddev"));
       algo.setMaxIterations(hm.get("maxiter").intValue());
       algo.setSeed(hm.get("seed").intValue());
       algo.setNumClusters(hm.get("no_of_clusters").intValue());
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
