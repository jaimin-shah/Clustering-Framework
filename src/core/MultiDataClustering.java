package core;

import java.io.File;
import java.util.HashMap;
import java.util.HashSet;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import clusterers.*;

public class MultiDataClustering {
	
	String selectedAlgorithm = null;
	HashMap<String, Double> parameters = new HashMap<String, Double>();
	File[] selectedFiles = null;
	
	ExecutorService executor;
	
	public MultiDataClustering() {
		executor = Executors.newFixedThreadPool(5);
		System.out.println("5 worker threads started..");
	}
	
	public void setAlgorithm(String s, HashMap<String, Double> hm) {
		selectedAlgorithm = s;
		parameters = hm;
	}
	
	public String getAlgorithm() {
		return selectedAlgorithm;
	}
	
	public void setSelectedFiles(File[] f) {
		selectedFiles = f;
	}
	
	public File[] getFiles() {
		return selectedFiles;
	}
	
	public void runAlgorithm() {	
		CountDownLatch pendingFiles = new CountDownLatch(selectedFiles.length);
		
		//run selected algorithm on all files selected
		switch(selectedAlgorithm) {
			case "KMEANS":
				
				for(int i = 0; i < selectedFiles.length; i++) {
					final String s = selectedFiles[i].getPath();
					executor.execute(new Runnable() {
						
						@Override
						public void run() {
							// TODO Auto-generated method stub
							kmeans kms = new kmeans();
							try {
								kms.compute(s, parameters);
							} catch (Exception e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
							pendingFiles.countDown();
						}
					});
				}
				
				
				break;
			case "HIERARCHICAL" :
				
				for(int i = 0; i < selectedFiles.length; i++) {
					final String s = selectedFiles[i].getPath();
					executor.execute(new Runnable() {
						
						@Override
						public void run() {
							// TODO Auto-generated method stub
							hierarchy hry = new hierarchy();
							try {
								hry.compute(s, parameters);
							} catch (Exception e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
							pendingFiles.countDown();
						}
					});
				}
				
				break;
			case "EM":
				for(int i = 0; i < selectedFiles.length; i++) {
					final String s = selectedFiles[i].getPath();
					executor.execute(new Runnable() {
						
						@Override
						public void run() {
							// TODO Auto-generated method stub
							em ems = new em();
							try {
								ems.compute(s, parameters);
							} catch (Exception e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
							pendingFiles.countDown();
						}
					});
				}
				
				break;
			case "COBWEB":
				for(int i = 0; i < selectedFiles.length; i++) {
					final String s = selectedFiles[i].getPath();
					executor.execute(new Runnable() {
						
						@Override
						public void run() {
							// TODO Auto-generated method stub
							cobweb cb = new cobweb();
							try {
								cb.compute(s, parameters);
							} catch (Exception e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
							pendingFiles.countDown();
						}
					});
				}
				
				break;
			case "DBSCAN":
				for(int i = 0; i < selectedFiles.length; i++) {
					final String s = selectedFiles[i].getPath();
					executor.execute(new Runnable() {
						
						@Override
						public void run() {
							// TODO Auto-generated method stub
							DBS db = new DBS();
							try {
								db.compute(s, parameters);
							} catch (Exception e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
							pendingFiles.countDown();
						}
					});
				}
				
				break;
			case "FARTHEST FIRST":
				for(int i = 0; i < selectedFiles.length; i++) {
					final String s = selectedFiles[i].getPath();
					executor.execute(new Runnable() {
						
						@Override
						public void run() {
							// TODO Auto-generated method stub
							farthest ft = new farthest();
							try {
								ft.compute(s, parameters);
							} catch (Exception e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
							pendingFiles.countDown();
						}
					});
				}
				
				break;
		}
		
		try {
			pendingFiles.await();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void stopWorkerThreads() {
		System.out.println("shutting 5 workers");
		executor.shutdown();
	}
}
