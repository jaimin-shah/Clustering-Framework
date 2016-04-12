package core;

import java.util.HashMap;

import weka.core.Instances;
import weka.core.converters.ConverterUtils.DataSource;

public class DataInstancesStore {
	
	private static HashMap<String, Instances> hm = new HashMap<String, Instances>();
        private static HashMap<String, Integer> hm1 = new HashMap<String, Integer>();
	
	public static synchronized Instances getDataInstanceOf(String file) {
            System.out.println("get");
                int i1=hm1.get(file);
                i1++;
                hm1.put(file, i1);
		return hm.get(file);
	}
        
        public static synchronized void remove(String file)
        {
            System.out.println("in remove");
                int i1=hm1.get(file);
                i1--;
                System.out.println(i1);
                hm1.put(file, i1);
                if(i1==0)
                {
                    System.out.println("remove instance");
                    hm.remove(file);
                    hm1.remove(file);
                }
        }
	
	public static boolean hasDataInstance(String file) {
		return hm.containsKey(file);
	}
	
	public static Instances computeDataInstance(String file) throws Exception {
		System.out.println("Data Processed..");
		Instances i = DataSource.read(file);
		hm.put(file, i);
		hm1.put(file, 1);
		return i;
	}
        public static Instances InitializeDataInstance(String file) throws Exception {
		
		Instances i = DataSource.read(file);
		hm.put(file, i);
		hm1.put(file, 0);
		return i;
	}
}
