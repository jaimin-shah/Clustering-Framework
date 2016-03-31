/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clusterers;

import gui.*;
import java.util.Arrays;
import weka.clusterers.ClusterEvaluation;
import weka.clusterers.Cobweb;
import weka.core.Instances;
import weka.core.converters.ConverterUtils.DataSource;

/*
 * usage: pass path of file during creating object instance
 */
public class cobweb implements ClusteringAlgorithm {

    private String filePath;
    double acuity,cutoff;
    int seed;
    public cobweb(String f,int seed,double acuity,double cutoff) {
        this.acuity=acuity;
        this.seed=seed;
        this.cutoff=cutoff;
    	filePath = f;
    	try {
			compute();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
    public void compute() throws Exception {
        // TODO code application logic here
        Instances dataa = DataSource.read(filePath); 
      
        Cobweb algo=new Cobweb();
       
       algo.setAcuity(acuity);
       algo.setCutoff(cutoff);
       algo.setSeed(seed);
       algo.buildClusterer(dataa);
       ClusterEvaluation eval=new ClusterEvaluation();
       eval.setClusterer(algo);
       eval.evaluateClusterer(dataa);
        double[] p=eval.getClusterAssignments();
        new AttributeSelection_Stats(dataa, eval, "COBWEB", p);
        dataa=null;
        eval=null;
        algo=null;
        Runtime.getRuntime().gc();
        System.runFinalization ();
    }
    
}
