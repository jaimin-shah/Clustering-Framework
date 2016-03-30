package core;

import java.net.URI;
import clusterers.*;
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
			
			/*
			
			Use the ClusteringAlgorithmFactory here
			
			ClusteringAlgorithm algo = ClusteringAlgorithmFactory.getAlgorithmInstance(it.next());
			algo.commit(filepath)
			
			
			so using this you will remove all the switch case.
			
			*/
			
			//delegate the algorithms
			switch(it.next()) {
				case "DBSCAN":
                                    executor.execute(new Runnable() {

                                        @Override
                                        public void run() {
                                           new DBS(filePath);
                                           pendingAlgorithms.countDown();
                                        }
                                    });
					break;
				case "Cobweb":
                                     executor.execute(new Runnable() {

                                        @Override
                                        public void run() {
                                          new cobweb(filePath); 
                                          pendingAlgorithms.countDown();
                                        }
                                    });
					break;
				case "KMeans":	
                                     executor.execute(new Runnable() {

                                        @Override
                                        public void run() {
                                           new kmeans(filePath);
                                           pendingAlgorithms.countDown();
                                        }
                                    });
					break;
				case "Hierarchial":
                                     executor.execute(new Runnable() {

                                        @Override
                                        public void run() {
                                          new hierarchy(filePath); 
                                          pendingAlgorithms.countDown();
                                        }
                                    });
					break;
				case "Farthest First":
                                     executor.execute(new Runnable() {

                                        @Override
                                        public void run() {
                                           new farthest(filePath);
                                           pendingAlgorithms.countDown();
                                        }
                                    });
					break;
				case "EM":	
                                     executor.execute(new Runnable() {

                                        @Override
                                        public void run() {
                                           new em(filePath);
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
