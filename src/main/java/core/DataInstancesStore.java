package core;

import java.util.HashMap;

import weka.core.Instances;
import weka.core.converters.ConverterUtils.DataSource;

public class DataInstancesStore {
	
	private static HashMap<String, Instances> hm = new HashMap<String, Instances>();
	
	public static Instances getDataInstanceOf(String file) {
		return hm.get(file);
	}
	
	public static boolean hasDataInstance(String file) {
		return hm.containsKey(file);
	}
	
	public static Instances computeDataInstance(String file) throws Exception {
		System.out.println("Data Processed..");
		Instances i = DataSource.read(file);
		hm.put(file, i);
		
		return i;
	}
}
