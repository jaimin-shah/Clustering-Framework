/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clusterers;

import gui.*;
import java.util.Arrays;
import java.util.HashMap;

import weka.clusterers.ClusterEvaluation;
import weka.clusterers.Cobweb;
import weka.core.Instances;
import weka.core.converters.ConverterUtils.DataSource;

/*
 * usage: pass path of file during creating object instance
 */
public class cobweb {

	
	//int seed,double acuity,double cutoff
	
	//get default parameters for Cobweb
	public static HashMap<String, Double> getDefaults(){
		
		HashMap<String, Double> hm = new HashMap<>();
		hm.put("seed", 42.0);
		hm.put("acuity", 1.0);
		hm.put("cutoff", 0.0028209479177387815);
		
		return hm;
		
	}
	
	//set default parameters for Cobweb
	public static HashMap<String, Double> setParameters(double seed, double acuity, double cutoff) {
    	HashMap<String, Double> hm = new HashMap<String, Double>(5);
    	hm.put("seed", seed);
    	hm.put("acuity", acuity);
    	hm.put("cutoff", cutoff);
    	return hm;
    }
	
    public cobweb() {

    }
    
    //run algo by providing file path
    public void compute(String filePath, HashMap<String, Double> hm) throws Exception {
        // TODO code application logic here
		Instances dataa = DataSource.read(filePath); 
		compute(dataa, hm);
    }
    
    //run algo by providing data instances
    public void compute(Instances dataa, HashMap<String, Double> hm) throws Exception {
    	
    	Cobweb algo=new Cobweb();
 	   
	    algo.setAcuity(hm.get("acuity"));
	    algo.setCutoff(hm.get("cutoff"));
	    algo.setSeed(hm.get("seed").intValue());
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
