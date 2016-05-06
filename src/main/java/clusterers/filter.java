/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clusterers;

import weka.clusterers.ClusterEvaluation;
import weka.clusterers.FilteredClusterer;
import weka.core.Instances;
import weka.core.converters.ConverterUtils;

/**
 *
 * @author hp
 */
public class filter {

    /**
     * @param args the command line arguments
     * @throws java.lang.Exception
     */
    public static void main(String[] args) throws Exception {
        // TODO code application logic here
        Instances dataa = ConverterUtils.DataSource.read("C:\\Users\\hp\\Downloads\\weka-3-6-13\\weka-3-6-13\\data\\iris.arff"); 
      
        FilteredClusterer algo=new FilteredClusterer();
      
       
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
