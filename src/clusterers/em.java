/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package clusterers;

import java.io.File;
import weka.clusterers.*;
import weka.clusterers.EM;
import weka.core.Instance;
import weka.core.Instances;
import weka.core.converters.CSVLoader;
import weka.core.converters.ConverterUtils.DataSource;


public class em  {

    private String filePath;
    public em(String f) {
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
