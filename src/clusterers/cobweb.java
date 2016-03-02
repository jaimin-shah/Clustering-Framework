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
public class cobweb {

    private String filePath;
    
    public cobweb(String f) {
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
      
        Cobweb algo=new Cobweb();
       
       
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
