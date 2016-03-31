package core;

import java.net.URI;
import clusterers.*;

import java.util.HashMap;
import java.util.HashSet;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.swing.JCheckBox;
import javax.swing.text.html.HTMLDocument.Iterator;

public class MultiAlgorithmClustering {
	
	//location of file
	String filePath;
	
	//algorithms to be run on data
	HashSet<String> selectedAlgorithms;
	
	//for creating a pool of threads
	ExecutorService executor;
	
	//constructor
	public MultiAlgorithmClustering() {
		selectedAlgorithms = new HashSet<String>();
		executor = Executors.newFixedThreadPool(7);
		System.out.println("started 7 worker threads " + Runtime.getRuntime().availableProcessors());
	}
	
	//stop worker threads
	public void stopWorkerThreads() {
		System.out.println("Shutting 7 workers");
		executor.shutdown();
	}
	
	//add algos to list
	public void addAlgorithms(String s) {
		selectedAlgorithms.add(s);
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
	
	//fil path setter
	public void setFilePath(String f) {
		filePath = f;
	}
	
	//run algorithms
	public void runAlgorithms() {
		
		java.util.Iterator<String> it = selectedAlgorithms.iterator();
            
		CountDownLatch pendingAlgorithms = new CountDownLatch(selectedAlgorithms.size());
		
		while(it.hasNext()) {
			
			//delegate the algorithms
			switch(it.next()) {
				case "DBSCAN":
                                    executor.execute(new Runnable() {

                                        @Override
                                        public void run() {
                                           DBS dbs = new DBS();
                                           
                                           try {
											dbs.compute(filePath, dbs.getDefaults());
										} catch (Exception e) {
											// TODO Auto-generated catch block
											e.printStackTrace();
										}
                                           pendingAlgorithms.countDown();
                                        }
                                    });
					break;
				case "Cobweb":
                                     executor.execute(new Runnable() {

                                        @Override
                                        public void run() {
                                          cobweb cb = new cobweb(); 
                                          
                                          try {
											cb.compute(filePath, cb.getDefaults());
										} catch (Exception e) {
											// TODO Auto-generated catch block
											e.printStackTrace();
										}
                                          pendingAlgorithms.countDown();
                                        }
                                    });
					break;
				case "KMeans":	
                                     executor.execute(new Runnable() {

                                        @Override
                                        public void run() {
                                           kmeans kms = new kmeans();
                                           
                                           try {
											kms.compute(filePath, kms.getDefaults());
										} catch (Exception e) {
											// TODO Auto-generated catch block
											e.printStackTrace();
										}
                                           
                                           pendingAlgorithms.countDown();
                                        }
                                    });
					break;
				case "Hierarchial":
                                     executor.execute(new Runnable() {

                                        @Override
                                        public void run() {
                                          hierarchy hry = new hierarchy(); 
                                          
                                          try {
											hry.compute(filePath, hry.getDefaults());
										} catch (Exception e) {
											// TODO Auto-generated catch block
											e.printStackTrace();
										}
                                          
                                          pendingAlgorithms.countDown();
                                        }
                                    });
					break;
				case "Farthest First":
                                     executor.execute(new Runnable() {

                                        @Override
                                        public void run() {
                                           farthest ft = new farthest();
                                           
                                           try {
											ft.compute(filePath, ft.getDefaults());
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
											emc.compute(filePath, emc.getDefaults());											
											
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
