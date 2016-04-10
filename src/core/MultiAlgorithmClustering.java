package core;

import java.net.URI;
import clusterers.*;
import weka.core.Instances;
import weka.core.converters.ConverterUtils.DataSource;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.swing.JCheckBox;
import javax.swing.text.html.HTMLDocument.Iterator;

public class MultiAlgorithmClustering {
	
	//location of file
	String filePath;
	
	//it's data instance
	Instances data;
	
	//algorithms to be run on data
	HashMap<String, HashMap<String, Double>> selectedAlgorithms;
	
	//for creating a pool of threads
	ExecutorService executor;
	
	//constructor
	public MultiAlgorithmClustering() {
		selectedAlgorithms = new HashMap<String, HashMap<String, Double>>();
		executor = Executors.newFixedThreadPool(7);
		System.out.println("started 7 worker threads " + Runtime.getRuntime().availableProcessors());
	}
	
	//stop worker threads
	public void stopWorkerThreads() {
		System.out.println("Shutting 7 workers");
		executor.shutdown();
	}
	
	//add algos to list
	public void addAlgorithms(String s, HashMap<String, Double> hm) {
		selectedAlgorithms.put(s, hm);
	}
	
	//remove algos from list
	public void removeAlgorithms(String s) {
		selectedAlgorithms.remove(s);
	}
	
	//is list of algorithms empty?
	public boolean isAlgorithmsListEmpty() {
		return selectedAlgorithms.isEmpty();
	}
	
	//file-path getter
	public String getFilePath() {
		return filePath;
	}
	
	//file path setter
	public void setFilePath(String f) {
		filePath = f;
		try {
			data = DataSource.read(f);
		}
		catch(Exception e) {
			System.out.println(e);
		}
	}
	
	//run algorithms
	public void runAlgorithms() {
		
		Set<Map.Entry<String, HashMap<String, Double>>> set = selectedAlgorithms.entrySet();
            
		CountDownLatch pendingAlgorithms = new CountDownLatch(selectedAlgorithms.size());
		for(Map.Entry<String, HashMap<String, Double>> mapentry : set) {
			
			System.out.println(mapentry.getKey());
			System.out.println(mapentry.getValue());
			//delegate the algorithms
			switch(mapentry.getKey()) {
				case "DBSCAN":
                                    executor.execute(new Runnable() {

                                        @Override
                                        public void run() {
                                           DBS dbs = new DBS();
                                           
                                           try {
                                        	   
                                        	   dbs.compute(data, mapentry.getValue());
										} catch (Exception e) {
											// TODO Auto-generated catch block
											e.printStackTrace();
										}
                                           pendingAlgorithms.countDown();
                                        }
                                    });
					break;
				case "COBWEB":
                                     executor.execute(new Runnable() {

                                        @Override
                                        public void run() {
                                          cobweb cb = new cobweb(); 
                                          
                                          try {
											cb.compute(data, mapentry.getValue());
										} catch (Exception e) {
											// TODO Auto-generated catch block
											e.printStackTrace();
										}
                                          pendingAlgorithms.countDown();
                                        }
                                    });
					break;
				case "KMEANS":	
                                     executor.execute(new Runnable() {

                                        @Override
                                        public void run() {
                                           kmeans kms = new kmeans();
                                           
                                           try {
											kms.compute(data, mapentry.getValue());
										} catch (Exception e) {
											// TODO Auto-generated catch block
											e.printStackTrace();
										}
                                           
                                           pendingAlgorithms.countDown();
                                        }
                                    });
					break;
				case "HIERARCHICAL":
                                     executor.execute(new Runnable() {

                                        @Override
                                        public void run() {
                                          hierarchy hry = new hierarchy(); 
                                          
                                          try {
											hry.compute(data, mapentry.getValue());
										} catch (Exception e) {
											// TODO Auto-generated catch block
											e.printStackTrace();
										}
                                          
                                          pendingAlgorithms.countDown();
                                        }
                                    });
					break;
				case "FARTHEST FIRST":
                                     executor.execute(new Runnable() {

                                        @Override
                                        public void run() {
                                           farthest ft = new farthest();
                                           
                                           try {
											ft.compute(data, mapentry.getValue());
										} catch (Exception e) {
											// TODO Auto-generated catch block
											e.printStackTrace();
										}
                                           
                                           pendingAlgorithms.countDown();
                                        }
                                    });
					break;
				case "EM":	
                                     executor.execute(new Runnable() {

                                        @Override
                                        public void run() {
                                           em emc = new em();
                                           
                                           try {
											emc.compute(data, mapentry.getValue());											
											
										} catch (Exception e) {
											// TODO Auto-generated catch block
											e.printStackTrace();
										}
                                           
                                           pendingAlgorithms.countDown();
                                        }
                                    });
					break;
                           
			}
		}
		
		try {
			pendingAlgorithms.await();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
