/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clusterers;
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
        
        System.out.println(eval.getNumClusters());
        for(int i=0;i<p.length;i++)
        {
            System.out.println(i+" "+(int)p[i]);
        }
       
       
    }
    
}
